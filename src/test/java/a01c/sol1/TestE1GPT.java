package a01c.sol1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.*;

public class TestE1GPT {

    // Instantiate the factory implementation to create timesheets
    private final TimeSheetFactory factory = new TimeSheetFactoryImpl();

    @Test
    public void testOfRawData() {
        // Prepare raw data: each Pair represents one hour spent
        List<Pair<String, String>> data = Arrays.asList(
                new Pair<>("act1", "day1"),
                new Pair<>("act1", "day1"),
                new Pair<>("act2", "day1"),
                new Pair<>("act2", "day2")
        );
        TimeSheet ts = factory.ofRawData(data);

        // Verify that the counts are correct
        assertEquals(2, ts.getSingleData("act1", "day1"), "act1 on day1 should have 2 hours");
        assertEquals(0, ts.getSingleData("act1", "day2"), "act1 on day2 should have 0 hours");
        assertEquals(1, ts.getSingleData("act2", "day1"), "act2 on day1 should have 1 hour");
        assertEquals(1, ts.getSingleData("act2", "day2"), "act2 on day2 should have 1 hour");

        // Check the distinct activities and days
        Set<String> expectedActivities = new HashSet<>(Arrays.asList("act1", "act2"));
        Set<String> expectedDays = new HashSet<>(Arrays.asList("day1", "day2"));
        assertEquals(expectedActivities, ts.activities(), "Activities should match");
        assertEquals(expectedDays, ts.days(), "Days should match");

        // Since raw timesheets don't enforce bounds, isValid should always return true
        assertTrue(ts.isValid(), "Raw timesheet should be valid");
    }

    @Test
    public void testWithBoundsPerActivity_Valid() {
        List<Pair<String, String>> data = Arrays.asList(
                new Pair<>("act1", "day1"),
                new Pair<>("act1", "day1"),
                new Pair<>("act2", "day2")
        );
        // Set bounds: act1 can have up to 2 hours, act2 up to 1 hour
        Map<String, Integer> bounds = new HashMap<>();
        bounds.put("act1", 2);
        bounds.put("act2", 1);
        TimeSheet ts = factory.withBoundsPerActivity(data, bounds);
        assertTrue(ts.isValid(), "Timesheet should be valid when activity bounds are met");
    }

    @Test
    public void testWithBoundsPerActivity_Invalid() {
        // Create data where act1 accumulates 3 hours, exceeding its bound
        List<Pair<String, String>> data = Arrays.asList(
                new Pair<>("act1", "day1"),
                new Pair<>("act1", "day2"),
                new Pair<>("act1", "day2")
        );
        Map<String, Integer> bounds = new HashMap<>();
        bounds.put("act1", 2);
        TimeSheet ts = factory.withBoundsPerActivity(data, bounds);
        assertFalse(ts.isValid(), "Timesheet should be invalid as act1 exceeds its bound");
    }

    @Test
    public void testWithBoundsPerDay_Valid() {
        List<Pair<String, String>> data = Arrays.asList(
                new Pair<>("act1", "day1"),
                new Pair<>("act2", "day1"),
                new Pair<>("act1", "day2")
        );
        // Set bounds: day1 can have 2 hours, day2 1 hour
        Map<String, Integer> bounds = new HashMap<>();
        bounds.put("day1", 2);
        bounds.put("day2", 1);
        TimeSheet ts = factory.withBoundsPerDay(data, bounds);
        assertTrue(ts.isValid(), "Timesheet should be valid when day bounds are met");
    }

    @Test
    public void testWithBoundsPerDay_Invalid() {
        // Create data where day1 accumulates 3 hours, exceeding its bound
        List<Pair<String, String>> data = Arrays.asList(
                new Pair<>("act1", "day1"),
                new Pair<>("act2", "day1"),
                new Pair<>("act2", "day1")
        );
        Map<String, Integer> bounds = new HashMap<>();
        bounds.put("day1", 2);
        TimeSheet ts = factory.withBoundsPerDay(data, bounds);
        assertFalse(ts.isValid(), "Timesheet should be invalid as day1 exceeds its bound");
    }

    @Test
    public void testWithBounds_BothValid() {
        List<Pair<String, String>> data = Arrays.asList(
                new Pair<>("act1", "day1"),
                new Pair<>("act2", "day1"),
                new Pair<>("act1", "day2")
        );
        // Set activity bounds: act1 up to 2 hours, act2 up to 1 hour
        Map<String, Integer> boundsActivities = new HashMap<>();
        boundsActivities.put("act1", 2);
        boundsActivities.put("act2", 1);
        // Set day bounds: day1 up to 2 hours, day2 up to 1 hour
        Map<String, Integer> boundsDays = new HashMap<>();
        boundsDays.put("day1", 2);
        boundsDays.put("day2", 1);
        TimeSheet ts = factory.withBounds(data, boundsActivities, boundsDays);
        assertTrue(ts.isValid(), "Timesheet should be valid when both activity and day bounds are met");
    }

    @Test
    public void testWithBounds_BothInvalid_ActivityExceeded() {
        // Create data where act1 appears 3 times
        List<Pair<String, String>> data = Arrays.asList(
                new Pair<>("act1", "day1"),
                new Pair<>("act1", "day2"),
                new Pair<>("act1", "day2")
        );
        Map<String, Integer> boundsActivities = new HashMap<>();
        boundsActivities.put("act1", 2);
        Map<String, Integer> boundsDays = new HashMap<>();
        boundsDays.put("day1", 2);
        boundsDays.put("day2", 2);
        TimeSheet ts = factory.withBounds(data, boundsActivities, boundsDays);
        assertFalse(ts.isValid(), "Timesheet should be invalid as act1 exceeds its bound");
    }

    @Test
    public void testWithBounds_BothInvalid_DayExceeded() {
        // Create data where day1 gets 3 hours in total
        List<Pair<String, String>> data = Arrays.asList(
                new Pair<>("act1", "day1"),
                new Pair<>("act2", "day1"),
                new Pair<>("act3", "day1")
        );
        Map<String, Integer> boundsActivities = new HashMap<>();
        boundsActivities.put("act1", 2);
        boundsActivities.put("act2", 2);
        boundsActivities.put("act3", 2);
        Map<String, Integer> boundsDays = new HashMap<>();
        boundsDays.put("day1", 2); // Bound is less than total 3 hours
        TimeSheet ts = factory.withBounds(data, boundsActivities, boundsDays);
        assertFalse(ts.isValid(), "Timesheet should be invalid as day1 exceeds its bound");
    }
}
