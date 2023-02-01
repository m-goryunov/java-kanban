package Supplementary;

public class Epic extends Task {

    private final Integer epicID;
    private TaskStatus epicStatus;

    public Epic(String name, String description, TaskStatus status, Integer ID, String type) {
        super(name, description, status, ID, type);
        this.epicID = ID;
        this.epicStatus = status;
        this.type = type;
    }


    public void setEpicStatus(TaskStatus epicStatus) {
        this.epicStatus = epicStatus;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", ID=" + ID +
                ", type='" + type + '\'' +
                '}';
    }
}
