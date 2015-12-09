package eu.freme.broker.niftools;

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
		String spanUri = new StringBuilder().append(docURI).append("#char=").append(startIndex).append(',').append(endIndex).toString();

		Resource spanAsResource = outModel.createResource(spanUri);
		outModel.add(spanAsResource, RDF.type, NIF.String);
		outModel.add(spanAsResource, RDF.type, NIF.RFC5147String);
		// TODO add language to String
		outModel.add(spanAsResource, NIF.anchorOf,
				outModel.createTypedLiteral(text, XSDDatatype.XSDstring));
		outModel.add(spanAsResource, NIF.beginIndex,
				outModel.createTypedLiteral(startIndex, XSDDatatype.XSDnonNegativeInteger));
		outModel.add(spanAsResource, NIF.endIndex, outModel.createTypedLiteral(endIndex, XSDDatatype.XSDnonNegativeInteger));
		outModel.add(spanAsResource, ITSRDF.taIdentRef, outModel.createResource(taIdentRef));
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
}
