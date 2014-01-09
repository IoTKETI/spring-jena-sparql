package ch.evaletolab.controller;

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


import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;

import ch.evaleto.service.NextprotFunctionRegistry;

@Controller
public class SparqlController {
	
	
//	static NextprotFunctionRegistry registry=new NextprotFunctionRegistry();
	
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {	
		return "/spaql";
	}
	
//	
//	@RequestMapping(value = "/sparql", method = RequestMethod.GET, produces="application/json")
//    public @ResponseBody String spaql(HttpServletRequest request, HttpServletResponse response,
//    		@RequestParam(value="query", required=false) String query) {
//		response.setHeader("Accept", "application/sparql-results+xml");
//		Enumeration<String> e=request.getAttributeNames();
//		while(e.hasMoreElements()){
//			String name=e.nextElement();
//			System.out.println(name+" - "+request.getAttribute(name));
//		}
//		
//		System.out.println("INPUT -------------");
//		System.out.println(query);
//		System.out.println("HEADERs -------------");
//		Enumeration<String> h=request.getHeaderNames();
//		while(h.hasMoreElements()){
//			String name=h.nextElement();
//			System.out.println(name+" - "+request.getHeaders(name));
//		}
//
//		System.out.println("OUTPUT -------------");
//		
//		Model rdf = registry.getDefault();
//		ResultSet rs =QueryExecutionFactory.create(query, rdf).execSelect();
//
//		
//		return registry.convertResultSetToJSON(rs);
//	}
//	
	
	

}
