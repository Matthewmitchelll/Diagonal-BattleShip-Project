package project2;

class TrackerLocation extends SmartLocation {
	
	/*
	 * TrackerLocation objects are used by the Commander class to make smarter decisions about what
	 * locations to target next. This class extends the SmartLocation class by adding freedom parameters
	 * to indicate how many untargeted locations a location is around, reach parameters to indicate how
	 * the longest possible length ship a location can accommodate, possibilities parameters to keep
	 * track of the number of ways a location can accommodate each ship along each direction, and a
	 * boolean value to keep track of whether a location is used as part of a possible configuration of
	 * ships. This class also includes functions to update some parameters and to reset.
	 */
	
	TrackerLocation[][] boardLocations;
	
	private int defaultLongestReach;
	private Direction defaultMaxReachDirection;
	private int[] defaultPossibilitiesByDirection = new int[Direction.NUM_DIRECTIONS/2];
	private int defaultTotalPossibilities;
	
	public int[] freedomValuesByDirection = new int[Direction.NUM_DIRECTIONS]; // the number of untargeted locations extending out from a location, not including the location itself, in all directions (starting from north and going clockwise)
	
	public double freedom; // the number of untargeted locations this location is connected to, either through direct bordering or through other untargeted locations
	
	public int longestReach; // the longest length of untargeted or occupied locations that extend out from a location, not including the locaiton itself
	public Direction maxReachDirection; // the direction the longestReach extends out to
	
	public int[] possibilitiesByDirection = new int[Direction.NUM_DIRECTIONS/2]; // holds the total number of ways a location can accommodate all ships in each direction (the first value in the array holds the value for north-south orientations, and the array proceeds clockwise)
	public int totalPossibilities; // the total number of ways an array can hold all ships, always the sum of the values in the possibilitiesByDirection array
	
	public boolean locationUsed = false; // indicates that this location is being used in a possible configuration of ships, to be used for determining whether a location is possible by the Commander
	
	/***
	 * Constructs a TrackerLocation
	 * 
	 * @param row the row of the location
	 * @param col the column of the location
	 * @param boardLocations 2D array of SmartLocations used for determining default values and in the locationTowards function
	 */
	TrackerLocation(int row, int col, TrackerLocation[][] boardLocations) {
		super(row, col, boardLocations);
		this.boardLocations = boardLocations;
		setDefaultSettings();
		setSettingsToDefault();
	}
	
	/***
	 * Sets default values for a TrackerLocation
	 */
	private void setDefaultSettings() {
		for (int i = 0; i < Direction.NUM_DIRECTIONS/2; i++) {
			if (potentialValuesByDirection[i] > defaultLongestReach) {
				defaultMaxReachDirection = Direction.directions[i];
				defaultLongestReach = potentialValuesByDirection[i];
			}
			if (potentialValuesByDirection[i + 4] > defaultLongestReach) { // checks the opposite direction, as only half the directions are scanned to avoid overcounting total possibilities below
				defaultMaxReachDirection = Direction.directions[i + 4];
				defaultLongestReach = potentialValuesByDirection[i + 4];
			}
			int shipPossibilities = 0;
			for (int j = 0; j < Ship.NUM_SHIPS; j++) {
				for (int k = 0; k <= Math.min(potentialValuesByDirection[i], Ship.shipSizes[j] - 1); k++) { // k represents the number of locations used in the ith direction from this location to accomodate a ship, can never be greater than the potential in that direction or the size of the ship minus one
					if (potentialValuesByDirection[i + 4] >= Ship.shipSizes[j] - k - 1) shipPossibilities++; // 1 indicates the current location being used
				}
			}
			defaultPossibilitiesByDirection[i] = shipPossibilities;
			defaultTotalPossibilities += shipPossibilities;
		}
	}
	
	/***
	 * Sets the parameters of a TrackerLocation to the default values calculated during construction
	 */
	private void setSettingsToDefault() {
		for (int i = 0; i < Direction.NUM_DIRECTIONS; i++) {
			freedomValuesByDirection[i] = defaultPotentialValues[i];
		}
		freedom = defaultPotential;
		longestReach = defaultLongestReach;
		maxReachDirection = defaultMaxReachDirection;
		possibilitiesByDirection[0] = defaultPossibilitiesByDirection[0];
		possibilitiesByDirection[1] = defaultPossibilitiesByDirection[1];
		possibilitiesByDirection[2] = defaultPossibilitiesByDirection[2];
		possibilitiesByDirection[3] = defaultPossibilitiesByDirection[3];
		totalPossibilities = defaultTotalPossibilities;
		locationUsed = false;
	}
	
	/***
	 * Updates a locations longest reach, as well as the direction of the reach
	 */
	public void updateReachProperties() {
		longestReach = 0;
		for (int i = 0; i < Direction.NUM_DIRECTIONS; i++) {
			if (potentialValuesByDirection[i] > longestReach) {
				maxReachDirection = Direction.directions[i];
				longestReach = potentialValuesByDirection[i];
			}
		}
	}
	
	/***
	 * Updates the number of ways a location can accommodate all ships by adjusting the number of ways it can do so
	 * along a single direction
	 * 
	 * @param directionIndex the index of the desired direction to consider, starting from north at 0 and ending at northwest at 7, going clockwise
	 */
	public void updatePossibilities(int directionIndex) {
		int shipPossibilities = 0;
		for (int i = 0; i < Ship.NUM_SHIPS; i++) {
			for (int k = 0; k <= Math.min(potentialValuesByDirection[directionIndex], Ship.shipSizes[i] - 1); k++) {
				if (potentialValuesByDirection[directionIndex + 4] >= Ship.shipSizes[i] - k - 1) shipPossibilities++;
			}
		}
		totalPossibilities -= (possibilitiesByDirection[directionIndex] - shipPossibilities);
		possibilitiesByDirection[directionIndex] = shipPossibilities;
	}
	
	/***
	 * Determines the location in a specified direction from this location
	 * 
	 * @param dir the desired direction to inspect
	 * @return a TrackerLocation object representing the location on the board in the given direction from this direction, or null if it is off the board
	 */
	public TrackerLocation locationTowards(Direction dir) {
		int newRow = row, newCol = col;
		switch (dir) {
		case NORTH:
			newRow--;
			break;
		case NORTHEAST:
			newRow--;
			newCol++;
			break;
		case EAST:
			newCol++;
			break;
		case SOUTHEAST:
			newRow++;
			newCol++;
			break;
		case SOUTH:
			newRow++;
			break;
		case SOUTHWEST:
			newRow++;
			newCol--;
			break;
		case WEST:
			newCol--;
			break;
		case NORTHWEST:
			newRow--;
			newCol--;
		}
		if (BoardUtilities.onTheBoard(newRow, newCol, boardLocations)) return boardLocations[newRow][newCol];
		return null;
	}
	
	/***
	 * Resets a TrackerLocation by setting parameters to their default values
	 */
	public void reset() {
		super.reset();
		setSettingsToDefault();
	}
}