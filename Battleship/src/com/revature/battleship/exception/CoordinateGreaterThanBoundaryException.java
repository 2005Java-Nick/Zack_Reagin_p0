package com.revature.battleship.exception;

public class CoordinateGreaterThanBoundaryException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4883787247846439875L;

public static final String MSG = "Coordinates must be less than the boundary of the map.";
	
	public CoordinateGreaterThanBoundaryException() {
		super(MSG);
	}

	public CoordinateGreaterThanBoundaryException(Throwable cause) {
		super(MSG);
	}
}
