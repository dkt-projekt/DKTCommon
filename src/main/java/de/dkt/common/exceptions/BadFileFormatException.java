package de.dkt.common.exceptions;

@SuppressWarnings("serial")
public class BadFileFormatException extends RuntimeException{

	public BadFileFormatException(String msg){
		super(msg);
	}
}
