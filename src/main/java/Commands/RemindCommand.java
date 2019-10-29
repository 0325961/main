package Commands;

import DukeExceptions.DukeException;
import DukeExceptions.DukeInvalidDateTimeException;

import Commons.LookupTable;
import Commons.Reminder;
import Commons.Storage;
import Commons.Ui;
import Parser.DateTimeParser;
import Tasks.Assignment;
import Tasks.TaskList;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;
import java.util.Date;
import java.util.Timer;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Represents the command to set a reminder for the user
 */
public class RemindCommand extends Command {

    private Assignment task;
    private Date time;
    private boolean remind;
    private Reminder reminder;
    private static final Logger LOGGER = Logger.getLogger(RemindCommand.class.getName());

    /**
     * Creates RemindCommand object
     * @param task Task to have a reminder set
     * @param time Time for the reminder to be set at
     * @param remind Whether a reminder needs to be set
     */
    public RemindCommand (Assignment task, Date time, boolean remind) {
        this.task = task;
        this.time = time;
        this.remind = remind;
    }

    /**
     * Sets a reminder pop-up for task user wants to set a reminder to.
     * @param events The TaskList object for events
     * @param deadlines The TaskList object for deadlines
     * @param ui The Ui object to display the done task message
     * @param storage The Storage object to access file to load or save the tasks
     * @return This returns the method in the Ui object which returns the string to display remind message
     * @throws DukeException On invalid task and time input
     */
    @Override
    public String execute(LookupTable LT, TaskList events, TaskList deadlines, Ui ui, Storage storage) throws Exception {
        reminder = storage.getReminderObject();
        reminder.setDeadlines(deadlines);
        HashMap<String, HashMap<String, ArrayList<Assignment>>> deadlineMap = deadlines.getMap();
        HashMap<Date, Assignment> remindMap = reminder.getRemindMap();
        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy hh:mm a");
        String reminderTime = dateFormat.format(time);
        String taskDateTimeString = task.getDateTime();
        Date taskDateTime = DateTimeParser.deadlineTaskStringToDate(taskDateTimeString);
        if(taskDateTime.before(currentDate)) {
            throw new DukeInvalidDateTimeException("Sorry, your selected task has already passed!");
        }
        if (!remind) {
            if (!remindMap.containsKey(time)) {
                throw new DukeInvalidDateTimeException("Sorry, you have no such reminder at that inputted time.");
            } else if (!remindMap.get(time).getDescription().equals(task.getDescription())) {
                throw new DukeInvalidDateTimeException("Sorry, you have no such reminder with inputted description at that time");
            }
            reminder.removeTimerTask(task, time, reminderTime);
            return ui.showCancelReminder(task, reminderTime);
        }
        if (this.time.before(currentDate)) {
            throw new DukeInvalidDateTimeException("Sorry, you cannot set a time that has already passed!");
        }
        if (remindMap.containsKey(time)) {
            Assignment remindedTask = remindMap.get(time);
            throw new DukeInvalidDateTimeException("Sorry, you have a reminder set for " + remindedTask.getDescription() + " at: " + task.getDateTime());
        } else if (!deadlineMap.containsKey(task.getModCode())) {
            throw new DukeException("Sorry, you have no such mod entered in your deadline table!");
        } else if (!deadlineMap.get(task.getModCode()).containsKey(task.getDate())) {
            throw new DukeException("Sorry, you have no such timing entered in your deadline table!");
        } else {
            ArrayList<Assignment> allTaskInDate = deadlineMap.get(task.getModCode()).get(task.getDate());
            boolean hasTask = false;
            for (Assignment taskInList : allTaskInDate) {
                if (taskInList.getDescription().equals(task.getDescription())) {
                    hasTask = true;
                    break;
                }
            }
            if (!hasTask) {
                throw new DukeException("Sorry, there are no such task description in your deadline table!");
            }
        }
        reminder.setReminderThread(time, task);
        return ui.showReminder(task, reminderTime);
    }
}