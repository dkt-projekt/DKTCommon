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

<<<<<<< HEAD
    public static final Property AnnotationUnit = property("annotationUnit");


}
=======
    //public static final Property taClassRef = property("taClassRef");
    //public static final Property taConfidence = property("taConfidence");
    //public static final Property taIdentRef = property("taIdentRef");
    //public static final Property taSource = property("taSource");

    //public static final Property target = property("target");
}

>>>>>>> branch 'master' of https://github.com/dkt-projekt/DKTCommon
