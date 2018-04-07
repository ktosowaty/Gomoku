package utility;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyTextArea extends JTextArea {

    private BufferedImage img = null;

    private static final Logger LOGGER = Logger.getLogger(MyTextArea.class.getName());

    public MyTextArea() {
        super();
        try {
            img = ImageIO.read(new File("images/goImage.png"));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(img, -300, -150, null);
        super.paintComponent(g);
    }
}
