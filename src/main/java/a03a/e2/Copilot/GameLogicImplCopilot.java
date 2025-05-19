package a03a.e2.Copilot;

import javax.swing.*;
import javax.swing.Timer;
import java.util.*;

public class GameLogicImplCopilot implements GameLogicCopilot {
    private final List<JButton> cells;
    private final int gridWidth, gridHeight;
    private PairCopilot<Integer, Integer> target;
    private boolean isAnimating = false;

    public GameLogicImplCopilot(List<JButton> cells, int gridWidth, int gridHeight) {
        this.cells = cells;
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
    }

    @Override
    public void placeTarget() {
        Random rand = new Random();
        int targetRow = rand.nextInt(gridHeight);
        target = new PairCopilot<>(gridWidth - 1, targetRow);
        getButtonAt(target).setText("o");
    }

    @Override
    public void startTrajectory(PairCopilot<Integer, Integer> startPos) {
        isAnimating = true;

        final int dx = 1;
        final int[] dy = new int[]{-1};
        final int[] currentX = new int[]{startPos.getX()};
        final int[] currentY = new int[]{startPos.getY()};

        clearTrace();

        if (!startPos.equals(target)) {
            getButtonAt(startPos).setText("x");
        }

        Timer timer = new Timer(200, null);
        timer.addActionListener(e -> {
            int nextX = currentX[0] + dx;
            int nextY = currentY[0] + dy[0];

            if (nextY < 0) {
                nextY = 1;
                dy[0] = 1;
            } else if (nextY >= gridHeight) {
                nextY = gridHeight - 2;
                dy[0] = -1;
            }

            currentX[0] = nextX;
            currentY[0] = nextY;
            PairCopilot<Integer, Integer> nextPos = new PairCopilot<>(nextX, nextY);
            JButton btn = getButtonAt(nextPos);

            if (!nextPos.equals(target)) {
                btn.setText("x");
            }

            if (nextPos.equals(target)) {
                timer.stop();
                JOptionPane.showMessageDialog(null, "Target hit! Exiting.");
                System.exit(0);
            }

            if (nextX == gridWidth - 1) {
                timer.stop();
                isAnimating = false;
            }
        });
        timer.start();
    }

    @Override
    public boolean isAnimating() {
        return isAnimating;
    }

    private JButton getButtonAt(PairCopilot<Integer, Integer> pos) {
        int col = pos.getX();
        int row = pos.getY();
        int index = row * gridWidth + col;
        return cells.get(index);
    }

    private void clearTrace() {
        for (JButton jb : cells) {
            PairCopilot<Integer, Integer> pos = (PairCopilot<Integer, Integer>) jb.getClientProperty("pos");
            if (pos.equals(target)) continue;
            jb.setText(pos.toString());
        }
    }
}