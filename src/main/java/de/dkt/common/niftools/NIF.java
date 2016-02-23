package de.dkt.common.niftools;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;

public class NIF {

    protected static final String uri = "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#";

    /**
     * returns the URI for this schema
     * 
     * @return the URI for this schema
     */
    public static String getURI() {
        return uri;
    }

    protected static final Resource resource(String local) {
        return ResourceFactory.createResource(uri + local);
    }

    protected static final Property property(String local) {
        return ResourceFactory.createProperty(uri, local);
    }

    public static final Resource Annotation = resource("Annotation");
    public static final Resource Context = resource("Context");
    public static final Resource String = resource("String");
    public static final Resource RFC5147String = resource("RFC5147String");

    public static final Property anchorOf = property("anchorOf");
    public static final Property beginIndex = property("beginIndex");
    public static final Property confidence = property("confidence");
    public static final Property isString = property("isString");
    public static final Property endIndex = property("endIndex");
    public static final Property entity = property("entity");
    public static final Property posTag = property("posTag");
    public static final Property keyword = property("keyword");
    public static final Property referenceContext = property("referenceContext");
    public static final Property topic = property("topic");
        
    public static final Property meanDate = property("meanDate");
    public static final Property stdevDate = property("stdevDate");

    public static final Property meanDateRange = property("meanDateRange");
    public static final Property geoPoint = property("geoPoint");
    public static final Property centralGeoPoint = property("centralGeoPoint");
    public static final Property geoStandardDevs = property("geoStandardDevs");

    public static final Property documentClassification = property("documentClassificationLabel");
    public static final Property topicModelling = property("topicModellingLabel");

    public static final Property indexName = property("indexName");
    public static final Property indexPath = property("indexPath");
    
    public static final Property birthDate = property("birthDate");
    public static final Property deathDate = property("deathDate");
    public static final Property normalizedDate = property("normalizedDate");
//    public static final Property  = property("");
//    public static final Property  = property("");
//    public static final Property  = property("");
//    public static final Property  = property("");
    public static final Property orgType = property("organizationType");

}
