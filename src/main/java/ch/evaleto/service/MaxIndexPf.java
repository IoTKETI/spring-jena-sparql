package ch.evaleto.service;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.sparql.engine.ExecutionContext;
import com.hp.hpl.jena.sparql.engine.QueryIterator;
import com.hp.hpl.jena.sparql.engine.binding.Binding;
import com.hp.hpl.jena.sparql.pfunction.PropFuncArg;
import com.hp.hpl.jena.sparql.pfunction.PropFuncArgType;
import com.hp.hpl.jena.sparql.pfunction.PropertyFunctionEval;
import com.hp.hpl.jena.sparql.util.IterLib;

public class MaxIndexPf extends PropertyFunctionEval {

	public MaxIndexPf() {
		super(PropFuncArgType.PF_ARG_EITHER, PropFuncArgType.PF_ARG_EITHER);
	}

	@Override
	public QueryIterator execEvaluated(Binding binding, PropFuncArg argSubject, Node predicate, PropFuncArg argObject, ExecutionContext execCxt) {
		
		System.out.println("subject: "+argSubject.getArg().getURI()+" -ls "+argSubject.isList());
		System.out.println("object: "+argObject+" -ls "+argObject.isList());
	
	       if ( argSubject.equals(PropFuncArgType.PF_ARG_SINGLE) ){
	            if ( argSubject.isList() )
	                System.out.println(("List arguments (subject) to "+predicate.getURI())); 
	       }
	        
	        if ( argSubject.equals(PropFuncArgType.PF_ARG_LIST) && ! argSubject.isList() )
	        	System.out.println("Single argument, list expected (subject) to "+predicate.getURI()) ;

	        if ( argObject.equals(PropFuncArgType.PF_ARG_SINGLE) && argObject.isList() )
	        {
	            if ( ! argObject.isNode() )
	                // But allow rdf:nil.
	            	System.out.println("List arguments (object) to "+predicate.getURI()) ;
	        }
	        
	        if ( argObject.equals(PropFuncArgType.PF_ARG_LIST) )
	            if ( ! argObject.isList() )
	            	System.out.println("Single argument, list expected (object) to "+predicate.getURI()); 	
		
		return IterLib.noResults(execCxt);
	}

}
