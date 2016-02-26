package de.dkt.common.niftools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.vocabulary.RDF;

import de.dkt.common.niftools.ITSRDF;
import de.dkt.common.niftools.NIF;
import de.dkt.common.niftools.NIFTransferPrefixMapping;

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
		String docURI = NIFReader.extractDocumentURI(outModel);
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
		String docURI = NIFReader.extractDocumentURI(outModel);
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

	public static void addParagraphEntity(Model outModel, int startIndex, int endIndex){
		String docURI = NIFReader.extractDocumentURI(outModel);
		docURI = NIFReader.extractDocumentURI(outModel);
		String paragraphUri = new StringBuilder().append(docURI).append("#char=").append(startIndex).append(',').append(endIndex).toString();

		Resource paragraphAsResource = outModel.createResource(paragraphUri);
		outModel.add(paragraphAsResource, RDF.type, NIF.String);
		outModel.add(paragraphAsResource, RDF.type, NIF.RFC5147String);
		outModel.add(paragraphAsResource, NIF.beginIndex, outModel.createTypedLiteral(startIndex, XSDDatatype.XSDnonNegativeInteger));
		outModel.add(paragraphAsResource, NIF.endIndex, outModel.createTypedLiteral(endIndex, XSDDatatype.XSDnonNegativeInteger));
		
	}

	public static void addPosTagAnnotation(Model outModel, int startIndex, int endIndex, String text, String posTag){
		String docURI = NIFReader.extractDocumentURI(outModel);
		String spanUri = new StringBuilder().append(docURI).append("#char=").append(startIndex).append(",").append(endIndex).toString();
		Resource spanAsResource = outModel.createResource(spanUri);
		outModel.add(spanAsResource, RDF.type, NIF.String);
		outModel.add(spanAsResource, RDF.type, NIF.RFC5147String);
		outModel.add(spanAsResource, NIF.anchorOf, outModel.createTypedLiteral(text, XSDDatatype.XSDstring));
		outModel.add(spanAsResource, NIF.beginIndex, outModel.createTypedLiteral(startIndex, XSDDatatype.XSDnonNegativeInteger));
		outModel.add(spanAsResource, NIF.endIndex, outModel.createTypedLiteral(endIndex, XSDDatatype.XSDnonNegativeInteger));
		outModel.add(spanAsResource, NIF.referenceContext, outModel.createResource(NIFReader.extractDocumentWholeURI(outModel)));
		outModel.add(spanAsResource, NIF.posTag, outModel.createResource(posTag));
	}
	
	public static void addAnnotationEntities(Model outModel, int startIndex, int endIndex, String text, List<String> list, String nerType){
		String docURI = NIFReader.extractDocumentURI(outModel);
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
		//System.out.println("Start/END positions:" + start2 + "-" + end2);
		//System.out.println("inputtext length: "+inputText.length() + " inputtext: "+inputText);
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
        //prefixes.put("dfkinif", "<http://persistence.dfki.de/ontologies/nif#>");
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

	public static Model addPrefixToModel(Model model, String abbrev, String uri){
		model.setNsPrefix(abbrev, uri);
		return model;
	}

	
	
	public static void addDateStats(Model outModel, String inputText, String documentURI, String meanDateRange){
		
		int endTotalText = inputText.codePointCount(0, inputText.length());
		String documentUri = new StringBuilder().append(documentURI).append("#char=").append("0").append(',').append(endTotalText).toString();
        Resource documentResource = outModel.getResource(documentUri);
        outModel.add(documentResource, NIF.meanDateRange, outModel.createTypedLiteral(meanDateRange, XSDDatatype.XSDstring));
	}
	
	//public static void addGeoStats(Model outModel, String inputText, String documentURI, String centralGeoPoint, String geoStdDevs){
	public static void addGeoStats(Model outModel, String inputText, Double avgLatitude, Double avgLongitude, Double stdDevLatitude, Double stdDevLongitude, String docURI){
		
		// add prefix here
		addPrefixToModel(outModel, "dfkinif", DFKINIF.uri);
		int endTotalText = inputText.codePointCount(0, inputText.length());
		String documentUri = new StringBuilder().append(docURI).append("#char=").append("0").append(',').append(endTotalText).toString();
        Resource documentResource = outModel.getResource(documentUri);
        //outModel.add(documentResource, NIF.centralGeoPoint, outModel.createTypedLiteral(centralGeoPoint, XSDDatatype.XSDstring));
        outModel.add(documentResource, DFKINIF.averageLatitude, outModel.createTypedLiteral(avgLatitude, XSDDatatype.XSDdouble));
        outModel.add(documentResource, DFKINIF.averageLongitude, outModel.createTypedLiteral(avgLongitude, XSDDatatype.XSDdouble));
        outModel.add(documentResource, DFKINIF.standardDeviationLatitude, outModel.createTypedLiteral(stdDevLatitude, XSDDatatype.XSDdouble));
        outModel.add(documentResource, DFKINIF.standardDeviationLongitude, outModel.createTypedLiteral(stdDevLongitude, XSDDatatype.XSDdouble));
        //outModel.add(documentResource, NIF.geoStandardDevs, outModel.createTypedLiteral(geoStdDevs, XSDDatatype.XSDstring));
	}


	public static void addEntityProperty(Model nifModel, int beginIndex, int endIndex, String documentURI, String info, Property prop, XSDDatatype dataType) {
		String entityUri = new StringBuilder().append(documentURI).append("#char=").append(Integer.toString(beginIndex)).append(',').append(Integer.toString(endIndex)).toString();
		Resource entityResource = nifModel.getResource(entityUri);
		nifModel.add(entityResource, prop, nifModel.createTypedLiteral(info, dataType));
	}
	
	public static void addLuceneIndexingInformation(Model outModel, String inputText, String documentURI, String indexName, String indexPath){
		
		int endTotalText = inputText.codePointCount(0, inputText.length());
		String documentUri = new StringBuilder().append(documentURI).append("#char=").append("0").append(',').append(endTotalText).toString();
        Resource documentResource = outModel.getResource(documentUri);
        outModel.add(documentResource, NIF.indexName, outModel.createTypedLiteral(indexName, XSDDatatype.XSDstring));
        outModel.add(documentResource, NIF.indexPath, outModel.createTypedLiteral(indexPath, XSDDatatype.XSDstring));
	}

	public static void addDocumentClassification(Model outModel, String inputText, String documentURI, String label){
		int endTotalText = inputText.codePointCount(0, inputText.length());
		String documentUri = new StringBuilder().append(documentURI).append("#char=").append("0").append(',').append(endTotalText).toString();
        Resource documentResource = outModel.getResource(documentUri);
        outModel.add(documentResource, NIF.documentClassification, outModel.createTypedLiteral(label, XSDDatatype.XSDstring));
	}

	public static void addTopicModelling(Model outModel, String inputText, String documentURI, String label){
		int endTotalText = inputText.codePointCount(0, inputText.length());
		String documentUri = new StringBuilder().append(documentURI).append("#char=").append("0").append(',').append(endTotalText).toString();
        Resource documentResource = outModel.getResource(documentUri);
        outModel.add(documentResource, NIF.topicModelling, outModel.createTypedLiteral(label, XSDDatatype.XSDstring));
	}
}
