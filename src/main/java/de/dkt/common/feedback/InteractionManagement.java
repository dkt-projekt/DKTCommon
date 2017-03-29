package de.dkt.common.feedback;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

@Component
public class InteractionManagement {

	public static Logger logger = Logger.getLogger(InteractionManagement.class);

	private static String serverAddress;

	@Value("${dkt.feedback.serveraddress}")
	private String serverAddress2;
//static String serverAddress = "https://dev.digitale-kuratierung.de/api/e-logging/storeInteractionInformation";

	public InteractionManagement() {
		
	}
	
	@PostConstruct
	public void init(){
		InteractionManagement.serverAddress = serverAddress2;
	}
	
	public static void printAddress(){
		System.out.println(serverAddress);
	}
	
	public static boolean sendInteraction(String user, String interactionType, String objectId, String value, 
			String relevanceValue, String errorId, String errorType,String additionalInformation){
		try{
			HttpResponse<String> response = Unirest.post(serverAddress)
					.queryString("serviceType", "database")
					.queryString("feedbackServiceName", "serviceName")
					.queryString("create", false)
					.queryString("user", user)
					.queryString("interactionType", interactionType)
					.queryString("additionalInformation", additionalInformation)
					.queryString("objectId", objectId)
					.queryString("relevancevalue", relevanceValue)
					.queryString("value", value)
					.queryString("errorId", errorId)
					.queryString("errorType", errorType)
					.asString();
			if(response.getStatus()!=200){
				logger.error("ERROR storing interaction for "+objectId);
				return false;
			}
			logger.info("Interaction properly stored for: "+objectId);
		}
		catch(Exception e){
			e.printStackTrace();
			logger.error("Error storing feedback", e);
			return false;
		}
		return true;
	}

}
