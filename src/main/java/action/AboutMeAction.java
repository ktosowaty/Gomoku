package action;

import panel.App;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.logging.Logger;

public class AboutMeAction extends AbstractAction {

    private static final Logger LOGGER = Logger.getLogger(AboutMeAction.class.getName());

    public AboutMeAction(String text) {
        super(text);
    }

    public void actionPerformed(ActionEvent e) {
        App.frame.setContentPane(App.aboutMePanel);
        App.frame.pack();
    }
}
