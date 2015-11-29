/*
 * Matt Asnes
 * COMP-86 Assignment 6
 *
 * This class is an extension of Plane shaped like a fighter jet that cannot
 * be controlled by the player but instead tracks down a player plane.
 */

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.awt.event.*;
import javax.swing.event.*;

public class Enemy extends Plane {

        // Polygon for the plane
        private int xCoords[] = { 0,  5, 10, 13, 15, 17,  50,  53,  15,  35,  36,  10,   7,   3,  -3,  -7, -10, -36, -35, -15, -53, -50, -17, -15, -13, -10, -5};
        private int yCoords[] = {80, 60, 35, 40, 35, 10, -15, -30, -40, -55, -63, -70, -48, -54, -54, -48, -70, -63, -55, -40, -30, -15,  10,  35,  40,  35, 60};
        private int velocity = 15;
        private Plane target;
        Random r;

        public Enemy(Plane target, int x, int y) {
                super(x, y, 0, "", -1);
                angle0 = Math.PI;
                this.target = target;
                alt = 1000;
                maxAlt = 200000;
                r = new Random();
                plane = new Polygon(xCoords, yCoords, xCoords.length);
        }

        // Update loop for drawing the plane on a graphics object.
        public void draw(Graphics g) {

                // Create a new g2d instance for this plane, so our transforms
                // don't carry over to every plane we make.
                Graphics2D g2d = (Graphics2D) g.create();

                g2d.setColor(color);

                g2d.translate(x, y);
                g2d.rotate(angle + angle0);
                g2d.scale(0.2, 0.2);

                // Draw the polygon
                g2d.fillPolygon(plane);

                g2d.dispose();

        }

        public boolean targetEquals(Plane target) {
                return target == this.target;
        }

        public void tick() {

                double targetAngle = Math.atan2(y - target.getY(), x - target.getX());

                x -= 15 * Math.cos(targetAngle);
                y -= 15 * Math.sin(targetAngle);

                int R, G, B;
                R = Math.abs(r.nextInt()) % 155 + 100;
                G = Math.abs(r.nextInt()) % 50;
                B = Math.abs(r.nextInt()) % 50;
                color = new Color(R, G, B);
        }

}