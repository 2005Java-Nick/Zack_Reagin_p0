package com.revature.battleship.exception;

public class HeightLessThanOneException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5752364232374044444L;
	
	public static final String MSG = "Height must be at least 1.";
	
	public HeightLessThanOneException() {
		super(MSG);
	}
	
	public HeightLessThanOneException(Throwable cause) {
		super(MSG, cause);
	}

}
