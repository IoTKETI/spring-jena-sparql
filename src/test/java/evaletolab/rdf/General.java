package evaletolab.rdf;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
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
import com.sun.tools.javac.code.Attribute.Array;

import evaletolab.config.WebConfig;
import evaletolab.tool.FileUtil;

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
 * Q32 with a coiled coil region and involved in transcription but does not contain a bZIP domain
 * Q47 with a gene name CLDN*
 * Q64 which are enzymes with an incomplete EC number
 * Q65 Proteins with >1 catalytic activity 
 * Q68 with protein evidence PE=2 (transcript level)
 * Q73 Proteins with no domain 
 *  
 * @author evaleto
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
public class General extends TripleStore{
	
	@Autowired
	private Properties config;
	
	@Before
	public void setup() throws Exception {
		//
		// open session in triplestore
		open();
	}
	
	/**
	 * Q1 that are phosphorylated and located in the cytoplasm 
	 * @throws Exception 
	 */
	@Test
	public void thatArePhosphorylatedAndLocatedInTheCytoplasm() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q1.sparql");
		//
		// execute query
		String acs=getQueryMetaAc(q);
		int count=getQueryMetaCount(q);
		
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        
        //
        // validate result
		List<String> uri=getLiterals(rs);
        assertTrue( rs.getRowNumber()>=count);
        for(String ac:acs.split(","))
        	assertTrue(uri.contains(ac));      
	}		

	
	
	/**
	 * Q2 that are located both in the cytoplasm and in the nucleus 
	 * @throws Exception 
	 */
	@Test
	public void thatAreLocatedBothInTheCytoplasmAndInTheNucleus() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q2.sparql");
		//
		// execute query
		String acs=getQueryMetaAc(q);
		int count=getQueryMetaCount(q);
		
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        
        //
        // validate result
		List<String> uri=getLiterals(rs);
        assertTrue( rs.getRowNumber()>=count);
        for(String ac:acs.split(","))
        	assertTrue(uri.contains(ac));

                
	}	
	
	/**
	 * Q5 located in mitochondrion and that lack a transit peptide 
	 * @throws Exception 
	 */
	@Test
	public void locatedInMitochondrionAndLackATransitPeptide() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q5.sparql");
		//
		// execute query
		String acs=getQueryMetaAc(q);
		int count=getQueryMetaCount(q);
		
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        
        //
        // validate result
		List<String> uri=getLiterals(rs);
        assertTrue( rs.getRowNumber()>=count);
        for(String ac:acs.split(","))
        	assertTrue(uri.contains(ac));           
	}	
			
	
	/**
	 * Q6 whose genes are on chromosome 2 and linked with a disease 
	 * @throws Exception 
	 */
	@Test
	public void whoseGenesAreOnChromosome2AndLinkedWithADisease() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q6.sparql");
		//
		// execute query
		String acs=getQueryMetaAc(q);
		int count=getQueryMetaCount(q);
		
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        
        //
        // validate result
		List<String> uri=getLiterals(rs);
        assertTrue( rs.getRowNumber()>=count);
        for(String ac:acs.split(","))
        	assertTrue(uri.contains(ac));       
	}		
	
	/**
	 * Q7 linked to diseases that are associated with cardiovascular aspects 
	 * @throws Exception 
	 */
	@Test
	public void linkedToDiseasesThatAreAssociatedWithCardiovascularAspects() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q7.sparql");
		//
		// execute query
		String acs=getQueryMetaAc(q);
		int count=getQueryMetaCount(q);
		
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        
        //
        // validate result
		List<String> uri=getLiterals(rs);
        assertTrue( rs.getRowNumber()>=count);
        for(String ac:acs.split(","))
        	assertTrue(uri.contains(ac));       
	}		
	
	/**
	 * Q8 whose genes are x bp away from the location of the gene of protein Y 
	 * @throws Exception 
	 */
	@Test
	public void whoseGenesAreXbpAwayFromTheLocationOfGeneOfProteinY() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q8.sparql");
		//
		// execute query
		String acs=getQueryMetaAc(q);
		int count=getQueryMetaCount(q);
		
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        
        //
        // validate result
		List<String> uri=getLiterals(rs);
        assertTrue( rs.getRowNumber()>=count);
        for(String ac:acs.split(","))
        	assertTrue(uri.contains(ac));     
	}	

	/**
	 * Q47 with a gene name CLDN* 
	 * @throws Exception 
	 */
	@Test
	public void withAgeneNameCLDNstar() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q47.sparql");
		//
		// execute query
		String acs=getQueryMetaAc(q);
		int count=getQueryMetaCount(q);
		
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        
        //
        // validate result
		List<String> uri=getLiterals(rs);
        assertTrue( rs.getRowNumber()>=count);
        for(String ac:acs.split(","))
        	assertTrue(uri.contains(ac));       
	}	
	
	/**
	 * Q68 with protein existence PE=2 (transcript level) 
	 * @throws Exception 
	 */
	@Test
	public void withProteinExistencePE2() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q68.sparql");
		//
		// execute query
		String acs=getQueryMetaAc(q);
		int count=getQueryMetaCount(q);
		
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        
        //
        // validate result
		List<String> uri=getLiterals(rs);
        assertTrue( rs.getRowNumber()>=count);
        for(String ac:acs.split(","))
        	assertTrue(uri.contains(ac));       
	}	
			
	
	/**
	 * Q22 Proteins with no function annotated 
	 * @throws Exception 
	 */
	@Test
	public void proteinWithNoFunction() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q22.sparql");
		//
		// execute query
		String acs=getQueryMetaAc(q);
		int count=getQueryMetaCount(q);
		
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        
        //
        // validate result
		List<String> uri=getLiterals(rs);
        assertTrue( rs.getRowNumber()>=count);
        for(String ac:acs.split(","))
        	assertTrue(uri.contains(ac));              
	}	

	/**
	 * Q31 with >=10 "splice" isoforms
	 */
	@Test
	public void protein10SpliceIsoforms() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q31.sparql");
		//
		// execute query
		String acs=getQueryMetaAc(q);
		int count=getQueryMetaCount(q);
		
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        
        //
        // validate result
		List<String> uri=getLiterals(rs);
        assertTrue( rs.getRowNumber()>=count);
        for(String ac:acs.split(","))
        	assertTrue(uri.contains(ac));            
	}
	
	/**
	 * Q32 with a coiled coil region and involved in transcription but does not contain a bZIP domain
	 * uniprot=database:(type:nextprot) AND keyword:"Transcription [KW-0804]" AND annotation:(type:coiled) NOT annotation:(type:"positional domain" bzip)
	 */
	@Test
	public void withCoiledCoilAndInvolvedInTranscriptionButWithoutAbZipDomain() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q32.sparql");
		//
		// execute query
		String acs=getQueryMetaAc(q);
		int count=getQueryMetaCount(q);
		
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        
        //
        // validate result
		List<String> uri=getLiterals(rs);
        assertTrue( rs.getRowNumber()>=count);
        for(String ac:acs.split(","))
        	assertTrue(uri.contains(ac));           
	}	
	
	/**
	 * Q64 which are enzymes with an incomplete EC number 
	 * @throws Exception 
	 */
	@Test
	public void whichAreEnzymesWithAnIncompleteEC() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q64.sparql");
		//
		// execute query
		String acs=getQueryMetaAc(q);
		int count=getQueryMetaCount(q);
		
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        
        //
        // validate result
		List<String> uri=getLiterals(rs);
        assertTrue( rs.getRowNumber()>=count);
        for(String ac:acs.split(","))
        	assertTrue(uri.contains(ac));             
	}		
	/**
	 * Q65 Proteins with >1 catalytic activity 
	 * @throws Exception 
	 */
	@Test
	public void proteinWithCatalitycActivity() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q65.sparql");
		//
		// execute query
		String acs=getQueryMetaAc(q);
		int count=getQueryMetaCount(q);
		
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        
        //
        // validate result
		List<String> uri=getLiterals(rs);
        assertTrue( rs.getRowNumber()>=count);
        for(String ac:acs.split(","))
        	assertTrue(uri.contains(ac));             
	}	
	
	/**
	 * Q73 Proteins with no domain 
	 * @throws Exception 
	 */
	@Test
	public void proteinWithNoDomain() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q73.sparql");
		//
		// execute query
		String acs=getQueryMetaAc(q);
		int count=getQueryMetaCount(q);
		
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
        
        //
        // validate result
		List<String> uri=getLiterals(rs);
        assertTrue( rs.getRowNumber()>=count);
        for(String ac:acs.split(","))
        	assertTrue(uri.contains(ac));             
	}		
}
