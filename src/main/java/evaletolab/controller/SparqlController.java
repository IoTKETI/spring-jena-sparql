package evaletolab.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;

import evaletolab.service.NextprotFunctionRegistry;

@Controller
public class SparqlController {
	
	static String endpoint="http://cactusprime:8890/sparql";
	static NextprotFunctionRegistry registry=new NextprotFunctionRegistry();
	
    public static String sparqlSelect(String q) {
    	QueryExecution qe=null;
    	String result;
		try {
			qe = QueryExecutionFactory.sparqlService(endpoint,q); //"application/sparql-results+json"
			
			
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
	
	
	@RequestMapping(value = "/sparql",  produces="application/json")
    public @ResponseBody String spaql(HttpServletRequest request, HttpServletResponse response,
    		@RequestParam(value="query", required=false) String query, @RequestParam(value="outout", required=false) String output) {
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
