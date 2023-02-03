package Supplementary;

import java.util.Objects;

public class Epic extends Task {

    private final Integer epicID;
    private TaskStatus epicStatus;

    public Epic(String name, String description, TaskStatus status, Integer ID, TaskType type) {
        super(name, description, status, ID, type);
        this.epicID = ID;
        this.epicStatus = status;
        this.type = type;
    }


    public void setEpicStatus(TaskStatus epicStatus) {
        this.epicStatus = epicStatus;
    }

    @Override
    public String toString() {
        return "Epic{" +
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
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(epicID, epic.epicID) && epicStatus == epic.epicStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicID, epicStatus);
    }
}
