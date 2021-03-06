/*
 * Matt Asnes
 * COMP-86 Assignment 3
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
                JFrame frame = new JFrame("Assignment 3");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                // Add BorderLayout
                JPanel mainPanel = new JPanel(new BorderLayout());
                frame.add(mainPanel);

                Map map = new Map(20);

                // Fill the top BorderLayout panel with buttons
                ControlPanel top = new ControlPanel(25, 40, "Filters");
                top.setPreferredSize(new Dimension(1200, 150));
                top.addFilterButton("Filter 1", map, 250);
                top.addFilterButton("Filter 2", map, 200);
                top.addFilterButton("Filter 3", map, 150);
                top.addFilterButton("Filter 4", map, 100);
                top.addFilterButton("Filter 5", map, 50);


                // PAGE_START puts the panel at the top
                mainPanel.add(top, BorderLayout.PAGE_START);

                // Add the canvas to the center, with a border
                
                map.setPreferredSize(new Dimension(800, 600));
                map.setBorder(new LineBorder(Color.DARK_GRAY, 5));
                mainPanel.add(map, BorderLayout.CENTER);

                // Fill the left panel with buttons
                ControlPanel left = new ControlPanel(100, 25, "Planes");
                left.setPreferredSize(new Dimension(200, 600));
                left.addButton("Ascend All");
                left.addButton("Descend All");
                left.addButton("Stop All");

                // Add a list of planes to the left panel
                PlaneList list = new PlaneList(map);
                list.setPreferredSize(new Dimension(100, 300));
                left.add(list);

                // Add the left panel (buttons and list), to the main panel 
                mainPanel.add(left, BorderLayout.LINE_START);

                // Add buttons to the bottom panel
                ControlPanel bottom = new ControlPanel(25, 40, "Instruments");
                bottom.setPreferredSize(new Dimension(1200, 150));
                bottom.addButton("Zoom In");
                bottom.addButton("Zoom Out");
                bottom.addButton("Capture Image");
                bottom.addButton("Send Images");
                bottom.addButton("Send Sensor Values");
                mainPanel.add(bottom, BorderLayout.PAGE_END);

                // Add things to the right panel
                ControlPanel right = new ControlPanel(100, 0, "Flight Controls");
                right.setPreferredSize(new Dimension(200, 600));
                
                VelocityPanel vel = new VelocityPanel(map, right.getPreferredSize());
                right.add(vel);

                PitchPanel pitch = new PitchPanel(map, right.getPreferredSize());
                right.add(pitch);

                AnglePanel angle = new AnglePanel(map, right.getPreferredSize());
                right.add(angle);
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