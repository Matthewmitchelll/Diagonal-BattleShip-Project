package project2;

import java.util.ArrayList;

class Commander extends Prioritizer<TrackerLocation> {
	
	/*
	 * The Commander Targeter is the strongest BattleShip player, averaging only 55.8 guesses to win
	 * a game. It considers locations based on how many ways they can accommodate each of the ships placed
	 * and how many locations are in the longest chain of occupied locations that it is a part of, assuming
	 * it holds a ship. It also employs the same focusing algorithm as the WeightedPrioritizer, in that
	 * once it finds an occupied location that is not connected to another occupied location, it will target
	 * its bordering locations in order of decreasing perceived utility (as calculated using its 2 weights)
	 * until it finds a second occupied location, then it extends that chain until both sides are either
	 * tabbed as non-occupied ships or off the board. What makes this targeter better than the previous
	 * versions, however, is that it can fully determine when a location is not feasible (not just when
	 * multiple 2-length ships would be made or when a location is singled out, as the WeightedPrioritizer
	 * does). It accomplishes this by keeping track of a possible configuration of ships, and reconfiguring
	 * its beliefs as needed through a DFS-related algorithm based on previously discovered ship locations
	 * and non-ship locations. The tradeoff for this feature is a performance hit- 1000 games takes roughly
	 * 20 seconds to simulate with a TestSimulator instance. For comparison, the WeightedPrioritizer could
	 * simulate roughly 310,000 games in as much time. Fortunately, this hit would not be perceived when
	 * the player plays it.
	 */
	
	private TrackerLocation nextMove;
	
	private ArrayList<TrackerLocation> movePool = new ArrayList<TrackerLocation>();
	
	private TrackerLocation[][] locations;
	
	private ArrayList<TrackerLocation> surroundingLocations = new ArrayList<TrackerLocation>();
	
	// the following 2 variables are weights that have performed the best in extensive simulations
	private int tPWeight = 5; // multiplier for the total amount of ways a location can accommodate each of the ships placed
	private int maxICWeight = 12; // multiplier for the longest chain of xs that a location can be a part of, assuming it holds a ship
	
	private TrackerLocation lastMove; // the last targeted square
	private ArrayList<TrackerLocation> xsFound = new ArrayList<TrackerLocation>();
	
	private ArrayList<TrackerLocation> searchPool = new ArrayList<TrackerLocation>(); // includes the movePool, the chosen move, and known ship spots
	
	private int[] maxResetIndices = new int[5]; // keeps track of the number of ways a location can accommodate a ship, for each ship
	
	private int numXsUsed; // the number of known locations used while finding a possible ship configuration
	
	// The following instance variables keep track of a possible configuration of opponent ships
	
	private TrackerLocation headOfFive;
	private TrackerLocation secondOfFive;
	private TrackerLocation middleOfFive;
	private TrackerLocation fourthOfFive;
	private TrackerLocation endOfFive;
	
	private TrackerLocation headOfFour;
	private TrackerLocation secondOfFour;
	private TrackerLocation thirdOfFour;
	private TrackerLocation endOfFour;
	
	private TrackerLocation headOfThree;
	private TrackerLocation middleOfThree;
	private TrackerLocation endOfThree;
	
	private TrackerLocation headOfThree2;
	private TrackerLocation middleOfThree2;
	private TrackerLocation endOfThree2;
	
	private TrackerLocation headOfTwo;
	private TrackerLocation endOfTwo;
	
	/***
	 * Constructs a Commander
	 * 
	 * @param targetBoard the targetBoard of the overlying ComputerState object
	 */
	Commander(char[][] targetBoard) {
		super(targetBoard);
		locations = new TrackerLocation[targetBoard.length][targetBoard.length]; // to store information about all locations
		setLocationsAndMovePool();
		nextMove = locations[0][0]; // arbitrary initialization to avoid null pointer exceptions
	}
	
	/***
	 * Fills the movePool with all possible moves at the beginning of a game, as TrackerLocation objects
	 */
	private void setLocationsAndMovePool() {
		for (int i = 0; i < locations.length; i++) {
			for (int j = 0; j < locations.length; j++) {
				locations[i][j] = new TrackerLocation(i, j, locations);
				movePool.add(locations[i][j]);
			}
		}
	}
	
