# 03Lab - Application of LLM is Software Engineering

## Task 3: TDD
**Goal**: investigate the effectiveness of Copilot in a Test-Driven Development (TDD) scenario. <br>
**Task**: apply TDD principles to solve a given a PPS exercise (https://github.com/unibo-pps/pps-23-24-lab01b, exercise 2) 
using an LLM (you can use both Java or Scala to implement it). Assess whether incremental steps and test-first 
development aid the LLM in generating correct code solutions.

### Work Done:

As the task asked, I took the second exercise from the posted GitHub repository, and pasted it into this project. The
task asked to apply TDD to resolve the exercise, in this case developing tests, what I did is I took it a step further,
and I directly asked Copilot to read the provided README file, which included several steps, the first of which was to
develop tests classes, and also do the all the other steps, to see if the model could deal with multi-steps tasks.

The result is as follow, the prompt given to copilot was this:
```
Analyzing the content of the README referenced file, and the content of all the other referenced java files, you could 
pretty pretty please try to implement the steps described in the README file to the best of your abilities, making sure
 to pay extra attention to the tests to produce, knowing what's the structure of the Logic and LogicImpl classes?
```
And adding basically all the files contained in the e2 package, aside from the PositionGenerator interface, the
RandomPositionGenerator class and the "old" version of the LogicsImpl class (I will explain why I say old in a second).

Copilot operated as instructed, and tried to execute all the steps contained in the README file, which first of all 
involves the creation of a test file [here](src/test/java/e2/LogicsImplTest.java), but unfortunately the tests did *not*
much work, I actually tried several different prompts and ways to make sure Copilot was reading correctly the package
files, but alas it was to not avail. 
Thereafter, Copilot went on to step 2 to refactor, and what it produced was the PositionGenerator classe:
```
package e2;

public interface PositionGenerator {
    Pair<Integer, Integer> generate(int size);
}
```
And the RandomPositionGenerator class:
```
package e2;

import java.util.Random;

public class RandomPositionGenerator implements PositionGenerator {
    private final Random random = new Random();

    @Override
    public Pair<Integer, Integer> generate(int size) {
        return new Pair<>(random.nextInt(size), random.nextInt(size));
    }
}
```
And finally, to change LogicsImpl to make use of these classes, [here](src/main/java/e2/LogicsImpl.java), which all 
worked fine by manually launching the application with the GUI, but obviously the tests implemented earlier still weren't
working unfortunately.

I tried to prompt Copilot to *just* develop the tests using TDD, to try to reduce the scope of the task asked of the LLM
to see if it could fare better, and I used this prompt:
```
Analyzing the content of the README referenced file, and the content of all the other referenced java files, you could 
pretty pretty please try to implement the first step of the reference README file, which is to basically develop some 
tests regarding the LogicsImpl class, using TDD?
```
Once again, using as reference all the classes needed. Unfortunately, **again**, Copilot produced a class that was not
correct and usable, even more, it was filled with more mistakes than the one produced earlier, with the code being as
follows:
```
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
        // Assuming initial knight position is (0, 0)
        logics = new LogicsImpl(5, () -> new Pair<>(0, 0));
        assertTrue(logics.hit(2, 1)); // Valid move
    }

    @Test
    void testInvalidKnightMove() {
        // Assuming initial knight position is (0, 0)
        logics = new LogicsImpl(5, () -> new Pair<>(0, 0));
        assertFalse(logics.hit(3, 3)); // Invalid move
    }

    @Test
    void testKnightHitsPawn() {
        // Assuming initial knight position is (0, 0) and pawn position is (2, 1)
        logics = new LogicsImpl(5, new PositionGenerator() {
            @Override
            public Pair<Integer, Integer> generate(int size) {
                return new Pair<>(0, 0);
            }
        });
        ((LogicsImpl) logics).setPawnPosition(new Pair<>(2, 1));
        assertTrue(logics.hit(2, 1)); // Move knight to pawn position
    }
}
```
So all in all, Copilot doesn't seem to still have good grasp on some logical concepts for applications, or at least
regarding applications where a somewhat complicated logic is present (like in our case, being a chess-lite application)
when it comes to actually writing tests. Meanwhile, interacting and even changing the logic of the application seems to
be of no problem for it. This hypothetically could indicate a problem regarding the knowledge base of the LLM, meaning
that it lacks information regarding TDD and intricate testing in general, *OR* maybe the use of even better 
prompt-engineering could help.