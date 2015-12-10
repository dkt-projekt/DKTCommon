package eu.freme.broker.niftools;

import java.io.IOException;
import java.io.StringWriter;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.RDF;

//import eu.freme.broker.eopennlp.exceptions.ExternalServiceFailedException;

public class NIFReader {

	public static String extractIsString(Model nifModel){
		
		String str = null;
		
        StmtIterator iter = nifModel.listStatements(null, RDF.type, nifModel.getResource("http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#Context"));
        boolean textFound = false;
        //String tmpPrefix = "http://freme-project.eu/#";
        // The first nif:Context with assigned nif:isString will be processed.
        while (!textFound) {
            Resource contextRes = iter.nextStatement().getSubject();
            //tmpPrefix = contextRes.getURI().split("#")[0];
//            System.out.println(tmpPrefix);
            //nifParameters.setPrefix(tmpPrefix + "#");
//            System.out.println(parameters.getPrefix());
            Statement isStringStm = contextRes.getProperty(nifModel.getProperty("http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#isString"));
            if (isStringStm != null) {
                str = isStringStm.getObject().asLiteral().getString();
                textFound = true;
            }
        }

        if (str == null) {
            throw new eu.freme.broker.exception.BadRequestException("No text to process.");
        }

		
		return str;
	}
	
	public static String model2String(Model nifModel) {
		
		StringWriter writer = new StringWriter();
		nifModel.write(writer, "RDF/XML");
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return writer.toString();
	}
	
}
