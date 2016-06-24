package de.dkt.common.niftools;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;

public class RDFS extends com.hp.hpl.jena.vocabulary.RDFS{

	public static final String uri = "http://www.w3.org/2000/01/rdf-schema#";
	
	
//    protected static final Resource resource(String local) {
//        return ResourceFactory.createResource(uri + local);
//    }
//
//    protected static final Property property(String local) {
//        return ResourceFactory.createProperty(uri, local);
//    }
	
    public static final Property arabicLabel = property("label_ar");
   
	
}
