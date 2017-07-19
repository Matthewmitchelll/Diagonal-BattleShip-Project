package project2;

import java.util.Scanner;

class PlayerState extends State {
	
	/*
	 * Represents the state of the game for the player (see the State abstract class for details)
	 * 
	 * Extends State by adding functions to populate the player's board with ships and process
	 * player moves according to his or her instructions.
	 */
	
	/***
	 * Constructs the state of the player at the start of the game
	 * 
	 * @param boardSize the size of a board for the game
	 */
	PlayerState(int boardSize) {
		super(boardSize, false); // boards and trackers setup
		addShips(); // adds ships to the board as specified by the user
	}
	
	/***
	 * Constructs the state of the player at the start of the game
	 * 
	 * @param boardSize the size of a board for the game
	 * @param addingRandomShips boolean value to determine whether random ships need to be added, true if so else false
	 */
	PlayerState(int boardSize, boolean addingRandomShips) {
		super(boardSize, addingRandomShips);
		if (addingRandomShips) super.addShips();
		else {
			addShips();
		}
	}
	
	/***
	 * Acquires desired ship configurations from the user and adds ships to the player's board as requested
	 */
	public void addShips() {
		System.out.println("Okay, now let's create your board! We will add a ship one at a time, from\n"
				+ "largest to smallest. Please specify the coordinate for the front of the ship along\n"
				+ "with the direction you desire the end of the ship to face. For example, to place the\n"
				+ "front of a ship in the fourth row at the fifth column whose end faces northeast, enter\n"
				+ "'4 5 northeast' without quotes. Capitalizing the direction is unecessary, though\n"
				+ "spelling it correctly is :).\n");
		Scanner s = new Scanner(System.in); // takes keyboard input
		Ship[] ships = Ship.values(); // acquires the different ship types
		for (int i = 0; i < ships.length; i++) {
			queryForShips(ships[i].toString().toLowerCase(), ships[i].size);
			String userInput = s.nextLine(); // retrieves the user's reply
			while (!processedShip(userInput, ships[i].size)) { // checks the validity of the user's reply
				System.out.println("Sorry, that isn't a valid request. Make sure that your current\n"
						+ "board position allows for a ship of the current size to have its front at\n"
						+ "the location you desire. Requests should be of the form 'row# col# direction'\n"
						+ "without the quotes but with the spaces.");
				queryForShips(ships[i].toString().toLowerCase(), ships[i].size);
				userInput = s.nextLine();
			}
		}
		System.out.println("Board so far:");
		BoardUtilities.showBoard(ownBoard); // show the user his or her final board configuration
	}

	/***
	 * Prints output to help the user configure his or her board and asks for his or her instructions
	 * 
	 * @param ship current ship to be placed
	 * @param shipSize size of ship to be placed
	 */
	private void queryForShips(String ship, int shipSize) {
		// allows the user to see the current board configuration to facilitate his or her decisions
		System.out.println("Board so far:");
		BoardUtilities.showBoard(ownBoard);
		System.out.println("Waiting for next ship input. How would you like to position your\n"
				+ ship + " (of size " + shipSize + "?)");
	}
	
	/***
	 * Processes the player's reply for where to add a ship next and heeds the request if the
	 * input is valid
	 * 
	 * @param userInput the player's request for where to add a ship next
	 * @param shipSize the size of the ship to be added
	 * @return true if the request is valid and has been processed, false else
	 */
	private boolean processedShip(String userInput, int shipSize) {
		String[] instructions = userInput.split(" "); // separates the user's reply based on the space regex
		if (instructions.length != 3) return false; // all replies should be length 3 (row, col, direction)
		try {
			int row = Integer.parseInt(instructions[0]) - 1; // subtracts 1 to allow user to normally index
			int col = Integer.parseInt(instructions[1]) - 1;
			Direction dir = Direction.valueOf(instructions[2].toUpperCase()).opposite();
			// checks if the ship would not run off the board or intersect with other ships before placing
			if (BoardUtilities.validSetLocation(row, col, dir, shipSize, ownBoard)) {
				BoardUtilities.setShip(row, col, dir, shipSize, ownBoard);
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}
	
	/***
	 * Prompts the player to make a move and processes his or her reply
	 * 
	 * @param opponent the state of the computer (this player's opponent)
	 */
	public void makeMove(State opponent) {
		System.out.println("Your turn! Select a location to guess as a row column\n"
				+ "pair, with a space in between. No decimals!");
		Scanner s = new Scanner(System.in);
		String userInput = s.nextLine();
		while (!processedMove(userInput, opponent)) {
			System.out.println("Sorry, that isn't a valid input. Please enter a space-separated\n"
					+ "pair of numbers specifying the row and column numbers, respectively. For\n"
					+ "example, to target the cell in the third row at the fourth column, enter\n"
					+ "'3 4' without quotes but with the space in between. Make sure you pick a\n"
					+ "new location!");
			userInput = s.nextLine();
		}
	}
	
	/***
	 * Processes the players instructions for where to target next, and does so if the input is valid
	 * 
	 * @param move the move a player has requested
	 * @param opponent the state of the computer (this player's opponent)
	 * @return true if the move is valid and has been processed, false else
	 */
	private boolean processedMove(String move, State opponent) {
		String[] coords = move.split(" ");
		if (coords.length != 2) return false; // answer must by composed of just a row and column number
		try {
			int row = Integer.parseInt(coords[0]) - 1; // allows user to use 1 indexing
			int col = Integer.parseInt(coords[1]) - 1;
			if (row < 0 || col < 0
					|| row >= targetBoard.length || col >= targetBoard.length) {
				return false; // coordinate is off the board
			} else if (targetBoard[row][col] != '-') {
				return false; // already targeted this location
			}
			target(row, col, opponent);
			return true;
		} catch (NumberFormatException e) {
			return false; // user didn't input a string that can be parsed as an integer
		}
	}
}