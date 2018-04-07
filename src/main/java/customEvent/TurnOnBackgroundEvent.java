package customEvent;

import customEventListener.TurnOnBackgroundListener;

import java.util.Vector;
import java.util.logging.Logger;

public class TurnOnBackgroundEvent {

    private Vector<TurnOnBackgroundListener> listeners = new Vector<TurnOnBackgroundListener>();

    private static final Logger LOGGER = Logger.getLogger(TurnOnBackgroundEvent.class.getName());

    public void addListener(TurnOnBackgroundListener listener) {
        listeners.add(listener);
    }

    public void turnOnBackgroundInPanels() {
        for(int i=0; i<listeners.size(); i++) {
            listeners.get(i).turnOnBackgroundInPanel();
        }
    }
}
