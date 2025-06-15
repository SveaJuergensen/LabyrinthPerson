package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainMenu {

    private static JFrame frame;

    public MainMenu(ActionListener startGraphic, ActionListener exit) {
        frame = new JFrame("Labyrinth - Main Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton graphicButton = new JButton("Start");
        JButton exitButton = new JButton("Exit");

        graphicButton.addActionListener(startGraphic);
        exitButton.addActionListener(exit);

        panel.add(graphicButton);
        panel.add(exitButton);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    public static void close() {
        frame.dispose();
    }
}
