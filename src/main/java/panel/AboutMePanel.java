package panel;

import customEventListener.ChangeLanguageListener;
import customEventListener.TurnOffBackgroundListener;
import customEventListener.TurnOnBackgroundListener;
import utility.MyTextArea;
import utility.ReadApplicationProperties;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AboutMePanel extends JPanel implements ActionListener, ChangeLanguageListener, TurnOffBackgroundListener, TurnOnBackgroundListener {

    private BufferedImage img = null;
    private JButton goBackToMenu;
    private JTextArea textArea;
    private int goBackToMenuButtonWidth, goBackToMenuButtonHeight, textAreaWidth, textAreaHeight, textAreaFontSize;
    private boolean background = true;

    private static final Logger LOGGER = Logger.getLogger(AboutMePanel.class.getName());

    public AboutMePanel() {
        super(new GridBagLayout());
        readProperties();
        loadImage();
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = 1;
        c.gridheight = 1;
        goBackToMenu = new JButton(App.messages.getString("AboutMePanel_goBackToMenu"));
        goBackToMenu.setPreferredSize(new Dimension(goBackToMenuButtonWidth, goBackToMenuButtonHeight));
        goBackToMenu.addActionListener(this);
        goBackToMenu.setVisible(true);
        c.gridx = 0;
        c.gridy = 0;
        add(goBackToMenu, c);
        textArea = new MyTextArea();
        textArea.setBackground(new Color(1,1,1, (float) 0.01));
        textArea.setPreferredSize(new Dimension(textAreaWidth, textAreaHeight));
        textArea.setForeground(Color.YELLOW);
        textArea.setFont(new Font("Arial", Font.PLAIN, textAreaFontSize));
        textArea.setText(App.messages.getString("AboutMePanel_aboutMe"));
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        c.gridx = 0;
        c.gridy = 1;
        add(textArea, c);
    }

    private void loadImage() {
        try {
            img = ImageIO.read(new File("images/gray.jpg"));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(background) g.drawImage(img, 0, 0, null);
        else g.drawImage(null, 0, 0, null);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==goBackToMenu) {
            App.frame.setContentPane(App.menuPanel);
            App.frame.pack();
        }
    }

    public void changeLanguage() {
        goBackToMenu.setText(App.messages.getString("AboutMePanel_goBackToMenu"));
        textArea.setText(App.messages.getString("AboutMePanel_aboutMe"));
    }

    private void readProperties() {
        goBackToMenuButtonWidth = Integer.parseInt(ReadApplicationProperties.appProperties.getProperty("AboutMePanel_goBackToMenuButtonWidth"));
        goBackToMenuButtonHeight = Integer.parseInt(ReadApplicationProperties.appProperties.getProperty("AboutMePanel_goBackToMenuButtonHeight"));
        textAreaWidth = Integer.parseInt(ReadApplicationProperties.appProperties.getProperty("AboutMePanel_textAreaWidth"));
        textAreaHeight = Integer.parseInt(ReadApplicationProperties.appProperties.getProperty("AboutMePanel_textAreaHeight"));
        textAreaFontSize = Integer.parseInt(ReadApplicationProperties.appProperties.getProperty("AboutMePanel_textAreaFontSize"));
    }

    public void turnOffBackgroundInPanel() {
        background = false;
        repaint();
        revalidate();
    }

    public void turnOnBackgroundInPanel() {
        background = true;
        repaint();
        revalidate();
    }
}
