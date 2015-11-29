/*
 * Matt Asnes
 * COMP-86 Assignment 2
 *
 * This class is an extension of JPanel which fills a FlowLayout
 * with evenly spaced buttons. Buttons can be added indefinitely
 * and each button can have a different string. When the buttons
 * are pushed, the string is printed.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class ButtonPanel extends JPanel {

        private ArrayList<JButton> buttons;

        // Make a new panel, where the buttons are separated
        // by the given vertical and horizontal gaps. Laid
        // out by FlowLayout.
        public ButtonPanel(int hgap, int vgap) {
                buttons = new ArrayList<JButton>();
                setLayout(new FlowLayout(FlowLayout.CENTER, hgap, vgap));
        }

        public void addButton(String s) {
                PrintButton button = new PrintButton(s);
                buttons.add(button);
                this.add(button);
        }

}