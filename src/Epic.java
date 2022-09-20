public class Epic extends Task{

    protected Integer epicID;

    public Epic(String name, String description, String status, Integer ID, Integer epicID) {
        super(name, description, status, ID);
        this.epicID = epicID;
        this.ID = epicID;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "epicID=" + epicID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", ID=" + ID +
                '}';
    }
}
