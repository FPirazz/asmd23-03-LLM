# 03Lab - Application of LLM is Software Engineering

## Task 1: CODE GENERATION
**Goal**: evaluate the effectiveness of various LLMs in generating solutions for OOP exam from previous years. <br>
**Task**: utilize multiple LLMs (such as ChatGPT, GitHub Copilot, and Codex) to attempt solving past OOP exam questions. 
Assess which models deliver accurate solutions and document any modifications you apply to enhance the models’ responses. <br>
**Additional Task**: experiment with different prompting strategies (e.g., zero-shot, few-shot) to understand their 
impact on solutions.


### Work Done:

I used as a baseline for all tests with all LLMs, which I've chosen to be ChatGPT, Copilot and LLama, and the exercise 
e2, from batch a03a, from the 2023 OOP exams. The task at hand was originally written in Italian, but I choose to 
translate to English, as to keep everything the same as with every other task also. The original task can be found 
[here](src/main/java/a03a.e2/Test.java), the translation used for the prompt is as following:
```
The purpose of this exercise is to create a GUI whose objective is to create a trajectory with a bounce to hit a target, considering the GUI is made up of an arbitrary number of buttons:
1 - When the application is first executed at the beginning, randomly place an "o" in the last column
2 - The user then can click on any cell of the grid: when clicked, a diagonal trace towards the top-right must appear in one go, which when it reaches the upper side (or the lower side) bounces, this continues until it reaches the last column
3 - if the trace does not hit (i.e. goes above or below) the "o", then the user is given the possibility to try again, as in point 2
4 - if the trace hits the "o", close the application

The following are considered optional for the purpose of being able to correct the exercise:
- separation via delegation of all aspects that are not view in an interface + external class
- management of the end of the game

The GUI class provided, to be modified, includes code that could be useful for the solution.
```
This is part of the prompt given to the LLMs, other than this, the other necessary files have been provided (which is the
context of the exercise) in order for the models to actual produce something usable, therefore, the final prompt looks 
something like this:
```
Can you pretty pretty please help solve a Java exercise, considering that:
The purpose of this exercise is to create a GUI whose objective is to create a trajectory with a bounce to hit a target, considering the GUI is made up of an arbitrary number of buttons:
1 - When the application is first executed at the beginning, randomly place an "o" in the last column
2 - The user then can click on any cell of the grid: when clicked, a diagonal trace towards the top-right must appear in one go, which when it reaches the upper side (or the lower side) bounces, this continues until it reaches the last column
3 - if the trace does not hit (i.e. goes above or below) the "o", then the user is given the possibility to try again, as in point 2.
4 - if the trace hits the "o", close the application

The following are considered optional for the purpose of being able to correct the exercise:
- separation via delegation of all aspects that are not view in an interface + external class
- management of the end of the game



The GUI class provided, to be modified, includes code that could be useful for the solution. The classes provided are the GUI, which is this:
"
...
",
And a support class called Pair:
"
...
"
Given this information, please solve the exercise to the best of your capabilities.
```
The prompt slightly varies based on the model, that meaning that both GPT and LLama require the classes to be pasted
"manually", meanwhile Copilot as a button on the UI to reference project classes.

#### **ChatGPT**

As stated in previous labs, GPT offers a "Reason" and "Search the Web" Button on their UI, which I used to let the model
do more reasoning, and search the internet for information if needed.

The prompt was based on a "zero-shot" strategy, and to start off *technically* the provided information from the model
does solve the task at hand, but looking more in-depth at the provided code, what's in there, how is it written and most
importantly if it goes against what is asked is another thing. The class provided can be found inside the 
[GPT](src/main/java/a03a/e2/GPT) package, [here](src/main/java/a03a.e2/GUIGPT.java), with the annexed TestGPT class to 
start it.

