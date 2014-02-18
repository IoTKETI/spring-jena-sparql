package evaletolab.rdf;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;

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
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.reasoner.ValidityReport;
import com.hp.hpl.jena.reasoner.ValidityReport.Report;

import evaletolab.config.WebConfig;

/**
 * schema validation *  
 * @author evaleto
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
public class Schema {
	
	Model m;
	InfModel rdfs;
	
	@Before
	public void setup() {

	}	

	private void report(ValidityReport validity){
	    if (validity.isValid()) {
	        System.out.println("OK");
	    } else {
	        System.out.println("Conflicts");
	        for (Iterator<Report> i = validity.getReports(); i.hasNext(); ) {
	            System.out.println(" - " + i.next());
	        }
	    }			    		
	    assertEquals(true, validity.isValid());
	}
	
	@Test
	public void validateOWL(){
		m=ModelFactory.createDefaultModel();
		m.read("owl/np.ttl");
		rdfs= ModelFactory.createRDFSModel(m);
		report(rdfs.validate());		
	
	}	
	
	@Test
	public void validateTerminology(){
		m=ModelFactory.createDefaultModel();
		m.read("terminology-disease.ttl").read("owl/np.ttl");
		rdfs= ModelFactory.createRDFSModel(m);
		report(rdfs.validate());		
	}	

	@Test
	public void validatePublication(){
		m=ModelFactory.createDefaultModel();
		m.read("publication.ttl").read("owl/np.ttl");
		rdfs= ModelFactory.createRDFSModel(m);
		report(rdfs.validate());		
	}	
	
	@Test
	public void validateEvidence(){
		m=ModelFactory.createDefaultModel();
		m.read("evidence.ttl").read("owl/np.ttl");
		rdfs= ModelFactory.createRDFSModel(m);
		report(rdfs.validate());		
	
	}	
}
