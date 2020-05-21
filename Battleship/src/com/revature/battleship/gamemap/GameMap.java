package com.revature.battleship.gamemap;

import java.util.ArrayList;
import org.apache.log4j.Logger;

import com.revature.battleship.exception.CoordinateGreaterThanBoundaryException;
import com.revature.battleship.exception.CoordinateLessThanZeroException;
import com.revature.battleship.exception.HeightLessThanOneException;
import com.revature.battleship.exception.WidthLessThanOneException;
import com.revature.battleship.ship.Ship;

public class GameMap {

	private static Logger log = Logger.getRootLogger();
	
	private int height;
	private int width;
	private String[][] contents;
	private final int MIN_X = 0;
	private final int MIN_Y = 0;
	private ArrayList<String> attacked = new ArrayList<String>();
	private int hits = 0;
	
	public GameMap(int height, int width) {
		this.height = height;
		this.width = width;
		contents = new String [this.height][this.width];
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public void setHeight(int height) throws HeightLessThanOneException {
		if(height < 0) {
			throw new HeightLessThanOneException();
		} else {
			this.height = height;
		} 
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public void setWidth(int width) throws WidthLessThanOneException {
		if (width < 0) {
			throw new WidthLessThanOneException();
		} else {
			this.width = width;
		}
	}
	
	public boolean checkIfCoordinatesValid(String loc) {
		if(loc == null) {
			return false;
		}
		loc = loc.toUpperCase();
		String alphabet = "ABCDEFGHIJ";
		String numbers = "123456789";
		String ten = "10";
		if (loc.length() < 2 || loc.length() > 3) {
			return false;
		} else if (alphabet.indexOf(loc.charAt(0)) == -1){
			return false;
		} else if (loc.length() == 3 && !loc.substring(1).equalsIgnoreCase(ten)) {
			return false;
		} else if (numbers.indexOf(loc.charAt(1)) == -1) {
			return false;
		}
		return true;
	}
	
	// Returns what is located at the given coordinates in the map
	public String getLocation(int y, int x) throws CoordinateLessThanZeroException, CoordinateGreaterThanBoundaryException {
		if(y < 0 || x < 0) {
			throw new CoordinateLessThanZeroException();
		} else if (y > this.getHeight() || x > this.getWidth()) {
			throw new CoordinateGreaterThanBoundaryException();
		} else {
			return this.contents[y][x];
		}
	}
	
	// Checks if location has been attacked previously. If not, updates map as a hit or miss, and returns true.
	public boolean attackLocation(int y, int x) {
		String location = this.getCoordinates(y, x);
		if(attacked.contains(location)) {
			return false;
		} else {
			attacked.add(location);
			try {
				if(this.getLocation(y, x) == null) {
					this.contents[y][x] = "O";
					System.out.println("Miss!");
				} else {
					this.contents[y][x] = "X";
					System.out.println("Hit!");
					hits++;
				}
			} catch (CoordinateLessThanZeroException e) {
				log.error("Coordinate less than zero", e);
				e.printStackTrace();
			} catch (CoordinateGreaterThanBoundaryException e) {
				log.error("Coordinate out of bounds", e);
				e.printStackTrace();
			}
			return true;
		}
	}
	
	// Returns total number of hits.
	public int getHits() {
		return this.hits;
	}
	
	// Place a ship on the map by first using the findLocation method to find a vacant location on the map
	public void placeShip(Ship s) {
		int[] location = findLocation(s);
		if(s.getOrientation() == "vertical") {
			for (int y = location[1]; y < s.getLength() + location[1]; y++) {
				contents[y][location[0]] = s.getID();
			}
		} else if(s.getOrientation() == "horizontal") {
			for (int x = location[0]; x < s.getLength() + location[0]; x++) {
				contents[location[1]][x] = s.getID();
			}
		} 
	}
	
	/*
	 *  Selects a random location on the map, and determines if a given ship can be placed there.
	 *  Continues generating random locations until a suitable location is found.
	 */
	private int[] findLocation(Ship s) {
		int xAdjust = 0;
		int yAdjust = 0;
		String orientation = s.getOrientation();
		if(orientation == "vertical") {
			yAdjust = s.getLength();
		}
		if(orientation == "horizontal") {
			xAdjust = s.getLength();
		}
		int xCoordinate = 0;
		int yCoordinate = 0;
		int[] coordinates = new int[2];
		boolean locationFound = false;
		while(!locationFound) {
			xCoordinate = (int) ((Math.random() * (this.getWidth() - xAdjust)) + MIN_X);
			yCoordinate = (int) ((Math.random() * (this.getHeight() - yAdjust)) + MIN_Y);
			if (orientation == "vertical") {
				for (int y = yCoordinate; y < s.getLength() + yCoordinate; y++) {
					locationFound = true;
					try {
						if(this.getLocation(y, xCoordinate) != null) {
							locationFound = false;
							break;
						}
					} catch (CoordinateLessThanZeroException e) {
						log.error("Coordinate less than zero", e);
						e.printStackTrace();
					} catch (CoordinateGreaterThanBoundaryException e) {
						log.error("Coordinate out of bounds", e);
						e.printStackTrace();
					}
				}
			} else if (orientation == "horizontal") {
				for (int x = xCoordinate; x < s.getLength() + xCoordinate; x++) {
					locationFound = true;
					try {
						if(this.getLocation(yCoordinate, x) != null) {
							locationFound = false;
							break;
						}
					} catch (CoordinateLessThanZeroException e) {
						log.error("Coordinate less than zero", e);
						e.printStackTrace();
					} catch (CoordinateGreaterThanBoundaryException e) {
						log.error("Coordinate out of bounds", e);
						e.printStackTrace();
					}
				}
			}
		}
		coordinates[0] = xCoordinate;
		coordinates[1] = yCoordinate;
		return coordinates;
	}
	
	// Prints a map showing the current status of the map, including all hits, misses, and ship locations
	public void printMap() {
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String border = " -";
		for(int i = 0; i < this.getWidth(); i++) {
			border += "----";
			System.out.print("   " + (i+1));
		}
		System.out.println();
		for(int y = 0; y < this.getHeight(); y++) {	
			System.out.println(border);
			System.out.print(alphabet.charAt(y));
			for(int x = 0; x < this.getWidth(); x++) {
				try {
					if(getLocation(y, x) == null) {
						System.out.print("|   ");
					} else {
						System.out.print("| " + getLocation(y, x) + " ");
					}
				} catch (CoordinateLessThanZeroException e) {
					log.error("Coordinate less than zero", e);
					e.printStackTrace();
				} catch (CoordinateGreaterThanBoundaryException e) {
					log.error("Coordinate out of bounds", e);
					e.printStackTrace();
				}
			}
			System.out.println("|");
		}
		System.out.println(border);
	}
	
	// Prints a map for gameplay, showing only hits and misses
	public void printPlayMap() {
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String border = " -";
		for(int i = 0; i < this.getWidth(); i++) {
			border += "----";
			System.out.print("   " + (i+1));
		}
		System.out.println();
		for(int y = 0; y < this.getHeight(); y++) {	
			System.out.println(border);
			System.out.print(alphabet.charAt(y));
			for(int x = 0; x < this.getWidth(); x++) {
				try {
					if(getLocation(y, x) == null || getLocation(y, x).matches("[2-6]")) {
						System.out.print("|   ");
					} else if (getLocation(y, x) == "X"){
						System.out.print("| X ");
					}
					else if (getLocation(y, x) == "O"){
						System.out.print("| O ");
					}
				} catch (CoordinateLessThanZeroException e) {
					log.error("Coordinate less than zero", e);
					e.printStackTrace();
				} catch (CoordinateGreaterThanBoundaryException e) {
					log.error("Coordinate out of bounds", e);
					e.printStackTrace();
				}
			}
			System.out.println("|");
		}
		System.out.println(border);
	}
	
	// Takes two ints and generates the corresponding grid location (ex. 'A1', 'D7', 'F5', etc.)
	public String getCoordinates(int y, int x) {
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String coordinates = "";
		coordinates += alphabet.charAt(y);
		coordinates += (x + 1);
		return coordinates;
	}
	
}
