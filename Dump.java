package project2;

class Dump {

	//	Calculates the initial total number of occupied, unhit spaces. The first
	//	ship added to the board is of size minimumShipSize, and the size of each
	//	successive ship added increases by one, yielding the formula:
	//	minimumShipSize*numShips + (numShips - 1)*(numShips)/2 = minimumShipSize
	//	                                                        + (minimumShipSize + 1)
	//	                                                        + (minimumShipSize + 2) + ...
	//	                                                        + (minimumShipSize + (numShips - 1))

	//	private static int boardSize;
	//	private static int minimumShipSize;
	//	private static int numShips;
	//	
	//	private static ComputerState cs;
	//	private static PlayerState ps;
	//	
	//	private static boolean userTurn;
	//	
	//	private static void createGame() {
	//		Scanner s = new Scanner(System.in);
	//		setBoardSize(s);
	//		setMinimumShipSize(s);
	//		setNumShips(s);
	//		cs = new ComputerState(boardSize, minimumShipSize, numShips);
	//		ps = new PlayerState(boardSize, minimumShipSize, numShips);
	//		userTurn = new java.util.Random().nextInt(2) == 1;
	//	}
	//
	//	private static void playGame() {
	//		System.out.print("Now, time to play! ");
	//		System.out.println(userTurn? "By random selection, you go first :)"
	//				: "By random selection, the computer goes first :P");
	//		while (cs.locationsLeft != 0 && ps.locationsLeft != 0) {
	//			if (userTurn) {
	//				BoardUtilities.showBoards(ps, cs);
	//				ps.makeMove(cs);
	//			} else {
	//				cs.makeMove(ps);
	//			}
	//			userTurn = !userTurn;
	//		}
	//		endGame();
	//	}
	//	
	//	private static void endGame() {
	//		BoardUtilities.showBoards(ps, cs);
	//		if (ps.locationsLeft == 0 && cs.locationsLeft == 0) {
	//			// user must have entered to play with zero ships
	//			System.out.println("Did you not want to play? You entered 0 ships!");
	//		} else if (ps.locationsLeft == 0) {
	//			System.out.println("YOU LOSE HAHAHAHA");
	//		} else {
	//			System.out.println("You win!");
	//		}
	//	}
	//
	//	private static void setBoardSize(Scanner s) {
	//		System.out.println("Welcome to BattleShip! How large would you like the board to be?\n"
	//				+ "Simply enter a single number, as all games will be played on a square.");
	//		String userInput = s.nextLine();
	//		while (BoardUtilities.notInteger(userInput)) {
	//			System.out.println("Please enter a single number. For best results, enter a number between\n"
	//					+ "5 and 10. No decimals allowed :P");
	//			userInput = s.nextLine();
	//		}
	//		boardSize = Integer.parseInt(userInput);
	//	}
	//
	//	private static void setMinimumShipSize(Scanner s) {
	//		System.out.println("What would you like the minimum ship size to be? This must be less\n"
	//				+ "than or equal to the board size you just specified.");
	//		String userInput = s.nextLine();
	//		while (BoardUtilities.notInteger(userInput) || Integer.parseInt(userInput) > boardSize) {
	//			System.out.println("Please enter a single number that is less than or equal to the board size\n"
	//					+ "you specified (" + boardSize + "). No decimals allowed :P");
	//			userInput = s.nextLine();
	//		}
	//		minimumShipSize = Integer.parseInt(userInput);
	//	}
	//
	//	private static void setNumShips(Scanner s) {
	//		System.out.println("How many ships would you like the game to use? The smallest ship size "
	//				+ "is set to " + minimumShipSize + ".\nFor each ship added, the size "
	//				+ "will increase by one. So for example, if you want 3 ships,\nthe first ship will be "
	//				+ "size " + minimumShipSize + ", the second will be size " + (minimumShipSize + 1) + ""
	//				+ ", and the third will be size " + (minimumShipSize + 2) + ".\nMake sure that the size"
	//				+ " of the last ship added isn't larger than the span of the board!");
	//		String userInput = s.nextLine();
	//		while (BoardUtilities.notInteger(userInput)
	//				|| !BoardUtilities.validSetup(boardSize, minimumShipSize, Integer.parseInt(userInput))){
	//			System.out.println("Please enter a single number. If you did enter a number and you\n"
	//					+ "still see this message, try a smaller number! Your largest ship would have\n"
	//					+ "ended up being too large for at least 1 configuration of previously set ships :P\n"
	//					+ "No decimals allowed! :P");
	//			userInput = s.nextLine();
	//		}
	//		numShips = Integer.parseInt(userInput);
	//	}

	//	public static void main(String[] args) {
	//		createGame();
	//		playGame();
	//	}

	//	/***
	//	 * Marks a coordinate as being hit
	//	 * 
	//	 * @param row row of the hit coordinate
	//	 * @param col column of the hit coordinate
	//	 */
	//	private final void hit(int row, int col) {
	//		ownBoard[row][col] = 'x';
	//		locationsLeft--; // one step closer to losing
	//	}

	//	private static void setComputerLevels(Scanner s) {
	//		System.out.println("What level would you like your first computer to be? Enter either 1, 2, or 3");
	//		String userInput = s.nextLine();
	//		while (!validNumber(userInput)
	//				|| Integer.parseInt(userInput) < 1 || Integer.parseInt(userInput) > 3) {
	//			System.out.println("Sorry, that input is invalid. Please enter either 1, 2, or 3, no decimals.");
	//			userInput = s.nextLine();
	//		}
	//		computerOneLevel = Integer.parseInt(userInput);
	//		System.out.println("What level would you like your second computer to be? Enter either 1, 2, or 3");
	//		userInput = s.nextLine();
	//		while (!validNumber(userInput)
	//				|| Integer.parseInt(userInput) < 1 || Integer.parseInt(userInput) > 3) {
	//			System.out.println("Sorry, that input is invalid. Please enter either 1, 2, or 3, no decimals.");
	//			userInput = s.nextLine();
	//		}
	//		computerTwoLevel = Integer.parseInt(userInput);
	//	}

	//	if (numGames - winCounter > 0) {
	//		System.out.println("Avg. number of guesses needed for Computer 1 to win: "
	//				+ computerOneNumGuesses/(numGames - winCounter));
	//	} else {
	//		System.out.println("Computer 1 never won. Avg. number of guesses needed to win: N/A.");
	//	}
	//	if (winCounter > 0) {
	//		System.out.println("Avg. number of guesses needed for Computer 2 to win: "
	//				+ computerTwoNumGuesses/winCounter);
	//	} else {
	//		System.out.println("Computer 2 never won. Avg. number of guesses needed to win: N/A.");
	//	}

	//	System.out.println("Welcome to Battleship Simulation! Here, you will simulate games\n"
	//			+ "between 2 computer players. The players may differ in level!\n");

	//	System.out.println("Welcome to Performance Simulation! Here, you will simulate games\n"
	//			+ "involving only 1 computer for testing purposes.\n");

	//	package project2;
	//
	//	import java.util.Random;
	//
	//	class Board {
	//		
	//		private char[][] board;
	//		
	//		Board(int boardSize) {
	//			board = new char[boardSize][boardSize];
	//			clear();
	//		}
	//		
	//		public void clear() {
	//			for (int row = 0; row < board.length; row++) {
	//				for (int col = 0; col < board.length; col++) {
	//					board[row][col] = '-';
	//				}
	//			}
	//		}
	//		
	//		public void addShips() {
	//			Random rand = new Random();
	//			int row, col; // will specify potential locations for the fronts of ships
	//			Direction[] directions = Direction.values();
	//			Direction dir; // will specify direction that the fronts of the ships will be facing 
	//			for (int i = 0; i < Ship.shipSizes.length; i++) {
	//				do {
	//					row = rand.nextInt(board.length);
	//					col = rand.nextInt(board.length);
	//					dir = directions[rand.nextInt(directions.length)];
	//					// loops if the randomly selected x and y coordinates are invalid with the
	//					// current board when the ship's front is pointing in the direction specified.
	//					// direction is 0 for NORTH, and increments clockwise so that 7 is NORTHWEST
	//				} while (!BoardUtilities.validSetLocation(row, col, dir, Ship.shipSizes[i], board));
	//				BoardUtilities.setShip(row, col, dir, Ship.shipSizes[i], board);
	//			}
	//		}
	//	}

	/*
	 * This class implements a mechanism for targeting ships. Once an occupied spot is found, the
	 * code here will examine surrounding areas until all known x chains on the target board are
	 * completely surrounded by o marks (indicating ships have been completely sunk).
	 * 
	 * More specifically, the targeter works as follows. It will maintain a 2D integer array that
	 * holds priority scores for all untargeted locations. The score for a location will be the
	 * number of known occupied locations it is immediately bordering. Once a location is targeted,
	 * it will have its score set to -1 for the remainder of the game, to avoid being targeted again.
	 * 
	 * In the beginning of a new game, the computer will query locations based on a flexibility
	 * measure. Let f be the sum of lengths of all paths of untargeted locations spanning out in every
	 * direction from some location i. The computer will choose the untargeted location that maximizes
	 * f as its next target (Thus, the first guess the computer makes will always be 5 5). The
	 * computer will keep doing this until it finds an occupied location.
	 * 
	 * Let z be the coordinate of the most newly discovered ship location by the above process. Once
	 * z has been marked on the target board and has its priority score set to -1, all untargeted
	 * locations next to z will have their priority score incremented, and the targeter will next
	 * aim in the direction it deems will most likely lead to another occupied slot. Call this
	 * direction d. The targeter determines d by following paths of untargeted locations in all
	 * directions until they end at either a targeted location or a board edge while keeping track
	 * of the lengths of all paths followed. The directions are then sorted according to the sum of
	 * their corresponding path length and a heuristic value, in non-increasing order. The heuristic
	 * value favors going towards the center of the board for more flexible locations. d is then set
	 * as the first element of this list, and the next slot in this direction from z will be targeted
	 * on the next move. Call this new location y.
	 * 
	 * If y is discovered to be an occupied location, appropriate locations in the 2D array are
	 * incremented and the targeter will continue going in direction d specified above, incrementing
	 * priorities along the way, until it runs into either the edge of the board or a targeted
	 * location.
	 * 
	 * If y is not discovered to be an occupied location, the next direction in the list of directions
	 * specified above will be inspected until a direction leads to another occupied location (which
	 * will always happen at least once, as all ships are at least size 2). The targeter will then
	 * follow the resulting direction until it reaches a dead end as described, incrementing
	 * priorities as above.
	 * 
	 * Next, the targeter will go back to the first location it discovered (z) and explore the
	 * opposite direction from d until it hits a dead end, incrementing priorities again. The results
	 * of these discoveries will be a chain of x locations on the target board in between o's or board
	 * edges (for example: oxxxxo or |xxxxxo or oxx| or |xxx| - the last of which only occurs for
	 * diagonally oriented x's that are near the corners of the board).
	 * 
	 * Once a new chain of x locations has been fully uncovered, the targeter will examine the
	 * untargeted locations surrounding the chain by simply repeatedly selecting the one with the
	 * highest priority in the 2D priority array (setting its priority to -1 and incrementing all
	 * untargeted locations bordering it if it's occupied as usual). Once all locations have a
	 * priority of zero, all x chains are completely covered by o marks on the target board. At this
	 * point, the computer will begin querying locations randomly again until a new occupied location
	 * is found, at which point the entire process is repeated, until the game ends.
	 */

