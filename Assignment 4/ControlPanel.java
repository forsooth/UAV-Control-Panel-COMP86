/*
 * Matt Asnes
 * COMP-86 Assignment 4
 *
 * This class is an extension of JPanel which creates a padded and titled
 * panel for control elements. It can also enable or disable all filter buttons.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.ArrayList;

public class ControlPanel extends JPanel {

        private ArrayList<JButton> filterButtons;

        // Make a new panel, where the elements are separated
        // by the given vertical and horizontal gaps. Laid
        // out by FlowLayout. 
        public ControlPanel(int hgap, int vgap, String title) {
                setLayout(new FlowLayout(FlowLayout.CENTER, hgap, vgap));

                // The panel is given a compound border with one part invisible
                // padding, and one part titled border.
                TitledBorder border = BorderFactory.createTitledBorder(title);
                border.setTitleJustification(TitledBorder.CENTER);
                CompoundBorder border2 = BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), border);
                setBorder(border2);

                filterButtons = new ArrayList<JButton>();
        }

        // Quick methods for adding different kinds of buttons.
        public void addButton(String s) {
                PrintButton button = new PrintButton(s);
                this.add(button);
        }

        // Add a new button which can change the filter of the map background
        public void addFilterButton(String s, Map map, int val) {
                FilterButton button = new FilterButton(s, map, val);
                this.add(button);
                filterButtons.add(button);
        }

        // Enable all filters
        public void enableFilters() {
                for (JButton x : filterButtons) {
                        x.setEnabled(true);
                }
        }

        // Disable all filters
        public void disableFilters() {
                for (JButton x : filterButtons) {
                        x.setEnabled(false);
                }
        }


}