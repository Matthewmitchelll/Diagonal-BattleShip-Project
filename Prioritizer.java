package project2;

import java.util.ArrayList;

abstract class Prioritizer<T extends SmartLocation> extends Targeter {
	
	/*
	 * Prioritizers are Targeters that employ more sophisticated schemes for targeting squares. All
	 * subclasses will, in general, begin by selecting squares according to some measure of utility
	 * that they define themselves, until an occupied location is found that is not connected to any
	 * other occupied locations. This location is tabbed as an 'originalX'. Once it is found, the
	 * surrounding locations are inspected in order of decreasing utility until a second occupied
	 * location is found, called 'xContinuation'. This forms a 2-length chain. Then, the locations
	 * at the end of the chains are targeted until the chain is capped by either unoccupied locations
	 * or locations that are off the board. Once this happens, Prioritizers will again begin targeting
	 * squares in order of decreasing utility, until the next occupied, unconnected location is found.
	 * From here it repeats the cycle until all ships are found.
	 * 
	 * This class contains fields for implementing the general chain expansion algorithm, as well
	 * as primitive functions for determining whether locations can possibly hold a ship and a
	 * reset procedure to carry out multiple games per simulation.
	 */

	protected T originalX;
	protected T xContinuation;

	protected Direction originalFocus; // direction from xContinuation to originalX
	protected Direction continuationFocus; // direction from originalX to xContinuation
	protected Direction lastDirectionWent;
	
	/***
	 * Constructs a Prioritizer
	 * 
	 * @param targetBoard the targetBoard of the overlying ComputerState object
	 */
	Prioritizer(char[][] targetBoard) {
		super(targetBoard);
	}
	
	/***
	 * Determines whether a newly discovered occupied location has any occupied locations bordering it
	 * 
	 * @param location the location to inspect
	 * @param locations the board of locations the Prioritizer is using to keep track of potential moves
	 * @return true if the location has no bordering occupied locations else false
	 */
	protected boolean noXNeighbors(T location, T[][] locations) {
		for (int drow = -1; drow < 2; drow++) {
			for (int dcol = -1; dcol < 2; dcol++) {
				if (drow == 0 && dcol == 0) continue; // skip the current location
				if (BoardUtilities.onTheBoard(location.row + drow, location.col + dcol, locations)
						&& targetBoard[location.row + drow][location.col + dcol] == 'x') return false;
			}
		}
		return true;
	}
	
	/***
	 * Fills the list of locations to reflect the untargeted locations bordering a newly discovered, occupied location
	 * 
	 * @param location the location to inspect
	 * @param locations the board of locations the Prioritizer is using to keep track of potential moves
	 * @param surroundingLocations the collection of surrounding locations to fill
	 */
	protected void updateSurroundingLocations(T location,
			T[][] locations, ArrayList<T> surroundingLocations) {
		for (int drow = -1; drow < 2; drow++) {
			for (int dcol = -1; dcol < 2; dcol++) {
				if (drow == 0 && dcol == 0) continue; // skip the location of the last move
				if (BoardUtilities.onTheBoard(location.row + drow, location.col + dcol, locations)
						&& targetBoard[location.row + drow][location.col + dcol] == '-') {
					surroundingLocations.add(locations[location.row + drow][location.col + dcol]);
				}
			}
		}
	}
	
	/***
	 * Sets up the directional parameters needed to employ the chain expansion algorithm
	 * 
	 * @param location the location marking the second occupied location discovered
	 */
	protected void setUpChain(T location) {
		originalFocus = originalX.directionFrom(location);
		continuationFocus = location.directionFrom(originalX);
		originalX = (T) originalX.locationTowards(originalFocus); // expands the chain in the direction along the old originalX
		xContinuation = (T) location.locationTowards(continuationFocus); // does the same with the old xContinuation
		if (originalX == null || targetBoard[originalX.row][originalX.col] != '-') { // completes the chain along the originalX end
			originalX = null;
			originalFocus = null;
		}
		if (xContinuation == null || targetBoard[xContinuation.row][xContinuation.col] != '-') { // completes the chain on the xContinuation end
			xContinuation = null;
			continuationFocus = null;
		}
	}
	
