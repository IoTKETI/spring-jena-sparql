package evaletolab.rdf.stats;

import org.junit.Test;

import evaletolab.TripleStoreBaseTest;

public class Stats extends TripleStoreBaseTest {

	@Test
	public void number_of_total_triplets() {
		runSparql("stats/number_of_triplets.sparql");
	}
}
