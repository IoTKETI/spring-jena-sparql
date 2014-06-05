#!/bin/bash
uniprot=" http://www.uniprot.org/uniprot -v -L -G --data-urlencode 'query=__QUERY__' --data-urlencode 'format=list' 2>/dev/null"
#uniprot=" http://sp.isb-sib.ch/uniprot -v -L -G --data-urlencode 'query=__QUERY__' --data-urlencode 'format=list' 2>/dev/null"

# -w %{time_connect}:%{time_starttransfer}
sparql=" -X POST http://kant:8890/sparql -H 'Accept:application/sparql-results+json'  --data-urlencode 'query="

prefix="PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX owl: <http://www.w3.org/2002/07/owl#>
PREFIX dc: <http://purl.org/dc/elements/1.1/>
PREFIX dcterms: <http://purl.org/dc/terms/>
PREFIX foaf: <http://xmlns.com/foaf/0.1/>
PREFIX sim: <http://purl.org/ontology/similarity/>
PREFIX mo: <http://purl.org/ontology/mo/>
PREFIX ov: <http://open.vocab.org/terms/>
PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>
PREFIX : <http://nextprot.org/rdf#>
PREFIX entry: <http://nextprot.org/rdf/entry/>
PREFIX isoform: <http://nextprot.org/rdf/isoform/>
PREFIX annotation: <http://nextprot.org/rdf/annotation/>
PREFIX evidence: <http://nextprot.org/rdf/evidence/>
PREFIX xref: <http://nextprot.org/rdf/xref/>
PREFIX publication: <http://nextprot.org/rdf/publication/>
PREFIX term: <http://nextprot.org/rdf/terminology/>
PREFIX gene: <http://nextprot.org/rdf/gene/>
PREFIX source: <http://nextprot.org/rdf/source/>
PREFIX db: <http://nextprot.org/rdf/db/>
PREFIX context: <http://nextprot.org/rdf/context/>
"

curl=$(which curl)
default_uquery='keyword:"Cytoplasm [KW-ABCD]"'
label="uniprot"

# uniprot query
[ -z "$1" ] &&{
	echo "Paste your uniprot query:" 
	uquery=$(sed '/^$/q')
	[ -z "$uquery" ] && uquery=$default_uquery
	[ -n "$uquery" ] && {
		uniprot="${uniprot/__QUERY__/$uquery}"
		# echo "run uniprot: $curl$uniprot"
		sr=$(bash -c "$curl$uniprot|sort>/tmp/q.up.$$")
	}
}  
[ -n "$1" ] &&{
	echo "Paste your sparql query:" 
	fquery=$(sed '/^$/q')

	[ -n "$fquery" ] && {
		fsparql="$curl$sparql $prefix$fquery'"
		sr0=$(bash -c "$fsparql|egrep -o 'NX_[^\"]*'| cut -d _ -f 2|sort >/tmp/q.up.$$")
	}
	label="sparql1"

}
echo "Paste your sparql query:" 
squery=$(sed '/^$/q')

[ -n "$squery" ] && {
	sparql="$curl$sparql $prefix$squery'"
	sr=$(bash -c "$sparql|egrep -o 'NX_[^\"]*'| cut -d _ -f 2|sort >/tmp/q.sparql.$$")
}
[[ -f "/tmp/q.sparql.$$" && -e "/tmp/q.up.$$" ]] && {
	# comm /tmp/q.sparql.$$ /tmp/q.up.$$
	echo "Query result for PID $$ and comm file /tmp/q.comm.$$"
	cp=$(comm -i23 /tmp/q.sparql.$$ /tmp/q.up.$$)
	cp10=$(echo "$cp"|head -10)
	echo  "sparql only : $(echo "$cp"|wc -l), ${cp10//$'\n'/,} ..."
	cp=$(comm -i13 /tmp/q.sparql.$$ /tmp/q.up.$$)
	cp10=$(echo "$cp"|head -10)
	echo  "$label only: $(echo "$cp"|wc -l), ${cp10//$'\n'/,} ..."
	cp=$(comm -i12 /tmp/q.sparql.$$ /tmp/q.up.$$)
	cp10=$(echo "$cp"|head -10)
	echo "commons     : $(echo "$cp"|wc -l), ${cp10//$'\n'/,} ..."
	comm  /tmp/q.up.$$ /tmp/q.sparql.$$ >/tmp/q.comm.$$
} || {
  [ -f "/tmp/q.up.$$" ] && cat /tmp/q.up.$$
  [ -f "/tmp/q.sparql.$$" ] && cat /tmp/q.sparql.$$
}






