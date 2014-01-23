package evaletolab.solr;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import evaletolab.config.WebConfig;

/**
 * Use case for features queries
 * - Q3	with >=2 transmembrane regions 
 * - Q5	located in mitochondrion and that lack a transit peptide
 * - Q9	with 3 disulfide bonds and that are not hormones 
 * - Q13 with a protein kinase domain but no kinase activity 
 * - Q14 with 2 SH3 domains and 1 SH2 domain 
 * - Q15 with a PDZ domain that interact with at least 1 protein which is expressed in brain 
 * - Q16 with a mature chain <= 100 amino acids which are secreted and do not contain cysteines in the mature chain 
 * - Q18 that are acetylated and methylated and located in the nucleus 
 * - Q19 contains a signal sequence followed by a extracellular domain containing a "KRKR" motif 
 * * Q22 with no function annotated
 * * Q27 with >=1 glycosylation sites reported in PubMed:X or PubMed:Y
 * - Q32 with a coiled coil region and involved in transcription but does not contain a bZIP domain
 * - Q34 with >=1 homeobox domain and with >=1 variant in the homeobox domain(s)
 * - Q35 located in the mitochondrion and which is an enzyme
 * - Q38 with >=1 selenocysteine in their sequence
 * - Q39 with >=1 mutagenesis in a position that correspond to an annotated active site
 * - Q40 that are enzymes and with >=1 mutagenesis that "decrease" or "abolish" activity
 * - Q41 that are annotated with GO "F" terms prefixed by "Not"
 * - Q48 with >=1 variants of the type "C->" (Cys to anything else) that are linked to >=1 disease
 * - Q49 with >=1 variants of the types "A->R" or "R->A"
 *  
 * @author evaleto
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
public class Features {
	

	
	@Before
	public void setup() {

	}
	
	
	
	/**
	 * Q3 with >=2 transmembrane regions 
	 */
	@Test
	public void with2TransmembraneRegions(){

	}	
	
	/**
	 * Q5 located in mitochondrion and that lack a transit peptide 
	 */
	@Test
	public void locatedInMitochondrionAndLackATransitPeptide(){

	}	
	
	/**
	 * Q9 with 3 disulfide bonds and that are not hormones 
	 */
	@Test
	public void with3Disulfide(){

	}	

	/**
	 * Q13 with a protein kinase domain but no kinase activity
	 */
	@Test
	public void withKinaseDomainButNotKinaseActivity(){

	}	

	/**
	 * Q14 with 2 SH3 domains and 1 SH2 domain
	 */
	@Test
	public void with2SH3And1SHD2(){

	}	
	
	/**
	 * Q15 with a PDZ domain that interact with at least 1 protein which is expressed in brain
	 *  --> hierarchical Terms for Nervous System
	 */
	@Test
	public void withPDZthatInteractWithProteinExpressedInBrain(){

	}	
	
	/**
	 * Q16 with a mature chain <= 100 amino acids which are secreted and do not contain cysteines in the mature chain
	 */
	@Test
	public void withMature100AAWhichAreSecretedAndNotContainsCysteinesInMature(){

	}
	
	/**
	 * Q18 that are acetylated and methylated and located in the nucleus
	 */
	@Test
	public void thatAreAcetylatedAndMethylated(){

	}
	
	/**
	 * Q19 contains a signal sequence followed by a extracellular domain containing a "KRKR" motif
	 */
	@Test
	public void containsSignalSequenceFollowedByAExtracellularDomainContainingKRKRMotif(){

	}
	
	/**
	 * Q32 with a coiled coil region and involved in transcription but does not contain a bZIP domain
	 */
	@Test
	public void withCoiledCoiledAndInvolvedInTranscriptionButNotContainBZIP(){

	}
	
	/**
	 * Q34 with >=1 homeobox domain and with >=1 variant in the homeobox domain(s)
	 */
	@Test
	public void withHomeoboxAndWithVariantsInTheHomeobox(){

	}		
	
	/**
	 * Q38 with >=1 selenocysteine in their sequence
	 */
	@Test
	public void withSelenocysteineInTheirSequence(){

	}		
}
