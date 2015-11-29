/*
 * Matt Asnes
 * COMP-86 Assignment 6
 *
 * This class is a wrapper for all data structures used to keep track of the
 * event listeners for the game. When the fireGameEvent method is called, this
 * class will notify all of the listeners.
 */

import java.util.EventListener;
import java.util.EventObject;
import javax.swing.event.EventListenerList;

public class Gamekeeper {
        protected EventListenerList listenerList = new EventListenerList();

        public void addGameListener(GameListener listener) {
                listenerList.add(GameListener.class, listener);
        }

        public void removeGameListener(GameListener listener) {
                listenerList.remove(GameListener.class, listener);
        }
  
        void fireGameEvent(GameEvent e) {
                Object[] listeners = listenerList.getListenerList();
                
                for (int i = 0; i < listeners.length; i = i + 2) {
                        if (listeners[i] == GameListener.class) {
                                ((GameListener) listeners[i+1]).gameEventOccurred(e);
                        }
                }
        }
}