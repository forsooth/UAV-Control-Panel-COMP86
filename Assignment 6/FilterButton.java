/*
 * Matt Asnes
 * COMP-86 Assignment 6
 *
 * This class is a button which works just like a PrintButton, except it also
 * hooks into the Map to update the filter of the background noise with a new
 * value.
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FilterButton extends JButton implements ActionListener {

        private String s1;
        private Map map;
        private int val;

        public FilterButton(String s1, Map map, int val) {
                this.s1 = s1;
                this.map = map;
                this.val = val;

                setText(s1);

                addActionListener(this);
        }

         public void actionPerformed(ActionEvent e) {
                System.out.println(s1);
                map.setFilterValue(val);

        }

}