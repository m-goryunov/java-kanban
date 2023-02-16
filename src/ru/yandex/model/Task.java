package ru.yandex.model;

import java.time.LocalDateTime;

public class Task {
    private String name;
    private String description;
    private TaskStatus status;
    private Integer id;
    private long duration;
    private LocalDateTime startTime;


    public Task(String name, String description, TaskStatus status, Integer id, long duration, LocalDateTime startTime) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.id = id;
        this.duration = duration;
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", id=" + id +
                ", duration=" + duration +
                ", startTime=" + startTime +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TaskType getType() {
        return TaskType.TASK;
    }

    public long getDuration() {return duration;}

    public void setDuration(long duration) {this.duration = duration;}

    public LocalDateTime getStartTime() {return startTime;}

    public void setStartTime(LocalDateTime startTime) {this.startTime = startTime;}

    public LocalDateTime getEndTime(){
        return getStartTime().plusMinutes(getDuration());
    }
}