	//	if (originalLocation == null && surroundingLocations.size() > 0) {
	//	} else if (surroundingLocations.size() > 0 && lastDir == null) {
	//		if (originalLocation[0] == nextMove[0] && originalLocation[1] == nextMove[1]) {
	//			// gets most mobile surroundingLocation and removes it from surroundingLocations
	//			ArrayList<Integer> mostMobileLocation = surroundingLocations.remove(TargetUtilities.getMostFlexibleSlot(surroundingLocations, targetBoard));
	//			nextMove[0] = mostMobileLocation.get(0); // selects a row
	//			nextMove[1] = mostMobileLocation.get(1); // selects a column from within the row
	//		} else {
	//			lastDir = Direction.directionFromTo(originalLocation[0], originalLocation[1], nextMove[0], nextMove[1]);
	//			nextMove = Direction.locationTo(nextMove, lastDir);
	//			if (nextMove[0] < 0 || nextMove[1] < 0 ||
	//					nextMove[0] >= targetBoard.length || nextMove[1] >= targetBoard.length ||
	//					targetBoard[nextMove[0]][nextMove[1]] != '-') {
	//				lastDir = Direction.opposite(lastDirection);
	//				nextMove = Direction.locationTo(nextMove, lastDir);
	//				if (nextMove[0] < 0 || nextMove[1] < 0 ||
	//						nextMove[0] >= targetBoard.length || nextMove[1] >= targetBoard.length ||
	//						targetBoard[nextMove[0]][nextMove[1]] != '-') {
	//					originalLocation = null;
	//					lastDir = null;
	//					// gets most mobile surroundingLocation and removes it from surroundingLocations
	//					ArrayList<Integer> mostMobileLocation = surroundingLocations.remove(TargetUtilities.getMostFlexibleSlot(surroundingLocations, targetBoard));
	//					nextMove[0] = mostMobileLocation.get(0); // selects a row
	//					nextMove[1] = mostMobileLocation.get(1); // selects a column from within the row
	//				}
	//			}
	//		}
	//	} else if (lastDir != null) {
	//		nextMove = TargetUtilities.getMostFlexibleSlot(targetBoard);
	//		locationAdded[nextMove[0]][nextMove[1]] = true; // avoids retargeting the first location
	//	}

	//	if (priorityLocation.priority > 0) {
	//	if (targetBoard[nextMove.row][nextMove.col] == 'x') {
	//		System.out.println("Looks like a location we prioritized was an x, great. ");
	//		addNewSurroundingLocations();
	//	}
	//	usePriority();
	//} else if (nextMove != null && targetBoard[nextMove.row][nextMove.col] == 'x') { // our last move hit an occupied location
	//	// updates our list of bordering locations with the locations that border our last target
	//	System.out.println("Found an x, updating everything.");
	//	addNewSurroundingLocations();
	//	if (originalLocation == null) {
	//		System.out.println("It was a new x, so updating base and looking for most flexible slots.");
	//		if (surroundingLocations.size() > 0) {
	//			System.out.println("That previous x was useful, good.");
	//			originalLocation = new Location(nextMove.row, nextMove.col, targetBoard.length);
	//			// gets most mobile surroundingLocation and removes it from surroundingLocations
	//			nextMove = TargetUtilities.getMostFlexibleMove(surroundingLocations, targetBoard);
	//		} else {
	//			System.out.println("That previous x was completely surrounded by borders or targeted "
	//					+ "locations. Gotta check most flexible locations now.");
	//			nextMove = TargetUtilities.getMostFlexibleMove(movePool, targetBoard);
	//		}
	//	} else if (lastDir == null) {
	//		System.out.println("It was a second x, so updating my direction to make a chain.");
	//		setDirectionAndNextMove();
	//		updateMovePool(nextMove.row, nextMove.col);
	//		validifyDirection();
	//	} else {
	//		System.out.println("Found a new x while chaining, on a roll here.");
	//		nextMove = nextMove.locationTowards(lastDir);
	//		validifyDirection();
	//	}
	//} else {
	//	System.out.println("No x here, or first move of the game.");
	//	if (originalLocation == null) {
	//		System.out.println("No x and no original, guess I'm all the way back at square 1.");
	//		nextMove = TargetUtilities.getMostFlexibleMove(movePool, targetBoard);
	//	} else if (lastDir == null) { // (?) looking for that line
	//		System.out.println("No x but an original, just no direction, looking for that chain.");
	//		if (surroundingLocations.size() > 0) {
	//			System.out.println("Still have surroundingLocations, phew.");
	//			// gets most mobile surroundingLocation and removes it from surroundingLocations
	//			nextMove = TargetUtilities.getMostFlexibleMove(surroundingLocations, targetBoard);
	//		} else {
	//			System.out.println("No locations left! Guess the last x found was while using priorities, "
	//					+ "it must have finished off a chain. Gotta just check the most flexible locations"
	//					+ "now. Should be at a corner, or edge, or completely surrounded by targeted"
	//					+ "locations.");
	//			nextMove = TargetUtilities.getMostFlexibleMove(movePool, targetBoard);
	//		}
	//	} else {
	//		System.out.println("Ended a chain, checking other direction.");
	//		checkOtherDirection();
	//		if (nextMoveNotValid()) {
	//			System.out.println("Completed a chain the long way, gonna be busy with priorities.");
	//			resetStateAndUsePriority();
	//		}
	//	}
	//}
	//System.out.println("\nComputer target: " + (nextMove.row + 1) + " " + (nextMove.col + 1));

	//	protected final void updateSurroundingLocations(int row, int col) {
	//	for (int i = 0; i < surroundingLocations.size(); i++) {
	//		if (surroundingLocations.get(i).row == row &&
	//				surroundingLocations.get(i).col == col) {
	//			surroundingLocations.remove(i);
	//			break;
	//		}
	//	}
	//}

	//protected final void updateMovePool(int row, int col) {
	//	for (int i = 0; i < surroundingLocations.size(); i++) {
	//		if (movePool.get(i).row == row &&
	//				movePool.get(i).col == col) {
	//			movePool.remove(i);
	//			break;
	//		}
	//	}
	//}

	//protected void resetStateAndUsePriority() {
	//	originalLocation = null;
	//	lastDir = null;
	//	usePriority();
	//}

	//protected void setDirectionAndNextMove() {
	//	lastDir = originalLocation.directionTo(nextMove.row, nextMove.col);
	//	nextMove = nextMove.locationTowards(lastDir);
	//	updateMovePool(nextMove.row, nextMove.col);
	//}

	//protected void checkOtherDirection() {
	//	lastDir = lastDir.opposite();
	//	nextMove = originalLocation.locationTowards(lastDir);
	//	updateMovePool(nextMove.row, nextMove.col);
	//}

	//protected void validifyDirection() {
	//	updateMovePool(nextMove.row, nextMove.col);
	//	if (nextMoveNotValid()) {
	//		System.out.println("The second x has no continuation, checking other direction.");
	//		checkOtherDirection();
	//		if (nextMoveNotValid()) {
	//			System.out.println("The opposite direction had nothing, 2 birds with one x.");
	//			resetStateAndUsePriority();
	//		}
	//	}
	//}

	//protected void usePriority() {
	//	System.out.println("Using priority");
	//	priorityLocation = TargetUtilities.getHighestPriorityMove(movePool, targetBoard);
	//	if (priorityLocation.priority == 0) {
	//		System.out.println("No priority locations left. Meaning no known squares next to x's.");
	//		nextMove = priorityLocation;
	//	} else if (notPossible(priorityLocation)) {
	//		System.out.println("I skipped a location!");
	//		targetBoard[priorityLocation.row][priorityLocation.col] = 'o';
	//		updateSurroundingLocations(priorityLocation.row, priorityLocation.col);
	//		nextMove = TargetUtilities.getMostFlexibleMove(movePool, targetBoard);
	//	} else {
	//		System.out.println("Gonna keep prioritizing.");
	//		nextMove = priorityLocation;
	//	}
	//}

	//	protected Location originalLocation;
	//	protected Location priorityLocation = new Location(-1, -1, 0);

	//	protected Direction lastDir;

	//	if (nextMove.priority == 1) {
	//	System.out.println("No locations next to x's, going with flexibility and variance.");
	//	movePool.add(nextMove);
	//	nextMove = TargetUtilities.getMostFlexibleMove(movePool, targetBoard);
	//	System.out.println("Traits for current Location: (" + (nextMove.row + 1) + " "
	//			+ (nextMove.col + 1) + ") " + nextMove.priority + " " + nextMove.totalXConnections + " "
	//			+ nextMove.mobility + " " + nextMove.variance + " " + nextMove.surroundingXs + " "
	//			+ nextMove.surroundingFreedom);
	//} else {
	//	System.out.println("Promising next location. Traits for current Location: (" + (nextMove.row + 1)
	//			+ " " + (nextMove.col + 1) + ") " + nextMove.priority + " " + nextMove.totalXConnections
	//			+ " " + nextMove.mobility + " " + nextMove.variance + " " + nextMove.surroundingXs + " "
	//			+ nextMove.surroundingFreedom);
	//}

	//	package project2;
	//
	//	abstract class Prioritizer extends Targeter {
	//		
	//		/*
	//		 * This class lays the foundation for prioritizing targeters, which prioritize moves
	//		 * according to various measures of utility. The measures are explained in detail in
	//		 * its subclasses. The Prioritizer class extends the Targeter class by adding functions
	//		 * that help determine when a location cannot possibly hold a ship location.
	//		 */
	//
	//		/***
	//		 * Constructs a Prioritizer
	//		 * 
	//		 * @param targetBoard the targetBoard of the overlying ComputerState object
	//		 */
	//		Prioritizer(char[][] targetBoard) {
	//			super(targetBoard);
	//		}
	//	}

	//	private static int getSurroundingFreedom(Location startCoord, char[][] targetBoard) {
	//	int surroundingFreedom = 0;
	//	for (Direction d : Direction.directions) {
	//		Location neighbor = startCoord.locationTowards(d);
	//		if (neighbor.onTheBoard() && targetBoard[neighbor.row][neighbor.col] == '-') {
	//			surroundingFreedom++;
	//		}
	//	}
	//	return surroundingFreedom;
	//}

	//	private static int getSurroundingXs(Location startCoord, char[][] targetBoard) {
	//		int surroundingXs = 0;
	//		for (Direction d : Direction.directions) {
	//			Location neighbor = startCoord.locationTowards(d);
	//			if (neighbor.onTheBoard() && targetBoard[neighbor.row][neighbor.col] == 'x') {
	//				surroundingXs++;
	//			}
	//		}
	//		return surroundingXs;
	//	}

	//	private static int longestChainPossible(Location link, char[][] targetBoard) {
	//		Direction[] relevantDirections = new Direction[]{Direction.NORTH, Direction.NORTHEAST, Direction.EAST, Direction.SOUTHEAST};
	//		int chainLengthPossible = 0;
	//		Location copy1 = new Location(link.row, link.col, targetBoard.length);
	//		Location copy2 = new Location(link.row, link.col, targetBoard.length);
	//		for (Direction d : relevantDirections) {
	//			Direction opposite = d.opposite();
	//			int score = 1;
	//			do {
	//				copy1 = moveOn(copy1, d);
	//				if (copy1.onTheBoard() && targetBoard[copy1.row][copy1.col] == 'x') {
	//					score++;
	//				} else {
	//					break;
	//				}
	//			} while (true);
	//			do {
	//				copy2 = moveOn(copy2, opposite);
	//				if (copy2.onTheBoard() && targetBoard[copy2.row][copy2.col] == 'x') {
	//					score++;
	//				} else {
	//					break;
	//				}
	//			} while (true);
	//			copy1.row = link.row;
	//			copy1.col = link.col;
	//			copy2.row = link.row;
	//			copy2.col = link.col;
	//			if (score > chainLengthPossible) chainLengthPossible = score;
	//		}
	//		System.out.print("(" + link.row + " " + link.col + "): " + chainLengthPossible);
	//	return chainLengthPossible;
	//	}

