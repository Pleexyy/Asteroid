import java.awt.*;

public class Asteroid {
    private Polygon asteroide;

    public Asteroid(int x, int y) {
        this.asteroide = new Polygon();
        this.asteroide.addPoint(x, y);
        this.asteroide.addPoint(x + 50, y);
        this.asteroide.addPoint(x + 50, y + 50);
        this.asteroide.addPoint(x, y + 50);
    }

    public void drawAsteroid(Graphics g) {
        /* Draw Asteroid */
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED);
        g2d.draw(asteroide);
    }

    public boolean contains(double x, double y) {
        return this.asteroide.contains(x, y);
    }
}