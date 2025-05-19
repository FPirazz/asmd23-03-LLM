package a03a.e2.Llama3_1;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
/*
public class GUILlama extends JFrame {

    private static final long serialVersionUID = -6218820567019985015L;
    private final List<JButton> cells = new ArrayList<>();
    private JButton target;
    private boolean gameStarted = false;
    private PairLlama<Integer, Integer> currentTargetPosition;


    public GUILlama(int width, int height) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(70 * width, 70 * height);

        JPanel panel = new JPanel(new GridLayout(width, height));
        this.getContentPane().add(panel);

        // Initialize target
        ActionListener alInitTarget = e -> initTarget();
        for (int i = 0; i < height; i++) {
            for (int j = width - 1; j >= 0; j--) {
                var pos = new PairLlama<>(j, i);
                final JButton jb = new JButton(pos.toString());
                this.cells.add(jb);
                jb.addActionListener(alInitTarget);
                panel.add(jb);
            }
        }

        // Add action listener to cells
        ActionListener alClickCell = e -> {
            if (gameStarted) {
                var jb = (JButton) e.getSource();
                PairLlama<Integer, Integer> pos = getPosFromButton(jb);
                int x = pos.getX(), y = pos.getY();

                if (!x.equals(currentTargetPosition.getX())) {
                    drawTrajectory(x, y);
                } else {
                    System.out.println("Hit!");
                    this.dispose();
                }
            }
        };

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                var pos = new PairLlama<>(j, i);
                final JButton jb = new JButton(pos.toString());
                this.cells.add(jb);
                jb.addActionListener(alClickCell);
                panel.add(jb);
            }
        }

        // Initialize random target position
        initTarget();

        this.setVisible(true);
    }

    private void initTarget() {
        if (!gameStarted) {
            Random r = new Random();
            int x = r.nextInt(10); // 0-9
            target = cells.get(width * height - 1 - (x + width * height));
            currentTargetPosition = new PairLlama<>(x, 0);
            target.setText("O");
        }
    }

    private void drawTrajectory(int x, int y) {
        gameStarted = true;
        for (int i = y; i >= 0 && i <= height - 1; i--) {
            var pos = new PairLlama<>(x, i);
            JButton jb = getButton(pos);
            if (jb != null && !pos.equals(currentTargetPosition)) {
                String s = "B";
                if (i > currentTargetPosition.getY()) {
                    s += "*"; // Down
                } else if (i < currentTargetPosition.getY()) {
                    s += "^"; // Up
                }
                jb.setText(s);
            }
        }

        for (int i = y + 1; i <= height - 1; i++) {
            var pos = new PairLlama<>(x, i);
            JButton jb = getButton(pos);
            if (jb != null) {
                String s = "B";
                if (i > currentTargetPosition.getY()) {
                    s += "*"; // Down
                } else if (i < currentTargetPosition.getY()) {
                    s += "^"; // Up
                }
                jb.setText(s);
            }
        }

        Thread t = new Thread(() -> {
            try {
                Thread.sleep(500); // 0.5 seconds
                while (x <= width - 1) {
                    x++;
                    drawTrajectory(x, y);
                    System.out.println("Moving to (" + x + ", " + currentTargetPosition.getY() + ")");
                    Thread.sleep(200); // 0.2 seconds
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        t.start();
    }

    private JButton getButton(PairLlama<Integer, Integer> pos) {
        int x = pos.getX(), y = pos.getY();
        return cells.get(width * y + x);
    }

    public static void main(String[] args) {
        new GUILlama(10, 5);
    }
}
*/