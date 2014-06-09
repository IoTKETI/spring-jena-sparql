package evaletolab.rdf.sab;

import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

import evaletolab.rdf.Evidences;
import evaletolab.rdf.Expression;
import evaletolab.rdf.Features;
import evaletolab.rdf.Federated;
import evaletolab.rdf.Gene;
import evaletolab.rdf.General;
import evaletolab.rdf.Interaction;
import evaletolab.rdf.PTM;
import evaletolab.rdf.Peptide;
import evaletolab.rdf.Xref;
import evaletolab.rdf._3Dstructure;

@RunWith(Categories.class)  
@IncludeCategory(SABTest.class)  
@SuiteClasses({  _3Dstructure.class, Evidences.class, Expression.class, Features.class, Federated.class, Gene.class, General.class, Interaction.class, Peptide.class, PTM.class, Xref.class})  
public class SelectedForSAB {

}
