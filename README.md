Study of protein queries with spring-mvc and jena 
=================================================

This project will help to build RDF schema by iteration. The schema creation mainly focues on the user queries. 
It demonstrates the use of spring 3.2 Java Servlet container initialization, with Jena(RDF) and Sorl(Lucene) ready to test use cases.

###Use case for expression
* Proteins that are not highly expressed in liver at embrion stage
    * graph: *Proteins* **notHighlyExpressed**   **in** *liver* **withExperimentDesciption** **atStage** *embrion*.
* Proteins that are expressed in liver and involved in transport
* Proteins that are expressed   in liver and involved in transport 
* Proteins >=1000 amino acids and located in nucleus and expression in nervous system
* Proteins highly expressed at IHC level in heart
* Proteins highly expressed in* brain but not expressed in* testis
* Proteins which are expressed in brain according to IHC but not expressed in brain according to microarray
* Simple join with aggregate group field 
* Proteins whose genes are on chromosome N that are expressed only a single tissue/organ
* Proteins which are expressed in liver according to IHC data but not found in HUPO liver proteome set
* **3 recursive/deeph path**
* Proteins with a PDZ domain that interact with at least 1 protein which is expressed in brain

###Use case for general annotation
###Use case for positional annotation
###Use case for PTM queries


It is compatible with tomcat and jetty maven plugins.

Use
    mvn tomcat7:run
or
    mvn jetty:run

Some sample controller (for SPARQL query provider and jena test) for proteins Expression are also provided.

