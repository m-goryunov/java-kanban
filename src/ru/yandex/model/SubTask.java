package ru.yandex.model;

public class SubTask extends Task {

    public SubTask(String name, String description, TaskStatus status, Integer ID, Integer epicId) {
        super(name, description, status, ID, epicId);
    }

    @Override
    public Integer getEpicId() {return super.getEpicId();}

    @Override
    public void setEpicId(Integer epicId) {
        super.setEpicId(epicId);
    }

    @Override
    public TaskType getType() {
        return TaskType.SUBTASK;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "epicId=" + getEpicId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", ID=" + getId() +
                ", type='" + getType() + '\'' +
                ", epicId='" + getEpicId() + '\'' +
                '}';
    }

}
