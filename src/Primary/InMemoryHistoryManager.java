package Primary;

import Supplementary.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private final Map<Integer, Node<Task>> nodeMap = new HashMap<>();
    private final CustomLinkedList<Task> customLinkedList = new CustomLinkedList<>();

    @Override
    public void add(Task task) {
        if (nodeMap.containsKey(task.getId())) {
            remove(task.getId());
        }
        nodeMap.put(task.getId(), customLinkedList.linkLast(task));
    }


    @Override
    public void remove(int id) {
        if (nodeMap.containsKey(id)) {
            customLinkedList.unlink(nodeMap.get(id));
            nodeMap.remove(id);
        }
    }

    @Override
    public void removeAll() {
        for (Integer id : nodeMap.keySet()) {
            customLinkedList.unlink(nodeMap.get(id));
            nodeMap.remove(id);
        }
    }


    @Override
    public List<Task> getHistory() {
        return customLinkedList.getTasks();
    }


    private class CustomLinkedList<E> {

        transient Node<E> first; //head
        transient Node<E> last; //tail
        int size = 0;

        List<E> getTasks() {
            List<E> history = new ArrayList<>();
            Node<E> current = first;
            while (current != null) {
                history.add(current.item);
                current = current.next;
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

        void unlink(Node<E> x) { // он же removeNode
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
        }

    }

    private class Node<E> { // перенес класс сюда, так понимаю гет-сет ерры не нужны тогда
        private E item;
        private Node<E> next;
        private Node<E> prev;

        public Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }

    }
}
