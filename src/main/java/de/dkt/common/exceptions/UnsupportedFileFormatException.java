package de.dkt.common.exceptions;

@SuppressWarnings("serial")
public class UnsupportedFileFormatException extends RuntimeException{

	public UnsupportedFileFormatException(String msg){
		super(msg);
	}
}
