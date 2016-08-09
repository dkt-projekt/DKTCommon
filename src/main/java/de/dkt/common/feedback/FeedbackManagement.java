package de.dkt.common.feedback;

import org.apache.log4j.Logger;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

public class FeedbackManagement {

	public static Logger logger = Logger.getLogger(FeedbackManagement.class);

	public static boolean sendFeedback(String user, String additionalInformation, String objectId,String value){
		/**
		 * ai = new FeedbackInformation(objectId, value);
		 */
		return sendFeedback(user, "usage", additionalInformation, objectId, null, value, null, null);
	}
	
	public static boolean sendGeneral(String user, String additionalInformation){
		/**
		 * ai = new GeneralInformation(additionalInformation);
		 */
		return sendFeedback(user, "general", additionalInformation, null, null, null, null, null);
	}
	
	public static boolean sendRelevance(String user, String additionalInformation, String objectId, String relevanceValue){
		/**
		 * ai = new RelevanceInformation(relevanceValue, objectId);
		 */
		return sendFeedback(user, "relevance", additionalInformation, objectId, relevanceValue, null, null, null);
	}
	
	public static boolean sendError(String user, String additionalInformation, String errorId, String errorType, String value){
		/**
		 * ai = new ErrorInformation(errorId, errorType, value);
		 */
		return sendFeedback(user, "error", additionalInformation, null, null, value, errorId, errorType);
	}
	
	public static boolean sendUsage(String user, String additionalInformation, String objectId,String value){
		/**
		 * ai = new UsageInformation(objectId, value);
		 */
		return sendFeedback(user, "usage", additionalInformation, objectId, null, value, null, null);
	}

	public static boolean sendFeedback(String user, String interactionType, String additionalInformation, String objectId,
			String relevanceValue, String value, String errorId, String errorType){
		/**
		 * interactionType: feedback, usage, relevance, usage, general
		 * ai = new UsageInformation(objectId, value);
		 * ai = new ErrorInformation(errorId, errorType, value);
		 * ai = new RelevanceInformation(relevanceValue, objectId);
		 * ai = new GeneralInformation(additionalInformation);
		 */
		try{
			HttpResponse<String> response = Unirest.post("http://dev.digitale-kuratierung.de/api/e-logging/storeLoggingInformation")
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
					//.body(annotatedContent)
					.asString();
			if(response.getStatus()!=200){
				return false;
			}
		}
		catch(Exception e){
			e.printStackTrace();
			logger.error("Error storing feedback", e);
			return false;
		}
		return true;
	}

}
