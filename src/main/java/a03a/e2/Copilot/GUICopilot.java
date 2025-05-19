package a03a.e2.Copilot;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.util.List;

public class GUICopilot extends JFrame {
    private static final long serialVersionUID = -6218820567019985015L;
    private final List<JButton> cells = new ArrayList<>();
    private final GameLogicCopilot gameLogic;

    public GUICopilot(int width, int height) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(70 * width, 70 * height);

        JPanel panel = new JPanel(new GridLayout(height, width));
        this.getContentPane().add(panel);

        gameLogic = new GameLogicImplCopilot(cells, width, height);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                PairCopilot<Integer, Integer> pos = new PairCopilot<>(j, i);
                JButton jb = new JButton(pos.toString());
                jb.putClientProperty("pos", pos);
                jb.addActionListener(e -> {
                    if (gameLogic.isAnimating()) return;
                    PairCopilot<Integer, Integer> startPos = (PairCopilot<Integer, Integer>) ((JButton) e.getSource()).getClientProperty("pos");
//                    if (startPos.equals(gameLogic.getTarget())) {
//                        JOptionPane.showMessageDialog(null, "You hit the target immediately! Exiting.");
//                        System.exit(0);
//                    }
                    gameLogic.startTrajectory(startPos);
                });
                cells.add(jb);
                panel.add(jb);
            }
        }

        gameLogic.placeTarget();
        this.setVisible(true);
    }
}