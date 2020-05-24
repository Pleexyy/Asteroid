import java.awt.*;

public class Laser {
    private double dx, dy, x, y;
    Point p;
    private static final double SPEED = 0.1;

    public Laser(int xMissile, int yMissile) {
        dx = xMissile - 400;
        dy = yMissile - 400;
        p = new Point(xMissile, yMissile);
    }

    public Laser() {
	}

	protected void updatePosition() {
        x = p.x + dx * SPEED;
        y = p.y + dy * SPEED;
        p.setLocation(x, y);
    }
    
    public void drawLaser(Graphics g) {
        /* Draw Missile */
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.GREEN);
        g2d.fillOval(p.x, p.y, 5, 5);
        updatePosition();
    }

    public double getX() {
        return p.getX();
    }

    public double getY() {
        return p.getY();
    }

}