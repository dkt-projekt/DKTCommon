package de.dkt.common.exceptions;

public class UnauthorizedAccessException extends Exception{
	private static final long serialVersionUID = 1L;

	public UnauthorizedAccessException() {
	}
	
	public UnauthorizedAccessException(String message) {
		super(message);
	}

}
