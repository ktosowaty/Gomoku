package action;

import panel.App;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.logging.Logger;

public class BestPlayersAction extends AbstractAction {

    private static final Logger LOGGER = Logger.getLogger(BestPlayersAction.class.getName());

    public BestPlayersAction(String text) {
        super(text);
    }

    public void actionPerformed(ActionEvent e) {
        App.frame.setContentPane(App.bestPlayersPanel);
        App.frame.pack();
    }
}
