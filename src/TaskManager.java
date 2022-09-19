import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskManager {
    Scanner scanner = new Scanner(System.in);
    HashMap<Integer, Object> tasks = new HashMap<>();
    HashMap<Integer, Object> subTasks = new HashMap<>();
    HashMap<Integer, Object> epics = new HashMap<>();

    Integer generateID(){
        AtomicInteger atomicInteger = new AtomicInteger();
        int id = atomicInteger.incrementAndGet();
        return id;
    }

    void createTask(){
        System.out.println("Введите название:");
        String name = scanner.next();
        System.out.println("Введите описание:");
        String description = scanner.next();
        String status = "NEW";
        Integer ID = generateID();
        Task task = new Task(name,description,status,ID);
        tasks.put(ID,task);
    }
    void updateTask(Task task){
        System.out.println("Введите ID обновляемой задачи: ");
        int ID = scanner.nextInt();
        if (tasks.containsKey(ID)){tasks.put(ID,task);}

    }
}
