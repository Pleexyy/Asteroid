import java.awt.*;

public class Laser {
    private double dx, dy;
    Point p;
    private static final double SPEED = 0.1;

    public Laser(int xMissile, int yMissile) {
        dx = xMissile - 400;
        dy = yMissile - 400;
        p = new Point(xMissile, yMissile);
    }

    protected void updatePosition() {
        double x = p.x + dx * SPEED;
        double y = p.y + dy * SPEED;
        p.setLocation(x, y);
    }

    public void drawLaser(Graphics g) {
        /* Draw Missile */
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.GREEN);
        g2d.fillOval(p.x, p.y, 5, 5);
        updatePosition();
    }
}