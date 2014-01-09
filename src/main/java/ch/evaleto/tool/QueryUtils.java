package ch.evaleto.tool;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;



import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;


/** 
 * Utilities for executing queries and working with query results. 
 * 
 */

public class QueryUtils {
    
    private static final Log log = LogFactory.getLog(QueryUtils.class);
    
    private QueryUtils() { }
    
  
    public static JsonNode merge( JsonNode mainNode, JsonNode updateNode) {

    	if(mainNode==null)
    		return updateNode;
    	
        Iterator<String> fieldNames = updateNode.getFieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            
        	if (mainNode.has(fieldName) && mainNode.get(fieldName).isArray()){
        		ArrayNode a=((ArrayNode)mainNode.get(fieldName));
        		if (!jsonNodeContains(a,updateNode.get(fieldName)))
        			a.add(updateNode.get(fieldName));
        		continue;
        	}
        	if (!mainNode.has(fieldName) || !mainNode.get(fieldName).isArray()){
        		ArrayNode e=JsonNodeFactory.instance.arrayNode();
        		e.add(updateNode.get(fieldName));
        		((ObjectNode) mainNode).put(fieldName,(JsonNode)e);        		
        	}
        }

        return mainNode;
    }    
    
	    
	private static boolean jsonNodeContains(ArrayNode a, Object o){
		if (a==null || o==null) return false;
		for (JsonNode v:a){
			if (v.equals(o))  return true;
		}
		return false;
	}
	 
	
    public static Map<String,Object> querySolutionToObjectValueMap( QuerySolution soln){
        Map<String,Object> map = new HashMap<String,Object>();
        Iterator<String> varNames = soln.varNames();
        while(varNames.hasNext()){
            String varName = varNames.next();
            Object value = nodeToObject( soln.get(varName));
            log.debug("Adding " + varName + " : " + value + " to query solution data.");            
            map.put(varName, value);
        }
        return map;
    }
 
    public static Map<String,String> querySolutionToStringValueMap( QuerySolution soln ){
        Map<String,String> map = new HashMap<String,String>();
        Iterator<String> varNames = soln.varNames();
        while(varNames.hasNext()){
            String varName = varNames.next();
            String value = nodeToString( soln.get(varName));
            log.debug("Adding " + varName + " : " + value + " to query solution data.");
            map.put(varName, value);           
        }
        return map;
    }
    
    public static Object nodeToObject( RDFNode node ){
        if( node == null ){
            return "";
        }else if( node.isLiteral() ){
            Literal literal = node.asLiteral();
            return literal.getValue();
        }else if( node.isURIResource() ){
            Resource resource = node.asResource();
            return resource.getURI();
        }else if( node.isAnon() ){  
            Resource resource = node.asResource();
            return resource.getId().getLabelString(); //get b-node id
        }else{
            return "";
        }
    }

    public static String nodeToString( RDFNode node ){
        if( node == null ){
            return "";
        }else if( node.isLiteral() ){
            Literal literal = node.asLiteral();
            return literal.getLexicalForm();
        }else if( node.isURIResource() ){
            Resource resource = node.asResource();
            
            return resource.getURI();
        }else if( node.isAnon() ){  
            Resource resource = node.asResource();
            return resource.getId().getLabelString(); //get b-node id
        }else{
            return "";
        }
    }
    
    /** Manually replace query variables with uris when prebinding causes the query to fail, probably
     * due to a Jena bug.
     */
    public static String subUrisForQueryVars(String queryString, Map<String, String> varsToUris) {
        
        for (String var : varsToUris.keySet()) {
           queryString = subUriForQueryVar(queryString, var, varsToUris.get(var));
        }
        return queryString;
    }

    /** Manually replace a query variable with a uri when prebinding causes the query to fail, probably
     * due to a Jena bug.
     */
    public static String subUriForQueryVar(String queryString, String varName, String uri) {
        return queryString.replaceAll("\\?" + varName + "\\b", "<" + uri + ">");
    }
    
    /**Replace one variable name with another**/
    public static String replaceQueryVar(String queryString, String varName, String newVarName) {
        return queryString.replaceAll("\\?" + varName + "\\b", "?" + newVarName);
    }
    
    

}
