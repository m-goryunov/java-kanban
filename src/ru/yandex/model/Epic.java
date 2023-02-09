package ru.yandex.model;

import java.util.HashMap;
import java.util.Map;

public class Epic extends Task {

    private final Map<Integer, Integer> relatedSubtaskIds = new HashMap<>();


    public Epic(String name, String description, Integer id) {
        super(name, description, TaskStatus.NEW, id);
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


    @Override
    public void setStatus(TaskStatus status) {
        super.setStatus(status);
    }

    @Override
    public TaskType getType() {
        return TaskType.EPIC;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", ID=" + getId() +
                ", relatedSubTaskIds'" + getRelatedSubtaskIds() + '\'' +
                '}';
    }
}
