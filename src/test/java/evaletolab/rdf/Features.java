package evaletolab.rdf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
 * Q3	with >=2 transmembrane regions 
 * Q5	located in mitochondrion and that lack a transit peptide
 * Q9	with 3 disulfide bonds and that are not hormones 
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
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
public class Features extends TripleStore{
	
	@Autowired
	private Properties config;
	
	@Before
	public void setup() throws Exception {
		//
		// open session in triplestore
		open();
	}
	
	
	
	/**
	 * Q3 with >=2 transmembrane regions 
	 * @throws Exception 
	 */
	@Test
	public void with2TransmembraneRegions() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q3-with2TransmembraneRegions.sparql");
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        
        //
        // validate result
		List<String> uri=getURIs(rs);
        assertTrue( rs.getRowNumber()>=3742);
        assertTrue(uri.contains("http://nextprot.org/rdf/entry/NX_Q2QL34"));
        assertTrue(uri.contains("http://nextprot.org/rdf/entry/NX_A6NFC5"));        
	}	
	
	/**
	 * Q5 located in mitochondrion and that lack a transit peptide 
	 * @throws Exception 
	 */
	@Test
	public void locatedInMitochondrionAndLackATransitPeptide() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q5-locatedInMitochondrionAndLackATransitPeptide.sparql");
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        //
        // validate result
		List<String> uri=getURIs(rs);
        assertTrue( rs.getRowNumber()>=28);
        assertTrue(uri.contains("http://nextprot.org/rdf/entry/NX_P29353"));
        assertTrue(uri.contains("http://nextprot.org/rdf/entry/NX_P78537"));            
	}	
	
	/**
	 * Q9 with 3 disulfide bonds and that are not hormones 
	 * @throws Exception 
	 */
	@Test
	public void with3DisulfideBondsAndNotHormones() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q9-with3DisulfideBondsAndNotHormones.sparql");
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        //
        // validate result
		List<String> uri=getURIs(rs);
        assertTrue( rs.getRowNumber()>=3141);
        assertTrue(uri.contains("http://nextprot.org/rdf/entry/NX_P09622"));
        assertTrue(uri.contains("http://nextprot.org/rdf/entry/NX_P0CF51"));   
	}	

	/**
	 * Q13 with a protein kinase domain but no kinase activity
	 * uniprot query, database:(type:nextprot) AND annotation:(type:"positional domain" "protein kinase") NOT ec:2.7.-.-
	 * @throws Exception 
	 */
	@Test
	public void withKinaseDomainButNotKinaseActivity() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q13-withKinaseDomainButNotKinaseActivity.sparql");
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        //
        // validate result
		List<String> uri=getURIs(rs);
        assertTrue( rs.getRowNumber()>=491);
        assertTrue(uri.contains("http://nextprot.org/rdf/entry/NX_P0C1S8"));
        assertTrue(uri.contains("http://nextprot.org/rdf/entry/NX_O14920"));   
	}	

	/**
	 * Q14 with 2 SH3 domains and 1 SH2 domain
	 * uniprot query,  database:(type:nextprot) AND annotation:(type:similarity "contains 2 SH3 domains") AND annotation:(type:similarity "contains 1 SH2")
	 * @throws Exception 
	 */
	@Test
	public void with2SH3And1SHD2() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q14-with2SH3And1SHD2.sparql");
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        //
        // validate result
		List<String> uri=getURIs(rs);
        assertTrue( rs.getRowNumber()>=9);
        assertTrue(uri.contains("http://nextprot.org/rdf/entry/NX_P52735"));
        assertTrue(uri.contains("http://nextprot.org/rdf/entry/NX_P16333"));    
        assertTrue(uri.contains("http://nextprot.org/rdf/entry/NX_O75791"));    
	}	
	

	
	/**
	 * Q16 with a mature chain <= 100 amino acids which are secreted and do not contain cysteines in the mature chain
	 * WARNING time>50s
	 * @throws Exception 
	 */
	@Test
	public void withMature100AAWhichAreSecretedAndNotContainsCysteinesInMature1() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q16-1-withMature100AAWhichAreSecretedAndNotContainsCysteinesInMature.sparql");
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        //
        // validate result
		List<String> uri=getURIs(rs);
        assertTrue( rs.getRowNumber()>=1);
	}
	
	/**
	 * Q16 with a mature chain <= 100 amino acids which are secreted and do not contain cysteines in the mature chain
	 * @throws Exception 
	 */
	@Test
	public void withMature100AAWhichAreSecretedAndNotContainsCysteinesInMature2() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q16-2-withMature100AAWhichAreSecretedAndNotContainsCysteinesInMature.sparql");
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        //
        // validate result
		List<String> uri=getURIs(rs);
        assertTrue( rs.getRowNumber()>=1);
	}
	
	/**
	 * Q16 with a mature chain <= 100 amino acids which are secreted and do not contain cysteines in the mature chain
	 * @throws Exception 
	 */
	@Test
	public void withMature100AAWhichAreSecretedAndNotContainsCysteinesInMature3() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q16-3-withMature100AAWhichAreSecretedAndNotContainsCysteinesInMature.sparql");
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        //
        // validate result
		List<String> uri=getURIs(rs);
        assertTrue( rs.getRowNumber()>=1);
	}
	
	/**
	 * Q18 that are acetylated and methylated and located in the nucleus
	 * uniprot query, database:(type:nextprot) AND keyword:"Acetylated [KW-0007|KW-0007]" AND keyword:"Methylated [KW-0488|KW-0488]" AND annotation:(type:location nucleus)
	 * @throws Exception 
	 */
	@Test
	public void thatAreAcetylatedAndMethylated() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q18-thatAreAcetylatedAndMethylated.sparql");
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        //
        // validate result
		List<String> uri=getURIs(rs);
        assertTrue( rs.getRowNumber()>=139);
        assertTrue(uri.contains("http://nextprot.org/rdf/entry/NX_P15056"));
        assertTrue(uri.contains("http://nextprot.org/rdf/entry/NX_P48382"));  
        assertTrue(uri.contains("http://nextprot.org/rdf/entry/NX_Q9NYF8"));  
	}
	
	/**
	 * Q19 contains a signal sequence followed by a extracellular domain containing a "KRKR" motif
	 * WARNING 5s query
	 * @throws Exception 
	 */
	@Test
	public void containsSignalSequenceFollowedByAExtracellularDomainContainingKRKRMotif() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q19-containsSignalSequenceFollowedByAExtracellularDomainContainingKRKRMotif.sparql");
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        
        //
        // validate result
		List<String> uri=getURIs(rs);
        assertTrue( rs.getRowNumber()>=31);
        assertTrue(uri.contains("http://nextprot.org/rdf/entry/NX_P21754"));
        assertTrue(uri.contains("http://nextprot.org/rdf/entry/NX_P53708"));  
        assertTrue(uri.contains("http://nextprot.org/rdf/entry/NX_P60508"));          
	}
	
	/**
	 * Q32 with a coiled coil region and involved in transcription but does not contain a bZIP domain
	 * uniprot query, database:(type:nextprot) AND keyword:"Transcription [KW-0804]" AND annotation:(type:coiled) NOT annotation:(type:"positional domain" bzip)
	 * @throws Exception 
	 */
	@Test
	public void withCoiledCoiledAndInvolvedInTranscriptionButNotContainBZIP() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q32.sparql");
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        
        //
        // validate result
		List<String> uri=getURIs(rs);
        assertTrue( rs.getRowNumber()>=180);
        assertTrue(uri.contains("http://nextprot.org/rdf/entry/NX_Q9Y5B9"));
        assertTrue(uri.contains("http://nextprot.org/rdf/entry/NX_Q13352"));  
        assertTrue(uri.contains("http://nextprot.org/rdf/entry/NX_Q8TDY2"));            
	}
	
	/**
	 * Q34 with >=1 homeobox domain and with >=1 variant in the homeobox domain(s)
	 * WARNING 5s query
	 * @throws Exception 
	 */
	@Test
	public void withHomeoboxAndWithVariantsInTheHomeobox() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q34-withHomeoboxAndWithVariantsInTheHomeobox.sparql");
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        
        //
        // validate result
		List<String> uri=getURIs(rs);
        assertTrue( rs.getRowNumber()>=225);
        assertTrue(uri.contains("http://nextprot.org/rdf/entry/NX_A6NJ46"));
        assertTrue(uri.contains("http://nextprot.org/rdf/entry/NX_P09629"));          
	}		
	
	/**
	 * Q38 with >=1 selenocysteine in their sequence
	 * uniprot search, database:(type:nextprot) AND annotation:(type:non_std)
	 * @throws Exception 
	 */
	@Test
	public void withSelenocysteineInTheirSequence() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q38.sparql");
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        
        //
        // validate result
		List<String> uri=getURIs(rs);
        assertTrue( rs.getRowNumber()>=26);
        assertTrue(uri.contains("http://nextprot.org/rdf/entry/NX_O60613"));
        assertTrue(uri.contains("http://nextprot.org/rdf/entry/NX_P63302"));                
        assertTrue(uri.contains("http://nextprot.org/rdf/entry/NX_Q9C0D9"));                
	}		
	
	/**
	 * Q39 with >=1 mutagenesis in a position that correspond to an annotated active site
	 * WARNING 2s query
	 * @throws Exception 
	 */
	@Test
	public void with1MutagenesisInAPositionThatCorrespondToAnAnnotatedActiveSite() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q39.sparql");
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();

        //
        // validate result
		List<String> uri=getURIs(rs);
        assertTrue( rs.getRowNumber()>=356);
        assertTrue(uri.contains("http://nextprot.org/rdf/entry/NX_P22303"));
        assertTrue(uri.contains("http://nextprot.org/rdf/entry/NX_Q2T9J0"));                
        assertTrue(uri.contains("http://nextprot.org/rdf/entry/NX_Q9NUW8"));   
	}		

	/**
	 * Q40 that are enzymes and with >=1 mutagenesis that "decrease" or "abolish" activity
	 * WARNING 3s query	  
	 * @throws Exception 
	 */
	@Test
	public void tahtAreEnzymesAndWith1mutagenesisThatDecreaseOrAbolishActivity() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q40.sparql");
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();

        //
        // validate result
		List<String> uri=getURIs(rs);
        assertTrue( rs.getRowNumber()>=696);
        assertTrue(uri.contains("http://nextprot.org/rdf/entry/NX_Q5T1C6"));
        assertTrue(uri.contains("http://nextprot.org/rdf/entry/NX_Q9HCK8"));           
	}		
	
	/**
	 * Q41 that are annotated with GO "F" terms prefixed by "Not"
	 * @throws Exception 
	 */
	@Test
	public void thatAreAnnotatedWithGO_F_termsPrefixedByNot() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q41.sparql");
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();

        //
        // validate result
		List<String> uri=getURIs(rs);
        assertTrue( rs.getRowNumber()>=198);
        assertTrue(uri.contains("http://nextprot.org/rdf/entry/NX_P14210"));
        assertTrue(uri.contains("http://nextprot.org/rdf/entry/NX_P12270"));            
	}		
	
	
	/**
	 * Q48 with >=1 variants of the type "C->" (Cys to anything else) that are linked to >=1 disease
	 * @throws Exception 
	 */
	@Test
	public void with1VariantOfType_CtoAnythingElse_thatAreTo1Disease() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q48.sparql");
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();

        //
        // validate result
		List<String> uri=getURIs(rs);
        assertTrue( rs.getRowNumber()>=1738);
        assertTrue(uri.contains("http://nextprot.org/rdf/entry/NX_Q9Y587"));
        assertTrue(uri.contains("http://nextprot.org/rdf/entry/NX_P09622"));         
	}		
	
	/**
	 * Q49 with >=1 variants of the types "A->R" or "R->A"
	 * @throws Exception 
	 */
	@Test
	public void with1VariantOfTheTypesA_R_or_R_A() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q49.sparql");
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();

        //
        // validate result
		List<String> uri=getURIs(rs);
        assertTrue( rs.getRowNumber()>=266);
        assertTrue(uri.contains("http://nextprot.org/rdf/entry/NX_O14828"));
        assertTrue(uri.contains("http://nextprot.org/rdf/entry/NX_P29323"));           
	}		

}
