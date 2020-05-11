package com.revature.battleship.exception;

public class ShipLengthLessThanOneException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5129996062630049532L;

	public static final String MSG = "Ship length must be at least 1.";
	
	public ShipLengthLessThanOneException() {
		super(MSG);
	}
	
	public ShipLengthLessThanOneException(Throwable cause) {
		super(MSG, cause);
	}
}
