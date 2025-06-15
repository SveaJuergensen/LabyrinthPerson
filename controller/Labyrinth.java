package controller;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;

import model.World;
import model.enemy.Enemy;
import model.enemy.Guard;
import view.ConsoleView;
import view.GraphicView;
import view.MainMenu;

/**
 * This is our main program. It is responsible for creating all of the objects
 * that are part of the MVC pattern and connecting them with each other.
 */
public class Labyrinth {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ActionListener startGraphic = new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // MainMenu.close();
                        startGraphic();
                    }
                };

                ActionListener exit = new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        MainMenu.close();
                        System.exit(0);
                    }
                };

                MainMenu mainMenu = new MainMenu(startGraphic, exit);
            }

                private void startGraphic() {
                    // Dimension of the game board (10x10).
                    int width = 10;
                    int height = 10;
                    // level configuration
                    int startX = 0;
                    int startY = 0;
                    int goalX = 5;
                    int goalY = 5;
                    // wall configuration
                    boolean[][] walls = {{false, true, false, false, false, true, false, false, true, true},
                            {false, true, false, true, false, false, false, false, false, false},
                            {false, false, false, true, false, false, false, false, false, true},
                            {false, true, true, true, true, true, false, false, false, false},
                            {false, true, false, false, false, false, false, false, false, false},
                            {false, true, true, false, false, false, false, false, false, false},
                            {false, true, false, false, false, false, false, true, true, true},
                            {false, true, true, true, true, true, false, false, false, false},
                            {false, false, false, false, false, true, true, true, true, false},
                            {true, true, false, false, false, false, false, false, false, false}};
                    int hammers = 3;
                    // enemies
                    ArrayList<Enemy> enemies = new ArrayList<>();
                    enemies.add(new Guard(goalX, goalY));

                    /** (Idea: Player can break e.g. 1 wall per round. Safe which walls have been broken)
                     boolean[][] broken = new boolean[width][height];
                     for (int y = 0; y < height; y++) {
                     for (int x = 0; x < width; x++) {
                     broken[x][y] = false;
                     }
                     }
                     */

                    // Create a new game world.
                    World world = new World(width, height, startX, startY, goalX, goalY, walls, hammers, enemies);

                    // Size of a field in the graphical view.
                    Dimension fieldDimensions = new Dimension(25, 25);
                    // Create and register graphical view.
                    GraphicView gview = new GraphicView(
                            width * fieldDimensions.width,
                            height * fieldDimensions.height,
                            fieldDimensions);
                    world.registerView(gview);
                    gview.setVisible(true);

                    // Create and register console view.
                    ConsoleView cview = new ConsoleView();
                    world.registerView(cview);

                    // Create controller and initialize JFrame.
                    Controller controller = new Controller(world);
                    controller.setTitle("Square Move Practice");
                    controller.setResizable(false);
                    // controller.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    controller.getContentPane().add(gview);
                    // pack() is needed before JFrame size can be calculated.
                    controller.pack();

                    // Calculate size of window by size of insets (titlebar + border), size of
                    // graphical view, and some space for mouse controls
                    Insets insets = controller.getInsets();

                    int windowX = width * fieldDimensions.width + insets.left + insets.right + 3 * fieldDimensions.width;
                    int windowY = height * fieldDimensions.height + insets.bottom + insets.top;
                    Dimension size = new Dimension(windowX, windowY);
                    controller.setSize(size);
                    controller.setMinimumSize(size);
                    controller.setVisible(true);
            }
        });
    }
}
