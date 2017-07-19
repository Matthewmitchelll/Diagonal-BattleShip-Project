package project2;

class RunSims {
	
	/*
	 * This class is used to play a game of BattleShip and to create instances
	 * of Simulator objects in order to run simulations of Battleship.
	 */
	
	public static void main(String[] args) {
		BattleShip bs = new BattleShip(true);
		bs.playGame(); // plays a game between a human and computer
		
		BattleSimulator bsim = new BattleSimulator(10000, false, 3, 4);
		bsim.runSim(); // plays a game between two computers
		
		TestSimulator ts1 = new TestSimulator(1, 4, true);
		ts1.runSim(); // tests the level 4 strategy one time with print statements
		TestSimulator ts2 = new TestSimulator(100000, 4, false);
		ts2.runSim(); // tests the level 4 strategy with 100000 boards, no print statements
		TestSimulator ts3 = new TestSimulator(1000, 4, false);
		ts3.runSim();
	}
}