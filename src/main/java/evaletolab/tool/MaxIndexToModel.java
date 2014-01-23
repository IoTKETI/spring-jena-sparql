package evaletolab.tool;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;


import static us.monoid.web.Resty.*;
import us.monoid.web.Resty;
import us.monoid.web.TextResource;

public class MaxIndexToModel {
    static String Uniprot ="http://purl.uniprot.org/core/";
	static String EntryURI    = "http://rdftest:2020/resource/nextprot/proteins/NX_";

	//Promise<WS.Response> homePage = WS.url("http://mysite.com").get();
	public Model getModelFromAA(String aa) throws IOException{
		Model model = ModelFactory.createDefaultModel();
		
		//
		// list entries for this AA
		long time=System.currentTimeMillis();
		String body=new Resty().text("http://maxindex.isb-sib.ch/cgi-bin/maxindex", 
					form(data("pep",aa))
		).toString();
		
		
		//Promise<WS.Response> promise = WS.url("http://maxindex.isb-sib.ch/cgi-bin/maxindex").setQueryParameter("pep", aa).get();
		List<String> acs=new LinkedList<String>(Arrays.asList(body.split("<br>")));
		acs.remove(0);
		if(acs.size()>1)acs.remove(acs.size()-1);
		for(String ac: acs){
			Resource protein = model.createResource(EntryURI+ac);
			Resource TypeProtein = model.createResource(Uniprot+"Protein");
			model.add(protein, RDF.type, TypeProtein);
		}
		System.out.println("sib:maxindex: "+(System.currentTimeMillis()-time)+" [ms] count("+model.size()+")");
		
		return model;
	}
	
	
	    
}
