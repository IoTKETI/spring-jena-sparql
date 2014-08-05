package evaletolab.rdf;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import evaletolab.TripleStoreBaseTest;
import evaletolab.rdf.sab.SABTest;

/**
 * Use case for gene queries
 * Q55 which have genes of length >=10000 bp
 * Q58 which are located on the genome next to a protein_which is involved in spermatogenesis righ
 * 
 * @author dteixeir
 */
public class Gene extends TripleStoreBaseTest {

	@Test
	@Category(SABTest.class)  
	public void Q55_wich_have_genes_of_length_bigger_than_10000BP() {
		testSparql("Q055.sparql");
	}

	@Test
	public void Q58_which_are_located_on_the_genome_next_to_a_protein_which_is_involved_in_spermatogenesis_right() {
		testSparql("Q058-right.sparql");
	}
	
	
	@Test
	public void Q58_which_are_located_on_the_genome_next_to_a_protein_which_is_involved_in_spermatogenesis_left() {
		//Don't why the performance of this query is much slower than on the right side!!!!
		testSparql("Q058-left.sparql");
	}
	
	@Test
	public void Q58_which_are_located_on_the_genome_next_to_a_protein_which_is_involved_in_spermatogenesis_left_b() {
		//This one fixes the performance issue but does not return the same number of rows
		testSparql("Q058-left-b.sparql");
	}

	@Test
	@Category(SABTest.class)  
	public void Q58_which_are_located_on_the_genome_next_to_a_protein_which_is_involved_in_spermatogenesis() {
		testSparql("Q058.sparql");
	}

}
