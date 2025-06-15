package model.enemy;

public abstract class Enemy {
    private int positionX = 0;
    private int positionY = 0;

    public Enemy(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Getters and Setters

    /**
     * Returns the enemy's x position.
     *
     * @return the enemy's x position.
     */
    public int getX() {
        return positionX;
    }

    /**
     * Sets the enemy's x position.
     *
     * @param x the enemy's new x position.
     * @param boardWidth the width dimension of the labyrinth
     */
    public void setX(int x, int boardWidth) {
        x = Math.max(0, x);
        x = Math.min(boardWidth - 1, x);
        this.positionX = x;
    }

    /**
     * Returns the enemy's y position.
     *
     * @return the enemy's y position.
     */
    public int getY() {
        return positionY;
    }

    /**
     * Sets the enemy's y position.
     *
     * @param y the enemy's new y position.
     * @param boardHeight the height dimension of the labyrinth
     */
    public void setY(int y, int boardHeight) {
        y = Math.max(0, y);
        y = Math.min(boardHeight - 1, y);
        this.positionY = y;
    }


    ///////////////////////////////////////////////////////////////////////////
    // Movements

    /**
     * Abstract class for making a move. Has to be implemented in subclasses to reflect
     * the specific behavior of each enemy type.
     *
     * @param walls the wall configuration of the labyrinth
     * @param playerX the current x coordinate where the player is
     * @param playerY the current y coordinate where the player is
     * @param boardHeight the height dimension of the labyrinth
     * @param boardWidth the width dimension of the labyrinth
     */
    public abstract void move(boolean[][] walls, int playerX, int playerY, int boardHeight, int boardWidth);

}
