package evaletolab.rdf;

import org.junit.Test;

import evaletolab.TripleStoreBaseTest;

/**
 * Use case for slow queries
 * 
 * @author pam
 */
public class Slow extends TripleStoreBaseTest{
	
	
	/**
	 * Q4-faster high in brain not in testis
	 * @throws Exception 
	 */
	//@Test
	public void Q4faster_HighInBrain_NotInTestis() {
		testSparql("Q4-faster.sparql");
	}	
}
