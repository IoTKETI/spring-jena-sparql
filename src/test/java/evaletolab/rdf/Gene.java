package evaletolab.rdf;

import org.junit.Test;

import evaletolab.controller.TripleStoreBaseTest;

/**
 * Use case for features queries
 * Q55 which have genes of length >=10000 bp 
 * 
 * @author dteixeir
 */
public class Gene extends TripleStoreBaseTest{
	
	
	/**
	 * Q55 which have genes of length >=10000 bp
	 * @throws Exception 
	 */
	@Test
	public void Q55_withHaveGenesOfLengthBiggerThan10000BP() {
		testSparql("Q55.sparql");
	}	
}
