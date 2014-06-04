package evaletolab.controller;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Properties;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.ResultSet;

import evaletolab.config.WebConfig;
import evaletolab.tool.FileUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
public class TripleStoreBaseTest extends TripleStore {

	@Autowired
	private Properties config;

	@Before
	public void setup() throws Exception {
		open();
	}

	protected void testSparql(String sparqlFileName) {
		String q = null;
		try {
			q = FileUtil.getResourceAsString("sparql/" + sparqlFileName);
		} catch (Exception e) {
			e.printStackTrace();
			org.junit.Assert.fail();
		}

		// execute query
		String acs = getMetaInfo(q).get("acs");
		int count = getQueryMetaCount(q);

		QueryExecution qe = createQueryExecution(q);
		ResultSet rs = qe.execSelect();

		// validate result
		List<String> uri = getLiterals(rs);
		System.out.println(uri.size() + " results found for " + sparqlFileName + ":");
		for(String u : uri){
			System.out.print(u);
			System.out.print(",");
		}
		assertTrue(rs.getRowNumber() >= count);
		for (String ac : acs.split(","))
			assertTrue(ac, uri.contains(ac.trim()));

	}
}
