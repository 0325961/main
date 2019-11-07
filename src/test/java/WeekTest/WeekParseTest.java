package WeekTest;

import Commands.Command;
import DukeExceptions.DukeInvalidFormatException;
import Parser.WeekParse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

//@@author hwbjerry
/**
 * This class tests WeekParse.
 */
public class WeekParseTest {
    private final String invalidWeek = "Invalid Week. Please enter the command as follows. \n" +
            "show/week 'x' , where 'x' is a digit between 1 - 13 or \n" +
            "'x' is either 'recess', 'reading', or 'exam'";

    private static String validUserInputWithDigitWeek;
    private static String validUserInputWithRecessWeek;
    private static String validUserInputWithReadingWeek;
    private static String validUserInputWithExamWeek;


    private static String userInputWithWeekZero;
    private static String userInputWithWeekFourteen;
    private static String userInputWithRandomStringWeek;
    private static String userInputWithWeekInDecimal;

    private static String userInputWithoutWeek;

    @BeforeAll
    public static void setAllVariables() {
        validUserInputWithDigitWeek = "show/week 5";
        validUserInputWithRecessWeek = "show/week recess";
        validUserInputWithReadingWeek = "show/week reading";
        validUserInputWithExamWeek = "show/week exam";

        userInputWithWeekZero = "show/week 0";
        userInputWithWeekFourteen = "show/week 14";
        userInputWithRandomStringWeek = "a0b1c2";
        userInputWithWeekInDecimal = "show/week 1.1";

        userInputWithoutWeek = "show/week ";
    }

    @Test
    public void weekWithWeekZero(){
        String expected = invalidWeek;
        String actual = null;
        Command command = null;
        try {
            command = new WeekParse(userInputWithWeekZero).parse();
        } catch (DukeInvalidFormatException e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void weekWithWeekFourteen(){
        String expected = invalidWeek;
        String actual = null;
        Command command = null;
        try {
            command = new WeekParse(userInputWithWeekFourteen).parse();
        } catch (DukeInvalidFormatException e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void weekWithRandomStringWeek(){
        String expected = invalidWeek;
        String actual = null;
        Command command = null;
        try {
            command = new WeekParse(userInputWithRandomStringWeek).parse();
        } catch (DukeInvalidFormatException e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void weekWithWeekInDecimal(){
        String expected = invalidWeek;
        String actual = null;
        Command command = null;
        try {
            command = new WeekParse(userInputWithWeekInDecimal).parse();
        } catch (DukeInvalidFormatException e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void weekWithoutWeek(){
        String expected = invalidWeek;
        String actual = null;
        Command command = null;
        try {
            command = new WeekParse(userInputWithoutWeek).parse();
        } catch (DukeInvalidFormatException e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void weekValidUserInputWithDigitWeek() {
        String actual = "No error";
        Command command = null;
        try {
            command = new WeekParse(validUserInputWithDigitWeek).parse();
        } catch (DukeInvalidFormatException e) {
            actual = e.getMessage();
        }
        assertNotNull(command, actual);
    }

    @Test
    public void weekValidUserInputWithRecessWeek() {
        String actual = "No error";
        Command command = null;
        try {
            command = new WeekParse(validUserInputWithRecessWeek).parse();
        } catch (DukeInvalidFormatException e) {
            actual = e.getMessage();
        }
        assertNotNull(command, actual);
    }

    @Test
    public void weekValidUserInputWithReadingWeek() {
        String actual = "No error";
        Command command = null;
        try {
            command = new WeekParse(validUserInputWithReadingWeek).parse();
        } catch (DukeInvalidFormatException e) {
            actual = e.getMessage();
        }
        assertNotNull(command, actual);
    }

    @Test
    public void weekValidUserInputWithExamWeek() {
        String actual = "No error";
        Command command = null;
        try {
            command = new WeekParse(validUserInputWithExamWeek).parse();
        } catch (DukeInvalidFormatException e) {
            actual = e.getMessage();
        }
        assertNotNull(command, actual);
    }
}
