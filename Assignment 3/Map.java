/*
 * Matt Asnes
 * COMP-86 Assignment 3
 *
 * This class works as a canvas element for Swing to make a map, built on top
 * of JPanel. It creates a series of planes, and updates them on the map in
 * a drawing loop thread. It also allows limited access to the data values in
 * the planes. 
 */

import java.awt.*;
import java.awt.font.*;
import java.util.Random;
import javax.swing.*;
import java.util.ArrayList;

public class Map extends JPanel implements Runnable {

        private int numPlanes;
        private int w, h;
        private int filterVal;
        Thread thread;
        private ArrayList<Plane> planes;
        // The index of the currently active plane
        private int selectedIndex = 0;

        public Map(int numPlanes) {
                this.numPlanes = numPlanes;
                filterVal = 150;
                planes = new ArrayList<Plane>();

                // Make a bunch of planes evenly spaced out on the map
                for (int i = 0; i < numPlanes; i++) {
                        PlainPlane p = new PlainPlane(300 + 45 * i, 400, 0, Integer.toString(i), i);
                        UFO ufo = new UFO(300 + 45 * i, 300, 0, Integer.toString(numPlanes + i), numPlanes + i);
                        planes.add(p);
                        planes.add(ufo);
                }

                Thread thread = new Thread(this);
                thread.start();
        }
        
        // Store a font for numbering the planes
        private final Font font = new Font("SansSerif", Font.PLAIN, 16);

        private void doDrawing(Graphics g) {

                Graphics2D g2d = (Graphics2D) g;

                Dimension size = getSize();

                w = size.width;
                h = size.height;

                Random r = new Random();

                // Fill the background with random noise. Simple probability
                // math says that on average half of the canvas will be filled with
                // a random color.
                for (int i = 0; i <= w * h; i++) {
                        // Grab a new point at a random position
                        int x = Math.abs(r.nextInt()) % w;
                        int y = Math.abs(r.nextInt()) % h;
                        // Make a random color brighter than rgb(150, 150, 150)
                        int R, G, B;
                        R = Math.abs(r.nextInt()) % (255 - filterVal) + filterVal;
                        G = Math.abs(r.nextInt()) % (255 - filterVal) + filterVal;
                        B = Math.abs(r.nextInt()) % (255 - filterVal) + filterVal;
                        // Draw a line from (x,y) to (x,y), i.e. one point
                        g2d.setColor(new Color(R, G, B));
                        g2d.drawLine(x, y, x, y);
                }

                // We want black airplanes labeled with our font.
                g2d.setColor(Color.BLACK);
                g2d.setFont(font); 

                // Draw the airplane polygon NUM_PLANES times, at random coordinates
                for(int i = 0; i < numPlanes; i++) {
                        if (i != selectedIndex) {
                                planes.get(i).setColor(Color.BLACK);
                                planes.get(i).draw(g);
                        }
                }

                planes.get(selectedIndex).setColor(Color.RED);
                planes.get(selectedIndex).draw(g2d);
        }

        @Override
        public void paintComponent(Graphics g) {
        
                super.paintComponent(g);
                doDrawing(g);
        }

        // Get and set values

        public void setSelectedIndex(int i) {
                selectedIndex = i;
        }

        public int getSelectedIndex() {
                return selectedIndex;
        }

        public String getLabel(int i) {
                return planes.get(i).getLabel();
        }

        public int getNumPlanes() {
                return numPlanes;
        }

        public void setFilterValue(int val) {
                filterVal = val;
        }

        // Get and set information about individual planes

        public void setPlaneVelocity(double xv, double yv, int planeIndex) {
                planes.get(planeIndex).setSpeed(xv, yv);
        }

        public void setPlaneXVelocity(double xv, int planeIndex) {
                planes.get(planeIndex).setXSpeed(xv);
        }

        public void setPlaneYVelocity(double yv, int planeIndex) {
                planes.get(planeIndex).setYSpeed(yv);
        }

        public void setPlanePitch(double pitch, int planeIndex) {
                planes.get(planeIndex).setPitch(pitch);
        }

        public void setPlaneAngle(double angle, int planeIndex) {
                planes.get(planeIndex).setAngle(angle);
        }

        public void run() {
                while (true) {
                        repaint();
                        try {

                                Thread.sleep(500);
                        } catch (Exception ex) {}
                }
        }
}