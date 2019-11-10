package Tasks;

import Commons.DukeConstants;

/**
 * Represents a task called event.
 */
public class Event extends Assignment {

    private final String by;
    private final String start;
    private final String end;

    /**
     * Creates an Event object.
     * @param description Description of a task
     * @param by Date of when a task should be done
     * @param start Start time
     * @param end End time
     */
    public Event(String description, String by, String start, String end) {
        super(description);
        this.by = by;
        this.start = start;
        this.end = end;
    }

    @Override
    public String getType() {
        return DukeConstants.EVENT_INDICATOR;
    }

    @Override
    public String toString() {
        return super.getModCode() + " " + getType() + super.toString() + "(at: " + by + " time: " + start + " to: " + end + ")";
    }

    @Override
    public String getDateTime() {
        return by + " " + start + " to " + end;
    }

    @Override
    public String toShow() {
        return "Start: " + start + "\nEnd: " + end + "\n";// + super.toShow();
    }

    @Override
    public String getDate() {
        return by;
    }

    @Override
    public String getTime() {
        return start + " to " + end;
    }

    @Override
    public String getStartTime() {
        return start;
    }

    @Override
    public String getEndTime() {
        return end;
    }


}
