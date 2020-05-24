import java.awt.*;
import javax.swing.JPanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.awt.geom.AffineTransform;

public class AsteroidGame extends JPanel implements KeyListener {
    /**
     *
     */
    private static final long serialVersionUID = 913853861154657373L;
    private int rotation = 0;
    private int x1Ship, x2Ship, x3Ship, y1Ship, y2Ship, y3Ship;
    private final ArrayList<Integer> pressedKeys = new ArrayList<>();
    private ArrayList<Laser> lasers = new ArrayList<Laser>();
    private Polygon ship = new Polygon();
    private Asteroid asteroid = new Asteroid(200, 200);

    public AsteroidGame(int width, int height) {
        super();
        System.out.println("AsteroidGame");
        addKeyListener(this);
        this.setFocusable(true);
        setBackground(Color.BLACK);

        int cntrX = 400; // width / 2;
        int cntrY = 400; // height / 2;

        x1Ship = cntrX;
        x2Ship = cntrX - 20;
        x3Ship = cntrX + 20;

        y1Ship = cntrY - 60;
        y2Ship = cntrY + 15;
        y3Ship = cntrY + 15;

        ship.addPoint(x1Ship, y1Ship);
        ship.addPoint(x2Ship, y2Ship);
        ship.addPoint(x3Ship, y3Ship);

        

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (pressedKeys.contains(e.getKeyCode()) == false) {
            pressedKeys.add(e.getKeyCode());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int index = pressedKeys.indexOf(e.getKeyCode());
        if (index > -1) {
            pressedKeys.remove(index);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // active l'antialiasing
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHints(rh);

        g2d.setColor(Color.RED);
        g2d.fillOval(400, 400, 3, 3);

        /* dessine le Ship */
        g2d.setColor(Color.WHITE);
        rotateShip(this.rotation);
        g2d.draw(ship);
        asteroid.drawAsteroid(g);
        for (int i = 0; i < lasers.size(); i++) {
            Laser laser = lasers.get(i);

            laser.drawLaser(g);
            // si le laser sort de l'écran, on le supprime de la liste
            if (laser.getX() > 800 || laser.getY() > 800 || laser.getX() < 0 || laser.getY() < 0) {
                lasers.remove(i);
            }
            // asteroid dans liste 
            if (asteroid.contains(laser.getX(), laser.getY()) == true) {
                System.out.println("Collision");
            }
        }

        for (int i = 0; i < pressedKeys.size(); i++) {
            int k = pressedKeys.get(i);
            if (k == KeyEvent.VK_LEFT) {
                this.rotation -= 5;
            }
            if (k == KeyEvent.VK_RIGHT) {
                this.rotation += 5;
            }
            if (k == KeyEvent.VK_SPACE) {
                Laser laser = new Laser(ship.xpoints[0], ship.ypoints[0]);
                lasers.add(laser);
            }
        }
    }

    private void rotateShip(int angle) {
        AffineTransform at = new AffineTransform();
        at.rotate(Math.toRadians(angle), 400, 400);
        // on reprend les coordonnées initiales avant d'appliquer la rotation
        Point p1 = new Point(x1Ship, y1Ship);
        Point p2 = new Point(x2Ship, y2Ship);
        Point p3 = new Point(x3Ship, y3Ship);

        at.transform(p1, p1);
        at.transform(p2, p2);
        at.transform(p3, p3);

        ship.reset();
        ship.addPoint(p1.x, p1.y);
        ship.addPoint(p2.x, p2.y);
        ship.addPoint(p3.x, p3.y);
    }

}