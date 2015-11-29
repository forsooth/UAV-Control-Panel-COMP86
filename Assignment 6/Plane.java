/*
 * Matt Asnes
 * COMP-86 Assignment 6
 *
 * This class is the implementation of planes in the UAV program. It contains 
 * all of the data pertaining to the current state of a given plane object, like
 * position, velocity, color, ID, etc.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.event.*;

public abstract class Plane {

        // Polygon for the plane (left blank in abstraction, to be filled in by subclasses)
        protected Polygon plane;

        // Start ALL planes facing to the right. This is the base angle, which
        // determines how much the plane polygon is rotated by with an angle of
        // 0. In radians.
        protected double angle0 = Math.PI / 2;

        protected double x, y, xv, yv, alt, angle, pitch;
        protected Color color;
        protected int id;
        protected int maxAlt = 80000;

        // The plane needs to know whether or not it is selected, so it can
        // paint itself red
        protected boolean selected;
        protected String label;

        private int canSpawnEnemy = 0;

        public Plane(double x, double y, double angle, String label, int id) {
                this.x = x;
                this.y = y;
                this.angle = angle;
                this.label = label;
                this.id = id;

                xv = 0;
                yv = 0;

                angle = 0;
                alt = 10000;
                pitch = 0;

                selected = false;

        }

        public double getX() {
                return x;
        }

        public double getY() {
                return y;
        }

        public double getSpeed() {
                return Math.sqrt(Math.pow(xv, 2) + Math.pow(yv, 2));
        }

        // Methods for interacting with the data of the plane.
        public void setSpeed(double xv, double yv) {
                this.xv = xv;
                this.yv = yv;
        }

        public void setXSpeed(double xv) {
                this.xv = xv;
        }

        public void setYSpeed(double yv) {
                this.yv = yv;
        }

        public void setPitch(double pitch) {
                this.pitch = pitch;
        }

        public void setAngle(double angle) {
                this.angle = angle;
        }

        public void setColor(Color color) {
                this.color = color;
        }

        public String getLabel() {
                return label;
        }

        public double getAltitude() {
                return alt;
        }

        public int getMaxAlt() {
                return maxAlt;
        }

        public boolean canSpawnEnemy() {
                canSpawnEnemy = canSpawnEnemy % 21;
                return ++canSpawnEnemy > 20;
        }

        // Update loop for drawing the plane on a graphics object.
        public void draw(Graphics g) {

                // Create a new g2d instance for this plane, so our transforms
                // don't carry over to every plane we make.
                Graphics2D g2d = (Graphics2D) g.create();
                // Set the color
                if (selected) {
                        g2d.setColor(Color.RED);
                        selected = false;
                } else {
                        g2d.setColor(color);
                }

                g2d.translate(x, y);
                g2d.rotate(angle + angle0);

                // Draw the polygon
                g2d.fillPolygon(plane);

                g2d.dispose();

                // Make a second g2d object, so the label doesn't get rotated
                // and then paint the label

                g2d = (Graphics2D) g.create();
                g2d.translate(x + 15, y - 15);

                g2d.setColor(Color.BLACK);
                
                g2d.drawString("ID: " + Integer.toString(id), 0, 0);
                g2d.drawString(Integer.toString((int)alt) + "m", 0, 15);

                g2d.dispose();

        }

        public boolean isAt(double x, double y, double scale, double offsetx, double offsety) {
                AffineTransform t = new AffineTransform();
                t.scale(scale, scale);
                t.translate(this.x + offsetx, this.y + offsety);
                t.rotate(angle + angle0);
                Shape plane2 = t.createTransformedShape(plane);
                return plane2.contains(x, y);
        }

        // Add velocity to position and pitch to altitude. The trig is to make
        // a rough simulation of how increasing in altitude will make you go 
        // forward more slowly, if you cap out at a given maximum speed. i.e.
        // going up at 90 degrees means you don't go forward.
        public void tick() {
                x += xv * Math.cos(Math.toRadians(pitch % 90));
                y += yv * Math.cos(Math.toRadians(pitch % 90));
                alt += Math.signum(pitch) * 10 * Math.sqrt(Math.pow(yv * Math.sin(Math.toRadians(pitch % 90)), 2) + Math.pow(xv * Math.sin(Math.toRadians(pitch % 90)), 2));
        }

        public void setSelected() {
                selected = true;
        }

        public void unsetSelected() {
                selected = false;
        }

}