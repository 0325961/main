package Tasks;
import java.util.*;

/**
 * To keep track of the list of task input by user.
 */
public class TaskList {
    private static final String NO_FIELD = "void";

    private ArrayList<Task> list;
    private HashMap<String, HashMap<String, ArrayList<Task>>> map;
    private HashMap<String, HashMap<String, ArrayList<String>>> deadlineList = new HashMap<>();
    private HashMap<String, HashMap<String, ArrayList<String>>> eventList = new HashMap<>();
    private ArrayList<String> deadlineArrList = new ArrayList<>();
    private ArrayList<String> eventArrList = new ArrayList<>();

    /**
     * Creates a TaskList object.
     */
    public TaskList(){
        this.list = new ArrayList<>();
        this.map = new HashMap<>();
    }

    public ArrayList<Task> getList() {
        return list;
    }

    public HashMap<String, HashMap<String, ArrayList<Task>>> getMap(){
        return this.map;
    }

    public ArrayList<Task> getListFromDate(Task task) {
        return this.map.get(task.getModCode()).get(task.getDate());
    }

    public HashMap<String, ArrayList<Task>> getMapFromModCode(Task task) {
        return this.map.get(task.getModCode());
    }

    public void addTask(Task task){
        this.list.add(task);
        if (this.map.containsKey(task.getModCode())) {
            if (!this.map.get(task.getModCode()).containsKey(task.getDate())) {
                map.get(task.getModCode()).put(task.getDate(), new ArrayList<>());
            }
        } else {
            this.map.put(task.getModCode(), new HashMap<>());
            this.map.get(task.getModCode()).put(task.getDate(), new ArrayList<>());
        }
        this.map.get(task.getModCode()).get(task.getDate()).add(task);
    }

    public void removeTask(Task task) {
        for(Task taskInList : this.map.get(task.getModCode()).get(task.getDate())) {
            if(taskInList.getDescription().equals(task.getDescription())) {
                this.map.get(task.getModCode()).get(task.getDate()).remove(taskInList);
                break;
            }
        }
    }

    public void updateTask(Task task) {
        for(Task taskInList : this.map.get(task.getModCode()).get(task.getDate())) {
            if(taskInList.getDateTime().equals(task.getDateTime())) {
                Integer index = this.map.get(task.getModCode()).get(task.getDate()).indexOf(taskInList);
                Task temp = this.map.get(task.getModCode()).get(task.getDate()).get(index);
                temp.setDone(true);
                this.map.get(task.getModCode()).get(task.getDate()).remove(taskInList);
                this.map.get(task.getModCode()).get(task.getDate()).add(temp);
                break;
            }
        }
    }

    //Do not use this: User will input the task in the CLI
    public Task getTask(int index){
        return this.list.get(index);
    }

    //Do not use this: Use toString method in Task
    public String taskToString(int index){
        return list.get(index).toString();
    }

    public int taskListSize() {

        int size = 0;
        for (String modCode : map.keySet()) {
            for (String date : map.get(modCode).keySet()) {
                for (Task task : map.get(modCode).get(date))
                    size++;
            }
        }
        return size;
    }

    public void setReminder(Task task, String time, boolean reminder){
        for (Task taskInList : this.map.get(task.getModCode()).get(task.getDate())) {
            if (taskInList.getDescription().equals(task.getDescription())) {
                if (reminder) {
                    taskInList.setRemindTime(time);
                    taskInList.setReminder(true);
                    break;
                } else {
                    taskInList.setRemindTime("");
                    taskInList.setReminder(false);
                    break;
                }
            }
        }
    }

    /**
     * This method sort the tasks according to their categories.
     */
    private void sortList() {
        for (int i = 0; i < list.size(); i++) {
            String description = list.get(i).toString();
            if (list.get(i).getType().equals("[D]")) {
                this.deadlineArrList.add(description);
            } else if (list.get(i).getType().equals("[E]")){
                this.eventArrList.add(description);
            }
        }
    }

    /**
     * This method gets the schedule requested by user.
     * @return This returns the String containing the schedule requested by user
     */
    public String schedule() {
        sortList();
        int sizeOfDeadlineArr = getDeadlineArrList().size();
        int sizeOfEventArr = getEventArrList().size();
        String finalSchedule = "Here is your schedule!\n";
        if (sizeOfDeadlineArr != 0) {
            finalSchedule += "DEADLINE Task\n";
            int num = 1;

            for (int i = 0; i < sizeOfDeadlineArr; i++) {
                finalSchedule = finalSchedule + num + "." + getDeadlineArrList().get(i) + "\n";
                num++;
            }
        }
        if (sizeOfEventArr != 0) {
            finalSchedule += "EVENT Task\n";
            int num = 1;

            for (int i = 0; i < sizeOfEventArr; i++) {
                finalSchedule = finalSchedule + num + "." + getEventArrList().get(i) + "\n";
                num++;
            }
        }
        deadlineArrList.clear();
        eventArrList.clear();
        return finalSchedule;
    }

    private ArrayList<String> getDeadlineArrList() {
        return this.deadlineArrList;
    }

    private ArrayList<String> getEventArrList() {
        return this.eventArrList;
    }


}