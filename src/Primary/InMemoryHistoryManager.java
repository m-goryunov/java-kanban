package Primary;

import Supplementary.Task;
import java.util.*;

// Избавиться от повторных просмотров в Истории просмотров и ограничения на размер истории.

public class InMemoryHistoryManager implements HistoryManager {

    private final Deque<Task> history = new LinkedList<>();

    Map <Integer, Object> NodeMap = new HashMap<>();


    @Override
    public void add(Task task) {
        if(history.size() >= 10){
            history.removeFirst();
        }
        history.addLast(task);
        System.out.println(history);
    }
    @Override
    public void remove(Task task){ // при удалении задачи так же удалять её из истории просмотров

    }
    @Override
    public Deque<Task> getHistory(){
        return history;
    }
}

class CustomLinkedList<T> {

    class Node<E> {
        public E data;
        public Node<E> next;
        public Node<E> prev;

        public Node(Node<E> prev, E data, Node<E> next) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    public void addFirst(T element) {
        final Node<T> oldHead = head;
        final Node<T> newNode = new Node<>(null, element, oldHead);
        head = newNode;
        if (oldHead == null)
            tail = newNode;
        else
            oldHead.prev = newNode;
        size++;
    }

    public void addLast(T element) {
        final Node<T> oldTail = tail;
        final Node<T> newNode = new Node<>(tail, element, null);
        tail = newNode;
        if (oldTail == null) {
            oldTail.next = newNode;
        } else {
            oldTail.next = newNode;
            head = newNode;
        }
        size++;
    }

    public T getFirst() {
        final Node<T> curHead = head;
        if (curHead == null)
            throw new NoSuchElementException();
        return head.data;
    }

    public T getLast() {
        // Реализуйте метод
        final Node<T> curTail = tail;
        if (curTail == null)
            throw new NoSuchElementException();
        return tail.data;
    }

    public int size() {
        return this.size;
    }
}