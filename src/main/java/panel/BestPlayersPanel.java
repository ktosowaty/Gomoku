package panel;

import customEventListener.ChangeLanguageListener;
import customEventListener.TurnOffBackgroundListener;
import customEventListener.TurnOnBackgroundListener;
import utility.DatabaseConnection;
import utility.ReadApplicationProperties;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BestPlayersPanel extends JPanel implements ActionListener, ChangeLanguageListener, TurnOffBackgroundListener, TurnOnBackgroundListener {

    private JButton goBackToMenu;
    private JTable table;
    private JScrollPane pane;
    private DefaultTableModel model;
    private String[] columnNames = {App.messages.getString("BestPlayersPanel_playerName"),
            App.messages.getString("BestPlayersPanel_gamesPlayed"), App.messages.getString("BestPlayersPanel_gamesWon")};
    private int goBackToMenuButtonWidth, goBackToMenuButtonHeight, tableWidth, tableHeight;
    public DatabaseConnection databaseConnection = new DatabaseConnection();
    private String[][] data;
    private BufferedImage img = null;
    private BufferedImage backgroundImg = null;
    private boolean background = false;

    private static final Logger LOGGER = Logger.getLogger(BestPlayersPanel.class.getName());

    public BestPlayersPanel() {
        readProperties();
        SwingWorker worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                init(downloadData());
                return null;
            }
        };
        worker.execute();
    }

    public String[][] downloadData() {
        int index = 0;
        String sqlQuery = "SELECT COUNT(*) sum FROM Player";
        databaseConnection.connectToDB();
        Connection connection = databaseConnection.getConnection();
        data = null;
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sqlQuery);
            rs.next();
            data = new String[Integer.parseInt(rs.getString("sum"))][3];
            sqlQuery = "SELECT * FROM Player ORDER BY GamesWon DESC";
            rs = statement.executeQuery(sqlQuery);
            while (rs.next()) {
                data[index][0] = rs.getString("PlayerName");
                data[index][1] = rs.getString("GamesPlayed");
                data[index][2] = rs.getString("GamesWon");
                index++;
            }
        } catch(SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        databaseConnection.disconnectDB();
        return data;
    }

    private void init(String data[][]) {
        loadImage();
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = 1;
        c.gridheight = 1;
        goBackToMenu = new JButton(App.messages.getString("BestPlayersPanel_goBackToMenu"));
        goBackToMenu.setPreferredSize(new Dimension(goBackToMenuButtonWidth, goBackToMenuButtonHeight));
        goBackToMenu.addActionListener(this);
        goBackToMenu.setVisible(true);
        c.gridx = 0;
        c.gridy = 0;
        add(goBackToMenu, c);
        c.gridx = 0;
        c.gridy = 1;
        model = new DefaultTableModel(data, columnNames);
        table = new JTable(model) {{
            setOpaque(false);
            setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {{
                setOpaque(false);
            }});
        }};
        pane = new JScrollPane(table) {{
                setOpaque(false);
                getViewport().setOpaque(false);
        }
            @Override
            protected void paintComponent(Graphics g) {
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
                super.paintComponent(g);
            }
        };
        pane.setPreferredSize(new Dimension(tableWidth, tableHeight));
        add(pane, c);
    }

    public void refresh(String data[][]) {
        model = new DefaultTableModel(data, columnNames);
        table.setModel(model);
    }

    private void readProperties() {
        goBackToMenuButtonWidth = Integer.parseInt(ReadApplicationProperties.appProperties.getProperty("BestPlayersPanel_goBackToMenuButtonWidth"));
        goBackToMenuButtonHeight = Integer.parseInt(ReadApplicationProperties.appProperties.getProperty("BestPlayersPanel_goBackToMenuButtonHeight"));
        tableWidth = Integer.parseInt(ReadApplicationProperties.appProperties.getProperty("BestPlayersPanel_tableWidth"));
        tableHeight = Integer.parseInt(ReadApplicationProperties.appProperties.getProperty("BestPlayersPanel_tableHeight"));
    }

    public void changeLanguage() {
        goBackToMenu.setText(App.messages.getString("BestPlayersPanel_goBackToMenu"));
        columnNames = new String[] {App.messages.getString("BestPlayersPanel_playerName"),
                App.messages.getString("BestPlayersPanel_gamesPlayed"), App.messages.getString("BestPlayersPanel_gamesWon")};
        model = new DefaultTableModel(data, columnNames);
        table.setModel(model);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==goBackToMenu) {
            App.frame.setContentPane(App.menuPanel);
            App.frame.pack();
        }
    }

    private void loadImage() {
        try {
            img = ImageIO.read(new File("images/jtableimg.jpg"));
            backgroundImg = ImageIO.read(new File("images/gray.jpg"));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(background) g.drawImage(backgroundImg, 0, 0, null);
        else g.drawImage(null, 0, 0, null);
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