	//	public static int longestChainPossible(Location dependent, Location provider, char[][] targetBoard) {
	//		int chainLengthPossible = 0;
	//		Location copy1 = new Location(dependent.row, dependent.col, targetBoard.length);
	//		Location copy2 = new Location(dependent.row, dependent.col, targetBoard.length);
	//		for (Direction d : Direction.forwardDirections) {
	//			Direction opposite = d.opposite();
	//			int score = 1;
	//			do {
	//				copy1 = moveOn(copy1, d);
	//				if (copy1.onTheBoard() &&
	//						(copy1.row != provider.row || copy1.col != provider.col) &&
	//						(targetBoard[copy1.row][copy1.col] == 'x' || targetBoard[copy1.row][copy1.col] == '-')) {
	//					score++;
	//				} else {
	//					break;
	//				}
	//			} while (true);
	//			do {
	//				copy2 = moveOn(copy2, opposite);
	//				if (copy2.onTheBoard() &&
	//						(copy2.row != provider.row || copy2.col != provider.col) &&
	//						(targetBoard[copy2.row][copy2.col] == 'x' || targetBoard[copy2.row][copy2.col] == '-')) {
	//					score++;
	//				} else {
	//					break;
	//				}
	//			} while (true);
	//			copy1.row = dependent.row;
	//			copy1.col = dependent.col;
	//			copy2.row = dependent.row;
	//			copy2.col = dependent.col;
	//			if (score > chainLengthPossible) chainLengthPossible = score;
	//		}
	//		System.out.println("Max chain length possible for a neighbor x: " + chainLengthPossible);
	//		return chainLengthPossible;
	//	}

	//	private static int getScore(Location coord, char[][] targetBoard, boolean gettingPriority) {
	//		Location copy = new Location(coord.row, coord.col, targetBoard.length);
	//		int score = 0;
	//		for (Direction d : Direction.directions) {
	//			do {
	//				copy = moveOn(copy, d);
	//				if (copy.onTheBoard() &&
	//						(targetBoard[copy.row][copy.col] == 'x' && gettingPriority
	//						    || targetBoard[copy.row][copy.col] == '-' && !gettingPriority)) {
	//					score += 1;
	//				} else {
	//					break;
	//				}
	//			} while (true);
	//			copy.row = coord.row;
	//			copy.col = coord.col;
	//		}
	//		return score;
	//	}

	//	else if (movePool.get(i).maxImmersionChainLength == highestPriorityMove.maxImmersionChainLength) {
	//		if (movePool.get(i).immersion > highestPriorityMove.immersion) {
	//			indexOfMove = i;
	//			highestPriorityMove = movePool.get(i);
	//		} else if (movePool.get(i).immersion == highestPriorityMove.immersion) {
	//			if (movePool.get(i).maxPotentialChainLength > highestPriorityMove.maxPotentialChainLength) {
	//				indexOfMove = i;
	//				highestPriorityMove = movePool.get(i);
	//			} else if (movePool.get(i).maxPotentialChainLength == highestPriorityMove.maxPotentialChainLength) {
	//				if (movePool.get(i).potential > highestPriorityMove.potential) {
	//					indexOfMove = i;
	//					highestPriorityMove = movePool.get(i);
	//				} else if (movePool.get(i).potential == highestPriorityMove.potential) {
	//					if (movePool.get(i).maxFreedomChainLength > highestPriorityMove.maxFreedomChainLength) {
	//						indexOfMove = i;
	//						highestPriorityMove = movePool.get(i);
	//					} else if (movePool.get(i).maxFreedomChainLength == highestPriorityMove.maxFreedomChainLength) {
	//						if (movePool.get(i).freedom > highestPriorityMove.freedom) {
	//							indexOfMove = i;
	//							highestPriorityMove = movePool.get(i);
	//						} else if (movePool.get(i).freedom == highestPriorityMove.freedom) {
	//							if (movePool.get(i).variance < highestPriorityMove.variance) {
	//								indexOfMove = i;
	//								highestPriorityMove = movePool.get(i);
	//							} else if (movePool.get(i).variance == highestPriorityMove.variance) {
	//								if (movePool.get(i).surroundingImmersion > highestPriorityMove.surroundingImmersion) {
	//									indexOfMove = i;
	//									highestPriorityMove = movePool.get(i);
	//								} else if (movePool.get(i).surroundingImmersion == highestPriorityMove.surroundingImmersion) {
	//									if (movePool.get(i).surroundingPotential > highestPriorityMove.surroundingPotential) {
	//										indexOfMove = i;
	//										highestPriorityMove = movePool.get(i);
	//									} else if (movePool.get(i).surroundingPotential == highestPriorityMove.surroundingPotential) {
	//										if (movePool.get(i).surroundingFreedom > highestPriorityMove.surroundingFreedom) {
	//											indexOfMove = i;
	//											highestPriorityMove = movePool.get(i);
	//										}
	//									}
	//								}
	//							}
	//						}
	//					}
	//				}
	//			}
	//		}
	//	}

	//	/***
	//	 * Constructs a TestSimulator object that will run with pre-specified parameters
	//	 * 
	//	 * @param numGames the number of games per simulation desired
	//	 * @param verbose boolean value specifying whether to show in-game progress, true to do so, else false
	//	 */
	//	TestSimulator(int numGames, boolean verbose, double ... neuralWeights) {
	//		super(numGames, verbose); // sets general Simulator settings
	//		guesser = new ComputerState(BattleShip.DEFAULT_BOARD_SIZE, 4, neuralWeights);
	//		dummy = new ComputerState(BattleShip.DEFAULT_BOARD_SIZE, true); // true, specifying a dummy
	//	}

	//	package project2;
	//
	//	import java.util.Random;
	//
	//	class NeuralNetSim {
	//		
	//		Random rand = new Random();
	//		
	//		public double maxICWeight = 10*rand.nextDouble();
	//		public double maxPCWeight = 10*rand.nextDouble();
	//		public double maxFCWeight = 10*rand.nextDouble();
	//		public double iWeight = 10*rand.nextDouble();
	//		public double pWeight = 10*rand.nextDouble();
	//		public double fWeight = 10*rand.nextDouble();
	//		public double vWeight = -10*rand.nextDouble();
	//		public double sIWeight = 10*rand.nextDouble();
	//		public double sPWeight = 10*rand.nextDouble();
	//		public double sFWeight = 10*rand.nextDouble();
	//		
	//		private int numRuns;
	//		private int numGames;
	//		private boolean verbose;
	//		
	//		public ComputerState guesser; // the computer employing the desired test strategy
	//		private ComputerState dummy; // the computer that won't make any moves
	//		
	//		public double numGuesses;
	//		public double bestPerformance = 100.0;
	//		
	//		public double bestMaxICWeight;
	//		public double bestMaxPCWeight;
	//		public double bestMaxFCWeight;
	//		public double bestIWeight;
	//		public double bestPWeight;
	//		public double bestFWeight;
	//		public double bestVWeight;
	//		public double bestSIWeight;
	//		public double bestSPWeight;
	//		public double bestSFWeight;
	//		
	//		NeuralNetSim(int numRuns, int numGames, boolean verbose) {
	//			this.numRuns = numRuns;
	//			this.numGames = numGames;
	//			this.verbose = verbose;
	//			
	//		}
	//		
	//		public void run() {
	//			long start = System.nanoTime();
	//			for (int i = 0; i < numRuns; i++) {
	//				for (long j = 0; j < numGames; j++) {
	//					guesser = new ComputerState(BattleShip.DEFAULT_BOARD_SIZE, 4, maxICWeight, maxPCWeight, maxFCWeight, iWeight,
	//							pWeight, fWeight, vWeight, sIWeight, sPWeight, sFWeight);
	//					dummy = new ComputerState(BattleShip.DEFAULT_BOARD_SIZE, true); // true, specifying a dummy
	//					while (dummy.locationsLeft != 0) { // ship(s) still afloat
	//						guesser.makeMove(dummy);
	//						if (verbose) {
	//							BoardUtilities.showBoards(guesser.ownBoard, dummy.targetBoard,
	//									guesser.targetBoard, dummy.ownBoard, true); // shows all boards
	//							System.out.println(
	//									"\nScore: " + (Ship.TOTAL_SHIP_OCCUPANCY - dummy.locationsLeft));
	//							System.out.println("Guesses: " + guesser.numGuesses + "\n");
	//						}
	//					}
	//					numGuesses += guesser.numGuesses;
	//				}
	//				if (numGuesses/numGames < bestPerformance) {
	//					adjustSettings();
	//					bestPerformance = numGuesses/numGames;
	//				}
	//				resetWeights();
	//				numGuesses = 0;
	//			}
	//			long end = System.nanoTime();
	//			System.out.println((end - start)/(Math.pow(10, 9)));
	//			displayBestResults();
	//		}
	//		
	//		private void adjustSettings() {
	//			bestMaxICWeight = maxICWeight;
	//			bestMaxPCWeight = maxPCWeight;
	//			bestMaxFCWeight = maxFCWeight;
	//			bestIWeight = iWeight;
	//			bestPWeight = pWeight;
	//			bestFWeight = fWeight;
	//			bestVWeight = vWeight;
	//			bestSIWeight = sIWeight;
	//			bestSPWeight = sPWeight;
	//			bestSFWeight = sFWeight;
	//		}
	//		
	//		private void resetWeights() {
	//			maxICWeight = 10*rand.nextDouble();
	//			maxPCWeight = 10*rand.nextDouble();
	//			maxFCWeight = 10*rand.nextDouble();
	//			iWeight = 10*rand.nextDouble();
	//			pWeight = 10*rand.nextDouble();
	//			fWeight = 10*rand.nextDouble();
	//			vWeight = -10*rand.nextDouble();
	//			sIWeight = 10*rand.nextDouble();
	//			sPWeight = 10*rand.nextDouble();
	//			sFWeight = 10*rand.nextDouble();
	//		}
	//
	//		private void displayBestResults() {
	//			System.out.println("Best performance: " + bestPerformance);
	//			System.out.println("Weights for this performance: " + bestMaxICWeight + " " + bestMaxPCWeight + " "
	//					+ bestMaxFCWeight + " " + bestIWeight + " " + bestPWeight + " " + bestFWeight + " "
	//					+ bestVWeight + " " + bestSIWeight + " " + bestSPWeight + " " + bestSFWeight);
	//		}
	//	}

	//	package project2;
	//
	//	class NeuralLocation extends SmartLocation {
	//		
	//		NeuralLocation(int row, int col, NeuralLocation[][] boardLocations, int ... neuralWeights) {
	//			super(row, col, boardLocations);
	//			setWeights(neuralWeights);
	//		}
	//		
	//		public void setWeights(int ... neuralWeights) {
	//			if (neuralWeights.length > 0) maxICWeight = neuralWeights[0];
	//			if (neuralWeights.length > 1) maxPCWeight = neuralWeights[1];
	//			if (neuralWeights.length > 2) maxFCWeight = neuralWeights[2];
	//			if (neuralWeights.length > 3) iWeight = neuralWeights[3];
	//			if (neuralWeights.length > 4) pWeight = neuralWeights[4];
	//			if (neuralWeights.length > 5) fWeight = neuralWeights[5];
	//			if (neuralWeights.length > 6) vWeight = neuralWeights[6];
	//			if (neuralWeights.length > 7) sIWeight = neuralWeights[7];
	//			if (neuralWeights.length > 8) sPWeight = neuralWeights[8];
	//			if (neuralWeights.length > 9) sFWeight = neuralWeights[9];
	//		}
	//	}

	//	package project2;
	//
	//	import java.util.Comparator;
	//
	//	class SmartLocationComparator implements Comparator<SmartLocation> {
	//		
	//		public int compare(SmartLocation first, SmartLocation second) {
	//			double prior1 = WeightedPrioritizer.priorityScore(first);
	//			double prior2 = WeightedPrioritizer.priorityScore(second);
	//			if (prior1 > prior2) {
	//				return 1;
	//			} else if (prior1 < prior2) {
	//				return -1;
	//			}
	//			return 0;
	//		}
	//	}

	//	private SmartLocation focus(boolean focusOnOriginal) {
	//		if (focusOnOriginal) {
	//			SmartLocation continuation = originalX.locationTowards(originalFocus);
	//			if (continuation != null && targetBoard[continuation.row][continuation.col] != '-') {
	//				updateMovePool(continuation.row, continuation.col);
	//				originalX = continuation;
	//				return continuation;
	//			}
	//			originalX = null;
	//			return getHighestPriorityMove();
	//		}
	//		if (continuationFocus == null) {
	//			System.out.println(xContinuation.row + " " + xContinuation.col);
	//			BoardUtilities.showBoard(targetBoard);
	//		}
	//		SmartLocation continuation = xContinuation.locationTowards(continuationFocus);
	//		if (continuation != null && targetBoard[continuation.row][continuation.col] != '-') {
	//			updateMovePool(continuation.row, continuation.col);
	//			xContinuation = continuation;
	//			return continuation;
	//		}
	//		return getHighestPriorityMove();
	//	}

