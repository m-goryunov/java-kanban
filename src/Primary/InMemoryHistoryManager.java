package Primary;

import Supplementary.Task;

import java.util.Deque;
import java.util.LinkedList;

public class InMemoryHistoryManager implements HistoryManager {

    private Deque<Task> history = new LinkedList<>();


    // тут пустышка получается. Пока плохо понимаю, зачем этот класс...
    @Override
    public Deque<Task> add(Task task) {
        if(history.size() >= 10){
            history.removeFirst();
        }
        history.addLast(task);
        return history;
    }

    public Deque<Task> remove(Task task){ // при удалении задачи так же удалять её из истории просмотров
        return null;
    }


    @Override
    public Deque<Task> getHistory(){
        return history;
    }
}
