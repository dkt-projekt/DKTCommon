package de.dkt.common.niftools;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;

/**
 * The NIF 2.1 Annotation class. <b>Note</b>
 * that this class might need to be edited later, this is a temporary shortcut for esmt/xlingual.
 * 
 * @author Ankit Srivastava
 * 
 */
public class NIFANN {

    protected static final String uri = "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-annotation#";

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

    public static final Property AnnotationUnit = property("annotationUnit");

}