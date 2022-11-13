package Primary;
import Supplementary.Task;
import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private final List<Task> history = new ArrayList<>();
    private final Map <Integer, Object> nodeMap = new HashMap<>();

    @Override
    public void add(Task task) { // Избавиться от повторных просмотров в Истории просмотров и ограничения на размер истории.
        if(nodeMap.containsKey(task.ID)){ //если уже сожержит - удаляем старую, записываем новую
            nodeMap.remove(task.ID);
            linkLast(task);
        } else {
            linkLast(task); // если нет - просто записываем новую
        }
    }
    @Override
    public void remove(int id){ // при удалении задачи так же удалять её из истории просмотров

    }
    @Override
    public List<Task> getHistory(){
        return getTasks();
    }



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

    private Node<Task> head;
    private Node<Task> tail;
    private int size = 0;

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
        size++;
        nodeMap.put(task.ID, newNode);
    }

    public List<Task> getTasks(){
        history.
    }

    public void removeNode(Node node){

    }

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


       /* if(history.size() >= 10){
            history.removeFirst();
        }
        history.addLast(task);
        System.out.println(history);*/
