package a03a.e2.GPT;

import a03a.e2.Pair;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.util.*;
import java.util.List;

public class GUIGPT extends JFrame {

    private static final long serialVersionUID = -6218820567019985015L;

    private final List<JButton> cells = new ArrayList<>();
    private int gridWidth, gridHeight;
    private Pair<Integer,Integer> target;
    private boolean isAnimating = false; // to prevent multiple concurrent trajectories

    public GUIGPT(int width, int height) {
        super("Bounce to Target");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.gridWidth = width;
        this.gridHeight = height;
        this.setSize(70 * width, 70 * height);

        JPanel panel = new JPanel(new GridLayout(height, width));
        this.getContentPane().add(panel);

        // Create the grid of buttons and store each cell's coordinate in a client property
        for (int i = 0; i < gridHeight; i++){
            for (int j = 0; j < gridWidth; j++){
                Pair<Integer, Integer> pos = new Pair<>(j, i);
                JButton jb = new JButton(pos.toString());
                jb.putClientProperty("pos", pos);
                jb.addActionListener(e -> {
                    if (isAnimating) return; // ignore clicks during animation
                    // retrieve the starting position from the clicked button
                    @SuppressWarnings("unchecked")
                    Pair<Integer,Integer> startPos = (Pair<Integer,Integer>)((JButton)e.getSource()).getClientProperty("pos");
                    // If user clicks the target directly, win immediately.
                    if (startPos.equals(target)) {
                        JOptionPane.showMessageDialog(null, "You hit the target immediately! Exiting.");
                        System.exit(0);
                    }
                    startTrajectory(startPos);
                });
                cells.add(jb);
                panel.add(jb);
            }
        }

        // randomly place the target "o" in the last column (column index gridWidth-1)
        Random rand = new Random();
        int targetRow = rand.nextInt(gridHeight);
        target = new Pair<>(gridWidth - 1, targetRow);
        getButtonAt(target).setText("o");

        this.setVisible(true);
    }

    // Returns the button at a given (col, row) coordinate
    private JButton getButtonAt(Pair<Integer,Integer> pos) {
        int col = pos.getX();
        int row = pos.getY();
        // Note: cells are stored row-major: row * gridWidth + col
        int index = row * gridWidth + col;
        return cells.get(index);
    }

    // Launches the diagonal trace from the given start position.
    // The trace goes in a top-right direction (initially) and bounces when reaching top or bottom.
    private void startTrajectory(Pair<Integer,Integer> startPos) {
        isAnimating = true;

        // starting coordinates and initial direction (dx always 1; dy = -1 means upward)
        final int dx = 1;
        final int[] dy = new int[] { -1 };
        final int[] currentX = new int[] { startPos.getX() };
        final int[] currentY = new int[] { startPos.getY() };

        // clear previous trace markers (except the target)
        clearTrace();

        // mark starting cell (if not target)
        if (!startPos.equals(target)) {
            getButtonAt(startPos).setText("x");
        }

        // use a Swing Timer to animate the trace steps
        Timer timer = new Timer(200, null);
        timer.addActionListener(e -> {
            int nextX = currentX[0] + dx;
            int nextY = currentY[0] + dy[0];

            // Bounce if nextY is out of bounds (upper or lower)
            if (nextY < 0) {
                nextY = 1; // bounce from top edge
                dy[0] = 1;
            } else if (nextY >= gridHeight) {
                nextY = gridHeight - 2; // bounce from bottom edge
                dy[0] = -1;
            }

            currentX[0] = nextX;
            currentY[0] = nextY;
            Pair<Integer,Integer> nextPos = new Pair<>(nextX, nextY);
            JButton btn = getButtonAt(nextPos);

            // mark the cell with trace (if not the target cell)
            if (!nextPos.equals(target)) {
                btn.setText("x");
            }

            // Check if we have hit the target (even if target cell already has "o")
            if (nextPos.equals(target)) {
                timer.stop();
                JOptionPane.showMessageDialog(null, "Target hit! Exiting.");
                System.exit(0);
            }

            // Stop when reaching the last column (without hitting the target)
            if (nextX == gridWidth - 1) {
                timer.stop();
                isAnimating = false;
            }
        });
        timer.start();
    }

    // Clear all cells from any trace markers ("x") while keeping the target "o"
    private void clearTrace() {
        for (JButton jb : cells) {
            // Do not clear the target cell text
            @SuppressWarnings("unchecked")
            Pair<Integer,Integer> pos = (Pair<Integer,Integer>) jb.getClientProperty("pos");
            if (pos.equals(target)) continue;
            // Reset to the default text (the coordinates)
            jb.setText(pos.toString());
        }
    }
}
