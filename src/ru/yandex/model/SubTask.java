package ru.yandex.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class SubTask extends Task {

    private Integer epicId;

    public SubTask(String name, String description, TaskStatus status, Integer id, long duration, LocalDateTime startTime, Integer epicId) {
        super(name, description, status, id, duration, startTime);
        this.epicId = epicId;
    }

    public Integer getEpicId() {
        return epicId;
    }

    public void setEpicId(Integer epicId) {
        this.epicId = epicId;
    }

    @Override
    public TaskType getType() {
        return TaskType.SUBTASK;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", ID=" + getId() +
                ", epicId='" + getEpicId() + '\'' +
                ", duration=" + getDuration() + " min." +
                ", startTime=" + dateFormatter(getStartTime()) +
                ", endTime=" + dateFormatter(getEndTime()) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SubTask subTask = (SubTask) o;
        return Objects.equals(epicId, subTask.epicId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicId);
    }
}