Going point per point, the model doesn't satisfy technically this part:
```
2 - The user then can click on any cell of the grid: when clicked, a diagonal trace towards the top-right must appear in one go, which when it reaches the upper side (or the lower side) bounces, this continues until it reaches the last column
```
Meaning that the trace does appear of "X" does appear, but not instantly, instead it's based on a timer (a Swing Timer
to be exact); then, also important, every cell is filled with the wrong text, more specifically it's filled with the name
of the Pair indicating the coordinate of that button, which wasn't asked (as we can see in the image below).
![java_MpOB6IGVVK.png](READMEimages/java_MpOB6IGVVK.png)

Thereafter again, the problems stands in the last two optional points, GPT absolutely ignored the fact that the logic
should've been implemented in separate classes, interface + implementation, which would've given a clearer cut of how
the application was implemented; therefore we can safely say that the code produced was definitely low quality, even if
it worked, considering the amount of precise prompting we did to it.

#### **Copilot**

Once again I used a zero-shot strategy for the prompt in this case.

Through Copilot the code that was produced was actually very decent in terms of separating all the classes, as it can be
seen in the [*Copilot*](src/main/java/a03a/e2/Copilot) package, producing the GameLogicCopilot interface, 
GameLogicImplementationCopilot implementation of the interface, and a modified version of the GUI considering the 
presented GameLogic.

The classes actually present some minor problems: Aside from the usual cleaning up, the GUI had a method non-existing
in the Logic, which I had to comment out, but the application still works because the logic still detects when the "o" is
hit, and exits the application. Other problems is the fact that the buttons of the grid still have that weird text with
the "Pair" initials, and the diagonals are still drawn with a Swing Timer, I suspect this is the case because both GPT
and Copilot use the same models, so they might easily produce similar results.

All in all, as much as the application still presents minor problems, it fared way better than the normal GPT model, even
with using the *Reasoning* and *Search The Web* options.

#### **LLama**

For the Llama models, I firstly used a Llama3.1 model with 8 billion parameters, and the result given can be seen in the
[Llama3_1](src/main/java/a03a/e2/Llama3_1) package, more precisely this file 
[GUILlama.java](src/main/java/a03a/e2/Llama3_1/GUILlama.java). Unfortunately the file presents many problems, and even
given the context it couldn't give me a good GUI file to run, even by trying to fix it the GUI wouldn't run.
On top of that, locally run Llama models, require to use the command line for prompts, which is not very easy to use
when dealing information like entire snippets of classes.

## Task 2: TESTING
**Goal**: analyze the quality of test cases generated by LLMs for existing solutions to OOP exams <br>
**Task**: remove the existing tests and use LLMs to regenerate test cases. Evaluate whether the newly generated tests 
are comprehensive and retain the characteristics of the original tests. Try to guide the LLMs to generate tests that 
are more effective and efficient.

### Work Done:

Once again, I'm using the same LLMs as the ones mentioned in the task before, and I used exercise ***a01c***, from the
2023 OOP exams, the first exercise. The main exercise was about building a Java factory captured by the Timesheet
interface, and the test class [TestE1.java](src/test/java/a01c/sol1/TestE1.java) reflects that. First thing I did, was
to feed each model a prompt, containing the classes needed to build the tests, and asking the LLMs to build an
appropriate test class. The prompt used generally speaking is:
```
Could you pretty pretty please help me build complete and significant tests for a Java application I've written? The
context of the application was to build a Java factory based on an interface called TimeSheet, which represents a work
Timesheet as the name implies. Consider the following classes that include information to be included in whichever way
is possible to build the tests:
TimeSheet interface:
"
...
",

TimeSheetFactory interface:
"
...
",

The TimeSheetFactoryImplementation of the interface:
"
...
",

And finally a support Pair class:
"
...
"
```

#### **GPT**:

The results given by GPT can be seen in the class [TestE1GPT.java](src/test/java/a01c/sol1/TestE1GPT.java), and considering
the results it's actually very surprising, because of how good it is. GPT managed to cover test cases that were included
in the original test file [here](src/test/java/a01c/sol1/TestE1.java), and even then, went above and beyond and made
several more tests to test if BoundsPerDay and BoundsPerActivity were both valid and invalid, giving great leeway for
even more tests to be dispatched, and on top of it all, considering that they are also well written syntactically.

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