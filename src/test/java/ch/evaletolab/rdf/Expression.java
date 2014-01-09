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
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

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
