package evaletolab.tool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Generate {
	String dbname="jdbc:postgresql://crick:5432/np_web_dev?user=postgres&password=postgres";
	Connection db;
	public Generate() throws ClassNotFoundException, SQLException {
		Class.forName( "org.postgresql.Driver" ) ;
		db = DriverManager.getConnection(dbname);	
	}
	

	public void superClassAnnotationMapping() throws SQLException, ClassNotFoundException{
		String owlMapping="#Class\n" + 
				":_ANNOT_TYPE\n" + 
				"      rdf:type owl:Class ;\n" + 
				"      rdfs:comment \"Describe of the _TEXT of a Isoform.\"^^xsd:string ;\n" + 
				"      rdfs:label \"_TEXT\"^^xsd:string ;\n" + 
				"      rdfs:seeAlso \"http://www.nextprot.org/manual/_ANNOT_TYPE\"^^xsd:anyURI ;\n" + 
				"      rdfs:subClassOf :_PARENT;\n" +
				"      owl:disjointWith :_DISTJOIN.\n" + 
				"      .\n\n";
		

		//
		// retrieve all feature type and their direct parent
		String sql=	"    select root.cv_name as parent, root.cv_id as pid, first.cv_name as relation, first.cv_id as rid\n" + 
					"      from nextprot.cv_terms first\n" + 
					"inner join nextprot.cv_term_relationships root_r on (first.cv_id=root_r.subject_id)\n" + 
					"inner join nextprot.cv_terms root on (root_r.object_id=root.cv_id)\n" + 
					"     where root.cv_id in (2)";
		
		
		PreparedStatement stmt=db.prepareStatement(sql);			
		stmt.setFetchSize(1000);
		ResultSet rs = stmt.executeQuery();		
		String distJoin="";String m="";
		while (rs.next()) {
			distJoin+=":"+toCamelCase(rs.getString("relation"), false)+", ";

			m+=owlMapping.replaceAll("_ANNOT_TYPE", toCamelCase(rs.getString("relation"), false))
							   .replaceAll("_TEXT", "describe a "+rs.getString("relation")+" annotation ")
					  		   .replaceAll("_RELATION", toCamelCase(rs.getString("relation"), true))
					  		   .replaceAll("_PARENT", toCamelCase(rs.getString("parent"), false))
							   .replaceAll("_ANNOT_ID", String.valueOf(rs.getShort("rid")));
		}
		System.out.println(m.replace("_DISTJOIN",distJoin)+"\n");
		
	}		
	
	public void classAnnotationMapping() throws SQLException, ClassNotFoundException{
		String owlMapping="#Class\n" + 
				":_ANNOT_TYPE\n" + 
				"      rdf:type owl:Class ;\n" + 
				"      rdfs:comment \"describe of the _TEXT of an Isoform.\"^^xsd:string ;\n" + 
				"      rdfs:label \"_TEXT\"^^xsd:string ;\n" + 
				"      rdfs:seeAlso \"http://www.nextprot.org/manual/_ANNOT_TYPE\"^^xsd:anyURI ;\n" + 
				"      rdfs:subClassOf :_PARENT;\n" + 
				"      .";
		

		//
		// retrieve all feature type and their direct parent
		String sql=	"    select root.cv_name as parent, root.cv_id as pid, first.cv_name as relation, first.cv_id as rid\n" + 
					"      from nextprot.cv_terms first\n" + 
					"inner join nextprot.cv_term_relationships root_r on (first.cv_id=root_r.subject_id)\n" + 
					"inner join nextprot.cv_terms root on (root_r.object_id=root.cv_id)\n" + 
					"     where root.cv_id in (10,11,12,13,14,15,16,17)";
		
		
		PreparedStatement stmt=db.prepareStatement(sql);			
		stmt.setFetchSize(1000);
		ResultSet rs = stmt.executeQuery();		
		while (rs.next()) {

			String m=owlMapping.replaceAll("_ANNOT_TYPE", toCamelCase(rs.getString("relation"), false))
							   .replaceAll("_TEXT", rs.getString("relation"))
					  		   .replaceAll("_RELATION", toCamelCase(rs.getString("relation"), true))
					  		   .replaceAll("_PARENT", toCamelCase(rs.getString("parent"), false))
							   .replaceAll("_ANNOT_ID", String.valueOf(rs.getShort("rid")));
			System.out.println(m+"\n");
		}	  
	}			
	
public void relationAnnotationMapping() throws SQLException, ClassNotFoundException{
	String owlMapping="#\n" + 
			"#Property :_RELATION \n" +
			":has_ANNOT_TYPE\n" +
			"      a rdf:Property, owl:FunctionalProperty;\n" + 
			"      rdfs:subPropertyOf :annotation;\n" + 
			"      rdfs:comment \"describe a _TEXT of annotation\"^^xsd:string ;\n" + 
			"      rdfs:domain :Isoform ;\n" + 
			"      rdfs:range :_ANNOT_TYPE.";
	

	//
	// retrieve all feature type and their direct parent
	String sql=	"    select root.cv_name as parent, root.cv_id as pid, first.cv_name as relation, first.cv_id as rid\n" + 
				"      from nextprot.cv_terms first\n" + 
				"inner join nextprot.cv_term_relationships root_r on (first.cv_id=root_r.subject_id)\n" + 
				"inner join nextprot.cv_terms root on (root_r.object_id=root.cv_id)\n" + 
				"     where root.cv_id in (10,11,12,13,14,15,16,17)";
	
	
	PreparedStatement stmt=db.prepareStatement(sql);			
	stmt.setFetchSize(1000);
	ResultSet rs = stmt.executeQuery();		
	while (rs.next()) {
		String m=owlMapping.replaceAll("_ANNOT_TYPE", toCamelCase(rs.getString("relation"), false))
						   .replaceAll("_TEXT", rs.getString("relation"))
				  		   .replaceAll("_RELATION", toCamelCase(rs.getString("relation"), true))
						   .replaceAll("_ANNOT_ID", String.valueOf(rs.getShort("rid")));
		System.out.println(m+"\n");
	}	  
}	
	
	public static String toCamelCase(final String init, boolean first) {
	    if (init==null)
	        return null;
	    

	    final StringBuilder ret = new StringBuilder(init.length());

	    for (final String word : init.split("[ -]")) {
	    	if (first){
	            ret.append(word.toLowerCase());
	    		first=false;
	    		continue;
	    	}
	        if (!word.isEmpty() && !first) {
	            ret.append(word.substring(0, 1).toUpperCase());
	            ret.append(word.substring(1).toLowerCase());
	        }
	    }

	    return ret.toString();
	}	
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Generate generate=new Generate();
//		generate.superClassAnnotationMapping();
		generate.classAnnotationMapping();
		generate.relationAnnotationMapping();
		
	}
}
