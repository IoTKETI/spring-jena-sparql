package evaletolab.rdf;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import evaletolab.TripleStoreBaseTest;
import evaletolab.rdf.sab.SABTest;

/**
 * Use case for Xref queries
 * - Q72 with a cross-reference to CCDS
 * - Q107 All proteins with a protein evidence not "At protein level" with a HGNC identifier/xref that includes the regexp "orf"
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
		testSparql("Q72.sparql");
	}			
	

	/**
	 * Q107 All proteins with a protein evidence not "At protein level" with a HGNC identifier/xref that includes the regexp "orf"
	 * @throws Exception 
	 */
	@Test
	@Category(SABTest.class)
	public void Q107_PE1_HGNCref_containingORF() {
		testSparql("Q107.sparql");
	}	

	
		
}
