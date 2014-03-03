package org.nextprot.rdf.jena.reasoners;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.reasoner.InfGraph;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.ReasonerVocabulary;

public class Capabilities {

	@Before
	public void setup() {
		System.out.println("hello");
        
	}

	@Test
	public void testSubPropertyOf() {

		List<Reasoner> reasoners = new ArrayList<Reasoner>();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		System.out.println("Running " + methodName);
		//reasoners.add(ReasonerRegistry.getTransitiveReasoner()); // fails
		reasoners.add(ReasonerRegistry.getRDFSSimpleReasoner());
		reasoners.add(ReasonerRegistry.getOWLMicroReasoner());
		reasoners.add(ReasonerRegistry.getOWLMiniReasoner());
		reasoners.add(ReasonerRegistry.getOWLReasoner());

		for (Reasoner reasoner: reasoners) {
			String name = reasoner.getClass().getName();
			//String name = reasoner.toString();
			System.out.println("reasoner:"+name);
	        Model schema = FileManager.get().loadModel("test-subPropertyOf-schema.ttl");
	        Model data = FileManager.get().loadModel("test-subPropertyOf-data.ttl");
	        InfModel im = ModelFactory.createInfModel(reasoner, schema, data);
	
	        String q="PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
					 "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
					 "PREFIX owl: <http://www.w3.org/2002/07/owl#> " +
					 "PREFIX : <http://example/ns#> " +
					 "select ?site { :protein1 :site ?site} order by asc(?site)";
	        
	        ResultSet rs = QueryExecutionFactory.create(QueryFactory.create(q),im).execSelect();
	        // we expect these 2 and only 2 solutions:
	        if (!rs.hasNext() || ! rs.next().get("site").toString().equals("http://example/ns#site1")) fail("fails at step 1 with reasoner " + name);
	        if (!rs.hasNext() || ! rs.next().get("site").toString().equals("http://example/ns#site2")) fail("fails at step 2 with reasoner " + name);
	        if (rs.hasNext()) fail("fails at step 3 with reasoner " + name);
		}
	}

}
