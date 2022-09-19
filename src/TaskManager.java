import java.util.HashMap;
import java.util.Scanner;

public class TaskManager {
    Scanner scanner = new Scanner(System.in);
    HashMap<Integer, Object> tasks = new HashMap<>();
    HashMap<Integer, Object> subTasks = new HashMap<>();
    HashMap<Integer, Object> epics = new HashMap<>();

    Integer generateID(){
        Integer id = 0;
        id++;
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
    void updateTask(){

    }
}
