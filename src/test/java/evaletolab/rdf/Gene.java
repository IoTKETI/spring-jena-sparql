package evaletolab.rdf;

import org.junit.Test;

import evaletolab.controller.TripleStoreBaseTest;

/**
 * Use case for gene queries
 * Q55 which have genes of length >=10000 bp
 * 
 * @author dteixeir
 */
public class Gene extends TripleStoreBaseTest {

	@Test
	public void Q55_wich_have_genes_of_length_bigger_than_10000BP() {
		testSparql("Q055.sparql");
	}

	@Test
	public void Q58_which_are_located_on_the_genome_next_to_a_protein_which_is_involved_in_spermatogenesis_right() {
		testSparql("Q058-right.sparql");
	}
	
	
	@Test
	public void Q58_which_are_located_on_the_genome_next_to_a_protein_which_is_involved_in_spermatogenesis_left() {
		testSparql("Q058-left.sparql");
	}

	@Test
	public void Q58_which_are_located_on_the_genome_next_to_a_protein_which_is_involved_in_spermatogenesis() {
		testSparql("Q058.sparql");
	}

}
