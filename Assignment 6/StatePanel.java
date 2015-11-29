/*
 * Matt Asnes
 * COMP-86 Assignment 6
 *
 * This class is an extension of JPanel which creates a padded and titled
 * panel for control elements. This particular one simply displays information
 * about the game state.
 */

import java.awt.*;
import javax.swing.*;
import java.text.*;

public class StatePanel extends ControlPanel implements GameListener {

        private JLabel planesLeft, planesDestroyed, runtime;
        private DecimalFormat formatter;
        private boolean isPlaying;

        // Make a new panel, where the elements are separated
        // by the given vertical and horizontal gaps. Laid
        // out by FlowLayout. 
        public StatePanel(int hgap, int vgap, String title) {
                super(hgap, vgap, title);

                isPlaying = true;

                planesLeft = new JLabel("Planes Left:");
                planesDestroyed = new JLabel("Planes Destroyed:");
                runtime = new JLabel("Runtime:");

                add(planesLeft);
                add(planesDestroyed);
                add(runtime);

                formatter = new DecimalFormat("#0.000");



        }

        public void updateGame(int planes, int destroyed, double time) {
                if (isPlaying) {
                        planesLeft.setText("Planes Left: " + planes);
                        planesDestroyed.setText("Planes Destroyed: " + destroyed);
                        runtime.setText("Runtime: " + formatter.format(time));
                }
        }

        public void gameEventOccurred(GameEvent e) {
                isPlaying = false;
        }
}