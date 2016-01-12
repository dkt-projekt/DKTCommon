package eu.freme.broker.niftools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;

import eu.freme.broker.niftools.ITSRDF;
import eu.freme.broker.niftools.NIF;
import eu.freme.broker.niftools.NIFTransferPrefixMapping;

public class NIFWriter {

	public static void addAnnotation(Model outModel, Resource documentResource, String documentURI, int annotationId, String annotation) {
        StringBuilder uriBuilder = new StringBuilder();
        uriBuilder.append(documentURI);
        uriBuilder.append(annotation);
        uriBuilder.append(annotationId);

        Resource annotationAsResource = outModel.createResource(uriBuilder.toString());
        outModel.add(annotationAsResource, RDF.type, NIF.Annotation);
        outModel.add(documentResource, NIF.topic, annotationAsResource);
        outModel.add(annotationAsResource, ITSRDF.taIdentRef, outModel.createResource("http://example.dkt.de/meainingUri1"));

        outModel.add(annotationAsResource, NIF.confidence,Double.toString(0), XSDDatatype.XSDstring);
	}
	
	public static void addAnnotation(Model outModel, Resource documentResource, String documentURI, int annotationId) {
		addAnnotation(outModel, documentResource, documentURI, annotationId, "#annotation");
	}
	
	public static void addAnnotationWithTaIdentRef(Model outModel, int startIndex, int endIndex, String text, String taIdentRef){
		String docURI = "http://dkt.dfki.de/examples/"; 
		docURI = NIFReader.extractDocumentURI(outModel);
		String spanUri = new StringBuilder().append(docURI).append("#char=").append(startIndex).append(',').append(endIndex).toString();

		Resource spanAsResource = outModel.createResource(spanUri);
		outModel.add(spanAsResource, RDF.type, NIF.String);
		outModel.add(spanAsResource, RDF.type, NIF.RFC5147String);
		// TODO add language to String
		outModel.add(spanAsResource, NIF.anchorOf, outModel.createTypedLiteral(text, XSDDatatype.XSDstring));
		outModel.add(spanAsResource, NIF.beginIndex, outModel.createTypedLiteral(startIndex, XSDDatatype.XSDnonNegativeInteger));
		outModel.add(spanAsResource, NIF.endIndex, outModel.createTypedLiteral(endIndex, XSDDatatype.XSDnonNegativeInteger));
		outModel.add(spanAsResource, ITSRDF.taIdentRef, outModel.createResource(taIdentRef));
        //outModel.add(spanAsResource, ITSRDF.taClassRef, outModel.createResource("http://dkt.dfki.de/entities/location"));
        
	}

	public static void addAnnotationEntity(Model outModel, int startIndex, int endIndex, String text, String taIdentRef, String nerType){
		String docURI = "http://dkt.dfki.de/examples/"; 
		docURI = NIFReader.extractDocumentURI(outModel);
		String spanUri = new StringBuilder().append(docURI).append("#char=").append(startIndex).append(',').append(endIndex).toString();

		Resource spanAsResource = outModel.createResource(spanUri);
		outModel.add(spanAsResource, RDF.type, NIF.String);
		outModel.add(spanAsResource, RDF.type, NIF.RFC5147String);
		// TODO add language to String
		outModel.add(spanAsResource, NIF.anchorOf, outModel.createTypedLiteral(text, XSDDatatype.XSDstring));
		outModel.add(spanAsResource, NIF.beginIndex, outModel.createTypedLiteral(startIndex, XSDDatatype.XSDnonNegativeInteger));
		outModel.add(spanAsResource, NIF.endIndex, outModel.createTypedLiteral(endIndex, XSDDatatype.XSDnonNegativeInteger));
		outModel.add(spanAsResource, ITSRDF.taIdentRef, outModel.createResource(taIdentRef));
		outModel.add(spanAsResource, NIF.entity, outModel.createResource(nerType));
        //outModel.add(spanAsResource, ITSRDF.taClassRef, outModel.createResource("http://dkt.dfki.de/entities/location"));
        
	}

