package ru.yandex.taskmanager;

import ru.yandex.model.*;

import java.util.List;

public interface TaskManager {
    void createTask(Task task);

    void updateTask(Task task);

    List<Task> getAllTasks();

    void deleteAllTasks();

    Task getTaskById(int id);

    void deleteTaskById(int id);

    void createSubTask(SubTask subTask);

    void updateSubTask(SubTask subTask);

    List<Task> getAllSubTasks();

    void deleteAllSubTasks();

    SubTask getSubTaskById(int id);

    void deleteSubTaskById(int id);

    void createEpic(Epic epic);

    void updateEpic(Epic epic);

    List<Epic> getAllEpics();

    void deleteAllEpics();

    Epic getEpicById(int id);

    void deleteEpicById(int id);

    List<SubTask> getAllSubtasksByEpic(int id);

    List<Task> getHistory();

    List<Task> getPrioritizedTasks();


}
