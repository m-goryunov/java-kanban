package Primary;

import Supplementary.Epic;
import Supplementary.SubTask;
import Supplementary.Task;
import Supplementary.TaskStatus;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private Integer id = 0;
    protected final Map<Integer, Task> tasks = new HashMap<>();
    protected final Map<Integer, SubTask> subTasks = new HashMap<>();
    protected final Map<Integer, Epic> epics = new HashMap<>();

    HistoryManager historyManager = Managers.getDefaultHistory();


    private Integer getId() {
        return ++id;
    }


    @Override
    public void createTask(Task task) {

        if (epics.containsKey(task.getEpicId())) {
            int setId = getId();
            task.setId(setId);
            epics.get(task.getEpicId()).addRelatedSubtaskIds(task.getId());
            tasks.put(setId, task);
        } else {
            System.out.println("Для сабтаски не создан Эпик!");

        }
    }

    @Override
    public void updateTask(Task task) {
        if (tasks.containsKey(id)) {
            tasks.put(id, task);
            updateEpicStatus(task.getEpicId());
        } else {
            System.out.println("Такой id не существует.");
        }
    }

    @Override
    public Map<Integer, Task> printAllTasks() {
        return tasks;
    }

    @Override
    public void deleteAllTasks() {
        for (int i = 0; i <= tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task != null) {
                historyManager.remove(task.getId());
                epics.get(task.getEpicId()).removeRelatedSubtaskIds();
                tasks.remove(task.getId());
            }
        }
    }

    @Override
    public Task printTaskById(int id) {
        Task task = tasks.get(id);
        if (task != null) {
            historyManager.add(task);
        }
        return task;
    }


    @Override
    public void deleteTaskById(int id) {
        if (tasks.containsKey(id)) {
            Task task = tasks.get(id);
            historyManager.remove(task.getId());
            epics.get(task.getEpicId()).removeRelatedSubtaskIds(task.getId());
            tasks.remove(task.getId());
        } else {
            System.out.println("Такой id не существует!");
        }
    }

    @Override
    public void createSubTask(SubTask subTask) {
        if (epics.containsKey(subTask.getEpicId())) {
            int setId = getId();
            subTask.setId(setId);
            epics.get(subTask.getEpicId()).addRelatedSubtaskIds(subTask.getId());
            subTasks.put(setId, subTask);
        } else {
            System.out.println("Для сабтаски не создан Эпик!");

        }
    }

    @Override
    public void updateSubTask(SubTask task) {
        if (subTasks.containsKey(id)) {
            subTasks.put(id, task);
            updateEpicStatus(task.getEpicId());
            historyManager.add(subTasks.get(id));
        } else {
            System.out.println("Такой id не существует");
        }
    }

    void updateEpicStatus(Integer epicId) {
        for (SubTask subTask : subTasks.values()) {
            for (Epic epic : epics.values()) {
                if (subTask.getStatus() == TaskStatus.NEW) {
                    epics.put(epicId, new Epic(epic.getName(), epic.getDescription(), TaskStatus.NEW, epicId,
                            epic.getEpicId(), epic.getRelatedSubtaskIds()));
                } else if (subTask.getStatus() == TaskStatus.DONE) {
                    epics.put(epicId, new Epic(epic.getName(), epic.getDescription(), TaskStatus.DONE, epicId,
                            epic.getEpicId(), epic.getRelatedSubtaskIds()));
                } else if (subTask.getStatus() == TaskStatus.IN_PROGRESS) {
                    epics.put(epicId, new Epic(epic.getName(), epic.getDescription(), TaskStatus.IN_PROGRESS, epicId,
                            epic.getEpicId(), epic.getRelatedSubtaskIds()));
                }
            }
        }
    }

    @Override
    public Map<Integer, SubTask> printAllSubTasks() {
        return subTasks;
    }

    @Override
    public void deleteAllSubTasks() {
        for (int i = 0; i <= subTasks.size(); i++) {
            SubTask subTask = subTasks.get(i);
            if (subTask != null) {
                historyManager.remove(subTask.getId());
                epics.get(subTask.getEpicId()).removeRelatedSubtaskIds();
                subTasks.remove(subTask.getId());
            }
        }
        for (Epic epic : epics.values()) {
            epic.setStatus(TaskStatus.NEW);
        }
    }

    @Override
    public SubTask printSubTaskById(int id) {
        SubTask task = subTasks.get(this.id);
        if (task != null) {
            historyManager.add(task);
        }
        return task;
    }

    @Override
    public void deleteSubTaskById(int id) {
        if (subTasks.containsKey(id)) {
            SubTask subTask = subTasks.get(id);
            subTasks.remove(subTask.getId());
            epics.get(subTask.getEpicId()).removeRelatedSubtaskIds(subTask.getId());
            updateEpicStatus(subTask.getEpicId());
            historyManager.remove(id);
        }
    }

    @Override
    public void createEpic(Epic epic) {
        int setId = getId();
        epic.setId(setId);
        epics.put(setId, epic);
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            epics.put(epic.getId(), epic);
        }
    }

    @Override
    public Map<Integer, Epic> printAllEpics() {return epics;}

    @Override
    public void deleteAllEpics() {
        for (int i = 0; i <= epics.size(); i++) {

            if (epics.get(i) != null) {
                Epic epic = epics.get(i);
                historyManager.remove(epic.getId());
                epics.remove(epic.getId());
                epic.removeRelatedSubtaskIds();
            }
        }
    }

    @Override
    public Epic printEpicById(int id) {
        Epic task = epics.get(this.id);
        if (task != null) {
            historyManager.add(task);
        }
        return task;
    }

    @Override
    public void deleteEpicById(int id) {
        if (epics.containsKey(id)) {
            Epic epic = epics.get(id);
            epic.removeRelatedSubtaskIds();
            epics.remove(epic.getId());
            historyManager.remove(epic.getId());
        } else {
            System.out.println("Такой id не существует!");
        }
    }

    @Override
    public List<Task> getAllSubtasksByEpic(int id) {
        List<Task> subTasksByEpic = new ArrayList<>();
        if (epics.get(id) != null) {
            Epic epic = epics.get(id);
            for (int getTaskById: epic.getRelatedSubtaskIds()) {
                subTasksByEpic.add(printSubTaskById(getTaskById));
            }
        }
        return subTasksByEpic;
    }
}
