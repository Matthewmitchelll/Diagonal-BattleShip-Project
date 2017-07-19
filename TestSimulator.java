package project2;

class TestSimulator extends Simulator {
	
	/*
	 * Objects of this class will run simulations where only one computer, from here
	 * on called the guesser, will make guesses. The guesser will perform guesses on
	 * randomly generated board configurations to see, on average, how quickly (in
	 * terms of the number of guesses made) it can find all ships. The randomly
	 * generated boards will be part of the state of a 'dummy' ComputerState object
	 * that will never make any guesses. Therefore, only the guesser can end the
	 * simulation. The class is meant to be used to conduct performance tests for
	 * various strategies. The results from these tests will be used to assign levels
	 * for the strategies tested in order to configure the Targeter class, which will
	 * represent a computer's 'ai' for 2-player games.
	 */
	
	public ComputerState guesser; // the computer employing the desired test strategy
	private ComputerState dummy; // the computer that won't make any moves
	
	/***
	 * Constructs a TestSimulator object that will run with pre-specified parameters
	 * 
	 * @param numGames the number of games to simulate
	 * @param level the level of the guessing computer used to test a strategy
	 * @param verbose boolean value to determine whether game progress is displayed, true if so else false
	 */
	TestSimulator(long numGames, int level, boolean verbose) {
		super(numGames, verbose);
		guesser = new ComputerState(BoardUtilities.DEFAULT_BOARD_SIZE, level, false);
		dummy = new ComputerState(BoardUtilities.DEFAULT_BOARD_SIZE, true); // true, specifying a dummy
	}
	
	/***
	 * Constructs a TestSimulator object that will configure its general settings from user input
	 */
	TestSimulator() {
		super();
		guesser = new ComputerState(BoardUtilities.DEFAULT_BOARD_SIZE);
		dummy = new ComputerState(BoardUtilities.DEFAULT_BOARD_SIZE, true); // true, specifying a dummy
	}
	
	/***
	 * Runs a TestSimulator
	 */
	public void runSim() {
		long start = System.nanoTime();
		for (long i = 0; i < numGames; i++) {
			while (dummy.locationsLeft != 0) { // ship(s) still afloat
				guesser.makeMove(dummy);
				if (verbose) {
					BoardUtilities.showBoards(guesser.ownBoard, dummy.targetBoard,
							guesser.targetBoard, dummy.ownBoard, true); // shows all boards
					System.out.println(
							"\nScore: " + (Ship.TOTAL_SHIP_OCCUPANCY - dummy.locationsLeft));
					System.out.println("Guesses: " + guesser.numGuesses + "\n");
				}
			}
			reset();
		}
		long end = System.nanoTime();
		System.out.println((end - start)/(Math.pow(10, 9)));
		displaySimResults();
	}
	
	/***
	 * Resets relevant ComputerState fields to play another game. Does not reset the guesser's
	 * numGuess field so that it can be used as a measure of the total number of guesses it
	 * has made for all games
	 */
	private void reset() {
		guesser.resetTargetBoard(); // resets only targetBoard because our ownBoard does not matter
		guesser.resetTargeter();
		dummy.reconfigureShips(); // resets only ownBoard because targetBoard does not matter
		dummy.locationsLeft = Ship.TOTAL_SHIP_OCCUPANCY; // to avoid auto-wins for the next game
	}
	
	/***
	 * Displays the final result of a simulation
	 */
	private void displaySimResults() {
		System.out.println("Total Guesses: " + guesser.numGuesses);
		System.out.println("Avg. number of guesses needed for the guesser to sink all ships: "
				+ (guesser.numGuesses)/numGames);
	}
}