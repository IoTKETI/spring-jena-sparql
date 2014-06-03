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
 * - Q108 All proteins that have a 3D structure in PDB that overlap by at least 50 amino acids with a SH3 domain.
 * @author evaleto
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
public class _3Dstructure extends TripleStore{
	
	@Autowired
	private Properties config;
	
	@Before
	public void setup() throws Exception {
		//
		// open session in triplestore
		open();
	}
	
	/**
	 * Q108 All proteins that have a 3D structure in PDB that overlap by at least 50 amino acids with a SH3 domain. 
	 * @throws Exception 
	 */
	@Test
	public void Q108_thatHave3DStructInPDBThatOverlapByAtLeast50AAWithaSH3() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q108.sparql");
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
