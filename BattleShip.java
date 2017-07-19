package project2;

import java.util.Scanner;

class BattleShip {
	
	/*
	 * A game of Battleship will be represented as a pair of State objects, (see the
	 * State class). BattleShip objects will run games between a human and a computer.
	 */
	
	private ComputerState cs;
	private PlayerState ps;
	
	private boolean userTurn; // used to switch turns
	
	/***
	 * Constructs a BattleShip object, setting up all preliminary info needed for a game
	 * 
	 * @param addRandomShips boolean value to indicate if the player is to have ships placed randomly, true if so, else false
	 */
	BattleShip(boolean addRandomShips) {
		System.out.println("Welcome to BattleShip! Let's see if you can beat a computer!\n");
		cs = new ComputerState(BoardUtilities.DEFAULT_BOARD_SIZE);
		ps = new PlayerState(BoardUtilities.DEFAULT_BOARD_SIZE, addRandomShips);
		userTurn = new java.util.Random().nextInt(2) == 1; // lets the player go first roughly half the time
	}

	/***
	 * Executes a game
	 */
	public void playGame() {
		System.out.print("Now, time to play! ");
		System.out.println(userTurn? "By random selection, you go first :)\n"
				: "By random selection, the computer goes first :P\n");
		while (cs.locationsLeft != 0 && ps.locationsLeft != 0) {
			if (userTurn) {
				displayGameProgress(); // displays boards, scores, and guesses
				ps.makeMove(cs);
			} else {
				cs.makeMove(ps);
			}
			userTurn = !userTurn; // alternate turns
		}
		endGame();
	}
	
	/***
	 * Displays scores and guesses made so far by both players
	 */
	private void displayGameProgress() {
		// show user-controlled boards, as well as the target board of the computer (but not its ships)
		BoardUtilities.showBoards(ps.ownBoard, cs.targetBoard, ps.targetBoard, cs.ownBoard, false);
		System.out.println("\nScore: Player - " + (Ship.TOTAL_SHIP_OCCUPANCY - cs.locationsLeft)
				+ " Computer - " + (Ship.TOTAL_SHIP_OCCUPANCY - ps.locationsLeft));
		System.out.println("Guesses: Player - " + ps.numGuesses
				+ " Computer - " + cs.numGuesses + "\n");
	}
	
	/***
	 * Concludes a game, displaying the final results, and starts a new game if desired
	 */
	private void endGame() {
		// final board configurations. Allows player to see the computer's ships when the game is over
		BoardUtilities.showBoards(ps.ownBoard, cs.targetBoard, ps.targetBoard, cs.ownBoard, true);
		if (ps.locationsLeft == 0) { // no floating ships on player's board, so player loses
			System.out.println("YOU LOSE HAHAHAHA");
		} else {
			System.out.println("\nYou win!");
		}
		System.out.println("Final Score: Player - " + (Ship.TOTAL_SHIP_OCCUPANCY - cs.locationsLeft)
				+ " Computer - " + (Ship.TOTAL_SHIP_OCCUPANCY - ps.locationsLeft) + "\n");
		System.out.println("Total Guesses: Player - " + ps.numGuesses
				+ " Computer - " + cs.numGuesses + "\n");
		System.out.println("Play again? Enter 0 for no. Enter any other character for yes.");
		Scanner s = new Scanner(System.in);
		String userInput = s.nextLine();
		if (!userInput.equals("0")) { // user wants to play again, so reinstantiate references to reset
			cs = new ComputerState(BoardUtilities.DEFAULT_BOARD_SIZE);
			ps = new PlayerState(BoardUtilities.DEFAULT_BOARD_SIZE);
			userTurn = new java.util.Random().nextInt(2) == 1;
			playGame();
		}
	}
}