package eu.freme.broker.niftools;

import org.springframework.beans.factory.annotation.Autowired;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.RDF;

import eu.freme.broker.tools.NIFParameterSet;
import eu.freme.common.conversion.rdf.RDFConstants;
import eu.freme.common.conversion.rdf.RDFConversionService;


public class NIFCommons {

	@Autowired
	static RDFConversionService rdfConversionService;

	public static String extractIsStringFromNIF(NIFParameterSet nifParameters, String language){
		
		Model inModel = ModelFactory.createDefaultModel();
        
        String textForProcessing = null;
        System.out.println("DEBUGGING nifParams:" + nifParameters.getInput());
        if (nifParameters.getInformat().equals(RDFConstants.RDFSerialization.PLAINTEXT)) {
            // input is sent as value of the input parameter
            textForProcessing = nifParameters.getInput();
			rdfConversionService.plaintextToRDF(inModel, textForProcessing,language, nifParameters.getPrefix());
            
        } else {

            try {
				inModel = rdfConversionService.unserializeRDF(nifParameters.getInput(), nifParameters.getInformat());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            StmtIterator iter = inModel.listStatements(null, RDF.type, inModel.getResource("http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#Context"));
            boolean textFound = false;
            String tmpPrefix = "http://freme-project.eu/#";
            // The first nif:Context with assigned nif:isString will be processed.
            while (!textFound) {
                Resource contextRes = iter.nextStatement().getSubject();
                tmpPrefix = contextRes.getURI().split("#")[0];
//                System.out.println(tmpPrefix);
                nifParameters.setPrefix(tmpPrefix + "#");
//                System.out.println(parameters.getPrefix());
                Statement isStringStm = contextRes.getProperty(inModel.getProperty("http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#isString"));
                if (isStringStm != null) {
                    textForProcessing = isStringStm.getObject().asLiteral().getString();
                    textFound = true;
                }
            }

            if (textForProcessing == null) {
                throw new eu.freme.broker.exception.BadRequestException("No text to process.");
            }
        }
        
        return textForProcessing;
		
	}
	
	public static void main(String[] args){
		
		
	}
}
