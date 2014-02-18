package evaletolab.rdf;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import evaletolab.config.WebConfig;

/**
 * Use case for evidences queries
 * - Q53	which are involved in cell adhesion according to GO with an evidence not IAE and not ISS
 * - Q57	which are located in mitochondrion with an evidence other than HPA and DKFZ-GFP
 * - Q63	which have >=1 RRM RNA-binding domain and either no GO "RNA binding" other a GO "RNA binding" with evidence IEA or ISS
 * - Q68	with protein evidence PE=2 (transcript level)
 *  
 * @author evaleto
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
public class Evidences {
	
	Model m;
	InfModel rdfs;
	
	@Before
	public void setup() {
		m=ModelFactory.createDefaultModel();
		m.read("expression-heavy.ttl").read("owl/np.ttl");
		rdfs= ModelFactory.createRDFSModel(m);
		
		//
		// preload load data
		String q="PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
				 "PREFIX : <http://nextprot.org/rdf#> " +
				 "SELECT * WHERE { " +
				 "  ?tissue a :NextprotTissues . " +
				 "}";		
		Query query = QueryFactory.create(q);
        QueryExecution qe = QueryExecutionFactory.create(query,rdfs);
        qe.execSelect();
	}
	
	
	
	/**
	 * Q53	which are involved in cell adhesion according to GO with 
	 *      an evidence not IAE and not ISS 
	 *      - Cell adhesion [GO:0007155] 
	 */
	@Test
	public void involvedInGO0007155_WithEvidence_NotIEA_And_NotISS(){
		// query
		String q="PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
				 "PREFIX : <http://nextprot.org/rdf#> " +
				 "SELECT ?entry WHERE { " +
				 "  ?entry  :isoform/:involved/ ?statement;:in GO:0007155"+
				 "  FILTER NOT EXISTS { ?statement :evidence/rdfs:type :IAE } "+
				 "}";	
	}	

	@Test
	public void involvedInGO0007155_WithEvidence_NotIEA_And_NotISS_2(){
		// query
		String q="PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
				 "PREFIX : <http://nextprot.org/rdf#> " +
				 "SELECT ?entry WHERE { " +
				 "  ?entry  :isoform/:involved/ ?statement;:in GO:0007155."+
				 "  ?statement :evidence/rdfs:type/owl:differentFrom :IAE;owl:differentFrom :ISS."+
				 "}";	
	}	

	@Test
	public void involvedInGO0007155_WithEvidence_NotIEA_And_NotISS_3(){
		// query
		String q="PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
				 "PREFIX : <http://nextprot.org/rdf#> " +
				 "SELECT ?entry WHERE { " +
				 "  ?entry  :isoform/:involved/ ?statement;:in GO:0007155."+
				 "  ?statement :evidence/rdfs:type/owl:complementOf :IAE;owl:complementOf :ISS. "+
				 "}";	
	}	
	
	/**
	 * Q57	which are located in mitochondrion with an evidence other 
	 *      than HPA and DKFZ-GFP 
	 */
	@Test
	public void locatedInMitochondrionWithEvidenceOtherThan_HPA_And_DKFZ_GFP(){
		// query
		String q="";		

	}	
	
	/**
	 * Q63 which have >=1 RRM RNA-binding domain and either no GO "RNA binding" 
	 *     other a GO "RNA binding" with evidence IEA or ISS 
	 */
	@Test
	public void with1RRM_RNAbindingDomainWithEvidenceIEAorISS(){

	}	

	/**
	 * Q68 with protein evidence PE=2 (transcript level)
	 */
	@Test
	public void withProteinEvidencePE2(){

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
