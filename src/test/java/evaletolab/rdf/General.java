package evaletolab.rdf;

import org.junit.Test;

import evaletolab.TripleStoreBaseTest;

/**
 * Use case for features queries
 * Q1 that are phosphorylated and located in the cytoplasm
 * Q2 that are located both in the cytoplasm and in the nucleus
 * Q5 located in mitochondrion and that lack a transit peptide
 * Q6 whose genes are on chromosome 2 and linked with a disease
 * Q7 linked to diseases that are associated with cardiovascular aspects
 * Q8 whose genes are x bp away from the location of the gene of protein Y
 * Q22 Proteins with no function annotated
 * Q31 with >=10 "splice" isoforms
 * Q30 Proteins whose gene is located in chromosome 2 that belongs to families with >=5 members in the human proteome
 * Q32 with a coiled coil region and involved in transcription but does not contain a bZIP domain
 * Q47 with a gene name CLDN*
 * Q64 which are enzymes with an incomplete EC number
 * Q65 Proteins with >1 catalytic activity
 * Q68 with protein evidence PE=2 (transcript level)
 * Q73 Proteins with no domain
 * 
 * @author evaleto
 */
public class General extends TripleStoreBaseTest {

	@Test
	public void Q1_that_are_phosphorylated_and_located_in_the_cytoplasm() {
		testSparql("Q1.sparql");
	}

	@Test
	public void Q2_that_are_located_both_in_the_cytoplasm_and_in_the_nucleus() {
		testSparql("Q2.sparql");
	}

	@Test
	public void Q5_that_are_located_in_mitochondrion_and_lack_a_transit_peptide() {
		testSparql("Q5.sparql");
	}

	@Test
	public void Q6_whose_genes_are_on_chromosome_2_and_linked_with_a_disease() {
		testSparql("Q6.sparql");
	}

	@Test
	public void Q7_linked_to_diseases_that_are_associated_with_cardiovascular_aspects() {
		testSparql("Q7.sparql");
	}

	@Test
	public void Q8_whose_genes_are_xbp_away_from_the_location_of_gene_of_protein_Y() {
		testSparql("Q8.sparql");
	}

	@Test
	public void Q47_with_a_gene_name_CLDN_star() throws Exception {
		testSparql("Q47.sparql");
	}

	@Test
	public void Q68_with_protein_existence_PE_equals_2_means_transcript_level() throws Exception {
		testSparql("Q68.sparql");
	}

	@Test
	public void Q22_proteins_with_no_function_annotated() throws Exception {
		testSparql("Q22.sparql");
	}

	@Test
	public void Q30_whose_gene_is_located_in_chromosome_2_that_belongs_to_families_with_more_or_equal_to_5_members_in_the_human_proteome() {
		testSparql("Q30.sparql");
	}

	@Test
	public void Q31_with_more_or_equal_10_splices_isoforms() throws Exception {
		testSparql("Q31.sparql");
	}

	@Test
	public void Q32_with_a_coiled_coil_region_and_involved_in_transcription_but_does_not_contain_a_bZIP_domain() throws Exception {
		// uniprot=database:(type:nextprot) AND keyword:"Transcription [KW-0804]" AND annotation:(type:coiled) NOT annotation:(type:"positional domain" bzip)
		testSparql("Q32.sparql");
	}

	@Test
	public void Q64_which_are_enzymes_with_an_incomplete_EC_number() {
		testSparql("Q64.sparql");
	}

	@Test
	public void Q65_protein_with_more_than_one_catalityc_activity() {
		testSparql("Q65.sparql");
	}

	@Test
	public void Q73_proteins_with_no_domain() {
		testSparql("Q73.sparql");
	}
}
