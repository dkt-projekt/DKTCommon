package de.dkt.common.niftools;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;

public class DKTNIF {

    protected static final String uri = "http://dkt.dfki.de/ontologies/nif#";
    protected static final String defaultPrefix = "http://dkt.dfki.de/documents/";

    /**
     * returns the URI for this schema
     * 
     * @return the URI for this schema
     */
    public static String getURI() {
        return uri;
    }
    public static String getDefaultPrefix() {
        return defaultPrefix;
    }

    public static final Resource resource(String local) {
        return ResourceFactory.createResource(uri + local);
    }

    protected static final Property property(String local) {
        return ResourceFactory.createProperty(uri, local);
    }
      

    //public static final Resource location = resource("location");
    //public static final Resource person = resource("person");
    //public static final Resource organization = resource("organization");
    //public static final Resource date = resource("date");
    

    public static final Property DocumentPath = property("DocumentPath");
    public static final Property DocumentNIFPath = property("DocumentNIFPath");

    public static final Resource anchorOf = resource("anchorOf");
    public static final Property beginIndex = property("beginIndex");
    public static final Property confidence = property("confidence");
    public static final Property isString = property("isString");
    public static final Property endIndex = property("endIndex");
    public static final Property entity = property("entity");
    public static final Property keyword = property("keyword");
    
    public static final Property averageLatitude = property("averageLatitude");
    public static final Property averageLongitude = property("averageLongitude");
    public static final Property standardDeviationLatitude = property("standardDeviationLatitude");
    public static final Property standardDeviationLongitude = property("standardDeviationLongitude");
    
    public static final Property meanDateStart = property("meanDateStart");
    public static final Property meanDateEnd = property("meanDateEnd");
     
    
    public static String createDocumentURI(){
    	return defaultPrefix+"doc"+((int)(Math.random()*9000)+1000);
    }

}
