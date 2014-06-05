package evaletolab.rdf;

import static org.junit.Assert.assertTrue;

import java.util.List;
import org.junit.Test;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.ResultSet;

import evaletolab.controller.TripleStoreBaseTest;
import evaletolab.tool.FileUtil;

/**
 * Use case for Xref queries
 * - Q72 with a cross-reference to CCDS
 * @author evaleto
 *
 */

public class Xref extends TripleStoreBaseTest {
	
	
	/**
	 * Q72 with a cross-reference to CCDS 
	 * @throws Exception 
	 */
	@Test
	public void Q72_withXrefToCCDS() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q72.sparql");
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
	 * Q107 All proteins with a protein evidence not "At protein level" with a HGNC identifier/xref that includes the regexp "orf"
	 * @throws Exception 
	 */
	@Test
	public void Q107_PE1_HGNCref_containingORF() {
		testSparql("Q107.sparql");
	}	

	
		
}
