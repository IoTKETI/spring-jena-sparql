package evaletolab.rdf;

import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.ResultSet;


import evaletolab.config.WebConfig;
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
public class Integrity extends TripleStore{

	@Autowired
	private Properties config;
	
	@Before
	public void setup() throws Exception {
		//
		// open session in triplestore
		open();
	}
	

	
	/**
	 * nb terminology classes
	 */
	
	@Test
	public void countTerminologyClass(){
		String q="SELECT distinct ?class WHERE { ?class rdfs:subClassOf :Term }ORDER BY ?class";		
		
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        
        //
        // validate result
		List<String> uri=getLiterals(rs,"class","");
        assertThat("countTerminologyClass (36)",37.0,closeTo(rs.next().get("c").asLiteral().getInt(),1));
        
	}	
	
	@Test
	public void countNotUsedTerminologyClass(){
		String q="SELECT distinct ?class\n" + 
				"WHERE { ?class rdfs:subClassOf :Term " +
				"        FILTER NOT EXISTS {[] a ?class}}ORDER BY ?class";		
		
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        
        //
        // validate result
		List<String> uri=getLiterals(rs,"class","");
        assertEquals("countNotUsedTerminologyClass (7)",8,rs.getRowNumber());
	}		
	@Test
	public void countAnnotationClass(){
		String q="SELECT distinct ?class\n" + 
				"WHERE {?class rdfs:subClassOf* :Annotation }ORDER BY ?class";		
		
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        
        //
        // validate result
		List<String> uri=getLiterals(rs,"class","");
        assertEquals("countAnnotationClass (76)",rs.getRowNumber(),76);
	}		
	/**
	 * classes intersection between Term and Annotation
	 */
	
	@Test
	public void countIntersectBetweenAnnotationAndTerminologyClass(){
		
		String q="SELECT distinct ?class WHERE{\n" + 
				"   ?class rdfs:subClassOf* :Term.\n" + 
				"   ?class rdfs:subClassOf* :Annotation.\n" + 
				"}ORDER BY ?class";
		
		
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        //
        // validate result
		List<String> uri=getLiterals(rs,"class","");
        
        assertEquals("countIntersectBetweenAnnotationAndTerminologyClass (0)",0,rs.getRowNumber());
	}	
	
	
	/**
	 * nb entries (20'130)
	 * sql: select count(distinct(si.unique_name)) from nextprot.sequence_identifiers si 
	 *      where si.cv_type_id=2 AND si.cv_status_id=1
	 */
	
	@Test
	public void countEntries(){
		String q="SELECT (count(distinct ?entry)as ?c) WHERE { \n" + 
				 "  ?entry a :Entry.\n" + 
				 "}";
		
		
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        
        assertThat("countEntries (20'130)",20130.0,closeTo(rs.next().get("c").asLiteral().getInt(),5));
	}	
	
	/**
	 * nb genes (22'715)
	 * sql: select count(distinct(si.unique_name)) from nextprot.sequence_identifiers si 
	 *      where si.cv_type_id=3 AND si.cv_status_id=1
	 */
	@Test
	public void countGenes(){
		String q="SELECT (count(distinct ?entry)as ?c) WHERE { \n" + 
				 "  ?entry a :Gene.\n" + 
				 "}";
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        assertThat("countGenes (22'715)",22725.0,closeTo(rs.next().get("c").asLiteral().getInt(),10.0));
        

	}
	
	/**
	 * nb antibody (21'726)
	 * sql: select count(distinct(si.unique_name)) from nextprot.sequence_identifiers si 
	 *      where si.cv_type_id=6 AND si.cv_status_id=1
	 */
	@Test
	public void countAntobodies(){
		String q="SELECT (count(distinct ?entry)as ?c) WHERE { \n" + 
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
		String q="SELECT (count(distinct ?entry)as ?c) WHERE { \n" + 
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
		String q="SELECT (count(distinct ?entry)as ?c) WHERE { \n" + 
				 "  ?entry a :Isoform.\n" + 
				 "}";
		
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        assertThat("countIsoforms (39'651)",39661.0,closeTo(rs.next().get("c").asLiteral().getInt(),10.0));


	}	
	
	/**
	 * - count annotation :expression (3'108'020) 
	 *   NX_P61604,NX_Q15029,NX_Q07973
	 */
	@Test
	public void countAnnotationExpression(){
		String q= 
				"SELECT  (count(distinct ?a)as ?c) WHERE { \n" + 
				"  ?a a :Annotation.\n" + 
				"  [] :expression ?a\n" + 
				"}";	
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        assertThat("countAnnotationExpression (3'108'020)",3108020,greaterThanOrEqualTo(rs.next().get("c").asLiteral().getInt()));
	}	



}
