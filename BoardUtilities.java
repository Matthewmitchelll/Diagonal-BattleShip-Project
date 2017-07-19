package project2;

class BoardUtilities {
	
	public static final int DEFAULT_BOARD_SIZE = 10; // typical rule of the game
	
	private static String[] boardIdentifications = new String[]{"Player 1's Board    ", "Player 2's Targets  ",
		"Player 1's Targets  ", "Player 2's Board"};
	
	/*
	 * This class contains some general utility methods for working with boards, used for
	 * placing ships on boards and showing the player everything he or she is allowed to
	 * see throughout the game (the player's own board and target boards, as well as the
	 * computer's target board). For a BattleSimulator, includes a function to show all
	 * 4 boards involved in a game. Also includes functions for determining whether a
	 * location is on the board
	 */
	
	/***
	 * Determines whether an integer pair is on a character board
	 * 
	 * @param row the row of inquiry
	 * @param col the column of inquiry
	 * @param board the board of inquiry
	 * @return true if the (row, col) pair is on the board else false
	 */
	public static boolean onTheBoard(int row, int col, char[][] board) {
		return row >= 0 && col >= 0 && row < board.length && col < board.length;
	}
	
	/***
	 * Determines whether an integer pair is on a board of locations
	 * 
	 * @param row the row of inquiry
	 * @param col the column of inquiry
	 * @param board the board of inquiry
	 * @return true if the (row, col) pair is on the board else false
	 */
	public static boolean onTheBoard(int row, int col, Location[][] board) {
		return row >= 0 && col >= 0 && row < board.length && col < board.length;
	}
	
	/***
	 * Checks if a location for a ship is valid given the coordinates for the front of the ship,
	 * its orientation, its size, and a board configuration
	 * 
	 * @param row essentially the y coordinate for the front of the ship
	 * @param col essentially the x coordinate for the front of the ship
	 * @param direction direction the front of the ship is facing
	 * @param shipSize size of the prospective ship
	 * @param board the board configuration inspected
	 * @return true if the ship can be placed in this orientation, false otherwise
	 */
	public static boolean validSetLocation(int row, int col,
			Direction direction, int shipSize, char[][] board) {
		int startRow = row, startCol = col;
		for (int i = 0; i < shipSize; i++) {
			if (startRow < 0 || startCol < 0 || startRow >= board.length || startCol >= board.length) 
				return false; // ran off the board
			if (board[startRow][startCol] == 'o') {
				// current location is occupied, so this ship can't be in this orientation
				return false;
			}
			// the if statements within the switch make it impossible for a ship to be placed diagonally
			// such that it forms a diagonal intersection (as in an x) with an orthogonally oriented
			// ship already on the board (such a ship wouldn't actually share a coordinate with the
			// prospective ship, so this would otherwise be allowed. This is a preventive measure -
			// ships can't overlap, including when they are diagonal! Some technically valid
			// configurations are disallowed by this, however, as in the case where two separate ships
			// are oriented on the same diagonal and are right next to each other, not overlapping. 
			// This code will interpret these ships as a single ship, and will prevent any ship from
			// being placed orthogonally to them, crossing the point where they meet, even though it
			// wouldn't be considered placing a ship on top of another in real life
			switch (direction) {
			// uses the direction the front of the ship is facing to determine the next point to check.
				case NORTH:
					startRow++;
					break;
				case NORTHEAST:
					startRow++;
					startCol--;
					if (startRow < board.length && startCol >= 0 &&
							board[startRow][startCol + 1] == 'o' &&
							board[startRow - 1][startCol] == 'o' &&
							i < shipSize - 1) return false;
					break;
				case EAST:
					startCol--;
					break;
				case SOUTHEAST:
					startRow--;
					startCol--;
					if (startRow >= 0 && startCol >= 0 &&
							board[startRow][startCol + 1] == 'o' &&
							board[startRow + 1][startCol] == 'o' &&
							i < shipSize - 1) return false;
					break;
				case SOUTH:
					startRow--;
					break;
				case SOUTHWEST:
					startRow--;
					startCol++;
					if (startRow >= 0 && startCol < board.length &&
							board[startRow][startCol - 1] == 'o' &&
							board[startRow + 1][startCol] == 'o' &&
							i < shipSize - 1) return false;
					break;
				case WEST:
					startCol++;
					break;
				case NORTHWEST:
					startRow++;
					startCol++;
					if (startRow < board.length && startCol < board.length &&
							board[startRow][startCol - 1] == 'o' &&
							board[startRow - 1][startCol] == 'o' &&
							i < shipSize - 1) return false;
			}
		}
		return true;
	}
	
