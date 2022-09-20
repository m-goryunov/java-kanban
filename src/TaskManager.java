import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskManager {
    Scanner scanner = new Scanner(System.in);
    Map<Integer, Object> tasks = new HashMap<>();
    Map<Integer, Object> subTasks = new HashMap<>();
    Map<Integer, Object> epics = new HashMap<>();

    public Integer getID() {
        return new Random().nextInt(1000);
    }

    void createTask(String Name, String Description, String Status, Integer ID) {
        Task task = new Task(Name, Description, Status, ID);
        Integer ID = getID();
        tasks.put(ID, task);
    }

    void updateTask(int ID, Task task) {
        if (tasks.containsKey(ID)) {
            tasks.put(ID, task);
        } else {
            System.out.println("Такой ID не существует.");
        }
    }

    void printAllTasks() {
        for (Map.Entry<Integer, Object> entry : tasks.entrySet()) {
            Integer ID = entry.getKey();
            Object task = entry.getValue();
            System.out.println("ID: " + ID + "\n" + task);
        }
    }

    void deleteAllTasks() {
        tasks.clear();
    }

    void printTaskByID(int ID) {
        System.out.println(tasks.get(ID));
    }

    void deleteTaskByID(int ID) {
        tasks.remove(ID);
    }

    void createSubTask(String subTaskName1, String subTaskDescription1, String subTaskStatus1, Integer subTaskID1, Integer epicID) {
        Integer ID = getID();
        SubTask subTask = new SubTask(subTaskName1, subTaskDescription1, subTaskStatus1, subTaskID1, epicID);
        if (!epics.containsKey(epicID)) {
            System.out.println("Для сабтаски не создан Эпик!");
        } else {
            subTasks.put(ID, subTask);
        }
    }

    void updateSubTask(int ID, SubTask subTask){
        if (subTasks.containsKey(ID)){
            subTasks.put(ID, subTask);
        } else {
            System.out.println("Такой ID не существует");
        }
    }

    void printAllSubTasks() {
        for (Map.Entry<Integer, Object> entry : subTasks.entrySet()) {
            Integer ID = entry.getKey();
            Object task = entry.getValue();
            System.out.println("ID: " + ID + "\n" + task);
        }
    }

    void deleteAllSubTasks() {
        subTasks.clear();
        //Статус Эпика!!!
    }

    void printSubTaskByID(int ID) {
        System.out.println(subTasks.get(ID));
    }

    void deleteSubTaskByID(int ID) {
        subTasks.remove(ID);
    }

}
