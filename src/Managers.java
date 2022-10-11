import Primary.HistoryManager;
import Primary.InMemoryHistoryManager;
import Primary.InMemoryTaskManager;
import Primary.TaskManager;

public class Managers {

    public static TaskManager getDefault(){
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory(){
        return new InMemoryHistoryManager();
    }
}
