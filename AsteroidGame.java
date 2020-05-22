import java.awt.Graphics;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class AsteroidGame extends JPanel implements KeyListener {
    private static final long serialVersionUID = 1L;
    private int rotation = 0;
    private int x1, x2, x3, y1, y2, y3;

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
        System.out.println("keyPressed");
        int k = e.getKeyCode();
        if (k == KeyEvent.VK_A) {
            this.rotation += -5;
            this.repaint();
        }
        if (k == KeyEvent.VK_E) {
            this.rotation += 5;
            this.repaint();
        }
        if (k == KeyEvent.VK_UP) {
            y1 -= 5;
            y2 -= 5;
            y3 -= 5;
            this.repaint();
        }
        if (k == KeyEvent.VK_DOWN) {
            y1 += 5;
            y2 += 5;
            y3 += 5;
            this.repaint();
        }
        if (k == KeyEvent.VK_LEFT) {
            x1 -= 5;
            x2 -= 5;
            x3 -= 5;
            this.repaint();
        }
        if (k == KeyEvent.VK_RIGHT && x3 != getWidth()) {
            x1 += 5;
            x2 += 5;
            x3 += 5;
            this.repaint();
        } else if (x3 == getWidth()) {
            x1 = 30;
            x2 = 10;
            x3 = 50;
            this.repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void paintComponent(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g);
        g2d.setColor(Color.WHITE);
        g2d.rotate(Math.toRadians(this.rotation), (x1 + x2 + x3) / 3, (y1 + y2 + y3) / 3);
        g2d.drawPolygon(new int[] { x1, x2, x3 }, new int[] { y1, y2, y3 }, 3);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Asteroid");
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        Container contentPane = frame.getContentPane();
        contentPane.add(new AsteroidGame(800, 800));
        frame.setVisible(true);
    }

}