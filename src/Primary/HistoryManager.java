package Primary;

import Supplementary.Task;
import java.util.Deque;

public interface HistoryManager {

    void add(Task task);

    void remove(Task task);

    Deque<Task> getHistory();

}
