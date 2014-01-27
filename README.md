Study of protein queries with spring-mvc and jena 
=================================================

This project will help to build an RDF schema by iteration and tests. The schema creation mainly focues on the user queries. 
It demonstrates the use of spring 3.2 Java Servlet container initialization, with Jena(RDF) and Sorl(Lucene) together ready to tests our use cases.

* [initial rdf schema](https://github.com/evaletolab/spring-jena-sparql/tree/master/src/main/resources/owl)

###Use case for expression
Proteins that are not highly expressed in liver at embrion stage
> graph: Proteins **notHighlyExpressed**   **in** *liver* **withExperimentDesciption** **atStage** *embrion*.

Proteins that are expressed in liver and involved in transport
> graph: Proteins expressed in liver and involved in transport. 

Proteins >=1000 amino acids and located in nucleus and expression in nervous system
> graph: Proteins length(>= 1000) and located in nucleus and expressed in nervous system.  

Proteins highly expressed at IHC level in heart
> graph: Proteins notHighlyExpressed in liver withExperimentDesciption atLevel IHC;in heart.

Proteins highly expressed in* brain but not expressed in* testis
> graph: Proteins highlyExpressed in(+) brain and notExpressed in(+) testis

Proteins which are expressed in brain according to IHC but not expressed in brain according to microarray
> graph: Proteins expressed in(+) brain withExperimentDesciption atLevel IHC and 
	proteins notExpressed in(+) brain withExperimentDesciption atLevel microarray

Proteins whose genes are on chromosome N that are expressed only a single tissue/organ
> graph: Proteins gene chromosome N and COUNT( expressed in tissue)==1 

Proteins which are expressed in liver according to IHC data but not found in HUPO liver proteome set
> graph: Proteins expressed in liver withExperimentDesciption atLevel IHC and not 
	proteins peptide withExperimentDesciption assignedBy HUPO liver proteome  

* **3 recursive/deeph path**
* Proteins with a PDZ domain that interact with at least 1 protein which is expressed in brain

> graph: Proteins domain in PDZ interactWith proteins expressed in brain

###Use case for sequence annotations
 * - Q3	Proteins with >=2 transmembrane regions 
 * - Q5	Proteins located in mitochondrion and that lack a transit peptide
 * - Q9	Proteins with 3 disulfide bonds and that are not hormones 
 * - Q13 Proteins with a protein kinase domain but no kinase activity 
 * - Q14 Proteins with 2 SH3 domains and 1 SH2 domain 
 * - Q15 Proteins with a PDZ domain that interact with at least 1 protein which is expressed in brain 
 * - Q16 Proteins with a mature chain <= 100 amino acids which are secreted and do not contain cysteines in the mature chain 
 * - Q18 Proteins that are acetylated and methylated and located in the nucleus 
 * - Q19 Proteins contains a signal sequence followed by a extracellular domain containing a "KRKR" motif 
 * * Q22 Proteins with no function annotated
 * * Q27 Proteins with >=1 glycosylation sites reported in PubMed:X or PubMed:Y
 * - Q32 Proteins with a coiled coil region and involved in transcription but does not contain a bZIP domain
 * - Q34 Proteins with >=1 homeobox domain and with >=1 variant in the homeobox domain(s)
 * - Q35 Proteins located in the mitochondrion and which is an enzyme
 * - Q38 Proteins with >=1 selenocysteine in their sequence
 * - Q39 Proteins with >=1 mutagenesis in a position that correspond to an annotated active site
 * - Q40 Proteins that are enzymes and with >=1 mutagenesis that "decrease" or "abolish" activity
 * - Q41 Proteins that are annotated with GO "F" terms prefixed by "Not"
 * - Q48 Proteins with >=1 variants of the type "C->" (Cys to anything else) that are linked to >=1 disease
 * - Q49 Proteins with >=1 variants of the types "A->R" or "R->A"

###Use case for general annotations
###Use case for PTM queries


It is compatible with tomcat and jetty maven plugins.

Use
    mvn tomcat7:run
or
    mvn jetty:run

Some sample controller (for SPARQL query provider and jena test) for proteins Expression are also provided.

