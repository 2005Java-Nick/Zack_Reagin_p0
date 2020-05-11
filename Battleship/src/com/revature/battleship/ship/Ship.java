package com.revature.battleship.ship;

import org.apache.log4j.Logger;

import com.revature.battleship.exception.ShipLengthLessThanOneException;

public class Ship {

	private static Logger log = Logger.getRootLogger();
	private String ID;
	private int length;
	private String orientation;
	//private String[][] status; Currently unimplemented
	
	public Ship(String ID, int length) {
		this.ID = ID;
		try {
			this.setLength(length);
		} catch (ShipLengthLessThanOneException e) {
			log.error("Ship length cannot be less than one.");
			e.printStackTrace();
		}
		//this.status = new String [length][2];
		this.setRandomOrientation();
	}

	// Get Ship ID
	public String getID() {
		return this.ID;
	}
	
	// Get Ship length
	public int getLength() {
		return this.length;
	}

	// Set Ship length
	public void setLength(int length) throws ShipLengthLessThanOneException {
		if(length > 0) {
			this.length = length;
		} else {
			throw new ShipLengthLessThanOneException();
		}
	}
	
	// Get Ship Orientation
	public String getOrientation() {
		return this.orientation;
	}
	
	// Get Ship Orientation
	private void setOrientation(String orientation) {
		this.orientation = orientation;
	}
	
	// Set Ship Orientation to either vertical or horizontal (Approx. 50% chance of either)
	private void setRandomOrientation() {
		int orientation = (int) (Math.random() + 0.5);
		if(orientation == 0) {
			this.setOrientation("vertical"); 
		} else if(orientation == 1) {
			this.setOrientation("horizontal"); 
		}
	}
	
	//The following methods are unimplemented. May be implemented in future update.
	/*
	private void setCoordinates() {
		
	}
	
	private void hit(int x, int y) {
		
	}
	
	private boolean isSunk() {
		return false;
	}*/
}
