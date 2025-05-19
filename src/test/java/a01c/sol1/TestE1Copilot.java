package a01c.sol1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.*;

public class TestE1Copilot {

    private final TimeSheetFactory factory = new TimeSheetFactoryImpl();

    @Test
    public void testEmptyData() {
        List<Pair<String, String>> data = Collections.emptyList();
        TimeSheet ts = factory.ofRawData(data);
        assertTrue(ts.activities().isEmpty(), "Activities should be empty");
        assertTrue(ts.days().isEmpty(), "Days should be empty");
        assertTrue(ts.isValid(), "Empty timesheet should be valid");
    }

    @Test
    public void testSingleEntry() {
        List<Pair<String, String>> data = Arrays.asList(new Pair<>("act1", "day1"));
        TimeSheet ts = factory.ofRawData(data);
        assertEquals(1, ts.getSingleData("act1", "day1"), "act1 on day1 should have 1 hour");
        assertTrue(ts.isValid(), "Single entry timesheet should be valid");
    }

    @Test
    public void testMultipleEntriesNoBounds() {
        List<Pair<String, String>> data = Arrays.asList(
                new Pair<>("act1", "day1"),
                new Pair<>("act1", "day2"),
                new Pair<>("act2", "day1")
        );
        TimeSheet ts = factory.ofRawData(data);
        assertEquals(1, ts.getSingleData("act1", "day1"), "act1 on day1 should have 1 hour");
        assertEquals(1, ts.getSingleData("act1", "day2"), "act1 on day2 should have 1 hour");
        assertEquals(1, ts.getSingleData("act2", "day1"), "act2 on day1 should have 1 hour");
        assertTrue(ts.isValid(), "Timesheet with no bounds should be valid");
    }

    @Test
    public void testWithBoundsPerActivityAndDay() {
        List<Pair<String, String>> data = Arrays.asList(
                new Pair<>("act1", "day1"),
                new Pair<>("act1", "day1"),
                new Pair<>("act2", "day2")
        );
        Map<String, Integer> boundsActivities = new HashMap<>();
        boundsActivities.put("act1", 2);
        boundsActivities.put("act2", 1);
        Map<String, Integer> boundsDays = new HashMap<>();
        boundsDays.put("day1", 2);
        boundsDays.put("day2", 1);
        TimeSheet ts = factory.withBounds(data, boundsActivities, boundsDays);
        assertTrue(ts.isValid(), "Timesheet should be valid when both activity and day bounds are met");
    }

    @Test
    public void testWithBoundsPerActivityAndDay_Invalid() {
        List<Pair<String, String>> data = Arrays.asList(
                new Pair<>("act1", "day1"),
                new Pair<>("act1", "day1"),
                new Pair<>("act1", "day1")
        );
        Map<String, Integer> boundsActivities = new HashMap<>();
        boundsActivities.put("act1", 2);
        Map<String, Integer> boundsDays = new HashMap<>();
        boundsDays.put("day1", 2);
        TimeSheet ts = factory.withBounds(data, boundsActivities, boundsDays);
        assertFalse(ts.isValid(), "Timesheet should be invalid as act1 exceeds its bound");
    }
}