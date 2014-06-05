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

import evaletolab.TripleStoreBaseTest;
import evaletolab.config.WebConfig;
import evaletolab.controller.TripleStore;
import evaletolab.tool.FileUtil;

/**
 * Use case for PTM queries

 * @author evaleto
 * - Q10 that are glycosylated and not located in the membrane
 * - Q66 that are cytoplasmic with alternate O-glycosylation or phosphorylation at the same positions
 * - Q67 with alternative acetylation or Ubl conjugation (SUMO or Ubiquitin) at the same positions
 */
public class PTM extends TripleStoreBaseTest{
	
	/**
	 * Q10 that are glycosylated and not located in the membrane 
	 * @throws Exception 
	 */
	@Test
	public void Q10_thatAreGlycosylatedAndNotLocatedInTheMembrane() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q10.sparql");
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
	 * Q66 that are cytoplasmic with alternate O-glycosylation or phosphorylation at the same positions 
	 * @throws Exception 
	 */
	@Test
	public void Q66_thatAreCytoplasmicWithAlternateO_glycoOrPhosphoAtTheSamePosition() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q66.sparql");
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
	 * Q67 with alternative acetylation or Ubl conjugation (SUMO or Ubiquitin) at the same positions 
	 * @throws Exception 
	 */
	@Test
	public void Q67_altAcetylationOrUblConjugationAtSamePos() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q67.sparql");
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
	 * Q97 located on chromosome 2 and having known variants on a phosphotyrosine position
	 * @throws Exception 
	 */
	@Test
	public void Q97_withVariantOnPhosphotyrosyne() {
		testSparql("Q97.sparql");
	}	


}

