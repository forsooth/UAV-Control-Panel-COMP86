/*
 * Matt Asnes
 * COMP-86 Assignment 4
 *
 * This class is a scrollable list of airplanes for the UAV program.
 * It is built as a JScrollPane surrounding a JList. It takes in a
 * map element in order to fill the list with planes, and updates
 * that map with a selected plane corresponding to the selected
 * plane in the list via a ListSelectionListener. The list is implemented on
 * top of a ListModel, so planes can be added and removed from the list.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.ArrayList;

public class PlaneList extends JScrollPane implements ListSelectionListener {

        private ArrayList<String> planes;
        private JList<String> list;
        private DefaultListModel<String> model;
        private Map map;

        public PlaneList(Map map) {
                this.map = map; 



                // For the number of planes on the map, add a new plane
                // to the list with the title "Plane x" where x is the index
                // of the plane in the list
                planes = new ArrayList<String>(map.getNumPlanes());

                for(int i = 0; i < map.getNumPlanes(); i++) {
                        planes.add("Plane " + map.getLabel(i));
                }

                setPreferredSize(new Dimension(100, 250));

                // Add plane list to swing list component

                model = new DefaultListModel<String>();
                list = new JList<String>();
                list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

                for (String x : planes) {
                        model.addElement(x);
                }

                list.setModel(model);
                
                
                // Add the list component to the viewport of this scroll pane, so
                // the list has a scrollbar
                getViewport().add(list);
                // Add a listener to the list so the map can be updated
                list.addListSelectionListener(this);
        }

        public void removeSelected() {
                if (list.isSelectionEmpty()) 
                        return;
                int i = list.getSelectedIndex();
                model.remove(i);
                list.setSelectedIndex(0);
        }

        public void addPlane(String x) {
                model.addElement(x);
        }

        public void valueChanged (ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) 
                        return;
                if (list.isSelectionEmpty()) 
                        return;
                // Print out the plane selected
                int i = list.getSelectedIndex();
                // Paint the newly selected plane red
                map.setSelectedIndex(i);
                map.repaint();

        }

}