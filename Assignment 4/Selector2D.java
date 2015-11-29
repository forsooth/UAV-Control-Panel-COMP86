/*
 * Matt Asnes
 * COMP-86 Assignment 4
 *
 * This class is a 2D cartesian grid element which allows the user to select 
 * an (x, y) coordinate pair with the mouse. 
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class Selector2D extends JPanel implements MouseMotionListener {

        private double x, y;
        private int w, h, radius;
        // Has the user picked a new value?
        private boolean updated;
        private final int STROKE = 5;

        public Selector2D(int w, int h, int radius) {
                setPreferredSize(new Dimension(w, h));
                // radius of the selector point
                this.radius = radius;
                this.w = w;
                this.h = h;
                x = w / 2;
                y = h / 2;
                updated = true;
                addMouseMotionListener(this);
        }

        // Methods to retrieve and set the value of the selector point in the grid

        public double reportX() {
                return x - w / 2;
        }

        public double reportY() {
                return y - h / 2;
        }

        public void setCoords(double xv, double yv) {
                x = xv / 10;
                y = yv / 10;
                repaint();
        }

        public void setXCoord(double xv) {
                x = xv + w / 2;
                repaint();
        }

        public void setYCoord(double yv) {
                y = yv + h / 2;
                repaint();
        }

        // Is there a new value?
        public boolean hasUpdate() {
                if (updated) {
                        updated = false;
                        return true;
                }

                return false;
        }

        private void doDrawing(Graphics g) {

                Graphics2D g2d = (Graphics2D) g;

                Dimension size = getSize();
                w = size.width;
                h = size.height;

                // First, paint a dark gray rectangle. Then paint a white
                // rectangle inside it. Add two axes to the white rectangle, 
                // then draw the two circles that make up the user's selector
                // dot.
                g2d.setColor(Color.DARK_GRAY);
                g2d.fillRect(0, 0, w, h);

                g2d.setColor(Color.WHITE);
                g2d.fillRect(STROKE, STROKE, w - 2 * STROKE, h - 2 * STROKE);

                g2d.setColor(Color.LIGHT_GRAY);
                g2d.drawLine(w / 2, STROKE, w / 2, h - STROKE);
                g2d.drawLine(STROKE, h / 2, w - STROKE, h / 2);

                g2d.setColor(Color.GREEN);
                g2d.setColor(g2d.getColor().darker());
                g2d.fillOval((int)x - radius - 2, (int)y - radius - 2, radius * 2 + 4, radius * 2 + 4);

                g2d.setColor(Color.GREEN);
                g2d.fillOval((int)x - radius, (int)y - radius, radius * 2, radius * 2);

        }

        @Override
        public void paintComponent(Graphics g) {
                super.paintComponent(g);
                doDrawing(g);
                updated = true;
        }


        // When the mouse is dragged, update our x and y coordinates to be
        // drawn on the next paint cycle.
        public void mousePressed(MouseEvent e) {
        }

        public void mouseDragged(MouseEvent e) {
                x = e.getX();
                y = e.getY();
                repaint();
        }

        public void mouseMoved(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseClicked(MouseEvent e) {
        }

}