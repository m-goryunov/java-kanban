package Supplementary;

public class Task {
    public String name;
    public String description;
    public TaskStatus status;
    public Integer ID;
    public String type = "Task";

    public Task(String name, String description, TaskStatus status, Integer ID) {
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

