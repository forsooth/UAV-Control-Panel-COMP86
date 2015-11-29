/*
 * Matt Asnes
 * COMP-86 Assignment 5
 *
 * This class works as a canvas element for Swing to make a map, built on top
 * of JPanel. It creates a series of planes, and updates them on the map in
 * a drawing loop thread. It also allows limited access to the data values in
 * the planes. Planes can be created or destroyed on the map.
 */

import java.awt.*;
import java.awt.font.*;
import java.util.Random;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class Map extends JPanel implements MouseListener {

        private int numPlanes;
        private int w, h;
        private int filterVal;
        private double scale, offsetx, offsety;
        private ArrayList<Plane> planes;
        // The index of the currently active plane
        private int selectedIndex = 0;

        public Map (int numPlanes) {
                this.numPlanes = numPlanes;
                scale = 1.0;
                filterVal = 150;
                offsetx = 0;
                offsety = 0;
                planes = new ArrayList<Plane>();

                // Make a bunch of planes evenly spaced out on the map
                for (int i = 0; i < numPlanes - 6; i++) {
                        PlainPlane p = new PlainPlane(-200 + 40 * i, -100, 0, Integer.toString(i), i);
                        planes.add(p);
                }

                for (int i = numPlanes - 6; i < numPlanes; i++) {
                        UFO ufo = new UFO(-600 + 40 * i, -200, 0, Integer.toString(i), i);
                        planes.add(ufo);
                }

                addMouseListener(this);

        }
        
        // Store a font for numbering the planes
        private final Font font = new Font("SansSerif", Font.PLAIN, 16);

        private void doDrawing(Graphics g) {

                Graphics2D g2d = (Graphics2D) g;

                Dimension size = getSize();

                w = size.width;
                h = size.height;
                
                // Protect against the case where there are no planes
                if (planes.size() > selectedIndex) {
                        offsetx = w / 2 / scale - planes.get(selectedIndex).getX();
                        offsety = h / 2 / scale - planes.get(selectedIndex).getY();
                }

                Random r = new Random();

                // Fill the background with random noise. Simple probability
                // math says that on average 1/8 of the canvas will be filled with
                // a random color.
                for (int i = 0; i <= 0.25 * w * h; i++) {
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

                // Real borders get messed up by zoom, so we make our own
                g2d.setColor(Color.DARK_GRAY);
                g2d.fillRect(0, 0, 5, h);
                g2d.fillRect(0, 0, w, 5);
                g2d.fillRect(w - 5, 0, 5, h);
                g2d.fillRect(0, h - 5, w, 5);

                // If there are no planes, don't bother with the rest of drawing
                if (planes.size() <= selectedIndex) return;


                // We want black airplanes labeled with our font.
                g2d.setColor(Color.BLACK);
                g2d.setFont(font);

                g2d.scale(scale, scale);
                g2d.translate(offsetx, offsety);

                // Draw the airplane polygon NUM_PLANES times, at random coordinates
                for(int i = 0; i < numPlanes; i++) {
                        if (i != selectedIndex) {
                                planes.get(i).draw(g);
                        }
                }

                planes.get(selectedIndex).setSelected();
                planes.get(selectedIndex).draw(g);
        }

        public void tickAll(PlaneList list) {
                for(int i = 0; i < numPlanes; i++) {
                        planes.get(i).tick();
                        double x = planes.get(i).getX();
                        double y = planes.get(i).getY();
                        for(int j = 0; j < numPlanes; j++) {
                                
                                // offset and scale are default because we just want to compare the coordinates
                                if (j != i && planes.get(j).isAt(x, y, 1.0, 0, 0)) {

                                        // Compare the altitudes, break if no collision
                                        double dAlt = planes.get(j).getAltitude() - planes.get(i).getAltitude();
                                        if (Math.abs(dAlt) > 10) break;

                                        // There was a collision! Delete the planes
                                        System.out.println("Collision!");
                                        if (j > i) {
                                                // Order of removals is important!
                                                planes.remove(j);
                                                planes.remove(i);
                                                list.remove(j);
                                                list.remove(i);
                                                selectedIndex = 0;
                                                numPlanes -= 2;
                                                break;
                                        } else {
                                                planes.remove(i);
                                                planes.remove(j);
                                                list.remove(i);
                                                list.remove(j);
                                                selectedIndex = 0;
                                                numPlanes -= 2;
                                                break;
                                        }
                                }
                        }                
                }
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
                if (i >= planes.size()) return "";
                return planes.get(i).getLabel();
        }

        public int getNumPlanes() {
                return numPlanes;
        }

        // Delete the currently selected plane
        public void deleteSelected() {
                planes.remove(selectedIndex);
                numPlanes--;
        }

        // Add a new plane at the given coordinates
        public void addPlane(int x, int y, String type) {

                if (type.equals("Plain")) {
                        PlainPlane newPlane = new PlainPlane(x, y, 0, Integer.toString(numPlanes), numPlanes);
                        planes.add(newPlane);
                        numPlanes++;
                } else {
                        UFO newPlane = new UFO(x, y, 0, Integer.toString(numPlanes), numPlanes);
                        planes.add(newPlane);
                        numPlanes++;
                }

        }

        public void setFilterValue(int val) {
                filterVal = val;
        }

        // Get and set information about individual planes

        public void setPlaneVelocity(double xv, double yv, int planeIndex) {
                if (planeIndex >= planes.size()) return;
                planes.get(planeIndex).setSpeed(xv, yv);
        }

        public void setPlaneXVelocity(double xv, int planeIndex) {
                if (planeIndex >= planes.size()) return;
                planes.get(planeIndex).setXSpeed(xv);
        }

        public void setPlaneYVelocity(double yv, int planeIndex) {
                if (planeIndex >= planes.size()) return;
                planes.get(planeIndex).setYSpeed(yv);
        }

        public void setPlanePitch(double pitch, int planeIndex) {
                if (planeIndex >= planes.size()) return;
                planes.get(planeIndex).setPitch(pitch);
        }

        public void setPlaneAngle(double angle, int planeIndex) {
                if (planeIndex >= planes.size()) return;
                planes.get(planeIndex).setAngle(angle);
        }

        // Zoom commands
        public void zoomIn() {
                scale *= 1.5;
        }

        public void zoomOut() {
                scale /= 1.5;
        }

        // Select a new plane if one is clicked
        public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                for(int i = 0; i < numPlanes; i++) {
                        if(planes.get(i).isAt(x, y, scale, offsetx, offsety)) {
                                planes.get(selectedIndex).unsetSelected();
                                selectedIndex = i;
                                planes.get(i).setSelected();
                        }
                }
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mouseClicked(MouseEvent e) {
        }
}