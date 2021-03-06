package de.dkt.common.niftools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDF;

import eu.freme.common.conversion.rdf.RDFConstants;

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
		outModel.add(spanAsResource, NIF.referenceContext, outModel.createResource(NIFReader.extractDocumentWholeURI(outModel)));
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
		//outModel.add(spanAsResource, NIF.entity, outModel.createResource(nerType));
        outModel.add(spanAsResource, ITSRDF.taClassRef, nerType);
        outModel.add(spanAsResource, NIF.referenceContext, outModel.createResource(NIFReader.extractDocumentWholeURI(outModel)));
        
	}
	
	private static String stringDateXSDDateTimeFormatter(String date){
		String year = date.substring(0, 4);
		String month = date.substring(4, 6);
		String day = date.substring(6, 8);
		String hour = date.substring(8,10);
		String minute = date.substring(10, 12);
		String second = date.substring(12, 14);
		return String.format("%s-%s-%sT%s:%s:%s", year, month, day, hour, minute, second);
	}
	
	public static void addTemporalEntity(Model outModel, int startIndex, int endIndex, String text, String normalization){
		String docURI = NIFReader.extractDocumentURI(outModel);
		docURI = NIFReader.extractDocumentURI(outModel);
		String spanUri = new StringBuilder().append(docURI).append("#char=").append(startIndex).append(',').append(endIndex).toString();

		Resource spanAsResource = outModel.createResource(spanUri);
		outModel.add(spanAsResource, RDF.type, NIF.String);
		outModel.add(spanAsResource, RDF.type, NIF.RFC5147String);
		
		outModel.add(spanAsResource, NIF.anchorOf, outModel.createTypedLiteral(text, XSDDatatype.XSDstring));
		outModel.add(spanAsResource, NIF.beginIndex, outModel.createTypedLiteral(startIndex, XSDDatatype.XSDnonNegativeInteger));
		outModel.add(spanAsResource, NIF.endIndex, outModel.createTypedLiteral(endIndex, XSDDatatype.XSDnonNegativeInteger));
		
		// convert normalization to xsd:dateTime notation
		String[] norm = normalization.split("_");
		System.out.println("D1: "+norm[0]);
		System.out.println("D2: "+stringDateXSDDateTimeFormatter(norm[0]));
		String intervalStart = stringDateXSDDateTimeFormatter(norm[0]);
		String intervalEnd = stringDateXSDDateTimeFormatter(norm[1]);
		outModel.add(spanAsResource, TIME.intervalStarts, outModel.createTypedLiteral(intervalStart, XSDDatatype.XSDdateTime));
		outModel.add(spanAsResource, TIME.intervalFinishes, outModel.createTypedLiteral(intervalEnd, XSDDatatype.XSDdateTime));
        outModel.add(spanAsResource, ITSRDF.taClassRef, TIME.temporalEntity);
        outModel.add(spanAsResource, NIF.referenceContext, outModel.createResource(NIFReader.extractDocumentWholeURI(outModel)));
        
	}

	public static void addTemporalEntityFromTimeML(Model outModel, int startIndex, int endIndex, String text, String normalization){
		String docURI = NIFReader.extractDocumentURI(outModel);
		docURI = NIFReader.extractDocumentURI(outModel);
		String spanUri = new StringBuilder().append(docURI).append("#char=").append(startIndex).append(',').append(endIndex).toString();

		Resource spanAsResource = outModel.createResource(spanUri);
		outModel.add(spanAsResource, RDF.type, NIF.String);
		outModel.add(spanAsResource, RDF.type, NIF.RFC5147String);
		
		outModel.add(spanAsResource, NIF.anchorOf, outModel.createTypedLiteral(text, XSDDatatype.XSDstring));
		outModel.add(spanAsResource, NIF.beginIndex, outModel.createTypedLiteral(startIndex, XSDDatatype.XSDnonNegativeInteger));
		outModel.add(spanAsResource, NIF.endIndex, outModel.createTypedLiteral(endIndex, XSDDatatype.XSDnonNegativeInteger));
		
		// convert normalization to xsd:dateTime notation
		String[] norm = normalization.split("_");
		String intervalStart = norm[0];
		String intervalEnd = norm[1];
		outModel.add(spanAsResource, TIME.intervalStarts, outModel.createTypedLiteral(intervalStart, XSDDatatype.XSDdateTime));
		outModel.add(spanAsResource, TIME.intervalFinishes, outModel.createTypedLiteral(intervalEnd, XSDDatatype.XSDdateTime));
        outModel.add(spanAsResource, ITSRDF.taClassRef, TIME.temporalEntity);
        outModel.add(spanAsResource, NIF.referenceContext, outModel.createResource(NIFReader.extractDocumentWholeURI(outModel)));
        
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
	
	public static void addSentenceSentimentAnnotation(Model outModel, int startIndex, int endIndex, double sentVal){
		String docURI = NIFReader.extractDocumentURI(outModel);
		docURI = NIFReader.extractDocumentURI(outModel);
		String sentenceUri = new StringBuilder().append(docURI).append("#char=").append(startIndex).append(',').append(endIndex).toString();

		Resource sentenceAsResource = outModel.createResource(sentenceUri);
		outModel.add(sentenceAsResource, RDF.type, NIF.String);
		outModel.add(sentenceAsResource, RDF.type, NIF.RFC5147String);
		outModel.add(sentenceAsResource, NIF.beginIndex, outModel.createTypedLiteral(startIndex, XSDDatatype.XSDnonNegativeInteger));
		outModel.add(sentenceAsResource, NIF.endIndex, outModel.createTypedLiteral(endIndex, XSDDatatype.XSDnonNegativeInteger));
		outModel.add(sentenceAsResource, DKTNIF.sentimentValue, outModel.createTypedLiteral(sentVal, XSDDatatype.XSDdouble));
		
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
		outModel.add(spanAsResource, NIF.posTag, outModel.createTypedLiteral(posTag, XSDDatatype.XSDstring));
	}
	
	public static void addAnnotationEntities(Model outModel, int startIndex, int endIndex, String text, String uri, String nerType){
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
		
		outModel.add(spanAsResource, ITSRDF.taIdentRef, outModel.createResource(uri));
		
		//outModel.add(spanAsResource, NIF.entity, outModel.createResource(nerType));
		outModel.add(spanAsResource, ITSRDF.taClassRef, outModel.createResource(nerType));
        //outModel.add(spanAsResource, ITSRDF.taClassRef, outModel.createResource("http://dkt.dfki.de/entities/location"));
        
	}

	public static void addAnnotationEntitiesWithoutURI(Model outModel, int startIndex, int endIndex, String text, String nerType){
		String docURI = NIFReader.extractDocumentURI(outModel);
		docURI = NIFReader.extractDocumentURI(outModel);
		String spanUri = new StringBuilder().append(docURI).append("#char=").append(startIndex).append(',').append(endIndex).toString();

		Resource spanAsResource = outModel.createResource(spanUri);
		outModel.add(spanAsResource, RDF.type, NIF.String);
		outModel.add(spanAsResource, RDF.type, NIF.RFC5147String);
		outModel.add(spanAsResource, NIF.anchorOf, outModel.createTypedLiteral(text, XSDDatatype.XSDstring));
		outModel.add(spanAsResource, NIF.beginIndex, outModel.createTypedLiteral(startIndex, XSDDatatype.XSDnonNegativeInteger));
		outModel.add(spanAsResource, NIF.endIndex, outModel.createTypedLiteral(endIndex, XSDDatatype.XSDnonNegativeInteger));
		outModel.add(spanAsResource, NIF.referenceContext, outModel.createResource(NIFReader.extractDocumentWholeURI(outModel)));
		//outModel.add(spanAsResource, NIF.entity, outModel.createResource(nerType));
		outModel.add(spanAsResource, ITSRDF.taClassRef, outModel.createResource(nerType));
		
	}
	
	
	public static void addCoreferenceAnnotation(Model outModel, int startIndex, int endIndex, String text, String sameAsEntityURI, String sameAsEntityTaIdentRef){
		
		String docURI = NIFReader.extractDocumentURI(outModel);
		String spanUri = new StringBuilder().append(docURI).append("#char=").append(startIndex).append(',').append(endIndex).toString();

		Map<String, String> prefixes = outModel.getNsPrefixMap();
		if (!prefixes.containsKey("owl")){
			prefixes.put("owl", "http://www.w3.org/2002/07/owl#");
			outModel.setNsPrefixes(prefixes);
		}
		
		Resource spanAsResource = outModel.createResource(spanUri);
		outModel.add(spanAsResource, RDF.type, NIF.String);
		outModel.add(spanAsResource, RDF.type, NIF.RFC5147String);
		outModel.add(spanAsResource, NIF.anchorOf, outModel.createTypedLiteral(text, XSDDatatype.XSDstring));
		outModel.add(spanAsResource, NIF.beginIndex, outModel.createTypedLiteral(startIndex, XSDDatatype.XSDnonNegativeInteger));
		outModel.add(spanAsResource, NIF.endIndex, outModel.createTypedLiteral(endIndex, XSDDatatype.XSDnonNegativeInteger));
		outModel.add(spanAsResource, NIF.referenceContext, outModel.createResource(NIFReader.extractDocumentWholeURI(outModel)));
		outModel.add(spanAsResource, OWL.sameAs, outModel.createTypedLiteral(sameAsEntityURI, XSDDatatype.XSDstring));
		if (sameAsEntityTaIdentRef != null){
			outModel.add(spanAsResource, ITSRDF.taIdentRef, outModel.createTypedLiteral(sameAsEntityTaIdentRef, XSDDatatype.XSDstring));
		}
		
	}

	public static void addAnnotationRelation(Model outModel, int startIndex, int endIndex, String text, String sub, String act, String obj, String thematicRoleSubj, String thematicRoleObj){
		String docURI = NIFReader.extractDocumentURI(outModel);
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
		outModel.add(spanAsResource, ITSRDF.taIdentRef, outModel.createResource("http://dkt.dfki.de/entities/relation"));
		
//		outModel.add(spanAsResource, NIF.relationSubject, outModel.createResource(sub));
//		outModel.add(spanAsResource, NIF.relationAction, outModel.createResource(act));
//		outModel.add(spanAsResource, NIF.relationObject, outModel.createResource(obj));
//		if (thematicRoleSubj != null){
//			outModel.add(spanAsResource, NIF.thematicRoleSubj, outModel.createResource(thematicRoleSubj));
//		}
//		if (thematicRoleObj != null){
//			outModel.add(spanAsResource, NIF.thematicRoleObj, outModel.createResource(thematicRoleObj));
//		}
		outModel.add(spanAsResource, NIF.relationSubject, outModel.createTypedLiteral(sub,XSDDatatype.XSDstring));
		outModel.add(spanAsResource, NIF.relationAction, outModel.createTypedLiteral(act,XSDDatatype.XSDstring));
		outModel.add(spanAsResource, NIF.relationObject, outModel.createTypedLiteral(obj,XSDDatatype.XSDstring));
		if (thematicRoleSubj != null){
			outModel.add(spanAsResource, NIF.thematicRoleSubj, outModel.createTypedLiteral(thematicRoleSubj,XSDDatatype.XSDstring));
		}
		if (thematicRoleObj != null){
			outModel.add(spanAsResource, NIF.thematicRoleObj, outModel.createTypedLiteral(thematicRoleObj,XSDDatatype.XSDstring));
		}
        //outModel.add(spanAsResource, ITSRDF.taClassRef, outModel.createResource("http://dkt.dfki.de/entities/location"));
	}
	
	public static void addDareRelationAnnotation(Model outModel, int startIndex, int endIndex, HashMap<String, List<Integer>> arguments, HashMap<String, String> argumentStrings, String relationType){
		String docURI = NIFReader.extractDocumentURI(outModel);
		
		String spanUri = new StringBuilder().append(docURI).append("#char=").append(startIndex).append(',').append(endIndex).toString();

		Resource spanAsResource = outModel.createResource(spanUri);
		outModel.add(spanAsResource, RDF.type, NIF.String);
		outModel.add(spanAsResource, RDF.type, NIF.RFC5147String);
		outModel.add(spanAsResource, NIF.beginIndex, outModel.createTypedLiteral(startIndex, XSDDatatype.XSDnonNegativeInteger));
		outModel.add(spanAsResource, NIF.endIndex, outModel.createTypedLiteral(endIndex, XSDDatatype.XSDnonNegativeInteger));
		outModel.add(spanAsResource, DKTNIF.dareRelationType, outModel.createTypedLiteral(relationType, XSDDatatype.XSDstring));
		outModel.add(spanAsResource, NIF.referenceContext, outModel.createResource(NIFReader.extractDocumentWholeURI(outModel)));
		
		for (String argumentName : arguments.keySet()){
			//outModel.add(spanAsResource, DKTNIF.dareRelationArgumentName, outModel.createTypedLiteral(argumentName, XSDDatatype.XSDstring));
			Property dareRelationArgumentStartIndexPrefix = ResourceFactory.createProperty("http://dkt.dfki.de/ontologies/nif#dfkiDareStartIndexArgument_" + argumentName);
			Property dareRelationArgumentEndIndexPrefix = ResourceFactory.createProperty("http://dkt.dfki.de/ontologies/nif#dfkiDareEndIndexArgument_" + argumentName);
			Property dareRelationArgumentAnchorOfPrefix = ResourceFactory.createProperty("http://dkt.dfki.de/ontologies/nif#dfkiDareAnchorOfArgument_" + argumentName);
			outModel.add(spanAsResource, dareRelationArgumentStartIndexPrefix, outModel.createTypedLiteral(arguments.get(argumentName).get(0), XSDDatatype.XSDnonNegativeInteger));
			outModel.add(spanAsResource, dareRelationArgumentEndIndexPrefix, outModel.createTypedLiteral(arguments.get(argumentName).get(1), XSDDatatype.XSDnonNegativeInteger));
			outModel.add(spanAsResource, dareRelationArgumentAnchorOfPrefix, outModel.createTypedLiteral(argumentStrings.get(argumentName), XSDDatatype.XSDnonNegativeInteger));
		}
		
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
	
	/** Method to add NIF annotations for source segments in esmt/xlingual
	 * Note these source segments are of type nif:Phrase
	 * @param outModel the current NIF model
	 * @param startIndex beginning of the source phrase
	 * @param endIndex end of the source phrase
	 * @param text surface representation of the phrase
	 * @param documentResource is the original context
	 * @param annIndex index number of the annotation unit
	 */
	public static void addAnnotationMTSource(Model outModel, int startIndex, int endIndex, String text, Resource documentResource, String annIndex){
				//System.out.println("Hello I am inside here\n");
				String docURI = NIFReader.extractDocumentURI(outModel);
				String spanUri = new StringBuilder().append(docURI).append("#char=").append(startIndex).append(',').append(endIndex).toString();

				Resource spanAsResource = outModel.createResource(spanUri);
				outModel.add(spanAsResource, RDF.type, NIF.Phrase);
				//outModel.add(spanAsResource, RDF.type, NIF.RFC5147String);
				outModel.add(spanAsResource, RDF.type, NIF.OffsetString);
				
				outModel.add(spanAsResource, NIF.anchorOf, outModel.createTypedLiteral(text, XSDDatatype.XSDstring));
				outModel.add(spanAsResource, NIF.beginIndex, outModel.createTypedLiteral(startIndex, XSDDatatype.XSDnonNegativeInteger));
				outModel.add(spanAsResource, NIF.endIndex, outModel.createTypedLiteral(endIndex, XSDDatatype.XSDnonNegativeInteger));
				outModel.add(spanAsResource, NIF.referenceContext, documentResource);
				outModel.add(spanAsResource, NIFANN.AnnotationUnit, outModel.createResource(annIndex));
				//outModel.add(spanAsResource, ITSRDF.taIdentRef, outModel.createResource(taIdentRef));
				//outModel.add(spanAsResource, NIF.entity, outModel.createResource(nerType));
		        //outModel.add(spanAsResource, ITSRDF.taClassRef, nerType);
		        
	}
	
	
	/** Method to add NIF annotations for target segments in esmt/xlingual
	 * Note these target segments are annotation units (blank nodes represented by AnonId)
	 * @param outModel the current NIF model
	 * @param text surface representation of the phrase
	 * @param targetLang language of the text
	 * @param documentResource is the original context
	 * @param annIndex index number of the annotation unit
	 */
	public static void addAnnotationMTTarget(Model outModel, String text, String targetLang, Resource documentResource, String annIndex){
				//System.out.println("Hello I am inside here\n");
				//AnonId bnode = new AnonId(annIndex);
				Resource spanAsResource = outModel.createResource(annIndex);
				
				if (!outModel.getNsPrefixMap().containsValue(RDFConstants.itsrdfPrefix)) {
	                outModel.setNsPrefix("itsrdf", RDFConstants.itsrdfPrefix);
	            }

	            Literal literal = outModel.createLiteral(text, targetLang);
	            spanAsResource.addLiteral(outModel.getProperty(RDFConstants.itsrdfPrefix + "target"), literal);
		        
	}
	
	
	
	
	public static Model initializeOutputModel(){
		Model model = ModelFactory.createDefaultModel();
		//TODO Add NIF namespaces and more.
        Map<String,String> prefixes = new HashMap<String, String>();
        prefixes.put("xsd", "<http://www.w3.org/2001/XMLSchema#>");
        prefixes.put("nif", "<http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#>");
        prefixes.put("dktnif", "http://dkt.dfki.de/ontologies/nif#");
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

	public static void addLanguageAnnotation(Model outModel, String inputText, String documentURI, String language){
		
		int endTotalText = inputText.codePointCount(0, inputText.length());
		String documentUri = new StringBuilder().append(documentURI).append("#char=").append("0").append(',').append(endTotalText).toString();
        Resource documentResource = outModel.getResource(documentUri);
        outModel.add(documentResource, NIF.language, outModel.createTypedLiteral(language, XSDDatatype.XSDstring));
	}
	
	public static void addSentimentAnnotation(Model outModel, String inputText, String documentURI, double sentValue){
		
		int endTotalText = inputText.codePointCount(0, inputText.length());
		String documentUri = new StringBuilder().append(documentURI).append("#char=").append("0").append(',').append(endTotalText).toString();
        Resource documentResource = outModel.getResource(documentUri);
        outModel.add(documentResource, DKTNIF.sentimentValue, outModel.createTypedLiteral(sentValue, XSDDatatype.XSDdouble));
	}
		
	public static void addDateStats(Model outModel, String inputText, String documentURI, String meanDateRange){
		
		int endTotalText = inputText.codePointCount(0, inputText.length());
		String documentUri = new StringBuilder().append(documentURI).append("#char=").append("0").append(',').append(endTotalText).toString();
        Resource documentResource = outModel.getResource(documentUri);
        String[] norm = meanDateRange.split("_");
		String intervalStart = stringDateXSDDateTimeFormatter(norm[0]);
		String intervalEnd = stringDateXSDDateTimeFormatter(norm[1]);
		outModel.add(documentResource, DKTNIF.meanDateStart, outModel.createTypedLiteral(intervalStart, XSDDatatype.XSDdateTime));
		outModel.add(documentResource, DKTNIF.meanDateEnd, outModel.createTypedLiteral(intervalEnd, XSDDatatype.XSDdateTime));
        //outModel.add(documentResource, NIF.meanDateRange, outModel.createTypedLiteral(meanDateRange, XSDDatatype.XSDstring));
	}
	
	//public static void addGeoStats(Model outModel, String inputText, String documentURI, String centralGeoPoint, String geoStdDevs){
	public static void addGeoStats(Model outModel, String inputText, Double avgLatitude, Double avgLongitude, Double stdDevLatitude, Double stdDevLongitude, String docURI){
		
		// add prefix here
		//addPrefixToModel(outModel, "dfkinif", DKTNIF.uri);
		int endTotalText = inputText.codePointCount(0, inputText.length());
		String documentUri = new StringBuilder().append(docURI).append("#char=").append("0").append(',').append(endTotalText).toString();
        Resource documentResource = outModel.getResource(documentUri);
        //outModel.add(documentResource, NIF.centralGeoPoint, outModel.createTypedLiteral(centralGeoPoint, XSDDatatype.XSDstring));
        outModel.add(documentResource, DKTNIF.averageLatitude, outModel.createTypedLiteral(avgLatitude, XSDDatatype.XSDdouble));
        outModel.add(documentResource, DKTNIF.averageLongitude, outModel.createTypedLiteral(avgLongitude, XSDDatatype.XSDdouble));
        outModel.add(documentResource, DKTNIF.standardDeviationLatitude, outModel.createTypedLiteral(stdDevLatitude, XSDDatatype.XSDdouble));
        outModel.add(documentResource, DKTNIF.standardDeviationLongitude, outModel.createTypedLiteral(stdDevLongitude, XSDDatatype.XSDdouble));
        //outModel.add(documentResource, NIF.geoStandardDevs, outModel.createTypedLiteral(geoStdDevs, XSDDatatype.XSDstring));
	}


	public static void addEntityProperty(Model nifModel, int beginIndex, int endIndex, String documentURI, String info, Property prop, XSDDatatype dataType) {

		String entityUri = new StringBuilder().append(documentURI).append("#char=").append(Integer.toString(beginIndex)).append(',').append(Integer.toString(endIndex)).toString();
		Resource entityResource = nifModel.getResource(entityUri);
		nifModel.add(entityResource, prop, nifModel.createTypedLiteral(info, dataType));
	}
	
	public static void addEntityURI(Model nifModel, int beginIndex, int endIndex, String documentURI, String entURI){
		String entityNIFURI = new StringBuilder().append(documentURI).append("#char=").append(Integer.toString(beginIndex)).append(',').append(Integer.toString(endIndex)).toString();
		Resource entityResource = nifModel.getResource(entityNIFURI);
		nifModel.add(entityResource, ITSRDF.taIdentRef, nifModel.createResource(entURI));
	}
	
	public static void addLuceneIndexingInformation(Model outModel, String indexName, String indexPath){
		String documentUri = NIFReader.extractDocumentWholeURI(outModel);
        Resource documentResource = outModel.getResource(documentUri);
        outModel.add(documentResource, NIF.indexName, outModel.createTypedLiteral(indexName, XSDDatatype.XSDstring));
        outModel.add(documentResource, NIF.indexPath, outModel.createTypedLiteral(indexPath, XSDDatatype.XSDstring));
	}

	public static void changeLuceneIndexingInformation(Model outModel, String indexName, String indexPath){
		String documentUri = NIFReader.extractDocumentWholeURI(outModel);
        Resource documentResource = outModel.getResource(documentUri);
        NodeIterator it = outModel.listObjectsOfProperty(documentResource, NIF.indexPath);
        while(it.hasNext()){
            outModel.remove(documentResource, NIF.indexPath, it.next());
        }
        outModel.add(documentResource, NIF.indexName, outModel.createTypedLiteral(indexName, XSDDatatype.XSDstring));
        outModel.add(documentResource, NIF.indexPath, outModel.createTypedLiteral(indexPath, XSDDatatype.XSDstring));
	}

	public static void addDocumentClassification(Model outModel, String inputText, String documentURI, String label){
		int endTotalText = inputText.codePointCount(0, inputText.length());
		String documentUri = new StringBuilder().append(documentURI).append("#char=").append("0").append(',').append(endTotalText).toString();
        Resource documentResource = outModel.getResource(documentUri);
        outModel.add(documentResource, NIF.documentClassification, outModel.createTypedLiteral(label, XSDDatatype.XSDstring));
	}

	public static void addTranslation(Model outModel, String translation){
		String inputText = NIFReader.extractIsString(outModel);
		String documentURI = NIFReader.extractDocumentURI(outModel);
		int endTotalText = inputText.codePointCount(0, inputText.length());
		String documentUri = new StringBuilder().append(documentURI).append("#char=").append("0").append(',').append(endTotalText).toString();
        Resource documentResource = outModel.getResource(documentUri);
        outModel.add(documentResource, NIF.translation, outModel.createTypedLiteral(translation, XSDDatatype.XSDstring));
	}

	public static void addSummary(Model outModel, String summary){
		String inputText = NIFReader.extractIsString(outModel);
		String documentURI = NIFReader.extractDocumentURI(outModel);
		int endTotalText = inputText.codePointCount(0, inputText.length());
		String documentUri = new StringBuilder().append(documentURI).append("#char=").append("0").append(',').append(endTotalText).toString();
        Resource documentResource = outModel.getResource(documentUri);
        outModel.add(documentResource, NIF.summary, outModel.createTypedLiteral(summary, XSDDatatype.XSDstring));
	}

	public static void addTopicModelling(Model outModel, String inputText, String documentURI, String label){
		int endTotalText = inputText.codePointCount(0, inputText.length());
		String documentUri = new StringBuilder().append(documentURI).append("#char=").append("0").append(',').append(endTotalText).toString();
        Resource documentResource = outModel.getResource(documentUri);
        outModel.add(documentResource, NIF.topicModelling, outModel.createTypedLiteral(label, XSDDatatype.XSDstring));
	}

	public static void addAnnotationUnit(Model outModel, String uri, String documentURI, double confidence){
		addPrefixToModel(outModel, "nif-ann", "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-annotation#");
		Resource resource = outModel.getResource(uri);
        Resource anon = outModel.createResource();
//        Bag bag = outModel.createBag();
        anon.addProperty(DKTNIF.isHyperlinkedTo, outModel.createResource(documentURI));
        anon.addLiteral(DKTNIF.hasHyperlinkedConfidence, confidence);
        outModel.add(resource, NIFANN.AnnotationUnit, anon);
	}

	public static void addBabelnetAnnotation(Model outModel, String documentURI, String sense, String language){
		Resource resource = outModel.getResource(documentURI);
        outModel.add(resource, DKTNIF.babelnetSense, sense+"@"+language);
	}

	public static void addSextupleMAEAnnotation(Model outModel, String documentURI, String person, String origin,
			String destination, Date dTime, Date aTime, String travelMode, int startIndex, int endIndex, String text, float score){
		
		String spanUri = new StringBuilder().append(documentURI).append("#char=").append(startIndex).append(',').append(endIndex).toString();

		Resource resource = outModel.getResource(spanUri);
		outModel.add(resource, RDF.type, DKTNIF.MovementActionEvent);
		outModel.add(resource, NIF.beginIndex, outModel.createTypedLiteral(startIndex, XSDDatatype.XSDnonNegativeInteger));
		outModel.add(resource, NIF.endIndex, outModel.createTypedLiteral(endIndex, XSDDatatype.XSDnonNegativeInteger));
		if(person!=null){
			outModel.add(resource, DKTNIF.maePerson, outModel.createTypedLiteral(person, XSDDatatype.XSDstring));
		}
		if(origin!=null){
			outModel.add(resource, DKTNIF.maeOrigin, outModel.createTypedLiteral(origin, XSDDatatype.XSDstring));
		}
		if(destination!=null){
			outModel.add(resource, DKTNIF.maeDestination, outModel.createTypedLiteral(destination, XSDDatatype.XSDstring));
		}
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		if(dTime!=null){
			outModel.add(resource, DKTNIF.maeDepartureTime, outModel.createTypedLiteral(df.format(dTime), XSDDatatype.XSDdateTime));
		}
		if(aTime!=null){
			outModel.add(resource, DKTNIF.maeArrivalTime, outModel.createTypedLiteral(df.format(aTime), XSDDatatype.XSDdateTime));
		}
		if(travelMode!=null){
			outModel.add(resource, DKTNIF.maeTravelMode, outModel.createTypedLiteral(travelMode, XSDDatatype.XSDstring));
		}
		if(text!=null){
			outModel.add(resource, NIF.anchorOf, outModel.createTypedLiteral(text, XSDDatatype.XSDstring));
		}
		outModel.add(resource, DKTNIF.maeScore, outModel.createTypedLiteral(score, XSDDatatype.XSDfloat));
	}

	public static void addMAETransportationMode(Model outModel, String documentURI, String travelMode, int startIndex, int endIndex){
		
		String spanUri = new StringBuilder().append(documentURI).append("#char=").append(startIndex).append(',').append(endIndex).toString();
		Resource resource = outModel.getResource(spanUri);
		outModel.add(resource, NIF.beginIndex, outModel.createTypedLiteral(startIndex, XSDDatatype.XSDnonNegativeInteger));
		outModel.add(resource, NIF.endIndex, outModel.createTypedLiteral(endIndex, XSDDatatype.XSDnonNegativeInteger));
        outModel.add(resource, DKTNIF.travelMode, outModel.createTypedLiteral(travelMode, XSDDatatype.XSDstring));
	}

	public static void addMAEMovementVerb(Model outModel, String documentURI, String verb, int startIndex, int endIndex){
		String spanUri = new StringBuilder().append(documentURI).append("#char=").append(startIndex).append(',').append(endIndex).toString();
		Resource resource = outModel.getResource(spanUri);
		outModel.add(resource, RDF.type, DKTNIF.MovementTrigger);
		outModel.add(resource, NIF.beginIndex, outModel.createTypedLiteral(startIndex, XSDDatatype.XSDnonNegativeInteger));
		outModel.add(resource, NIF.endIndex, outModel.createTypedLiteral(endIndex, XSDDatatype.XSDnonNegativeInteger));
        outModel.add(resource, DKTNIF.movementVerb, outModel.createTypedLiteral(verb, XSDDatatype.XSDstring));
	}
	
	public static void addMetaDataInformation(Model outModel, String documentURI, String author, String date, String location){
		if(author!=null){
			String spanUriAuthor = new StringBuilder().append(documentURI).append("#char=author").toString();
			Resource resourceAuthor = outModel.getResource(spanUriAuthor);
			outModel.add(resourceAuthor, RDF.type, NIF.String);
			outModel.add(resourceAuthor, RDF.type, NIF.RFC5147String);
			outModel.add(resourceAuthor, NIF.beginIndex, outModel.createTypedLiteral(0, XSDDatatype.XSDnonNegativeInteger));
			outModel.add(resourceAuthor, NIF.endIndex, outModel.createTypedLiteral(0, XSDDatatype.XSDnonNegativeInteger));
	        outModel.add(resourceAuthor, NIF.anchorOf, outModel.createTypedLiteral(author, XSDDatatype.XSDstring));
	        outModel.add(resourceAuthor, ITSRDF.taClassRef, DBO.person);
			outModel.add(resourceAuthor, NIF.referenceContext, outModel.createResource(NIFReader.extractDocumentWholeURI(outModel)));
		}
		if(date!=null){
			String spanUriDate = new StringBuilder().append(documentURI).append("#char=date").toString();
			Resource resourceDate = outModel.getResource(spanUriDate);
			outModel.add(resourceDate, RDF.type, NIF.String);
			outModel.add(resourceDate, RDF.type, NIF.RFC5147String);
			outModel.add(resourceDate, NIF.beginIndex, outModel.createTypedLiteral(0, XSDDatatype.XSDnonNegativeInteger));
			outModel.add(resourceDate, NIF.endIndex, outModel.createTypedLiteral(0, XSDDatatype.XSDnonNegativeInteger));
	        outModel.add(resourceDate, NIF.anchorOf, outModel.createTypedLiteral(date, XSDDatatype.XSDstring));
	        outModel.add(resourceDate, ITSRDF.taClassRef, TIME.temporalEntity);
			outModel.add(resourceDate, NIF.referenceContext, outModel.createResource(NIFReader.extractDocumentWholeURI(outModel)));
		}
		if(location!=null){
			String spanUriLocation = new StringBuilder().append(documentURI).append("#char=location").toString();
			Resource resourceLocation = outModel.getResource(spanUriLocation);
			outModel.add(resourceLocation, RDF.type, NIF.String);
			outModel.add(resourceLocation, RDF.type, NIF.RFC5147String);
			outModel.add(resourceLocation, NIF.beginIndex, outModel.createTypedLiteral(0, XSDDatatype.XSDnonNegativeInteger));
			outModel.add(resourceLocation, NIF.endIndex, outModel.createTypedLiteral(0, XSDDatatype.XSDnonNegativeInteger));
	        outModel.add(resourceLocation, NIF.anchorOf, outModel.createTypedLiteral(location, XSDDatatype.XSDstring));
	        outModel.add(resourceLocation, ITSRDF.taClassRef, DBO.location);
			outModel.add(resourceLocation, NIF.referenceContext, outModel.createResource(NIFReader.extractDocumentWholeURI(outModel)));
		}
	
	}
}
