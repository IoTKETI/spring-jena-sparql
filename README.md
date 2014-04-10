Advanced SPARQL for nextprot with spring-mvc, jena and virtuoso 
===============================================================
The purpose of this document is to give an original way to build and test the new advanced search engine for neXtProt. neXtProt is an on-line knowledge platform on human proteins. It is based on a top-down data integration process, materialized in a central SQL engine (postgres). neXtProt tends to integrate, with a top-down process, a large amount of data provided by independant groups (bottom-up process). Currently, all neXtProt's data can't be easily interrogated because of the lack of an advanced query engine. The nature of bioinformatics data makes this features difficult to achieve. Data are highly interconnected and are difficult to be normalized without adding useless of complexity.
This project proposes a solution to build an advanced query engine, based on the use cases provided by our (main) users. We currently have 91 queries that describe all perspectives of data for the first release. This is our first milestone, it mainly focuses on those queries. 

**This project will help to build a closed world RDF schema by iterations and tests. The schema creation mainly focuses on the user queries. It has nothing to do with semantic data in open world. It emphasizes on understandable SPARQL queries.**
> For example, All proteins which are located in **mitochondrion** with an evidence other than **HPA** and **DKFZ-GFP**
```SPARQL
  ?proteins :isoform/:localisation ?statement.
    ?statement :in/:childOf term:SL-0173 #Mitochondrion ; 
               :withEvidence/:fromXref/:notIn :HPA,:DKFZ-GFP
```  


This project also demonstrates how to use and configure a triplestore (open-virtuoso, fuseki) with Jena and spring-mvc. Following the instructions, you should be able to build your own neXtProt mirror

<!--
###RDFS and queries,
* [initial rdf schema](src/main/resources/owl)
* [view all sparql queries](src/test/resources/sparql)
-->

