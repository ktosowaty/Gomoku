package panel;

import customEvent.*;
import utility.ReadApplicationProperties;
import utility.ReadXML;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javax.swing.*;

public class App {

    public static JFrame frame;
    public static MainMenuPanel menuPanel;
    public static GamePanel gamePanel;
    public static BestPlayersPanel bestPlayersPanel;
    public static AboutMePanel aboutMePanel;
    public static GameInfoPanel gameInfoPanel;
    public static GameOptionsPanel gameOptionsPanel;
    public static Locale currentLocale;
    public static ResourceBundle messages;
    public static ChangeLanguageEvent changeLanguageEvent;
    public static GameStartEvent gameStartEvent;
    public static GameOverEvent gameOverEvent;
    public static TurnOnBackgroundEvent turnOnBackgroundEvent;
    public static TurnOffBackgroundEvent turnOffBackgroundEvent;
    public static ReadApplicationProperties readApplicationProperties;
    public static MenuBarManagement menuBarManagement;
    public static App app;
    private static int frameWidth, frameHeight, frameBordersLeftRight, frameBordersTopBottom;
    public static ReadXML readXML;

    public static boolean isThereSavedGame = false;

    private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    public App() {
        readProperties();
        initLogManager();
        initReadXML();
        initLanguage();
        initLookAndFeel();
        initMenuBar();
    }

    private void initMenuBar() {
        menuBarManagement = new MenuBarManagement();
        menuBarManagement.createMenu();
    }

    private void initLookAndFeel() {
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.aero.AeroLookAndFeel");
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        } catch (InstantiationException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        } catch (IllegalAccessException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        } catch (UnsupportedLookAndFeelException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    private static void createAndShowGUI() {
        app = new App();
        frame = new JFrame("Gomoku");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(frameWidth + frameBordersLeftRight, frameHeight + frameBordersTopBottom));
        frame.setLayout(new BorderLayout());

        menuPanel = new MainMenuPanel();
        frame.add(menuPanel, BorderLayout.CENTER);

        gamePanel = new GamePanel();
        frame.add(gamePanel, BorderLayout.CENTER);

        bestPlayersPanel = new BestPlayersPanel();
        frame.add(bestPlayersPanel, BorderLayout.CENTER);

        aboutMePanel = new AboutMePanel();
        frame.add(aboutMePanel, BorderLayout.CENTER);

        gameInfoPanel = new GameInfoPanel();
        frame.add(gameInfoPanel, BorderLayout.CENTER);

        gameOptionsPanel = new GameOptionsPanel();
        frame.add(gameOptionsPanel, BorderLayout.CENTER);

        frame.setJMenuBar(MenuBarManagement.menuBar);
        frame.setContentPane(menuPanel);

        app.initGameStartEvent();
        app.initLanguageChangeEvent();
        app.initGameOverEvent();
        app.initTurnOnBackgroundEvent();
        app.initTurnOffBackgroundEvent();

        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private void initLanguage() {
        currentLocale = new Locale("en", "GB");
        messages = ResourceBundle.getBundle("Lang", currentLocale);
    }

    private void initLanguageChangeEvent() {
        changeLanguageEvent = new ChangeLanguageEvent();
        changeLanguageEvent.addListener(menuBarManagement);
        changeLanguageEvent.addListener(menuPanel);
        changeLanguageEvent.addListener(gamePanel);
        changeLanguageEvent.addListener(gameOptionsPanel);
        changeLanguageEvent.addListener(gameInfoPanel);
        changeLanguageEvent.addListener(aboutMePanel);
        changeLanguageEvent.addListener(bestPlayersPanel);
    }

    private void initGameStartEvent() {
        gameStartEvent = new GameStartEvent();
        gameStartEvent.addListener(gamePanel);
        gameStartEvent.addListener(menuBarManagement);
    }

    private void initGameOverEvent() {
        gameOverEvent = new GameOverEvent();
        gameOverEvent.addListener(gamePanel);
        gameOverEvent.addListener(gameOptionsPanel);
    }

    private void initTurnOnBackgroundEvent() {
        turnOnBackgroundEvent = new TurnOnBackgroundEvent();
        turnOnBackgroundEvent.addListener(aboutMePanel);
        turnOnBackgroundEvent.addListener(gameInfoPanel);
        turnOnBackgroundEvent.addListener(bestPlayersPanel);
    }

    private void initTurnOffBackgroundEvent() {
        turnOffBackgroundEvent = new TurnOffBackgroundEvent();
        turnOffBackgroundEvent.addListener(aboutMePanel);
        turnOffBackgroundEvent.addListener(gameInfoPanel);
        turnOffBackgroundEvent.addListener(bestPlayersPanel);
    }

    private void initLogManager() {
        try {
            LogManager.getLogManager().readConfiguration(new FileInputStream("src/main/resources/logger.properties"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void readProperties() {
        readApplicationProperties = new ReadApplicationProperties();
        readApplicationProperties.initProperties();
        frameWidth = Integer.parseInt(ReadApplicationProperties.appProperties.getProperty("App_paneWidth"));
        frameHeight = Integer.parseInt(ReadApplicationProperties.appProperties.getProperty("App_paneHeight"));
        frameBordersLeftRight = Integer.parseInt(ReadApplicationProperties.appProperties.getProperty("App_widthLeftRightBorders"));
        frameBordersTopBottom = Integer.parseInt(ReadApplicationProperties.appProperties.getProperty("App_heightTopBottomBorders"));
    }

    private void initReadXML() {
        SwingWorker worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                readXML = new ReadXML();
                readXML.readXML();
                return null;
            }
        };
        worker.execute();
    }
}