	/***
	 * Places a ship of a specified size at the specified location in the specified orientation.
	 * on the specified board. Assumes that this request will not place a ship in an occupied location
	 * 
	 * @param row essentially the y coordinate for the front of the ship
	 * @param col essentially the x coordinate for the front of the ship
	 * @param direction direction the front of the ship is facing
	 * @param shipSize size of the prospective ship
	 * @param board the board to add a ship to
	 */
	public static void setShip(int row, int col, Direction direction, int shipSize, char[][] board) {
		int startRow = row, startCol = col;
		for (int i = 0; i < shipSize; i++) {
			// marks the appropriate board occupied at the current location
			board[startRow][startCol] = 'o';
			// updates the next location to mark occupied according to the orientation of the ship
			switch (direction) {
				case NORTH:
					startRow++;
					break;
				case NORTHEAST:
					startRow++;
					startCol--;
					break;
				case EAST:
					startCol--;
					break;
				case SOUTHEAST:
					startRow--;
					startCol--;
					break;
				case SOUTH:
					startRow--;
					break;
				case SOUTHWEST:
					startRow--;
					startCol++;
					break;
				case WEST:
					startCol++;
					break;
				case NORTHWEST:
					startRow++;
					startCol++;
			}
		}
	}
	
	/***
	 * Provides a human-readible printout of the boards associated with a game. The
	 * boolean parameter specifies whether to show the last board argument passed in,
	 * and it is used to control whether to show opponent ship configurations or not.
	 * Opponent ship configurations are shown in simulator executions
	 * 
	 * @param playerOwnBoard the player's own board
	 * @param computerTargetBoard the computer's target board, to show the computer's progress
	 * @param playerTargetBoard the player's target board, to show the player's progress
	 * @param computerOwnBoard the computer's own board
	 * @param showAll boolean value specifying whether to show the last board
	 */
	public static void showBoards(char[][] playerOwnBoard,
			char[][] computerTargetBoard, char[][] playerTargetBoard,
			char[][] computerOwnBoard, boolean showAll) {
		String spacing = "     "; // provides spacing between board displays
		System.out.print(" "); // offsets column enumeration so it is aligned with double digit rows
		for (int i = 0; i < (showAll ? 4 : 3); i++) { // iterates 4 times to show 4 boards or 3 to show 3
			System.out.print(spacing); // adds spacing to for the next board display
			System.out.print(" "); // offsets print statement so it is aligned with column outputs
			System.out.print(" " + boardIdentifications[i]);
		}
		System.out.println();
		System.out.print(" ");
		for (int i = 0; i < (showAll ? 4 : 3); i++) {
			System.out.print(spacing);
			System.out.print(" "); // offsets column enumeration so it is aligned with column outputs
			for (int col = 0; col < playerOwnBoard.length; col++) {
				System.out.print(" " + (col + 1));
			}
		}
		System.out.println();
		for (int row = 0; row < playerOwnBoard.length; row++) {
			if (row < 9) System.out.print(" "); // aligns single digit rows with double digit rows
			System.out.print(spacing); // adds spacing to for the next board display
			System.out.print((row + 1));
			for (int col = 0; col < playerOwnBoard.length; col++) {
				System.out.print(" "); // space between columns
				System.out.print(playerOwnBoard[row][col]);
			}
			if (row < 9) System.out.print(" ");
			System.out.print(spacing + (row + 1));
			for (int col = 0; col < computerTargetBoard.length; col++) {
				System.out.print(" ");
				System.out.print(computerTargetBoard[row][col]);
			}
			if (row < 9) System.out.print(" ");
			System.out.print(spacing + (row + 1));
			for (int col = 0; col < playerTargetBoard.length; col++) {
				System.out.print(" ");
				System.out.print(playerTargetBoard[row][col]);
			}
			if (showAll) { // all 4 boards are requested, so show the last one
				if (row < 9) System.out.print(" ");
				System.out.print(spacing + (row + 1));
				for (int col = 0; col < computerOwnBoard.length; col++) {
					System.out.print(" ");
					System.out.print(computerOwnBoard[row][col]);
				}
			}
			System.out.println();
		}
	}
	
	/***
	 * Prints a representation of the given character board to the console to facilitate board configuration
	 * 
	 * @param board the board to print to the console
	 */
	public static void showBoard(char[][] board) {
		System.out.print("  "); // offsets column enumeration so it is aligned with locations
		for (int col = 0; col < board.length; col++) {
			System.out.print(" " + (col + 1)); // adds one to start counting columns with 1
		}
		System.out.println();
		for (int row = 0; row < board.length; row++) {
			if (row < 9) System.out.print(" "); // aligns single digit rows with double digit rows
			System.out.print((row + 1) + " "); // start counting rows with 1
			for (int col = 0; col < board.length; col++) {
				System.out.print(board[row][col]);
				System.out.print(" "); // put space between columns for readability
			}
			System.out.println();
		}
	}
}