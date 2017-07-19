package project2;

class BattleSimulator extends Simulator {
	
	/*
	 * Objects of this class run simulations of games between two computers where standard
	 * rules apply, i.e. the game is exactly the same procedurally as a traditional game
	 * between 2 humans or a game between a human and computer.
	 */

	private ComputerState cs1;
	private ComputerState cs2;

	private int winCounter = 0;

	private boolean userTurn; // for switching turns

	/***
	 * Constructs a BattleSimulator object will configure its settings through passed in arguments
	 * 
	 * @param numGames the number of games per simulation desired
	 * @param verbose boolean value specifying whether to show in-game progress, true to do so, else false
	 * @param cs1Level the level of the first computer
	 * @param cs2Level the level of the second computer
	 */
	BattleSimulator(int numGames, boolean verbose, int cs1Level, int cs2Level) {
		super(numGames, verbose); // sets general Simulator settings
		cs1 = new ComputerState(BoardUtilities.DEFAULT_BOARD_SIZE, cs1Level, true); // true indicates ships need to be added randomly (false is used for dummies)
		cs2 = new ComputerState(BoardUtilities.DEFAULT_BOARD_SIZE, cs2Level, true);
	}

	/***
	 * Constructs a BattleSimulator object that will configure its general settings from user input
	 */
	BattleSimulator() {
		super();
		cs1 = new ComputerState(BoardUtilities.DEFAULT_BOARD_SIZE);
		cs2 = new ComputerState(BoardUtilities.DEFAULT_BOARD_SIZE);
	}
	
	/***
	 * Runs a BattleSimulator
	 */
	public void runSim() {
		long start = System.nanoTime();
		for (int i = 0; i < numGames; i++) {
			userTurn = new java.util.Random().nextInt(2) == 1; // randomly decide who goes first
			while (cs1.locationsLeft != 0 && cs2.locationsLeft != 0) { // no one won yet
				if (userTurn) {
					if (verbose) displayGameProgress();
					cs1.makeMove(cs2);
				} else {
					cs2.makeMove(cs1);
				}
				userTurn = !userTurn;
			}
			concludeGame(); // someone won, so end the game
			reset();
		}
		long end = System.nanoTime();
		System.out.println((end - start)/(Math.pow(10, 9)));
		displaySimResults();
	}
	
	/***
	 * Displays progress of games, as they are running, by showing all 4 boards, scores, and guesses
	 */
	private void displayGameProgress() {
		BoardUtilities.showBoards(cs1.ownBoard, cs2.targetBoard, cs1.targetBoard, cs2.ownBoard, true);
		System.out.println(
				"\nScore: Computer 1: " + (Ship.TOTAL_SHIP_OCCUPANCY - cs2.locationsLeft)
				+ " Computer 2: " + (Ship.TOTAL_SHIP_OCCUPANCY - cs1.locationsLeft));
		System.out.println("\nTotal Guesses: Computer 1: " + cs1.numGuesses
				+ " Computer 2: " + cs2.numGuesses + "\n");
	}
	
	/***
	 * Records the result of a game by deciding whether to increment a counter, and displays the winner
	 */
	private void concludeGame() {
		if (verbose) displayGameProgress();
		if (cs1.locationsLeft == 0) {
			winCounter++; // counts wins by Computer 2, subtract from numGames to get wins from Computer 1
			if (verbose) System.out.println("Computer 2 wins!");
		} else if (verbose) { // Computer 1 won, and so we display this if verbose is set to true
			System.out.println("Computer 1 wins!");
		}
	}
	
	/***
	 * Resets relevant ComputerState fields to play an additional game. numGuess is not reset in
	 * order to show the user the total amounts of guesses by each computer as the games progress
	 */
	private void reset() {
		cs1.clearAllAndReconfigure(); // clears both boards and adds ships to the ownBoard of this player
		cs1.locationsLeft = Ship.TOTAL_SHIP_OCCUPANCY; // must reset locationsLeft to avoid auto-wins
		cs2.clearAllAndReconfigure();
		cs2.locationsLeft = Ship.TOTAL_SHIP_OCCUPANCY;
	}
	
	/***
	 * Displays the final result of a simulation
	 */
	private void displaySimResults() {
		System.out.println("Total Wins: Computer 1: " + (numGames - winCounter)
				+ " Computer 2: " + winCounter + "\n");
	}
}