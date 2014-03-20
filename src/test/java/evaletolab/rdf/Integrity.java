package evaletolab.rdf;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;

import evaletolab.config.WebConfig;
import static org.junit.Assert.*;
/**
 * Use case for intigrity queries
 * - count Entries 
 * - Brain (TS-0095) relevant For 18202 entries (NX_P61604,NX_Q15029,NX_Q07973)
 * - Liver (TS-0564) relevant For 17848 entries (NX_P61604,NX_Q15029,NX_Q07973)
 * - :classifiedWith Transport (KW-0813) is relevant for 1859 entries (NX_Q9H0U6, NX_P08195, NX_P46098)
 * - 
 *  
 * @author evaleto
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
public class Integrity {

	private String endpoint="http://cactusprime:4040/bigdata/sparql";
	
	Model model, schema;
	InfModel rdfs;
	
	@Before
	public void setup() {
	}
	
	

	private QueryExecution createQueryExecution(String query){
		if(model!=null){
			Query q = QueryFactory.create(query);
	        return QueryExecutionFactory.create(q,model);			
		}
		return QueryExecutionFactory.sparqlService(endpoint,query);
	}
	
	
	
	/**
	 * nb entries (20'130)
	 * sql: select count(distinct(si.unique_name)) from nextprot.sequence_identifiers si 
	 *      where si.cv_type_id=2 AND si.cv_status_id=1
	 */
	
	@Test
	public void countEntries(){
		String q="PREFIX : <http://nextprot.org/rdf#>\n" + 
				 "SELECT (count(distinct ?entry)as ?c) WHERE { \n" + 
				 "  ?entry a :Entry.\n" + 
				 "}";
		
		
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
//        int rows=0;while (rs.hasNext()) {rs.next();rows++;}
        
        assertTrue("countEntries (20'130)",rs.next().get("c").asLiteral().getInt()>=20130);
	}	
	
	/**
	 * nb genes (22'715)
	 * sql: select count(distinct(si.unique_name)) from nextprot.sequence_identifiers si 
	 *      where si.cv_type_id=3 AND si.cv_status_id=1
	 */
	@Test
	public void countGenes(){
		String q="PREFIX : <http://nextprot.org/rdf#>\n" + 
				 "SELECT (count(distinct ?entry)as ?c) WHERE { \n" + 
				 "  ?entry a :Gene.\n" + 
				 "}";
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        assertTrue("countGenes (22'715)",rs.next().get("c").asLiteral().getInt()>=22715);

	}
	
	/**
	 * nb antibody (21'726)
	 * sql: select count(distinct(si.unique_name)) from nextprot.sequence_identifiers si 
	 *      where si.cv_type_id=6 AND si.cv_status_id=1
	 */
	@Test
	public void countAntobodies(){
		String q="PREFIX : <http://nextprot.org/rdf#>\n" + 
				 "SELECT (count(distinct ?entry)as ?c) WHERE { \n" + 
				 "  ?entry a :Antobody.\n" + 
				 "}";
	}			
	
	
	/**
	 * nb peptides (449'623)
	 * sql: select count(distinct(si.unique_name)) from nextprot.sequence_identifiers si 
	 *      where si.cv_type_id=7 AND si.cv_status_id=1
	 */
	@Test
	public void countPeptides(){
		String q="PREFIX : <http://nextprot.org/rdf#>\n" + 
				 "SELECT (count(distinct ?entry)as ?c) WHERE { \n" + 
				 "  ?entry a :Peptide.\n" + 
				 "}";
	}
	
	/**
	 * nb isoforms (39'651)  
	 * sql: select count(distinct(si.unique_name)) from nextprot.sequence_identifiers si 
	 *      where si.cv_type_id=2 AND si.cv_status_id=1
	 */
	@Test
	public void countIsoforms(){
		String q="PREFIX : <http://nextprot.org/rdf#>\n" + 
				 "SELECT (count(distinct ?entry)as ?c) WHERE { \n" + 
				 "  ?entry a :Isoform.\n" + 
				 "}";
		
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        assertTrue("countIsoforms (39'651)",rs.next().get("c").asLiteral().getInt()>=39651);


	}	
	
	/**
	 * - count annotation :expression (3'108'020) 
	 *   NX_P61604,NX_Q15029,NX_Q07973
	 */
	@Test
	public void countAnnotationExpression(){
		String q="PREFIX : <http://nextprot.org/rdf#>\n" + 
 				"PREFIX term: <http://nextprot.org/rdf/terminology/>\n" + 
				"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" + 
				"SELECT  (count(distinct ?a)as ?c) WHERE { \n" + 
				"  ?a a :Annotation.\n" + 
				"  [] :expression ?a\n" + 
				"}";	
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        assertTrue("countAnnotationExpression (3'108'020)",rs.next().get("c").asLiteral().getInt()>=3108020);
	}	

	/**
	 * - Brain (TS-0095) relevant For 18202 entries 
	 *   NX_P61604,NX_Q15029,NX_Q07973
	 */
	@Test
	public void countProteinsRelevantForBrain_TS_0095(){
		String q="PREFIX : <http://nextprot.org/rdf#>\n" + 
 				"PREFIX term: <http://nextprot.org/rdf/terminology/>\n" + 
				"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" + 
				"SELECT (count(distinct ?entry)as ?c) WHERE {\n" + 
				"  ?entry :isoform/:annotation/:in/:subPartOf  term:TS-0564.\n" + 
				"}";	
		
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        assertTrue("countProteinsRelevantForBrain_TS_0095 (18202)",rs.next().get("c").asLiteral().getInt()>=18202);
	}	
	
	
	/**
	 * - Liver (TS-0564) relevant For 17848 entries 
	 *   NX_P61604,NX_Q15029,NX_Q07973
	 */
	@Test
	public void countProteinsRelevantForLiver_TS_0564(){
		String q="PREFIX : <http://nextprot.org/rdf#>\n" + 
 				"PREFIX term: <http://nextprot.org/rdf/terminology/>\n" + 
				"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" + 
				"SELECT (count(distinct ?entry)as ?c) WHERE {\n" + 
				"  ?entry :isoform/:annotation/:in/:subPartOf  term:TS-0564.\n" + 
				"}";	

		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        assertTrue("countProteinsRelevantForLiver_TS_0564 (17848)",rs.next().get("c").asLiteral().getInt()>=17848);
	}	
	


	
	/**
	 * proteins :classifiedWith Transport (KW-0813) is relevant for 1859 entries
	 * NX_Q9H0U6, NX_P08195, NX_P46098
	 */
	//@Test
	public void countProteinsRelevantForKW_0813(){
		String q="PREFIX : <http://nextprot.org/rdf#>\n" + 
 				"PREFIX term: <http://nextprot.org/rdf/terminology/>\n" + 
				"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" + 
				"SELECT (count(distinct ?entry)as ?c) WHERE {\n" + 
				"  ?entry :classifiedWith  term:KW-0813.\n" + 
				"}";
		
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        assertTrue("countProteinsRelevantForKW_0813 (1859)",rs.next().get("c").asLiteral().getInt()>=17848);
        
	}	

}