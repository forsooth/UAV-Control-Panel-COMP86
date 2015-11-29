/*
 * Matt Asnes
 * COMP-86 Assignment 2
 *
 * This class is a canvas element for Swing, built on top
 * of JPanel. It contains the airplane polygon for the UAV
 * interface, and when updated it repaints the airplane
 * field. A 'selected' plane can be chosen, which is highlighted
 * in red. 
 */

import java.awt.*;
import java.awt.font.*;
import java.util.Random;
import javax.swing.*;

public class JCanvas extends JPanel {

        // Polygon for the planes
        private int xCoords[] = {  0,   1,   2,   2, 12, 13,  2, 2, 1, -1, -2, -2, -13, -12,  -2,  -2,  -1,   0};
        private int yCoords[] = {-20, -19, -16, -10, -8, -7, -7, -1, 2,  2,  -1, -7,  -7,  -8, -10, -16, -19, -20};
        private Polygon plane;
        
        public final int NUM_PLANES = 30;
        
        // The index of the currently active plane
        private int selectedIndex = 0;
        
        // Store a font for numbering the planes
        private final Font font = new Font("SansSerif", Font.PLAIN, 16);

        private void doDrawing(Graphics g) {

                Graphics2D g2d = (Graphics2D) g;

                Dimension size = getSize();

                int w = size.width;
                int h = size.height;

                Random r = new Random();

                // Fill the background with random noise. Simple probability
                // math says that on average half of the canvas will be filled with
                // a random color.
                for (int i = 0; i <= this.getWidth()*this.getHeight(); i++) {
                        // Grab a new point at a random position
                        int x = Math.abs(r.nextInt()) % w;
                        int y = Math.abs(r.nextInt()) % h;
                        // Make a random color brighter than rgb(150, 150, 150)
                        int R, G, B;
                        R = Math.abs(r.nextInt()) % 106 + 150;
                        G = Math.abs(r.nextInt()) % 106 + 150;
                        B = Math.abs(r.nextInt()) % 106 + 150;
                        // Draw a line from (x,y) to (x,y), i.e. one point
                        g2d.setColor(new Color(R, G, B));
                        g2d.drawLine(x, y, x, y);
                }

                // Space to copy the x and y points of the airplane polygon, so that we
                // don't overwrite anything we want to use later.
                int xcopy[] = new int[xCoords.length];
                int ycopy[] = new int[yCoords.length];

                // We want black airplanes labeled with our font.
                g2d.setColor(Color.BLACK);
                g2d.setFont(font); 

                // Draw the airplane polygon NUM_PLANES times, at random coordinates
                for(int n = 0; n < NUM_PLANES; n++) {
                        // Reset the color, because we change it in the loop
                        g2d.setColor(Color.BLACK);

                        // Copy over the polygon so we can transform and scale each plane
                        // independently of the others.
                        System.arraycopy(xCoords, 0, xcopy, 0, xCoords.length);
                        System.arraycopy(yCoords, 0, ycopy, 0, yCoords.length);

                        // Create a random offset from (0, 0) for the plane
                        int xAdd = Math.abs(r.nextInt()) % w;
                        int yAdd = Math.abs(r.nextInt()) % h;

                        // Transform the polygon by scaling it (multiplication)
                        // and translating it (addition)
                        for (int i = 0; i < xCoords.length; i++) {
                                xcopy[i] = xcopy[i] * 4 + xAdd;
                                ycopy[i] = ycopy[i] * 4 + yAdd;
                        }

                        // Make the polygon
                        plane = new Polygon(xcopy, ycopy, xCoords.length);

                        // If this is the selected plane, make it red (other planes
                        // will be black because we set the color at the loop start)
                        if (n == selectedIndex) {
                                g2d.setColor(Color.RED);
                        }

                        // Draw the polygon
                        g2d.fillPolygon(plane);

                        // Label the polygon. Log math is to offset the numbers so that
                        // they are still centered even if they have more digits. Not exact
                        // or perfect, but should be good for now.
                        g2d.setColor(Color.LIGHT_GRAY);
                        g2d.drawString(Integer.toString(n), xAdd - 4 - 6*((int)Math.log10(n)), yAdd - 20);      

                        
                  }
    }

        @Override
        public void paintComponent(Graphics g) {
        
                super.paintComponent(g);
                doDrawing(g);
        }

        public void setSelectedIndex(int i) {
                selectedIndex = i;
        }
}