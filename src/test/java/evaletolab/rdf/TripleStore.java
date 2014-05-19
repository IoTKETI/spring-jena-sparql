package evaletolab.rdf;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;


import org.springframework.beans.factory.annotation.Autowired;
import virtuoso.jena.driver.VirtGraph;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class TripleStore {
	@Autowired
	public Properties config;
	
	
	private String endpoint;
	private Model model;
	private boolean isNative=false;
	
	//
	// identify the current test
	private String instanceSignature;
	
	private String prefix="PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" + 
			"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" + 
			"PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" + 
			"PREFIX dc: <http://purl.org/dc/elements/1.1/>\n" + 
			"PREFIX dcterms: <http://purl.org/dc/terms/>\n" + 
			"PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n" + 
			"PREFIX sim: <http://purl.org/ontology/similarity/>\n" + 
			"PREFIX mo: <http://purl.org/ontology/mo/>\n" + 
			"PREFIX ov: <http://open.vocab.org/terms/>\n" + 
			"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" + 
			"PREFIX : <http://nextprot.org/rdf#>\n" + 
			"PREFIX entry: <http://nextprot.org/rdf/entry/>\n" + 
			"PREFIX isoform: <http://nextprot.org/rdf/isoform/>\n" + 
			"PREFIX annotation: <http://nextprot.org/rdf/annotation/>\n" + 
			"PREFIX evidence: <http://nextprot.org/rdf/evidence/>\n" + 
			"PREFIX xref: <http://nextprot.org/rdf/xref/>\n" + 
			"PREFIX publication: <http://nextprot.org/rdf/publication/>\n" + 
			"PREFIX term: <http://nextprot.org/rdf/terminology/>\n" + 
			"PREFIX gene: <http://nextprot.org/rdf/gene/>\n" + 
			"PREFIX source: <http://nextprot.org/rdf/source/>\n" + 
			"PREFIX db: <http://nextprot.org/rdf/db/>\n" + 
			"PREFIX context: <http://nextprot.org/rdf/context/>\n";

	public void open(){
   
		//
		// trying to use virtuoso with the native driver 
		if (config.containsKey("virtuoso.url")){

			
			VirtGraph graph = new VirtGraph("http://nextprot.org/rdf", 
					config.getProperty("virtuoso.url"), 
					config.getProperty("virtuoso.user"), 
					config.getProperty("virtuoso.password")
			);
			model=ModelFactory.createModelForGraph(graph);
			instanceSignature="#id:"+generateTestId()+" host:"+config.getProperty("virtuoso.url")+"\n";
			isNative=true;
			return;
		}
		
		//
		// else we use the apsql endpoint (that doesn't support ARQ)
		endpoint=config.getProperty("sparql.endpoint");
		instanceSignature="#id:"+generateTestId()+" host:"+endpoint+"\n";
	}
	
	
	/**
	 * generate meta content as comment in the sparql query 
	 * @return
	 */
	private String generateTestId() {

		String newstring = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
		String testId = "TEST-SPARQL-QUERIES-" + newstring;
		return testId;
	}

	/**
	 * read meta info 'ac' in sparql query comment
	 * ac variable define the values we want to get as result 
	 * @param query
	 * @return
	 */
	public String getQueryMetaAc(String query){
		String split[]=query.split("#ac:");
		if (split.length==0)
			return "";
		return split[1].replaceAll("\\n.*", "");
	}
	
	/**
	 * read meta info 'count' in sparql query comment
	 * count variable define the size of the result 
	 * @param query
	 * @return
	 */
	public int getQueryMetaCount(String query){
		String split[]=query.split("#count:");
		if (split.length<2)
			return 0;
		return Integer.parseInt(split[1].replaceAll("\\n.*", ""));
	}

	/**
	 * read meta info 'title' in sparql query comment
	 * title variable define the query title 
	 * @param query
	 * @return
	 */
	public String getQueryMetaTitle(String query){
		Pattern p = Pattern.compile("^#title:(.+)$",Pattern.DOTALL | Pattern.MULTILINE);
		String split[]=query.split("#title:");
		if (split.length==0)
			return "";
		return split[1].replaceAll("\\n.*", "");
	}

	/**
	 * read meta info 'pending' in sparql query comment
	 * pending is the status of the query quality check
	 * @param query
	 * @return
	 */
	public boolean isQueryPending(String query){
		return query.indexOf("#pending")!=-1;
	}	
	

	/**
	 * get a normalized list of accession  
	 * @param rs
	 * @return
	 */
	public List<String> getLiterals(ResultSet rs){
		return getLiterals(rs,"entry","NX_");
	}
	
	/**
	 * get a list of literal
	 * @param rs
	 * @param variable
	 * @param replace
	 * @return
	 */
	public List<String> getLiterals(ResultSet rs, String variable, String replace){
		List<String> uri=new ArrayList<String>();		
        while(rs.hasNext()){
        	QuerySolution qs=rs.next();
        	uri.add(qs.getResource(variable).getLocalName().replace(replace,""));
        }
        Collections.sort(uri);
        return uri;
	}

	
	public QueryExecution createQueryExecution(String query ){
		if (isQueryPending(query))
			System.out.println("PENDING: "+getQueryMetaTitle(query));
		
		if(isNative){
			Query q = QueryFactory.create(prefix+instanceSignature+query);
	        return QueryExecutionFactory.create(q,model);			
		}
		return QueryExecutionFactory.sparqlService(endpoint,prefix+instanceSignature+query);
	}
	
	
	public boolean isNative(){
		return isNative;
	}
		
	public Model getModel(){
		return model;
	}
	
	public String getEndpoint(){
		return endpoint;
	}
	
	public String getPrefix(){
		return prefix;
	}

	
}
