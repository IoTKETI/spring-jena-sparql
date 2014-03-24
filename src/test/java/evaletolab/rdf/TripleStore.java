package evaletolab.rdf;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import virtuoso.jena.driver.VirtGraph;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class TripleStore {
	@Autowired
	public Properties config;
	
	private String endpoint;
	private Model model;
	private boolean isNative=false;

	public void open(){

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
	
	public boolean isNative(){
		return isNative;
	}
		
	public Model getModel(){
		return model;
	}
	
	public String getEndpoint(){
		return endpoint;
	}
}
