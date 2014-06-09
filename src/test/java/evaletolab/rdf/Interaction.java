package evaletolab.rdf;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import evaletolab.TripleStoreBaseTest;
import evaletolab.rdf.sab.SABTest;

/**
 * Use case for interactions queries
 * Q24 with >1 reported gold interaction
 * Q25 with >=50 interactors and not involved in a disease  
 * Q26 interacting with >=1 protein located in the mitochondrion  
 * @author evaleto
 *
 */
public class Interaction extends TripleStoreBaseTest{
	/**
	 * Q24 with >1 reported gold interaction 
	 * @throws Exception 
	 */
	@Test
	public void Q24_witnInteractionsReportedGold() throws Exception{
		testSparql("Q24.sparql");
	}			
	
	/**
	 * Q25 with >=50 interactors and not involved in a disease  
	 * @throws Exception 
	 */
	@Test
	@Category(SABTest.class)  
	public void Q25_with50InteractorsAndNotInvolvedInADisease(){
		testSparql("Q25.sparql");
	}	
	
	/**
	 * Q26 interacting with >=1 protein located in the mitochondrion  
	 * @throws Exception 
	 */
	@Test
	public void Q26_withInteractionsLocatedInTheMitochondrion(){
		testSparql("Q26.sparql");
	}	
		
}
