/*
 * Matt Asnes
 * COMP-86 Assignment 6
 *
 * This class is a panel which creates a 2D selector tool to pick a velocity
 * and updates the map with that velocity. It also hooks up two text boxes
 * to the velocity for more accurate positioning.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.ArrayList;
import javax.swing.text.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.text.*;

public class VelocityPanel extends JPanel implements PropertyChangeListener {

        private double vx, vy;
        private Selector2D velocity;
        private JFormattedTextField xText, yText;
        private NumberFormat format;
        private JPanel xTextPanel, yTextPanel;
        private JLabel xTextLabel, yTextLabel;
        private Map map;
        private Thread thread;

        // Make a new titled panel, add the selector and the two text boxes to it.
        // Each text box is actually its own small panel so the boxes and their
        // labels can be easily formatted. We use JFormattedTextFields so that
        // they can be made to only store doubles.
        public VelocityPanel(Map map, Dimension size) {
                this.map = map;
                velocity = new Selector2D(125, 125, 5);
                this.add(velocity);

                TitledBorder border = BorderFactory.createTitledBorder("Velocity");
                border.setTitleJustification(TitledBorder.CENTER);
                CompoundBorder border2 = BorderFactory.createCompoundBorder(new EmptyBorder(5, 5, 5, 5), border);
                setBorder(border2);

                setLayout(new FlowLayout(FlowLayout.CENTER, 100, 15));
                Dimension newSize = new Dimension(size.width - 40, 275);
                setPreferredSize(newSize);

                xTextPanel = new JPanel();
                yTextPanel = new JPanel();

                format = NumberFormat.getNumberInstance();

                xText = new JFormattedTextField(format);
                xText.setPreferredSize(new Dimension(65, 25));
                xText.setHorizontalAlignment(SwingConstants.RIGHT);
                xText.setValue(new Double(0.00));
                xText.setColumns(5);
                xText.addPropertyChangeListener("value", this);

                yText = new JFormattedTextField(format);
                yText.setPreferredSize(new Dimension(65, 25));
                yText.setHorizontalAlignment(SwingConstants.RIGHT);
                yText.setValue(new Double(0.00));
                yText.setColumns(5);
                yText.addPropertyChangeListener("value", this);

                xTextLabel = new JLabel("X Velocity:");
                yTextLabel = new JLabel("Y Velocity:");


                xTextPanel.add(xTextLabel);
                xTextPanel.add(xText);

                yTextPanel.add(yTextLabel);
                yTextPanel.add(yText);

                this.add(xTextPanel);
                this.add(yTextPanel);


        }
 
        public void updateVelocity() {
                // The conversion between pixels on the selector and the velocity in
                // the simulation was arbitrarily chosen to be 10.
                vx = velocity.reportX() / 10;
                vy = velocity.reportY() / 10;
                map.setPlaneVelocity(vx, vy, map.getSelectedIndex());
                
                try {
                        xText.setValue(new Double(vx * 10));
                        yText.setValue(new Double(vy * 10));
                } catch (Exception e) {

                }
        }

        public boolean hasUpdate() {
                return velocity.hasUpdate();
        }

        public void propertyChange(PropertyChangeEvent e) {

                Object source = e.getSource();
                double vel;

                if (source == xText) {
                        vel = ((Number)xText.getValue()).doubleValue();
                        velocity.setXCoord(vel);
                } else if (source == yText) {
                        vel = ((Number)yText.getValue()).doubleValue();
                        velocity.setYCoord(vel);
                }
                
        }



}