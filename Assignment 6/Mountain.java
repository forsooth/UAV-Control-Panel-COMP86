/*
 * Matt Asnes
 * COMP-86 Assignment 6
 *
 * This class is an object that can be drawn on a graphics canvas. Given an
 * altitude, it draws a logarithmic topographical map of a mountain (constructed
 * from a randomly generated polygon)
 */

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.*;

public class Mountain {

        private double x, y, alt;
        private int radius;
        private Polygon poly;
        private Random r;

        public Mountain(double x, double y, double alt, int radius) {
                this.x = x;
                this.y = y;
                this.alt = alt;

                r = new Random();

                int sides = r.nextInt(25) + 5;
                this.radius = radius;
                generatePolygon(sides, radius);
        }

        public double getX() {
                return x;
        }

        public double getY() {
                return y;
        }

        public boolean contains(double x, double y, double alt) {
                
                if (alt > this.alt) return false;

                AffineTransform t = new AffineTransform();
                t.translate(this.x, this.y);

                // Find the level at which the mountain becomes a danger to the plane
                Shape poly = this.poly;
                int level = (int) Math.log10(alt);
                double totalLevels = Math.log10(this.alt);

                double scale = 1 - level * 1.0 / totalLevels;
                t.scale(scale, scale);

                poly = t.createTransformedShape(poly);

                return poly.contains(x, y);
        }

        // Update loop for drawing on a graphics object.
        public void draw(Graphics g) {

                double scale = 1.00;
                double iterations = (double) Math.log10(alt);

                for (double i = alt; i > 1; i /= 10) {
                        Graphics2D g2d = (Graphics2D) g.create();
                        g2d.translate(x, y);
                        g2d.setColor(new Color(150, 75, 0));
                        g2d.scale(scale, scale);
                        scale -= 1.0 / iterations;
                        g2d.drawPolygon(poly);
                        g2d.dispose();

                }

                Graphics2D g2d = (Graphics2D) g.create();
                g2d.translate(x, y);
                g2d.setColor(Color.BLACK);

                double j = alt / Math.pow(10, (int) iterations);
                for (int i = radius; i > 0; i -= radius / iterations, j *= 10) {
                        g2d.drawString((int)j + "m", 0, i);
                }

        }

        // Create a new mountain-shaped polygon by making a set of points
        // positioned around a circle, but pushing each point out or in by
        // a random number thereby making a non-uniform, not-necessarily convex
        // shape while keeping a large area and avoiding any self-intersections.
        private void generatePolygon(int sides, int radius) {

                AffineTransform trans = new AffineTransform();
                Polygon poly = new Polygon();
                for (int i = 0; i < sides; i++) {
                        trans.rotate(Math.PI / (float) sides);
                        Point2D out = trans.transform(new Point2D.Double(0, radius + radius / 5 * r.nextGaussian()), null);
                        poly.addPoint((int) out.getX(), (int) out.getY());
                        trans.rotate(Math.PI / (float) sides);

                }

                this.poly = poly;
        }
}
