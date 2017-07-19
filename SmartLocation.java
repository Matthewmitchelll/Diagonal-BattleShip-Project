package project2;

class SmartLocation extends Location {
	
	/*
	 * SmartLocation objects are used by the WeightedPriorizer class to make smarter decisions
	 * about what locations to target next. This class extends the Location class by adding a wide
	 * variety of parameters to keep track of and provide a measure for a locations expected utility,
	 * as well as functions to update and reset them to default values as games progress and start over,
	 * respectively. Also adds the locationTowards function, which returns the SmartLocation object in a
	 * specified direction from the invoking SmartLocation.
	 */
	
	SmartLocation[][] boardLocations; // used to set default values and in the locationTowards function
	
	// the following are default variables used to reset SmartLocations efficiently
	protected int[] defaultPotentialValues = new int[Direction.NUM_DIRECTIONS];
	private int defaultMaxPotentialChainLength;
	private Direction defaultMaxPoDirection;
	protected double defaultPotential;
	private double defaultAverageChainLength;
	private double defaultVariance;
	private int defaultSurroundingFreedom;
	
	public int[] potentialValuesByDirection = new int[Direction.NUM_DIRECTIONS]; // array that stores the amount of untargeted or occupied locations going out in a direction, not including the location itself. Clockwise starting with going north
	public int[] immersionValuesByDirection = new int[Direction.NUM_DIRECTIONS]; // array that stores the amount of occupied locations going out in a direction in the same way
	
	public int maxPotentialChainLength; // the longest chain of untargeted or occupied locations that this location is a part of
	public int maxImmersionChainLength; // the longest chain of occupied locations that this location would be a part of if it were an occupied location
	
	public Direction maxPoDirection; // the direction the max potential chain is oriented in, only considering the first four directions in the Direction class (north, northeast east, and southeast)
	public Direction maxImDirection; // the direction the max immersion chain is oriented in, again only considering the first four direction in the Direction class
	
	public double potential; // the total number of untargeted or occupied locations this location is connected with, either through direct bordering or through a chain of untargeted or occupied locations. Does not include the location itself
	public double variance; // the measure of how little the potential of a location in a particular direction deviates from the mean as the direction being considered changes. This trait is used to give stronger consideration to centralized locations
	
	public int immersion; // the total number of occupied locations this location is connected with, either through direct bordering or through a chain of occupied locations. Does not include the location itself
	public int surroundingImmersion; // the total number of occupied locations immediately surrounding this location
	public int surroundingFreedom; // the total number of untargeted locations immediately surrounding this location
	
	/***
	 * Constructs a SmartLocation
	 * 
	 * @param row the row of the location
	 * @param col the column of the location
	 * @param boardLocations 2D array of SmartLocations used for determining default values and in the locationTowards function
	 */
	SmartLocation(int row, int col, SmartLocation[][] boardLocations) {
		super(row, col);
		this.boardLocations = boardLocations;
		setDefaultSettings();
		setSettingsToDefault();
	}
	
	/***
	 * Sets default values for a SmartLocation
	 */
	private void setDefaultSettings() {
		// the number of untargeted locations to the north, northeast, east, southeast, south, southwest,
		// west, and northwest of a coordinate (row, col) are, respectively: row,
		// Math.min(row, boardLocations.length - col - 1), boardLocations.length - col - 1,
		// Math.min(boardLocations.length - row - 1, boardLocations.length - col - 1),
		// boardLocations.length - row - 1, Math.min(boardLocations.length - row - 1, col), col, and
		// Math.min(row, col). Summing these up yield the location's freedom rating at the beginning of a
		// game, which is the point of the next few lines of code
		defaultPotentialValues[0] = row;
		defaultPotentialValues[1] = Math.min(row, boardLocations.length - col - 1);
		defaultPotentialValues[2] = boardLocations.length - col - 1;
		defaultPotentialValues[3] = Math.min(boardLocations.length - row - 1, boardLocations.length - col - 1);
		defaultPotentialValues[4] = boardLocations.length - row - 1;
		defaultPotentialValues[5] = Math.min(boardLocations.length - row - 1, col);
		defaultPotentialValues[6] = col;
		defaultPotentialValues[7] = Math.min(row, col);
		for (int i = 0; i < defaultPotentialValues.length/2; i++) {
			if (defaultMaxPotentialChainLength < defaultPotentialValues[i] + defaultPotentialValues[i + 4] + 1) {
				defaultMaxPotentialChainLength = defaultPotentialValues[i] + defaultPotentialValues[i + 4] + 1;
				defaultMaxPoDirection = Direction.directions[i];
			}
		}
		defaultPotential = defaultPotentialValues[0] + defaultPotentialValues[1] + defaultPotentialValues[2]
				+ defaultPotentialValues[3] + defaultPotentialValues[4] + defaultPotentialValues[5]
						+ defaultPotentialValues[6] + defaultPotentialValues[7];
		defaultAverageChainLength = defaultPotential/Direction.NUM_DIRECTIONS;
		for (int length : defaultPotentialValues) {
			defaultVariance += Math.pow((length - defaultAverageChainLength), 2);
			if (length > 0) defaultSurroundingFreedom++;
		}
	}
	
	/***
	 * Sets the parameters of a SmartLocation to the default values calculated during construction
	 */
	private void setSettingsToDefault() {
		potentialValuesByDirection[0] = defaultPotentialValues[0];
		potentialValuesByDirection[1] = defaultPotentialValues[1];
		potentialValuesByDirection[2] = defaultPotentialValues[2];
		potentialValuesByDirection[3] = defaultPotentialValues[3];
		potentialValuesByDirection[4] = defaultPotentialValues[4];
		potentialValuesByDirection[5] = defaultPotentialValues[5];
		potentialValuesByDirection[6] = defaultPotentialValues[6];
		potentialValuesByDirection[7] = defaultPotentialValues[7];
		maxPotentialChainLength = defaultMaxPotentialChainLength;
		maxPoDirection = defaultMaxPoDirection;
		potential = defaultPotential;
		variance = defaultVariance;
		surroundingFreedom = defaultSurroundingFreedom;
	}
	
	/***
	 * Updates the maxPotentialChainLength of a SmartLocation, along with the direction it is oriented in
	 */
	public void updateMaxPotentialChainLength() {
		maxPotentialChainLength = 0;
		for (int i = 0; i < potentialValuesByDirection.length/2; i++) {
			if (maxPotentialChainLength < potentialValuesByDirection[i] + potentialValuesByDirection[i + 4] + 1) {
				maxPotentialChainLength = potentialValuesByDirection[i] + potentialValuesByDirection[i + 4] + 1;
				maxPoDirection = Direction.directions[i];
			}
		}
	}
	
	/***
	 * Updates the variance parameter of a SmartLocation
	 */
	public void updateVariance() {
		double averageChainLength = potential/Direction.NUM_DIRECTIONS;
		variance = 0;
		for (int length : potentialValuesByDirection) {
			variance += Math.pow((length - averageChainLength), 2);
		}
	}
	
	/***
	 * Determines the location in a specified direction from this location
	 * 
	 * @param dir the desired direction to inspect
	 * @return a SmartLocation object representing the location on the board in the given direction from this direction, or null if it is off the board
	 */
	public SmartLocation locationTowards(Direction dir) {
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
			newRow++;;
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
	 * Resets a SmartLocation by setting parameters to their default values
	 */
	public void reset() {
		setSettingsToDefault();
		for (int i = 0; i < immersionValuesByDirection.length; i++) immersionValuesByDirection[i] = 0;
		maxImmersionChainLength = 0;
		immersion = 0;
		surroundingImmersion = 0;
	}
}