import java.awt.Graphics;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class AsteroidGame extends JPanel implements KeyListener {
    private static final long serialVersionUID = 1L;
    private int rotation = 0;
    private int x1, x2, x3, y1, y2, y3, locX, locY;
    private boolean shot = false;

    public AsteroidGame(int width, int height) {
        super();
        addKeyListener(this);
        this.setFocusable(true);
        setBackground(Color.BLACK);

        int cntrX = width / 2;
        int cntrY = height / 2;

        x1 = cntrX;
        x2 = cntrX - 20;
        x3 = cntrX + 20;

        y1 = cntrY - 60;
        y2 = cntrY + 15;
        y3 = cntrY + 15;
        System.out.println(cntrX);
        System.out.println(cntrY);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int k = e.getKeyCode();
        if (k == KeyEvent.VK_A) {
            this.rotation += -5;
            this.repaint();
        }
        if (k == KeyEvent.VK_E) {
            this.rotation += 5;
            this.repaint();
        }
        if (k == KeyEvent.VK_SPACE) {
            System.out.println("Fire ---");
            shot = true;
            this.repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g);
        g2d.setColor(Color.WHITE);
        g2d.rotate(Math.toRadians(this.rotation), (x1 + x2 + x3) / 3, (y1 + y2 + y3) / 3);
        g2d.drawPolygon(new int[] { x1, x2, x3 }, new int[] { y1, y2, y3 }, 3);
    }

    public void drawLaser(Graphics g) {
        super.paintComponents(g);
        locX = x1;
        locY = y1 - 10;
        g.setColor(Color.WHITE);
        g.drawLine(locX, locY, locX, locY);
    }

    public void paint(Graphics g) {
        super.paint(g);
        // tir si la touche est activée
        if (shot == true) {
            drawLaser(g);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Asteroid");
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        Container contentPane = frame.getContentPane();
        contentPane.add(new AsteroidGame(800, 800));
        // permet de centrer la frame au milieu de l'écran
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
        frame.setVisible(true);
    }

}