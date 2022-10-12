package Primary;

import Supplementary.Task;

import java.util.Deque;

public interface HistoryManager {

    void add(Task task);

    Deque<Task> remove(Task task);

    Deque<Task> getHistory();

}
