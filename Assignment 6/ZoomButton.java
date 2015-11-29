/*
 * Matt Asnes
 * COMP-86 Assignment 6
 *
 * This class is a button which works just like a PrintButton, except it also
 * hooks into the Map to update the scale at which the map is rendered. 
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ZoomButton extends JButton implements ActionListener {

        private String s1;
        private Map map;
        private boolean in;

        public ZoomButton(String s1, Map map, boolean in) {
                this.s1 = s1;
                this.map = map;
                this.in = in;

                setText(s1);
                addActionListener(this);
        }

         public void actionPerformed(ActionEvent e) {
                if(in) {
                        map.zoomIn();
                } else {
                        map.zoomOut();
                }

        }

}