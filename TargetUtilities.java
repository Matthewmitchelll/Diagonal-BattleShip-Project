package project2;

class TargetUtilities {
	
	/*
	 * This class contains methods for updating the parameters of SmartLocations and TrackerLocations
	 * as moves are made and information about the board is discovered.
	 */
	
	private static int rowCopy, colCopy; // used to expand outward in various directions from a newly targeted location in order to update the information stored in relevant locations
	
	/***
	 * Updates all locations affected by the revelation of information of a location, targetedLoc.
	 * SmartLocations need only update parameters related to immersion, freedom, and potential
	 * 
	 * @param targetedLoc the location that was just targeted
	 * @param locations the board of location objects used to store information about a board
	 * @param targetBoard the board of guesses of the overlying player
	 */
	public static void updateLocationProperties(SmartLocation targetedLoc,
			SmartLocation[][] locations, char[][] targetBoard) {
		for (int i = 0; i < Direction.directions.length; i++) {
			rowCopy = targetedLoc.row; // rowCopy and colCopy are used to travel throughout the board
			colCopy = targetedLoc.col;
			boolean mustUpdateImmersion = true;
			int oppositeDirectionIndex = (i + 4) < Direction.NUM_DIRECTIONS ? i + 4 : i - 4;
			int distanceCounter = -1; // used to update surrounding properties
			int immersionCounter = targetedLoc.immersionValuesByDirection[oppositeDirectionIndex] + 1; // count locations themselves;
			do {
				moveOn(Direction.directions[i]);
				distanceCounter++;
				immersionCounter++;
				if (BoardUtilities.onTheBoard(rowCopy, colCopy, locations) && targetBoard[rowCopy][colCopy] != 'o') {
					if (targetBoard[rowCopy][colCopy] == '-' && distanceCounter == 0) locations[rowCopy][colCopy].surroundingFreedom--;
					if (targetBoard[targetedLoc.row][targetedLoc.col] == 'o') { // need to update the potential traits of all locations we are connected to, up until we reach the first unoccupied location, or board edge
						locations[rowCopy][colCopy].potential -= (locations[rowCopy][colCopy].potentialValuesByDirection[oppositeDirectionIndex] - distanceCounter);
						locations[rowCopy][colCopy].potentialValuesByDirection[oppositeDirectionIndex] = distanceCounter;
						locations[rowCopy][colCopy].updateVariance();
						if (locations[rowCopy][colCopy].maxPoDirection == Direction.directions[i]
								|| locations[rowCopy][colCopy].maxPoDirection == Direction.directions[oppositeDirectionIndex]) {
							locations[rowCopy][colCopy].updateMaxPotentialChainLength();
						}
					} else if (mustUpdateImmersion) { // need to update the immersion traits of all locations we are connected to, up until we reach the first untareted location, or board edge
						if (distanceCounter == 0) locations[rowCopy][colCopy].surroundingImmersion++;
						if (locations[rowCopy][colCopy].maxImmersionChainLength
								< immersionCounter + locations[rowCopy][colCopy].immersionValuesByDirection[i]) {
							locations[rowCopy][colCopy].maxImmersionChainLength =
									immersionCounter + locations[rowCopy][colCopy].immersionValuesByDirection[i];
						}
						locations[rowCopy][colCopy].immersion += ((immersionCounter - 1) - locations[rowCopy][colCopy].immersionValuesByDirection[oppositeDirectionIndex]);
						locations[rowCopy][colCopy].immersionValuesByDirection[oppositeDirectionIndex] = immersionCounter - 1;
						if (targetBoard[rowCopy][colCopy] == '-') mustUpdateImmersion = false; // after passing the first untargeted location, the 'x' located on the targetedLoc square has no effect on the immersion properties of further locations
					}
				}
			} while (BoardUtilities.onTheBoard(rowCopy, colCopy, locations) && targetBoard[rowCopy][colCopy] != 'o');
		}
	}
	
	/***
	 * Determines the longest chain of occupied locations that a location, the dependent, can be a part of such that the provider
	 * is not a part of the chain
	 * 
	 * @param dependent the location to determine the longest possible chain for
	 * @param provider the location to exclude from chains
	 * @param awayFromProvider 
	 * @param toProvider
	 * @return
	 */
	public static int longestPotentialChain(SmartLocation dependent,
			SmartLocation provider, Direction awayFromProvider, Direction toProvider) {
		int longestChain = -1;
		for (int i = 0; i < Direction.directions.length/2; i++) { // only need to consider half the directions
			int oppositeDirectionIndex = i + 4;
			if (Direction.directions[i] != toProvider
					&& Direction.directions[i] != awayFromProvider) { // can use the whole chain expanded through this direction line, as it does not run into the provider
				if (longestChain < dependent.potentialValuesByDirection[i]
						+ dependent.potentialValuesByDirection[oppositeDirectionIndex]) {
					longestChain = dependent.potentialValuesByDirection[i]
						+ dependent.potentialValuesByDirection[oppositeDirectionIndex];
				}
			} else if (Direction.directions[i] == toProvider
					&& longestChain
					   < dependent.potentialValuesByDirection[oppositeDirectionIndex]) { // if looking at the provider, need to use the chain from the other side
				longestChain = dependent.potentialValuesByDirection[oppositeDirectionIndex];
			} else if (Direction.directions[i] == awayFromProvider
					&& longestChain
					   < dependent.potentialValuesByDirection[i]) { // if looking away from the provider, need to use the chain from this side
				longestChain = dependent.potentialValuesByDirection[i];
			}
		}
		return longestChain + 1; // adds 1 to include the dependent location in the chain
	}
	
