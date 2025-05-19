package a03a.e2.Copilot;

public interface GameLogicCopilot {
    void placeTarget();
    void startTrajectory(PairCopilot<Integer, Integer> startPos);
    boolean isAnimating();
}