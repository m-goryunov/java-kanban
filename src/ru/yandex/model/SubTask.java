package ru.yandex.model;

public class SubTask extends Task {

    private Integer epicId;

    public SubTask(String name, String description, TaskStatus status, Integer id, Integer epicId) {
        super(name, description, status, id);
        this.epicId = epicId;
    }


    public Integer getEpicId() {return epicId;}

    public void setEpicId(Integer epicId) {this.epicId = epicId;}

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
                '}';
    }

}
