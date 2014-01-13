Study of protein queries with spring-mvc and jena 
=================================================

This project will help to build an RDF schema by iteration and tests. The schema creation mainly focues on the user queries. 
It demonstrates the use of spring 3.2 Java Servlet container initialization, with Jena(RDF) and Sorl(Lucene) together ready to tests our use cases.

###Use case for expression
* Proteins that are not highly expressed in liver at embrion stage
    * graph: *Proteins* **notHighlyExpressed**   **in** *liver* **withExperimentDesciption** **atStage** *embrion*.
* Proteins that are expressed in liver and involved in transport
   * graph: Proteins expressed in liver and involved in transport. 
* Proteins >=1000 amino acids and located in nucleus and expression in nervous system
   * graph: Proteins length(>= 1000) and located in nucleus and expressed in nervous system.  
* Proteins highly expressed at IHC level in heart
   * graph: Proteins notHighlyExpressed in liver withExperimentDesciption atLevel IHC;in heart.
* Proteins highly expressed in* brain but not expressed in* testis
   * graph: Proteins highlyExpressed in(+) brain and notExpressed in(+) testis
* Proteins which are expressed in brain according to IHC but not expressed in brain according to microarray
   * graph: Proteins expressed in(+) brain withExperimentDesciption atLevel IHC and 
	proteins notExpressed in(+) brain withExperimentDesciption atLevel microarray
* Proteins whose genes are on chromosome N that are expressed only a single tissue/organ
   * graph: Proteins gene chromosome N and COUNT( expressed in tissue)==1 
* Proteins which are expressed in liver according to IHC data but not found in HUPO liver proteome set
   * graph: Proteins expressed in liver withExperimentDesciption atLevel IHC and not 
	proteins peptide withExperimentDesciption assignedBy HUPO liver proteome  
* **3 recursive/deeph path**
* Proteins with a PDZ domain that interact with at least 1 protein which is expressed in brain
   * graph: Proteins domain in PDZ interactWith proteins expressed in brain

###Use case for general annotation
###Use case for positional annotation
###Use case for PTM queries


It is compatible with tomcat and jetty maven plugins.

Use
    mvn tomcat7:run
or
    mvn jetty:run

Some sample controller (for SPARQL query provider and jena test) for proteins Expression are also provided.

