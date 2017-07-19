# Diagonal-BattleShip-Project

Brief descriptions for what every file in the project is used for:

RunSims.java - starting point of the program, runs a game of battleship between human
               and computer, as well as simulations between computers

BattleShip.java - used for running a game between a human and computer

Simulator.java - abstract class that lays the foundation for simulators, taking in
                 the number of games to simulate and a boolean verbosity parameter

BattleSimulator.java - used for running a game between two computers

TestSimulator.java - used for testing various strategies by playing a game between
                     a computer running a strategy in question against a dummy computer

State.java - abstract class representing the state of a game for a player

PlayerState.java - represents a human's state, used for asking the player how he/she would
                   like to place his/her ships

ComputerState.java - represents a computer's state, used to set the strategy the computer
                     will run and randomly configure its ships

Targeter.java - abstract class laying the groundwork for Targeter objects, which computers use to
                execute their strategies

RandomTargeter.java - targeter used to target random locations

SeekingTargeter.java - targeter used to seek locations next to known occupied squares

Prioritizer.java - abstract class (extending Targeter) that lays the groundwork for Targeters
                   that employ measures of utility (and no randomness) in selecting their next
                   square to target

WeightedPrioritizer.java - Prioritizer that targets locations based on a weighted sum of 5
                           measures of utility and a primitive function for weeding out
                           impossible squares

Commander.java - Prioritizer that targets locations based on different measures of utility from
                 the WeightedPrioritizer, and using a fully exhaustive algorithm for ruling out
                 any impossible squares

Location.java - represents a location on the board, defines useful directional methods

SmartLocation.java - extends location by storing numerous measures of a location's utility
                     and some methods to reset and maintain them throughout a game, used by
                     the WeightedPrioritizer class

TrackerLocation.java - extends SmartLocation by keeping track of additional meaures of utility
                       and some methods to reset and maintain them, used by the Commander class

TargetUtilities.java - contains functions for updating many of SmartLocation's and TargetLocation's
                       measures of utility

BoardUtilities.java - stores utility methods needed during a game, such as ones to print
                      boards and lay out ships

Ship.java - stores info about ships (number of ships/sizes of ships/total squares occupied)

Direction.java - enumerates all 8 possible ship orientations

WeightFinder.java - used to find the best configuration of weights for the WeightedPrioritizer and
                    Commander targeters (most recently updated to find weights for the commander)
