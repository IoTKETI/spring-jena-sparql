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
 * Use case for evidences queries
 * - Q53	which are involved in cell adhesion according to GO with an evidence not IAE and not ISS
 * - Q57	which are located in mitochondrion with an evidence other than HPA and DKFZ-GFP
 * - Q63	which have >=1 RRM RNA-binding domain and either no GO "RNA binding" other a GO "RNA binding" with evidence IEA or ISS
 * - Q68	with protein evidence PE=2 (transcript level)
 *  
 * @author evaleto
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
public class Evidences {
	String prefix="PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
			 "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"+
			 "PREFIX owl: <http://www.w3.org/2002/07/owl#> "+
			 "PREFIX xsd:<http://www.w3.org/2001/XMLSchema#> "+
			 "PREFIX : <http://nextprot.org/rdf#> \n";
	
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
	 * PREPARE QUERY Q53
	 * 	which are involved in cell adhesion according to GO  
	 *      - Cell adhesion [GO:0007155] 
	 */
	@Test
	public void involvedInGO0007155(){
		// query
		String q=prefix +
				 "PREFIX term: <http://nextprot.org/rdf/terminology/> " +
				 "SELECT ?involvedInGO0007155 WHERE { " +
				 "  ?involvedInGO0007155 :isoform/:function/:in term:GO:0007155"+
				 "}";	
		Query query = QueryFactory.create(q);
        QueryExecution qe = QueryExecutionFactory.create(query,rdfs);
        ResultSet rs=qe.execSelect();
//        ResultSetFormatter.out(System.out, rs, query);
        int rows=0;
        while (rs.hasNext()) {
            rs.next();
            rows++;
        }        
        assertEquals(5, rows);
	}	
	


	
	/**
	 * Q53	which are involved in cell adhesion according to GO with 
	 *      an evidence not IAE and not ISS 
	 *      - Cell adhesion [GO:0007155 ] 
	 */
	@Test
	public void involvedInGO0007155_WithEvidence_NotIEA_And_NotISS(){
		// query
		String q=prefix +
				 "PREFIX term: <http://nextprot.org/rdf/terminology/> " +
				 "SELECT distinct ?entry WHERE { " +
				 "  ?entry  :isoform/:function ?statement." +
				 "  ?statement :in term:GO:0007155."+
				 "  FILTER NOT EXISTS { " +
				 "    {?statement :evidence/rdf:type :IEA }UNION{?statement :evidence/rdf:type :ISS }"+
				 "  } "+
				 "}";	
		Query query = QueryFactory.create(q);
        QueryExecution qe = QueryExecutionFactory.create(query,rdfs);
        ResultSet rs=qe.execSelect();
        while (rs.hasNext()) {
            QuerySolution row= rs.next();
            assertEquals("http://nextprot.org/rdf/entry/NX_Q53_2", row.get("entry").toString());
        }
        
	}	

	/**
	 * Q53	which are involved in cell adhesion according to GO with 
	 *      an evidence not IAE and not ISS 
	 *      - Cell adhesion [GO:0007155 ] 
	 */
	@Test
	public void involvedInGO0007155_WithEvidence_NotIEA_And_NotISS_disJointWith(){
		// query
		String q=prefix +
				 "PREFIX term: <http://nextprot.org/rdf/terminology/> " +
				 "SELECT distinct ?entry WHERE { " +
				 "  ?entry  :isoform/:function ?statement." +
				 "  ?statement :in term:GO:0007155."+
				 "  ?statement :evidence/rdf:type/owl:disjointWith :IEA,:ISS "+
				 "}";	
		Query query = QueryFactory.create(q);
        QueryExecution qe = QueryExecutionFactory.create(query,rdfs);
        ResultSet rs=qe.execSelect();
        while (rs.hasNext()) {
            QuerySolution row= rs.next();
            assertEquals("http://nextprot.org/rdf/entry/NX_Q53_2", row.get("entry").toString());
        }
	}	
	

	@Test
	public void involvedInGO0007155_WithEvidence_NotIEA_And_NotISS_differentFrom(){
		// query
		String q=prefix +
				 "PREFIX term: <http://nextprot.org/rdf/terminology/> " +
				 "SELECT distinct ?entry WHERE { " +
				 "  ?entry  :isoform/:function ?statement." +
				 "  ?statement :in term:GO:0007155."+
				 "  ?statement :evidence/rdf:type/owl:differentFrom :IEA,:ISS "+
				 "}";	
		Query query = QueryFactory.create(q);
        QueryExecution qe = QueryExecutionFactory.create(query,rdfs);
        ResultSet rs=qe.execSelect();
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
	 */
	@Test
	public void locatedInMitochondrionWithEvidenceOtherThan_HPA_And_DKFZ_GFP(){
		// expected
        String[] expected={"http://nextprot.org/rdf/entry/NX_Q57_1","http://nextprot.org/rdf/entry/NX_Q57_2"};
        
		// query
		String q=prefix +
				 "PREFIX term: <http://nextprot.org/rdf/terminology/> " +
				 "SELECT distinct ?entry WHERE { " +
				 "  ?entry" +
				 "     :isoform/:localisation ?statement." +
				 "     ?statement :in/owl:sameAs* term:SL-0173."+
				 "  FILTER NOT EXISTS { " +
				 "    {?statement :evidence/:assignedBy 'HPA'}UNION{?statement :evidence/:assignedBy 'DKFZ-GFP'}"+
				 "  } "+
				 "}";	
		
		Query query = QueryFactory.create(q);
		QueryExecution qe = QueryExecutionFactory.create(query,rdfs);

        ResultSet rs=qe.execSelect();
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
	 */
	@Test
	public void with1RRM_RNAbindingDomainWithEvidenceIEAorISS(){
		// ?entry :isoform/:function ?statement
		// {?statement :in DO-00581} UNION 
		// {?statement :in term:GO:0003723;:evidence/rdf:type :IEA,:ISS}UNION
		// {NOT EXISTS{?statement :in term:GO:0003723 }}
		// 

	}	

	/**
	 * Q68 with protein evidence PE=2 (transcript level)
	 */
	@Test
	public void withProteinEvidencePE2(){

	}	
}
