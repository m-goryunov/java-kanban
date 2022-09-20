public class SubTask extends Task {

    protected String name = "Сабтаска";
    protected String description = "Полить цветочек";
    protected String status = "NEW";
    protected Integer ID = null;
    protected Integer epicID = null;


    public SubTask(String name, String description, String status, Integer ID) {
        super(name, description, status, ID);
    }
}
