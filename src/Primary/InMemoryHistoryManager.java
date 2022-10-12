package Primary;

import Supplementary.Task;

import java.util.Deque;
import java.util.LinkedList;

public class InMemoryHistoryManager implements HistoryManager {

    public Deque<Task> history = new LinkedList<>();



    public void add(Task task) {
        if(history.size() >= 10){
            history.removeFirst();
        }
        history.addLast(task);
        System.out.println(history);
        //return history;
    }

    public Deque<Task> remove(Task task){ // при удалении задачи так же удалять её из истории просмотров
        return null;
    }

    public Deque<Task> getHistory(){
        return history;
    }
}
