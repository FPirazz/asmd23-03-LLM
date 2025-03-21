package e2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LogicsImplTest {

    private Logics logics;

    @BeforeEach
    void setUp() {
        logics = new LogicsImpl(5);
    }

    @Test
    void testInitialPositions() {
        assertNotNull(logics);
        assertTrue(logics.hasPawn(0, 0) || logics.hasPawn(4, 4));
        assertTrue(logics.hasKnight(0, 0) || logics.hasKnight(4, 4));
    }

    @Test
    void testValidKnightMove() {
        assertTrue(logics.hit(2, 1)); // Valid move
    }

    @Test
    void testInvalidKnightMove() {
        assertFalse(logics.hit(3, 3)); // Invalid move
    }

    @Test
    void testKnightHitsPawn() {
        assertTrue(logics.hit(2, 1)); // Move knight to pawn position
    }
}