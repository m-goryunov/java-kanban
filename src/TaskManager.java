import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskManager {
    Scanner scanner = new Scanner(System.in);
    Map<Integer, Object> tasks = new HashMap<>();
    Map<Integer, Object> subTasks = new HashMap<>();
    Map<Integer, Object> epics = new HashMap<>();

    public Integer getID(){
        AtomicInteger atomicInteger = new AtomicInteger();
        int id = atomicInteger.incrementAndGet();
        return id;
    }

    void createTask(String initialName, String initialDescription, String initialStatus, Integer initialID){
        Task task = new Task(initialName,initialDescription,initialStatus,initialID);
        Integer ID = getID();
        tasks.put(ID,task);
    }

    void updateTask(int ID, Task task) {
        if (tasks.containsKey(ID)) {
            tasks.put(ID, task);
        } else {
            System.out.println("Такой ID не существует.");
        }
    }

    void printAllTasks(){
        for (Map.Entry<Integer, Object> entry: tasks.entrySet()){
            Integer ID = entry.getKey();
            Object task = entry.getValue();
            System.out.println("ID: " + ID + "\n" + task);
        }
    }

    void deleteAllTasks(){
        tasks.clear();
    }

    void printTaskByID(int ID){
        System.out.println(tasks.get(ID));
    }

    void deleteTaskByID(int ID){
        tasks.remove(ID);
    }
}
