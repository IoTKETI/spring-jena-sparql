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
import evaletolab.tool.FileUtil;
import static org.junit.Assert.*;
/**
 * Use case for evidences queries
 * - Q53	which are involved in cell adhesion according to GO with an evidence not IAE and not ISS
 * - Q57	which are located in mitochondrion with an evidence other than HPA and DKFZ-GFP
 * - Q63	which have >=1 RRM RNA-binding domain and either no GO "RNA binding" other a GO "RNA binding" with evidence IEA or ISS
 *  
 * @author evaleto
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
public class Evidences {
	
	Model m, schema;
	InfModel rdfs;
	
	@Before
	public void setup() {
		m=ModelFactory.createDefaultModel();
		schema=ModelFactory.createDefaultModel();
		m.read("evidence-Q53.ttl")
		 .read("evidence-Q57.ttl")
		 .read("terminology-disease.ttl")
		 .read("publication.ttl");
        schema = schema.read("owl/np.ttl");

        //
        // get the micro owl reasoner 
        Reasoner reasoner = ReasonerRegistry.getOWLMicroReasoner();
        reasoner = reasoner.bindSchema(schema);
        rdfs = ModelFactory.createInfModel(reasoner, m);
        
	}


	
	/**
	 * Q53	which are involved in cell adhesion according to GO with 
	 *      an evidence not IAE and not ISS 
	 *      - Cell adhesion [GO:0007155 ] 
	 * @throws Exception 
	 */
	@Test
	public void involvedInGO0007155_WithEvidence_NotIEA_And_NotISS() throws Exception{
		//
		// specific query
		String q=FileUtil.getResourceAsString("sparql/Q53-1-involvedInGO0007155_WithEvidence_NotIEA_And_NotISS.sparql");

		//
		// execute query
		Query query = QueryFactory.create(q);
        QueryExecution qe = QueryExecutionFactory.create(query,rdfs);
        ResultSet rs = qe.execSelect();

        //
        // validate result
		while (rs.hasNext()) {
            QuerySolution row= rs.next();
            assertEquals("http://nextprot.org/rdf/entry/NX_Q53_2", row.get("entry").toString());
        }
        
	}	

	/**
	 * Q53	which are involved in cell adhesion according to GO with 
	 *      an evidence not IAE and not ISS 
	 *      - Cell adhesion [GO:0007155 ] 
	 * @throws Exception 
	 */
	@Test
	public void involvedInGO0007155_WithEvidence_NotIEA_And_NotISS_disJointWith() throws Exception{
		// specific query
		String q=FileUtil.getResourceAsString("sparql/Q53-2-involvedInGO0007155_WithEvidence_NotIEA_And_NotISS.sparql");

		//
		// execute query
		Query query = QueryFactory.create(q);
        QueryExecution qe = QueryExecutionFactory.create(query,rdfs);
        ResultSet rs = qe.execSelect();

        //
        // validate result
        while (rs.hasNext()) {
            QuerySolution row= rs.next();
            assertEquals("http://nextprot.org/rdf/entry/NX_Q53_2", row.get("entry").toString());
        }
	}	
	

	@Test
	public void involvedInGO0007155_WithEvidence_NotIEA_And_NotISS_differentFrom() throws Exception{
		// specific query
		String q=FileUtil.getResourceAsString("sparql/Q53-1-involvedInGO0007155_WithEvidence_NotIEA_And_NotISS.sparql");

		//
		// execute query
		Query query = QueryFactory.create(q);
        QueryExecution qe = QueryExecutionFactory.create(query,rdfs);
        ResultSet rs = qe.execSelect();

        //
        // validate result
        while (rs.hasNext()) {
            QuerySolution row= rs.next();
            assertEquals("http://nextprot.org/rdf/entry/NX_Q53_2", row.get("entry").toString());
        }        
	}		

	/**
	 * Q57	which are located in mitochondrion with an evidence other 
	 *      than HPA and DKFZ-GFP
	 *      WARNING! term:SL-0173 is (this must be inferred) 
	 *        rdfs:sameAs term:GO:0005739;
  	 *		  rdfs:sameAs term:KW-0496; 
	 * @throws Exception 
	 */
	@Test
	public void locatedInMitochondrionWithEvidenceOtherThan_HPA_And_DKFZ_GFP() throws Exception{
		// expected
        String[] expected={"http://nextprot.org/rdf/entry/NX_Q57_1","http://nextprot.org/rdf/entry/NX_Q57_2"};
        
		// specific query
		String q=FileUtil.getResourceAsString("sparql/Q57-locatedInMitochondrionWithEvidenceOtherThan_HPA_And_DKFZ_GFP.sparql");

		//
		// execute query
		Query query = QueryFactory.create(q);
        QueryExecution qe = QueryExecutionFactory.create(query,rdfs);
        ResultSet rs = qe.execSelect();

        //
        // validate result
        List<String> rows=new ArrayList<String>();
        while (rs.hasNext()) {
            QuerySolution row= rs.next();
            rows.add(row.get("entry").toString());
        }
        assertArrayEquals(expected,rows.toArray());
	
	}	
	
	/**
	 * Q63 which have >=1 RRM RNA-binding domain (DO-00581 UniprotDomain) and either no GO "RNA binding" (GO:0003723 go molecular function)
	 *     other a GO "RNA binding" with evidence IEA or ISS 
	 * @throws Exception 
	 */
	@Test
	public void with1RRM_RNAbindingDomainWithEvidenceIEAorISS() throws Exception{
		// specific query
		String q=FileUtil.getResourceAsString("sparql/Q63-with1RRM_RNAbindingDomainWithEvidenceIEAorISS.sparql");

		//
		// execute query
		Query query = QueryFactory.create(q);
        QueryExecution qe = QueryExecutionFactory.create(query,rdfs);
        ResultSet rs = qe.execSelect();
	}	


}
