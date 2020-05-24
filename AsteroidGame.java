import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Timer;
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
    private ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();
    private JLabel scoreLabel, timeLabel;
    private int score = 0;

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

        // ajout de 3 astéroidess à notre liste, à des positions aléatoires
        // (dans le constructeur pour éviter d'en ajouter 60 par seconde)
        Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int max = 750;
                int min = 1;
                int range = max - min + 1;

                for (int i = 0; i < 3; i++) {
                    int randx = (int) (Math.random() * range) + min;
                    int randy = (int) (Math.random() * range) + min;

                    Asteroid asteroid = new Asteroid(randx, randy);
                    asteroids.add(asteroid);
                }
            }
        });
        timer.setRepeats(true);
        timer.start();

        scoreLabel = new JLabel("Score : 0");
        scoreLabel.setForeground(Color.white);

        add(scoreLabel);

        timeLabel = new JLabel();
        timeLabel.setForeground(Color.white);
        timer();
        add(timeLabel);

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
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            Laser laser = new Laser(ship.xpoints[0], ship.ypoints[0]);
            lasers.add(laser);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // active l'antialiasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // active l'antialiasing pour le texte
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g2d.setColor(Color.RED);
        g2d.fillOval(400, 400, 3, 3);

        /* dessine le Ship */
        g2d.setColor(Color.WHITE);
        rotateShip(this.rotation);
        g2d.draw(ship);

        // boucle d'affichage des astéroides
        for (int j = 0; j < asteroids.size(); j++) {
            asteroids.get(j).drawAsteroid(g);
        }

        // boucle d'affichage des lasers
        for (int i = 0; i < lasers.size(); i++) {
            Laser laser = lasers.get(i);
            laser.drawLaser(g);
        }

        for (int i = 0; i < lasers.size(); i++) {
            Laser laser = lasers.get(i);
            // si le laser sort de l'écran, on le supprime de la liste
            if (laser.getX() > 800 || laser.getY() > 800 || laser.getX() < 0 || laser.getY() < 0) {
                lasers.remove(i);
            }
            for (int j = 0; j < asteroids.size(); j++) {
                // si le missile touche l'asteroid, elle est détruite
                if (asteroids.get(j).contains(laser.getX(), laser.getY()) == true) {
                    // supprime l'astéroide de la liste
                    asteroids.remove(j);
                    // supprime le laser de la liste. Le laser est donc supprimé s'il touche une
                    // cible
                    lasers.remove(i);
                    // incrémente le score dès qu'un astéroide est touché
                    scoreValue();
                }
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

    public void scoreValue() {
        score++;
        scoreLabel.setText("Score : " + score);
    }

    private void timer() {
        new Thread() {
            int counter = 30;

            public void run() {
                while (counter >= 0) {
                    timeLabel.setText("Temps restant : " + (counter--));
                    try {
                        Thread.sleep(1000);
                        // appelle la fonction endgame si le temps est écoulé
                        if (counter == 0) {
                            endGame();
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }.start();
    }

    private void endGame() {
        // supprime la fenetre du jeu
        Main.getFrame().dispose();
        JFrame frame2 = new JFrame();
        // affiche une boite de dialogue qui permet à l'utilisateur de re-commencer ou
        // quitter le jeu
        Object[] options = { "Recommencer", "Quitter" };
        int n = JOptionPane.showOptionDialog(frame2, "Le jeu est terminé ! Votre score est de " + score, "",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        System.out.println("Fin de la partie");
        if (n == 0) {
            // System.exit(0); // temporaire. Le jeu est sensé recommencer
            // String[] arguments = new String[] {"0"};
            // Main.main(arguments);
            // new Main();
            System.exit(0);
        } else
            System.exit(0);
    }

}