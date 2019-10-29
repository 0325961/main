package Commons;

import Commands.Command;
import Commands.RetrieveFreeTimesCommand;
import Commands.RetrievePreviousCommand;
import Tasks.TaskList;

import Parser.MainParser;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A program that manages tasks input by user
 * with commands that includes adding, deleting, displaying list of tasks
 * and to mark completion of a task.
 */
public class Duke  {

    private final Storage storage;
    private final TaskList events;
    private final TaskList deadlines;
    private final Ui ui;
    private final Reminder reminder;
    private static final Logger LOGGER = Logger.getLogger(Duke.class.getName());
    private static LookupTable LT;
    public static ArrayList<String> userInputs = new ArrayList<>();

    /**
     * Creates Duke object.
     */
    public Duke() {
        ui = new Ui();
        storage = new Storage();
        events = new TaskList();
        deadlines = new TaskList();
        reminder = new Reminder();
        storage.setReminderObject(reminder);
        try {
            storage.readDeadlineList(deadlines);
            storage.readEventList(events);
            reminder.setDeadlines(deadlines);
            storage.setReminderOnStart();
            LT = new LookupTable();
        } catch (Exception e) {
            ui.showLoadingError(e);
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    /**
     * This method runs the Duke program.
     * @param input The user's input to the Duke program
     * @return This returns the string to respond to user's input
     */
    private String run(String input) {
        try {
            Command c = MainParser.parse(input);
            return c.execute(LT,events, deadlines, ui, storage);
        } catch (Exception e) {
            LOGGER.severe(e.toString());
            return ui.getError(e);
        }
    }

    public String getResponse(String input) {
        userInputs.add(input);
        return run(input);
    }

    public static ArrayList<String> getUserInputs() {
        return userInputs;
    }

    public static String getPreviousInput() {
        String previousInput = RetrievePreviousCommand.getChosenOutput();
        return previousInput;
    }

    /*
    This method retrieves the free time option selected by the user
     */
    public static String getSelectedOption() {
        String selectedOption = RetrieveFreeTimesCommand.getSelectedOption();
        return selectedOption;
    }

    public LookupTable getLT() {
        return this.LT;
    }

}