	/***
	 * Selects the overlying ComputerState object's next move from the current pool of untargeted
	 * locations, considering the number of ways a location can accommodate every ship, maximum
	 * occupied chain length, and feasibility
	 * 
	 * The algorithm begins by selecting moves that can accommodate the most amount of ships until
	 * it finds an occupied location. Then, it sets that location as an 'originalX' value, and
	 * looks at locations bordering it until a second location is found, called the 'xContinuation'.
	 * Then, it sets the originalFocus and continuationFocus directions (originalFocus is the direction
	 * from xContinuation to originalX, and continuationFocus is the opposite direction). The algorithm
	 * then expands on these two locations, forming a chain until both ends are either not on the board
	 * or are discovered to be unoccupied (or deemed infeasible). The algorithm then resets and
	 * cycles back, looking for new originalX values (a location will only be deemed an originalX if
	 * it is occupied and has no locations bordering it that are occupied. This algorithm takes
	 * advantage of the knowledge that all ships are of at least size 2)
	 * 
	 * @returns a Location object representing the overlying computer's next target
	 */
	public Location getNextMove() {
		if (originalX == null && xContinuation == null) continuationFocus = null; // to complete the cycle (continuationFocus is not null until now to avoid setting up a wrong chain)
		if (targetBoard[nextMove.row][nextMove.col] != '-') {
			lastMove = nextMove;
			TargetUtilities.updateLocationProperties(nextMove, locations, targetBoard);
			if (targetBoard[nextMove.row][nextMove.col] == 'x') {
				xsFound.add(nextMove);
				if (noXNeighbors(nextMove, locations)) { // no neighboring occupied locations implies a new originalX is found
					originalX = nextMove;
					updateSurroundingLocations(nextMove, locations, surroundingLocations); // sets up a collection to search surrounding locations
				} else if (originalX != null && continuationFocus == null) { // found an xContinuation, so a chain is set up
					setUpChain(nextMove);
					surroundingLocations.clear(); // none of the remaining surrounding locations are needed, as we just found a chain
				}
			} else {
				if (lastDirectionWent == originalFocus && originalFocus != null) { // we went in the originalX direction and found an unoccupied spot, ending a chain on one end
					originalX = null;
					originalFocus = null;
				} else if (lastDirectionWent == continuationFocus && continuationFocus != null) { // ended a chain on the other end
					xContinuation = null;
				}
			}
		}
		makeBestMove(); // based on what we know, selects the highest scoring move, from either the movePool, surroundingLocations, or between either an originalX and xContinuation
		while (nextMoveNotValid()) {
			targetBoard[nextMove.row][nextMove.col] = 'o'; // mark as unoccupied without targeting
			TargetUtilities.updateLocationProperties(nextMove, locations, targetBoard);
			makeBestMove();
		}
		return nextMove;
	}
	
	/***
	 * Sets the next target
	 */
	private void makeBestMove() {
		if (surroundingLocations.size() > 0) {
			nextMove = getHighestPriorityMove(movePool, surroundingLocations);
		} else if (originalX != null && xContinuation != null){
			nextMove = getHighestPriorityMove(movePool, originalX, xContinuation);
		} else if (originalX != null) {
			updateMovePool(movePool, originalX.row, originalX.col); // removes the chosen move from the movePool
			nextMove = originalX;
			updateChain(false); // updates indicating the originalX was chosen (setting the location that is in the originalFocus direction from originalX as the new originalX)
		} else if (xContinuation != null) {
			updateMovePool(movePool, xContinuation.row, xContinuation.col);
			nextMove = xContinuation;
			updateChain(true); // updates indicating the xContinuation was chosen
		} else {
			nextMove = getHighestPriorityMove(movePool);
		}
	}
	
	/***
	 * Returns a highest scoring location from a list of locations surrounding a known ship
	 * location (breaks ties by selecting the first location with the score in question)
	 * 
	 * @param movePool pool of untargeted moves to update
	 * @param surroundingLocations list of untargeted locations surrounding the most recently discovered ship location
	 * @return a highest scoring untargeted location according to the priorityScore function
	 */
	protected TrackerLocation getHighestPriorityMove(ArrayList<TrackerLocation> movePool,
			ArrayList<TrackerLocation> surroundingLocations) {
		int indexOfMove = 0;
		int highestScore = priorityScore(surroundingLocations.get(0));
		for (int i = 1; i < surroundingLocations.size(); i++) {
			int score = priorityScore(surroundingLocations.get(i));
			if (score > highestScore) {
				indexOfMove = i;
				highestScore = score;
			}
		}
		updateMovePool(movePool, surroundingLocations.get(indexOfMove).row, surroundingLocations.get(indexOfMove).col);
		return surroundingLocations.remove(indexOfMove);
	}
	
