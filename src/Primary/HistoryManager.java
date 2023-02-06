package Primary;

import Supplementary.Task;

import java.util.List;

public interface HistoryManager {

    void add(Task task);

    void remove(int id);

    void removeAll();

    List<Task> getHistory();

}
