package customEvent;

import customEventListener.GameStartListener;

import java.util.Vector;
import java.util.logging.Logger;

public class GameStartEvent {

    private Vector<GameStartListener> listeners = new Vector<GameStartListener>();

    private static final Logger LOGGER = Logger.getLogger(GameStartEvent.class.getName());

    public void addListener(GameStartListener listener) {
        listeners.add(listener);
    }

    public void updateVariablesInApp() {
        for(int i=0; i<listeners.size(); i++) {
            listeners.get(i).updateVariables();
        }
    }
}
