package org.nextprot.rdf.jena.reasoners;

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
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;
import com.hp.hpl.jena.vocabulary.ReasonerVocabulary;

import evaletolab.config.WebConfig;

/**
 * Use cases
 *  - union with SPARQL union
 *  - union with SPARQL PREFIX
 *  - union with owl:unionOf
 *  - not in by owl:DifferentFrom
 *  - not in by owl:complementOf
 *  
 * @author evaleto
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
public class Owl {
	
	Model m, schema;
	InfModel owl;
	
	@Before
	public void setup() {
		m=ModelFactory.createDefaultModel().read("owl.ttl");
		schema=ModelFactory.createDefaultModel().read("owl-schema.ttl");
        
        //
        // getTransitiveReasoner, getRDFSReasoner, getRDFSSimpleReasoner, 
        // getOWLReasoner, getOWLMiniReasoner, getOWLMicroReasoner
        Reasoner reasoner = ReasonerRegistry.getOWLMicroReasoner();
        reasoner.setParameter(ReasonerVocabulary.PROPtraceOn, false);
        reasoner.setParameter(ReasonerVocabulary.PROPderivationLogging, false);
        reasoner = reasoner.bindSchema(schema);
        owl = ModelFactory.createInfModel(reasoner, m);
        

	}
	
	/**
	 * NOT IN by using disJointWith and owl:differentFrom 	
	 */
	@Test
	public void notInByDifferentFrom(){
		// query
		String q="PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
				 "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"+
				 "PREFIX owl: <http://www.w3.org/2002/07/owl#> "+
				 "PREFIX : <http://go.org/rdf#> " +
				 "SELECT * WHERE { " +
				 "  ?notInByDifferentFrom owl:differentFrom :A"+
				 "}";	
		Query query = QueryFactory.create(q);
        QueryExecution qe = QueryExecutionFactory.create(query,owl);
        ResultSetFormatter.out(System.out, qe.execSelect(), query);
	}	
	

	/**
	 * NOT IN by using disJointWith and owl:differentFrom 	
	 */
	@Test
	public void notInByDisjointWithA_B(){
		// query
		String q="PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
				 "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"+
				 "PREFIX owl: <http://www.w3.org/2002/07/owl#> "+
				 "PREFIX : <http://go.org/rdf#> " +
				 "SELECT * WHERE { " +
				 "  ?notInByDisjointWithA_B owl:disjointWith :A,:B"+
				 "}";	
		Query query = QueryFactory.create(q);
        QueryExecution qe = QueryExecutionFactory.create(query,owl);
        ResultSetFormatter.out(System.out, qe.execSelect(), query);
	}	

	/**
	 * NOT IN by using disJointWith and owl:complementOf 	
	 */
	@Test
	public void notInByComplementOf(){
		// query
		String q="PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
				 "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"+
				 "PREFIX owl: <http://www.w3.org/2002/07/owl#> "+
				 "PREFIX : <http://go.org/rdf#> " +
				 "SELECT * WHERE { " +
				 "  ?notInByComplementOf owl:complementOf :A" +
				 "}";	
		Query query = QueryFactory.create(q);
        QueryExecution qe = QueryExecutionFactory.create(query,owl);
        ResultSetFormatter.out(System.out, qe.execSelect(), query);
	}	

}
