public class SubTask extends Task {

    protected Integer epicID;

    public SubTask(String name, String description, String status, Integer ID, Integer epicID) {
        super(name, description, status, ID);
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