	/***
	 * The scoring function the Commander uses to evaluate untargeted locations
	 * 
	 * @param location the location to evaluate
	 * @return the location's score
	 */
	public int priorityScore(TrackerLocation location) {
		return location.totalPossibilities*tPWeight
				+ location.maxImmersionChainLength*maxICWeight;
	}
	
	/***
	 * Returns the highest scoring move between the originalX location and the xContinuation,
	 * selecting the originalX location to resolve ties
	 * 
	 * @param movePool pool of untargeted moves to update
	 * @param original the location oriented with the original newly discovered, unconnected ship location
	 * @param continuation the location oriented with the ship location discovered after the newly discovered, unconnected location
	 * @return either the original or the continuation, depending on who has the higher score
	 */
	protected TrackerLocation getHighestPriorityMove(ArrayList<TrackerLocation> movePool,
			TrackerLocation original, TrackerLocation continuation) {
		if (priorityScore(original) >= priorityScore(continuation)) {
			updateMovePool(movePool, original.row, original.col);
			updateChain(false);
			return original;
		}
		updateMovePool(movePool, continuation.row, continuation.col);
		updateChain(true);
		return continuation;
	}
	
	/***
	 * Returns a highest scoring location from the movePool
	 * (breaks ties by selecting the first location with the score in question)
	 * 
	 * @param movePool pool of untargeted moves to update
	 * @return a highest scoring untargeted location according to the priorityScore function
	 */
	protected TrackerLocation getHighestPriorityMove(ArrayList<TrackerLocation> movePool) {
		if (movePool.size() == 0) BoardUtilities.showBoard(targetBoard);
		int indexOfMove = 0;
		int highestScore = priorityScore(movePool.get(0));
		for (int i = 1; i < movePool.size(); i++) {
			int score = priorityScore(movePool.get(i));
			if (score > highestScore) {
				indexOfMove = i;
				highestScore = score;
			}
		}
		return movePool.remove(indexOfMove);
	}
	
	/***
	 * Determines whether the chosen location for targeting cannot possibly hold a ship, given
	 * what we know about the locations so far
	 * 
	 * @return true if the chosen location cannot possibly hold a ship, else false
	 */
	private boolean nextMoveNotValid() {
		if (nextMove.locationUsed && targetBoard[lastMove.row][lastMove.col] == 'x') return false; // makes use of computed information from the last time a configuration was looked for to get a performance edge
		if (xsFound.size() == Ship.TOTAL_SHIP_OCCUPANCY - 1
				&& (nextMove.surroundingImmersion == 0 || super.notPossible(nextMove))) return true; // removes clogged locations at the end of games that have no chance at being connected from consideration
		if (xsFound.size() == 0) return false;
		falsifyLocations(); // need to recompute a possible configuration of ships, so set locationUsed to false for our previous configuration of locations
		return cannotFindValidConfiguration();
	}
	
	/***
	 * Sets the locationsUsed boolean value for each of our locations to false in order to recompute
	 * a possible ship configuration correctly
	 */
	private void falsifyLocations() {
		if (headOfFive != null) {
			headOfFive.locationUsed = false;
			secondOfFive.locationUsed = false;
			middleOfFive.locationUsed = false;
			fourthOfFive.locationUsed = false;
			endOfFive.locationUsed = false;
			
			headOfFour.locationUsed = false;
			secondOfFour.locationUsed = false;
			thirdOfFour.locationUsed = false;
			endOfFour.locationUsed = false;
			
			headOfThree.locationUsed = false;
			middleOfThree.locationUsed = false;
			endOfThree.locationUsed = false;
			
			headOfThree2.locationUsed = false;
			middleOfThree2.locationUsed = false;
			endOfThree2.locationUsed = false;
			
			headOfTwo.locationUsed = false;
			endOfTwo.locationUsed = false;
		}
	}
	
