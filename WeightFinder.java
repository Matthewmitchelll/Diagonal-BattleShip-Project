package project2;

class WeightFinder {
	
	/*
	 * A WeightFinder instance runs a series of games between a guesser and dummy
	 * using all possible combinations of weight configurations that applies to
	 * the guesser, with the goal of finding the best configuration of weights.
	 * At the end of the games, the best weight configuration is displayed. All
	 * configurations are tested using the same set of boards.
	 */
	
	private int maxWeight = 16; // all weights vary from 1 to this maximum, inclusive
	
	private int bestTPWeight; // the following are weights for a Commander, which was the last Targeter object tested
	private int bestMaxICWeight;
	
	private int numGames;
	
	private ComputerState guesser; // the computer employing the desired test strategy
	private ComputerState dummy; // the computer that won't make any moves
	
	private char[][][] testBoards;
	
	private double bestPerformance = 100.0;
	
	private double highestGuesses; // used to obtain a measure of the most difficult ship configuration for a weight configuration
	private double guessTracker; // used to obtain the number of guesses made for each game
	
	/***
	 * Constructs a WeightFinder
	 * 
	 * @param numGames the number of games used to test each weight configuration
	 */
	WeightFinder(int numGames) {
		this.numGames = numGames;
		testBoards = new char[numGames][][];
		guesser = new ComputerState(BoardUtilities.DEFAULT_BOARD_SIZE, 4, false);
		setUpTestBoards();
	}
	
	/***
	 * Stores the different ship configurations to be used for each game so that different weight
	 * configurations are ensured to be assessed on a consistent set of boards
	 */
	private void setUpTestBoards() {
		System.out.print("Setting up boards...");
		for (int i = 0; i < numGames; i++) {
			dummy = new ComputerState(BoardUtilities.DEFAULT_BOARD_SIZE, true); // this line obtains different boards. New instances are needed to avoid using the same reference for each slot in testBoards
			testBoards[i] = dummy.ownBoard;
		}
		System.out.println("complete.");
	}
	
	/***
	 * Scans all combinations of weights from 1 to maxWeight, inclusive, to ascertain the
	 * best weights for a particular targeter
	 */
	public void run() {
		long start = System.nanoTime();
		for (int i = 1; i <= maxWeight; i++) {
			for (int j = 1; j <= maxWeight; j++) {
				for (int s = 0; s < numGames; s++) {
					guesser.setTargeterWeights(i, j);
					dummy.ownBoard = testBoards[s];
					while (dummy.locationsLeft != 0) { // ship still afloat
						guesser.makeMove(dummy);
					}
					if (guesser.numGuesses - guessTracker > highestGuesses) {
						highestGuesses = guesser.numGuesses - guessTracker;
					}
					guessTracker = guesser.numGuesses;
					guesser.resetTargetBoard();
					guesser.resetTargeter();
					dummy.locationsLeft = Ship.TOTAL_SHIP_OCCUPANCY;
				}
				if (guesser.numGuesses/numGames <= bestPerformance) {
					bestPerformance = guesser.numGuesses/numGames;
					adjustBestSettings(i, j);
				}
				guesser.numGuesses = 0;
				guessTracker = 0;
				highestGuesses = 0;
			}
		}
		long end = System.nanoTime();
		System.out.println((end - start)/(Math.pow(10, 9)));
		displayBestResults();
	}
	
	/***
	 * Records the best weight configuration seen so far during a simulation
	 * 
	 * @param i the best tpWeight seen for the Commander
	 * @param j the best maxICWeight seen for the Commander
	 */
	private void adjustBestSettings(int i, int j) {
		System.out.print("(" + highestGuesses + " " + bestPerformance + ") ");
		System.out.println(i + ", " + j + ": " + bestPerformance);
		bestTPWeight = i;
		bestMaxICWeight = j;
	}
	
	/***
	 * Displays the results of a weight-finding simulation
	 */
	private void displayBestResults() {
		System.out.println("Best performance: " + bestPerformance);
		System.out.println("Weights for this performance: " + bestTPWeight + " " + bestMaxICWeight);
	}
}