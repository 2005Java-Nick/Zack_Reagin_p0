package com.revature.battleship.exception;

public class WidthLessThanOneException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4111913605442877280L;
	
	public static final String MSG = "Width must be at least 1.";
	
	public WidthLessThanOneException() {
		super(MSG);
	}

	public WidthLessThanOneException(Throwable cause) {
		super(MSG);
	}


}
