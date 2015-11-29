/*
 * Matt Asnes
 * COMP-86 Assignment 6
 *
 * This class is a simple button which pauses and/or plays the main map
 * animation on a toggle.
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AnimationToggleButton extends JButton implements ActionListener {

        private String s1, s2;
        Timer t;
        ControlPanel filters;

        public AnimationToggleButton(String s1, String s2, Timer t, ControlPanel filters) {
                this.s1 = s1;
                this.s2 = s2;
                this.t = t;
                this.filters = filters;

                this.setText(s1);

                addActionListener(this);

        }


        public void actionPerformed(ActionEvent e) {
                // If we are playing the animation, stop it
                if (this.getText().equals(s1)) {
                        System.out.println(s1);
                        this.setText(s2);
                        
                        t.stop();
                        filters.disableFilters();
                // If we have a stopped animation, play it
                } else {
                        System.out.println(s2);
                        this.setText(s1);

                        t.start();
                        filters.enableFilters();
                }
        }
}