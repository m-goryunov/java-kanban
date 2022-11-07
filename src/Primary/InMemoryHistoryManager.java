package Primary;

import Supplementary.Task;
import java.util.*;

// Избавиться от повторных просмотров в Истории просмотров и ограничения на размер истории.

public class InMemoryHistoryManager implements HistoryManager {

    private final Deque<Task> history = new LinkedList<>();

    Map <Integer, Object> NodeMap = new HashMap<>();
    private final CustomLinkedList<Object> linkedHistory = new CustomLinkedList<>();

    @Override
    public void add(Task task) {
        linkedHistory.linkLast(task);


       /* if(history.size() >= 10){
            history.removeFirst();
        }
        history.addLast(task);
        System.out.println(history);*/
    }
    @Override
    public void remove(Task task){ // при удалении задачи так же удалять её из истории просмотров

    }
    @Override
    public Deque<Task> getHistory(){
        return linkedHistory.getTasks();
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

    public void linkLast(T element) {
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

    public void getTasks(CustomLinkedList<T> tasks){
        List<Task> simpleList = new ArrayList<>();
        simpleList.addAll((Collection<? extends Task>) tasks);
        return simpleList;
    }


  /*  public T getFirst() {
        final Node<T> curHead = head;
        if (curHead == null)
            throw new NoSuchElementException();
        return head.data;
    }

    public T getLast() {
        final Node<T> curTail = tail;
        if (curTail == null)
            throw new NoSuchElementException();
        return tail.data;
    }

    public int size() {
        return this.size;
    }
    public void addFirst(T element) {
        final Node<T> oldHead = head;
        final Node<T> newNode = new Node<>(null, element, oldHead);
        head = newNode;
        if (oldHead == null)
            tail = newNode;
        else
            oldHead.prev = newNode;
        size++;
    }*/
}