package ch.evaleto.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import ch.evaleto.tool.MaxIndexToModel;

import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.sparql.ARQInternalErrorException;
import com.hp.hpl.jena.sparql.core.Var;
import com.hp.hpl.jena.sparql.engine.ExecutionContext;
import com.hp.hpl.jena.sparql.engine.QueryIterator;
import com.hp.hpl.jena.sparql.engine.binding.Binding;
import com.hp.hpl.jena.sparql.expr.ExprEvalException;
import com.hp.hpl.jena.sparql.pfunction.PropFuncArg;
import com.hp.hpl.jena.sparql.pfunction.library.ListBaseList;
import com.hp.hpl.jena.sparql.util.IterLib;
import com.hp.hpl.jena.sparql.util.graph.GNode;

/** 
 * List membership with index : property function to access list using ?pep
 * Usage: ?list arq:maxindex (?pep ?option1 ?option2) 
 */
public class MaxIndex extends ListBaseList{

	MaxIndexToModel maxindex=new MaxIndexToModel();

	
	private QueryIterator _maxindex(Node pepNode, Binding binding, Var listVar, Node predicate, List<Node> objectArgs, ExecutionContext execCxt){
		final List<Node> x=new ArrayList<Node>();
		Model model;
		try {
			model = maxindex.getModelFromAA(pepNode.getLiteralValue().toString());
			NodeIterator itr = model.listObjects();
			while(itr.hasNext()) {
				Node node = itr.next().asNode();
				System.out.print(" node "+node);
				x.add(node);
			}		
	     
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return super.allLists(binding,x, listVar, predicate, new PropFuncArg(objectArgs, null), execCxt) ;			
	}
	
	@Override
	protected QueryIterator execObjectList(Binding binding, Var listVar, Node predicate, List<Node> objectArgs, ExecutionContext execCxt) {
		//check if listVar is a Var (vs list)
        //if ( Var.isVar(listVar) )
        //     throw new ARQInternalErrorException("maxindex: Subject is a variable") ;		
		if ( objectArgs.size() == 0 )
	            throw new ExprEvalException("maxindex : almost one peptide must be given") ; 	
		Node pepNode = objectArgs.get(0) ;
		for (int i=1;i<objectArgs.size();i++){
		//	System.out.println("arq:maxindex( "+objectArgs.get(i)+" )");
		}
        //  Node optionNode = objectArgs.get(i) ;
		//  if(Var.isVar(optionNode)
        
		
		
		final List<Node> x=new ArrayList<Node>();
		Model model;
		try {
			model = maxindex.getModelFromAA(pepNode.getLiteralValue().toString());
			StmtIterator itr = model.listStatements();
			while(itr.hasNext()) {
				Triple node = itr.next().asTriple();
				x.add(node.getSubject());
			}		
		} catch (IOException e) {
			e.printStackTrace();
		}
        return super.allLists(binding,x, listVar, predicate, new PropFuncArg(objectArgs, null), execCxt) ;	
    }

	@Override
	protected QueryIterator execOneList(Binding binding, Node listNode, Node predicate, List<Node> objectArgs, ExecutionContext execCxt) {
        Graph graph = execCxt.getActiveGraph() ;		
        GNode gnode=new GNode(graph, listNode);

        
        //System.out.println("Hello world list: "+(gnode));
		//System.out.println("             obj: "+objectArgs);
		//System.out.println("             grp: "+graph);
		
		
		// check if listVar is a Var (vs list)
		if ( Var.isVar(listNode) )
             throw new ARQInternalErrorException("maxindex: Subject is a variable") ;
        
		if ( objectArgs.size() == 0 )
            throw new ExprEvalException("maxindex : almost one peptide must be given: maxindex('EED')") ; 	
	
		Node pepNode = objectArgs.get(0) ;
		for (int i=1;i<objectArgs.size();i++){
		//	System.out.println("arq:maxindex( "+objectArgs.get(i)+" )");
		}		
		return IterLib.result(binding, execCxt);
		//return IterLib.noResults(execCxt);
	}
	

}
