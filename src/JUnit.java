import org.junit.Before;
import org.junit.Test;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static org.junit.Assert.*;

public class JUnit {

    TaskItem task;

    String validTitle="Test Title";
    String validDescription ="Test Description";
    LocalDate validDueDate=LocalDate.parse("2020-12-31");

    @Before
    public void setUp() throws Exception {
        try {
            task = new TaskItem(validTitle, validDescription, validDueDate);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testValidTitle() {
        assertEquals(validTitle,task.getTitle());
    }

    @Test
    public void testEmptyTitle() {
        boolean success=false;

        try {
            task.setTitle("");
        }
        catch (NullPointerException e) {
            success = true;
        }

        assertEquals(true,success);
    }

    @Test
    public void testTrimEmptyTitle() {
        boolean success=false;

        try {
            task.setTitle("     ");
            fail("should have thrown a NullPointerException");
        } catch (NullPointerException e) {
            success = true;
        }

        assertEquals(true,success);
    }

    @Test
    public void testNullTitle() {
        boolean success=false;

        try {
            task.setTitle(null);
            fail("should have thrown a NullPointerException");
        } catch (NullPointerException e) {
            success = true;
        }

        assertEquals(true,success);
    }

    @Test
    public void testValidProject() {
        assertEquals(validDescription,task.getDescription());
    }

    @Test
    public void testEmptyProject() {
        task.setDescription("");
        assertEquals("",task.getDescription());
    }

    @Test
    public void testValidDueDate() {
        assertEquals(validDueDate,task.getDueDate());
    }

    @Test
    public void testIncorrectFormatDueDate() {
        boolean success=false;

        try {
            task.setDueDate(LocalDate.parse("2020-31-12"));
            fail("should have thrown a DateTimeParseException");
        } catch (DateTimeParseException e) {
            success = true;
        }

        assertEquals(true,success);
    }

    @Test
    public void testPastDueDate() {
        boolean success=false;

        try {
            task.setDueDate(LocalDate.parse("2017-08-17"));
            fail("should have thrown a DateTimeParseException");
        } catch (DateTimeException e) {
            success = true;
        }

        assertEquals(true,success);
    }
}
