package Commons;
import Tasks.Assignment;
import Tasks.TaskList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.Date;

/**
 * Represents the user interface which displays the messages to
 * respond to the user based on the user's input.
 */
public class Ui {
    private static final String NO_FIELD = "void";
    private final String byeMessage = "Bye. Hope to see you again soon!\n";

    /**
     * Displays the exit message when Duke Program ends.
     */
    public String showBye(){
        return byeMessage;
    }

    /**
     * Displays the list message when user inputs list.
     */
    public String showList(TaskList list){
        String listMessage = "Here are the tasks in your list:\n";
        for (int i = 1; i <= list.taskListSize(); i++) {
            listMessage = listMessage + i + "." + list.taskToString(i-1) + "\n";
        }
        return listMessage;
    }

    /**
     * Displays add task message when user wants to add a task.
     */
    public String showAdd(Assignment task, int listSize){
        return "Got it. I've added this task:\n" + task.toString() + "\n"
                + "Now you have " + listSize + (listSize > 1 ? " tasks in the list.\n" : " task in the list.\n");
    }

    /**
     * Displays done task message when user marks a task as done.
     */
    public String showDone(Assignment task){
        return "Nice! I've marked this task as done:\n" + task.toString() + "\n";
    }

    /**
     * Displays the delete task message when user wants to delete a task.
     */
    public String showDelete(Assignment task, int listSize){
        listSize -= 1;
        return "Noted. I've removed this task:\n" + task.toString() + "\n" + "Now you have "
                + listSize  + (listSize > 1 ? " tasks in the list.\n" : " task in the list.\n");
    }

    /**
     * Displays the free time found with the template to be shown.
     * @param message The free times found
     * @return The output to be shown to the user
     */
    public String showFreeTimes(String message){
        return ("You are available at: \n" + message);
    }

    /**
     * Displays the invalid chosen duration message.
     * @param message The chosen free time
     * @return The invalid free time with the proper format
     */
    public String showFreeTimesInvalidDuration(String message){
        return "Invalid duration\n" + "Please enter the command in the format:\n" +
                "find 'x' hours, where 'x' is between 1 - 16";
    }

    /**
     * Displays the invalid chosen week message.
     * @param message The chosen week
     * @return The invalid week entry with the proper format
     */
    public String showWeeksInvalidEntry(String message){
        return "Invalid week\n" + "Please enter the command in the format:\n" +
                "Week 'x', where 'x' is a digit between 1 - 13";
    }

    /**
     * Displays the show reminder message when user sets a reminder for a task.
     */
    public String showReminder(Assignment task, String time) {
        return "Reminder has been set for " + task.getModCode() + " " + task.getDescription() + "at: " + time;
    }

    /**
     * Displays the show cancel reminder message when user sets a reminder for a task.
     */
    public String showCancelReminder(Assignment task, String time) {
        return "Reminder has been removed for " + task.getModCode() + " " + task.getDescription() + "at: " + time;
    }

    /**
     * Displays the error message if a file is not found.
     */
    public String showLoadingError(Exception e){
        return "File not found" + e.getMessage() + "\n";
    }

    /**
     * Displays any of the DukeException error message caught throughout the program.
     */
    public String getError(Exception e){
        return e.getMessage() + "\n";
    }

    /**
     * Displays the show reminder message when user enter a task with a period to do within
     * @param TaskDescription The description of the task entered
     * @param startDate The start date for task
     * @param endDate The end date for task
     * @param isValid determine if user's input date is entered correctly
     * @return This returns the reminder message which contain the task description and the start
     * and end date
     */
    public String showReminder(String TaskDescription, String startDate, String endDate, boolean isValid) {
        if (!isValid) {
            return "Please enter another valid date in format of DD/MM/yyyy";
        } else {
            return "Reminder have been set for: " + TaskDescription + "." + " Start Date: " + startDate +
                    " End Date: " + endDate + "\n";
        }
    }

    /**
     * Display recurring tasks that are added
     * @param description description of recurring task
     * @param startDate  start of recurrence
     * @param endDate   end of recurrence
     *
     */
    public String showRecurring(String description, String startDate, String endDate) {
        return "Recurring task: " + description + " has been added between " + startDate + " and " + endDate + "\n";
    }

    /**
     * Display task with instance of keyword
     * @param list List of task with keyword
     * @param keyword keyword entered by user
     *
     */
    public String showFilter(ArrayList<String> list,String keyword){

        if(list.size() == 0) {
            return "There are no task(s) matching your keyword.\n";
        } else {
            String message = "Here are the following events/deadline with the keyword " + keyword + "\n";
            for (int i = 1; i <= list.size(); i++) {
                message +=  i + ". " + list.get(i - 1) + "\n";
            }
            return message;
        }
    }

    /**
     *Display a guide to commands
     */
    public String showHelp(String help){
        return help;
    }

    /**
     * Display recommended weekly workload
     * @param workloadMap map of weekly workload
     * @return This returns the string of workload
     * @throws ParseException
     */
    public String showWorkload(TreeMap<String, ArrayList<Assignment>> workloadMap) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatter1 = new SimpleDateFormat("E");
        String workloadSchedule = "Here is your recommended schedule for next week:\n";
        if (workloadMap.isEmpty()) {
            return "You have no tasks scheduled for next week! \n";
        } else {
            for (Map.Entry<String, ArrayList<Assignment>> workload: workloadMap.entrySet()) {
                Date tempDay = formatter.parse(workload.getKey());
                String day = formatter1.format(tempDay);
                workloadSchedule = workloadSchedule + day + ": \n";
                for (Assignment task: workload.getValue()) {
                    workloadSchedule = workloadSchedule + task.getType() + " " + task.getModCode() + " "
                            + task.getDescription() + "\n";
                }
            }
        }
        return workloadSchedule;
    }

    public String showPrevious(ArrayList<String> outputList) {
        int size = outputList.size();
        System.out.println(size);
        if (size == 0) {
            String message = "There are no such input type in previous command";
            return message;
        } else {
            String output = "";
            for (int i = 0; i < size; i++) {
                output += (i + 1) + ". " + outputList.get(i);
            }
            return output;
        }
    }

    public String showChosenPreviousChoice(String chosenInput) {
        String message = "Your chosen previous input is: \n" + chosenInput;
        return message;
    }
}