	// The Commander Algorithm that will beat most humans:
	// To determine if it is possible for a location to contain an x, given knowledge of locations of x's:
	// 1) look at all x's that you are next to. For each, do:
	//	    maintain a list of sizes seen so far
	// 2) go down the chain until you hit the last x.
	// 3) once end chain is found, update list of sizes with size of this chain
	// 4) examine each x you haven't looked at so far that is next to where we are now. for each, do:
	//	          see if it starts a chain of size that can be added to current sizes, and
	// 5)       if it can, update list of sizes, and
	// 6)          check all side x's of this x, see if you can start a chain of size compatible with list so far
	// 7)          if ran out of side x's to check and still good, simply go to an x we haven't seen yet', and
	// 8)                 see if a legal chain can be started from there
	// 9)      convenient if we have a way of distinguishing x marks that are at corners
	// 10) if ever see an impossible orientation, chain up false to original to say so
	// 11) if none of the original's x locations is a possible continuation, return false. It's impossible.

	//	if (knownXs.size() == Ship.TOTAL_SHIP_OCCUPANCY - 1 && prospectiveLocation.surroundingImmersion == 0) return true;
	//	HashMap<SmartLocation, Boolean> locationsUsed = new HashMap<SmartLocation, Boolean>();
	//	locationsUsed.put(prospectiveLocation, true);
	//	return dfsChainSearch(prospectiveLocation, null, locationsUsed, 4, 1);
	//}

	//protected boolean dfsChainSearch(SmartLocation prospectiveLocation, SmartLocation previousLocation,
	//		HashMap<SmartLocation, Boolean> locationsUsed, int indexOfDesiredSize, int currentSize) {
	//	if (locationsUsed.size() == Ship.TOTAL_SHIP_OCCUPANCY) return true;
	//	for (Direction d : Direction.directions) {
	//		SmartLocation neighbor = prospectiveLocation.locationTowards(d);
	//		if (targetBoard[neighbor.row][neighbor.col] == 'x' && ! locationsUsed.containsKey(neighbor)) {
	//			
	//		}
	//	}
	//	return false;
	//}

	//	protected boolean notPossible(TrackerLocation prospectiveLocation) {
	//	if (prospectiveLocation.potential == 0) return true;
	//	return prospectiveLocation.totalPossibilities == 0;

	//	while (notPossible(nextMove)) {
	//		targetBoard[nextMove.row][nextMove.col] = 'o'; // updates targetBoard without making the move
	//		TargetUtilities.updateFreedomAndPotentialProperties(nextMove, valueBoard, targetBoard);
	//		nextMove = getBestMove();
	//	}

	//	private boolean canFindValidConfiguration(TrackerLocation nextMove) {
	//	BoardUtilities.showBoard(targetBoard);
	//	for (int i = 0; i < movePool.size(); i++) System.out.print((movePool.get(i).row + 1) + " " + (movePool.get(i).col + 1) + " " + movePool.get(i).locationUsed + " " + movePool.get(i).surroundingImmersion + " ");
	//	System.out.println();
	//	xsFound.sort(null);
	//	for (int i = 0; i < xsFound.size(); i++) System.out.print((xsFound.get(i).row + 1) + " " + (xsFound.get(i).col + 1) + " " + xsFound.get(i).locationUsed + " " + xsFound.get(i).surroundingImmersion + " ");
	//	System.out.println();
	//	potentialLocationUsed = false;
	//	for (int i = 0; i < xsFound.size(); i++) {
	//		xsFound.get(i).locationUsed = true;
	//		if (configurationFound(nextMove, xsFound.get(i), null, 1, 0)) {
	//			xsFound.get(i).locationUsed = false;
	//			return true;
	//		}
	//		xsFound.get(i).locationUsed = false;
	//	}
	//	nextMove.locationUsed = true;
	//	potentialLocationUsed = true;
	//	if (configurationFound(nextMove, nextMove, null, 1, 0)) {
	//		nextMove.locationUsed = false;
	//		return true;
	//	}
	//	nextMove.locationUsed = false;
	//	potentialLocationUsed = false;
	//	for (int i = 0; i < movePool.size(); i++) {
	//		if (movePool.get(i) == nextMove) continue;
	//		movePool.get(i).locationUsed = true;
	//		if (configurationFound(nextMove, movePool.get(i), null, 1, 0)) {
	//			movePool.get(i).locationUsed = false;
	//			return true;
	//		}
	//		movePool.get(i).locationUsed = false;
	//	}
	//	return false;
	//}

	//private boolean configurationFound(TrackerLocation nextMove, TrackerLocation start, Direction lastDir,
	//		int currentSize, int indexOfSizeToLookFor) {
	////	System.out.println((nextMove.row + 1) + " " + (nextMove.col + 1) + " " + (start.row + 1) + " " + (start.col + 1) + " " + lastDir + " " + currentSize + " " + Ship.shipSizes[indexOfSizeToLookFor]);
	//	if (lastDir == null) {
	//		for (Direction d : Direction.directions) {
	//			TrackerLocation neighbor = start.locationTowards(d);
	//			if (neighbor != null && !neighbor.locationUsed && targetBoard[neighbor.row][neighbor.col] == 'x') {
	//				neighbor.locationUsed = true;
	//				if (configurationFound(nextMove, neighbor, d, currentSize + 1, indexOfSizeToLookFor)) {
	//					neighbor.locationUsed = false;
	//					return true;
	//				}
	//				neighbor.locationUsed = false;
	//			}
	//		}
	//		for (Direction d : Direction.directions) {
	//			TrackerLocation neighbor = start.locationTowards(d);
	//			if (neighbor != null && !neighbor.locationUsed && targetBoard[neighbor.row][neighbor.col] == '-') {
	//				neighbor.locationUsed = true;
	//				if (neighbor == nextMove) potentialLocationUsed = true;
	//				if (configurationFound(nextMove, neighbor, d, currentSize + 1, indexOfSizeToLookFor)) {
	//					neighbor.locationUsed = false;
	//					return true;
	//				}
	//				if (neighbor == nextMove) potentialLocationUsed = false;
	//				neighbor.locationUsed = false;
	//			}
	//		}
	//		start.locationUsed = false;
	//		return false;
	//	} else if (currentSize != Ship.shipSizes[indexOfSizeToLookFor]) {
	//		TrackerLocation neighbor = start.locationTowards(lastDir);
	//		if (neighbor != null && !neighbor.locationUsed && targetBoard[neighbor.row][neighbor.col] != 'o') {
	//			neighbor.locationUsed = true;
	//			if (neighbor == nextMove) potentialLocationUsed = true;
	//			if (configurationFound(nextMove, neighbor, lastDir, currentSize + 1, indexOfSizeToLookFor)) {
	//				neighbor.locationUsed = false;
	//				return true;
	//			}
	//			if (neighbor == nextMove) potentialLocationUsed = false;
	//			neighbor.locationUsed = false;
	//		}
	//		start.locationUsed = false;
	//		return false;
	//	} else if (indexOfSizeToLookFor == Ship.NUM_SHIPS - 1) {
	//		start.locationUsed = false;
	//		return potentialLocationUsed;
	//	} else {
	//		if (xsFound.size() > 0) {
	//			xsFound.sort(null);
	//			for (int i = 0; i < xsFound.size(); i++) {
	//				if (xsFound.get(i).locationUsed) break;
	//				xsFound.get(i).locationUsed = true;
	//				if (configurationFound(nextMove, xsFound.get(i), null, 1, indexOfSizeToLookFor + 1)) {
	//					xsFound.get(i).locationUsed = false;
	//					return true;
	//				}
	//				xsFound.get(i).locationUsed = false;
	//			}
	//		}
	//		if (!nextMove.locationUsed) {
	//			nextMove.locationUsed = true;
	//			potentialLocationUsed = true;
	//			if (configurationFound(nextMove, nextMove, null,
	//					1, indexOfSizeToLookFor + 1)) {
	//				nextMove.locationUsed = false;
	//				return true;
	//			}
	//			potentialLocationUsed = false;
	//			nextMove.locationUsed = false;
	//		}
	//		movePool.sort(null);
	//		for (int i = 0; i < movePool.size(); i++) {
	//			if (movePool.get(i).locationUsed) break;
	//			if (movePool.get(i) == nextMove) continue;
	//			movePool.get(i).locationUsed = true;
	//			if (configurationFound(nextMove, movePool.get(i), null, 1, indexOfSizeToLookFor + 1)) {
	//				movePool.get(i).locationUsed = false;
	//				return true;
	//			}
	//			movePool.get(i).locationUsed = false;
	//		}
	//		start.locationUsed = false;
	//		return false;
	//	}
	//}

