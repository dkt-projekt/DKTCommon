package de.dkt.common.feedback;

import org.apache.log4j.Logger;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

public class InteractionManagement {

	public static Logger logger = Logger.getLogger(InteractionManagement.class);

	public static boolean sendInteraction(String user, String interactionType, String objectId, String value, 
			String relevanceValue, String errorId, String errorType,String additionalInformation){
		try{
			HttpResponse<String> response = Unirest.post("https://dev.digitale-kuratierung.de/api/e-logging/storeInteractionInformation")
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
