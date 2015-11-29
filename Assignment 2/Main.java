/*
 * Matt Asnes
 * COMP-86 Assignment 2
 *
 * This class is the main class for the program, and instantiates the GUI window
 * as well as creating and placing the smaller GUI elements.
 */

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class Main {

        public Main() {
                 /* (Try to) Use default OS window look and feel */
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }     

                createAndShowGUI();
        }

        private static void createAndShowGUI() {
                // Create and set up the window.
                JFrame frame = new JFrame("Assignment 2");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                // Add BorderLayout
                JPanel mainPanel = new JPanel(new BorderLayout());
                frame.add(mainPanel);

                // Fill the top BorderLayout panel with buttons
                ButtonPanel top = new ButtonPanel(25, 65);
                top.setPreferredSize(new Dimension(1200, 150));
                top.addButton("Descend");
                top.addButton("Ascend");
                top.addButton("Pitch Left");
                top.addButton("Pitch Right");
                top.addButton("Increase Velocity");
                // PAGE_START puts the panel at the top
                mainPanel.add(top, BorderLayout.PAGE_START);

                // Add the canvas to the center, with a border
                JCanvas canvas = new JCanvas();
                canvas.setPreferredSize(new Dimension(800, 600));
                canvas.setBorder(new LineBorder(Color.DARK_GRAY, 5));
                mainPanel.add(canvas, BorderLayout.CENTER);

                // Fill the left panel with buttons
                ButtonPanel left = new ButtonPanel(100, 25);
                left.setPreferredSize(new Dimension(200, 600));
                left.addButton("Filter 0");
                left.addButton("Filter 1");
                left.addButton("Filter 2");
                left.addButton("Filter 3");
                left.addButton("Filter 4");

                // Add a list of planes to the left panel
                PlaneList list = new PlaneList(canvas);
                left.add(list);

                // Add the left panel (buttons and list), to the main panel 
                mainPanel.add(left, BorderLayout.LINE_START);

                // Add buttons to the bottom panel
                ButtonPanel bottom = new ButtonPanel(25, 65);
                bottom.setPreferredSize(new Dimension(1200, 150));
                bottom.addButton("Zoom In");
                bottom.addButton("Zoom Out");
                bottom.addButton("Capture Image");
                bottom.addButton("Send Images");
                bottom.addButton("Send Sensor Values");
                mainPanel.add(bottom, BorderLayout.PAGE_END);

                // Add buttons to the right panel
                ButtonPanel right = new ButtonPanel(100, 25);
                right.setPreferredSize(new Dimension(200, 600));
                right.addButton("Channel 0");
                right.addButton("Channel 1");
                right.addButton("Channel 2");
                right.addButton("Channel 3");
                right.addButton("Channel 4");
                mainPanel.add(right, BorderLayout.LINE_END);

                //Display the window.
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);


        }

        public static void main(String[] args) {
                Main main = new Main();
        }
}