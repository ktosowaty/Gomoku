package customEvent;

import customEventListener.ChangeLanguageListener;

import java.util.Vector;
import java.util.logging.Logger;

public class ChangeLanguageEvent {

    private Vector<ChangeLanguageListener> listeners = new Vector<ChangeLanguageListener>();

    private static final Logger LOGGER = Logger.getLogger(ChangeLanguageEvent.class.getName());

    public void addListener(ChangeLanguageListener listener) {
        listeners.add(listener);
    }

    public void changeLanguageInApp() {
        for(int i=0; i<listeners.size(); i++) {
            listeners.get(i).changeLanguage();
        }
    }

}
