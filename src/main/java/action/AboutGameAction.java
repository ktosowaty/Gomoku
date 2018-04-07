package action;

import panel.App;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.logging.Logger;

public class AboutGameAction extends AbstractAction {

    private static final Logger LOGGER = Logger.getLogger(AboutGameAction.class.getName());

    public AboutGameAction(String text) {
        super(text);
    }

    public void actionPerformed(ActionEvent e) {
        App.frame.setContentPane(App.gameInfoPanel);
        App.frame.pack();
    }
}
