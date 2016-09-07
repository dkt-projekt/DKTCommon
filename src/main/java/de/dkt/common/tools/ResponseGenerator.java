package de.dkt.common.tools;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import eu.freme.common.conversion.rdf.RDFConstants.RDFSerialization;
import eu.freme.common.exception.BadRequestException;

public class ResponseGenerator {

    public static ResponseEntity<String> successResponse(String body, String contentType) throws BadRequestException {
    	if(contentType==null){
    		contentType = "text/plain";
    	}
    	RDFSerialization format = RDFSerialization.fromValue(contentType);
//    	System.out.println(contentType);
//   	System.out.println(format);
    	HttpHeaders responseHeaders = new HttpHeaders();
    	responseHeaders.add("Content-Type", format.contentType());
    	ResponseEntity<String> response = new ResponseEntity<String>(body, responseHeaders, HttpStatus.OK);
    	return response;
    }

}
