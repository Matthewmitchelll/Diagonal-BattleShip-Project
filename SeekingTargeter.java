package project2;

import java.util.ArrayList;

class SeekingTargeter extends RandomTargeter {
	
	/*
	 * A SeekingTargeter will randomly target untargeted locations until an occupied
	 * location is found, then will randomly target locations bordering any known
	 * occupied locations until no such locations exist. Finally, it will continue
	 * randomly targeting untargeted locations, repeating the cycle until the game
	 * ends.
	 */
	
	// used to store locations bordering known occupied locations
	private ArrayList<Location> surroundingLocations = new ArrayList<Location>();
	
	// used to efficiently determine whether a location resides in surroundingLocations
	// (to avoid adding it again when discovering another occupied location bordering it)
	private boolean[][] locationAdded;
	
	/***
	 * Constructs a SeekingTargeter
	 * 
	 * @param targetBoard the targetBoard of the overlying ComputerState object
	 */
	SeekingTargeter(char[][] targetBoard) {
		super(targetBoard);
		nextMove = movePool.get(0);
		locationAdded = new boolean[targetBoard.length][targetBoard.length];
	}
	
	/***
	 * Selects the overlying ComputerState object's next move from the current pool of untargeted
	 * locations. Considers known occupied locations
	 * 
	 * @returns a Location object representing the overlying computer's next target
	 */
	public Location getNextMove() {
		if (targetBoard[nextMove.row][nextMove.col] == 'x') { // last move hit an occupied location
			// updates list of locations that border known occupied locations
			addNewSurroundingLocations();
		}
		if (surroundingLocations.size() > 0) {
			// selects a random location from the list of locations surrounding occupied spots, removing it
			nextMove = surroundingLocations.remove(rand.nextInt(surroundingLocations.size()));
			updateMovePool(nextMove.row, nextMove.col);
		} else {
			super.getNextMove(); // chooses a move randomly from our movePool
		}
		return nextMove;
	}
	
	/***
	 * Updates list of locations that border known occupied locations with the locations bordering
	 * our last target, which is assumed to be the location of an 'x'
	 */
	private final void addNewSurroundingLocations() {
		int row = nextMove.row, col = nextMove.col;
		for (int drow = -1; drow < 2; drow++) {
			for (int dcol = -1; dcol < 2; dcol++) {
				if (drow == 0 && dcol == 0) continue; // skip the location of the last move
				if (BoardUtilities.onTheBoard(row + drow, col + dcol, targetBoard)
						&& !locationAdded[row + drow][col + dcol]
						&& targetBoard[row + drow][col + dcol] == '-') {
					Location coord = new Location(row + drow, col + dcol);
					surroundingLocations.add(coord);
					locationAdded[row + drow][col + dcol] = true;
				}
			}
		}
	}
	
	/***
	 * Updates the movePool after selecting a move by removing the Location that shares that move's
	 * row and column
	 * 
	 * @param row the row in question
	 * @param col the column in question
	 */
	private void updateMovePool(int row, int col) {
		for (int i = 0; i < movePool.size(); i++) {
			if (movePool.get(i).row == row &&
					movePool.get(i).col == col) {
				movePool.remove(i);
				break;
			}
		}
	}
	
	/***
	 * Clears the collections used by the SeekingTargeter, resets all locations (see the Location class),
	 * and sets all locationAdded values to false
	 */
	public void reset() {
		super.reset();
		surroundingLocations.clear();
		resetLocationsAdded();
	}
	
	/***
	 * Sets all locationAdded values to false
	 */
	private void resetLocationsAdded() {
		for (int i = 0; i < locationAdded.length; i++) {
			for (int j = 0; j < locationAdded.length; j++) {
				locationAdded[i][j] = false;
			}
		}
	}
}