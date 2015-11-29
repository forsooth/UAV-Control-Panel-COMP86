/*
 * Matt Asnes
 * COMP-86 Assignment 6
 *
 * This class is an object which can be painted onto a swing graphics canvas.
 * it draws a red rectangle covered in diagonal red stripes, like:
 *   ---------------
 *  |/ / / / / / / /|
 *  | / / / / / / / |
 *  |/ / / / / / / /|
 *  | / / / / / / / |
 *   ---------------
 */

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.event.*;

public class NoFlyZone {

        private double x, y, w, h;

        // Distance between lines in the no fly zone shape
        private final static int dist = 5; 

        public NoFlyZone(double x, double y, double w, double h) {
                this.x = x;
                this.y = y;
                this.w = w;
                this.h = h;
        }

        public double getX() {
                return x;
        }

        public double getY() {
                return y;
        }

        public double getWidth() {
                return w;
        }

        public double getHeight() {
                return h;
        }

        public boolean contains(double x, double y) {
                return (this.x < x && this.x + w > x) && (this.y < y && this.y + h > y);
        }

        // Update loop for drawing the plane on a graphics object.
        public void draw(Graphics g) {

                // Create a new g2d instance for this plane, so our transforms
                // don't carry over to every plane we make.
                Graphics2D g2d = (Graphics2D) g.create();

                g2d.setColor(Color.RED);

                g2d.translate(x, y);

                int x = (int) this.x;
                int y = (int) this.y;
                int w = (int) this.w;
                int h = (int) this.h;

                int tempx = w;
                int tempy = h;
                int tempw = w - dist;
                int temph = h - dist;

                // Draw an outline of a red rectangle
                g2d.drawRect(0, 0, w, h);


                // fill the retangle with diagonal lines
                while (true) {

                        if (tempw <= 0 || temph <= 0) {
                                break;
                        }

                        if (tempx >= 0 && tempy >= 0) {
                                g2d.drawLine(tempx, h, w, tempy);
                                tempx -= dist;
                                tempy -= dist;
                                continue;
                        }

                        if (tempx >= 0 && tempy < 0) {
                                g2d.drawLine(tempx, h, tempw, 0);
                                tempw -= dist;
                                tempx -= dist;
                                continue;
                        }

                        if (tempx < 0 && tempy >= 0) {
                                g2d.drawLine(0, temph, w, tempy);
                                temph -= dist;
                                tempy -= dist;
                                continue;
                        } 

                        if (tempx < 0 && tempy < 0) {
                                g2d.drawLine(0, temph, tempw, 0);
                                temph -= dist;
                                tempw -=  dist;
                                continue;
                        }
                }


                g2d.dispose();

        }

}