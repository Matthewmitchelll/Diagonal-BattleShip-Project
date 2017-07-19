package project2;

enum Ship {
	
	/*
	 * This class contains all information needed about ships to play the game.
	 */
	
	CARRIER(5), BATTLESHIP(4), CRUISER(3), SUBMARINE(3), DESTROYER(2);
	
	public static int[] shipSizes = {5, 4, 3, 3, 2};
	
	public static final int TOTAL_SHIP_OCCUPANCY = 17; // total occupancy of all 5 ship types
	public static final int NUM_SHIPS = 5;
	
	public final int size; // number of slots each ship will occupy
	
	/***
	 * Constructs a ship
	 * 
	 * @param size number of slots each ship will occupy
	 */
	Ship(int size) {
		this.size = size;
	}
}