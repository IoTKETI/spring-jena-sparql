package ch.evaleto.service;


import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.sparql.function.FunctionRegistry;
import com.hp.hpl.jena.sparql.pfunction.PropertyFunctionRegistry;

public class NextprotFunctionRegistry {
	 /** <p>The RDF model that holds the vocabulary terms</p> */
    private static Model m_model = ModelFactory.createDefaultModel();
    
    /** <p>The namespace of the vocabulary as a string</p> */
    public static final String NS = "http://nextprot.org/arq#";
    
    /** <p>The namespace of the vocabulary as a string</p>
* @see #NS */
    public static String getURI() {return NS;}
    
    /** <p>The namespace of the vocabulary as a resource</p> */
    public static final Resource NAMESPACE = m_model.createResource( NS );
    
    public static final Property maxindex = m_model.createProperty( "http://nextprot.org/arq#maxindex" );	
    public static final Property maxindexpf = m_model.createProperty( "http://nextprot.org/arq#maxindexpf" );	
	
	public NextprotFunctionRegistry() {
//		PropertyFunctionRegistry registry=PropertyFunctionRegistry.standardRegistry();
//		registry.put(maxindex.getURI(), MaxIndex.class);
//		FunctionRegistry.get().put(maxindex.getURI(), MaxIndex.class);
		PropertyFunctionRegistry.get().put(maxindex.getURI(), MaxIndex.class);
		PropertyFunctionRegistry.get().put(maxindexpf.getURI(), MaxIndexPf.class);
	}
	
	public Model getDefault(){
		return m_model;
	}
	
	public String convertResultSetToJSON(ResultSet resultSet) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ResultSetFormatter.outputAsJSON(baos, resultSet);
		// resultSet.reset();
		try {
			return baos.toString("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// should never happen as UTF-8 is supported
			throw new Error(e);
		}
	}

	public String convertResultSetToXML(ResultSet resultSet) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ResultSetFormatter.outputAsXML(baos, resultSet);
		// resultSet.reset();
		try {
			return baos.toString("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// should never happen as UTF-8 is supported
			throw new Error(e);
		}
	}	
}
