package de.dkt.common.tools;

import eu.freme.common.exception.BadRequestException;

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
	
	public static void checkInList (String param, String list, String message) throws BadRequestException {
		checkNotNullOrEmpty(param, message);
		String parts[] = list.split(";");
		for (String string : parts) {
			if(param.equalsIgnoreCase(string)){
				return;
			}
		}
        throw new BadRequestException("Param "+message+" not present in list: ["+list+"]");
    }
	
}
