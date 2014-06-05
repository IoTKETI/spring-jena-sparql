package evaletolab.rdf;

import org.junit.Test;

import evaletolab.TripleStoreBaseTest;

/**
 * Use case for _3Dstructure
 * - Q81 with >=1 3D structure and are located in the mitochondrion and are linked with a disease 
 * - Q108 All proteins that have a 3D structure in PDB that overlap by at least 50 amino acids with a SH3 domain.
 * @author evaleto, dteixeir
 *
 */

public class _3Dstructure extends TripleStoreBaseTest{
	
	@Test
	public void Q108_that_have_a_3D_structure_in_PDB_that_overlap_by_at_least_50_amino_acids_with_a_SH3_domain(){
		testSparql("Q108.sparql");
	}			
	
	@Test
	public void Q081_with_at_least_one_3D_structure_that_is_located_in_the_mitochondrion_and_are_linked_with_a_disease_using_keyword(){
		testSparql("Q081-keyword.sparql");
	}			

	@Test
	public void Q081_with_at_least_one_3D_structure_that_is_located_in_the_mitochondrion_and_are_linked_with_a_disease_using_localisation(){
		testSparql("Q081-localisation.sparql");
	}			
	
}
