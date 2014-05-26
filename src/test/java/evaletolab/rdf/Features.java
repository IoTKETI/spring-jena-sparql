package evaletolab.rdf;

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

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.ResultSet;

import evaletolab.config.WebConfig;
import evaletolab.controller.TripleStore;
import evaletolab.tool.FileUtil;

/**
 * Use case for features queries
 * Q3	with >=2 transmembrane regions 
 * Q9	with 3 disulfide bonds and that are not hormones 
 * Q10 that are glycosylated and not located in the membrane
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
	public void Q3_with2TransmembraneRegions() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q3-with2TransmembraneRegions.sparql");
		//
		// execute query
		String acs=getMetaInfo(q).get("acs");
		int count=getQueryMetaCount(q);
		
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        
        //
        // validate result
		List<String> uri=getLiterals(rs);
        assertTrue( rs.getRowNumber()>=count);
        for(String ac:acs.split(","))
        	assertTrue(ac,uri.contains(ac.trim()));      
	}	
	

	/**
	 * Q9 with 3 disulfide bonds and that are not hormones 
	 * @throws Exception 
	 */
	@Test
	public void Q9_with3DisulfideBondsAndNotHormones() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q9-with3DisulfideBondsAndNotHormones.sparql");
		//
		// execute query
		String acs=getMetaInfo(q).get("acs");
		int count=getQueryMetaCount(q);
		
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        
        //
        // validate result
		List<String> uri=getLiterals(rs);
        assertTrue( rs.getRowNumber()>=count);
        for(String ac:acs.split(","))
        	assertTrue(ac,uri.contains(ac.trim()));  
	}	
	
	/**
	 * Q10 that are glycosylated and not located in the membrane 
	 * @throws Exception 
	 */
	@Test
	public void Q10_thatAreGlycosylatedAndNotLocatedInTheMembrane() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q10.sparql");
		//
		// execute query
		String acs=getMetaInfo(q).get("acs");
		int count=getQueryMetaCount(q);
		
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        
        //
        // validate result
		List<String> uri=getLiterals(rs);
        assertTrue( rs.getRowNumber()>=count);
        for(String ac:acs.split(","))
        	assertTrue(ac,uri.contains(ac.trim())); 
	}	

	/**
	 * Q13 with a protein kinase domain but no kinase activity
	 * uniprot query, database:(type:nextprot) AND annotation:(type:"positional domain" "protein kinase") NOT ec:2.7.-.-
	 * @throws Exception 
	 */
	@Test
	public void Q13_withKinaseDomainButNotKinaseActivity() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q13-withKinaseDomainButNotKinaseActivity.sparql");
		//
		// execute query
		String acs=getMetaInfo(q).get("acs");
		int count=getQueryMetaCount(q);
		
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        
        //
        // validate result
		List<String> uri=getLiterals(rs);
        assertTrue( rs.getRowNumber()>=count);
        for(String ac:acs.split(","))
        	assertTrue(ac,uri.contains(ac.trim()));  
	}	

	/**
	 * Q14 with 2 SH3 domains and 1 SH2 domain
	 * uniprot query,  database:(type:nextprot) AND annotation:(type:similarity "contains 2 SH3 domains") AND annotation:(type:similarity "contains 1 SH2")
	 * @throws Exception 
	 */
	@Test
	public void wQ14_ith2SH3And1SHD2() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q14-with2SH3And1SHD2.sparql");
		//
		// execute query
		String acs=getMetaInfo(q).get("acs");
		int count=getQueryMetaCount(q);
		
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        
        //
        // validate result
		List<String> uri=getLiterals(rs);
        assertTrue( rs.getRowNumber()>=count);
        for(String ac:acs.split(","))
        	assertTrue(ac,uri.contains(ac.trim()));   
	}	
	

	
	/**
	 * Q16 with a mature chain <= 100 amino acids which are secreted and do not contain cysteines in the mature chain
	 * WARNING time>50s
	 * @throws Exception 
	 */
	@Test
	public void Q16_withMature100AAWhichAreSecretedAndNotContainsCysteinesInMature1() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q16-1-withMature100AAWhichAreSecretedAndNotContainsCysteinesInMature.sparql");
		//
		// execute query
		String acs=getMetaInfo(q).get("acs");
		int count=getQueryMetaCount(q);
		
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        
        //
        // validate result
		List<String> uri=getLiterals(rs);
        assertTrue( rs.getRowNumber()>=count);
        for(String ac:acs.split(","))
        	assertTrue(ac,uri.contains(ac.trim()));
	}
	
	/**
	 * Q16 with a mature chain <= 100 amino acids which are secreted and do not contain cysteines in the mature chain
	 * @throws Exception 
	 */
	@Test
	public void Q16_withMature100AAWhichAreSecretedAndNotContainsCysteinesInMature2() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q16-2-withMature100AAWhichAreSecretedAndNotContainsCysteinesInMature.sparql");
		//
		// execute query
		String acs=getMetaInfo(q).get("acs");
		int count=getQueryMetaCount(q);
		
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        
        //
        // validate result
		List<String> uri=getLiterals(rs);
        assertTrue( rs.getRowNumber()>=count);
        for(String ac:acs.split(","))
        	assertTrue(ac,uri.contains(ac.trim()));
	}
	
	/**
	 * Q16 with a mature chain <= 100 amino acids which are secreted and do not contain cysteines in the mature chain
	 * @throws Exception 
	 */
	@Test
	public void Q16_withMature100AAWhichAreSecretedAndNotContainsCysteinesInMature3() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q16-3-withMature100AAWhichAreSecretedAndNotContainsCysteinesInMature.sparql");
		//
		// execute query
		String acs=getMetaInfo(q).get("acs");
		int count=getQueryMetaCount(q);
		
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        
        //
        // validate result
		List<String> uri=getLiterals(rs);
        assertTrue( rs.getRowNumber()>=count);
        for(String ac:acs.split(","))
        	assertTrue(ac,uri.contains(ac.trim()));
	}
	
	/**
	 * Q18 that are acetylated and methylated and located in the nucleus
	 * uniprot query, database:(type:nextprot) AND keyword:"Acetylated [KW-0007|KW-0007]" AND keyword:"Methylated [KW-0488|KW-0488]" AND annotation:(type:location nucleus)
	 * @throws Exception 
	 */
	@Test
	public void Q18_thatAreAcetylatedAndMethylated() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q18-thatAreAcetylatedAndMethylated.sparql");
		//
		// execute query
		String acs=getMetaInfo(q).get("acs");
		int count=getQueryMetaCount(q);
		
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        
        //
        // validate result
		List<String> uri=getLiterals(rs);
        assertTrue( rs.getRowNumber()>=count);
        for(String ac:acs.split(","))
        	assertTrue(ac,uri.contains(ac.trim()));
	}
	
	/**
	 * Q19 contains a signal sequence followed by a extracellular domain containing a "KRKR" motif
	 * WARNING 5s query
	 * @throws Exception 
	 */
	@Test
	public void Q19_containsSignalSequenceFollowedByAExtracellularDomainContainingKRKRMotif() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q19-containsSignalSequenceFollowedByAExtracellularDomainContainingKRKRMotif.sparql");
		//
		// execute query
		String acs=getMetaInfo(q).get("acs");
		int count=getQueryMetaCount(q);
		
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        
        //
        // validate result
		List<String> uri=getLiterals(rs);
        assertTrue( rs.getRowNumber()>=count);
        for(String ac:acs.split(","))
        	assertTrue(ac,uri.contains(ac.trim()));         
	}
	
	/**
	 * Q32 with a coiled coil region and involved in transcription but does not contain a bZIP domain
	 * uniprot query, database:(type:nextprot) AND keyword:"Transcription [KW-0804]" AND annotation:(type:coiled) NOT annotation:(type:"positional domain" bzip)
	 * @throws Exception 
	 */
	@Test
	public void Q32_withCoiledCoiledAndInvolvedInTranscriptionButNotContainBZIP() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q32.sparql");
		//
		// execute query
		String acs=getMetaInfo(q).get("acs");
		int count=getQueryMetaCount(q);
		
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        
        //
        // validate result
		List<String> uri=getLiterals(rs);
        assertTrue( rs.getRowNumber()>=count);
        for(String ac:acs.split(","))
        	assertTrue(ac,uri.contains(ac.trim()));         
	}
	
	/**
	 * Q34 with >=1 homeobox domain and with >=1 variant in the homeobox domain(s)
	 * WARNING 5s query
	 * @throws Exception 
	 */
	@Test
	public void Q34_withHomeoboxAndWithVariantsInTheHomeobox() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q34-withHomeoboxAndWithVariantsInTheHomeobox.sparql");
		//
		// execute query
		String acs=getMetaInfo(q).get("acs");
		int count=getQueryMetaCount(q);
		
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        
        //
        // validate result
		List<String> uri=getLiterals(rs);
        assertTrue( rs.getRowNumber()>=count);
        for(String ac:acs.split(","))
        	assertTrue(ac,uri.contains(ac.trim()));       
	}		
	
	/**
	 * Q38 with >=1 selenocysteine in their sequence
	 * uniprot search, database:(type:nextprot) AND annotation:(type:non_std)
	 * @throws Exception 
	 */
	@Test
	public void Q38_withSelenocysteineInTheirSequence() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q38.sparql");
		//
		// execute query
		String acs=getMetaInfo(q).get("acs");
		int count=getQueryMetaCount(q);
		
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        
        //
        // validate result
		List<String> uri=getLiterals(rs);
        assertTrue( rs.getRowNumber()>=count);
        for(String ac:acs.split(","))
        	assertTrue(ac,uri.contains(ac.trim()));               
	}		
	
	/**
	 * Q39 with >=1 mutagenesis in a position that correspond to an annotated active site
	 * WARNING 2s query
	 * @throws Exception 
	 */
	@Test
	public void Q39_with1MutagenesisInAPositionThatCorrespondToAnAnnotatedActiveSite() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q39.sparql");
		//
		// execute query
		String acs=getMetaInfo(q).get("acs");
		int count=getQueryMetaCount(q);
		
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        
        //
        // validate result
		List<String> uri=getLiterals(rs);
        assertTrue( rs.getRowNumber()>=count);
        for(String ac:acs.split(","))
        	assertTrue(ac,uri.contains(ac.trim()));   
	}		

	/**
	 * Q40 that are enzymes and with >=1 mutagenesis that "decrease" or "abolish" activity
	 * WARNING 3s query	  
	 * @throws Exception 
	 */
	@Test
	public void Q40_thatAreEnzymesAndWith1mutagenesisThatDecreaseOrAbolishActivity() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q40.sparql");
		//
		// execute query
		String acs=getMetaInfo(q).get("acs");
		int count=getQueryMetaCount(q);
		
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        
        //
        // validate result
		List<String> uri=getLiterals(rs);
        assertTrue( rs.getRowNumber()>=count);
        for(String ac:acs.split(","))
        	assertTrue(ac,uri.contains(ac.trim()));         
	}		
	
	/**
	 * Q41 that are annotated with GO "F" terms prefixed by "Not"
	 * @throws Exception 
	 */
	@Test
	public void Q41_thatAreAnnotatedWithGO_F_termsPrefixedByNot() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q41.sparql");
		//
		// execute query
		String acs=getMetaInfo(q).get("acs");
		int count=getQueryMetaCount(q);
		
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        
        //
        // validate result
		List<String> uri=getLiterals(rs);
        assertTrue( rs.getRowNumber()>=count);
        for(String ac:acs.split(","))
        	assertTrue(ac,uri.contains(ac.trim()));          
	}		
	
	
	/**
	 * Q48 with >=1 variants of the type "C->" (Cys to anything else) that are linked to >=1 disease
	 * @throws Exception 
	 */
	@Test
	public void Q48_with1VariantOfType_CtoAnythingElse_thatAreTo1Disease() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q48.sparql");
		//
		// execute query
		String acs=getMetaInfo(q).get("acs");
		int count=getQueryMetaCount(q);
		
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        
        //
        // validate result
		List<String> uri=getLiterals(rs);
        assertTrue( rs.getRowNumber()>=count);
        for(String ac:acs.split(","))
        	assertTrue(ac,uri.contains(ac.trim()));     
	}		
	
	/**
	 * Q49 with >=1 variants of the types "A->R" or "R->A"
	 * @throws Exception 
	 */
	@Test
	public void Q49_with1VariantOfTheTypesA_R_or_R_A() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q49.sparql");
		//
		// execute query
		String acs=getMetaInfo(q).get("acs");
		int count=getQueryMetaCount(q);
		
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        
        //
        // validate result
		List<String> uri=getLiterals(rs);
        assertTrue( rs.getRowNumber()>=count);
        for(String ac:acs.split(","))
        	assertTrue(ac,uri.contains(ac.trim()));          
	}		

}
