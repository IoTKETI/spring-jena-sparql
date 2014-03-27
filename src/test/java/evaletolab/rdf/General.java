package evaletolab.rdf;

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
import evaletolab.tool.FileUtil;

/**
 * Use case for features queries
 * Q68 with protein evidence PE=2 (transcript level)
 * Q22 Proteins with no function annotated
 * Q64 which are enzymes with an incomplete EC number
 * Q65 Proteins with >1 catalytic activity 
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
	 * Q68 with protein existence PE=2 (transcript level) 
	 * @throws Exception 
	 */
	@Test
	public void withProteinExistencePE2() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q68.sparql");
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
	}	
			
	
	/**
	 * Q22 Proteins with no function annotated 
	 * @throws Exception 
	 */
	@Test
	public void proteinWithNoFunction() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q22.sparql");
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
	}	

	/**
	 * Q64 which are enzymes with an incomplete EC number 
	 * @throws Exception 
	 */
	@Test
	public void whichAreEnzymesWithAnIncompleteEC() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q64.sparql");
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
	}		
	/**
	 * Q65 Proteins with >1 catalytic activity 
	 * @throws Exception 
	 */
	@Test
	public void proteinWithCatalitycActivity() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q65.sparql");
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
	}	
	
	/**
	 * Q73 Proteins with no domain 
	 * @throws Exception 
	 */
	@Test
	public void proteinWithNoDomain() throws Exception{
		String q=FileUtil.getResourceAsString("sparql/Q73.sparql");
		QueryExecution qe = createQueryExecution(q);
        ResultSet rs=qe.execSelect();
	}		
}
