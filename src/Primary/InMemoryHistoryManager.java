package Primary;
import Supplementary.Node;
import Supplementary.Task;
import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private final Map <Integer, Node> nodeMap = new HashMap<>();

    @Override
    public void add(Task task) { // Избавиться от повторных просмотров в Истории просмотров и ограничения на размер истории.
        if(nodeMap.containsKey(task.ID)){ //если уже сожержит - удаляем старую, записываем новую
            removeNode(nodeMap.get(task.ID));
            nodeMap.remove(task.ID);
            linkLast(task);
        } else {
            linkLast(task); // если нет - просто записываем новую
        }
    }
    @Override
    public void remove(int id){ // при удалении задачи так же удалять её из истории просмотров
    nodeMap.remove(id);
    }
    @Override
    public List<Object> getHistory(){
        return getTasks();
    }



    private Node<Task> head;
    private Node<Task> tail;

    public void linkLast(Task task) {
        final Node<Task> oldTail = tail;
        final Node<Task> newNode = new Node<>(tail, task, null);
        tail = newNode;
        if (oldTail == null) {
            oldTail.next = newNode;
        } else {
            oldTail.next = newNode;
            head = newNode;
        }
        nodeMap.put(task.ID, newNode);
    }

    public List<Object> getTasks(){
        return new ArrayList<>(nodeMap.values());
    }

    public void removeNode(Node node){
        if (head == null || node == null) {
            return;
        }

        if (head == node) {
            head = node.next;
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        }

        if (node.prev != null) {
            node.prev.next = node.next;
        }
    }
}
