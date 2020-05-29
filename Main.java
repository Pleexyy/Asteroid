import java.awt.*;
import javax.swing.JFrame;
import java.lang.Thread;

public class Main {
    private static JFrame frame = new JFrame("Asteroid");

    public static void main(String[] args) {

        // if (args[0].equals("0")) {
        // frame.dispose();
        // }
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        Container contentPane = frame.getContentPane();
        AsteroidGame ag = new AsteroidGame(800, 800);
        contentPane.add(ag);
        // permet de centrer la frame au milieu de l'écran
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
        frame.setVisible(true);

        Toolkit toolkit = Toolkit.getDefaultToolkit();

        while (true) {
            ag.repaint();
            toolkit.sync();
            try {
                Thread.sleep(1000 / 60);
            } catch (InterruptedException e) {
            }
        }

    }

    public static JFrame getFrame() {
        return frame;
    }
}
