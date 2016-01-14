package eu.freme.broker.tools;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import eu.freme.common.exception.BadRequestException;

public class ResponseGenerator {

    public static ResponseEntity<String> successResponse(String body, String contentType) throws BadRequestException {
    	HttpHeaders responseHeaders = new HttpHeaders();
    	responseHeaders.add("Content-Type", contentType);
    	ResponseEntity<String> response = new ResponseEntity<String>(body, responseHeaders, HttpStatus.OK);
    	return response;
    }
}
