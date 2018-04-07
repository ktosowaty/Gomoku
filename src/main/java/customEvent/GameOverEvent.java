package customEvent;

import customEventListener.GameOverListener;

import java.util.Vector;
import java.util.logging.Logger;

public class GameOverEvent {

    private Vector<GameOverListener> listeners = new Vector<GameOverListener>();

    private static final Logger LOGGER = Logger.getLogger(GameOverEvent.class.getName());

    public void addListener(GameOverListener listener) {
        listeners.add(listener);
    }

    public void updateVariablesInAppAfterGameOver() {
        for(int i=0; i<listeners.size(); i++) {
            listeners.get(i).updateVariablesAfterGameOver();
        }
    }
}
