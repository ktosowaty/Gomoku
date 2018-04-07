package panel;

import customEventListener.ChangeLanguageListener;
import customEventListener.GameOverListener;
import customEventListener.GameStartListener;
import utility.CircleComponent;
import utility.DatabaseConnection;
import utility.GetTimeFromServer;
import utility.ReadApplicationProperties;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GamePanel extends JPanel implements ActionListener, ChangeLanguageListener, GameStartListener, GameOverListener {

    public static int moveCounter = 0;
    public static JButton button[][] = new JButton[15][15];
    private GridBagConstraints c;
    public static Vector<CircleComponent> movesOnBoard = new Vector<CircleComponent>();
    public static int moves[][] = new int[15][15];
    private String winner;
    private static Timer timer;
    private int secondsPassed = 0;
    public static GetTimeFromServer getTimeFromServer;
    private static String dateAndTime;
    private int lengthUnit, fontSize, player1X, player1Y, player2X, player2Y, timeRemainingX, timeRemainingY, dateAndTimeX, dateAndTimeY;

    public static Vector<CircleComponent> movesOnBoardSaved = new Vector<CircleComponent>();
    public static int movesSaved[][] = new int[15][15];
    public static String player1NickSaved, player2NickSaved;
    public static String timeForMoveSavedString;
    public static int moveCounterSaved;

    private static final Logger LOGGER = Logger.getLogger(GamePanel.class.getName());

    public GamePanel() {
        super(new GridBagLayout());
        readProperties();
        this.setBackground(new Color(250, 204, 113));
        c = new GridBagConstraints();
        getTimeFromServer = new GetTimeFromServer();
        initializeButtons();
        initializeMoves();
        timer = new Timer(1000, this);
    }

    private void initializeMoves() {
        for(int i=0; i<15; i++) {
            for(int j=0; j<15; j++) {
                moves[i][j] = 0;
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(int i=0; i<15; i++) {
            g.drawLine(2*lengthUnit+i*lengthUnit, 2*lengthUnit, 2*lengthUnit + i*lengthUnit, 2*lengthUnit+14*lengthUnit);
        }
        int j=0;
        for(char i='a'; i<='o'; i++) {
            g.drawString(""+i, 2*lengthUnit+j*lengthUnit, 2*lengthUnit + 15*lengthUnit);
            j++;
        }
        for(int i=0; i<15; i++) {
            g.drawLine(2*lengthUnit, 2*lengthUnit + i*lengthUnit, 2*lengthUnit + 14*lengthUnit, 2*lengthUnit + i*lengthUnit);

        }
        j=0;
        for(int i=15; i>=1; i--) {
            g.drawString(""+i, lengthUnit, 2*lengthUnit+j*lengthUnit);
            j++;
        }
        for(int i=0; i<movesOnBoard.size(); i++) {
            movesOnBoard.get(i).drawCircle(g);
        }
        g.setColor(Color.BLUE);
        g.setFont(new Font("Tahoma", Font.BOLD, fontSize));
        g.drawString(App.messages.getString("GamePanel_player1") + " " + GameOptionsPanel.getPlayer1Nick(), player1X, player1Y);
        g.drawString(App.messages.getString("GamePanel_player2") + " " + GameOptionsPanel.getPlayer2Nick(), player2X, player2Y);
        g.drawString(App.messages.getString("GamePanel_timeRemaining") + " " +
                (Integer.parseInt(GameOptionsPanel.getTimeForMoveString())-secondsPassed), timeRemainingX, timeRemainingY);
        g.drawString(App.messages.getString("GamePanel_dateAndTime") + " " + dateAndTime, dateAndTimeX, dateAndTimeY);
    }

    private void initializeButtons() {
        for(int i=0; i<15; i++) {
            for(int j=0; j<15; j++) {
                button[i][j] = new JButton();
                button[i][j].setPreferredSize(new Dimension(32, 32));
                button[i][j].addActionListener(this);
                button[i][j].setVisible(true);
                c.gridwidth = 1;
                c.gridheight = 1;
                c.gridx = j;
                c.gridy = i;
                button[i][j].setOpaque(false);
                button[i][j].setContentAreaFilled(false);
                button[i][j].setBorderPainted(false);
                button[i][j].setFocusPainted(false);
                add(button[i][j], c);
            }
        }
    }

    private void resetButtons() {
        for(int i=0; i<15; i++) {
            for(int j=0; j<15; j++) {
                button[i][j].setEnabled(true);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==timer) {
            secondsPassed++;
            revalidate();
            repaint();
            if(Integer.parseInt(GameOptionsPanel.getTimeForMoveString())==secondsPassed) {
                timer.stop();
                if(moveCounter%2==0) winner = App.messages.getString("GamePanel_winnerPlayer2");
                else winner = App.messages.getString("GamePanel_winnerPlayer1");
                updateDatabase();
                App.readXML.getRandomWord();
                int result = JOptionPane.showConfirmDialog(this, winner +
                                App.messages.getString("GamePanel_playAgain") + "\npol: " + App.readXML.pol + " | eng: " +
                                App.readXML.eng + " | esp: " + App.readXML.esp, App.messages.getString("GamePanel_gameOver"),
                        JOptionPane.YES_NO_OPTION);
                jOptionPaneAction(result);
            }
        } else {
            secondsPassed = 0;
            timer.restart();
            for(int i=0; i<15; i++) {
                for(int j=0; j<15; j++) {
                    if(e.getSource()==button[i][j]) {
                        button[i][j].setEnabled(false);
                        Color color;
                        if(moveCounter%2==0) {
                            color = Color.BLACK;
                            moves[i][j] = 1;
                        } else {
                            color = Color.WHITE;
                            moves[i][j] = 2;
                        }
                        moveCounter++;
                        int xCoordinate = 2*lengthUnit - lengthUnit/2 + j*lengthUnit;
                        int yCoordinate = 2*lengthUnit - lengthUnit/2 + i*lengthUnit;
                        int circleWidth = lengthUnit;
                        int circleHeight = lengthUnit;
                        CircleComponent circle = new CircleComponent(color, xCoordinate, yCoordinate, circleWidth, circleHeight);
                        movesOnBoard.add(circle);
                        revalidate();
                        repaint();
                        if(isThereAWinner(i, j)) {
                            updateDatabase();
                            App.readXML.getRandomWord();
                            timer.stop();
                            int result = JOptionPane.showConfirmDialog(this, winner +
                                    App.messages.getString("GamePanel_playAgain") + "\npol: " + App.readXML.pol + " | eng: " +
                                            App.readXML.eng + " | esp: " + App.readXML.esp, App.messages.getString("GamePanel_gameOver"),
                                    JOptionPane.YES_NO_OPTION);
                            jOptionPaneAction(result);
                        }
                        break;
                    }
                }
            }
        }
    }

    private void jOptionPaneAction(int result) {
        App.gameOverEvent.updateVariablesInAppAfterGameOver();
        //resetGame();
        if(result == JOptionPane.YES_OPTION) {
            timer.start();
            dateAndTime = getTimeFromServer.getDateAndTime();
        } else {
            if(App.isThereSavedGame) MenuBarManagement.loadGame.setEnabled(true);
            MenuBarManagement.mainMenu.setEnabled(true);
            App.frame.setContentPane(App.menuPanel);
            App.frame.pack();
        }
    }

    private boolean isThereAWinner(int x, int y) {
        for(int i=0; i<5; i++) {
            if(x-4+i<0 || x+i>14 || y-4+i<0 || y+i>14) continue;
            if(moves[x-4+i][y-4+i]==moves[x-3+i][y-3+i] && moves[x-3+i][y-3+i]==moves[x-2+i][y-2+i] && moves[x-2+i][y-2+i]==moves[x-1+i][y-1+i]
                    && moves[x-1+i][y-1+i]==moves[x+i][y+i]) {
                if((x-5+i<0 || y-5+i<0 || moves[x-5+i][y-5+i]!=moves[x][y]) && (x+i+1>14 || y+i+1>14 || moves[x+i+1][y+i+1]!=moves[x][y])) {
                    winner = App.messages.getString("GamePanel_winner") + " " + moves[x][y];
                    return true;
                }
            }
        }
        for(int i=0; i<5; i++) {
            if(x-4+i<0 || x+i>14 || y+4-i>14 || y-i<0) continue;
            if(moves[x-4+i][y+4-i]==moves[x-3+i][y+3-i] && moves[x-3+i][y+3-i]==moves[x-2+i][y+2-i] && moves[x-2+i][y+2-i]==moves[x-1+i][y+1-i]
                    && moves[x-1+i][y+1-i]==moves[x+i][y-i]) {
                if((x-5+i<0 || y+5-i>14 || moves[x-5+i][y+5-i]!=moves[x][y]) && (x+i+1>14 || y-i-1<0 || moves[x+i+1][y-i-1]!=moves[x][y])) {
                    winner = App.messages.getString("GamePanel_winner") + " " + moves[x][y];
                    return true;
                }
            }
        }
        for(int i=0; i<5; i++) {
            if(x-4+i<0 || x+i>14) continue;
            if(moves[x-4+i][y]==moves[x-3+i][y] && moves[x-3+i][y]==moves[x-2+i][y] && moves[x-2+i][y]==moves[x-1+i][y]
                    && moves[x-1+i][y]==moves[x+i][y]) {
                if((x-5+i<0 || moves[x-5+i][y]!=moves[x][y]) && (x+i+1>14 || moves[x+i+1][y]!=moves[x][y])) {
                    winner = App.messages.getString("GamePanel_winner") + " " + moves[x][y];
                    return true;
                }
            }
        }
        for(int i=0; i<5; i++) {
            if(y-4+i<0 || y+i>14) continue;
            if(moves[x][y-4+i]==moves[x][y-3+i] && moves[x][y-3+i]==moves[x][y-2+i] && moves[x][y-2+i]==moves[x][y-1+i]
                    && moves[x][y-1+i]==moves[x][y+i]) {
                if((y-5+i<0 || moves[x][y-5+i]!=moves[x][y]) && (y+i+1>14 || moves[x][y+i+1]!=moves[x][y])) {
                    winner = App.messages.getString("GamePanel_winner") + " " + moves[x][y];
                    return true;
                }
            }
        }
        return false;
    }

    public void changeLanguage() {
        if(App.currentLocale.toString().equals("en_GB")) {
            UIManager.put("OptionPane.yesButtonText", "Yes");
            UIManager.put("OptionPane.noButtonText", "No");
        } else {
            UIManager.put("OptionPane.yesButtonText", "Tak");
            UIManager.put("OptionPane.noButtonText", "Nie");
        }
        revalidate();
        repaint();
    }

    private void readProperties() {
        lengthUnit = Integer.parseInt(ReadApplicationProperties.appProperties.getProperty("GamePanel_lengthUnit"));
        fontSize = Integer.parseInt(ReadApplicationProperties.appProperties.getProperty("GamePanel_fontSize"));
        player1X = Integer.parseInt(ReadApplicationProperties.appProperties.getProperty("GamePanel_player1X"));
        player1Y = Integer.parseInt(ReadApplicationProperties.appProperties.getProperty("GamePanel_player1Y"));
        player2X = Integer.parseInt(ReadApplicationProperties.appProperties.getProperty("GamePanel_player2X"));
        player2Y = Integer.parseInt(ReadApplicationProperties.appProperties.getProperty("GamePanel_player2Y"));
        timeRemainingX = Integer.parseInt(ReadApplicationProperties.appProperties.getProperty("GamePanel_timeRemainingX"));
        timeRemainingY = Integer.parseInt(ReadApplicationProperties.appProperties.getProperty("GamePanel_timeRemainingY"));
        dateAndTimeX = Integer.parseInt(ReadApplicationProperties.appProperties.getProperty("GamePanel_dateAndTimeX"));
        dateAndTimeY = Integer.parseInt(ReadApplicationProperties.appProperties.getProperty("GamePanel_dateAndTimeY"));
    }

    private void updateDatabase() {
        SwingWorker worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                DatabaseConnection conn = new DatabaseConnection();
                conn.connectToDB();
                Connection connection = conn.getConnection();
                try {
                    PreparedStatement statement = connection.prepareStatement("SELECT * FROM Player WHERE PlayerName = ?");
                    statement.setString(1, GameOptionsPanel.getPlayer1Nick());
                    ResultSet rs = statement.executeQuery();
                    if(rs.next()) {
                        if(winner.endsWith("1")) {
                            statement = connection.prepareStatement("UPDATE Player SET GamesPlayed = GamesPlayed+1, GamesWon = GamesWon+1 " +
                                    "WHERE PlayerName = ?");
                            statement.setString(1, GameOptionsPanel.getPlayer1Nick());
                            statement.execute();
                        } else {
                            statement = connection.prepareStatement("UPDATE Player SET GamesPlayed = GamesPlayed+1 WHERE PlayerName = ?");
                            statement.setString(1, GameOptionsPanel.getPlayer1Nick());
                            statement.execute();
                        }
                    } else {
                        if(winner.endsWith("1")) {
                            statement = connection.prepareStatement("INSERT INTO Player VALUES (?, ?, ?)");
                            statement.setString(1, GameOptionsPanel.getPlayer1Nick());
                            statement.setInt(2, 1);
                            statement.setInt(3, 1);
                            statement.execute();
                        } else {
                            statement = connection.prepareStatement("INSERT INTO Player VALUES (?, ?, ?)");
                            statement.setString(1, GameOptionsPanel.getPlayer1Nick());
                            statement.setInt(2, 1);
                            statement.setInt(3, 0);
                            statement.execute();
                        }
                    }

                    statement = connection.prepareStatement("SELECT * FROM Player WHERE PlayerName = ?");
                    statement.setString(1, GameOptionsPanel.getPlayer2Nick());
                    rs = statement.executeQuery();
                    if(rs.next()) {
                        if(winner.endsWith("2")) {
                            statement = connection.prepareStatement("UPDATE Player SET GamesPlayed = GamesPlayed+1, GamesWon = GamesWon+1 " +
                                    "WHERE PlayerName = ?");
                            statement.setString(1, GameOptionsPanel.getPlayer2Nick());
                            statement.execute();
                        } else {
                            statement = connection.prepareStatement("UPDATE Player SET GamesPlayed = GamesPlayed+1 WHERE PlayerName = ?");
                            statement.setString(1, GameOptionsPanel.getPlayer2Nick());
                            statement.execute();
                        }
                    } else {
                        if(winner.endsWith("2")) {
                            statement = connection.prepareStatement("INSERT INTO Player VALUES (?, ?, ?)");
                            statement.setString(1, GameOptionsPanel.getPlayer2Nick());
                            statement.setInt(2, 1);
                            statement.setInt(3, 1);
                            statement.execute();
                        } else {
                            statement = connection.prepareStatement("INSERT INTO Player VALUES (?, ?, ?)");
                            statement.setString(1, GameOptionsPanel.getPlayer2Nick());
                            statement.setInt(2, 1);
                            statement.setInt(3, 0);
                            statement.execute();
                        }
                    }
                } catch(SQLException e) {
                    LOGGER.log(Level.SEVERE, e.toString(), e);
                } finally {
                    conn.disconnectDB();
                    App.bestPlayersPanel.refresh(App.bestPlayersPanel.downloadData());
                }
                return null;
            }
        };
        worker.execute();
    }

    public void updateVariables() {
        timer.start();
        dateAndTime = getTimeFromServer.getDateAndTime();
    }

    public void updateVariablesAfterGameOver() {
        moveCounter = 0;
        movesOnBoard.clear();
        initializeMoves();
        resetButtons();
        secondsPassed = 0;
        timer.stop();
        revalidate();
        repaint();
    }
}
