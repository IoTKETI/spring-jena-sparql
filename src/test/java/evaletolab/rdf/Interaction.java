package evaletolab.rdf;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
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
import evaletolab.tool.FileUtil;

/**
 * Use case for interactions queries
 * Q24 with >1 reported gold interaction
 * Q25 with >=50 interactors and not involved in a disease  
 * Q26 interacting with >=1 protein located in the mitochondrion  
 * @author evaleto
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
public class Interaction extends TripleStore{
	
	@Autowired
	private Properties config;
	
	@Before
	public void setup() throws Exception {
		//
		// open session in triplestore
		open();
	}
	
	/**
	 * Q24 with >1 reported gold interaction 
	 * @throws Exception 
	 */
	@Test
	public void Q24_witnInteractionsReportedGold() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q24.sparql");
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
	 * Q25 with >=50 interactors and not involved in a disease  
	 * @throws Exception 
	 */
	@Test
	public void Q25_with50InteractorsAndNotInvolvedInADisease() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q25.sparql");
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
	 * Q26 interacting with >=1 protein located in the mitochondrion  
	 * @throws Exception 
	 */
	@Test
	public void Q26_withInteractionsLocatedInTheMitochondrion() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q26.sparql");
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
