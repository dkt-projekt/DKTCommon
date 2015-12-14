package eu.freme.broker.tools;

import eu.freme.broker.exception.BadRequestException;

public class ParameterChecker {

	
	public static void checkNotNull (String param, String message) throws BadRequestException {
    	if( param==null ){
            throw new BadRequestException("NULL "+message+" param specified");
    	}
    }
	
	public static void checkNotNullOrEmpty (String param, String message) throws BadRequestException {
    	if( param==null || param.equals("") ){
            throw new BadRequestException("No "+message+" param specified");
    	}
    }
	
}
