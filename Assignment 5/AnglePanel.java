/*
 * Matt Asnes
 * COMP-86 Assignment 5
 *
 * This class is a panel to hold and title the angle selector element, and update
 * the map element with the new angle.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

public class AnglePanel extends JPanel {

        private double angle;
        private AngleSelector selector;
        private Map map;
        private Thread thread;

        // Adds to the panel an angle selector with a titled border around it.
        public AnglePanel(Map map, Dimension size) {
                this.map = map;
                
                TitledBorder border = BorderFactory.createTitledBorder("Angle");
                border.setTitleJustification(TitledBorder.CENTER);
                CompoundBorder border2 = BorderFactory.createCompoundBorder(new EmptyBorder(5, 5, 5, 5), border);
                setBorder(border2);

                setLayout(new FlowLayout(FlowLayout.CENTER, 100, 15));
                Dimension newSize = new Dimension(size.width - 40, 150);
                setPreferredSize(newSize);

                selector = new AngleSelector(50);
 
                this.add(selector);



        }

        public void updateAngle() {
                angle = selector.reportAngle();
                map.setPlaneAngle(angle, map.getSelectedIndex());
                
        }

}