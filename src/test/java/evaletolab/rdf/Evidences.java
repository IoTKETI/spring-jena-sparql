package evaletolab.rdf;

import org.junit.Test;

import evaletolab.TripleStoreBaseTest;

/**
 * Use case for evidences queries
 * - Q27 with >=1 glycosylation sites reported in PubMed:X or PubMed:Y
 * - Q53 which are involved in cell adhesion according to GO with an evidence not IAE and not ISS
 * - Q57 which are located in mitochondrion with an evidence other than HPA and DKFZ-GFP
 * - Q63 which have >=1 RRM RNA-binding domain and either no GO "RNA binding" other a GO "RNA binding" with evidence IEA or ISS
 * 
 * @author evaleto
 */
public class Evidences extends TripleStoreBaseTest {

	/**
	 * Q27 with >=1 glycosylation sites reported in PubMed:X or PubMed:Y
	 * 
	 * @throws Exception
	 */
	@Test
	public void Q27_withGlycosylationSitesReportedInPubmedXOrPubmedY() throws Exception {
		testSparql("Q27.sparql");
	}

	/**
	 * Q53 which are involved in cell adhesion according to GO with
	 * an evidence not IAE and not ISS
	 * - Cell adhesion [GO:0007155 ]
	 * 
	 * @throws Exception
	 */
	@Test
	public void Q53_involvedInGO0007155_WithEvidence_NotIEA_And_NotISS() throws Exception {
		testSparql("Q53-1.sparql");
	}

	/**
	 * Q53 which are involved in cell adhesion according to GO with
	 * an evidence not IAE and not ISS
	 * - Cell adhesion [GO:0007155 ]
	 * 
	 * @throws Exception
	 */
	@Test
	public void Q53_involvedInGO0007155_WithEvidence_NotIEA_And_NotISS_disJointWith() throws Exception {
		testSparql("Q53-2.sparql");
	}

	public void Q53_involvedInGO0007155_WithEvidence_NotIEA_And_NotISS_differentFrom() throws Exception {
		testSparql("Q53-3.sparql");
	}

	/**
	 * Q57 which are located in mitochondrion with an evidence other
	 * than HPA and DKFZ-GFP
	 * WARNING! term:SL-0173 is (this must be inferred)
	 * rdfs:sameAs term:GO:0005739;
	 * rdfs:sameAs term:KW-0496;
	 * 
	 * @throws Exception
	 */
	@Test
	public void Q57_locatedInMitochondrionWithEvidenceOtherThan_HPA_And_DKFZ_GFP() throws Exception {
		testSparql("Q57.sparql");
	}

	@Test
	public void Q57_1_locatedInMitochondrionWithEvidenceOtherThan_HPA_And_DKFZ_GFP() throws Exception {
		testSparql("Q57-1.sparql");
	}

	/**
	 * Q63 which have >=1 RRM RNA-binding domain (DO-00581 UniprotDomain) and either no GO "RNA binding" (GO:0003723 go molecular function)
	 * other a GO "RNA binding" with evidence IEA or ISS
	 * 
	 * @throws Exception
	 */
	@Test
	public void Q63_with1RRM_RNAbindingDomainWithEvidenceIEAorISS() throws Exception {
		testSparql("Q63.sparql");

	}

}
