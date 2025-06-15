package model;

import java.util.ArrayList;

import view.View;
import model.enemy.Enemy;

/**
 * The world is our model. It saves the bare minimum of information required to
 * accurately reflect the state of the game. Note how this does not know
 * anything about graphics.
 */
public class World {

	/** The world's width. */
	private final int width;
	/** The world's height. */
	private final int height;
	/** The player's x position in the world. */
	private int playerX = 0;
	/** The player's y position in the world. */
	private int playerY = 0;
	/** The player's starting position in the world. */
	private int startX = 0;
	private int startY = 0;
	/** The player's end position in the world. */
	private int goalX = 0;
	private int goalY = 0;
	/** The victory status (true if game has been won) */
	private boolean victory = false;
	private boolean defeat = false;

	/** The wall configuration of the labyrinth */
	private final boolean[][] walls;
	/** Number of hammers the player has left */
	private int hammers = 0;
	/** Enemies */
	private ArrayList<Enemy> enemies = new ArrayList<>();

	/** Set of views registered to be notified of world updates. */
	private final ArrayList<View> views = new ArrayList<>();

	/**
	 * Creates a new world with the given size.
	 */
	public World(int width, int height, int startX, int startY, int goalX, int goalY, boolean[][] walls, int hammers, ArrayList<Enemy> enemies) {
		// Normally, we would check the arguments for proper values
		this.width = width;
		this.height = height;
		this.playerX = startX;
		this.playerY = startY;
		this.goalX = goalX;
		this.goalY = goalY;
		this.victory = false;
		this.defeat = false;
		this.walls = walls;
		this.hammers = hammers;
		// To do: Ensure that walls has correct dimensions (height x width)
		this.enemies = enemies;
	}

	///////////////////////////////////////////////////////////////////////////
	// Getters and Setters

	/**
	 * Returns the width of the world.
	 * 
	 * @return the width of the world.
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Returns the height of the world.
	 * 
	 * @return the height of the world.
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Returns the player's x position.
	 * 
	 * @return the player's x position.
	 */
	public int getPlayerX() {
		return playerX;
	}

	/**
	 * Sets the player's x position.
	 * 
	 * @param playerX the player's x position.
	 */
	public void setPlayerX(int playerX) {
		playerX = Math.max(0, playerX);
		playerX = Math.min(getWidth() - 1, playerX);
		this.playerX = playerX;
	}

	/**
	 * Returns the player's y position.
	 * 
	 * @return the player's y position.
	 */
	public int getPlayerY() {
		return playerY;
	}

	/**
	 * Sets the player's y position.
	 * 
	 * @param playerY the player's y position.
	 */
	public void setPlayerY(int playerY) {
		playerY = Math.max(0, playerY);
		playerY = Math.min(getHeight() - 1, playerY);
		this.playerY = playerY;
	}

	/**
	 * Returns the goal's x position.
	 * 
	 * @return the goal's x  position.
	 */
	public int getGoalX() {
		return goalX;
	}

	/**
	 * Returns the goal's y position.
	 * 
	 * @return the goal's y  position.
	 */
	public int getGoalY() {
		return goalY;
	}

	/**
	 * Tells us if the game has already been won
	 *
	 * @return boolean value, true if player position = goal position at any time in the past
	 */
	public boolean getVictoryStatus() {
		return this.victory;
	}

	public void checkIfWon() {
		if (!this.victory) {
			if (playerX == goalX && playerY == goalY) {
				this.victory = true;
				// Remove all enemies once game is won
				this.enemies.clear();
			}
		}
	}

	/**
	 * Tells us if the game has already been lost
	 *
	 * @return boolean value, true if player position = enemy position for any enemy at some point in the past
	 */
	public boolean getDefeatStatus() {
		return this.defeat;
	}

	public void checkIfLost() {
		if (!this.defeat) {
			for (int i = 0; i < this.enemies.size(); i++) {
				if (playerX == enemies.get(i).getX() && playerY == enemies.get(i).getY()) {
					this.defeat = true;
				}
			}
		}
	}

	public boolean isWall(int x, int y) {
		return walls[x][y];
	}

	public int getHammers() {
		return hammers;
	}

	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}

	///////////////////////////////////////////////////////////////////////////
	// Player Management
	
	/**
	 * Moves the player along the given direction.
	 * 
	 * @param direction where to move.
	 */
	public void movePlayer(Direction direction) {	
		// The direction tells us exactly how much we need to move along
		// every direction
		// Check if player is allowed to move the specified direction
		// (without running into a wall)
		int newX = getPlayerX() + direction.deltaX;
		int newY = getPlayerY() + direction.deltaY;
		if (hammers<1) {
			if (!walls[newY][newX]) {
				setPlayerX(newX);
				setPlayerY(newY);
			}
		} else {
			if (walls[newY][newX]) {
				walls[newY][newX] = false; // break wall
				hammers--;
			}
			setPlayerX(newX);
			setPlayerY(newY);
		}
		checkIfWon();
		checkIfLost();
		updateViews();
	}

	/**
	 * Moves the enemies (according to their specific behavior as defined in subclass).
	 *
	 */
	public void moveEnemies() {
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).move(walls, playerX, playerY, height, width);
		}
	}

		///////////////////////////////////////////////////////////////////////////
	// View Management

	/**
	 * Adds the given view of the world and updates it once. Once registered through
	 * this method, the view will receive updates whenever the world changes.
	 * 
	 * @param view the view to be registered.
	 */
	public void registerView(View view) {
		views.add(view);
		view.update(this);
	}

	/**
	 * Updates all views by calling their {@link View#update(World)} methods.
	 */
	private void updateViews() {
		for (int i = 0; i < views.size(); i++) {
			views.get(i).update(this);
		}
	}

}
