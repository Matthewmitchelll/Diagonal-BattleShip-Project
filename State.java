package project2;

import java.util.Random;

abstract class State {
	
	/*
	 * Represents the state of the game for either a player or computer as:
	 * 
	 * 1) a 2D char array representing the locations of the subject's ships,
	 * 2) a 2D char array the subject will use to acquire knowledge of the
	 *    opponent's board and make moves, and
	 * 3) the number of unhit occupied locations left keeping that subject alive.
	 * 
	 * The symbols in the arrays are '-', 'o', and 'x'.
	 * 
	 * For the owned board:
	 * '-' = unoccupied location
	 * 'o' = occupied location
	 * ('x' not used)
	 * 
	 * For the target board:
	 * '-' = untargeted location
	 * 'o' = missed location
	 * 'x' = hit location
	 */
	
	protected char[][] ownBoard;
	protected char[][] targetBoard;
	
	Random rand; // used to place ships randomly

	protected int locationsLeft = Ship.TOTAL_SHIP_OCCUPANCY; // number of occupied spots of all ships at start
	protected double numGuesses = 0; // double to calculate precise performance metrics
	
	/***
	 * Constructs the state of the subject at the start of the game
	 * 
	 * @param boardSize the size of a board for the game
	 * @param needRandomness boolean value to determine whether ships need to be placed randomly, true if so, else false
	 */
	State(int boardSize, boolean needRandomness) {
		ownBoard = new char[boardSize][boardSize];
		targetBoard = new char[boardSize][boardSize];
		if (needRandomness) rand = new Random();
		clearBoards();
	}
	
	/***
	 * Populates the own board and target board with unoccupied and untargeted locations, respectively
	 */
	protected final void clearBoards() {
		for (int row = 0; row < ownBoard.length; row++) {
			for (int col = 0; col < ownBoard.length; col++) {
				ownBoard[row][col] = '-';
				targetBoard[row][col] = '-';
			}
		}
	}
	
	/***
	 * Adds ships to the board randomly
	 */
	public void addShips() {
		int row, col; // will specify potential locations for the fronts of ships
		Direction dir; // will specify direction that the fronts of the ships will be facing 
		for (int i = 0; i < Ship.shipSizes.length; i++) {
			do {
				row = rand.nextInt(ownBoard.length);
				col = rand.nextInt(ownBoard.length);
				dir = Direction.directions[rand.nextInt(Direction.directions.length)];
				// loops if the randomly selected row and column coordinates are invalid with
				// the current owned board when the ship's front is pointing in the direction
				// specified. direction is 0 for NORTH, and increments clockwise so that 7 is
				// NORTHWEST
			} while (!BoardUtilities.validSetLocation(row, col, dir, Ship.shipSizes[i], ownBoard));
			BoardUtilities.setShip(row, col, dir, Ship.shipSizes[i], ownBoard); // places ship when valid
		}
	}
	
	/***
	 * Lets the subject make a move, given an instance of the opponent's state
	 * 
	 * @param opponentState State object representing the opponent's state
	 */
	protected abstract void makeMove(State opponentState);
	
	/***
	 * Makes a guess and updates the states of both subjects of a game according to the results
	 * 
	 * @param row the row of the guess
	 * @param col the column of the guess
	 * @param opponent State object representing the opponent, whose board of ships is being targeted
	 */
	protected final void target(int row, int col, State opponent) {
		if (opponent.ownBoard[row][col] == 'o') { // a ship has been hit
			opponent.locationsLeft--; // the opponent is one step closer to losing
			targetBoard[row][col] = 'x'; // a successful hit is marked on the invoker's target board
		} else { // missed
			targetBoard[row][col] = 'o'; // a miss is marked on the invoker's target board
		}
		numGuesses++;
	}
}