package ru.yandex.model;

import java.util.HashMap;

public class Epic extends Task {

    private HashMap<Integer, Integer> relatedSubtaskIds;


    public Epic(String name, String description, TaskStatus status, Integer ID, Integer epicId, HashMap<Integer, Integer> relatedSubtaskIds) {
        super(name, description, status, ID, epicId);
        this.relatedSubtaskIds = relatedSubtaskIds;

    }

    public HashMap<Integer, Integer> getRelatedSubtaskIds() {
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

    public void setRelatedSubtaskIds(HashMap<Integer, Integer> relatedSubtaskIds) {
        this.relatedSubtaskIds = relatedSubtaskIds;
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
                ", type='" + getType() + '\'' +
                ", relatedSubTaskIds'" + getRelatedSubtaskIds() + '\'' +
                '}';
    }
}
