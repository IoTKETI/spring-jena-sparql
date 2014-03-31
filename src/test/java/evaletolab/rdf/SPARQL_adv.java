package evaletolab.rdf;

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
public class SPARQL_adv {
	
	Model m, schema;
	InfModel rdfs;
	
	@Before
	public void setup() {
		m=ModelFactory.createDefaultModel().read("evidence-Q53.ttl").read("publication.ttl");
		schema=ModelFactory.createDefaultModel().read("owl/np.ttl");;
        
        //
        // getTransitiveReasoner, getRDFSReasoner, getRDFSSimpleReasoner, 
        // getOWLReasoner, getOWLMiniReasoner, getOWLMicroReasoner
        Reasoner reasoner = ReasonerRegistry.getOWLMicroReasoner();
        reasoner.setParameter(ReasonerVocabulary.PROPtraceOn, false);
        reasoner.setParameter(ReasonerVocabulary.PROPderivationLogging, false);
        reasoner = reasoner.bindSchema(schema);
        rdfs = ModelFactory.createInfModel(reasoner, m);
	}

	/**
	 *  UNION	
	 * 	with an evidence IAE or ISS   
	 */
	@Test
	public void union_sparql_union(){
		// query
		String q="PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
				 "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"+
				 "PREFIX owl: <http://www.w3.org/2002/07/owl#> "+
				 "PREFIX : <http://nextprot.org/rdf#> " +
				 "PREFIX term: <http://nextprot.org/rdf/terminology/> " +
				 "PREFIX  list: <http://jena.hpl.hp.com/ARQ/list#> "+
				 "SELECT * WHERE { " +
				 "  {?union_sparql_union :isoform/:function/:evidence/rdf:type :IEA}"+
				 "UNION "+
				 "  {?union_sparql_union :isoform/:function/:evidence/rdf:type :ISS}"+
				 "}";	
		
		Query query = QueryFactory.create(q);
        QueryExecution qe = QueryExecutionFactory.create(query,rdfs);
                
        
        ResultSetFormatter.out(System.out, qe.execSelect(), query);
	}		

	
	


	/**
	 * NOT IN by owl:disJointWith 	
	 */
	@Test
	public void notInByDisJointWith(){
		// query
		String q="PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
				 "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"+
				 "PREFIX owl: <http://www.w3.org/2002/07/owl#> "+
				 "PREFIX : <http://nextprot.org/rdf#> " +
				 "PREFIX term: <http://nextprot.org/rdf/terminology/> " +
				 "SELECT * WHERE { " +
				 "  ?notInByDisJointWith :evidence/rdf:type ?eco." +
				 "  ?eco owl:disjointWith :IEA"+
				 "}";	
		Query query = QueryFactory.create(q);
        QueryExecution qe = QueryExecutionFactory.create(query,rdfs);
        ResultSetFormatter.out(System.out, qe.execSelect(), query);
	}		

	/**
	 * NOT IN by owl:differentFrom 	
	 */
	//@Test
	public void notInByDifferentFrom(){
		// query
		String q="PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
				 "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"+
				 "PREFIX owl: <http://www.w3.org/2002/07/owl#> "+
				 "PREFIX : <http://nextprot.org/rdf#> " +
				 "PREFIX term: <http://nextprot.org/rdf/terminology/> " +
				 "SELECT * WHERE { " +
				 "  ?notInByDifferentFrom :evidence/rdf:type ?eco." +
				 "  ?eco owl:differentFrom :IEA"+
				 "}";	
		Query query = QueryFactory.create(q);
        QueryExecution qe = QueryExecutionFactory.create(query,rdfs);
        ResultSetFormatter.out(System.out, qe.execSelect(), query);
	}	

	/**
	 * NOT IN by owl:complementOf 	
	 */
	@Test
	public void notInByComplementOf(){
		// query
		String q="PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
				 "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"+
				 "PREFIX owl: <http://www.w3.org/2002/07/owl#> "+
				 "PREFIX : <http://nextprot.org/rdf#> " +
				 "PREFIX term: <http://nextprot.org/rdf/terminology/> " +
				 "SELECT * WHERE { " +
				 "  ?notInByComplementOf :evidence/rdfs:type ?eco." +
				 "  ?eco owl:complementOf :IAE"+
				 "}";	
		Query query = QueryFactory.create(q);
        QueryExecution qe = QueryExecutionFactory.create(query,rdfs);
        ResultSetFormatter.out(System.out, qe.execSelect(), query);
	}	

}
