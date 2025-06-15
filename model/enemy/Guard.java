package model.enemy;

import java.util.concurrent.ThreadLocalRandom;

import model.enemy.Enemy;

public class Guard extends Enemy {

    public Guard(int goalX, int goalY) {
        super(goalX, goalY);
    }

    @Override
    public void move(boolean[][] walls, int playerX, int playerY, int boardHeight, int boardWidth) {
        int randomNum = ThreadLocalRandom.current().nextInt(1, 4 + 1);
        System.out.println("Random num: " + randomNum);
        switch(randomNum) {
            case 1:
                if (!walls[super.getY()][super.getX()-1]) {
                    super.setX(super.getX()-1, boardWidth);
                }
                break;
            case 2:
                if (!walls[super.getY()][super.getX()+1]) {
                    super.setX(super.getX()+1, boardWidth);
                }
                break;
            case 3:
                if (!walls[super.getY()-1][super.getX()]) {
                    super.setY(super.getY()-1, boardWidth);
                }
                break;
            case 4:
                if (!walls[super.getY()+1][super.getX()]) {
                    super.setY(super.getY()+1, boardWidth);
                }
                break;
        }
    }

}
