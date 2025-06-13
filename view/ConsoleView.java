package view;

import model.World;

/**
 * A view that prints the current state of the world to the console upon every
 * update.
 */
public class ConsoleView implements View {

	@Override
	public void update(World world) {
		// The player's position
		int playerX = world.getPlayerX();
		int playerY = world.getPlayerY();
		int goalX = world.getGoalX();
		int goalY = world.getGoalY();
		int hammers = world.getHammers();

		for (int row = 0; row < world.getHeight(); row++) {
			for (int col = 0; col < world.getWidth(); col++) {
				// If the player is here, print #, otherwise print .
				if (row == playerY && col == playerX) {
					System.out.print("#");
				} else if (row == goalY && col == goalX) {
					System.out.print("X");
				} else if (world.isWall(row, col)) {
					System.out.print("■");
				} else {
					System.out.print(".");
				}
			}
			// Number of hammers in central row
			if (row == (int) world.getHeight()/2) {
				System.out.print("    ");
				System.out.print(hammers);
			}
			// A newline after every row
			System.out.println();
		}

		// A newline between every update
		System.out.println();
	}

}