	/***
	 * Sets up the searchPool collection to look for potential ship configurations and
	 * proceeds to search for configurations
	 * 
	 * @return true if a ship configuration can be found, else false
	 */
	private boolean cannotFindValidConfiguration() {
		for (int i = 0; i < xsFound.size(); i++) {
			searchPool.add(xsFound.get(i));
		}
		searchPool.add(nextMove);
		for (int i = 0; i < movePool.size(); i++) {
			if (movePool.get(i).potential - movePool.get(i).freedom > 0 && movePool.get(i).potential > 0) searchPool.add(movePool.get(i)); // no locations with 0 potential are scanned
		}
		for (int i = 0; i < movePool.size(); i++) {
			if (movePool.get(i).potential - movePool.get(i).freedom == 0 && movePool.get(i).potential > 0) searchPool.add(movePool.get(i));
		}
		for (int i = 0; i < Ship.NUM_SHIPS; i++) {
			maxResetIndices[i] = searchPool.size() - 1; // for each ship size, determine the last possible location that may serve as its head
			for (int j = searchPool.size() - 1; j >= 0; j--) {
				if (searchPool.get(j).longestReach < Ship.shipSizes[i] - 1) {
					maxResetIndices[i]--;
				} else break;
			}
		}
		return cannotfindAConfig();
	}
	
