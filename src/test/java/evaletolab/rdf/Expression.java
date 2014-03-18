package evaletolab.rdf;

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
 * Use case for expression queries
 * 
 * QX  Proteins that are not highly expressed in liver at embrion stage
 * Q4  highly expressed in brain but not expressed in testis
 * Q11 that are expressed in liver and involved in transport 
 * Q15 with a PDZ domain that interact with at least 1 protein which is expressed in brain 
 * Q17 >=1000 amino acids and located in nucleus and expression in nervous system 
 * Q20 with >=2 HPA antibodies whose genes are located on chromosome 21 and that are highly expressed at IHC level in heart
 * Q50 which are expressed in brain according to IHC but not expressed in brain according to microarray
 * Q77 which are expressed in liver according to IHC data but not found in HUPO liver proteome set
 * Q83 whose genes are on chromosome N that are expressed only a single tissue/organ
 * Q89 which are located in nucleus and expressed in brain and only have orthologs/paralogs in primates
 * 
 * @author evaleto
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
public class Expression {
	
	Model m;
	InfModel rdfs;
	
	@Before
	public void setup() {
		m=ModelFactory.createDefaultModel();
		m.read("owl/np.ttl");
		rdfs= ModelFactory.createRDFSModel(m);
	}
	

	
	/**
	 * Proteins that are not highly expressed in liver at embrion stage (count=11'272)
	 * --> hierarchical Liver (TS-0564) with 17848 entries
	 *     NX_P61604,NX_Q15029,NX_Q07973
	 * --> not highly expressed =>  :expressionLevel/owl:disjointWith :High.
	 * @throws Exception 
	 * 
	 */
	@Test
	public void notHighlyExpressedAtEmbrionStage() throws Exception{
		//
		// specific query
		String q=FileUtil.getResourceAsString("sparql/notHighlyExpressedAtEmbrionStage.sparql");

		//
		// execute query
		Query query = QueryFactory.create(q);
		long start=System.currentTimeMillis();
        QueryExecution qe = QueryExecutionFactory.create(query,rdfs);
        ResultSet rs = qe.execSelect();
        //
        // check ResultSet content
        System.out.println("notHiglyExpressed "+(System.currentTimeMillis()-start)+" ms");
        ResultSetFormatter.out(System.out, rs, query);	
	}	
	
	
	/**
	 * Q11, Proteins that are expressed in liver and involved in transport
	 * --> hierarchical Liver (TS-0564) with 17848 entries
	 *     NX_P61604,NX_Q15029,NX_Q07973
	 * --> :classifiedWith Transport (KW-0813) with 1859 entries
	 *     NX_Q9H0U6, NX_P08195, NX_P46098 
	 * @throws Exception 
	 */
	@Test
	public void expressedInLiverAndInvolvedInTransport() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q11-expressedInLiverAndInvolvedInTransport.sparql");
		System.out.println(q);
	}
	
	/**
	 * Q17, Proteins >=1000 amino acids and located in nucleus and expression in nervous system
	 *  --> hierarchical Terms for Nervous System
	 * @throws Exception 
	 */
	@Test
	public void gt1000aaAndLocatedInNucleusAndExpressedInNervousSystem() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q17-gt1000aaAndLocatedInNucleusAndExpressedInNervousSystem.sparql");
		System.out.println(q);
	}		
	
	/**
	 * Q4, Proteins highly expressed in brain but not expressed in testis
	 *  --> hierarchical Terms for Testis (TS-1030)
	 *  --> Brain 18202 entries, NX_P61604, NX_Q15029, NX_Q07973
	 *  --> Testis TS-1030  18117 entries
	 * @throws Exception 
	 */
	@Test
	public void highlyExpressedInBrainButNotInTestis() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q4-highlyExpressedInBrainButNotInTestis.sparql");
		System.out.println(q);
	}			
	
	/**
	 * Q50, Proteins which are expressed in brain according to IHC 
	 *      but not expressed in brain according to microarray
	 * @throws Exception 
	 */
	@Test
	public void expressedInBrainAccordingIHCButNotExpressedInBrainAccordingMicroarray() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q50-expressedInBrainAccordingIHCButNotExpressedInBrainAccordingMicroarray.sparql");
		System.out.println(q);
	}			
	
	/**
	 * Q83, Proteins whose genes are on chromosome N that are expressed only a single tissue/organ
	 * @throws Exception 
	 */
	@Test
	public void expressedOnASingleTissue() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q83-expressedOnASingleTissue.sparql");
		System.out.println(q);
	}		
	
	/**
	 * Q77, Proteins which are expressed in liver according to IHC data but not found in HUPO liver proteome set
	 * @throws Exception 
	 */
	@Test
	public void expressedInLiverAccordingIHCButNotInHUPOLiverProteom() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q77-expressedInLiverAccordingIHCButNotInHUPOLiverProteom.sparql");
		System.out.println(q);
	}	
	
	/**
	 * Q15, Proteins with a PDZ domain that interact with at least 1 protein which is expressed in brain
	 *  --> hierarchical terms for Brain
	 * @throws Exception 
	 */
	@Test
	public void PDZdomainthatInteractWithProteinExpresssedInBrain() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q15-PDZdomainthatInteractWithProteinExpresssedInBrain.sparql");
		System.out.println(q);

	}	
	
	/**
	 * Q20, Proteins with >=2 HPA antibodies whose genes are located on chromosome 21 and that are 
	 *      highly expressed at IHC level in heart
	 * @throws Exception 
	 */
	@Test
	public void HPAOnChromosome21highlyExpresssedInHeartAtIHCLevel() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q20-HPAOnChromosome21highlyExpresssedInHeartAtIHCLevel.sparql");
		System.out.println(q);

	}	
}