	//	for (int fiveStart = 0; fiveStart < xsFound.size(); fiveStart++) {
	//		if (searchPool.get(fiveStart).longestReach < 4) continue;
	//		for (int fourStart = 0; fourStart < xsFound.size(); fourStart++) {
	//			if (fourStart == fiveStart) continue;
	//			if (searchPool.get(fourStart).longestReach < 3) continue;
	//			for (int threeStart1 = 0; threeStart1 < searchPool.size(); threeStart1++) {
	//				if (threeStart1 == fiveStart || threeStart1 == fourStart) continue;
	//				if (searchPool.get(threeStart1).longestReach < 2) continue;
	//				for (int threeStart2 = 0; threeStart2 < searchPool.size(); threeStart2++) {
	//					if (threeStart2 == fiveStart || threeStart2 == fourStart || threeStart2 == threeStart1) continue;
	//					if (searchPool.get(threeStart2).longestReach < 2) continue;
	//					for (int twoStart = 0; twoStart < searchPool.size(); twoStart++) {
	//						if (twoStart == fiveStart || twoStart == fourStart || twoStart == threeStart1 || twoStart == threeStart2) continue;
	//						if (searchPool.get(twoStart).longestReach < 1) continue;
	//						searchPool.get(twoStart).wantedShipSize = 2;
	//						locationStack.add(searchPool.get(twoStart));
	//						searchPool.get(threeStart2).wantedShipSize = 3;
	//						locationStack.add(searchPool.get(threeStart2));
	//						searchPool.get(threeStart1).wantedShipSize = 3;
	//						locationStack.add(searchPool.get(threeStart1));
	//						searchPool.get(fourStart).wantedShipSize = 4;
	//						locationStack.add(searchPool.get(fourStart));
	//						searchPool.get(fiveStart).wantedShipSize = 5;
	//						locationStack.add(searchPool.get(fiveStart));
	//						while (locationStack.size() > 0) {
	//							currentLocation = locationStack.remove(locationStack.size() - 1);
	//							if (currentLocation.wantedShipSize != Ship.shipSizes[indexOfSizeToLookFor]) {
	//								for (int j = 0; j < locationStack.size(); j++) {
	//									locationStack.get(j).orientation = null;
	//									locationStack.get(j).wantedShipSize = 0;
	//								}
	//								for (int j = 0; j < locationsUsed.size(); j++) {
	//									locationsUsed.get(j).locationUsed = false;
	//									locationsUsed.get(j).validated = false;
	//									locationsUsed.get(j).orientation = null;
	//									locationsUsed.get(j).extensions = 0;
	//									locationsUsed.get(j).wantedShipSize = 0;
	//								}
	//								currentLocation = null;
	//								currentSize = 0;
	//								indexOfSizeToLookFor = 0;
	//								numXsUsed = 0;
	//								locationStack.clear();
	//								locationsUsed.clear();
	//								potentialLocationUsed = false;
	//								break;
	//							}
	//							locationsUsed.add(currentLocation);
	//							currentSize++;
	//							currentLocation.locationUsed = true;
	//							if (currentLocation == nextMove) potentialLocationUsed = true;
	//							if (targetBoard[currentLocation.row][currentLocation.col] == 'x') numXsUsed++;
	//							System.out.println("Current location: (" + (currentLocation.row + 1) + ", " + (currentLocation.col + 1) + ") Current size: " + currentSize
	//									+ " Index: " + indexOfSizeToLookFor + " numXsUsed: " + numXsUsed + " Total xs known: " + xsFound.size());
	//							System.out.print("Locations used so far: ");
	//							for (int j = 0; j < locationsUsed.size(); j++) System.out.print("(" + (locationsUsed.get(j).row + 1) + ", " + (locationsUsed.get(j).col + 1) + ") ");
	//							System.out.println();
	//							if (currentLocation.orientation == null) {
	//								System.out.println("Need to look for directions");
	//								for (Direction d : Direction.directions) {
	//									TrackerLocation neighbor = currentLocation.locationTowards(d);
	//									if (neighbor != null && neighbor.wantedShipSize == 0 && !neighbor.locationUsed && targetBoard[neighbor.row][neighbor.col] == '-') { // check x crosses too
	//										currentLocation.extensions++;
	//										neighbor.orientation = d;
	//										neighbor.wantedShipSize = currentLocation.wantedShipSize;
	//										locationStack.add(neighbor);
	//									}
	//								}
	//								for (Direction d : Direction.directions) {
	//									TrackerLocation neighbor = currentLocation.locationTowards(d);
	//									if (neighbor != null && neighbor.wantedShipSize == 0 && !neighbor.locationUsed && targetBoard[neighbor.row][neighbor.col] == 'x') { // check x crosses too
	//										currentLocation.extensions++;
	//										neighbor.orientation = d;
	//										neighbor.wantedShipSize = currentLocation.wantedShipSize;
	//										locationStack.add(neighbor);
	//									}
	//								}
	//								System.out.println(currentLocation.extensions);
	//							} else if (currentSize != Ship.shipSizes[indexOfSizeToLookFor]) {
	//								System.out.println("Moving right along...");
	//								TrackerLocation neighbor = currentLocation.locationTowards(currentLocation.orientation);
	//								if (neighbor != null && neighbor.wantedShipSize == 0 && !neighbor.locationUsed && targetBoard[neighbor.row][neighbor.col] != 'o') { // check x crosses too
	//									System.out.println("Looks like I can keep going");
	//									neighbor.orientation = currentLocation.orientation;
	//									neighbor.wantedShipSize = currentLocation.wantedShipSize;
	//									locationStack.add(neighbor);
	//								} else {
	//									System.out.println("Looks like I can't keep going");
	//									boolean exhausted = false;
	//									for (int j = locationsUsed.size() - 1; j >= 0 && !locationsUsed.get(j).validated; j--) {
	//										if (locationsUsed.get(j).extensions <= 1) {
	//											if (targetBoard[locationsUsed.get(j).row][locationsUsed.get(j).col] == 'x') numXsUsed--;
	//											else if (locationsUsed.get(j) == nextMove) potentialLocationUsed = false;
	//											locationsUsed.get(j).locationUsed = false;
	//											locationsUsed.get(j).orientation = null;
	//											if (locationsUsed.get(j).extensions == 1) {
	//												exhausted = true;
	//											}
	//											locationsUsed.get(j).extensions = 0;
	//											locationsUsed.get(j).wantedShipSize = 0;
	//											locationsUsed.remove(j);
	//											currentSize--;
	//										} else {
	//											locationsUsed.get(j).extensions--;
	//										}
	//									}
	//									if (exhausted) {
	//										for (int j = 0; j < locationStack.size(); j++) {
	//											locationStack.get(j).orientation = null;
	//											locationStack.get(j).wantedShipSize = 0;
	//										}
	//										for (int j = 0; j < locationsUsed.size(); j++) {
	//											locationsUsed.get(j).locationUsed = false;
	//											locationsUsed.get(j).validated = false;
	//											locationsUsed.get(j).orientation = null;
	//											locationsUsed.get(j).extensions = 0;
	//											locationsUsed.get(j).wantedShipSize = 0;
	//										}
	//										currentLocation = null;
	//										currentSize = 0;
	//										indexOfSizeToLookFor = 0;
	//										numXsUsed = 0;
	//										locationStack.clear();
	//										locationsUsed.clear();
	//										potentialLocationUsed = false;
	//										break;
	//									}
	//								}
	//							} else if (indexOfSizeToLookFor == Ship.NUM_SHIPS - 1) {
	//								System.out.println("Looks like we might be done");
	//								if (potentialLocationUsed && numXsUsed == xsFound.size()) {
	//									System.out.println("Yay, we're done");
	//									for (int j = 0; j < locationStack.size(); j++) {
	//										locationStack.get(j).orientation = null;
	//										locationStack.get(j).wantedShipSize = 0;
	//									}
	//									for (int j = 0; j < locationsUsed.size(); j++) {
	//										locationsUsed.get(j).locationUsed = false;
	//										locationsUsed.get(j).validated = false;
	//										locationsUsed.get(j).orientation = null;
	//										locationsUsed.get(j).extensions = 0;
	//										locationsUsed.get(j).wantedShipSize = 0;
	//									}
	//									searchPool.clear();
	//									currentLocation = null;
	//									currentSize = 0;
	//									indexOfSizeToLookFor = 0;
	//									numXsUsed = 0;
	//									locationStack.clear();
	//									locationsUsed.clear();
	//									potentialLocationUsed = false;
	//									return true;
	//								} else {
	//									System.out.println("Well, guess not");
	//									for (int j = locationsUsed.size() - 1; j >= 0 && !locationsUsed.get(j).validated; j--) {
	//										if (locationsUsed.get(j).extensions <= 1) {
	//											if (targetBoard[locationsUsed.get(j).row][locationsUsed.get(j).col] == 'x') numXsUsed--;
	//											else if (locationsUsed.get(j) == nextMove) potentialLocationUsed = false;
	//											locationsUsed.get(j).locationUsed = false;
	//											locationsUsed.get(j).orientation = null;
	//											locationsUsed.get(j).extensions = 0;
	//											locationsUsed.get(j).wantedShipSize = 0;
	//											locationsUsed.remove(j);
	//											currentSize--;
	//										} else {
	//											locationsUsed.get(j).extensions--;
	//										}
	//									}
	//								}
	//							} else {
	//								System.out.println("Looks like we found a possible ship, but aren't done yet");
	//								currentSize = 0;
	//								indexOfSizeToLookFor++;
	//								for (int j = locationsUsed.size() - 1; j >= 0 && !locationsUsed.get(j).validated; j--) {
	//									locationsUsed.get(j).validated = true;
	//								}
	//								for (int j = locationStack.size() - 1; j >= 0 && locationStack.get(j).wantedShipSize == Ship.shipSizes[indexOfSizeToLookFor - 1]; j--) {
	//									locationStack.get(j).orientation = null;
	//									locationStack.get(j).wantedShipSize = 0;
	//									locationStack.remove(j);
	//								}
	//							}
	//						}
	//					}
	//				}
	//			}
	//		}
	//	}

	//	else if (indexOfSizeToLookFor == Ship.NUM_SHIPS - 1) {
	//		System.out.println("Looks like we might be done");
	//		if (nextMove.locationUsed && numXsUsed == xsFound.size()) {
	//			System.out.println("Yay, we're done");
	//			hardReset();
	//			return true;
	//		} else {
	//			System.out.println("Well, guess not");
	//			for (int j = locationsUsed.size() - 1; j >= 0 && !locationsUsed.get(j).validated; j--) {
	//				if (locationsUsed.get(j).extensions <= 1) {
	//					if (targetBoard[locationsUsed.get(j).row][locationsUsed.get(j).col] == 'x') numXsUsed--;
	//					locationsUsed.get(j).locationUsed = false;
	//					locationsUsed.get(j).orientation = null;
	//					locationsUsed.get(j).extensions = 0;
	//					locationsUsed.remove(j);
	//					currentSize--;
	//				} else {
	//					locationsUsed.get(j).extensions--;
	//				}
	//			}
	//			if (locationStack.size() == 0) {
	//				if (targetBoard[currentLocation.row][currentLocation.col] == 'x' || resetIndices[indexOfSizeToLookFor] >= maxResetIndices[indexOfSizeToLookFor]) {
	//					System.out.println("Going back immediately");
	//					backtrack();
	//				} else if (numXsUsed == xsFound.size() && !nextMove.locationUsed && !triedNextMove) {
	//					System.out.println("Trying next move");
	//					triedNextMove = true;
	//					locationStack.add(nextMove);
	//				} else {
	//					resetIndices[indexOfSizeToLookFor]++;
	//				}
	//			}
	//			printStacks();
	//		}
	//	}
	//	if (indexOfSizeToLookFor == 4) {
	//		if (targetBoard[currentLocation.row][currentLocation.col] == 'x' || resetIndices[indexOfSizeToLookFor] >= maxResetIndices[indexOfSizeToLookFor]) {
	//			System.out.println("Going back immediately");
	//			backtrack();
	//		} else if (numXsUsed == xsFound.size() && !nextMove.locationUsed && !triedNextMove) {
	//			System.out.println("Trying next move");
	//			triedNextMove = true;
	//			locationStack.add(nextMove);
	//		} else {
	//			resetIndices[indexOfSizeToLookFor]++;
	//		}
	//	}

