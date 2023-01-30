package Primary;

import Supplementary.Node;
import Supplementary.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private static final Map<Integer, Node<Task>> nodeMap = new HashMap<>();
    private static final CustomLinkedList<Task> customLinkedList = new CustomLinkedList<>();

    @Override
    public void add(Task task) {
        if (nodeMap.containsKey(task.ID)) {
            remove(task.ID);
        }
        nodeMap.put(task.ID, customLinkedList.linkLast(task));
    }


    @Override
    public void remove(int id) {
        customLinkedList.unlink(nodeMap.get(id));
        nodeMap.remove(id);
    }


    @Override
    public List<Task> getHistory() {
        return customLinkedList.getTasks();
    }


    private static class CustomLinkedList<E> {

        transient Node<E> first; //head
        transient Node<E> last; //tail
        int size = 0;

        private List<E> getTasks() {
            List<E> history = new ArrayList<>();
            for (int i = 0; i < customLinkedList.size; i++) {
                history.add(get(i));
            }

            return history;
        }

        Node<E> linkLast(E e) {
            final Node<E> l = last;
            final Node<E> newNode = new Node<>(l, e, null);
            last = newNode;
            if (l == null) {
                first = newNode;
            } else {
                l.next = newNode;
            }
            size++;
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
            size--;
            return element;
        }

        public E get(int index) {
            checkElementIndex(index);
            return node(index).item;
        }

        Node<E> node(int index) {

            if (index < (size >> 1)) {
                Node<E> x = first;
                for (int i = 0; i < index; i++)
                    x = x.next;
                return x;
            } else {
                Node<E> x = last;
                for (int i = size - 1; i > index; i--)
                    x = x.prev;
                return x;
            }
        }

        private void checkElementIndex(int index) {
            if (!isElementIndex(index)) System.out.println("Такого индекса нет / нет истории просмотров");
        }

        private boolean isElementIndex(int index) {
            return index >= 0 && index < size;
        }

    }
}
