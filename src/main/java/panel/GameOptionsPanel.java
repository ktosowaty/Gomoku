package panel;

import customEventListener.ChangeLanguageListener;
import customEventListener.GameOverListener;
import exception.*;
import utility.ReadApplicationProperties;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.*;
import java.util.regex.Pattern;

public class GameOptionsPanel extends JPanel implements ActionListener, ChangeLanguageListener, GameOverListener {

    private BufferedImage img = null;
    private JLabel player1NameLabel, player2NameLabel, timeForMoveLabel, instructions, player1Message, player2Message, timeForMoveMessage;
    private JTextField player1NameTextField, player2NameTextField, timeForMoveTextField;
    private JButton startGame;
    private static String player1Nick;
    private static String player2Nick;
    private static String timeForMoveString;
    private int instructionsLabelSize, player1NameLabelSize, player1NameTextFieldFontSize, player1NameTextFieldWidth, player1NameTextFieldHeight,
    player2NameLabelSize, player2NameTextFieldFontSize, player2NameTextFieldWidth, player2NameTextFieldHeight, timeForMoveLabelSize,
    timeForMoveTextFieldFontSize, timeForMoveTextFieldWidth, timeForMoveTextFieldHeight, player1MessageLabelSize, player2MessageLabelSize,
    timeForMoveMessageLabelSize;

    private static final Logger LOGGER = Logger.getLogger(GameOptionsPanel.class.getName());

