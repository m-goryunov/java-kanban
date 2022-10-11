package Primary;

import Supplementary.Epic;
import Supplementary.SubTask;
import Supplementary.Task;
import Supplementary.TaskStatus;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, SubTask> subTasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    //Managers manager = new Managers();
    //Primary.HistoryManager historyManager = new Primary.InMemoryHistoryManager();
    private final Deque<Task> history = new LinkedList<>();
    private Integer getID() {
        return new Random().nextInt(1000);
    }

    private Deque<Task> add(Task task) {
        if(history.size() >= 10){
            history.removeFirst();
        }
        history.addLast(task);
        return history;
    }
    public Deque<Task> getHistory(){
        return history;
    }



    @Override
    public void createTask(Task task) {
        tasks.put(getID(), task);
    }
    @Override
    public void updateTask(int ID, Task task) {
        if (tasks.containsKey(ID)) {
            tasks.put(ID, task);
        } else {
            System.out.println("Такой ID не существует.");
        }
    }
    @Override
    public void printAllTasks() {
        for (Map.Entry<Integer, Task> entry : tasks.entrySet()) {
            Integer ID = entry.getKey();
            Object task = entry.getValue();
            System.out.println("ID: " + ID + "\n" + task);
        }
    }
    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }
    @Override
    public void printTaskByID(int ID) {
        if (tasks.containsKey(ID)) {
            System.out.println(tasks.get(ID));
            add(tasks.get(ID)); // делаю так, по наставлению наставника
            // а не так manager.getDefaultHistory().add(tasks.get(ID));
        } else {
            System.out.println("Такой ID не существует!");
        }
    }
    @Override
    public void deleteTaskByID(int ID) {
        if (tasks.containsKey(ID)) {
            tasks.remove(ID);
        } else {
            System.out.println("Такой ID не существует!");
        }
    }
    @Override
    public void createSubTask(SubTask subTask, Integer epicID) {
        if (epics.containsKey(epicID)) {
            subTask.setEpicID(epicID);
            subTasks.put(getID(), subTask);
        } else {
            System.out.println("Для сабтаски не создан Эпик!");

        }
    }
    @Override
    public void updateSubTask(int ID, SubTask subTask) {
        if (subTasks.containsKey(ID)) {
            subTasks.put(ID, subTask);
            updateEpicStatus(subTask.getEpicID());

        } else {
            System.out.println("Такой ID не существует");
        }
    }

    void updateEpicStatus(Integer epicID) {
        for (SubTask subTask : subTasks.values()) {
            for (Epic epic : epics.values()) {
                if (subTask.status == TaskStatus.NEW) {
                    epics.put(epicID, new Epic(epic.name, epic.description, TaskStatus.NEW, epicID));
                } else if (subTask.status == TaskStatus.NEW) {
                    epics.put(epicID, new Epic(epic.name, epic.description, TaskStatus.DONE, epicID));
                } else {
                    epics.put(epicID, new Epic(epic.name, epic.description, TaskStatus.IN_PROGRESS, epicID));
                }
            }
        }
    }
    @Override
    public void printAllSubTasks() {
        for (Map.Entry<Integer, SubTask> entry : subTasks.entrySet()) {
            Integer ID = entry.getKey();
            Object task = entry.getValue();
            System.out.println("ID: " + ID + "\n" + task);
        }
    }
    @Override
    public void deleteAllSubTasks() {
        subTasks.clear();
        for (Epic epic : epics.values()) {
            epic.setEpicStatus(TaskStatus.NEW);
        }
    }
    @Override
    public void printSubTaskByID(int ID) {
        if (subTasks.containsKey(ID)) {
            System.out.println(subTasks.get(ID));
            add(tasks.get(ID));
        } else {
            System.out.println("Такой ID не существует!");
        }
    }
    @Override
    public void deleteSubTaskByID(int ID) {
        if (subTasks.containsKey(ID)) {
            subTasks.remove(ID);
        } else {
            System.out.println("Такой ID не существует!");
        }
    }
    @Override
    public void createEpic(Epic epic) {
        epics.put(getID(), epic);
    }
    @Override
    public void updateEpic(int ID, Epic epic) {
        if (epics.containsKey(ID)) {
            epics.put(ID, epic);
        } else {
            System.out.println("Такой ID не существует.");
        }
    }
    @Override
    public void printAllEpics() {
        for (Map.Entry<Integer, Epic> entry : epics.entrySet()) {
            Integer ID = entry.getKey();
            Object task = entry.getValue();
            System.out.println("ID: " + ID + "\n" + task);
        }
    }
    @Override
    public void deleteAllEpics() {
        epics.clear();
        subTasks.clear(); //не может существовать без Supplementary.Epic
    }
    @Override
    public void printEpicByID(int ID) {
        if (epics.containsKey(ID)) {
            System.out.println(epics.get(ID));
            add(tasks.get(ID));
        } else {
            System.out.println("Такой ID не существует!");
        }
    }
    @Override
    public void deleteEpicByID(int ID) {
        if (epics.containsKey(ID)) {
            epics.remove(ID);
        } else {
            System.out.println("Такой ID не существует!");
        }
    }
    @Override
    public void getAllSubtasksByEpic(int ID) {
        List<SubTask> subTasksByEpic = new ArrayList<>();

        for (SubTask subTask : subTasks.values()) {
            if (subTask.getEpicID() == ID) {
                subTasksByEpic.add(subTask);
            }
            System.out.println(subTasksByEpic);
        }
    }
}
