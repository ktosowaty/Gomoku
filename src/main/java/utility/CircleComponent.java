package utility;

import java.awt.*;
import java.util.logging.Logger;

public class CircleComponent {

    private Color color;
    private int x, y, width, height;

    private static final Logger LOGGER = Logger.getLogger(CircleComponent.class.getName());

    public CircleComponent(Color color, int xCoordinate, int yCoordinate, int circleWidth, int circleHeight) {
        this.color = color;
        this.x = xCoordinate;
        this.y = yCoordinate;
        this.width = circleWidth;
        this.height = circleHeight;
    }

    public void drawCircle(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawOval(x, y, width, height);
        g.setColor(color);
        g.fillOval(x, y, width, height);
    }
}
