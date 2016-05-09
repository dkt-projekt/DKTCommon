package de.dkt.common.niftools;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;

public class DBO {

	public static final String uri = "http://dbpedia.org/ontology/";
	
	
    protected static final Resource resource(String local) {
        return ResourceFactory.createResource(uri + local);
    }

    protected static final Property property(String local) {
        return ResourceFactory.createProperty(uri, local);
    }
	
    public static final Property birthDate = property("birthDate");
    public static final Property deathDate = property("deathDate");
    public static final Property person = property("Person");
    public static final Property location = property("Location");
    public static final Property organisation = property("Organisation");
	
}
