/*
 * Matt Asnes
 * COMP-86 Assignment 5
 *
 * This class is a panel to store and update the pitch (change in altitude) of
 * the selected plane.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.util.ArrayList;
import javax.swing.text.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.text.*;

public class PitchPanel extends JPanel implements ChangeListener, PropertyChangeListener {

        private double pitch;
        private JFormattedTextField pitchText;
        private NumberFormat format;
        private JPanel pitchPanel;
        private JSlider slider;
        private JLabel pitchLabel;
        private Map map;
        private Thread thread;

        // Make a new panel holding a slider for pitch, with a titled border
        // and a text box for accurate data entry.
        public PitchPanel(Map map, Dimension size) {
                this.map = map;

                TitledBorder border = BorderFactory.createTitledBorder("Pitch");
                border.setTitleJustification(TitledBorder.CENTER);
                CompoundBorder border2 = BorderFactory.createCompoundBorder(new EmptyBorder(5, 5, 5, 5), border);
                setBorder(border2);

                setLayout(new FlowLayout(FlowLayout.CENTER, 100, 15));
                Dimension newSize = new Dimension(size.width - 40, 125);
                setPreferredSize(newSize);

                pitchPanel = new JPanel();

                slider = new JSlider(JSlider.HORIZONTAL, -89, 89, 0);
                slider.setPreferredSize(new Dimension(125, 25));
                slider.addChangeListener(this);

                format = NumberFormat.getNumberInstance();

                pitchText = new JFormattedTextField(format);
                pitchText.setPreferredSize(new Dimension(65, 25));
                pitchText.setHorizontalAlignment(SwingConstants.RIGHT);
                pitchText.setValue(new Double(0.00));
                pitchText.setColumns(5);
                pitchText.addPropertyChangeListener("value", this);

                JLabel pitchTextLabel = new JLabel("Pitch:");

                pitchPanel.add(pitchTextLabel);
                pitchPanel.add(pitchText);

                this.add(slider);
                this.add(pitchPanel);


        }

        public void updatePitch() {
                map.setPlanePitch(pitch, map.getSelectedIndex());
                
        }

        // slider listener
        public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider)e.getSource();

                pitch = (int)source.getValue();
                pitchText.setValue(pitch);

        }

        // text box listener
        public void propertyChange(PropertyChangeEvent e) {

                Object source = e.getSource();
                double p;

                p = ((Number)pitchText.getValue()).doubleValue();
                slider.setValue((int)p);
                
        }



}