package project2;

import java.util.Scanner;

abstract class Simulator {
	
	/*
	 * This class lays out the basic properties and functionality that all simulators
	 * of the game should have, including the number of games to simulate, a boolean
	 * value to determine whether to display progress to the console while running,
	 * and functions to allow the user to set these properties. Both subclasses of
	 * this class defined in this package will also contain two ComputerState objects
	 * with which to run a simulation, though the declaration and initialization of
	 * these objects are left to the subclasses, as the simulators require different
	 * computer behavior.
	 */

	protected long numGames;

	protected boolean verbose;
	
	/***
	 * Constructs a Simulator that will run with pre-specified parameters
	 * 
	 * @param numGames the number of games per simulation desired
	 * @param verbose boolean value specifying whether to show in-game progress, true to do so, else false
	 */
	Simulator(long numGames, boolean verbose) {
		this.numGames = numGames;
		this.verbose = verbose;
	}
	
	/***
	 * Constructs a Simulator object that will configure its general settings from user input
	 */
	Simulator() {
		Scanner s = new Scanner(System.in);
		setNumGames(s);
		setVerbosity(s);
	}
	
	/***
	 * Runs the Simulator
	 */
	protected abstract void runSim();
	
	/***
	 * Gets and sets the number of games the user desires to simulate
	 * 
	 * @param s Scanner object with which to read user input
	 */
	private void setNumGames(Scanner s) {
		System.out.println("How many simulations would you like to run? For reasonably quick results,\n"
				+ "enter a number less than 1,000,000 (without commas)");
		String userInput = s.nextLine();
		while (!processedNumber(userInput)) {
			System.out.println("Sorry, that input is invalid. Please input an integer, no decimals.");
			userInput = s.nextLine();
		}
	}
	
	/***
	 * Processes a user's input for the number of games to simulate. Negative integers will
	 * be treated as 0 in any implementing subclass
	 * 
	 * @param userInput user's input for number of games to simulate
	 * @return true if the user's input is a valid integer and has been processed, false else
	 */
	private boolean processedNumber(String userInput) {
		try {
			numGames = Long.parseLong(userInput);
			return numGames >= 0;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	/***
	 * Gets and sets the boolean value controlling a simulator's display
	 * 
	 * @param s Scanner object with which to read user input
	 */
	private void setVerbosity(Scanner s) {
		System.out.println("Would you like boards and scores to be displayed as games are simulated?\n"
				+ "Enter 1 for yes, or enter any other character for no.");
		String userInput = s.nextLine();
		verbose = userInput.equals("1");
	}
}