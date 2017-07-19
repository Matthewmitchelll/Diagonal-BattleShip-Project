package project2;

import java.util.ArrayList;

class WeightedPrioritizer extends Prioritizer<SmartLocation> {
	
	/*
	 * A WeightedPrioritizer will consider moves based on 5 location properties:
	 * maxImmersionChainLength, maxPotentialChainLength, immersion, potential, and variance.
	 * The properties are described below. The prioritizer applies weights to these parameters
	 * and sums up products to determine a measure of a certain location's utility. Highest
	 * scoring locations are targeted first.
	 * 
	 * 1) maxImmersionChainLength: the length of the longest chain of occupied locations on the board
	 *                             that this location would be a part of if it was an occupied location.
	 * 
	 * 2) maxPotentialChainLength: the length of the longest chain of occupied or untargeted locations
	 *                             that this location would be a part of if it was either occupied
	 *                             or untargeted.
	 * 
	 * 3) immersion: the total number of occupied locations this location is connected with,
	 *               either through direct bordering or through a chain of occupied locations.
	 *               Does not include the location itself.
	 * 
	 * 4) potential: the total number of untargeted locations this location is connected with,
	 *               either through direct bordering or through a chain of untargeted locations.
	 *               Does not include the location itself.
	 * 
	 * 5) variance:  the measure of how little the potential of a location in a particular
	 *               direction deviates from the mean as the direction being considered changes.
	 *               This trait is used to give stronger consideration to centralized locations.
	 * 
	 * For all measures except variance, higher numbers are sought after first. For variance,
	 * lower values are deemed better, as it leads to the behavior of prioritizing central
	 * locations, which increases the possibility of ruling out locations for later guesses.
	 * 
	 * This class extends Prioritizer by adding the locations field to store info about locations
	 * and fields for weights used in the scoring function.
	 */

	protected SmartLocation nextMove;

	protected ArrayList<SmartLocation> movePool = new ArrayList<SmartLocation>();

	protected SmartLocation[][] locations;

	protected ArrayList<SmartLocation> surroundingLocations = new ArrayList<SmartLocation>(); // store locations surrounding a newly discovered, unconnected, occupied location
	
	// the following 5 variables are weights that have performed the best in extensive simulations
	private int maxICWeight = 15;
	private int maxPCWeight = 11;
	private int iWeight = 1;
	private int pWeight = 4;
	private int vWeight = 2;
	
	/***
	 * Constructs a WeightedPrioritizer
	 * 
	 * @param targetBoard the targetBoard of the overlying ComputerState object
	 */
	WeightedPrioritizer(char[][] targetBoard) {
		super(targetBoard);
		locations = new SmartLocation[targetBoard.length][targetBoard.length];
		setLocationsAndMovePool(movePool, locations);
		nextMove = locations[0][0];
	}
	
	/***
	 * Fills the movePool with all possible moves at the beginning of a game, as SmartLocation objects
	 */
	protected void setLocationsAndMovePool(ArrayList<SmartLocation> movePool, SmartLocation[][] locations) {
		for (int i = 0; i < locations.length; i++) {
			for (int j = 0; j < locations.length; j++) {
				locations[i][j] = new SmartLocation(i, j, locations);
				movePool.add(locations[i][j]);
			}
		}
	}
	
	/***
	 * Selects the overlying ComputerState object's next move from the current pool of untargeted
	 * locations.
	 * 
	 * The algorithm begins by selecting moves that score the highest with regard to the 5 properties
	 * described in the class description. Then, it sets that location as an 'originalX' value, and
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
	protected Location getNextMove() {
		if (originalX == null && xContinuation == null) continuationFocus = null; // to complete the cycle (continuationFocus is not null until now to avoid setting up a wrong chain)
		if (targetBoard[nextMove.row][nextMove.col] != '-') {
			TargetUtilities.updateLocationProperties(nextMove, locations, targetBoard);
			if (targetBoard[nextMove.row][nextMove.col] == 'x') {
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
		while (notPossible(nextMove)) {
			targetBoard[nextMove.row][nextMove.col] = 'o'; // updates targetBoard without making the move
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
	protected SmartLocation getHighestPriorityMove(ArrayList<SmartLocation> movePool,
			ArrayList<SmartLocation> surroundingLocations) {
		int indexOfMove = 0;
		double highestScore = priorityScore(surroundingLocations.get(0));
		for (int i = 1; i < surroundingLocations.size(); i++) {
			double score = priorityScore(surroundingLocations.get(i));
			if (score > highestScore) {
				indexOfMove = i;
				highestScore = score;
			}
		}
		updateMovePool(movePool, surroundingLocations.get(indexOfMove).row, surroundingLocations.get(indexOfMove).col);
		return surroundingLocations.remove(indexOfMove);
	}
	
	/***
	 * The scoring function a WeightedPrioritizer uses to evaluate untargeted locations
	 * 
	 * @param location the location to evaluate
	 * @return the location's score
	 */
	public double priorityScore(SmartLocation location) {
		return location.maxImmersionChainLength*maxICWeight + location.maxPotentialChainLength*maxPCWeight
				+ location.immersion*iWeight + location.potential*pWeight - location.variance*vWeight;
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
	protected SmartLocation getHighestPriorityMove(ArrayList<SmartLocation> movePool,
			SmartLocation original, SmartLocation continuation) {
		if (original.row == continuation.row && original.col == continuation.col) System.out.println("oops");
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
	protected SmartLocation getHighestPriorityMove(ArrayList<SmartLocation> movePool) {
		if (movePool.size() == 0) BoardUtilities.showBoard(targetBoard);
		int indexOfMove = 0;
		double highestScore = priorityScore(movePool.get(0));
		for (int i = 1; i < movePool.size(); i++) {
			double score = priorityScore(movePool.get(i));
			if (score > highestScore) {
				indexOfMove = i;
				highestScore = score;
			}
		}
		return movePool.remove(indexOfMove);
	}
	
	/***
	 * Resets the weights that a WeightedPrioritizer will use
	 */
	public void setNewWeights(int maxICWeight, int maxPCWeight, int iWeight, int pWeight, int vWeight) {
		this.maxICWeight = maxICWeight;
		this.maxPCWeight = maxPCWeight;
		this.iWeight = iWeight;
		this.pWeight = pWeight;
		this.vWeight = vWeight;
	}
	
	/***
	 * Clears the collections used by the WeightedPrioritizer and resets all locations (see the SmartLocation class)
	 */
	public void reset() {
		super.reset(movePool, surroundingLocations, locations);
	}
}