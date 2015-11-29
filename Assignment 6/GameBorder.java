/*
 * Matt Asnes
 * COMP-86 Assignment 6
 *
 * This class is another object which can be drawn on a swing graphics object.
 * It is a simple gray rectangle.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.*;

public class GameBorder {

        private double x, y, w, h;
        private Color c;

        public GameBorder(double x, double y, double w, double h, Color c) {
                this.x = x;
                this.y = y;
                this.w = w;
                this.h = h;
                this.c = c;


        }

        public double getX() {
                return x;
        }

        public double getY() {
                return y;
        }

        public boolean contains(double x, double y) {
                return (this.x < x && this.x + w > x) && (this.y < y && this.y + h > y);
        }

        // Update loop for drawing the border on a graphics object.
        public void draw(Graphics g) {

                Graphics2D g2d = (Graphics2D) g.create();
                g2d.translate(x, y);
                g2d.setColor(c);
                g2d.fillRect(0, 0, (int)w, (int)h);
               

        }

}
