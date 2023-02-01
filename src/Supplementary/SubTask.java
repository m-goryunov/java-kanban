package Supplementary;

public class SubTask extends Task {

    private Integer epicID;

    public SubTask(String name, String description, TaskStatus status, Integer ID, Integer epicID) {
        super(name, description, status, ID);
        this.epicID = epicID;
        this.type = "SubTask";
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
                ", status='" + status + '\'' +
                ", ID=" + ID +
                '}';
    }
}
