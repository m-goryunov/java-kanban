package Supplementary;

import java.util.Objects;

public class SubTask extends Task {

    private Integer epicID;

    public SubTask(String name, String description, TaskStatus status, Integer ID, Integer epicID, TaskType type) {
        super(name, description, status, ID, type);
        this.epicID = epicID;
        this.type = type;
    }

    public Integer getEpicID() {
        return epicID;
    }

    public void setEpicID(Integer epicID) {
        this.epicID = epicID;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "epicID=" + epicID +
                ", name='" + name + '\'' +
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
        SubTask subTask = (SubTask) o;
        return Objects.equals(epicID, subTask.epicID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicID);
    }
}
