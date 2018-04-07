package action;

import panel.App;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.logging.Logger;

public class NewGameAction extends AbstractAction {

    private static final Logger LOGGER = Logger.getLogger(NewGameAction.class.getName());

    public NewGameAction(String text) {
        super(text);
    }

    public void actionPerformed(ActionEvent e) {
        App.frame.setContentPane(App.gameOptionsPanel);
        App.frame.pack();
    }
}
