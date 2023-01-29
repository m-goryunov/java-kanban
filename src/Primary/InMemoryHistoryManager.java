package Primary;
import Supplementary.Node;
import Supplementary.Task;
import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private final Map <Integer, Node<Task>> nodeMap = new HashMap<>();
    private final CustomLinkedList<Task> customLinkedList = new CustomLinkedList<>();

    @Override
    public void add(Task task) {
        if(nodeMap.containsKey(task.ID)){
            remove(task.ID);
        }
        nodeMap.put(task.ID, customLinkedList.linkLast(task));// После добавления задачи не забудьте обновить значение узла в HashMap.
        customLinkedList.getTasks();
    }
    @Override
    public void remove(int id){
        customLinkedList.unlink(nodeMap.get(id));
        nodeMap.remove(id);
        customLinkedList.getTasks();
    }

    @Override
    public List<Task> getHistory() {
        return customLinkedList.getTasks();
    }


private class CustomLinkedList<E> {

    transient Node<E> first; //head
    transient Node<E> last; //tail

    Node<E> linkLast(E e) {
        final Node<E> l = last;
        final Node<E> newNode = new Node<>(l, e, null);
        last = newNode;
        if (l == null) {
            first = newNode;
        }
        else {
            l.next = newNode;
        }
        return newNode;
    }

        E unlink(Node<E> x) { // он же removeNode
            final E element = x.item;
            final Node<E> next = x.next;
            final Node<E> prev = x.prev;

            if (prev == null) {
                first = next;
            } else {
                prev.next = next;
                x.prev = null;
            }

            if (next == null) {
                last = prev;
            } else {
                next.prev = prev;
                x.next = null;
            }

            x.item = null;
            return element;
        }

    public List<Task> getTasks() { // Собирает все задачи из CustomLinkedList в обычный ArrayList
        List<Task> history = new ArrayList<>();
        for (Node<Task> node: nodeMap.values()) {
            history.add(node.item);
        }
        return history;
    }

}
}
