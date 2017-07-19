package project2;

import java.util.ArrayList;
import java.util.Random;

class RandomTargeter extends Targeter {

	/*
	 * A RandomTargeter will return a randomly selected move when its getNextMove() method
	 * is called. The targeter will only select randomly from a pool of untargeted locations
	 * to avoid repeatedly checking that a selected location was not previously targeted.
	 */
	
	protected Location nextMove; // used to store the newly selected move
	 							 // avoids reinitializing with every call to getNextMove()

	protected ArrayList<Location> movePool = new ArrayList<Location>();
	
	protected Location[][] locations; // 2D-array that stores Location objects, from which
	                                  // we will determine the next move to be made (see the Location class)

	protected Random rand = new Random(); // used to choose locations randomly

	/***
	 * Constructs a RandomTargeter
	 * 
	 * @param targetBoard the targetBoard of the overlying ComputerState object
	 */
	RandomTargeter(char[][] targetBoard) {
		super(targetBoard);
		locations = new Location[targetBoard.length][targetBoard.length];
		setLocations();
		setMovePool();
	}
	
	/***
	 * Fills the locations field with Location objects
	 */
	private final void setLocations() {
		for (int i = 0; i < locations.length; i++) {
			for (int j = 0; j < locations.length; j++) {
				locations[i][j] = new Location(i, j);
			}
		}
	}
	
	/***
	 * Fills the movePool with all possible moves at the beginning of a game
	 */
	private final void setMovePool() {
		for (int i = 0; i < targetBoard.length; i++) {
			for (int j = 0; j < targetBoard.length; j++) {
				movePool.add(locations[i][j]);
			}
		}
	}
	
	/***
	 * Selects the overlying ComputerState object's next move from the current pool of untargeted
	 * locations randomly
	 * 
	 * @returns a Location object representing the overlying computer's next target
	 */
	public Location getNextMove() {
		nextMove = movePool.remove(rand.nextInt(movePool.size())); // selects move randomly and removes it
		return nextMove;
	}
	
	/***
	 * Clears the movePool used by the RandomTargeter and resets all locations (see the Location class)
	 */
	protected void reset() {
		movePool.clear();
		setMovePool();
	}
}