	/***
	 * This method employs a primitive validity check for a specified Location object.
	 * It considers whether a ship segment in the specified location would make currently
	 * known occupied locations impossible to exist by checking for situations in which
	 * lone occupied locations or multiple 2-length ships are created (no ships are of
	 * length 1, and only 1 ship may be of length 2, so if these situations would occur,
	 * this method returns true, specifying that this location cannot possibly hold an x)
	 * 
	 * @param location the Location object representing the move to verify
	 * @return true if the location would make the current configuration of known occupied
	 *         locations impossible, else false
	 */
	protected boolean notPossible(SmartLocation location) {
		if (location.surroundingFreedom > 0 || location.surroundingImmersion > 1) return false;
		if (location.surroundingImmersion == 0) return true;
		SmartLocation loneNeighbor = null;
		Direction dirToLone = null;
		Direction opposite = null;
		for (Direction d : Direction.directions) { // finds the lone occupied location bordering the location in question
			SmartLocation neighbor = location.locationTowards(d);
			if (neighbor != null && targetBoard[neighbor.row][neighbor.col] == 'x') {
				loneNeighbor = neighbor;
				dirToLone = d;
				opposite = dirToLone.opposite();
				break;
			}
		}
		SmartLocation otherSideLocation = loneNeighbor.locationTowards(dirToLone);
		for (Direction d : Direction.directions) {
			if (d == dirToLone || d == opposite) continue; // a location oriented along the line with the location in question and the lone occupied location may be part of a 3 or greater length ship
			SmartLocation neighbor = loneNeighbor.locationTowards(d);
			if (neighbor != null && targetBoard[neighbor.row][neighbor.col] == 'x') {
				if (isNeedy(neighbor, loneNeighbor, otherSideLocation, d, d.opposite())) { // determines if the other location needs the lone occupied location from above to be a part of a ship. If it does, this location cannot hold a ship because it would either lead to 2 2 length ships or a 1 length ship
					return true;
				}
			}
		}
		return false;
	}
	
	/***
	 * This method checks whether a Location (the dependent) is incompatible with a known,
	 * occupied ship location (the original), with respect to another known ship location,
	 * considering the situations described in the specs for the notPossible function
	 * 
	 * @param dependent
	 * @param provider
	 * @param original
	 * @return
	 */
	protected boolean isNeedy(SmartLocation dependent, SmartLocation provider,
			SmartLocation otherSideLocation, Direction awayFromProvider, Direction toProvider) {
		int longestChain = TargetUtilities.longestPotentialChain(dependent, provider, awayFromProvider, toProvider); // determines the longest chain that a dependent location can be a part of such that the provider location is not included
		if (longestChain > 2) return false;
		if (longestChain == 1) return true;
		return (otherSideLocation == null || targetBoard[otherSideLocation.row][otherSideLocation.col] == 'o');
	}
	
	/***
	 * Updates the movePool of a Prioritizer
	 * 
	 * @param movePool the movePool to update
	 * @param row the row of the chosen move to target
	 * @param col the column of the chosen move to target
	 */
	protected void updateMovePool(ArrayList<T> movePool, int row, int col) {
		for (int i = 0; i < movePool.size(); i++) {
			if (movePool.get(i).row == row &&
					movePool.get(i).col == col) {
				movePool.remove(i);
				break;
			}
		}
	}
	
	/***
	 * Updates the current chain of expansion for a Prioritizer
	 * 
	 * @param updateContinuation true if the last location targeted was an xContinuation, else false
	 */
	protected void updateChain(boolean updateContinuation) {
		if (updateContinuation) {
			lastDirectionWent = continuationFocus;
			xContinuation = (T) xContinuation.locationTowards(continuationFocus);
			while (xContinuation != null && targetBoard[xContinuation.row][xContinuation.col] == 'x') { // if the chain runs into an occupied location, it passes over it and continues
				xContinuation = (T) xContinuation.locationTowards(continuationFocus);
			}
			if (xContinuation != null && targetBoard[xContinuation.row][xContinuation.col] == 'o') { // ends a chain on the xContinuation side
				xContinuation = null;
			}
		} else {
			lastDirectionWent = originalFocus;
			originalX = (T) originalX.locationTowards(originalFocus);
			while (originalX != null && targetBoard[originalX.row][originalX.col] == 'x') {
				originalX = (T) originalX.locationTowards(originalFocus);
			}
			if (originalX == null || targetBoard[originalX.row][originalX.col] == 'o') {
				originalX = null;
				originalFocus = null;
			}
		}
	}
	
	/***
	 * Clears the collections used by a Prioritizer and sets chain algorithm parameters to null, as well as resets the locations
	 * Prioritizers use
	 * 
	 * @param movePool the movePool used by a Prioritizer
	 * @param surroundingLocations list of locations surrounding a designated originalX
	 * @param locations 2D array of location objects use to collect information about different locations on th board
	 */
	public void reset(ArrayList<T> movePool, ArrayList<T> surroundingLocations,
			T[][] locations) {
		movePool.clear();
		surroundingLocations.clear();
		originalX = null;
		xContinuation = null;
		originalFocus = null;
		continuationFocus = null;
		for (int i = 0; i < locations.length; i++) {
			for (int j = 0; j < locations.length; j++) {
				locations[i][j].reset();
				movePool.add(locations[i][j]);
			}
		}
	}
}