	//	private TrackerLocation currentLocation;
	//	private int currentSize = 0;
	//	private int indexOfSizeToLookFor = 0;
	//	
	//	private ArrayList<TrackerLocation> locationStack = new ArrayList<TrackerLocation>();
	//	private ArrayList<TrackerLocation> locationsUsed = new ArrayList<TrackerLocation>();
	//	
	//	private boolean triedNextMove = false;
	//	
	//	makeAssertions();
	//	locationStack.add(searchPool.get(0));
	//	while (locationStack.size() > 0) {
	////		assert movePool.size() > 50 || indexOfSizeToLookFor <= 3: "Size of movePool: " + movePool.size() + " Index: " + indexOfSizeToLookFor;
	////		BoardUtilities.showBoard(targetBoard);
	////		System.out.println("Location to check: (" + (nextMove.row + 1) + ", " + (nextMove.col + 1) + ")");
	//		assert numXsUsed <= xsFound.size(): "Apparently we used more xs than we know about";
	//		assert locationsUsed.size() < Ship.TOTAL_SHIP_OCCUPANCY: "Too many locations used";
	//		int total = 0;
	//		for (int j = 0; j < Ship.NUM_SHIPS; j++) {
	//			total += Ship.shipSizes[j];
	//			if (locationsUsed.size() == total) assert indexOfSizeToLookFor == j + 1: locationsUsed.size() + " " + indexOfSizeToLookFor;
	//		}
	//		currentLocation = locationStack.remove(locationStack.size() - 1);
	//		locationsUsed.add(currentLocation);
	////		System.out.println("Size of usedLocations: " + locationsUsed.size());
	//		currentSize++;
	//		currentLocation.locationUsed = true;
	//		if (targetBoard[currentLocation.row][currentLocation.col] == 'x') numXsUsed++;
	////		printProgress();
	//		if (currentLocation.orientation == null) {
	//			assert indexOfSizeToLookFor < 3: "Index is either 3 or 4, should have attempted to finish";
	////			System.out.println("Need to look for directions");
	//			for (int j = 0; j < Direction.NUM_DIRECTIONS; j++) {
	//				if (currentLocation.potentialValuesByDirection[j] >= Ship.shipSizes[indexOfSizeToLookFor] - 1) {
	//					TrackerLocation neighbor = currentLocation.locationTowards(Direction.directions[j]);
	//					if (neighbor != null && neighbor != nextMove && !neighbor.locationUsed && targetBoard[neighbor.row][neighbor.col] == '-'
	//							&& xsFound.size() + locationsUsed.size() - numXsUsed + 1 <= Ship.TOTAL_SHIP_OCCUPANCY) { // check x crosses too
	//						currentLocation.extensions++;
	//						neighbor.orientation = Direction.directions[j];
	//						locationStack.add(neighbor);
	//					}
	//				}
	//			}
	//			for (int j = 0; j < Direction.NUM_DIRECTIONS; j++) {
	//				if (currentLocation.potentialValuesByDirection[j] >= Ship.shipSizes[indexOfSizeToLookFor] - 1) {
	//					TrackerLocation neighbor = currentLocation.locationTowards(Direction.directions[j]);
	//					if (neighbor == nextMove && !neighbor.locationUsed
	//							&& xsFound.size() + locationsUsed.size() - numXsUsed + 1 <= Ship.TOTAL_SHIP_OCCUPANCY) { // check x crosses too
	//						currentLocation.extensions++;
	//						neighbor.orientation = Direction.directions[j];
	//						locationStack.add(neighbor);
	//					}
	//				}
	//			}
	//			for (int j = 0; j < Direction.NUM_DIRECTIONS; j++) {
	//				if (currentLocation.potentialValuesByDirection[j] >= Ship.shipSizes[indexOfSizeToLookFor] - 1) {
	//					TrackerLocation neighbor = currentLocation.locationTowards(Direction.directions[j]);
	//					if (neighbor != null && !neighbor.locationUsed && targetBoard[neighbor.row][neighbor.col] == 'x') { // check x crosses too
	//						currentLocation.extensions++;
	//						neighbor.orientation = Direction.directions[j];
	//						locationStack.add(neighbor);
	//					}
	//				}
	//			}
	////			System.out.println("Number of extensions: " + currentLocation.extensions);
	//			if (currentLocation.extensions == 0) {
	////				System.out.println("This location brought nothing to the table");
	//				assert locationStack.size() == 0: "locationStack isn't actually empty";
	//				if (targetBoard[currentLocation.row][currentLocation.col] == 'x') numXsUsed--;
	//				currentLocation.locationUsed = false;
	//				currentLocation.orientation = null;
	//				locationsUsed.remove(locationsUsed.size() - 1);
	//				currentSize--;
	////				System.out.println("Size of search pool: " + searchPool.size());
	////				System.out.println("Index: " + indexOfSizeToLookFor + " Search pool Index: " + resetIndices[indexOfSizeToLookFor]
	////						+ " Max Index: " + maxResetIndices[indexOfSizeToLookFor]);
	//				if (indexOfSizeToLookFor == 0) {
	//					if (resetIndices[0] >= maxResetIndices[0]) {
	////						System.out.println("This location is impossible!");
	//						break;
	//					} else {
	//						resetIndices[0]++;
	//					}
	//				} else {
	//					if (resetIndices[indexOfSizeToLookFor] >= maxResetIndices[indexOfSizeToLookFor]) {
	////						System.out.println("Going back almost immediately");
	//						backtrack();
	//					} else {
	//						resetIndices[indexOfSizeToLookFor]++;
	//					}
	//				}
	////				printStacks();
	//			}
	//		} else if (currentSize != Ship.shipSizes[indexOfSizeToLookFor]) {
	//			assert indexOfSizeToLookFor < 3: "Index is either 3 or 4, should have attempted to finish";
	////			System.out.println("Moving right along...");
	//			TrackerLocation neighbor = currentLocation.locationTowards(currentLocation.orientation);
	//			if (!neighbor.locationUsed
	//					&& (targetBoard[neighbor.row][neighbor.col] == 'x'
	//					|| targetBoard[neighbor.row][neighbor.col] == '-' && xsFound.size() + locationsUsed.size() - numXsUsed + 1 <= Ship.TOTAL_SHIP_OCCUPANCY)) { // check x crosses too
	////				System.out.println("Looks like I can keep going");
	//				neighbor.orientation = currentLocation.orientation;
	//				locationStack.add(neighbor);
	//			} else {
	////				System.out.println("Looks like I can't keep going");
	//				for (int j = locationsUsed.size() - 1; j >= 0 && !locationsUsed.get(j).validated; j--) {
	//					if (locationsUsed.get(j).extensions <= 1) {
	//						if (targetBoard[locationsUsed.get(j).row][locationsUsed.get(j).col] == 'x') numXsUsed--;
	//						locationsUsed.get(j).locationUsed = false;
	//						locationsUsed.get(j).orientation = null;
	//						locationsUsed.get(j).extensions = 0;
	//						locationsUsed.remove(j);
	//						currentSize--;
	//					} else {
	//						locationsUsed.get(j).extensions--;
	//					}
	//				}
	//				if (locationStack.size() == 0) {
	//					if (indexOfSizeToLookFor == 0) {
	//						if (resetIndices[0] >= maxResetIndices[0]) {
	////							System.out.println("This location is impossible!");
	//							break;
	//						} else {
	//							resetIndices[0]++;
	//						}
	//					} else {
	//						if (resetIndices[indexOfSizeToLookFor] >= maxResetIndices[indexOfSizeToLookFor]) {
	////							System.out.println("Going back almost immediately");
	//							backtrack();
	//						} else {
	//							resetIndices[indexOfSizeToLookFor]++;
	//						}
	//					}
	//				}
	////				printStacks();
	//			}
	//		} else {
	//			assert indexOfSizeToLookFor < 3: "Index is either 3 or 4, should have attempted to finish";
	////			System.out.println("Looks like we found a possible ship, but aren't done yet");
	//			for (int j = locationsUsed.size() - 1; j >= 0 && !locationsUsed.get(j).validated; j--) {
	//				locationsUsed.get(j).validated = true;
	//			}
	//			for (int j = 0; j < locationStack.size(); j++) locationStack.get(j).orientation = null;
	//			locationStack.clear();
	//			triedNextMove = false;
	//			currentSize = 0;
	//			indexOfSizeToLookFor++;
	//			if (indexOfSizeToLookFor == 3) {
	//				if (canFinish()) {
	////					System.out.println("Yay, we're done");
	//					hardReset();
	//					return true;
	//				}
	////				System.out.println("Was close, but had to backtrack");
	//				backtrack();
	//			}
	//		}
	//		while (locationStack.size() == 0) {
	//			assert indexOfSizeToLookFor < 3: "Index is either 3 or 4, should have attempted to finish";
	////			System.out.println("No stack locations left");
	//			if (numXsUsed == xsFound.size() && !nextMove.locationUsed && !triedNextMove && nextMove.longestReach >= Ship.shipSizes[indexOfSizeToLookFor] - 1) {
	////				System.out.println("Checking potential move");
	////				System.out.println(nextMove.longestReach);
	//				locationStack.add(nextMove);
	//				triedNextMove = true;
	//			} else {
	//				for (int j = resetIndices[indexOfSizeToLookFor]; j <= maxResetIndices[indexOfSizeToLookFor]; j++) {
	//					if (!searchPool.get(j).locationUsed && (searchPool.get(j) != nextMove || !triedNextMove)) {
	//						if (searchPool.get(j).longestReach < Ship.shipSizes[indexOfSizeToLookFor] - 1) continue;
	////						System.out.println("Chosen Location: (" + (searchPool.get(j).row + 1)+ ", " + (searchPool.get(j).col + 1) + ")"); 
	////						System.out.println("Longest Reach " + searchPool.get(j).longestReach);
	//						locationStack.add(searchPool.get(j));
	//						if (searchPool.get(j) == nextMove) triedNextMove = true;
	//						resetIndices[indexOfSizeToLookFor] = j;
	//						break;
	//					}
	//				}
	//			}
	//			if (locationStack.size() == 0) {
	//				if (indexOfSizeToLookFor > 0) {
	//					backtrack();
	//					if (resetIndices[indexOfSizeToLookFor] > maxResetIndices[indexOfSizeToLookFor]) {
	////						System.out.println("This location is impossible!");
	//						break;
	//					}
	//				} else {
	////					System.out.println("This location is impossible!");
	//					break;
	//				}
	//			}
	//		}
	//	}
	//	assert locationStack.size() == 0: "location stack is not empty, but we broke from the loop: " + locationStack.size();
	////	System.out.println("Size of usedLocations: " + locationsUsed.size());
	////	System.out.println("Looks like I fully exhausted this option");
	////	System.out.print("Reset values: ");
	////	for (int j = 0; j < Ship.NUM_SHIPS; j++) {
	////		System.out.print(resetIndices[j] + " ");
	////	}
	////	System.out.println();
	//	hardReset();
	////	assert false;
	//	return false;

	//	private void makeAssertions() {
	//	assert currentLocation == null: "currentLocation is not null";
	//	assert currentSize == 0: "currentSize is not 0";
	//	assert indexOfSizeToLookFor == 0: "indexOfSizeToLookFor is not 0";
	//	assert locationStack.size() == 0: "locationStack is not empty";
	//	assert locationsUsed.size() == 0: "locationsUsed is not empty";
	//	assert !triedNextMove: "triedNextMove is not false";
	//	for (int j = 0; j < resetIndices.length; j++) assert resetIndices[j] == 0: "At least one reset index was not reset properly";
	//}

	//private void printProgress() {
	//	System.out.println("Current location: (" + (currentLocation.row + 1) + ", " + (currentLocation.col + 1) + ") Current size: " + currentSize
	//			+ " Index: " + indexOfSizeToLookFor + " resetIndex: " + resetIndices[indexOfSizeToLookFor] + " maxResetIndex: "
	//			+ maxResetIndices[indexOfSizeToLookFor] + " numXsUsed: " + numXsUsed + " Total xs known: " + xsFound.size());
	//	printStacks();
	//}

	//private void printStacks() {
	//	System.out.print("Locations in search pool: ");
	//	for (int j = 0; j < searchPool.size(); j++) System.out.print("(" + (searchPool.get(j).row + 1) + ", " + (searchPool.get(j).col + 1) + ") ");
	//	System.out.println();
	////	System.out.print("Locations in the stack: ");
	////	for (int j = 0; j < locationStack.size(); j++) System.out.print("(" + (locationStack.get(j).row + 1) + ", " + (locationStack.get(j).col + 1) + ") ");
	////	System.out.println();
	//	System.out.print("Locations used so far: ");
	//	for (int j = 0; j < locationsUsed.size(); j++) System.out.print("(" + (locationsUsed.get(j).row + 1) + ", " + (locationsUsed.get(j).col + 1) + ") ");
	//	System.out.println();
	//}

	//private void backtrack() {
	////	System.out.println("Couldn't move on, need to start the last ship over");
	//	assert indexOfSizeToLookFor <= 3: "Index is too big";
	//	do {
	//		resetIndices[indexOfSizeToLookFor] = 0;
	//		indexOfSizeToLookFor--;
	//		for (int j = 0; j < Ship.shipSizes[indexOfSizeToLookFor]; j++) {
	//			if (targetBoard[locationsUsed.get(locationsUsed.size() - 1).row][locationsUsed.get(locationsUsed.size() - 1).col] == 'x') numXsUsed--;
	//			locationsUsed.get(locationsUsed.size() - 1).locationUsed = false;
	//			locationsUsed.get(locationsUsed.size() - 1).validated = false;
	//			locationsUsed.get(locationsUsed.size() - 1).orientation = null;
	//			locationsUsed.get(locationsUsed.size() - 1).extensions = 0;
	//			locationsUsed.remove(locationsUsed.size() - 1);
	//		}
	//		resetIndices[indexOfSizeToLookFor]++;
	//	} while (indexOfSizeToLookFor > 0 && resetIndices[indexOfSizeToLookFor] > maxResetIndices[indexOfSizeToLookFor]);
	//	triedNextMove = false;
	//}

