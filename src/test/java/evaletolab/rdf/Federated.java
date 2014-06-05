package evaletolab.rdf;

import org.junit.Test;

import evaletolab.TripleStoreBaseTest;

/**
 * Use case for federated queries
 * Q95 which are targets of antibiotics (federated query with drugbank)
 * 
 * @author pam
 */
public class Federated extends TripleStoreBaseTest{
	
	
	/**
	 * Q55 which have genes of length >=10000 bp
	 * @throws Exception 
	 */
	@Test
	public void Q95_targetsForANtibiotics() {
		testSparql("Q95.sparql");
	}	
}
