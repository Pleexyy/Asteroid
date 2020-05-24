import java.awt.*;

public class Asteroid {
    private Polygon asteroid;

    public Asteroid(int x, int y) {
        this.asteroid = new Polygon();
        this.asteroid.addPoint(x, y);
        this.asteroid.addPoint(x + 50, y);
        this.asteroid.addPoint(x + 50, y + 50);
        this.asteroid.addPoint(x, y + 50);
    }

    public void drawAsteroid(Graphics g) {
        /* Dessine l'asteroide */
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED);
        g2d.draw(asteroid);
    }

    public boolean contains(double x, double y) {
        return this.asteroid.contains(x, y);
    }
}