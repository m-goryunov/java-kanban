import java.util.Deque;

public interface HistoryManager {

    Deque<Task> add(Task task);

    Deque<Task> getHistory();

}
