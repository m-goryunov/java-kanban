public class Epic extends Task{

    private Integer epicID;
    private String epicStatus;

    public Epic(String name, String description, String status, Integer ID) {
        super(name, description, status, ID);
        this.epicID = ID;
        this.epicStatus = status;
    }

    public Integer getEpicID() {
        return epicID;
    }

    public String getEpicStatus() {
        return epicStatus;
    }

    public void setEpicStatus(String epicStatus) {
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