	//private boolean canFinish() {
	//	assert indexOfSizeToLookFor == 3: "Apparently not looking for the second 3-length ship";
	////	printStacks();
	////	System.out.println("Location to check: (" + (nextMove.row + 1) + ", " + (nextMove.col + 1) + ")");
	//	TrackerLocation headOfThree;
	//	TrackerLocation middleOfThree;
	//	TrackerLocation endOfThree;
	//	TrackerLocation headOfTwo;
	//	TrackerLocation endOfTwo;
	//	if (numXsUsed == xsFound.size()) {
	////		System.out.println("All xs are used");
	//		if (nextMove.locationUsed) {
	////			System.out.println("The simplest case, the next location has been used");
	//			for (int i = 0; i < searchPool.size(); i++) {
	//				if (searchPool.get(i).locationUsed || searchPool.get(i).longestReach < Ship.shipSizes[indexOfSizeToLookFor] - 1) continue;
	//				headOfThree = searchPool.get(i);
	//				headOfThree.locationUsed = true;
	////				locationsUsed.add(headOfThree);
	////				printStacks();
	//				for (int j = 0; j < Direction.NUM_DIRECTIONS; j++) {
	//					if (headOfThree.potentialValuesByDirection[j] < Ship.shipSizes[indexOfSizeToLookFor] - 1) continue;
	//					middleOfThree = headOfThree.locationTowards(Direction.directions[j]);
	//					if (middleOfThree != null && !middleOfThree.locationUsed) {
	//						middleOfThree.locationUsed = true;
	////						locationsUsed.add(middleOfThree);
	////						printStacks();
	//						endOfThree = middleOfThree.locationTowards(Direction.directions[j]);
	//						if (endOfThree != null && !endOfThree.locationUsed) {
	//							endOfThree.locationUsed = true;
	////							locationsUsed.add(endOfThree);
	////							printStacks();
	//							for (int k = 0; k < searchPool.size(); k++) {
	//								if (searchPool.get(j).locationUsed || searchPool.get(j).longestReach < Ship.shipSizes[indexOfSizeToLookFor + 1] - 1) continue;
	//								headOfTwo = searchPool.get(j);
	//								headOfTwo.locationUsed = true;
	////								locationsUsed.add(headOfTwo);
	////								printStacks();
	//								for (int l = 0; l < Direction.NUM_DIRECTIONS; l++) {
	//									if (headOfTwo.potentialValuesByDirection[l] < Ship.shipSizes[indexOfSizeToLookFor + 1] - 1) continue;
	//									endOfTwo = headOfTwo.locationTowards(Direction.directions[l]);
	//									if (endOfTwo != null && !endOfTwo.locationUsed) {
	////										locationsUsed.add(endOfTwo);
	////										printStacks();
	//										headOfThree.locationUsed = false;
	//										middleOfThree.locationUsed = false;
	//										endOfThree.locationUsed = false;
	//										headOfTwo.locationUsed = false;
	//										return true;
	//									}
	//								}
	//								headOfTwo.locationUsed = false;
	////								locationsUsed.remove(locationsUsed.size() - 1);
	//							}
	//							endOfThree.locationUsed = false;
	////							locationsUsed.remove(locationsUsed.size() - 1);
	//						}
	//						middleOfThree.locationUsed = false;
	////						locationsUsed.remove(locationsUsed.size() - 1);
	//					}
	//				}
	//				headOfThree.locationUsed = false;
	////				locationsUsed.remove(locationsUsed.size() - 1);
	//			}
	//		} else {
	////			System.out.println("The next location has not been used yet");
	//			nextMove.locationUsed = true;
	////			locationsUsed.add(nextMove);
	////			printStacks();
	//			if (nextMove.longestReach >= 2) {
	//				for (int i = 0; i < Direction.NUM_DIRECTIONS; i++) {
	//					if (nextMove.potentialValuesByDirection[i] < Ship.shipSizes[indexOfSizeToLookFor] - 1) continue;
	//					middleOfThree = nextMove.locationTowards(Direction.directions[i]);
	//					if (middleOfThree != null && !middleOfThree.locationUsed) {
	//						middleOfThree.locationUsed = true;
	////						locationsUsed.add(middleOfThree);
	////						printStacks();
	//						endOfThree = middleOfThree.locationTowards(Direction.directions[i]);
	//						if (endOfThree != null && !endOfThree.locationUsed) {
	//							endOfThree.locationUsed = true;
	////							locationsUsed.add(endOfThree);
	////							printStacks();
	//							for (int j = 0; j < searchPool.size(); j++) {
	//								if (searchPool.get(j).locationUsed || searchPool.get(j).longestReach < Ship.shipSizes[indexOfSizeToLookFor + 1] - 1) continue;
	//								headOfTwo = searchPool.get(j);
	//								headOfTwo.locationUsed = true;
	////								locationsUsed.add(headOfTwo);
	////								printStacks();
	//								for (int k = 0; k < Direction.NUM_DIRECTIONS; k++) {
	//									if (headOfTwo.potentialValuesByDirection[k] < Ship.shipSizes[indexOfSizeToLookFor + 1] - 1) continue;
	//									endOfTwo = headOfTwo.locationTowards(Direction.directions[k]);
	//									if (endOfTwo != null && !endOfTwo.locationUsed) {
	////										locationsUsed.add(endOfTwo);
	////										printStacks();
	//										nextMove.locationUsed = false;
	//										middleOfThree.locationUsed = false;
	//										endOfThree.locationUsed = false;
	//										headOfTwo.locationUsed = false;
	//										return true;
	//									}
	//								}
	//								headOfTwo.locationUsed = false;
	////								locationsUsed.remove(locationsUsed.size() - 1);
	//							}
	//							endOfThree.locationUsed = false;
	////							locationsUsed.remove(locationsUsed.size() - 1);
	//						}
	//						middleOfThree.locationUsed = false;
	////						locationsUsed.remove(locationsUsed.size() - 1);
	//					}
	//				}
	//			}
	//			for (int i = 0; i < Direction.NUM_DIRECTIONS; i++) {
	//				if (nextMove.potentialValuesByDirection[i] < Ship.shipSizes[indexOfSizeToLookFor + 1] - 1) continue;
	//				endOfTwo = nextMove.locationTowards(Direction.directions[i]);
	//				if (endOfTwo != null && !endOfTwo.locationUsed) {
	//					endOfTwo.locationUsed = true;
	////					locationsUsed.add(endOfTwo);
	////					printStacks();
	//					for (int j = 0; j < searchPool.size(); j++) {
	//						if (searchPool.get(j).locationUsed || searchPool.get(j).longestReach < Ship.shipSizes[indexOfSizeToLookFor] - 1) continue;
	//						headOfThree = searchPool.get(j);
	//						headOfThree.locationUsed = true;
	////						locationsUsed.add(headOfThree);
	////						printStacks();
	//						for (int k = 0; k < Direction.NUM_DIRECTIONS; k++) {
	//							if (headOfThree.potentialValuesByDirection[k] < Ship.shipSizes[indexOfSizeToLookFor] - 1) continue;
	//							middleOfThree = headOfThree.locationTowards(Direction.directions[k]);
	//							if (middleOfThree != null && !middleOfThree.locationUsed) {
	//								middleOfThree.locationUsed = true;
	////								locationsUsed.add(middleOfThree);
	////								printStacks();
	//								endOfThree = middleOfThree.locationTowards(Direction.directions[k]);
	//								if (endOfThree != null && !endOfThree.locationUsed) {
	////									locationsUsed.add(endOfTwo);
	////									printStacks();
	//									endOfTwo.locationUsed = false;
	//									headOfThree.locationUsed = false;
	//									middleOfThree.locationUsed = false;
	//									return true;
	//								}
	//								middleOfThree.locationUsed = false;
	////								locationsUsed.remove(locationsUsed.size() - 1);
	//							}
	//						}
	//						headOfThree.locationUsed = false;
	////						locationsUsed.remove(locationsUsed.size() - 1);
	//					}
	//					endOfTwo.locationUsed = false;
	////					locationsUsed.remove(locationsUsed.size() - 1);
	//				}
	//			}
	//		}
	//	} else {
	////		System.out.println("This is where things get complicated");
	//		for (int i = 0; i < searchPool.size(); i++) {
	//			if (searchPool.get(i).locationUsed || searchPool.get(i).longestReach < Ship.shipSizes[indexOfSizeToLookFor] - 1) continue;
	//			headOfThree = searchPool.get(i);
	//			headOfThree.locationUsed = true;
	////			locationsUsed.add(headOfThree);
	////			printStacks();
	//			if (targetBoard[headOfThree.row][headOfThree.col] == 'x') numXsUsed++;
	//			for (int j = 0; j < Direction.NUM_DIRECTIONS; j++) {
	//				if (headOfThree.potentialValuesByDirection[j] < Ship.shipSizes[indexOfSizeToLookFor] - 1) continue;
	//				middleOfThree = headOfThree.locationTowards(Direction.directions[j]);
	//				if (middleOfThree != null && !middleOfThree.locationUsed) {
	//					middleOfThree.locationUsed = true;
	////					locationsUsed.add(middleOfThree);
	////					printStacks();
	//					if (targetBoard[middleOfThree.row][middleOfThree.col] == 'x') numXsUsed++;
	//					endOfThree = middleOfThree.locationTowards(Direction.directions[j]);
	//					if (endOfThree != null && !endOfThree.locationUsed) {
	//						endOfThree.locationUsed = true;
	////						locationsUsed.add(endOfThree);
	////						printStacks();
	//						if (targetBoard[endOfThree.row][endOfThree.col] == 'x') numXsUsed++;
	//						for (int k = 0; k < searchPool.size(); k++) {
	//							if (searchPool.get(k).locationUsed || searchPool.get(k).longestReach < Ship.shipSizes[indexOfSizeToLookFor + 1] - 1) continue;
	//							headOfTwo = searchPool.get(k);
	//							headOfTwo.locationUsed = true;
	////							locationsUsed.add(headOfTwo);
	////							printStacks();
	//							if (targetBoard[headOfTwo.row][headOfTwo.col] == 'x') numXsUsed++;
	//							for (int l = 0; l < Direction.NUM_DIRECTIONS; l++) {
	//								if (headOfTwo.potentialValuesByDirection[l] < Ship.shipSizes[indexOfSizeToLookFor + 1] - 1) continue;
	//								endOfTwo = headOfTwo.locationTowards(Direction.directions[l]);
	//								if (endOfTwo != null && !endOfTwo.locationUsed) {
	//									endOfTwo.locationUsed = true;
	////									locationsUsed.add(endOfTwo);
	////									printStacks();
	//									if (targetBoard[endOfTwo.row][endOfTwo.col] == 'x') numXsUsed++;
	//									if (numXsUsed == xsFound.size() && nextMove.locationUsed) {
	//										headOfThree.locationUsed = false;
	//										middleOfThree.locationUsed = false;
	//										endOfThree.locationUsed = false;
	//										headOfTwo.locationUsed = false;
	//										endOfTwo.locationUsed = false;
	//										return true;
	//									}
	//									endOfTwo.locationUsed = false;
	////									locationsUsed.remove(locationsUsed.size() - 1);
	//									if (targetBoard[endOfTwo.row][endOfTwo.col] == 'x') numXsUsed--;
	//								}
	//							}
	//							headOfTwo.locationUsed = false;
	////							locationsUsed.remove(locationsUsed.size() - 1);
	//							if (targetBoard[headOfTwo.row][headOfTwo.col] == 'x') numXsUsed--;
	//						}
	//						endOfThree.locationUsed = false;
	////						locationsUsed.remove(locationsUsed.size() - 1);
	//						if (targetBoard[endOfThree.row][endOfThree.col] == 'x') numXsUsed--;
	//					}
	//					middleOfThree.locationUsed = false;
	////					locationsUsed.remove(locationsUsed.size() - 1);
	//					if (targetBoard[middleOfThree.row][middleOfThree.col] == 'x') numXsUsed--;
	//				}
	//			}
	//			headOfThree.locationUsed = false;
	////			locationsUsed.remove(locationsUsed.size() - 1);
	//			if (targetBoard[headOfThree.row][headOfThree.col] == 'x') numXsUsed--;
	//		}
	//	}
	//	return false;
	//}

	//private void hardReset() {
	//	for (int j = 0; j < locationStack.size(); j++) {
	//		locationStack.get(j).orientation = null;
	//	}
	//	for (int j = 0; j < locationsUsed.size(); j++) {
	//		locationsUsed.get(j).locationUsed = false;
	//		locationsUsed.get(j).validated = false;
	//		locationsUsed.get(j).orientation = null;
	//		locationsUsed.get(j).extensions = 0;
	//	}
	//	searchPool.clear();
	//	currentLocation = null;
	//	currentSize = 0;
	//	indexOfSizeToLookFor = 0;
	//	numXsUsed = 0;
	//	locationStack.clear();
	//	locationsUsed.clear();
	//	triedNextMove = false;
	//	for (int j = 0; j < Ship.NUM_SHIPS; j++) {
	//		resetIndices[j] = 0;
	//		maxResetIndices[j] = 0;
	//	}
	//}

