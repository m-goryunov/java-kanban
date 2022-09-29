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
    @Override
    public Deque<Task> getHistory(){
        return history;
    }
}
