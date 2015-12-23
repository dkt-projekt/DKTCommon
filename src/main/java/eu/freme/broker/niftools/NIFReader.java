package eu.freme.broker.niftools;

import java.io.IOException;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.RDF;

import eu.freme.common.conversion.rdf.JenaRDFConversionService;
import eu.freme.common.conversion.rdf.RDFConstants.RDFSerialization;
import eu.freme.common.conversion.rdf.RDFConversionService;

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
	
	public static String model2String(Model nifModel, String format) {
		StringWriter writer = new StringWriter();
//		nifModel.write(writer, format);
//		nifModel.write(writer, format);
		nifModel.write(writer, format);
//		nifModel.write(writer, format);
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return writer.toString();
	}

	public static String extractTaIdentRefFromModel(Model nifModel){
		String ref = null;
		ResIterator iterEntities = nifModel.listSubjectsWithProperty(NIF.entity);
        while (iterEntities.hasNext()) {
            Resource r = iterEntities.nextResource();
            com.hp.hpl.jena.rdf.model.Statement st3 = r.getProperty(ITSRDF.taIdentRef);
            ref = ( st3!=null ) ? st3.getObject().asResource().getURI() : null;
        }
        return ref;
	}
	
	public static String extractDocumentURI(Model nifModel){
		StmtIterator iter = nifModel.listStatements(null, RDF.type, nifModel.getResource(NIF.Context.getURI()));
      
		while(iter.hasNext()){
			Resource contextRes = iter.nextStatement().getSubject();
			//System.out.println(contextRes.getURI());
			String uri = contextRes.getURI();
			return uri.substring(0, uri.indexOf('#'));
		}
		throw new eu.freme.broker.exception.BadRequestException("No context/document found.");
	}

	public static String extractDocumentWholeURI(Model nifModel){
		StmtIterator iter = nifModel.listStatements(null, RDF.type, nifModel.getResource(NIF.Context.getURI()));
      
		while(iter.hasNext()){
			Resource contextRes = iter.nextStatement().getSubject();
			//System.out.println(contextRes.getURI());
			String uri = contextRes.getURI();
			return uri;
		}
		throw new eu.freme.broker.exception.BadRequestException("No context/document found.");
	}

	public static String extractDocumentPath(Model nifModel){
		StmtIterator iter = nifModel.listStatements(null, RDF.type, nifModel.getResource(NIF.Context.getURI()));
      
		while(iter.hasNext()){
			Resource contextRes = iter.nextStatement().getSubject();
			Statement st = contextRes.getProperty(DFKINIF.DocumentPath);
			if(st!=null){
	//			System.out.println(contextRes.getURI());
				String uri = st.getObject().asResource().getURI();
				return uri;
			}
		}
		throw new eu.freme.broker.exception.BadRequestException("No document path found.");
	}

	public static String extractDocumentNIFPath(Model nifModel){
		StmtIterator iter = nifModel.listStatements(null, RDF.type, nifModel.getResource(NIF.Context.getURI()));
      
		while(iter.hasNext()){
			Resource contextRes = iter.nextStatement().getSubject();
			Statement st = contextRes.getProperty(DFKINIF.DocumentPath);
			if(st!=null){
	//			System.out.println(contextRes.getURI());
				String uri = st.getObject().asResource().getURI();
				return uri;
			}
		}
		throw new eu.freme.broker.exception.BadRequestException("No document path found.");
	}
	
	public static List<String[]> extractEntities(Model nifModel){
		List<String[]> list = new LinkedList<String[]>();
				
        ResIterator iterEntities = nifModel.listSubjectsWithProperty(NIF.entity);
        while (iterEntities.hasNext()) {
            Resource r = iterEntities.nextResource();
            com.hp.hpl.jena.rdf.model.Statement st = r.getProperty(NIF.entity);
            String stringSt = ( st!=null ) ? st.getObject().asResource().getURI() : null;
//            System.out.println("1."+st.getObject().asResource().getURI());
            com.hp.hpl.jena.rdf.model.Statement st2 = r.getProperty(NIF.anchorOf);
            String stringSt2 = ( st2!=null ) ? st2.getLiteral().getString() : null;
//            System.out.println("7."+st2.getLiteral().getString());
            com.hp.hpl.jena.rdf.model.Statement st3 = r.getProperty(ITSRDF.taIdentRef);
            String stringSt3 = ( st3!=null ) ? st3.getObject().asResource().getURI() : null;
            String[] information = {stringSt3,stringSt2,stringSt};
            list.add(information);
        }
        if(list.isEmpty()){
        	return null;
        }
		return list;
	}

	public static Model extractModelFromString(String content) throws Exception {
		//Model outModel = ModelFactory.createDefaultModel();
		Model outModel = NIFWriter.initializeOutputModel();
		RDFConversionService rdfConversion = new JenaRDFConversionService();
		outModel = rdfConversion.unserializeRDF(content, RDFSerialization.RDF_XML);
		if(outModel==null){
        	return null;
        }
		return outModel;
	}
	public static Model extractModelFromTurtleString(String content) throws Exception {
		//Model outModel = ModelFactory.createDefaultModel();
		Model outModel = NIFWriter.initializeOutputModel();
		RDFConversionService rdfConversion = new JenaRDFConversionService();
		outModel = rdfConversion.unserializeRDF(content, RDFSerialization.TURTLE);
		if(outModel==null){
        	return null;
        }
		return outModel;
	}
}