	//private void keepGoing() {
	//	for (Direction d : Direction.directions) {
	//		TrackerLocation neighbor = start.locationTowards(d);
	//		if (neighbor != null && neighbor.locationUsed && targetBoard[neighbor.row][neighbor.col] == 'x') {
	//			
	//		}
	//	}
	//}

	//private void searchXNeighbors() {
	//	for (Direction d : Direction.directions) {
	//		TrackerLocation neighbor = start.locationTowards(d);
	//		if (neighbor != null && neighbor.locationUsed && targetBoard[neighbor.row][neighbor.col] == '-') {
	//			
	//		}
	//	}
	//}

	//private void searchFreeNeighbors() {
	//	
	//}

	//	public boolean validated = false;
	//	public Direction orientation = null;
	//	public int extensions = 0;
	//	public int wantedShipSize = 0;

	//		private boolean canAdjustShips() {
	//			if (endOfFive.locationTowards(fiveDirection) == nextMove && targetBoard[headOfFive.row][headOfFive.col] == '-') {
	//				headOfFive.locationUsed = false;
	//				headOfFive = secondOfFive;
	//				secondOfFive = middleOfFive;
	//				middleOfFive = fourthOfFive;
	//				fourthOfFive = endOfFive;
	//				endOfFive = nextMove;
	//				return true;
	//			} else if (headOfFive.locationTowards(oppOfFive) == nextMove && targetBoard[endOfFive.row][endOfFive.col] == '-') {
	//				endOfFive.locationUsed = false;
	//				endOfFive = fourthOfFive;
	//				fourthOfFive = middleOfFive;
	//				middleOfFive = secondOfFive;
	//				secondOfFive = headOfFive;
	//				headOfFive = nextMove;
	//				return true;
	//			} else if (endOfFour.locationTowards(fourDirection) == nextMove && targetBoard[headOfFour.row][headOfFour.col] == '-') {
	//				headOfFour.locationUsed = false;
	//				headOfFour = secondOfFour;
	//				secondOfFour = thirdOfFour;
	//				thirdOfFour = endOfFour;
	//				endOfFour = nextMove;
	//				return true;
	//			} else if (headOfFour.locationTowards(oppOfFour) == nextMove && targetBoard[endOfFour.row][endOfFour.col] == '-') {
	//				endOfFour.locationUsed = false;
	//				endOfFour = thirdOfFour;
	//				thirdOfFour = secondOfFour;
	//				secondOfFour = headOfFour;
	//				headOfFour = nextMove;
	//				return true;
	//			} else if (endOfThree.locationTowards(threeDirection) == nextMove && targetBoard[headOfThree.row][headOfThree.col] == '-') {
	//				headOfThree.locationUsed = false;
	//				headOfThree = middleOfThree;
	//				middleOfThree = endOfThree;
	//				endOfThree = nextMove;
	//				return true;
	//			} else if (headOfThree.locationTowards(oppOfThree) == nextMove && targetBoard[endOfThree.row][endOfThree.col] == '-') {
	//				endOfThree.locationUsed = false;
	//				endOfThree = middleOfThree;
	//				middleOfThree = headOfThree;
	//				headOfThree = nextMove;
	//				return true;
	//			} else if (endOfThree2.locationTowards(three2Direction) == nextMove && targetBoard[headOfThree2.row][headOfThree2.col] == '-') {
	//				headOfThree2.locationUsed = false;
	//				headOfThree2 = middleOfThree2;
	//				middleOfThree2 = endOfThree2;
	//				endOfThree2 = nextMove;
	//				return true;
	//			} else if (headOfThree2.locationTowards(oppOfThree2) == nextMove && targetBoard[endOfThree2.row][endOfThree2.col] == '-') {
	//				endOfThree2.locationUsed = false;
	//				endOfThree2 = middleOfThree2;
	//				middleOfThree2 = headOfThree2;
	//				headOfThree2 = nextMove;
	//				return true;
	//			} else if (endOfTwo.locationTowards(twoDirection) == nextMove && targetBoard[headOfTwo.row][headOfTwo.col] == '-') {
	//				headOfTwo.locationUsed = false;
	//				headOfTwo = endOfTwo;
	//				endOfTwo = nextMove;
	//				return true;
	//			} else if (headOfTwo.locationTowards(oppOfTwo) == nextMove && targetBoard[endOfTwo.row][endOfTwo.col] == '-') {
	//				endOfTwo.locationUsed = false;
	//				endOfTwo = headOfTwo;
	//				headOfTwo = nextMove;
	//				return true;
	//			}
	//			return false;
	//		}

	//		fiveDirection = Direction.directions[b];
	//		oppOfFive = fiveDirection.opposite();
	//		fourDirection = Direction.directions[d];
	//		oppOfFour = fourDirection.opposite();
	//		threeDirection = Direction.directions[f];
	//		oppOfThree = threeDirection.opposite();
	//		three2Direction = Direction.directions[h];
	//		oppOfThree2 = three2Direction.opposite();
	//		twoDirection = Direction.directions[j];
	//		oppOfTwo = twoDirection.opposite();

	//	BoardUtilities.showBoard(targetBoard);
	//	System.out.println("Location to check: (" + (nextMove.row + 1) + ", " + (nextMove.col + 1) + ")");
	//	for (int i = 0; i < xsFound.size(); i++) {
	//		System.out.print("[(" + (xsFound.get(i).row + 1) + ", " + (xsFound.get(i).col + 1) + ") " + xsFound.get(i).surroundingImmersion + " "
	//				+ xsFound.get(i).potential + " " + xsFound.get(i).freedom + "] ");
	//	}
	//	System.out.println();
	//	System.out.print("[(" + (nextMove.row + 1) + ", " + (nextMove.col + 1) + ") " + nextMove.surroundingImmersion + " "
	//			+ nextMove.potential + " " + nextMove.freedom + "] ");
	//	System.out.println();
	//	for (int i = 0; i < movePool.size(); i++) {
	//		System.out.print("[(" + (movePool.get(i).row + 1) + ", " + (movePool.get(i).col + 1) + ") " + movePool.get(i).surroundingImmersion + " "
	//				+ movePool.get(i).potential + " " + movePool.get(i).freedom + "] ");
	//	}
	//	System.out.println();

	//	fiveEndExtension = null;
	//	fiveHeadExtension = null;
	//	fourEndExtension = null;
	//	fourHeadExtension = null;
	//	threeEndExtension = null;
	//	threeHeadExtension = null;
	//	three2EndExtension = null;
	//	three2HeadExtension = null;
	//	twoEndExtension = null;
	//	twoHeadExtension = null;

	//	public int compareTo(TrackerLocation otherLoc) {
	//		if (otherLoc.maxImmersionChainLength != maxImmersionChainLength) return otherLoc.maxImmersionChainLength - maxImmersionChainLength;
	//		if (surroundingImmersion != otherLoc.surroundingImmersion) return surroundingImmersion - otherLoc.surroundingImmersion;
	//		if (surroundingFreedom != otherLoc.surroundingFreedom) return surroundingFreedom - otherLoc.surroundingFreedom;
	//		if (otherLoc.longestReach != longestReach) return otherLoc.longestReach - longestReach;
	//		if (otherLoc.immersion != immersion) return otherLoc.immersion - immersion;
	//		return (int) ((otherLoc.potential - otherLoc.freedom) - (potential - freedom));
	//	}

	//	/***
	//	 * Determines whether a location is on a board of locations
	//	 * 
	//	 * @param location the location object in question
	//	 * @param board the board of inquiry
	//	 * @return true if the location is on the board else false
	//	 */
	//	public static boolean onTheBoard(Location location, Location[][] board) {
	//		return location.row >= 0 && location.col >= 0
	//				&& location.row < board.length && location.col < board.length;
	//	}

	//	/***
	//	 * Determines whether an integer pair, represented as an array, is on a character board
	//	 * 
	//	 * @param coord an array assumed to be of 2 elements, first of which is the row of inquiry, second of which is the column of inquiry
	//	 * @param board the board of inquiry
	//	 * @return true if the coordinate represented by the array is on the board else false
	//	 */
	//	public static boolean onTheBoard(int[] coord, char[][] board) {
	//		return coord[0] >= 0 && coord[1] >= 0 && coord[0] < board.length && coord[1] < board.length;
	//	}
	//	
	//	/***
	//	 * Determines whether an integer pair, represented as an array, is on a board of locations
	//	 * 
	//	 * @param coord an array assumed to be of 2 elements, first of which is the row of inquiry, second of which is the column of inquiry
	//	 * @param board the board of inquiry
	//	 * @return true if the coordinate represented by the array is on the board else false
	//	 */
	//	public static boolean onTheBoard(int[] coord, Location[][] board) {
	//		return coord[0] >= 0 && coord[1] >= 0 && coord[0] < board.length && coord[1] < board.length;
	//	}

	//	public static void updateTrackerProperties(TrackerLocation targetedLoc, TrackerLocation[][] locations, char[][] targetBoard) {
	//	for (int i = 0; i < Direction.NUM_DIRECTIONS; i++) {
	//		rowCopy = targetedLoc.row;
	//		colCopy = targetedLoc.col;
	//		int counter = 0;
	//		do {
	//			moveOn(Direction.directions[i]);
	//			counter++;
	//			if (BoardUtilities.onTheBoard(rowCopy, colCopy, locations)) {
	//				if (targetBoard[rowCopy][colCopy] == 'x') continue;
	//				if (BoardUtilities.onTheBoard(rowCopy, colCopy, locations) && targetBoard[rowCopy][colCopy] == '-') {
	//					locations[rowCopy][colCopy].updatePossibilities((i - 4) >= 0 ? i - 4 : i);
	//				}
	//			}
	//		} while (BoardUtilities.onTheBoard(rowCopy, colCopy, locations)
	//				&& targetBoard[rowCopy][colCopy] != 'o' && counter < Ship.shipSizes[0]);
	//	}
	//}

	//	public static void updateImmersionProperties(SmartLocation targetedLoc,
	//	SmartLocation[][] valueBoard, char[][] targetBoard) {
	//updateSurroundingImmersion(targetedLoc, valueBoard);
	//for (int i = 0; i < Direction.directions.length; i++) {
	//	rowCopy = targetedLoc.row;
	//	colCopy = targetedLoc.col;
	//	int oppositeDirectionIndex = (i + 4) < Direction.NUM_DIRECTIONS ? i + 4 : i - 4;
	//	int counter = targetedLoc.immersionValuesByDirection[oppositeDirectionIndex] + 1; // count locations themselves;
	//	do {
	//		moveOn(Direction.directions[i]);
	//		counter++;
	//		if (BoardUtilities.onTheBoard(rowCopy, colCopy, valueBoard)
	//				&& targetBoard[rowCopy][colCopy] != 'o') {
	//			if (valueBoard[rowCopy][colCopy].maxImmersionChainLength
	//					< counter + valueBoard[rowCopy][colCopy].immersionValuesByDirection[i]) {
	//				valueBoard[rowCopy][colCopy].maxImmersionChainLength =
	//						counter + valueBoard[rowCopy][colCopy].immersionValuesByDirection[i];
	//			}
	//			valueBoard[rowCopy][colCopy].immersion += ((counter - 1) - valueBoard[rowCopy][colCopy].immersionValuesByDirection[oppositeDirectionIndex]);
	//			valueBoard[rowCopy][colCopy].immersionValuesByDirection[oppositeDirectionIndex] = counter - 1;
	//		}
	//	} while (BoardUtilities.onTheBoard(rowCopy, colCopy, valueBoard) && targetBoard[rowCopy][colCopy] == 'x');
	//}
	//}
	//
	//private static void updateSurroundingImmersion(SmartLocation targetedLoc, SmartLocation[][] valueBoard) {
	//for (int i = 0; i < Direction.directions.length; i++) {
	//	rowCopy = targetedLoc.row;
	//	colCopy = targetedLoc.col;
	//	moveOn(Direction.directions[i]);
	//	if (BoardUtilities.onTheBoard(rowCopy, colCopy, valueBoard)) {
	//		valueBoard[rowCopy][colCopy].surroundingImmersion++;
	//	}
	//}
	//}
}