/*
 * Matt Asnes
 * COMP-86 Assignment 6
 *
 * This class is an extension of EventObject and constitutes the definition of 
 * a game event.
 */

import java.util.EventListener;
import java.util.EventObject;

class GameEvent extends EventObject {

        private int score;
        private boolean lost;

        public GameEvent(Object source, boolean lost, int score) {
                super(source);
                this.lost = lost;
                this.score = score;
        }

        public boolean lostGame() {
                return lost;
        }

        public int getScore() {
                return score;
        }


}