	public static void addAnnotationEntities(Model outModel, int startIndex, int endIndex, String text, List<String> list, String nerType){
		String docURI = "http://dkt.dfki.de/examples/"; 
		docURI = NIFReader.extractDocumentURI(outModel);
		String spanUri = new StringBuilder().append(docURI).append("#char=").append(startIndex).append(',').append(endIndex).toString();

		Resource spanAsResource = outModel.createResource(spanUri);
		outModel.add(spanAsResource, RDF.type, NIF.String);
		outModel.add(spanAsResource, RDF.type, NIF.RFC5147String);
		// TODO add language to String
		outModel.add(spanAsResource, NIF.anchorOf, outModel.createTypedLiteral(text, XSDDatatype.XSDstring));
		outModel.add(spanAsResource, NIF.beginIndex, outModel.createTypedLiteral(startIndex, XSDDatatype.XSDnonNegativeInteger));
		outModel.add(spanAsResource, NIF.endIndex, outModel.createTypedLiteral(endIndex, XSDDatatype.XSDnonNegativeInteger));
		
		outModel.add(spanAsResource, NIF.referenceContext, outModel.createResource(NIFReader.extractDocumentWholeURI(outModel)));
//		outModel.add(spanAsResource, NIF.referenceContext, outModel.createTypedLiteral("ReferenceContextDummy", XSDDatatype.XSDstring));
		for (String taIdentRef : list) {
			outModel.add(spanAsResource, ITSRDF.taIdentRef, outModel.createResource(taIdentRef));
		}
		outModel.add(spanAsResource, NIF.entity, outModel.createResource(nerType));
        //outModel.add(spanAsResource, ITSRDF.taClassRef, outModel.createResource("http://dkt.dfki.de/entities/location"));
        
	}

	
	public static void addSpan(Model outModel, Resource documentResource, String inputText, String documentURI,
			int start2, int end2) {
		System.out.println("Start/END positions:" + start2 + "-" + end2);
		System.out.println("inputtext length: "+inputText.length() + " inputtext: "+inputText);
        int startInJavaText = start2;
        int endInJavaText = end2;
        //int start = inputText.codePointCount(0, startInJavaText);
        //int end = start + inputText.codePointCount(startInJavaText, endInJavaText);
        int start = start2;
        int end = end2;

        String spanUri = NIFUriHelper.getNifUri(documentURI, start, end);
        Resource spanAsResource = outModel.createResource(spanUri);
        outModel.add(spanAsResource, RDF.type, NIF.String);
        outModel.add(spanAsResource, RDF.type, NIF.RFC5147String);
        // TODO add language to String
        outModel.add(spanAsResource, NIF.anchorOf,
                outModel.createTypedLiteral(inputText.substring(startInJavaText, endInJavaText), XSDDatatype.XSDstring));
        outModel.add(spanAsResource, NIF.beginIndex,
                outModel.createTypedLiteral(start, XSDDatatype.XSDnonNegativeInteger));
        outModel.add(spanAsResource, NIF.endIndex, outModel.createTypedLiteral(end, XSDDatatype.XSDnonNegativeInteger));
        outModel.add(spanAsResource, NIF.referenceContext, documentResource);

        outModel.add(spanAsResource, ITSRDF.taIdentRef, outModel.createResource("http://example.dkt.de/meainingUri1"));
        outModel.add(spanAsResource, ITSRDF.taConfidence,
        		outModel.createTypedLiteral(0, XSDDatatype.XSDdouble));
        outModel.add(spanAsResource, ITSRDF.taClassRef, outModel.createResource("http://exampledkt.de/SpanType1"));
	}

	public static void addInitialString(Model outModel, String inputText, String documentURI) {
        		
        outModel.setNsPrefixes(NIFTransferPrefixMapping.getInstance());
        int endTotalText = inputText.codePointCount(0, inputText.length());

        //String docURI = "http://dkt.dfki.de/examples/";
        
        String documentUri = new StringBuilder().append(documentURI).append("#char=").append("0").append(',').append(endTotalText).toString();

        Resource documentResource = outModel.createResource(documentUri);
        outModel.add(documentResource, RDF.type, NIF.Context);
        outModel.add(documentResource, RDF.type, NIF.String);
        outModel.add(documentResource, RDF.type, NIF.RFC5147String);
        outModel.add(documentResource, NIF.isString, 
        		outModel.createTypedLiteral(inputText, XSDDatatype.XSDstring));
        outModel.add(documentResource, NIF.beginIndex,
                outModel.createTypedLiteral(0, XSDDatatype.XSDnonNegativeInteger));
        outModel.add(documentResource, NIF.endIndex,
                outModel.createTypedLiteral(endTotalText, XSDDatatype.XSDnonNegativeInteger));

	}
	
	public static Model initializeOutputModel(){
		Model model = ModelFactory.createDefaultModel();
		//TODO Add NIF namespaces and more.
        Map<String,String> prefixes = new HashMap<String, String>();
        prefixes.put("xsd", "<http://www.w3.org/2001/XMLSchema#>");
        prefixes.put("nif", "<http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#>");
        prefixes.put("dfkinif", "<http://persistence.dfki.de/ontologies/nif#>");
        //prefixes.put("dbpedia-fr", "<http://fr.dbpedia.org/resource/>");
        //prefixes.put("dbc", "<http://dbpedia.org/resource/Category:>");
        //prefixes.put("dbpedia-es", "<http://es.dbpedia.org/resource/>");
		//prefixes.put("itsrdf", "<http://www.w3.org/2005/11/its/rdf#>");
		//prefixes.put("dbpedia", "<http://dbpedia.org/resource/>");
		//prefixes.put("rdfs", "<http://www.w3.org/2000/01/rdf-schema#>");
		//prefixes.put("dbpedia-de", "<http://de.dbpedia.org/resource/>");
		//prefixes.put("dbpedia-ru", "<http://ru.dbpedia.org/resource/>");
		////prefixes.put("freme-onto", "<http://freme-project.eu/ns#>");
		//prefixes.put("dbpedia-nl", "<http://nl.dbpedia.org/resource/>");
		//prefixes.put("dcterms", "<http://purl.org/dc/terms/>");
		//prefixes.put("dbpedia-it", "<http://it.dbpedia.org/resource/>");
        
        
        model.setNsPrefixes(prefixes);
        
		return model;
	}

	public static void addADateStats(Model outModel, String inputText, String documentURI, String meanDate, String stdDevDate){
		
		int endTotalText = inputText.codePointCount(0, inputText.length());
		String documentUri = new StringBuilder().append(documentURI).append("#char=").append("0").append(',').append(endTotalText).toString();

        Resource documentResource = outModel.getResource(documentUri);
        
        outModel.add(documentResource, NIF.property("meanDate"), outModel.createTypedLiteral(meanDate, XSDDatatype.XSDstring));
        outModel.add(documentResource, NIF.property("meanStandardDeviation"), outModel.createTypedLiteral(stdDevDate, XSDDatatype.XSDstring));
	}
	
}
