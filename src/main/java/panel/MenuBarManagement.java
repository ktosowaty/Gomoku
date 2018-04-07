package panel;

import action.AboutGameAction;
import action.AboutMeAction;
import action.BestPlayersAction;
import action.NewGameAction;
import customEventListener.ChangeLanguageListener;
import customEventListener.GameStartListener;
import utility.CircleComponent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuBarManagement implements ActionListener, ChangeLanguageListener, GameStartListener {

    public static JMenuBar menuBar;
    public static JMenu mainMenu;
    private static JMenu changeLanguage, changePLAF, changeBackground, loadSaveGame;
    private static JMenuItem newGame, bestPlayers, aboutGame, aboutMe, withoutBackground, withBackground;
    public static JMenuItem loadGame, saveGame;
    private static JRadioButtonMenuItem rbChangeLanguagePolish, rbChangeLanguageEnglish,
            rbChangePLAFAero, rbChangePLAFAluminium, rbChangePLAFBernstein, rbChangePLAFFast, rbChangePLAFHifi,
            rbChangePLAFLuna, rbChangePLAFMint, rbChangePLAFNoire, rbChangePLAFSmart;
    public static NewGameAction newGameAction;
    public static BestPlayersAction bestPlayersAction;
    public static AboutGameAction aboutGameAction;
    public static AboutMeAction aboutMeAction;

    private static final Logger LOGGER = Logger.getLogger(MenuBarManagement.class.getName());

    public void createMenu() {
        menuBar = new JMenuBar();
        mainMenu = new JMenu(App.messages.getString("App_mainMenu"));
        newGame = new JMenuItem();
        newGameAction = new NewGameAction(App.messages.getString("App_newGame"));
        newGame.setAction(newGameAction);
        mainMenu.add(newGame);
        mainMenu.addSeparator();
        bestPlayers = new JMenuItem();
        bestPlayersAction = new BestPlayersAction(App.messages.getString("App_bestPlayers"));
        bestPlayers.setAction(bestPlayersAction);
        mainMenu.add(bestPlayers);
        mainMenu.addSeparator();
        aboutGame = new JMenuItem();
        aboutGameAction = new AboutGameAction(App.messages.getString("App_aboutGame"));
        aboutGame.setAction(aboutGameAction);
        mainMenu.add(aboutGame);
        mainMenu.addSeparator();
        aboutMe = new JMenuItem();
        aboutMeAction = new AboutMeAction(App.messages.getString("App_aboutMe"));
        aboutMe.setAction(aboutMeAction);
        mainMenu.add(aboutMe);
        menuBar.add(mainMenu);



        loadSaveGame = new JMenu("Load/save game");
        loadGame = new JMenuItem("Load game");
        loadGame.addActionListener(this);
        loadGame.setEnabled(false);
        loadSaveGame.add(loadGame);
        loadSaveGame.addSeparator();
        saveGame = new JMenuItem("Save game");
        saveGame.addActionListener(this);
        saveGame.setEnabled(false);
        loadSaveGame.add(saveGame);
        menuBar.add(loadSaveGame);



        changeLanguage = new JMenu(App.messages.getString("App_changeLanguage"));
        ButtonGroup changeLanguageButtonGroup = new ButtonGroup();
        rbChangeLanguagePolish = new JRadioButtonMenuItem(App.messages.getString("App_changeLanguagePolish"));
        rbChangeLanguagePolish.addActionListener(this);
        changeLanguageButtonGroup.add(rbChangeLanguagePolish);
        changeLanguage.add(rbChangeLanguagePolish);
        rbChangeLanguageEnglish = new JRadioButtonMenuItem(App.messages.getString("App_changeLanguageEnglish"));
        rbChangeLanguageEnglish.setSelected(true);
        rbChangeLanguageEnglish.addActionListener(this);
        changeLanguageButtonGroup.add(rbChangeLanguageEnglish);
        changeLanguage.add(rbChangeLanguageEnglish);
        menuBar.add(changeLanguage);

        changePLAF = new JMenu(App.messages.getString("App_changeSkin"));
        ButtonGroup changePLAFButtonGroup = new ButtonGroup();
        rbChangePLAFAero = new JRadioButtonMenuItem("Aero");
        rbChangePLAFAero.setSelected(true);
        rbChangePLAFAero.addActionListener(this);
        changePLAFButtonGroup.add(rbChangePLAFAero);
        changePLAF.add(rbChangePLAFAero);
        rbChangePLAFAluminium = new JRadioButtonMenuItem("Aluminium");
        rbChangePLAFAluminium.addActionListener(this);
        changePLAFButtonGroup.add(rbChangePLAFAluminium);
        changePLAF.add(rbChangePLAFAluminium);
        rbChangePLAFBernstein = new JRadioButtonMenuItem("Bernstein");
        rbChangePLAFBernstein.addActionListener(this);
        changePLAFButtonGroup.add(rbChangePLAFBernstein);
        changePLAF.add(rbChangePLAFBernstein);
        rbChangePLAFFast = new JRadioButtonMenuItem("Fast");
        rbChangePLAFFast.addActionListener(this);
        changePLAFButtonGroup.add(rbChangePLAFFast);
        changePLAF.add(rbChangePLAFFast);
        rbChangePLAFHifi = new JRadioButtonMenuItem("HiFi");
        rbChangePLAFHifi.addActionListener(this);
        changePLAFButtonGroup.add(rbChangePLAFHifi);
        changePLAF.add(rbChangePLAFHifi);
        rbChangePLAFLuna = new JRadioButtonMenuItem("Luna");
        rbChangePLAFLuna.addActionListener(this);
        changePLAFButtonGroup.add(rbChangePLAFLuna);
        changePLAF.add(rbChangePLAFLuna);
        rbChangePLAFMint = new JRadioButtonMenuItem("Mint");
        rbChangePLAFMint.addActionListener(this);
        changePLAFButtonGroup.add(rbChangePLAFMint);
        changePLAF.add(rbChangePLAFMint);
        rbChangePLAFNoire = new JRadioButtonMenuItem("Noire");
        rbChangePLAFNoire.addActionListener(this);
        changePLAFButtonGroup.add(rbChangePLAFNoire);
        changePLAF.add(rbChangePLAFNoire);
        rbChangePLAFSmart = new JRadioButtonMenuItem("Smart");
        rbChangePLAFSmart.addActionListener(this);
        changePLAFButtonGroup.add(rbChangePLAFSmart);
        changePLAF.add(rbChangePLAFSmart);
        menuBar.add(changePLAF);

        changeBackground = new JMenu(App.messages.getString("MenuBarManagement_changeBackground"));
        withoutBackground = new JMenuItem(App.messages.getString("MenuBarManagement_withoutBackground"));
        withoutBackground.addActionListener(this);
        changeBackground.add(withoutBackground);
        mainMenu.addSeparator();
        withBackground = new JMenuItem(App.messages.getString("MenuBarManagement_withBackground"));
        withBackground.addActionListener(this);
        changeBackground.add(withBackground);
        menuBar.add(changeBackground);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            if(e.getSource()==rbChangePLAFAero) {
                UIManager.setLookAndFeel("com.jtattoo.plaf.aero.AeroLookAndFeel");
                SwingUtilities.updateComponentTreeUI(App.frame);
                App.frame.pack();
            } else if(e.getSource()==rbChangePLAFAluminium) {
                UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
                SwingUtilities.updateComponentTreeUI(App.frame);
                App.frame.pack();
            } else if(e.getSource()==rbChangePLAFBernstein) {
                UIManager.setLookAndFeel("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");
                SwingUtilities.updateComponentTreeUI(App.frame);
                App.frame.pack();
            } else if(e.getSource()==rbChangePLAFFast) {
                UIManager.setLookAndFeel("com.jtattoo.plaf.fast.FastLookAndFeel");
                SwingUtilities.updateComponentTreeUI(App.frame);
                App.frame.pack();
            } else if(e.getSource()==rbChangePLAFHifi) {
                UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
                SwingUtilities.updateComponentTreeUI(App.frame);
                App.frame.pack();
            } else if(e.getSource()==rbChangePLAFLuna) {
                UIManager.setLookAndFeel("com.jtattoo.plaf.luna.LunaLookAndFeel");
                SwingUtilities.updateComponentTreeUI(App.frame);
                App.frame.pack();
            } else if(e.getSource()==rbChangePLAFMint) {
                UIManager.setLookAndFeel("com.jtattoo.plaf.mint.MintLookAndFeel");
                SwingUtilities.updateComponentTreeUI(App.frame);
                App.frame.pack();
            } else if(e.getSource()==rbChangePLAFNoire) {
                UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
                SwingUtilities.updateComponentTreeUI(App.frame);
                App.frame.pack();
            } else if(e.getSource()==rbChangePLAFSmart) {
                UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
                SwingUtilities.updateComponentTreeUI(App.frame);
                App.frame.pack();
            } else if(e.getSource()==rbChangeLanguagePolish) {
                App.currentLocale = new Locale("pl", "PL");
                App.messages = ResourceBundle.getBundle("Lang", App.currentLocale);
                App.changeLanguageEvent.changeLanguageInApp();
            } else if(e.getSource()==rbChangeLanguageEnglish) {
                App.currentLocale = new Locale("en", "GB");
                App.messages = ResourceBundle.getBundle("Lang", App.currentLocale);
                App.changeLanguageEvent.changeLanguageInApp();
            } else if(e.getSource()==withoutBackground) {
                App.turnOffBackgroundEvent.turnOffBackgroundInPanels();
            } else if(e.getSource()==withBackground) {
                App.turnOnBackgroundEvent.turnOnBackgroundInPanels();
            } else if(e.getSource()==loadGame) {
                GamePanel.movesOnBoard.clear();
                GamePanel.movesOnBoard.addAll(GamePanel.movesOnBoardSaved);
                GameOptionsPanel.setPlayer1Nick(GamePanel.player1NickSaved);
                GameOptionsPanel.setPlayer2Nick(GamePanel.player2NickSaved);
                GameOptionsPanel.setTimeForMoveString(GamePanel.timeForMoveSavedString);
                GamePanel.moveCounter = GamePanel.moveCounterSaved;
                for(int i=0; i<15; i++) {
                    for(int j=0; j<15; j++) {
                        GamePanel.moves[i][j] = GamePanel.movesSaved[i][j];
                    }
                }
                for(int i=0; i<15; i++) {
                    for(int j=0; j<15; j++) {
                        if(GamePanel.moves[i][j]!=0) GamePanel.button[i][j].setEnabled(false);
                        else GamePanel.button[i][j].setEnabled(true);
                    }
                }
                App.gameStartEvent.updateVariablesInApp();
            } else if(e.getSource()==saveGame) {
                GamePanel.movesOnBoardSaved.addAll(GamePanel.movesOnBoard);
                GamePanel.player1NickSaved = GameOptionsPanel.getPlayer1Nick();
                GamePanel.player2NickSaved = GameOptionsPanel.getPlayer2Nick();
                GamePanel.timeForMoveSavedString = GameOptionsPanel.getTimeForMoveString();
                GamePanel.moveCounterSaved = GamePanel.moveCounter;
                loadGame.setEnabled(true);
                for(int i=0; i<15; i++) {
                    for(int j=0; j<15; j++) {
                        GamePanel.movesSaved[i][j] = GamePanel.moves[i][j];
                    }
                }
                App.isThereSavedGame = true;
            }
        } catch (ClassNotFoundException ex) {
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
        } catch (InstantiationException ex) {
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
        } catch (IllegalAccessException ex) {
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
        } catch (UnsupportedLookAndFeelException ex) {
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
        }
    }

    public void changeLanguage() {
        mainMenu.setText(App.messages.getString("App_mainMenu"));
        newGame.setText(App.messages.getString("App_newGame"));
        bestPlayers.setText(App.messages.getString("App_bestPlayers"));
        aboutGame.setText(App.messages.getString("App_aboutGame"));
        aboutMe.setText(App.messages.getString("App_aboutMe"));
        changeLanguage.setText(App.messages.getString("App_changeLanguage"));
        rbChangeLanguagePolish.setText(App.messages.getString("App_changeLanguagePolish"));
        rbChangeLanguageEnglish.setText(App.messages.getString("App_changeLanguageEnglish"));
        changePLAF.setText(App.messages.getString("App_changeSkin"));
        changeBackground.setText(App.messages.getString("MenuBarManagement_changeBackground"));
        withoutBackground.setText(App.messages.getString("MenuBarManagement_withoutBackground"));
        withBackground.setText(App.messages.getString("MenuBarManagement_withBackground"));
    }

    public void updateVariables() {
        mainMenu.setEnabled(false);
        App.frame.setContentPane(App.gamePanel);
        App.frame.pack();
    }
}
