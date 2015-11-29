/*
 * Matt Asnes
 * COMP-86 Assignment 4
 *
 * This class is a simple button which, given a map and a plane list, will 
 * delete a given plane from both when clicked. The plane to be deleted is
 * just the currently selected plane.
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DeletePlaneButton extends JButton implements ActionListener {

        private String s1;
        Map map;
        PlaneList list;

        public DeletePlaneButton(String s1, Map map, PlaneList list) {
                this.s1 = s1;
                this.map = map;
                this.list = list;

                this.setText(s1);

                addActionListener(this);

        }


        public void actionPerformed(ActionEvent e) {
                map.deleteSelected();
                list.removeSelected();
        }
}