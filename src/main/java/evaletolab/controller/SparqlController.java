package evaletolab.controller;

import static us.monoid.web.Resty.data;
import static us.monoid.web.Resty.*;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import us.monoid.web.Resty;
import us.monoid.web.TextResource;
import virtuoso.jena.driver.VirtGraph;



import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

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
			qe = createQueryExecution(q); //"application/sparql-results+json"
			
			
			ResultSet rs = qe.execSelect();
			result=registry.convertResultSetToJSON(rs);
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
		
		String q=FileUtil.getResourceAsString("sparql/entry.sparql").replaceAll("NX_VALUE", ac);;

		if (output!=null && output.equalsIgnoreCase("json")){
			response.setHeader("Accept", "application/sparql-results+json");
		}
		return sparqlSelect(getPrefix()+q);
	}

	
	@RequestMapping(value = "/uniprot", method = RequestMethod.GET)
    public @ResponseBody List<String> uniprot(HttpServletRequest request, HttpServletResponse response,
    		@RequestParam(value="query", required=false) String query) throws Exception {

		System.out.println("uniprot query="+query);
		TextResource rs=new Resty().text("http://www.uniprot.org/uniprot", 
				form(data("query",enc(query)),data("format","list"))
		);
		rs.withHeader("Accept", "text/plain");
		String body=rs.toString();	
		
		List<String> acs=new LinkedList<String>(Arrays.asList(body.split("\n")));
		return acs;
	}
	
	
	@RequestMapping(value = "/sparql",  produces="application/json")
    public @ResponseBody String spaql(HttpServletRequest request, HttpServletResponse response,
    		@RequestParam(value="query", required=false) String query, 
    		@RequestParam(value="outout", required=false) String output,
    		@RequestParam(value="uniprot", required=false) String uniprot) {
		if (output!=null && output.equalsIgnoreCase("json")){
			response.setHeader("Accept", "application/sparql-results+json");			
		}
		if (output!=null && output.equalsIgnoreCase("json")){
			response.setHeader("Accept", "application/sparql-results+xml");			
		}
		
		Enumeration<String> e=request.getAttributeNames();
		while(e.hasMoreElements()){
			String name=e.nextElement();
			System.out.println(name+" - "+request.getAttribute(name));
		}
		
//		System.out.println(query);
//		Enumeration<String> h=request.getHeaderNames();
//		while(h.hasMoreElements()){
//			String name=h.nextElement();
//			System.out.println(name+" - "+request.getHeaders(name));
//		}

		
//		Model rdf = registry.getDefault();
//		ResultSet rs =QueryExecutionFactory.create(query, rdf).execSelect();

		
		return sparqlSelect(query);
	}
	
	
	
	
	

}
