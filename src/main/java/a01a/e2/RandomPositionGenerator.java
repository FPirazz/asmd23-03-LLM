package a01a.e2;

import java.util.Random;

public class RandomPositionGenerator implements PositionGenerator {
    private final Random random = new Random();

    @Override
    public Pair<Integer, Integer> generate(int size) {
        return new Pair<>(random.nextInt(size), random.nextInt(size));
    }
}