public class Epic extends Task {

    private Integer epicID;
    private TaskStatus epicStatus;

    public Epic(String name, String description, TaskStatus status, Integer ID) {
        super(name, description, status, ID);
        this.epicID = ID;
        this.epicStatus = status;
    }


    public void setEpicStatus(TaskStatus epicStatus) {
        this.epicStatus = epicStatus;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", ID=" + ID +
                '}';
    }
}
