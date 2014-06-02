package evaletolab.rdf;

import static org.junit.Assert.assertTrue;

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
import evaletolab.controller.TripleStore;
import evaletolab.tool.FileUtil;
/**
 * Use case for evidences queries
 * - Q27 with >=1 glycosylation sites reported in PubMed:X or PubMed:Y
 * - Q53 which are involved in cell adhesion according to GO with an evidence not IAE and not ISS
 * - Q57 which are located in mitochondrion with an evidence other than HPA and DKFZ-GFP
 * - Q63 which have >=1 RRM RNA-binding domain and either no GO "RNA binding" other a GO "RNA binding" with evidence IEA or ISS
 *  
 * @author evaleto
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
public class Evidences extends TripleStore{

	@Autowired
	private Properties config;
	
	@Before
	public void setup() throws Exception {
		//
		// open session in triplestore
		open();
	}

	/**
	 * Q27 with >=1 glycosylation sites reported in PubMed:X or PubMed:Y 
	 * @throws Exception 
	 */
	@Test
	public void Q27_withGlycosylationSitesReportedInPubmedXOrPubmedY() throws Exception{
		//
		// specific query
		String q=FileUtil.getResourceAsString("sparql/Q27.sparql");

		//
		// execute query
		String acs=getMetaInfo(q).get("acs");
		int count=getQueryMetaCount(q);
		
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        
        //
        // validate result
		List<String> uri=getLiterals(rs);
        assertTrue( rs.getRowNumber()>=count);
        for(String ac:acs.split(","))
        	assertTrue(ac,uri.contains(ac.trim()));
	}	
	
	/**
	 * Q53	which are involved in cell adhesion according to GO with 
	 *      an evidence not IAE and not ISS 
	 *      - Cell adhesion [GO:0007155 ] 
	 * @throws Exception 
	 */
	@Test
	public void Q53_involvedInGO0007155_WithEvidence_NotIEA_And_NotISS() throws Exception{
		//
		// specific query
		String q=FileUtil.getResourceAsString("sparql/Q53-1.sparql");

		//
		// execute query
		String acs=getMetaInfo(q).get("acs");
		int count=getQueryMetaCount(q);
		
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        
        //
        // validate result
		List<String> uri=getLiterals(rs);
        assertTrue( rs.getRowNumber()>=count);
        for(String ac:acs.split(","))
        	assertTrue(ac,uri.contains(ac.trim()));
        
	}	

	/**
	 * Q53	which are involved in cell adhesion according to GO with 
	 *      an evidence not IAE and not ISS 
	 *      - Cell adhesion [GO:0007155 ] 
	 * @throws Exception 
	 */
	@Test
	public void Q53_involvedInGO0007155_WithEvidence_NotIEA_And_NotISS_disJointWith() throws Exception{
		// specific query
		String q=FileUtil.getResourceAsString("sparql/Q53-2.sparql");

		//
		// execute query
		String acs=getMetaInfo(q).get("acs");
		int count=getQueryMetaCount(q);
		
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        
        //
        // validate result
		List<String> uri=getLiterals(rs);
        assertTrue( rs.getRowNumber()>=count);
        for(String ac:acs.split(","))
        	assertTrue(ac,uri.contains(ac.trim()));
	}	
	

	public void Q53_involvedInGO0007155_WithEvidence_NotIEA_And_NotISS_differentFrom() throws Exception{
		// specific query
		String q=FileUtil.getResourceAsString("sparql/Q53-3.sparql");

		//
		// execute query
		String acs=getMetaInfo(q).get("acs");
		int count=getQueryMetaCount(q);
		
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        
        //
        // validate result
		List<String> uri=getLiterals(rs);
        assertTrue( rs.getRowNumber()>=count);
        for(String ac:acs.split(","))
        	assertTrue(ac,uri.contains(ac.trim()));
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
	public void Q57_locatedInMitochondrionWithEvidenceOtherThan_HPA_And_DKFZ_GFP() throws Exception{        
		// specific query
		String q=FileUtil.getResourceAsString("sparql/Q57.sparql");

		//
		// execute query
		String acs=getMetaInfo(q).get("acs");
		int count=getQueryMetaCount(q);
		
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        
        //
        // validate result
		List<String> uri=getLiterals(rs);
        assertTrue( rs.getRowNumber()>=count);
        System.out.println(acs+" = "+uri);
        for(String ac:acs.split(","))
        	assertTrue(ac,uri.contains(ac.trim()));
	
	}	
	@Test
	public void Q57_1_locatedInMitochondrionWithEvidenceOtherThan_HPA_And_DKFZ_GFP() throws Exception{        
		// specific query
		String q=FileUtil.getResourceAsString("sparql/Q57-1.sparql");

		//
		// execute query
		String acs=getMetaInfo(q).get("acs");
		int count=getQueryMetaCount(q);
		
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        
        //
        // validate result
		List<String> uri=getLiterals(rs);
        assertTrue( rs.getRowNumber()>=count);
        System.out.println(acs+" = "+uri);
        for(String ac:acs.split(","))
        	assertTrue(ac,uri.contains(ac.trim()));
	
	}		
	
	/**
	 * Q63 which have >=1 RRM RNA-binding domain (DO-00581 UniprotDomain) and either no GO "RNA binding" (GO:0003723 go molecular function)
	 *     other a GO "RNA binding" with evidence IEA or ISS 
	 * @throws Exception 
	 */
	@Test
	public void Q63_with1RRM_RNAbindingDomainWithEvidenceIEAorISS() throws Exception{
		// specific query
		String q=FileUtil.getResourceAsString("sparql/Q63.sparql");

		//
		// execute query
		String acs=getMetaInfo(q).get("acs");
		int count=getQueryMetaCount(q);
		
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        
        //
        // validate result
		List<String> uri=getLiterals(rs);
        assertTrue( rs.getRowNumber()>=count);
        for(String ac:acs.split(","))
        	assertTrue(ac,uri.contains(ac.trim()));
	}	


}
