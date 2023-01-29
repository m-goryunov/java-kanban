package Primary;

import Supplementary.Epic;
import Supplementary.SubTask;
import Supplementary.Task;

public interface TaskManager {
    void createTask(Task task);

    void updateTask(int ID, Task task);

    void printAllTasks();

    void deleteAllTasks();

    void printTaskByID(int ID);

    void deleteTaskByID(int ID);

    void createSubTask(SubTask subTask, Integer epicID);

    void updateSubTask(int ID, SubTask subTask);

    void printAllSubTasks();

    void deleteAllSubTasks();

    void printSubTaskByID(int ID);

    void deleteSubTaskByID(int ID);

    void createEpic(Epic epic);

    void updateEpic(int ID, Epic epic);

    void printAllEpics();

    void deleteAllEpics();

    void printEpicByID(int ID);

    void deleteEpicByID(int ID);

    void getAllSubtasksByEpic(int ID);
}
