/*
 * Matt Asnes
 * COMP-86 Assignment 5
 *
 * This class is an extension of the Plane class that is shaped like a regular
 * commercial jet plane, with no special behavior.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;

public class PlainPlane extends Plane {

        // Polygon for the plane
        private int xCoords[] = {  0,   1,   2,   2, 12, 13,  2, 2, 1, -1, -2, -2, -13, -12,  -2,  -2,  -1,   0};
        private int yCoords[] = {-20, -19, -16, -10, -8, -7, -7, -1, 2,  2,  -1, -7,  -7,  -8, -10, -16, -19, -20};

        public PlainPlane(double x, double y, double angle, String label, int id) {
                super(x, y, angle, label, id);
                this.angle0 = 0;
                plane = new Polygon(xCoords, yCoords, xCoords.length);
        }


}