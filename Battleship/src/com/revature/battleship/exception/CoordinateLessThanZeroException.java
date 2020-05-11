package com.revature.battleship.exception;

public class CoordinateLessThanZeroException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6944998620446634628L;


	public static final String MSG = "Coordinates must be greater than zero.";
	
	public CoordinateLessThanZeroException() {
		super(MSG);
	}

	public CoordinateLessThanZeroException(Throwable cause) {
		super(MSG);
	}
}
