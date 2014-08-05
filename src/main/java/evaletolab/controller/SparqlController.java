package evaletolab.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;

import evaletolab.service.NextprotFunctionRegistry;
import evaletolab.tool.FileUtil;

@Controller
public class SparqlController extends TripleStore{
	@Autowired
	public Properties config;
	
	static String endpoint;
	static Model model;
	static boolean isNative=false;
	
	static NextprotFunctionRegistry registry=new NextprotFunctionRegistry();

	
    public  String sparqlSelect(String q) {
    	open();
    	QueryExecution qe=null;
    	String result;
		try {
			qe = createQueryExecution(q); 
			
			ResultSet rs = qe.execSelect();
			System.out.println("------------"+rs);
			result=registry.convertResultSetToJSON(rs);
			qe.close();
		} 
		catch(Exception e) {
			return (e.getMessage());
		}
		finally {
			if (qe!=null)qe.close();
		}
    	
        return (result);
    }	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {	
		return "/home";
	}
	
	/**
	 * this controller send the set of sparql queries used for integration testing.
	 * @return
	 * @throws Exception  
	 */
	@RequestMapping(value = "/sparql/queries", method = RequestMethod.GET)
    public @ResponseBody List<Map<String,String>> queries() throws Exception {
		List<Map<String,String>> result=new ArrayList<Map<String,String>>();
		Set<String> sparqls =new TreeSet<String>();
		sparqls.addAll(new Reflections("sparql", new ResourcesScanner()).getResources(Pattern.compile("[^/]*\\.sparql")));
		Map<String,String> meta=new HashMap<String, String>();
		for(String q:sparqls){
			String query=FileUtil.getResourceAsString(q);
			if(getMetaInfo(query).get("title")==null){
				continue;
			}
			meta.put("title", getMetaInfo(query).get("title"));
			meta.put("query", query);
			meta.put("tags", getMetaInfo(query).get("tags"));
			result.add(meta);
			meta=new HashMap<String, String>();
		}
		return result;
	}	
	
	@RequestMapping(value = "/entry/{ac}", method = RequestMethod.GET)
    public  String entry(HttpServletRequest request, HttpServletResponse response,
    		@PathVariable("ac") String ac,
    		@RequestParam(value="output", required=false) String output) throws Exception {


		
		return "/entry";
	}
	
	@RequestMapping(value = "/sparql/entry/{ac}", method = RequestMethod.GET)
    public @ResponseBody String entryData(HttpServletRequest request, HttpServletResponse response,
    		@PathVariable("ac") String ac,
    		@RequestParam(value="output", required=false) String output) throws Exception {
		
		String q=FileUtil.getResourceAsString("sparql.services/entry.sparql").replaceAll("NX_VALUE", ac);;

		if (output!=null && output.equalsIgnoreCase("json")){
			response.setHeader("Accept", "application/sparql-results+json");
		}
		return sparqlSelect(getPrefix()+q);
	}

	
	
	@RequestMapping(value = "/sparql",  produces="application/json")
    public @ResponseBody String sparql(HttpServletRequest request, HttpServletResponse response,
    		@RequestParam(value="query", required=false) String query, 
    		@RequestParam(value="output", required=false) String output) {

		// default
		response.setHeader("Accept", "application/sparql-results+json");			
		if (output!=null && output.equalsIgnoreCase("xml")){
			response.setHeader("Accept", "application/sparql-results+xml");			
		}
		
//		Enumeration<String> e=request.getAttributeNames();
//		while(e.hasMoreElements()){
//			String name=e.nextElement();
//			System.out.println(name+" - "+request.getAttribute(name));
//		}
		
//		Enumeration<String> h=request.getHeaderNames();
//		while(h.hasMoreElements()){
//			String name=h.nextElement();
//			System.out.println(name+" - "+request.getHeader(name).toString());
//		}
//		System.out.println("content-type "+request.getContentType());

		
//		Model rdf = registry.getDefault();
//		ResultSet rs =QueryExecutionFactory.create(query, rdf).execSelect();

		
		return sparqlSelect(query);
	}
	
	
	
	
	

}
