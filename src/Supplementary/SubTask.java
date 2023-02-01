package Supplementary;

public class SubTask extends Task {

    private Integer epicID;

    public SubTask(String name, String description, TaskStatus status, Integer ID, Integer epicID, String type) {
        super(name, description, status, ID, type);
        this.epicID = epicID;
        this.type = type;
    }

    public Integer getEpicID() {
        return epicID;
    }

    public void setEpicID(Integer epicID) {
        this.epicID = epicID;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "epicID=" + epicID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", ID=" + ID +
                ", type='" + type + '\'' +
                '}';
    }
}
