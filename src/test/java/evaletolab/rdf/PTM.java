package evaletolab.rdf;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import evaletolab.TripleStoreBaseTest;
import evaletolab.rdf.sab.SABTest;

/**
 * Use case for PTM queries
 * 
 * @author evaleto
 *         - Q10 that are glycosylated and not located in the membrane
 *         - Q66 that are cytoplasmic with alternate O-glycosylation or phosphorylation at the same positions
 *         - Q67 with alternative acetylation or Ubl conjugation (SUMO or Ubiquitin) at the same positions
 */
public class PTM extends TripleStoreBaseTest {

	/**
	 * Q10 that are glycosylated and not located in the membrane
	 * 
	 * @throws Exception
	 */
	@Test
	public void Q10_thatAreGlycosylatedAndNotLocatedInTheMembrane() throws Exception {
		testSparql("Q10.sparql");
	}

	/**
	 * Q66 that are cytoplasmic with alternate O-glycosylation or phosphorylation at the same positions
	 * 
	 * @throws Exception
	 */
	@Test
	@Category(SABTest.class)
	public void Q66_thatAreCytoplasmicWithAlternateO_glycoOrPhosphoAtTheSamePosition() throws Exception {
		testSparql("Q66.sparql");
	}

	/**
	 * Q67 with alternative acetylation or Ubl conjugation (SUMO or Ubiquitin) at the same positions
	 * 
	 * @throws Exception
	 */
	@Test
	public void Q67_altAcetylationOrUblConjugationAtSamePos() throws Exception {
		testSparql("Q67.sparql");
	}

	/**
	 * Q97 located on chromosome 2 and having known variants on a phosphotyrosine position
	 * 
	 * @throws Exception
	 */
	@Test
	@Category(SABTest.class)  
	public void Q97_withVariantOnPhosphotyrosyne() {
		testSparql("Q97.sparql");
	}

}