	/***
	 * Determines whether a possible ship configuration cannot be found. Attempts to find
	 * ships in order of largest to smallest
	 * 
	 * @return true if a ship configuration cannot be found, else false
	 */
	private boolean cannotfindAConfig() {
		for (int a = 0; a <= maxResetIndices[0]; a++) { // looks for a 5-length ship
			if (searchPool.get(a).longestReach < 4) continue; // ignores locations that can't be the start of a 5-length ship
			headOfFive = searchPool.get(a);
			headOfFive.locationUsed = true;
			if (targetBoard[headOfFive.row][headOfFive.col] == 'x') numXsUsed++;
			for (int b = 0; b < Direction.NUM_DIRECTIONS/2; b++) { // only need to check half the directions (using the fact that a ship has 2 ends)
				if (headOfFive.potentialValuesByDirection[b] < 4) continue; // ignores directions where a 5-length ship can't extend out to
				secondOfFive = headOfFive.locationTowards(Direction.directions[b]);
				if (targetBoard[secondOfFive.row][secondOfFive.col] == 'x') numXsUsed++; // increments the number of occupied locations used whenever a discovered ship location is used
				middleOfFive = secondOfFive.locationTowards(Direction.directions[b]);
				if (targetBoard[middleOfFive.row][middleOfFive.col] == 'x') numXsUsed++;
				fourthOfFive = middleOfFive.locationTowards(Direction.directions[b]);
				if (targetBoard[fourthOfFive.row][fourthOfFive.col] == 'x') numXsUsed++;
				endOfFive = fourthOfFive.locationTowards(Direction.directions[b]);
				if (targetBoard[endOfFive.row][endOfFive.col] == 'x') numXsUsed++;
				if (xsFound.size() + (5 - numXsUsed) > Ship.TOTAL_SHIP_OCCUPANCY) { // adds the total number of xs known to the number of untargeted locations used and checks to make sure the sum is always at most the total numbe of ship spaces
					if (targetBoard[secondOfFive.row][secondOfFive.col] == 'x') numXsUsed--; // decrements whenever an occupied location is removed from configuration
					if (targetBoard[middleOfFive.row][middleOfFive.col] == 'x') numXsUsed--;
					if (targetBoard[fourthOfFive.row][fourthOfFive.col] == 'x') numXsUsed--;
					if (targetBoard[endOfFive.row][endOfFive.col] == 'x') numXsUsed--;
					continue;
				}
				secondOfFive.locationUsed = true;
				middleOfFive.locationUsed = true;
				fourthOfFive.locationUsed = true;
				endOfFive.locationUsed = true;
				for (int c = 0; c <= maxResetIndices[1]; c++) { // repeats the above process for a 4-length ship
					if (searchPool.get(c).locationUsed || searchPool.get(c).longestReach < 3) continue;
					headOfFour = searchPool.get(c);
					headOfFour.locationUsed = true;
					if (targetBoard[headOfFour.row][headOfFour.col] == 'x') numXsUsed++;
					for (int d = 0; d < Direction.NUM_DIRECTIONS/2; d++) {
						if (headOfFour.potentialValuesByDirection[d] < 3) continue;
						secondOfFour = headOfFour.locationTowards(Direction.directions[d]);
						if (secondOfFour.locationUsed) continue;
						thirdOfFour = secondOfFour.locationTowards(Direction.directions[d]);
						if (thirdOfFour.locationUsed) continue;
						endOfFour = thirdOfFour.locationTowards(Direction.directions[d]);
						if (endOfFour.locationUsed) continue;
						if (targetBoard[secondOfFour.row][secondOfFour.col] == 'x') numXsUsed++;
						if (targetBoard[thirdOfFour.row][thirdOfFour.col] == 'x') numXsUsed++;
						if (targetBoard[endOfFour.row][endOfFour.col] == 'x') numXsUsed++;
						if (xsFound.size() + (9 - numXsUsed) > Ship.TOTAL_SHIP_OCCUPANCY) {
							if (targetBoard[secondOfFour.row][secondOfFour.col] == 'x') numXsUsed--;
							if (targetBoard[thirdOfFour.row][thirdOfFour.col] == 'x') numXsUsed--;
							if (targetBoard[endOfFour.row][endOfFour.col] == 'x') numXsUsed--;
							continue;
						}
						secondOfFour.locationUsed = true;
						thirdOfFour.locationUsed = true;
						endOfFour.locationUsed = true;
						for (int e = 0; e <= maxResetIndices[2]; e++) { // repeats the above process for a 3-length ship
							if (searchPool.get(e).locationUsed || searchPool.get(e).longestReach < 2) continue;
							headOfThree = searchPool.get(e);
							headOfThree.locationUsed = true;
							if (targetBoard[headOfThree.row][headOfThree.col] == 'x') numXsUsed++;
							for (int f = 0; f < Direction.NUM_DIRECTIONS/2; f++) {
								if (headOfThree.potentialValuesByDirection[f] < 2) continue;
								middleOfThree = headOfThree.locationTowards(Direction.directions[f]);
								if (middleOfThree.locationUsed) continue;
								endOfThree = middleOfThree.locationTowards(Direction.directions[f]);
								if (endOfThree.locationUsed) continue;
								if (targetBoard[middleOfThree.row][middleOfThree.col] == 'x') numXsUsed++;
								if (targetBoard[endOfThree.row][endOfThree.col] == 'x') numXsUsed++;
								if (xsFound.size() + (12 - numXsUsed) > Ship.TOTAL_SHIP_OCCUPANCY) {
									if (targetBoard[middleOfThree.row][middleOfThree.col] == 'x') numXsUsed--;
									if (targetBoard[endOfThree.row][endOfThree.col] == 'x') numXsUsed--;
									continue;
								}
								middleOfThree.locationUsed = true;
								endOfThree.locationUsed = true;
								for (int g = 0; g <= maxResetIndices[3]; g++) { // repeats the above process for another 3-length ship
									if (searchPool.get(g).locationUsed || searchPool.get(g).longestReach < 2) continue;
									headOfThree2 = searchPool.get(g);
									headOfThree2.locationUsed = true;
									if (targetBoard[headOfThree2.row][headOfThree2.col] == 'x') numXsUsed++;
									for (int h = 0; h < Direction.NUM_DIRECTIONS/2; h++) {
										if (headOfThree2.potentialValuesByDirection[h] < 2) continue;
										middleOfThree2 = headOfThree2.locationTowards(Direction.directions[h]);
										if (middleOfThree2.locationUsed) continue;
										endOfThree2 = middleOfThree2.locationTowards(Direction.directions[h]);
										if (endOfThree2.locationUsed) continue;
										if (targetBoard[middleOfThree2.row][middleOfThree2.col] == 'x') numXsUsed++;
										if (targetBoard[endOfThree2.row][endOfThree2.col] == 'x') numXsUsed++;
										if (xsFound.size() + (15 - numXsUsed) > Ship.TOTAL_SHIP_OCCUPANCY) {
											if (targetBoard[middleOfThree2.row][middleOfThree2.col] == 'x') numXsUsed--;
											if (targetBoard[endOfThree2.row][endOfThree2.col] == 'x') numXsUsed--;
											continue;
										}
										middleOfThree2.locationUsed = true;
										endOfThree2.locationUsed = true;
										for (int i = 0; i <= maxResetIndices[4]; i++) { // repeats the above process for a 2-length ship
											if (searchPool.get(i).locationUsed) continue;
											headOfTwo = searchPool.get(i);
											headOfTwo.locationUsed = true;
											if (targetBoard[headOfTwo.row][headOfTwo.col] == 'x') numXsUsed++;
											for (int j = 0; j < Direction.NUM_DIRECTIONS/2; j++) {
												if (headOfTwo.potentialValuesByDirection[j] < 1) continue;
												endOfTwo = headOfTwo.locationTowards(Direction.directions[j]);
												if (endOfTwo.locationUsed) continue;
												if (targetBoard[endOfTwo.row][endOfTwo.col] == 'x') numXsUsed++;
												if (xsFound.size() + (17 - numXsUsed) > Ship.TOTAL_SHIP_OCCUPANCY) {
													if (targetBoard[endOfTwo.row][endOfTwo.col] == 'x') numXsUsed--;
													continue;
												}
												endOfTwo.locationUsed = true;
												if (numXsUsed == xsFound.size() && nextMove.locationUsed) { // if true, this is a possible configuration, so clear the pool to look for the next one
													searchPool.clear();
													numXsUsed = 0;
													return false;
												}
												endOfTwo.locationUsed = false; // if here, need to consider other locations, begining with other 2-length ship configurations, so set the end to false
												if (targetBoard[endOfTwo.row][endOfTwo.col] == 'x') numXsUsed--;
											}
											headOfTwo.locationUsed = false;
											if (targetBoard[headOfTwo.row][headOfTwo.col] == 'x') numXsUsed--;
										}
										middleOfThree2.locationUsed = false;
										endOfThree2.locationUsed = false;
										if (targetBoard[middleOfThree2.row][middleOfThree2.col] == 'x') numXsUsed--;
										if (targetBoard[endOfThree2.row][endOfThree2.col] == 'x') numXsUsed--;
									}
									headOfThree2.locationUsed = false;
									if (targetBoard[headOfThree2.row][headOfThree2.col] == 'x') numXsUsed--;
								}
								middleOfThree.locationUsed = false;
								endOfThree.locationUsed = false;
								if (targetBoard[middleOfThree.row][middleOfThree.col] == 'x') numXsUsed--;
								if (targetBoard[endOfThree.row][endOfThree.col] == 'x') numXsUsed--;
							}
							headOfThree.locationUsed = false;
							if (targetBoard[headOfThree.row][headOfThree.col] == 'x') numXsUsed--;
						}
						secondOfFour.locationUsed = false;
						thirdOfFour.locationUsed = false;
						endOfFour.locationUsed = false;
						if (targetBoard[secondOfFour.row][secondOfFour.col] == 'x') numXsUsed--;
						if (targetBoard[thirdOfFour.row][thirdOfFour.col] == 'x') numXsUsed--;
						if (targetBoard[endOfFour.row][endOfFour.col] == 'x') numXsUsed--;
					}
					headOfFour.locationUsed = false;
					if (targetBoard[headOfFour.row][headOfFour.col] == 'x') numXsUsed--;
				}
				secondOfFive.locationUsed = false;
				middleOfFive.locationUsed = false;
				fourthOfFive.locationUsed = false;
				endOfFive.locationUsed = false;
				if (targetBoard[secondOfFive.row][secondOfFive.col] == 'x') numXsUsed--;
				if (targetBoard[middleOfFive.row][middleOfFive.col] == 'x') numXsUsed--;
				if (targetBoard[fourthOfFive.row][fourthOfFive.col] == 'x') numXsUsed--;
				if (targetBoard[endOfFive.row][endOfFive.col] == 'x') numXsUsed--;
			}
			headOfFive.locationUsed = false;
			if (targetBoard[headOfFive.row][headOfFive.col] == 'x') numXsUsed--;
		}
		searchPool.clear(); // no configuration is found at this point, so clear the pool to look for another one
		return true;
	}
	
	/***
	 * Resets the weights that a Commander will use
	 */
	public void setNewWeights(int tPWeight, int maxICWeight) {
		this.tPWeight = tPWeight;
		this.maxICWeight = maxICWeight;
	}
	
	/***
	 * Clears the collections used by the Commander and resets all locations (see the TrackerLocation class)
	 */
	public void reset() {
		xsFound.clear();
		super.reset(movePool, surroundingLocations, locations);
	}
}