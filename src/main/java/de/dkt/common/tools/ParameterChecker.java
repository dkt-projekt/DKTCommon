package de.dkt.common.tools;

import org.apache.log4j.Logger;

import eu.freme.common.exception.BadRequestException;

public class ParameterChecker {
	
	public static void checkNotNull (String param, String message, Logger logger) throws BadRequestException {
    	if( param==null ){
            logger.error("NULL "+message+" param specified");
            throw new BadRequestException("NULL "+message+" param specified");
    	}
    }
	
	public static void checkNotNullOrEmpty (String param, String message, Logger logger) throws BadRequestException {
    	if( param==null || param.equals("") ){
    		logger.error("No "+message+" param specified");
            throw new BadRequestException("No "+message+" param specified");
    	}
    }
	
	public static void checkInList (String param, String list, String message, Logger logger) throws BadRequestException {
		checkNotNullOrEmpty(param, message, logger);
		String parts[] = list.split(";");
		for (String string : parts) {
			if(param.equalsIgnoreCase(string)){
				return;
			}
		}
		logger.error("Param "+message+" not present in list: ["+list+"]");
        throw new BadRequestException("Param "+message+" not present in list: ["+list+"]");
    }
	
}