	/***
	 * Updates all locations affected by the revelation of information of a location, targetedLoc.
	 * In addition to the information stored in SmartLocations, TrackerLocations also need to
	 * update information regarding how many ways they can accommodate ships, as well as the
	 * longestReach and direction the longest reach is oriented in (See the TrackerLocation class)
	 * 
	 * @param targetedLoc the location that was just targeted
	 * @param locations the board of location objects used to store information about a board
	 * @param targetBoard the board of guesses of the overlying player
	 */
	public static void updateLocationProperties(TrackerLocation targetedLoc,
			TrackerLocation[][] locations, char[][] targetBoard) {
		for (int i = 0; i < Direction.directions.length; i++) {
			rowCopy = targetedLoc.row;
			colCopy = targetedLoc.col;
			boolean mustUpdateFreedom = true;
			boolean mustUpdateImmersion = true;
			int oppositeDirectionIndex = (i + 4) < Direction.NUM_DIRECTIONS ? i + 4 : i - 4;
			int distanceCounter = -1;
			int immersionCounter = targetedLoc.immersionValuesByDirection[oppositeDirectionIndex] + 1; // count locations themselves;
			int trackingCounter = 0;
			do {
				moveOn(Direction.directions[i]);
				distanceCounter++;
				immersionCounter++;
				trackingCounter++;
				if (BoardUtilities.onTheBoard(rowCopy, colCopy, locations) && targetBoard[rowCopy][colCopy] != 'o') {
					if (distanceCounter == 0) locations[rowCopy][colCopy].surroundingFreedom--;
					if (mustUpdateFreedom) {
						locations[rowCopy][colCopy].freedom -= (locations[rowCopy][colCopy].freedomValuesByDirection[oppositeDirectionIndex] - distanceCounter);
						locations[rowCopy][colCopy].freedomValuesByDirection[oppositeDirectionIndex] = distanceCounter;
						if (targetBoard[rowCopy][colCopy] == 'x') mustUpdateFreedom = false;
					}
					if (targetBoard[targetedLoc.row][targetedLoc.col] == 'x') { // need to update immersion properties of connected locations until the first '-' is seen
						if (distanceCounter == 0) locations[rowCopy][colCopy].surroundingImmersion++;
						if (mustUpdateImmersion) {
							if (locations[rowCopy][colCopy].maxImmersionChainLength
									< immersionCounter + locations[rowCopy][colCopy].immersionValuesByDirection[i]) {
								locations[rowCopy][colCopy].maxImmersionChainLength =
										immersionCounter + locations[rowCopy][colCopy].immersionValuesByDirection[i];
							}
							locations[rowCopy][colCopy].immersion += ((immersionCounter - 1) - locations[rowCopy][colCopy].immersionValuesByDirection[oppositeDirectionIndex]);
							locations[rowCopy][colCopy].immersionValuesByDirection[oppositeDirectionIndex] = immersionCounter - 1;
							if (targetBoard[rowCopy][colCopy] == '-') mustUpdateImmersion = false;
						}
					} else { // need to update potential properties along with longestReach and ship possibilities
						locations[rowCopy][colCopy].potential -= (locations[rowCopy][colCopy].potentialValuesByDirection[oppositeDirectionIndex] - distanceCounter);
						locations[rowCopy][colCopy].potentialValuesByDirection[oppositeDirectionIndex] = distanceCounter;
						locations[rowCopy][colCopy].updateVariance();
						if (locations[rowCopy][colCopy].maxPoDirection == Direction.directions[i]
								|| locations[rowCopy][colCopy].maxPoDirection == Direction.directions[oppositeDirectionIndex]) {
							locations[rowCopy][colCopy].updateMaxPotentialChainLength();
						}
						if (locations[rowCopy][colCopy].maxReachDirection == Direction.directions[oppositeDirectionIndex]) {
							locations[rowCopy][colCopy].updateReachProperties();
						}
						if (targetBoard[rowCopy][colCopy] == 'x' || trackingCounter >= Ship.shipSizes[0]) {
							continue;
						}
						locations[rowCopy][colCopy].updatePossibilities((i - 4) >= 0 ? i - 4 : i);
					}
				}
			} while (BoardUtilities.onTheBoard(rowCopy, colCopy, locations) && targetBoard[rowCopy][colCopy] != 'o');
		}
	}
	
	/***
	 * Travels one unit in a specified direction
	 * 
	 * @param d the direction to travel
	 */
	private static void moveOn(Direction d) {
		switch (d) {
		case NORTH:
			rowCopy--;
			break;
		case NORTHEAST:
			rowCopy--;
			colCopy++;
			break;
		case EAST:
			colCopy++;
			break;
		case SOUTHEAST:
			rowCopy++;
			colCopy++;
			break;
		case SOUTH:
			rowCopy++;
			break;
		case SOUTHWEST:
			rowCopy++;
			colCopy--;
			break;
		case WEST:
			colCopy--;
			break;
		case NORTHWEST:
			rowCopy--;
			colCopy--;
		}
	}
}