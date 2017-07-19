package project2;

/*
 * Enumerates all possible ship orientations. Allows the user to place ships on the board diagonally
 * for more interesting configurations.
 */

enum Direction {
	
	NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST;
	
	public static final int NUM_DIRECTIONS = 8;
	
	public static final Direction[] directions = {NORTH, NORTHEAST, EAST, SOUTHEAST,
												  SOUTH, SOUTHWEST, WEST, NORTHWEST};
	public static final Direction[] scanDirections = {EAST, SOUTHEAST, SOUTH, SOUTHWEST};
	
	public Direction opposite() {
		switch (this) {
		case NORTH:
			return SOUTH;
		case NORTHEAST:
			return SOUTHWEST;
		case EAST:
			return WEST;
		case SOUTHEAST:
			return NORTHWEST;
		case SOUTH:
			return NORTH;
		case SOUTHWEST:
			return NORTHEAST;
		case WEST:
			return EAST;
		case NORTHWEST:
			return SOUTHEAST;
		}
		return null;
	}
}