    public GameOptionsPanel() {
        super(new GridBagLayout());
        readProperties();
        loadImage();
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(20, 5, 20 ,5);
        c.gridheight = 1;
        c.gridwidth = 2;
        instructions = new JLabel(App.messages.getString("GameOptionsPanel_instructions"));
        instructions.setFont(new Font("Tahoma", Font.BOLD, instructionsLabelSize));
        instructions.setForeground(Color.ORANGE);
        c.gridx = 0;
        c.gridy = 0;
        add(instructions, c);
        c.gridheight = 1;
        c.gridwidth = 1;
        player1NameLabel = new JLabel(App.messages.getString("GameOptionsPanel_player1Name"));
        player1NameLabel.setFont(new Font("Tahoma", Font.BOLD, player1NameLabelSize));
        player1NameLabel.setForeground(Color.ORANGE);
        c.gridx = 0;
        c.gridy = 2;
        add(player1NameLabel, c);
        player1NameTextField = new JTextField();
        player1NameTextField.setPreferredSize(new Dimension(player1NameTextFieldWidth, player1NameTextFieldHeight));
        player1NameTextField.setFont(new Font("Tahoma", Font.PLAIN, player1NameTextFieldFontSize));
        c.gridx = 1;
        c.gridy = 2;
        add(player1NameTextField, c);
        player2NameLabel = new JLabel(App.messages.getString("GameOptionsPanel_player2Name"));
        player2NameLabel.setFont(new Font("Tahoma", Font.BOLD, player2NameLabelSize));
        player2NameLabel.setForeground(Color.ORANGE);
        c.gridx = 0;
        c.gridy = 4;
        add(player2NameLabel, c);
        player2NameTextField = new JTextField();
        player2NameTextField.setPreferredSize(new Dimension(player2NameTextFieldWidth, player2NameTextFieldHeight));
        player2NameTextField.setFont(new Font("Tahoma", Font.PLAIN, player2NameTextFieldFontSize));
        c.gridx = 1;
        c.gridy = 4;
        add(player2NameTextField, c);
        timeForMoveLabel = new JLabel(App.messages.getString("GameOptionsPanel_timeForMove"));
        timeForMoveLabel.setFont(new Font("Tahoma", Font.BOLD, timeForMoveLabelSize));
        timeForMoveLabel.setForeground(Color.ORANGE);
        c.gridx = 0;
        c.gridy = 6;
        add(timeForMoveLabel, c);
        timeForMoveTextField = new JTextField();
        timeForMoveTextField.setPreferredSize(new Dimension(timeForMoveTextFieldWidth, timeForMoveTextFieldHeight));
        timeForMoveTextField.setFont(new Font("Tahoma", Font.PLAIN, timeForMoveTextFieldFontSize));
        c.gridx = 1;
        c.gridy = 6;
        add(timeForMoveTextField, c);
        startGame = new JButton(App.messages.getString("GameOptionsPanel_startGame"));
        startGame.addActionListener(this);
        c.gridheight = 1;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 7;
        add(startGame, c);
        player1Message = new JLabel(App.messages.getString("GameOptionsPanel_player1Message"));
        player1Message.setFont(new Font("Tahoma", Font.BOLD+Font.ITALIC, player1MessageLabelSize));
        player1Message.setForeground(Color.RED);
        c.gridx = 0;
        c.gridy = 1;
        add(player1Message, c);
        player2Message = new JLabel(App.messages.getString("GameOptionsPanel_player2Message"));
        player2Message.setFont(new Font("Tahoma", Font.BOLD+Font.ITALIC, player2MessageLabelSize));
        player2Message.setForeground(Color.RED);
        c.gridx = 0;
        c.gridy = 3;
        add(player2Message, c);
        timeForMoveMessage = new JLabel(App.messages.getString("GameOptionsPanel_timeForMoveMessage"));
        timeForMoveMessage.setFont(new Font("Tahoma", Font.BOLD+Font.ITALIC, timeForMoveMessageLabelSize));
        timeForMoveMessage.setForeground(Color.RED);
        c.gridx = 0;
        c.gridy = 5;
        add(timeForMoveMessage, c);
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
        g.drawImage(img, -300, -150, null);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==startGame) {
            boolean isEverythingCorrect = true;
            Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
            try {
                player1Nick = player1NameTextField.getText();
                boolean hasSpecialChar = pattern.matcher(player1Nick).find();
                if(player1Nick.length()<3) {
                    throw new TooShortNickException(App.messages.getString("GameOptionsPanel_TooShortNickExceptionPlayer1"));
                } else if(player1Nick.length()>15) {
                    throw new TooLongNickException(App.messages.getString("GameOptionsPanel_TooLongNickExceptionPlayer1"));
                } else if(hasSpecialChar) {
                    throw new NotAlphanumericCharactersException
                            (App.messages.getString("GameOptionsPanel_NotAlphanumericCharactersExceptionPlayer1"));
                } else {
                    player1Message.setText(App.messages.getString("GameOptionsPanel_player1Correct"));
                }
            } catch(TooShortNickException ex) {
                player1Message.setText(ex.getMessage());
                isEverythingCorrect = false;
                LOGGER.log(Level.WARNING, ex.toString(), ex);
            } catch(TooLongNickException ex) {
                player1Message.setText(ex.getMessage());
                isEverythingCorrect = false;
                LOGGER.log(Level.WARNING, ex.toString(), ex);
            } catch(NotAlphanumericCharactersException ex) {
                player1Message.setText(ex.getMessage());
                isEverythingCorrect = false;
                LOGGER.log(Level.WARNING, ex.toString(), ex);
            }

            try {
                player2Nick = player2NameTextField.getText();
                boolean hasSpecialChar = pattern.matcher(player2Nick).find();
                if(player2Nick.length()<3) {
                    throw new TooShortNickException(App.messages.getString("GameOptionsPanel_TooShortNickExceptionPlayer2"));
                } else if(player2Nick.length()>15) {
                    throw new TooLongNickException(App.messages.getString("GameOptionsPanel_TooLongNickExceptionPlayer2"));
                } else if(hasSpecialChar) {
                    throw new NotAlphanumericCharactersException
                            (App.messages.getString("GameOptionsPanel_NotAlphanumericCharactersExceptionPlayer2"));
                } else if(player1Nick.equals(player2Nick)) {
                    throw new SameNicknamesException(App.messages.getString("GameOptionsPanel_SameNicknamesException"));
                } else {
                    player2Message.setText(App.messages.getString("GameOptionsPanel_player2Correct"));
                }
            } catch(TooShortNickException ex) {
                player2Message.setText(ex.getMessage());
                isEverythingCorrect = false;
                LOGGER.log(Level.WARNING, ex.toString(), ex);
            } catch(TooLongNickException ex) {
                player1Message.setText(ex.getMessage());
                isEverythingCorrect = false;
                LOGGER.log(Level.WARNING, ex.toString(), ex);
            } catch(NotAlphanumericCharactersException ex) {
                player2Message.setText(ex.getMessage());
                isEverythingCorrect = false;
                LOGGER.log(Level.WARNING, ex.toString(), ex);
            } catch(SameNicknamesException ex) {
                player1Message.setText(ex.getMessage());
                player2Message.setText(ex.getMessage());
                isEverythingCorrect = false;
                LOGGER.log(Level.WARNING, ex.toString(), ex);
            }

            try {
                timeForMoveString = timeForMoveTextField.getText();
                Integer timeForMove = Integer.parseInt(timeForMoveString);
                if(timeForMove<10) {
                    throw new TooShortTimeForMoveException(App.messages.getString("GameOptionsPanel_TooShortTimeForMoveException"));
                } else if(timeForMove>60) {
                    throw new TooLongTimeForMoveException(App.messages.getString("GameOptionsPanel_TooLongTimeForMoveException"));
                } else {
                    timeForMoveMessage.setText(App.messages.getString("GameOptionsPanel_timeCorrect"));
                }
            } catch(TooShortTimeForMoveException ex) {
                timeForMoveMessage.setText(ex.getMessage());
                isEverythingCorrect = false;
                LOGGER.log(Level.WARNING, ex.toString(), ex);
            } catch(TooLongTimeForMoveException ex) {
                timeForMoveMessage.setText(ex.getMessage());
                isEverythingCorrect = false;
                LOGGER.log(Level.WARNING, ex.toString(), ex);
            } catch(NumberFormatException ex) {
                timeForMoveMessage.setText(App.messages.getString("GameOptionsPanel_NumberFormatException"));
                isEverythingCorrect = false;
                LOGGER.log(Level.WARNING, ex.toString(), ex);
            }
            if(isEverythingCorrect) {
                MenuBarManagement.saveGame.setEnabled(true);
                App.gameStartEvent.updateVariablesInApp();
            }
        }
    }

    public static String getPlayer1Nick() {
        return player1Nick;
    }

    public static void setPlayer1Nick(String nick) {
        player1Nick = nick;
    }

    public static String getPlayer2Nick() {
        return player2Nick;
    }

    public static void setPlayer2Nick(String nick) {
        player2Nick = nick;
    }

    public static String getTimeForMoveString() {
        return timeForMoveString;
    }

    public static void setTimeForMoveString(String val) {
        timeForMoveString = val;
    }

    public void changeLanguage() {
        instructions.setText(App.messages.getString("GameOptionsPanel_instructions"));
        player1NameLabel.setText(App.messages.getString("GameOptionsPanel_player1Name"));
        player2NameLabel.setText(App.messages.getString("GameOptionsPanel_player2Name"));
        timeForMoveLabel.setText(App.messages.getString("GameOptionsPanel_timeForMove"));
        startGame.setText(App.messages.getString("GameOptionsPanel_startGame"));
        player1Message.setText(App.messages.getString("GameOptionsPanel_player1Message"));
        player2Message.setText(App.messages.getString("GameOptionsPanel_player2Message"));
        timeForMoveMessage.setText(App.messages.getString("GameOptionsPanel_timeForMoveMessage"));
    }

    private void readProperties() {
        instructionsLabelSize = Integer.parseInt(ReadApplicationProperties.appProperties.getProperty("GameOptionsPanel_instructionsLabelSize"));
        player1NameLabelSize = Integer.parseInt(ReadApplicationProperties.appProperties.getProperty("GameOptionsPanel_player1NameLabelSize"));
        player1NameTextFieldFontSize = Integer.parseInt(ReadApplicationProperties.appProperties.getProperty("GameOptionsPanel_player1NameTextFieldFontSize"));
        player1NameTextFieldWidth = Integer.parseInt(ReadApplicationProperties.appProperties.getProperty("GameOptionsPanel_player1NameTextFieldWidth"));
        player1NameTextFieldHeight = Integer.parseInt(ReadApplicationProperties.appProperties.getProperty("GameOptionsPanel_player1NameTextFieldHeight"));
        player2NameLabelSize = Integer.parseInt(ReadApplicationProperties.appProperties.getProperty("GameOptionsPanel_player2NameLabelSize"));
        player2NameTextFieldFontSize = Integer.parseInt(ReadApplicationProperties.appProperties.getProperty("GameOptionsPanel_player2NameTextFieldFontSize"));
        player2NameTextFieldWidth = Integer.parseInt(ReadApplicationProperties.appProperties.getProperty("GameOptionsPanel_player2NameTextFieldWidth"));
        player2NameTextFieldHeight = Integer.parseInt(ReadApplicationProperties.appProperties.getProperty("GameOptionsPanel_player2NameTextFieldHeight"));
        timeForMoveLabelSize = Integer.parseInt(ReadApplicationProperties.appProperties.getProperty("GameOptionsPanel_timeForMoveLabelSize"));
        timeForMoveTextFieldFontSize = Integer.parseInt(ReadApplicationProperties.appProperties.getProperty("GameOptionsPanel_timeForMoveTextFieldFontSize"));
        timeForMoveTextFieldWidth = Integer.parseInt(ReadApplicationProperties.appProperties.getProperty("GameOptionsPanel_timeForMoveTextFieldWidth"));
        timeForMoveTextFieldHeight = Integer.parseInt(ReadApplicationProperties.appProperties.getProperty("GameOptionsPanel_timeForMoveTextFieldHeight"));
        player1MessageLabelSize = Integer.parseInt(ReadApplicationProperties.appProperties.getProperty("GameOptionsPanel_player1MessageLabelSize"));
        player2MessageLabelSize = Integer.parseInt(ReadApplicationProperties.appProperties.getProperty("GameOptionsPanel_player2MessageLabelSize"));
        timeForMoveMessageLabelSize = Integer.parseInt(ReadApplicationProperties.appProperties.getProperty("GameOptionsPanel_timeForMoveMessageLabelSize"));
    }

    @Override
    public void updateVariablesAfterGameOver() {
        String tmp = player1Nick;
        player1Nick = player2Nick;
        player2Nick = tmp;
    }
}
