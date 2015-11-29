/*
 * Matt Asnes
 * COMP-86 Assignment 4
 *
 * This class is a simple button which, given a map and a list of planes, opens
 * the plane creation dialog window. This dialog window creates a new plane.
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class AddPlaneButton extends JButton implements ActionListener {

        private String s1;
        Map map;
        NewPlaneDialog dia;
        PlaneList list;

        public AddPlaneButton(String s1, Map map, PlaneList list) {
                this.s1 = s1;
                this.map = map;
                this.list = list;

                this.setText(s1);

                addActionListener(this);
                dia = new NewPlaneDialog(map, list);

        }


        public void actionPerformed(ActionEvent e) {
                dia.setVisible(true);
        }
}