###Get your own triplestore instance 
* install open-virtuoso 7.x (redhat, ubuntu),
* get the neXtProt triples, 
* install virtuoso jena driver ([download Jena2 provider and jdbc4  jars](http://virtuoso.openlinksw.com/dataspace/doc/dav/wiki/Main/VOSDownload#Jena%20Provider)),
```shell
$mvn install:install-file -Dfile=virt_jena2.jar -DgroupId=virtuoso.jena2 -DartifactId=virtuoso-jena2 -Dversion=2.10.x
$mvn install:install-file -Dfile=virtjdbc4.jar -DgroupId=virtuoso.jdbc4 -DartifactId=virtuoso-jdbc4 -Dversion=4.0
```
* Configure triplestore endpoint
  * in file [main.properties](src/main/resources/config/main.properties) configure your own virtuoso instance or use the public nextprot sparql endpoint
  * if you dont have a virtuoso instance, you can use the public access of nextprot sparql. To do that, you have to uncomment the variable 'sparql.endpoint' in the config/main.properties
  * __NOTE: the public access of nextprot sparql is scheduled for June 2014__

###Test  your configuration: run a single TestClasse
```shell
$mvn -Dtest=Integrity test
```
###Run all rdf tests
* [view all sparql test](src/test/java/evaletolab/rdf)
```shell
$mvn -Dtest=evaletolab.rdf.* test
```

###Walking the graph
The class [SparqlController.java](src/main/java/evaletolab/controller/SparqlController.java) implement the basic proxying with the triplestore. With a native Jena2 driver, you have the ability to mix, in a single SPARQL query, data from your native datastore and magic properties from Jena ARQ.
```shell
$ mvn jetty:run
```

![SNORQL](src/main/webapp/resources/img/snorql.png "snorql frontend")

###Use case for [evidences](src/test/java/evaletolab/rdf/Evidences.java)
* [Q53](src/test/resources/sparql/Q53-1-involvedInGO0007155_WithEvidence_NotIEA_And_NotISS.sparql)	which are involved in cell adhesion according to GO with an evidence not IAE and not ISS
* [Q57](src/test/resources/sparql/Q57-locatedInMitochondrionWithEvidenceOtherThan_HPA_And_DKFZ_GFP.sparql)	which are located in mitochondrion with an evidence other than HPA and DKFZ-GFP
* [Q63](src/test/resources/sparql/Q63-with1RRM_RNAbindingDomainWithEvidenceIEAorISS.sparql)	which have >=1 RRM RNA-binding domain and either no GO "RNA binding" other a GO "RNA binding" with evidence IEA or ISS

###Use case for [expression](src/test/java/evaletolab/rdf/Expression.java)
* QX  Proteins that are not highly expressed in liver at embrion stage
* [Q4](src/test/resources/sparql/Q4-highlyExpressedInBrainButNotInTestis.sparql)  highly expressed in brain but not expressed in testis
* [Q11](src/test/resources/sparql/Q11-expressedInLiverAndInvolvedInTransport.sparql) that are expressed in liver and involved in transport 
* Q[15](src/test/resources/sparql/Q15-PDZdomainthatInteractWithProteinExpresssedInBrain.sparql) with a PDZ domain that interact with at least 1 protein which is expressed in brain 
* [Q17](src/test/resources/sparql/Q17-gt1000aaAndLocatedInNucleusAndExpressedInNervousSystem.sparql) >=1000 amino acids and located in nucleus and expression in nervous system 
* [Q20](src/test/resources/sparql/Q20-HPAOnChromosome21highlyExpresssedInHeartAtIHCLevel.sparql) with >=2 HPA antibodies whose genes are located on chromosome 21 and that are highly expressed at IHC level in heart
* [Q50](src/test/resources/sparql/Q50-expressedInBrainAccordingIHCButNotExpressedInBrainAccordingMicroarray.sparql) which are expressed in brain according to IHC but not expressed in brain according to microarray
* [Q77](src/test/resources/sparql/Q77-expressedInLiverAccordingIHCButNotInHUPOLiverProteom.sparql) which are expressed in liver according to IHC data but not found in HUPO liver proteome set
* [Q83](./src/test/resources/sparql/Q83-expressedOnASingleTissue.sparql) whose genes are on chromosome N that are expressed only a single tissue/organ
* Q89 which are located in nucleus and expressed in brain and only have orthologs/paralogs in primates

###Use case for [sequence annotations](src/test/java/evaletolab/rdf/Features.java)
* [Q3](src/test/resources/sparql/Q3-with2TransmembraneRegions.sparql)	Proteins with >=2 transmembrane regions 
* [Q5](src/test/resources/sparql/Q5-locatedInMitochondrionAndLackATransitPeptide.sparql)	Proteins located in mitochondrion and that lack a transit peptide
* [Q9](src/test/resources/sparql/Q9-with3DisulfideBondsAndNotHormones.sparql)	Proteins with 3 disulfide bonds and that are not hormones 
* [Q13](src/test/resources/sparql/Q13-withKinaseDomainButNotKinaseActivity.sparql) Proteins with a protein kinase domain but no kinase activity 
* [Q14](src/test/resources/sparql/Q14-with2SH3And1SHD2.sparql) Proteins with 2 SH3 domains and 1 SH2 domain 
* [Q15](src/test/resources/sparql/Q15-PDZdomainthatInteractWithProteinExpresssedInBrain.sparql) Proteins with a PDZ domain that interact with at least 1 protein which is expressed in brain 
* [Q16](src/test/resources/sparql/Q16-withMature100AAWhichAreSecretedAndNotContainsCysteinesInMature.sparql) Proteins with a mature chain <= 100 amino acids which are secreted and do not contain cysteines in the mature chain 
* [Q18](src/test/resources/sparql/Q18-thatAreAcetylatedAndMethylated.sparql) Proteins that are acetylated and methylated and located in the nucleus 
* [Q19](src/test/resources/sparql/Q19-containsSignalSequenceFollowedByAExtracellularDomainContainingKRKRMotif.sparql) Proteins contains a signal sequence followed by a extracellular domain containing a "KRKR" motif 
* Q27 Proteins with >=1 glycosylation sites reported in PubMed:X or PubMed:Y
* [Q32](src/test/resources/sparql/Q32-withCoiledCoiledAndInvolvedInTranscriptionButNotContainBZIP.sparql) Proteins with a coiled coil region and involved in transcription but does not contain a bZIP domain
* [Q34](src/test/resources/sparql/Q34-withHomeoboxAndWithVariantsInTheHomeobox.sparql) Proteins with >=1 homeobox domain and with >=1 variant in the homeobox domain(s)
* [Q35](src/test/resources/sparql/Q35.sparql) Proteins located in the mitochondrion and which is an enzyme
* [Q38](src/test/resources/sparql/Q38.sparql) Proteins with >=1 selenocysteine in their sequence
* [Q39](src/test/resources/sparql/Q39.sparql) Proteins with >=1 mutagenesis in a position that correspond to an annotated active site
* [Q40](src/test/resources/sparql/Q40.sparql) Proteins that are enzymes and with >=1 mutagenesis that "decrease" or "abolish" activity
* [Q41](src/test/resources/sparql/Q41.sparql) Proteins that are annotated with GO "F" terms prefixed by "Not"
* [Q48](src/test/resources/sparql/Q48.sparql) Proteins with >=1 variants of the type "C->" (Cys to anything else) that are linked to >=1 disease
* [Q49](src/test/resources/sparql/Q49.sparql) Proteins with >=1 variants of the types "A->R" or "R->A"

###Use case for general [annotations](src/test/java/evaletolab/rdf/General.java)
* [Q1](src/test/resources/sparql/Q1.sparql) that are phosphorylated and located in the cytoplasm 
* [Q2](src/test/resources/sparql/Q2.sparql) that are located both in the cytoplasm and in the nucleus
* [Q5](src/test/resources/sparql/Q5.sparql) located in mitochondrion and that lack a transit peptide
* [Q6](src/test/resources/sparql/Q6.sparql) whose genes are on chromosome 2 and linked with a disease
* [Q7](src/test/resources/sparql/Q7.sparql) linked to diseases that are associated with cardiovascular aspects
* [Q8](src/test/resources/sparql/Q8.sparql) whose genes are x bp away from the location of the gene of protein Y
* [Q22](src/test/resources/sparql/Q22.sparql) Proteins with no function annotated
* [Q31](src/test/resources/sparql/Q31.sparql) Proteins with >=10 "splice" isoforms
* [Q32](src/test/resources/sparql/Q32.sparql) Proteins with a coiled coil region and involved in transcription but does not contain a bZIP domain
* [Q68](src/test/resources/sparql/Q68.sparql)	with protein evidence PE=2 (transcript level)
* [Q65](src/test/resources/sparql/Q65.sparql) Proteins with >1 catalytic activity 
* [Q73](src/test/resources/sparql/Q73.sparql) Proteins with no domain 
###Use case for PTM queries


It is compatible with tomcat and jetty maven plugins.

Use
    mvn tomcat7:run
or
    mvn jetty:run

Some sample controller (for SPARQL query provider and jena test) for proteins Expression are also provided.

