package evaletolab.controller;

import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import virtuoso.jena.driver.VirtGraph;



import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import evaletolab.service.NextprotFunctionRegistry;

@Controller
public class SparqlController {
	@Autowired
	public Properties config;
	
	static String endpoint;
	static Model model;
	static boolean isNative=false;
	
	static NextprotFunctionRegistry registry=new NextprotFunctionRegistry();
	public void open(){
		if (endpoint!=null || isNative)
			return;
		
		//
		// trying to use virtuoso with the native driver 
		if (config.containsKey("virtuoso.url")){

			
			VirtGraph graph = new VirtGraph("http://nextprot.org/rdf", 
					config.getProperty("virtuoso.url"), 
					config.getProperty("virtuoso.user"), 
					config.getProperty("virtuoso.password")
			);
			model=ModelFactory.createModelForGraph(graph);
			isNative=true;
			return;
		}
		
		//
		// else we use the apsql endpoint (that doesn't support ARQ)
		endpoint=config.getProperty("sparql.endpoint");		
	}	
	
	public QueryExecution createQueryExecution(String query){
		if(isNative){
			Query q = QueryFactory.create(query);
	        return QueryExecutionFactory.create(q,model);			
		}
		return QueryExecutionFactory.sparqlService(endpoint,query);
	}
	
	
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
