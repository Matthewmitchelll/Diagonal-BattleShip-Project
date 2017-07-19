package project2;

abstract class Targeter {
	
	/*
	 * All computers (aside from dummies, described in the TestSimulator class) will initialize
	 * a Targeter object, which encapsulates the mechanism by which that computer will conduct
	 * its moves. A computer's playing strength depends on what level it has been set to; the
	 * computer will initialize a reference of a subclass of this class according to its level,
	 * which may be set to 1, 2, 3, or 4. The organization of the Targeter class hierarchy is
	 * as follows:
	 * 
	 *                                  Targeter (abstract)
	 *                                 .                   .
	 *                           .                               .
	 *                     .                                           .
	 *               RandomTargeter (1)                     Prioritizer (abstract)
	 *                     .                                .                      .
	 *                     .                            .                              .
	 *                     .                        .                                      .
	 *              SeekingTargeter (2)     WeightedPrioritizer (3)                    Commander (4)
	 * 
	 * 
	 * A description of the targeting mechanism employed for each level is given below.
	 * 
	 * Level 1: RandomTargeter - will simply randomly target untargeted locations.
	 *          Takes roughly 95.39 moves to find all ships.
	 * 
	 * From here on, all targeting mechanisms are cyclical, and employ seeking - they use
	 * knowledge of previously discovered occupied locations in some way.
	 * 
	 * Level 2: SeekingTargeter - will randomly target untargeted locations until an occupied
	 *          location is found, then will randomly target locations bordering any known
	 *          occupied locations until no such locations exist (once no such locations exist,
	 *          all known occupied locations are completely engulfed in a perimeter of known
	 *          unoccupied locations, indicating that all ships found have been completely
	 *          sunk). Finally, it will continue randomly targeting untargeted locations,
	 *          repeating the cycle until the game ends.
	 *          Takes roughly 71.23 moves to find all ships.
	 * 
	 * From here on, no element of randomness is involved. Prioritizers consider various
	 * measures of utility for each location.
	 * 
	 * Level 3: WeightedPrioritizer - uses weights to scale various Location parameters and
	 *          computes a measure of utility through a simple scoring function. Targets
	 *          locations in order of descending score until an occupied location, then
	 *          targets the locations surrounding it in order of descending score until it
	 *          finds a second location. From there, it extends the chain formed by these
	 *          discovered locations, targeting the end with the highest score, until both
	 *          ends are either off the board or capped by an unoccupied location. Once a
	 *          chain is completed, it again targets locations in order of descending score,
	 *          repeating the cycle. Uses a primitive method to validate locations selected
	 *          for targeting, ensuring that the location can hold a ship without creating
	 *          2 2-length ships or any 1-length ships. However, the function does not
	 *          detect if the location is incompatible due to any other circumstances.
	 *          For more detail, refer to the WeightedPrioritizer class.
	 *          Takes roughly 61.83 moves to find all ships.
	 * 
	 * Level 4: The Commander - similar to the WeightedPrioritizer in that it uses weights
	 *          in computing a measure of utility and employs the same chain expansion
	 *          algorithm described in the previous paragraph, however the weights it uses
	 *          scale different parameters, and the validation technique used to determine
	 *          whether a chosen location is valid attempts to find a possible configuration
	 *          assuming it is, and only determines that the location is impossible if no
	 *          such configuration exists. Although the computation incurs a performance
	 *          penalty, the increase in execution time is undetectable when a human plays
	 *          it, and results in a reduction of roughly 6 moves a game from that of the
	 *          WeightedPrioritizer.
	 *          Takes roughly 55.86 moves to find all ships.
	 * 
	 * All Targeter's require a reference to the overlying computer's targetBoard for
	 * initialization, and all will maintain a Location object representing the overlying
	 * computer's next target (see the Location class and its subclasses, SmartLocation and
	 * TrackerLocation) along with an ArrayList of Locations to store all moves yet to be played.
	 */
	
	protected char[][] targetBoard; // used to obtain knowledge of targeted locations
	
	/***
	 * Constructs a Targeter
	 * 
	 * @param targetBoard the targetBoard of the overlying ComputerState object
	 */
	Targeter(char[][] targetBoard) {
		this.targetBoard = targetBoard;
	}
	
	/***
	 * Selects the overlying ComputerState object's next move
	 * 
	 * @returns a Location object representing the overlying computer's next target
	 */
	protected abstract Location getNextMove();
	
	/***
	 * Resets a Targeter
	 */
	protected abstract void reset();
	
	/***
	 * Sets weights used for a WeightedPrioritizer
	 * 
	 * @param maxICWeight weight to scale the maxImmersionChainLength of a location
	 * @param maxPCWeight weight to scale the maxPotentialChainLength of a location
	 * @param iWeight weight to scale the immersion of a location
	 * @param pWeight weight to scale the potential of a location
	 * @param vWeight weight to scale the variance of a location
	 */
	protected void setNewWeights(int maxICWeight, int maxPCWeight, int iWeight, int pWeight,
			int vWeight) {}
	
	/***
	 * Sets weights used for a Commander
	 * 
	 * @param tPWeight weight to scale the total number of ways a location can accommodate all ships
	 * @param maxICWeight weight to scale the maxImmersionChainLength of a location
	 */
	protected void setNewWeights(int tPWeight, int maxICWeight) {}
}