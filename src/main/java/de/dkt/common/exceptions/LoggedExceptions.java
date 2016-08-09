package de.dkt.common.exceptions;

import org.apache.log4j.Logger;

import de.dkt.common.feedback.InteractionManagement;
import eu.freme.common.exception.BadRequestException;
import eu.freme.common.exception.ExternalServiceFailedException;

public class LoggedExceptions {

	public static BadRequestException generateLoggedBadRequestException(Logger logger,String msg){
    	logger.error(msg);
    	return new BadRequestException(msg);
	}
	
	public static BadRequestException generateInteractionLoggedBadRequestException(Logger logger,String msg, String user, String objectId){
    	logger.error(msg);
    	InteractionManagement.sendInteraction(user, "error", objectId, msg, "", "Exception", msg, "");
    	return new BadRequestException(msg);
	}
	
	public static ExternalServiceFailedException generateLoggedExternalServiceFailedException(Logger logger,String msg){
    	logger.error(msg);
    	return new ExternalServiceFailedException(msg);
	}
	

	
}
