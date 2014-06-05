package evaletolab.rdf.sab;

import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

import evaletolab.rdf.Expression;
import evaletolab.rdf.Features;
import evaletolab.rdf.General;
import evaletolab.rdf.Interaction;
import evaletolab.rdf._3Dstructure;

@RunWith(Categories.class)  
@IncludeCategory(SABTest.class)  
@SuiteClasses({ General.class, Features.class, Expression.class, _3Dstructure.class, Interaction.class })  
public class SelectedForSAB {

}
