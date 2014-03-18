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
import evaletolab.tool.FileUtil;

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
	
	Model m;
	InfModel rdfs;
	
	@Before
	public void setup() {
		m=ModelFactory.createDefaultModel();
		m.read("expression-heavy.ttl").read("owl/np.ttl");
		rdfs= ModelFactory.createRDFSModel(m);
		

	}
	
	
	
	/**
	 * Q3 with >=2 transmembrane regions 
	 * @throws Exception 
	 */
	@Test
	public void with2TransmembraneRegions() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q3-with2TransmembraneRegions.sparql");
		System.out.println(q);
	}	
	
	/**
	 * Q5 located in mitochondrion and that lack a transit peptide 
	 * @throws Exception 
	 */
	@Test
	public void locatedInMitochondrionAndLackATransitPeptide() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q3-locatedInMitochondrionAndLackATransitPeptide.sparql");
		System.out.println(q);	
	}	
	
	/**
	 * Q9 with 3 disulfide bonds and that are not hormones 
	 * @throws Exception 
	 */
	@Test
	public void with3DisulfideBondsAndNotHormones() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q9-with3DisulfideBondsAndNotHormones.sparql");
		System.out.println(q);	

	}	

	/**
	 * Q13 with a protein kinase domain but no kinase activity
	 * uniprot query, database:(type:nextprot) AND annotation:(type:"positional domain" "protein kinase") NOT ec:2.7.-.-
	 * @throws Exception 
	 */
	@Test
	public void withKinaseDomainButNotKinaseActivity() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q13-withKinaseDomainButNotKinaseActivity.sparql");
		System.out.println(q);	
	}	

	/**
	 * Q14 with 2 SH3 domains and 1 SH2 domain
	 * uniprot query,  database:(type:nextprot) AND annotation:(type:similarity "contains 2 SH3 domains") AND annotation:(type:similarity "contains 1 SH2")
	 * @throws Exception 
	 */
	@Test
	public void with2SH3And1SHD2() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q14-with2SH3And1SHD2.sparql");
		System.out.println(q);	
	}	
	

	
	/**
	 * Q16 with a mature chain <= 100 amino acids which are secreted and do not contain cysteines in the mature chain
	 * @throws Exception 
	 */
	@Test
	public void withMature100AAWhichAreSecretedAndNotContainsCysteinesInMature() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q16-withMature100AAWhichAreSecretedAndNotContainsCysteinesInMature.sparql");
		System.out.println(q);	
	}
	
	/**
	 * Q18 that are acetylated and methylated and located in the nucleus
	 * uniprot query, database:(type:nextprot) AND keyword:"Acetylated [KW-0007|KW-0007]" AND keyword:"Methylated [KW-0488|KW-0488]" AND annotation:(type:location nucleus)
	 * @throws Exception 
	 */
	@Test
	public void thatAreAcetylatedAndMethylated() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q18-thatAreAcetylatedAndMethylated.sparql");
		System.out.println(q);	
	}
	
	/**
	 * Q19 contains a signal sequence followed by a extracellular domain containing a "KRKR" motif
	 * @throws Exception 
	 */
	@Test
	public void containsSignalSequenceFollowedByAExtracellularDomainContainingKRKRMotif() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q19-containsSignalSequenceFollowedByAExtracellularDomainContainingKRKRMotif.sparql");
		System.out.println(q);	
	}
	
	/**
	 * Q32 with a coiled coil region and involved in transcription but does not contain a bZIP domain
	 * uniprot query, database:(type:nextprot) AND keyword:"Transcription [KW-0804]" AND annotation:(type:coiled) NOT annotation:(type:"positional domain" bzip)
	 * @throws Exception 
	 */
	@Test
	public void withCoiledCoiledAndInvolvedInTranscriptionButNotContainBZIP() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q32-withCoiledCoiledAndInvolvedInTranscriptionButNotContainBZIP.sparql");
		System.out.println(q);	
	}
	
	/**
	 * Q34 with >=1 homeobox domain and with >=1 variant in the homeobox domain(s)
	 * @throws Exception 
	 */
	@Test
	public void withHomeoboxAndWithVariantsInTheHomeobox() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q34-withHomeoboxAndWithVariantsInTheHomeobox.sparql");
		System.out.println(q);	

	}		
	
	/**
	 * Q38 with >=1 selenocysteine in their sequence
	 */
	@Test
	public void withSelenocysteineInTheirSequence(){

	}		
}
