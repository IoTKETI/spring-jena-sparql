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
 * Use case for Xref queries
 * - Q75 which have been detected in the HUPO liver proteome set but not the HUPO plasma proteome set
 * - Q109 All proteins that have a peptide that maps partly or fully into a signal sequence
 * @author evaleto
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
public class Paptide extends TripleStore{
	
	@Autowired
	private Properties config;
	
	@Before
	public void setup() throws Exception {
		//
		// open session in triplestore
		open();
	}
	
	/**
	 * Q75 which have been detected in the HUPO liver proteome set but not the HUPO plasma proteome set 
	 * @throws Exception 
	 */
	@Test
	public void Q75_whichHaveBeenDetectedInTheHUPOLiverProteomButNotHUPOPlasma() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q75.sparql");
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
	 * Q109 All proteins that have a peptide that maps partly or fully into a signal sequence 
	 * @throws Exception 
	 */
	@Test
	public void Q109_thatHavePeptideThatMapsPartlyOrFullyIntoSignalSeq() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q109.sparql");
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
