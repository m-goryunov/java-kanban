
public class Task {
    protected String name;
    protected String description;
    protected String status;
    protected Integer ID;

    public Task(String name, String description, String status, Integer ID) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.ID = ID;
    }


    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", ID=" + ID +
                '}';
    }
}

