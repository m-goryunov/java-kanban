package Primary;

import Supplementary.Task;
import java.util.Deque;
import java.util.List;

public interface HistoryManager {

    void add(Task task);

    void remove(int id);

    List<Task> getHistory(); //старая реализация

}
