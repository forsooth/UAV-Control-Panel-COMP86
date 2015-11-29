/*
 * Matt Asnes
 * COMP-86 Assignment 6
 *
 * This is a simple empty interface for the custom listener used in the game
 */

import java.util.EventListener;
import java.util.EventObject;

interface GameListener extends EventListener {
        public void gameEventOccurred(GameEvent e);
}