package evaletolab.rdf;

import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;
import java.util.Map;
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
/**
 * Use case for intigrity queries
 * - count Entries 
 * - Brain (TS-0095) relevant For 18202 entries (NX_P61604,NX_Q15029,NX_Q07973)
 * - Liver (TS-0564) relevant For 17848 entries (NX_P61604,NX_Q15029,NX_Q07973)
 * - :classifiedWith Transport (KW-0813) is relevant for 1859 entries (NX_Q9H0U6, NX_P08195, NX_P46098)
 * - 
 *  
 * @author evaleto
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
public class TripleStoreTest extends TripleStore{

	@Autowired
	private Properties config;
	
	@Before
	public void setup() throws Exception {
		//
		// open session in triplestore
	}
	
	/**
	 * test query meta
	 */
	@Test
	public void queryMeta_Id_Host_Title_Ac(){
		String q="#id:SPARQL--20140519-1714 endpoint:http://uat-web2:8890/sparql\n" + 
				 "#title:Q1 that are phosphorylated and located in the cytoplasm\n" + 
				 "#ac:A1A4S6,A1KZ92,A1L020\n" + 
				 "select distinct ?entry where { ?entry a :Entry}limit 1";
		Map<String, String> m=getMetaInfo(q);
		assertEquals("id","SPARQL--20140519-1714", m.get("id"));
		assertEquals("endpoint","http://uat-web2:8890/sparql", m.get("endpoint"));
		assertEquals("title","Q1 that are phosphorylated and located in the cytoplasm", m.get("title"));
		assertEquals("acs","A1A4S6,A1KZ92,A1L020", m.get("acs"));
		
	}

	@Test
	public void queryMeta_Id_Host_Title(){
		String q="#id:SPARQL--20140519-1714 endpoint:http://uat-web2:8890/sparql\n" + 
				 "#title:Q1 that are phosphorylated and located in the cytoplasm\n" + 
				 "select distinct ?entry where { ?entry a :Entry}limit 1";
		Map<String, String> m=getMetaInfo(q);
		assertEquals("id","SPARQL--20140519-1714", m.get("id"));
		assertEquals("endpoint","http://uat-web2:8890/sparql", m.get("endpoint"));
		assertEquals("title","Q1 that are phosphorylated and located in the cytoplasm", m.get("title"));
		assertEquals("acs",null, m.get("acs"));
		
	}
	
	@Test
	public void queryMeta_Id_Host_Title_variation1(){
		String q="#id:SPARQL--20140519-1714 endpoint:http://uat-web2:8890/sparql\n" +
				 "\n"+
				 "#title:Q1 that are phosphorylated and located in the cytoplasm\n" + 
				 "select distinct ?entry where { ?entry a :Entry}limit 1";
		Map<String, String> m=getMetaInfo(q);
		assertEquals("id","SPARQL--20140519-1714", m.get("id"));
		assertEquals("endpoint","http://uat-web2:8890/sparql", m.get("endpoint"));
		assertEquals("title","Q1 that are phosphorylated and located in the cytoplasm", m.get("title"));
		assertEquals("acs",null, m.get("acs"));	
		assertEquals("count","0", m.get("count"));	
	}	
	
	@Test
	public void queryMeta_Id_Host_Title_variation2(){
		String q="#id:SPARQL--20140519-1714 endpoint:http://uat-web2:8890/sparql\n" +
				 "\n"+
				 "#ac:A1A4S6,A1KZ92,A1L020 count:4\n" + 
				 "#title:Q1 that are phosphorylated and located in the cytoplasm\n" + 
				 "select distinct ?entry where { ?entry a :Entry}limit 1";
		Map<String, String> m=getMetaInfo(q);
		assertEquals("id","SPARQL--20140519-1714", m.get("id"));
		assertEquals("endpoint","http://uat-web2:8890/sparql", m.get("endpoint"));
		assertEquals("title","Q1 that are phosphorylated and located in the cytoplasm", m.get("title"));
		assertEquals("acs","A1A4S6,A1KZ92,A1L020", m.get("acs"));	
		assertEquals("count","4", m.get("count"));	
	}		

}
