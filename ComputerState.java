package project2;

import java.util.Scanner;

class ComputerState extends State {
	
	/*
	 * Represents the state of the game for the computer (see the State abstract class for details)
	 * 
	 * Extends State by adding functions to make moves based on various targeting schemes
	 * (see the Targeter class for details), as well as to handle various resetting capabilities.
	 * 
	 * The lone added instance variable is this computer's Targeter, which will act in one of 4
	 * possible ways, depending on the desired level for the computer, as described in the Targeter
	 * class.
	 */
	
	private static final int MAX_LEVEL = 4;
	
	private int level;
	
	private Targeter targeter;
	
	/***
	 * Constructs the state of the computer at the start of the game, pre-setting the level. Assumes
	 * the level passed in is between 1 and 4, inclusive, and that the computer is only used for
	 * testing purposes in the TestSimulator class (it doesn't fill it's board with ships, it will
	 * do all the guessing)
	 * 
	 * @param boardSize the size of a board for the game
	 * @param level the level to set the computer
	 * @param needToAddShips boolean value to determine whether a computer needs to randomly add ships to its board (Dummy's in simulations do, guesser's do not)
	 */
	ComputerState(int boardSize, int level, boolean needToAddShips) {
		super(boardSize, needToAddShips);
		this.level = level;
		setTargeter();
		if (needToAddShips) addShips();
	}
	
	/***
	 * Constructs the state of the computer at the start of the game, acquiring the level from the user
	 * 
	 * @param boardSize the size of a board for the game
	 */
	ComputerState(int boardSize) {
		super(boardSize, true); // boards and trackers setup
		configureSettings(); // set level and Targeter
		addShips(); // adds ships to ownBoard randomly (not to targetBoard)
	}
	
	/***
	 * Constructs the state of the computer at the start of the game. The boolean value determines
	 * whether this computer is meant to be used as a "dummy". If so, no targeter is initialized,
	 * as a computer intended as a dummy is not meant to make any moves. Dummies are used only in
	 * the SingleSim class as an opponent to test various strategies against. It doesn't make moves
	 * in order to allow its opponent to determine how long it takes to find all its ships
	 * 
	 * @param boardSize the size of a board for the game
	 * @param isDummy boolean value that is true if this computer is meant to be a dummy, false else
	 */
	ComputerState(int boardSize, boolean isDummy) {
		super(boardSize, true);
		if (!isDummy) configureSettings();
		addShips(); // adds ships to the board randomly
	}
	
	/***
	 * Sets the desired level for this computer and the corresponding Targeter
	 */
	private void configureSettings() {
		setLevel();
		setTargeter();
	}
	
	/***
	 * Prompts the user for a level to configure the computer
	 */
	private void setLevel() {
		System.out.println("Choose a computer difficulty. Enter 1, 2, 3, or 4.");
		Scanner s = new Scanner(System.in);
		String userInput = s.nextLine();
		while (!processedLevel(userInput)) {
			System.out.println("Sorry, that's an invalid answer. Please enter either '1', '2', '3', or\n"
					+ "'4', without quotes.");
			userInput = s.nextLine();
		}
	}
	
	/***
	 * Attempts to process a user's input for this computer's level
	 * 
	 * @param userInput user's input for this computer's level
	 * @return true if the user's input has been parsed and the level has been set correctly, else false
	 */
	private boolean processedLevel(String userInput) {
		try {
			level = Integer.parseInt(userInput);
			return level > 0 && level <= MAX_LEVEL; // make sure user's input is correctly bounded
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	/***
	 * Initializes the computer's Targeter based on its level
	 */
	private void setTargeter() {
		if (level == 1) {
			targeter = new RandomTargeter(targetBoard);
		} else if (level == 2) {
			targeter = new SeekingTargeter(targetBoard);
		} else if (level == 3) {
			targeter = new WeightedPrioritizer(targetBoard);
		} else {
			targeter = new Commander(targetBoard);
		}
	}
	
	/***
	 * Sets weights for a WeightedPrioritizer Targeter (See the WeightedPrioritizer class)
	 * 
	 * @param maxICWeight weight for maxImmersionChainLength
	 * @param maxPCWeight weight for maxPotentialChainLength
	 * @param iWeight weight for immersion
	 * @param pWeight weight for potential
	 * @param vWeight weight for variance
	 */
	public void setTargeterWeights(int maxICWeight, int maxPCWeight, int iWeight, int pWeight, int vWeight) {
		if (level != 3) System.out.println("The level configuration is not compatible with these weights.");
		else {
			targeter.setNewWeights(maxICWeight, maxPCWeight, iWeight, pWeight, vWeight);
		}
	}
	
	/***
	 * Sets weights for a Commander Targeter (See the Commander class)
	 * 
	 * @param tPWeight the weight to be used to determine the level of consideration for the amount of ways a location can accommodate various ships
	 * @param maxICWeight weight for maxImmersionChainLength
	 */
	public void setTargeterWeights(int tPWeight, int maxICWeight) {
		if (level != 4) System.out.println("The level configuration is not compatible with these weights.");
		else {
			targeter.setNewWeights(tPWeight, maxICWeight);
		}
	}
	
	/***
	 * Resets the ComputerState's underlying Targeter used to determine its moves
	 */
	public void resetTargeter() {
		targeter.reset();
	}
	
	/***
	 * Resets only targetBoard. Used in the TestSimulator class by test computers for assessing
	 * strategies (See TestSimulator for details)
	 */
	public void resetTargetBoard() {
		for (int row = 0; row < targetBoard.length; row++) {
			for (int col = 0; col < targetBoard.length; col++) {
				targetBoard[row][col] = '-';
			}
		}
	}
	
	/***
	 * Resets ships on ownBoard again in a random manner. Used by dummies that don't need to
	 * consider their targetBoard
	 */
	public void reconfigureShips() {
		resetOwnBoard(); // clears ownBoard first
		addShips();
	}
	
	/***
	 * Resets only ownBoard. Used in the TestSimulator class with dummies for assessing
	 * strategies
	 */
	private void resetOwnBoard() {
		for (int row = 0; row < ownBoard.length; row++) {
			for (int col = 0; col < ownBoard.length; col++) {
				ownBoard[row][col] = '-';
			}
		}
	}
	
	/***
	 * Resets both ownBoard and targetBoard and adds ships to ownBoard anew. Used by computers
	 * that are playing against each other normally (in a BattleSimulator)
	 */
	public void clearAllAndReconfigure() {
		clearBoards();
		targeter.reset();
		addShips();
	}
	
	/***
	 * Makes a move for the computer
	 * 
	 * @param ps state of the player (this computer's opponent)
	 */
	public void makeMove(State ps) {
		Location targetLocation = targeter.getNextMove();
		target(targetLocation.row, targetLocation.col, ps);
	}
}