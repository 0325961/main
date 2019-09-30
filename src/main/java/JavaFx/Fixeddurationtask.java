package JavaFx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Pair;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Controller for fixed duration tasks. Provides the GUI for user to select best option.
 */
public class FixedDurationTask {
    private static final String NO_FIELD = "void";
    private static final String CROSS = "[\u2718]";
    @FXML
    private ChoiceBox<String> taskTypeChoiceBox;
    @FXML
    private TextField taskDescriptionTextField;
    @FXML
    private ListView<String> periodListView;
    @FXML
    private Button cancelButton;
    @FXML
    private Button addButton;
    @FXML
    private TextField periodTextField;

    private ArrayList< Pair<Date, Date>> freeTimes = new ArrayList<>();

    private String taskDescription;
    private Pair<String, String> commandAndTaskDetails = new Pair<>(NO_FIELD,NO_FIELD);

    private final ObservableList<String> taskTypeList = FXCollections.observableArrayList("[T] Todo", "[D] Deadline", "[E] Event");
    private final ObservableList<String> freeTimesList = FXCollections.observableArrayList();

    /**
     * This method initializes the display in the window of the GUI.
     */
    @FXML
    public void initialize() {
        taskTypeChoiceBox.setValue(taskTypeList.get(0));
        taskTypeChoiceBox.setItems(taskTypeList);
    }

    /**
     * This function gets data from previous window
     * @param availableTimeSlot All free times found in  start and end pairs
     * @param task The task input by user
     */
    public void getData(ArrayList<Pair<Date, Date>> availableTimeSlot, String task){
        freeTimes = availableTimeSlot;
        taskDescription = task;
        taskDescriptionTextField.setText(taskDescription);
        populateFreeTimesList();
    }


    /**
     * This function populates data into FreeTimeList ObservableList
     */
    private void populateFreeTimesList(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy hh:mm aa");
        for(Pair<Date, Date> date: freeTimes){
            String startEnd = "Start: " + dateFormat.format(date.getKey()) + "\nEnd: " + dateFormat.format(date.getValue());
            freeTimesList.add(startEnd);
        }
        periodListView.setItems(freeTimesList);
    }


    /**
     * This function returns data processed in current window to previous window
     * @return The command and message to display to the ChatBot
     */
    public Pair<String, String> returnData(){
        return commandAndTaskDetails;
    }

    /**
     * This function processes the data selected from GUI then generates command and task details.
     * @param taskType The task type
     * @param date The start and end dates
     * @return The processed data command and task details in a pair
     * @throws ParseException e
     */
    private Pair<String, String> sortByTaskType(String taskType, String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
        SimpleDateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy hh:mm aa");

        date = date.replace("Start: ", "");
        String[] spiltDate = date.split("End: ");
        String command = NO_FIELD;
        String details = NO_FIELD;
        String startDate = spiltDate[0];
        String endDate = spiltDate[1];

        String typeTypeCommand = taskType.trim().substring(4).toLowerCase();
        if (taskType.substring(0,3).trim().equals("[T]")){
            command = typeTypeCommand + " " + taskDescription + " "+ startDate + " until " + endDate;
            details = taskType.trim().substring(0,3) + CROSS + " " + taskDescription + " " + startDate + " until " + endDate;
        } else if (taskType.substring(0,3).trim().equals("[D]")){
            command = typeTypeCommand + " " + taskDescription + " /by " + formatter.format(dateFormat.parse(startDate));
            details = taskType.trim().substring(0,3) + CROSS + " " + taskDescription + " (by: " + startDate + " until " + endDate + ")";
        } else if (taskType.substring(0,3).trim().equals("[E]")){
            command = typeTypeCommand + " " + taskDescription + " /at " + formatter.format(dateFormat.parse(startDate));
            details = taskType.trim().substring(0,3) + CROSS + " " + taskDescription + " (at: " + startDate + " until " + endDate + ")";
        }
        return new Pair<>(command, details);
    }

    /**
     * This function checks if is selected parameter valid, then prompts the user to confirm
     */
    @FXML
    private void handleAdd() throws ParseException {
        if(!periodTextField.getText().isEmpty()){
            commandAndTaskDetails = sortByTaskType((taskTypeChoiceBox.getValue()), periodTextField.getText());
            boolean isOk = AlertBox.display("Confirmation Dialog", "Add Task", "Press OK to add task.\nPress Cancel to change your options.", Alert.AlertType.CONFIRMATION);
            Stage stage = (Stage) addButton.getScene().getWindow();
            if (isOk) {
                stage.close();
                AlertBox.display("Notification Dialog", "", "Your task has been added.", Alert.AlertType.INFORMATION);
            }
        } else {
            AlertBox.display("Warning Dialog","Period is empty","Please Select and click a Period from the List.", Alert.AlertType.WARNING);
        }
    }

    /**
     * This function prompts the user to confirm cancel operation
     */
    @FXML
    private void handleCancel() {
        boolean isCancel =AlertBox.display("Confirmation Dialog","Cancel Task","Press OK to return to ChatBot.\nPress Cancel to return to Hello Better options.", Alert.AlertType.CONFIRMATION);
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        if(isCancel) {
            stage.close();

        }
    }

    /**
     * This function updates periodTextField when mouse clicked on ListView
     */
    @FXML
    private void updatePeriod (){
        String temp = periodListView.getSelectionModel().getSelectedItem();
        int index = temp.indexOf("End:");
        String period = temp.substring(0,index) + " " + temp.substring(index);

        periodListView.refresh();
        periodListView.setItems(freeTimesList);
        periodTextField.setText(period);
    }
}
