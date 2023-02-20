package ru.yandex.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Epic extends Task {

    private final Map<Integer, Integer> relatedSubtaskIds = new HashMap<>();
    private LocalDateTime endTime;


    public Epic(String name, String description, Integer id, long duration, LocalDateTime startTime, LocalDateTime endTime) {
        super(name, description, TaskStatus.NEW, id, duration, startTime);
        this.endTime = endTime;
    }

    public Map<Integer, Integer> getRelatedSubtaskIds() {
        return relatedSubtaskIds;
    }

    public void addRelatedSubtaskIds(int id) {
        relatedSubtaskIds.put(id, id);
    }

    public void removeRelatedSubtaskIds() {
        relatedSubtaskIds.clear();
    }

    public void removeRelatedSubtaskIds(int id) {
        relatedSubtaskIds.remove(id);
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public void setStatus(TaskStatus status) {
        super.setStatus(status);
    }

    @Override
    public TaskType getType() {
        return TaskType.EPIC;
    }

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", ID=" + getId() +
                ", relatedSubTaskIds'" + getRelatedSubtaskIds() + '\'' +
                ", duration=" + getDuration() + " min." +
                ", startTime=" + dateFormatter(getStartTime()) +
                ", endTime=" + dateFormatter(getEndTime()) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Epic epic = (Epic) o;
        return Objects.equals(relatedSubtaskIds, epic.relatedSubtaskIds) && Objects.equals(endTime, epic.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(relatedSubtaskIds, endTime);
    }
}
