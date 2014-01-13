package ch.evaletolab.rdf;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import ch.evaletolab.config.WebConfig;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

/**
 * Use case for expression queries
 * - Simple join
 * -  Proteins that are not highly expressed in liver at embrion stage
 * -  Proteins that are expressed in liver and involved in transport
 * -  Proteins that are expressed   in liver and involved in transport 
 * -  Proteins >=1000 amino acids and located in nucleus and expression in nervous system
 * -  Proteins highly expressed at IHC level in heart
 * -  Proteins highly expressed in* brain but not expressed in* testis
 * -  Proteins which are expressed in brain according to IHC but not expressed in brain according to microarray
 * -  Simple join with aggregate group field 
 * -  Proteins whose genes are on chromosome N that are expressed only a single tissue/organ
 * -  Proteins which are expressed in liver according to IHC data but not found in HUPO liver proteome set
 * 
 * - 3 recursive/deeph path
 * -  Proteins with a PDZ domain that interact with at least 1 protein which is expressed in brain
 * 
 * @author evaleto
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
public class Expression {
	
	Model m;
	InfModel rdfs;
	
	@Before
	public void setup() {
		m=ModelFactory.createDefaultModel();
		m.read("expression-heavy2.ttl").read("owl/np.ttl");
		rdfs= ModelFactory.createRDFSModel(m);
		
		
		//
		// load query
		String q="PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
				 "PREFIX : <http://np.org/np#> " +
				 "SELECT * WHERE { " +
				 "  ?tissue a :Tissue . " +
				 "}";		
		long start=System.currentTimeMillis();
		Query query = QueryFactory.create(q);
        QueryExecution qe = QueryExecutionFactory.create(query,rdfs);
        ResultSet rs = qe.execSelect();
        System.out.println("isoforms: "+(System.currentTimeMillis()-start)+" ms");
        
        ResultSetFormatter.out(System.out, rs, query);			
	}
	
	@Test
	//https://code.google.com/p/project-knowledgetv/source/browse/trunk/knowledge-tv/knowledge-tv-sqtv-lib/src/test/java/br/ufpb/di/knowledgetv/sqtv/tests/JenaTest.java?r=24
	public void listIsoforms(){
		String q="PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
				 "PREFIX : <http://np.org/np#> " +
				 "SELECT * WHERE { " +
				 "  ?isoform a :Isoform . " +
				 "}";
		
		
		
		long start=System.currentTimeMillis();
		Query query = QueryFactory.create(q);
        QueryExecution qe = QueryExecutionFactory.create(query,rdfs);
        ResultSet rs = qe.execSelect();
        System.out.println("isoforms: "+(System.currentTimeMillis()-start)+" ms");
        
        ResultSetFormatter.out(System.out, rs, query);	
//        while(rs.hasNext()){
//        	 QuerySolution row= rs.next();
//             String value= row.getLiteral("name").toString();        }
	}
	
	/**
	 * Proteins that are notHighlyExpressed   in liver withExperimentDesciption at embrion stage
	 */
	@Test
	public void notHighlyExpressed(){
		String q="PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
				 "PREFIX : <http://np.org/np#> " +
				 "PREFIX tissue: <http://np.org/np/terminology/> "+
				 "SELECT * WHERE { " +
				 "  ?isoform :notHighlyExpressed/:in tissue:Bile%20duct. " +
				 "}";
			
		Query query = QueryFactory.create(q);
		long start=System.currentTimeMillis();
        QueryExecution qe = QueryExecutionFactory.create(query,rdfs);
        ResultSet rs = qe.execSelect();
        System.out.println("notHiglyExpressed "+(System.currentTimeMillis()-start)+" ms");
        ResultSetFormatter.out(System.out, rs, query);	
	}	
	
	@Test
	public void expressed(){
		String q="PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
				 "PREFIX : <http://np.org/np#> " +
				 "PREFIX tissue: <http://np.org/np/terminology/> "+
				 "SELECT * WHERE { " +
				 "  ?isoform :expressed/:in tissue:Renal%20tubule. " +
				 "}";
		
		Query query = QueryFactory.create(q);
		long start=System.currentTimeMillis();
        QueryExecution qe = QueryExecutionFactory.create(query,rdfs);
        ResultSet rs = qe.execSelect();
        
        System.out.println("expressed: "+(System.currentTimeMillis()-start)+" ms");
        ResultSetFormatter.out(System.out, rs, query);	
	}		
}