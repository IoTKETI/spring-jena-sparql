package evaletolab.rdf;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import evaletolab.TripleStoreBaseTest;
import evaletolab.rdf.sab.SABTest;

/**
 * Use case for Xref queries
 * - Q75 which have been detected in the HUPO liver proteome set but not the HUPO plasma proteome set
 * - Q109 All proteins that have a peptide that maps partly or fully into a signal sequence
 * @author evaleto
 *
 */

public class Peptide extends TripleStoreBaseTest{
	/**
	 * Q75 which have been detected in the HUPO liver proteome set but not the HUPO plasma proteome set 
	 * @throws Exception 
	 */
	@Test
	public void Q75_whichHaveBeenDetectedInTheHUPOLiverProteomButNotHUPOPlasma() throws Exception{
		testSparql("Q75.sparql");
	}			
	
	/**
	 * Q109 All proteins that have a peptide that maps partly or fully into a signal sequence 
	 * @throws Exception 
	 */
	@Test
	@Category(SABTest.class)  
	public void Q109_thatHavePeptideThatMapsPartlyOrFullyIntoSignalSeq() throws Exception{
		testSparql("Q109.sparql");
	}	
		
}
