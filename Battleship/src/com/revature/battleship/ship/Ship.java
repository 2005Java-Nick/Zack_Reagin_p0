package com.revature.battleship.ship;

import org.apache.log4j.Logger;

import com.revature.battleship.exception.ShipLengthLessThanOneException;

/*
 * The Ship class is used to represent ships on a grid. Each Ship object has a String ID, used
 * for identification, a length, for how many spaces it will occupy on a grid, and an orientation,
 * used to identify if the ship is placed vertically or horizontally on a grid.
 */
public class Ship {

	private static Logger log = Logger.getRootLogger();
	
	private String ID;
	private int length;
	private String orientation;
	
	/*
	 * Takes a String ID and an int length as parameters. The String ID is used to identify
	 * the ship when it is placed on a map, and the length corresponds to how many grid spaces
	 * the ship occupies.
	 */
	public Ship(String ID, int length) {
		this.ID = ID;
		try {
			this.setLength(length);
		} catch (ShipLengthLessThanOneException e) {
			log.error("Ship length cannot be less than one.");
			e.printStackTrace();
		}
		this.setRandomOrientation();
	}

	/*
	 * Getter method that returns the ID of a Ship object as a String.
	 */
	public String getID() {
		return this.ID;
	}
	
	/*
	 * Getter method that returns the length of a Ship object as an int.
	 */
	public int getLength() {
		return this.length;
	}

	/*
	 * Setter method that sets the length of a Ship object to the given int length parameter.
	 */
	public void setLength(int length) throws ShipLengthLessThanOneException {
		if(length > 0) {
			this.length = length;
		} else {
			throw new ShipLengthLessThanOneException();
		}
	}
	
	/*
	 * Getter method that returns the orientation of a Ship object as a String.
	 */
	public String getOrientation() {
		return this.orientation;
	}
	
	/*
	 * Setter method that sets the orientation of a Ship object to the given String orientation
	 * parameter.
	 */
	private void setOrientation(String orientation) {
		this.orientation = orientation;
	}
	
	/*
	 * Setter method that sets the orientation of a Ship object to either vertical or horizontal
	 * (Approximately 50% chance of either)
	 */ 
	private void setRandomOrientation() {
		int orientation = (int) (Math.random() + 0.5);
		if(orientation == 0) {
			this.setOrientation("vertical"); 
		} else if(orientation == 1) {
			this.setOrientation("horizontal"); 
		}
	}
	
}
