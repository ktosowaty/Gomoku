package panel;

import customEventListener.ChangeLanguageListener;
import utility.ReadApplicationProperties;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainMenuPanel extends JPanel implements ChangeLanguageListener {

    private BufferedImage img = null;
    private GridBagConstraints c;
    private JButton newGameButton, bestPlayers, gameInfo, aboutMe;
    private int buttonsWidth, buttonsHeight;

    private static final Logger LOGGER = Logger.getLogger(MainMenuPanel.class.getName());

    public MainMenuPanel() {
        super(new GridBagLayout());
        readProperties();
        loadImage();
        c = new GridBagConstraints();
        c.insets = new Insets(20, 0, 20, 0);
        c.gridwidth = 1;
        c.gridheight = 1;
        initializeButtons();
    }

    private void loadImage() {
        try {
            img = ImageIO.read(new File("images/goImage.png"));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, null);
    }

    private void initializeButtons() {
        newGameButton = new JButton(App.messages.getString("MainMenuPanel_newGameButton"));
        newGameButton.setPreferredSize(new Dimension(buttonsWidth, buttonsHeight));
        newGameButton.setAction(MenuBarManagement.newGameAction);
        newGameButton.setVisible(true);
        c.gridx = 0;
        c.gridy = 0;
        add(newGameButton, c);
        bestPlayers = new JButton(App.messages.getString("MainMenuPanel_bestPlayers"));
        bestPlayers.setPreferredSize(new Dimension(buttonsWidth, buttonsHeight));
        bestPlayers.setAction(MenuBarManagement.bestPlayersAction);
        bestPlayers.setVisible(true);
        c.gridx = 0;
        c.gridy = 1;
        add(bestPlayers, c);
        gameInfo = new JButton(App.messages.getString("MainMenuPanel_gameInfo"));
        gameInfo.setPreferredSize(new Dimension(buttonsWidth, buttonsHeight));
        gameInfo.setAction(MenuBarManagement.aboutGameAction);
        gameInfo.setVisible(true);
        c.gridx = 0;
        c.gridy = 2;
        add(gameInfo, c);
        aboutMe = new JButton(App.messages.getString("MainMenuPanel_aboutMe"));
        aboutMe.setPreferredSize(new Dimension(buttonsWidth, buttonsHeight));
        aboutMe.setAction(MenuBarManagement.aboutMeAction);
        aboutMe.setVisible(true);
        c.gridx = 0;
        c.gridy = 3;
        add(aboutMe, c);
    }

    public void changeLanguage() {
        newGameButton.setText(App.messages.getString("MainMenuPanel_newGameButton"));
        bestPlayers.setText(App.messages.getString("MainMenuPanel_bestPlayers"));
        gameInfo.setText(App.messages.getString("MainMenuPanel_gameInfo"));
        aboutMe.setText(App.messages.getString("MainMenuPanel_aboutMe"));
    }

    private void readProperties() {
        buttonsWidth = Integer.parseInt(ReadApplicationProperties.appProperties.getProperty("MainMenuPanel_buttonsWidth"));
        buttonsHeight = Integer.parseInt(ReadApplicationProperties.appProperties.getProperty("MainMenuPanel_buttonsHeight"));
    }
}
