package de.dkt.common.niftools;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.RDF;

import eu.freme.common.conversion.rdf.JenaRDFConversionService;
import eu.freme.common.conversion.rdf.RDFConstants.RDFSerialization;
import eu.freme.common.conversion.rdf.RDFConversionService;
import eu.freme.common.exception.BadRequestException;

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
            throw new BadRequestException("No text to process.");
        }

		
		return str;
	}
	
	public static String extractMeanDate(Model nifModel){
		String str = null;
        StmtIterator iter = nifModel.listStatements(null, RDF.type, NIF.Context);
        boolean textFound = false;
        while (!textFound) {
            Resource contextRes = iter.nextStatement().getSubject();
            Statement isStringStm = contextRes.getProperty(NIF.meanDate);
            if (isStringStm != null) {
                str = isStringStm.getObject().asLiteral().getString();
                textFound = true;
            }
        }
		return str;
	}

	
	public static String extractMeanDateRange(Model nifModel){
		String str = null;
        StmtIterator iter = nifModel.listStatements(null, RDF.type, NIF.Context);
        boolean textFound = false;
        while (!textFound) {
            Resource contextRes = iter.nextStatement().getSubject();
            Statement isStringStm = contextRes.getProperty(NIF.meanDateRange);
            if (isStringStm != null) {
                str = isStringStm.getObject().asLiteral().getString();
                textFound = true;
            }
        }
		return str;
	}
	
	public static String model2String(Model nifModel, String format) {
		StringWriter writer = new StringWriter();
		nifModel.write(writer, format);
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
		throw new BadRequestException("No context/document found.");
	}

	public static int extractEndTotalText(Model nifModel){
		StmtIterator iter = nifModel.listStatements(null, RDF.type, nifModel.getResource(NIF.Context.getURI()));
		while(iter.hasNext()){
			Resource contextRes = iter.nextStatement().getSubject();
			//System.out.println(contextRes.getURI());
			String uri = contextRes.getURI();
			String sEnd = uri.substring(uri.lastIndexOf(','));
			int i = Integer.parseInt(sEnd);
			return i;
		}
		throw new BadRequestException("No context/document found.");
	}

	public static String extractDocumentWholeURI(Model nifModel){
		StmtIterator iter = nifModel.listStatements(null, RDF.type, nifModel.getResource(NIF.Context.getURI()));
      
		while(iter.hasNext()){
			Resource contextRes = iter.nextStatement().getSubject();
			//System.out.println(contextRes.getURI());
			String uri = contextRes.getURI();
			return uri;
		}
		throw new BadRequestException("No context/document found.");
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
		throw new BadRequestException("No document path found.");
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
		throw new BadRequestException("No document path found.");
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
	
	public static List<String[]> extractEntityIndices(Model nifModel){
		List<String[]> list = new LinkedList<String[]>();
				
		ResIterator iterEntities = nifModel.listSubjectsWithProperty(NIF.entity);
        while (iterEntities.hasNext()) {
            Resource r = iterEntities.nextResource();
            System.out.println("DEBUGGINg r here:" + r);
            com.hp.hpl.jena.rdf.model.Statement st = r.getProperty(NIF.entity);
            String stringSt = ( st!=null ) ? st.getObject().asResource().getURI() : null;
//            System.out.println("1."+st.getObject().asResource().getURI());
            com.hp.hpl.jena.rdf.model.Statement st2 = r.getProperty(NIF.anchorOf);
            String stringSt2 = ( st2!=null ) ? st2.getLiteral().getString() : null;
//            System.out.println("7."+st2.getLiteral().getString());
            com.hp.hpl.jena.rdf.model.Statement st3 = r.getProperty(ITSRDF.taIdentRef);
            String stringSt3 = ( st3!=null ) ? st3.getObject().asResource().getURI() : null;
            com.hp.hpl.jena.rdf.model.Statement st4 = r.getProperty(NIF.beginIndex);
            String stringSt4 = ( st4!=null ) ? st4.getLiteral().getString() : null;
            com.hp.hpl.jena.rdf.model.Statement st5 = r.getProperty(NIF.endIndex);
            String stringSt5 = ( st5!=null ) ? st5.getLiteral().getString() : null;
            String[] information = {stringSt3,stringSt2,stringSt,stringSt4,stringSt5};
            list.add(information);
        }
        if(list.isEmpty()){
        	return null;
        }
		return list;
	}
	
	public static String extractTaIdentRefWithEntityURI(Model nifModel, String entityURI){
		
		Resource r = ResourceFactory.createResource(entityURI);
		NodeIterator nodes = nifModel.listObjectsOfProperty(r, ITSRDF.taIdentRef);
		String taIdentRef = null;
		while(nodes.hasNext()){
			RDFNode node = nodes.next();
		   taIdentRef = node.asResource().getURI();
		}
		return taIdentRef;
	}

	public static Map<String,Map<String,String>> extractEntitiesExtended(Model nifModel){
		Map<String,Map<String,String>> list = new HashMap<String,Map<String,String>>();
				
        ResIterator iterEntities = nifModel.listSubjectsWithProperty(NIF.entity);
        while (iterEntities.hasNext()) {
    		Map<String,String> map = new HashMap<String,String>();
            Resource r = iterEntities.nextResource();

            String entityURI = r.getURI();
            
            StmtIterator iter2 = r.listProperties();
            while (iter2.hasNext()) {
				Statement st2 = iter2.next();
				String predicate =st2.getPredicate().getURI(); 
				String object = null;
				if(st2.getObject().isResource()){
					object = st2.getObject().asResource().getURI();
				}
				else{
					object = st2.getObject().asLiteral().getString();
				}
				map.put(predicate,object);
			}
            if(!map.isEmpty()){
                list.put(entityURI,map);
            }
        }
        if(list.isEmpty()){
        	return null;
        }
		return list;
	}

	public static Map<String,String> extractDocumentInformation(Model nifModel){
		Map<String,String> map = new HashMap<String,String>();
		
        ResIterator iterEntities = nifModel.listSubjectsWithProperty(RDF.type,"http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#Context");
        while (iterEntities.hasNext()) {
            Resource r = iterEntities.nextResource();
            StmtIterator iter2 = r.listProperties();
            while (iter2.hasNext()) {
				Statement st2 = iter2.next();
				String predicate =st2.getPredicate().getURI(); 
				String object = null;
				if(st2.getObject().isResource()){
					object = st2.getObject().asResource().getURI();
				}
				else{
					object = st2.getObject().asLiteral().getString();
				}
				map.put(predicate,object);
			}
        }
        if(map.isEmpty()){
        	return null;
        }
		return map;
	}

	
	public static List<String[]> extractTempStats(Model nifModel){
		List<String[]> list = new LinkedList<String[]>();
				
//	      nif:meanDate "19360621060000"^^xsd:string ;
//	      nif:stdevDate "96"^^xsd:string .
	    
        ResIterator iterEntities = nifModel.listSubjectsWithProperty(NIF.meanDate);
        while (iterEntities.hasNext()) {
            Resource r = iterEntities.nextResource();
            Statement st = r.getProperty(NIF.meanDate);
            String stringSt = ( st!=null ) ? st.getLiteral().getString() : null;
            Statement st2 = r.getProperty(NIF.stdevDate);
            String stringSt2 = ( st2!=null ) ? st2.getLiteral().getString() : null;
//            System.out.println("7."+st2.getLiteral().getString());
            String [] information = {stringSt,stringSt2};
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

	public static Model extractModelFromFormatString(String content, RDFSerialization rdfSerialization) throws Exception {
		//Model outModel = ModelFactory.createDefaultModel();
		Model outModel = NIFWriter.initializeOutputModel();
		RDFConversionService rdfConversion = new JenaRDFConversionService();
		outModel = rdfConversion.unserializeRDF(content, rdfSerialization);
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
