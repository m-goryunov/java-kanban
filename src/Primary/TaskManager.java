package Primary;

import Supplementary.Epic;
import Supplementary.SubTask;
import Supplementary.Task;

import java.util.List;
import java.util.Map;

public interface TaskManager {
    void createTask(Task task);

    void updateTask(Task task);

    Map<Integer,Task> printAllTasks();

    void deleteAllTasks();

    Task printTaskById(int id);

    void deleteTaskById(int id);

    void createSubTask(SubTask subTask);

    void updateSubTask(SubTask subTask);

    Map<Integer, SubTask> printAllSubTasks();

    void deleteAllSubTasks();

    SubTask printSubTaskById(int id);

    void deleteSubTaskById(int id);

    void createEpic(Epic epic);

    void updateEpic(Epic epic);

    Map<Integer, Epic> printAllEpics();

    void deleteAllEpics();

    Epic printEpicById(int id);

    void deleteEpicById(int id);

    List<Task> getAllSubtasksByEpic(int id);
}
