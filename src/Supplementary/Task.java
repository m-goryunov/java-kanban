package Supplementary;

import java.util.Objects;

public class Task {
    public String name;
    public String description;
    public TaskStatus status;
    public Integer ID;
    public TaskType type;

    public Task(String name, String description, TaskStatus status, Integer ID, TaskType type) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.ID = ID;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", ID=" + ID +
                ", type='" + type + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(name, task.name) && Objects.equals(description, task.description) && status == task.status && Objects.equals(ID, task.ID) && type == task.type;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}

