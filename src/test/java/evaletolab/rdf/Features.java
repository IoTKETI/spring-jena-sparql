package evaletolab.rdf;

import org.junit.Test;

import evaletolab.TripleStoreBaseTest;

/**
 * Use case for features queries
 * Q3 with >=2 transmembrane regions
 * Q9 with 3 disulfide bonds and that are not hormones
 * Q13 with a protein kinase domain but no kinase activity
 * Q14 with 2 SH3 domains and 1 SH2 domain
 * Q15 with a PDZ domain that interact with at least 1 protein which is expressed in brain
 * Q16 with a mature chain <= 100 amino acids which are secreted and do not contain cysteines in the mature chain
 * Q18 that are acetylated and methylated and located in the nucleus
 * Q19 contains a signal sequence followed by a extracellular domain containing a "KRKR" motif
 ** Q27 with >=1 glycosylation sites reported in PubMed:X or PubMed:Y
 * Q32 with a coiled coil region and involved in transcription but does not contain a bZIP domain
 * Q34 with >=1 homeobox domain and with >=1 variant in the homeobox domain(s)
 * Q35 located in the mitochondrion and which is an enzyme
 * Q38 with >=1 selenocysteine in their sequence
 * Q39 with >=1 mutagenesis in a position that correspond to an annotated active site
 * Q40 that are enzymes and with >=1 mutagenesis that "decrease" or "abolish" activity
 * Q41 that are annotated with GO "F" terms prefixed by "Not"
 * Q48 with >=1 variants of the type "C->" (Cys to anything else) that are linked to >=1 disease
 * Q49 with >=1 variants of the types "A->R" or "R->A"
 * 
 * @author evaleto
 */
public class Features extends TripleStoreBaseTest {

	@Test
	public void Q3_with2TransmembraneRegions() {
		testSparql("Q3.sparql");
	}

	@Test
	public void Q9_with3DisulfideBondsAndNotHormones() {
		testSparql("Q009.sparql");
	}

	/**
	 * Q13 with a protein kinase domain but no kinase activity
	 * uniprot query, database:(type:nextprot) AND annotation:(type:"positional domain" "protein kinase") NOT ec:2.7.-.-
	 * 
	 * @throws Exception
	 */
	@Test
	public void Q13_withKinaseDomainButNotKinaseActivity() {
		testSparql("Q13.sparql");
	}

	/**
	 * Q14 with 2 SH3 domains and 1 SH2 domain
	 * uniprot query, database:(type:nextprot) AND annotation:(type:similarity "contains 2 SH3 domains") AND annotation:(type:similarity "contains 1 SH2")
	 * 
	 * @throws Exception
	 */
	@Test
	public void wQ14_ith2SH3And1SHD2() {
		testSparql("Q14.sparql");
	}

	/**
	 * Q16 with a mature chain <= 100 amino acids which are secreted and do not contain cysteines in the mature chain
	 * WARNING time>50s
	 * 
	 * @throws Exception
	 */
	@Test
	public void Q16_withMature100AAWhichAreSecretedAndNotContainsCysteinesInMature1() {
		testSparql("Q16-1.sparql");
	}

	/**
	 * Q16 with a mature chain <= 100 amino acids which are secreted and do not contain cysteines in the mature chain
	 * 
	 * @throws Exception
	 */
	@Test
	public void Q16_withMature100AAWhichAreSecretedAndNotContainsCysteinesInMature2() throws Exception {
		testSparql("Q16-2.sparql");
	}

	/**
	 * Q16 with a mature chain <= 100 amino acids which are secreted and do not contain cysteines in the mature chain
	 * 
	 * @throws Exception
	 */
	@Test
	public void Q16_withMature100AAWhichAreSecretedAndNotContainsCysteinesInMature3() throws Exception {
		testSparql("Q16-3.sparql");
	}

	/**
	 * Q18 that are acetylated and methylated and located in the nucleus
	 * uniprot query, database:(type:nextprot) AND keyword:"Acetylated [KW-0007|KW-0007]" AND keyword:"Methylated [KW-0488|KW-0488]" AND annotation:(type:location nucleus)
	 * 
	 * @throws Exception
	 */
	@Test
	public void Q18_thatAreAcetylatedAndMethylated() throws Exception {
		testSparql("Q18.sparql");
	}

	/**
	 * Q19 contains a signal sequence followed by a extracellular domain containing a "KRKR" motif
	 * WARNING 5s query
	 * 
	 * @throws Exception
	 */
	@Test
	public void Q19_containsSignalSequenceFollowedByAExtracellularDomainContainingKRKRMotif() throws Exception {
		testSparql("Q19.sparql");
	}

	/**
	 * Q32 with a coiled coil region and involved in transcription but does not contain a bZIP domain
	 * uniprot query, database:(type:nextprot) AND keyword:"Transcription [KW-0804]" AND annotation:(type:coiled) NOT annotation:(type:"positional domain" bzip)
	 * 
	 * @throws Exception
	 */
	@Test
	public void Q32_withCoiledCoiledAndInvolvedInTranscriptionButNotContainBZIP() throws Exception {
		testSparql("Q32.sparql");
	}

	/**
	 * Q34 with >=1 homeobox domain and with >=1 variant in the homeobox domain(s)
	 * WARNING 5s query
	 * 
	 * @throws Exception
	 */
	@Test
	public void Q34_withHomeoboxAndWithVariantsInTheHomeobox() throws Exception {
		testSparql("Q34.sparql");
	}

	/**
	 * Q38 with >=1 selenocysteine in their sequence
	 * uniprot search, database:(type:nextprot) AND annotation:(type:non_std)
	 * 
	 * @throws Exception
	 */
	@Test
	public void Q38_withSelenocysteineInTheirSequence() throws Exception {
		testSparql("Q38.sparql");
	}

	/**
	 * Q39 with >=1 mutagenesis in a position that correspond to an annotated active site
	 * WARNING 2s query
	 * 
	 * @throws Exception
	 */
	@Test
	public void Q39_with1MutagenesisInAPositionThatCorrespondToAnAnnotatedActiveSite() throws Exception {
		testSparql("Q39.sparql");
	}

	/**
	 * Q40 that are enzymes and with >=1 mutagenesis that "decrease" or "abolish" activity
	 * WARNING 3s query
	 * 
	 * @throws Exception
	 */
	@Test
	public void Q40_thatAreEnzymesAndWith1mutagenesisThatDecreaseOrAbolishActivity() throws Exception {
		testSparql("Q40.sparql");
	}

	/**
	 * Q41 that are annotated with GO "F" terms prefixed by "Not"
	 * 
	 * @throws Exception
	 */
	@Test
	public void Q41_thatAreAnnotatedWithGO_F_termsPrefixedByNot() throws Exception {
		testSparql("Q41.sparql");
	}

	/**
	 * Q48 with >=1 variants of the type "C->" (Cys to anything else) that are linked to >=1 disease
	 * 
	 * @throws Exception
	 */
	@Test
	public void Q48_with1VariantOfType_CtoAnythingElse_thatAreTo1Disease() throws Exception {
		testSparql("Q48.sparql");
	}

	/**
	 * Q49 with >=1 variants of the types "A->R" or "R->A"
	 * 
	 * @throws Exception
	 */
	@Test
	public void Q49_with1VariantOfTheTypesA_R_or_R_A() throws Exception {
		testSparql("Q49.sparql");
	}

}
