public class SubTask extends Task {
    Integer epicID;

    public SubTask(String name, String description, String status, Integer ID, Integer epicID) {
        super(name, description, status, ID);
        this.epicID = epicID;
    }
}
