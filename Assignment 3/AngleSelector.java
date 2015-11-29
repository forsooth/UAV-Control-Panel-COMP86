/*
 * Matt Asnes
 * COMP-86 Assignment 3
 *
 * This class is a component allowing the user to select an angle by pointint
 * a line on a circle to that angle.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class AngleSelector extends JPanel implements MouseMotionListener {

        // Angle in radians
        private double angle;
        private int x0, y0, w, h, radius;
        private boolean updated;
        private final int STROKE = 5;

        public AngleSelector(int radius) {
                setPreferredSize(new Dimension(2 * radius, 2 * radius));
                this.radius = radius;
                this.w = 2 * radius;
                this.h = 2 * radius;
                // The center of the circle
                x0 = w / 2;
                y0 = h / 2;
                angle = 0;
                updated = true;
                addMouseMotionListener(this);
        }

        // Get and set the angle of the circle
        public double reportAngle() {
                return angle;
        }

        public void setAngle(double angle) {
                this.angle = angle;
                repaint();
        }

        // Is there an updated angle value?
        public boolean hasUpdate() {
                if (updated) {
                        updated = false;
                        return true;
                }

                return false;
        }

        // Draw the component on the panel
        private void doDrawing(Graphics g) {

                Graphics2D g2d = (Graphics2D) g;

                // Update in case the dimensions of the panel have changed
                Dimension size = getSize();
                w = size.width;
                h = size.height;

                x0 = w / 2;
                y0 = h / 2;

                // Draw a dark circle with a white circle inside it.
                g2d.setColor(Color.DARK_GRAY);
                g2d.fillOval(0, 0, w, h);

                g2d.setColor(Color.WHITE);
                g2d.fillOval(STROKE, STROKE, w - 2 * STROKE, h - 2 * STROKE);

                // Draw the user's selector line.
                g2d.setColor(Color.GREEN);
                g2d.drawLine(x0, y0, x0 + (int)(x0 * Math.cos(angle)), y0 + (int)(y0 * Math.sin(angle)));


        }

        @Override
        public void paintComponent(Graphics g) {
                super.paintComponent(g);
                doDrawing(g);
                updated = true;
        }

        // Update the angle when the mouse is dragged
        public void mousePressed(MouseEvent e) {
        }

        public void mouseDragged(MouseEvent e) {
                double x = e.getX();
                double y = e.getY();
                angle = Math.atan2(y - y0, x - x0);
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