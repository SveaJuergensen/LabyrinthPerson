package  view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JPanel;

import model.World;

/**
 * A graphical view of the world.
 */
public class GraphicView extends JPanel implements View {

	/** The view's width. */
	private final int WIDTH;
	/** The view's height. */
	private final int HEIGHT;

	private Dimension fieldDimension;

	public GraphicView(int width, int height, Dimension fieldDimension) {
		this.WIDTH = width;
		this.HEIGHT = height;
		this.fieldDimension = fieldDimension;
		this.bg = new Rectangle(WIDTH, HEIGHT);
	}

	/** The background rectangle. */
	private final Rectangle bg;
	/** The rectangle we're moving. */
	private final Rectangle player = new Rectangle(1, 1);
	/** The goal rectangle. */
	private final Rectangle goal = new Rectangle(1, 1);
	/** A wall rectangle. */
	private final ArrayList<Rectangle> walls = new ArrayList<Rectangle>();

	/** Game status */
	private boolean victory = false;
	/** Number of hammers left to the player */
	private int hammers = 0;

	/**
	 * Creates a new instance.
	 */
	@Override
	public void paint(Graphics g) {
		// Paint background, make whole field white if game has been won
		g.setColor(Color.WHITE);
		g.fillRect(bg.x, bg.y, bg.width+3*fieldDimension.width, bg.height);
		if (!victory) {
			g.setColor(Color.RED);
			g.fillRect(bg.x, bg.y, bg.width, bg.height);
		}
		// Paint goal
		g.setColor(Color.WHITE);
		g.fillRect(goal.x, goal.y, goal.width, goal.height);
		// Paint player
		g.setColor(Color.BLACK);
		g.fillOval(player.x, player.y, player.width, player.height);
		// Paint walls
		for (int i = 0; i < walls.size(); i++) {
			g.setColor(Color.BLACK);
			g.fillRect(walls.get(i).x, walls.get(i).y, walls.get(i).width, walls.get(i).height);
		}
		// Level
		String h = Integer.toString(hammers);
		g.drawString(h,WIDTH+fieldDimension.width,HEIGHT/2);
	}

	@Override
	public void update(World world) {

		// Update players size and location
		player.setSize(fieldDimension);
		player.setLocation(
			(int) (world.getPlayerX() * fieldDimension.width),
			(int) (world.getPlayerY() * fieldDimension.height)
		);

		// Update goal size and location
		goal.setSize(fieldDimension);
		goal.setLocation(
			(int) (world.getGoalX() * fieldDimension.width),
			(int) (world.getGoalY() * fieldDimension.height)
		);

		// Update wall locations
		walls.clear();
		// System.out.println(world.getHeight());
		// System.out.println(world.getWidth());
		for (int i = 0; i < world.getHeight(); i++) {
			for (int j = 0; j < world.getWidth(); j++) {
				if (world.isWall(i,j)) {
					Rectangle w = new Rectangle(1, 1);
					w.setSize(fieldDimension);
					w.setLocation(j*fieldDimension.width, i*fieldDimension.width);
					walls.add(w);
				}
			}
		}

		// Update number of hammers left to player
		hammers = world.getHammers();

		// Update victory status
		victory = world.getVictoryStatus();

		repaint();
	}

}
