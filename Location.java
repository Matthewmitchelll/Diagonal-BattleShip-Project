package project2;

import static project2.Direction.*;

class Location {
	
	/*
	 * This class represents a location on a board. A Location object consists of a row and column
	 * field, as well as functions to determine where it is in relation to other Location objects.
	 */
	
	public int row;
	public int col;
	
	/***
	 * Constructs a Location
	 * 
	 * @param row the row of the location
	 * @param col the column of the location
	 */
	Location(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	/***
	 * Determines the direction that this location is from another location
	 * 
	 * @param otherLoc the Location representing the starting point
	 * @return the direction from otherLoc to this location
	 */
	public Direction directionFrom(Location otherLoc) {
		int deltaRow = row - otherLoc.row, deltaCol = col - otherLoc.col;
		return orienter(deltaRow, deltaCol);
	}
	
	/***
	 * Determines the direction that this location is from a (row, col) coordinate
	 * 
	 * @param row the row of the starting point
	 * @param col the column of the starting point
	 * @return the direction from the starting point's coordinates to this location
	 */
	public Direction directionFrom(int row, int col) {
		int deltaRow = this.row - row, deltaCol = this.col - col;
		return orienter(deltaRow, deltaCol);
	}
	
	/***
	 * Determines the direction from this location to some other location
	 * 
	 * @param otherLoc the Location representing the destination
	 * @return the direction to the destination
	 */
	public Direction directionTo(Location otherLoc) {
		int deltaRow = otherLoc.row - row, deltaCol = otherLoc.col - col;
		return orienter(deltaRow, deltaCol);
	}
	
	/***
	 * Determines the direction from this location to a (row, col) pair
	 * 
	 * @param row the row of the destination
	 * @param col the column of the destination
	 * @return the direction to the destination
	 */
	public Direction directionTo(int row, int col) {
		int deltaRow = row - this.row, deltaCol = col - this.col;
		return orienter(deltaRow, deltaCol);
	}
	
	/***
	 * This function determine what direction was traveled given a
	 * change in row and a change in column. A positive change in row
	 * and column indicates traveling in the south and east directions,
	 * respectively, in correspondence with the order in which lists are
	 * scanned forward
	 * 
	 * @param deltaRow the change in row
	 * @param deltaCol the change in column
	 * @return the direction that was traveled given the change in row and the change in column
	 */
	private Direction orienter(int deltaRow, int deltaCol) {
		// arrange it so positive changes mean going east and south-nested for. Loop scans go in these directions
		if (deltaRow > 0 && deltaCol < 0) {
			return SOUTHWEST;
		} else if (deltaRow > 0 && deltaCol == 0) {
			return SOUTH;
		} else if (deltaRow > 0 && deltaCol > 0) {
			return SOUTHEAST;
		} else if (deltaRow == 0 && deltaCol < 0) {
			return WEST;
		} else if (deltaRow == 0 && deltaCol > 0) {
			return EAST;
		} else if (deltaRow < 0 && deltaCol < 0) {
			return NORTHWEST;
		} else if (deltaRow < 0 && deltaCol == 0) {
			return NORTH;
		} else if (deltaRow < 0 && deltaCol > 0) {
			return NORTHEAST;
		} else {
			return null;
		}
	}
}