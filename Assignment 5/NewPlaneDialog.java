/*
 * Matt Asnes
 * COMP-86 Assignment 5
 *
 * This class is a JFrame which functions as the dialog box for the creation
 * of a new plane. It has text boxes for the x and y position of the plane, as
 * well as mutually exclusive radio buttons for the type of plane to be created.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Enumeration;
import java.text.*;
 
public class NewPlaneDialog extends JFrame implements ActionListener {
    private JPanel main, xPanel, yPanel, radioPanel, buttonPanel;
    private JFormattedTextField xText, yText;
    private ButtonGroup bg;
    private JLabel x, y;
    private Map map;
    private PlaneList list;
 
    /** Creates the reusable dialog. */
        public NewPlaneDialog(Map map, PlaneList list) {
                this.map = map;
                this.list = list;

                setTitle("Create New Plane");
                main = new JPanel();
                add(main);
                main.setPreferredSize(new Dimension(300, 150));

                xPanel = new JPanel();
                x = new JLabel("X: ");
                xText = new JFormattedTextField(100);
                xText.setPreferredSize(new Dimension(50, 25));
                xPanel.add(x);
                xPanel.add(xText);

                yPanel = new JPanel();
                y = new JLabel("Y: ");
                yText = new JFormattedTextField(100);
                yText.setPreferredSize(new Dimension(50, 25));
                yPanel.add(y);
                yPanel.add(yText);

                main.add(xPanel);
                main.add(yPanel);

                radioPanel = new JPanel();
                // Set to selected by default
                JRadioButton plain = new JRadioButton("Plain");
                JRadioButton ufo = new JRadioButton("UFO");
                plain.doClick();
                bg = new ButtonGroup();
                bg.add(plain);
                bg.add(ufo);

                radioPanel.add(plain);
                radioPanel.add(ufo);
                radioPanel.setPreferredSize(new Dimension(200, 35));

                main.add(radioPanel);

                buttonPanel = new JPanel();
                JButton ok = new JButton("OK");
                ok.addActionListener(this);
                JButton cancel = new JButton("Cancel");
                cancel.addActionListener(new ActionListener(){
                        public void actionPerformed(ActionEvent e) {
                                NewPlaneDialog.this.setVisible(false);
                        }
                });
                buttonPanel.add(ok);
                buttonPanel.add(cancel);
                main.add(buttonPanel);
 
                //Handle window closing correctly.
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

                addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                                NewPlaneDialog.this.setVisible(false);
                        }
                });

                pack();
 
        }
 
        /** This method handles events for the text field. */
        public void actionPerformed(ActionEvent e) {
                int x = (int)xText.getValue();
                int y = (int)yText.getValue();
                String type = getSelectedButtonText(bg);

                map.addPlane(x, y, type);
                list.addPlane("Plane " + (map.getNumPlanes() - 1));

        }

        // Return the text of the selected button in the button group.
        // from http://stackoverflow.com/a/13232816/4984250
        public String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return button.getText();
            }
        }

        return null;
    }
}