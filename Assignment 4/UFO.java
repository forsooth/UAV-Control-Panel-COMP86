/*
 * Matt Asnes
 * COMP-86 Assignment 4
 *
 * This class is an extension of Plane shaped like a UFO that constantly
 * oscillates its color through random shades of green.
 */

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.awt.event.*;
import javax.swing.event.*;

public class UFO extends Plane {

        // Polygon for the plane
        private int xCoords[] = {20, 18, 14, 8, 6, 5, 3, 0, -3, -5, -6, -8, -14, -18, -20};
        private int yCoords[] = { 0,  2,  4, 6, 6, 8, 9, 9,  9,  8,  6,  6,   4,   2,   0};
        Random r;

        public UFO(double x, double y, double angle, String label, int id) {
                super(x, y, angle, label, id);
                r = new Random();
                plane = new Polygon(xCoords, yCoords, xCoords.length);
        }

        public void tick() {
                super.tick();

                int R, G, B;
                R = Math.abs(r.nextInt()) % 31;
                G = Math.abs(r.nextInt()) % 95;
                B = Math.abs(r.nextInt()) % 31;
                color = new Color(R, G, B);
        }

}