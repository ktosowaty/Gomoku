package customEvent;

import customEventListener.TurnOffBackgroundListener;

import java.util.Vector;
import java.util.logging.Logger;

public class TurnOffBackgroundEvent {

    private Vector<TurnOffBackgroundListener> listeners = new Vector<TurnOffBackgroundListener>();

    private static final Logger LOGGER = Logger.getLogger(TurnOffBackgroundEvent.class.getName());

    public void addListener(TurnOffBackgroundListener listener) {
        listeners.add(listener);
    }

    public void turnOffBackgroundInPanels() {
        for(int i=0; i<listeners.size(); i++) {
            listeners.get(i).turnOffBackgroundInPanel();
        }
    }
}
