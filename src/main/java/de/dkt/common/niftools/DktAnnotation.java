package de.dkt.common.niftools;

import java.util.HashMap;
import java.util.Map;

import com.hp.hpl.jena.vocabulary.RDF;

public class DktAnnotation {

	private String uri;
	private Map<String,String> properties;
	
	public DktAnnotation() {
		properties = new HashMap<String, String>();
	}
	
	public DktAnnotation(String uri, Map<String, String> properties) {
		super();
		this.uri = uri;
		this.properties = properties;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}
	
	public String getText(){
//		Set<String> keys = properties.keySet();
//		for (String key : keys) {
//			System.out.println(key+"--"+properties.get(key));
//		}
		if(this.getType().equalsIgnoreCase("temp")){
			return properties.get(TIME.intervalStarts.toString());
		}
		return properties.get(NIF.anchorOf.toString());
	}
	
	public int getStart(){
		int i = Integer.parseInt(properties.get(NIF.beginIndex.toString()));
		return i;
	}
	
	public int getEnd(){
		int i = Integer.parseInt(properties.get(NIF.endIndex.toString()));
		return i;
	}
	
	public String getType(){
		if(properties.isEmpty()){
			return "empty";
		}
		String type = properties.get(RDF.type.toString());
		String taClassRef = properties.get(ITSRDF.taClassRef.toString());
		if(type!=null && type.equalsIgnoreCase(DKTNIF.movementVerb.toString())){
			return "triggerVerb";
		}
		else if(type!=null && type.equalsIgnoreCase(DKTNIF.travelMode.toString())){
			return "triggerTerm";
		}
		else{
			if(taClassRef.equalsIgnoreCase(DBO.person.toString())){
				return "person";
			}
			else if(taClassRef.equalsIgnoreCase(DBO.location.toString())){
				return "location";
			}
			else if(taClassRef.equalsIgnoreCase(DBO.organisation.toString())){
				return "organization";
			}
			else if(taClassRef.equalsIgnoreCase(TIME.temporalEntity.toString())){
				return "temp";
			}
		}
		return "";
	}
		
}
