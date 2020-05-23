import java.awt.*;

public class Laser {
    private int xMissile;
    private int yMissile;
    private int coeff;

    Point p;

    public Laser(int xMissile, int yMissile) {
        // this.xMissile = xMissile;
        // this.yMissile = yMissile;
        coeff = (yMissile - 400) / (xMissile - 400);
        p = new Point(xMissile, yMissile);
    }

    protected void updatePosition() {
        p.translate(1, 1);
        // System.out.println(xMissile + " " + yMissile);
    }

    public void drawLaser(Graphics g) {
        /* Draw Missile */
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.GREEN);
        g2d.fillOval(p.x, p.y, 5, 5);
        // g2d.fillOval(xMissile, yMissile, 5, 5);
        updatePosition();
    }
}