/*
 * Matt Asnes
 * COMP-86 Assignment 2
 *
 * This class is a scrollable list of airplanes for the UAV program.
 * It is built as a JScrollPane surrounding a JList. It takes in a
 * canvas element in order to fill the list with planes, and updates
 * that canvas with a selected plane corresponding to the selected
 * plane in the list via a ListSelectionListener.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.ArrayList;

public class PlaneList extends JScrollPane implements ListSelectionListener {

        private ArrayList<String> planes;
        private JList list;
        private JCanvas canvas;

        public PlaneList(JCanvas canvas) {
                this.canvas = canvas; 

                // For the number of planes on the canvas, add a new plane
                // to the list with the title "Plane x" where x is the index
                // of the plane in the list
                planes = new ArrayList<String>(canvas.NUM_PLANES);
                for(int i = 0; i < canvas.NUM_PLANES; i++) {
                        planes.add("Plane " + Integer.toString(i));
                }

                setPreferredSize(new Dimension(100, 300));

                // Add plane list to swing list component
                // The strange looking array idiom is to fix the type
                // erasure of the arraylist before placing the objects
                // into the JList of strings.
                String names[] = planes.toArray(new String[planes.size()]);
                list = new JList<String>(names);
                list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
                
                // Add the list component to the viewport of this scroll pane, so
                // the list has a scrollbar
                getViewport().add(list);
                // Add a listener to the list so the canvas can be updated
                list.addListSelectionListener(this);
        }

        public void valueChanged (ListSelectionEvent e) {
                // Print out the plane selected
                int i = list.getSelectedIndex();
                System.out.println(planes.get(i) + " selected.");
                // Paint the newly selected plane red
                canvas.setSelectedIndex(i);
                canvas.repaint();
        }

}