package Commands;

import Commons.Duke;
import Commons.LookupTable;
import Commons.Storage;
import Commons.Ui;
import DukeExceptions.DukeInvalidCommandException;
import Tasks.TaskList;
import java.util.ArrayList;

public class ShowPreviousCommand extends Command{

    private String fullCommand;

    /**
     * Creates ShowPreviousCommand object.
     * @param fullCommand The user's input
     */
    public ShowPreviousCommand(String fullCommand) {
        this.fullCommand = fullCommand;

    }

    /**
     * This method adds previous command into the outputList base on user's choice/keyword.
     * @param userInputList The list that contains all user inputs
     * @param outputList The list that contains the inputs the user requested
     * @param string The user's keyword for adding into outputList
     * @return outputList which contains the inputs requested by user
     */
    public ArrayList<String> previousCommandsHandler(ArrayList<String> userInputList, ArrayList<String> outputList, String string) {
        int size = userInputList.size() - 1;
        String userInput;
        for (int j = 0; j < size; j ++) {
            userInput = userInputList.get(j);
            if (userInput.contains(string)) {
                outputList.add(userInput + " \n");
            }
        }
        return outputList;
    }

    public static ArrayList<String> result = new ArrayList<>();
    public ArrayList<String> userInputsList = new ArrayList<>();
    public ArrayList<String> updatedUserInputList = new ArrayList<>();

    /**
     * Shows the previous user inputs that user requested.
     * @param ui The Ui object to display the message to display all the inputs
     * @return This returns the method in the Ui object which returns the string to display the lists
     * of user inputs
     * @throws Exception
     */
    @Override
    public String execute(LookupTable LT, TaskList events, TaskList deadlines, Ui ui, Storage storage) throws DukeInvalidCommandException {
        fullCommand = fullCommand.replace("show/previous", "");
        fullCommand = fullCommand.trim();

        boolean isNumber = true;
        int number = 0;
        try {
            number = Integer.parseInt(fullCommand);
        } catch (NumberFormatException e) {
            isNumber = false;
        }

        ArrayList<String> outputList = new ArrayList<>();
        userInputsList = Duke.getUserInputs();
        int size = userInputsList.size();

        for (int i = 0; i < size; i ++) {
            if (i % 2 == 0) {
                String userInput = userInputsList.get(i);
                updatedUserInputList.add(userInput);
            }
        }

        int sizeOfUpdatedList = updatedUserInputList.size();
        int sizeOfPreviousList = sizeOfUpdatedList - 1;

        if (sizeOfPreviousList < number) {
            throw new DukeInvalidCommandException("There are only " + number + " of previous commands." +
                    "Please enter a valid number less than or equal to " + number + " .");
        }

        if (isNumber) {
            int startIndex = sizeOfPreviousList - 1;
            for (int i = 0; i < number; i ++) {
                outputList.add(updatedUserInputList.get(startIndex) + " \n");
                startIndex -= 1;
            }
            result = outputList;

        } else if (fullCommand.equals("add/d")) {
            result = previousCommandsHandler(updatedUserInputList, outputList,"add/d");
        } else if (fullCommand.equals("add/e")) {
            result = previousCommandsHandler(updatedUserInputList, outputList,"add/e");
        } else if (fullCommand.equals("delete/d")) {
            result = previousCommandsHandler(updatedUserInputList, outputList,"delete/d");
        } else if (fullCommand.equals("delete/e")) {
            result = previousCommandsHandler(updatedUserInputList, outputList, "delete/e");
        } else if (fullCommand.equals("recur/e")) {
            result = previousCommandsHandler(updatedUserInputList, outputList, "recur/e");
        } else if (fullCommand.equals("remind/set")) {
            result = previousCommandsHandler(updatedUserInputList, outputList, "remind/set");
        } else if (fullCommand.equals("remind/rm")) {
            result = previousCommandsHandler(updatedUserInputList, outputList, "remind/rm");
        } else if (fullCommand.equals("/show")) {
            result = previousCommandsHandler(updatedUserInputList, outputList, "/show");
        } else if (fullCommand.equals("filter")) {
            result = previousCommandsHandler(updatedUserInputList, outputList, "filter");
        } else if (fullCommand.equals("help")) {
            result = previousCommandsHandler(updatedUserInputList, outputList, "help");
        } else if (fullCommand.equals("list")) {
            result = previousCommandsHandler(updatedUserInputList, outputList, "list");
        } else if (fullCommand.equals("done")) {
            result = previousCommandsHandler(updatedUserInputList, outputList, "done");
        } else if (fullCommand.equals("Available")) {
            result = previousCommandsHandler(updatedUserInputList, outputList, "Available");
        } else if (fullCommand.equals("show previous")) {
            result = previousCommandsHandler(updatedUserInputList, outputList, "show previous");
        } else if (fullCommand.equals("Week")) {
            result = previousCommandsHandler(updatedUserInputList, outputList, "Week");
        }
        return ui.showPrevious(result);
    }

    public static ArrayList<String> getOutputList() {
        return result;
    }
}
