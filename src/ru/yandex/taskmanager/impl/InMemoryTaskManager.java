package ru.yandex.taskmanager.impl;

import ru.yandex.taskmanager.HistoryManager;
import ru.yandex.taskmanager.TaskManager;
import ru.yandex.util.*;
import ru.yandex.model.*;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    protected Integer id = 0;
    protected final Map<Integer, Task> tasks = new HashMap<>();
    protected final Map<Integer, SubTask> subTasks = new HashMap<>();
    protected final Map<Integer, Epic> epics = new HashMap<>();

    protected HistoryManager historyManager = Managers.getDefaultHistory();


    private Integer getId() {
        return ++id;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public void createTask(Task task) {
        int setId = getId();
        task.setId(setId);
        tasks.put(setId, task);
    }


    @Override
    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        } else {
            System.out.println("Такой id не существует.");
        }
    }


    @Override
    public List<Task> getAllTasks() {
        return List.copyOf(tasks.values());
    }


    @Override
    public void deleteAllTasks() {
        for (Integer id : tasks.keySet()) {
            historyManager.remove(id);
        }
        tasks.clear();
    }


    @Override
    public Task getTaskById(int id) {
        Task task = tasks.get(id);
        if (task != null) {
            historyManager.add(task);
        }
        return task;
    }


    @Override
    public void deleteTaskById(int id) {
        Task task = tasks.remove(id);
        if (task != null) {
            historyManager.remove(id);
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
            updateEpicStatus(subTask.getEpicId());
        } else {
            System.out.println("Для сабтаски не создан Эпик!");

        }
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        if (subTasks.containsKey(subTask.getId())) {
            subTasks.put(subTask.getId(), subTask);
            updateEpicStatus(subTask.getEpicId());
        } else {
            System.out.println("Такой id не существует");
        }
    }

    void updateEpicStatus(Integer id) {
        Epic epic = epics.get(id);
        int countNew = 0;
        int countDone = 0;
        Set<Integer> subtaskIds = epic.getRelatedSubtaskIds().keySet();
        for (int subTaskId : subtaskIds) {
            SubTask subTask = subTasks.get(subTaskId);
            if (subTask.getStatus() == TaskStatus.NEW) {
                countNew++;
            } else if (subTask.getStatus() == TaskStatus.DONE) {
                countDone++;
            } else {
                epic.setStatus(TaskStatus.IN_PROGRESS);
                return;
            }
        }
        if (countNew == subtaskIds.size()) {
            epic.setStatus(TaskStatus.NEW);
        } else if (countDone == subtaskIds.size()) {
            epic.setStatus(TaskStatus.DONE);
        } else {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }
    }


    @Override
    public List<Task> getAllSubTasks() {
        return List.copyOf(tasks.values());
    }


    @Override
    public void deleteAllSubTasks() {
        for (Integer subtaskId : subTasks.keySet()) {
            historyManager.remove(subtaskId);
        }
        subTasks.clear();
        for (Epic epic : epics.values()) {
            epic.removeRelatedSubtaskIds();
            epic.setStatus(TaskStatus.NEW);
        }
    }


    @Override
    public SubTask getSubTaskById(int id) {
        SubTask subTask = subTasks.get(id);
        if (subTask != null) {
            historyManager.add(subTask);
        }
        return subTask;
    }

    @Override
    public void deleteSubTaskById(int id) {
        if (subTasks.containsKey(id)) {
            SubTask subTask = subTasks.remove(id);
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
    public List<Epic> getAllEpics() {
        return List.copyOf(epics.values());
    }


    @Override
    public void deleteAllEpics() {
        for (Integer subtaskId : subTasks.keySet()) {
            historyManager.remove(subtaskId);
        }
        for (Integer epicId : epics.keySet()) {
            historyManager.remove(epicId);
        }
        epics.clear();
        subTasks.clear();
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            historyManager.add(epic);
        }
        return epic;
    }

    @Override
    public void deleteEpicById(int id) {
        if (epics.containsKey(id)) {
            Epic epic = epics.get(id);
            Map<Integer, Integer> ids = epic.getRelatedSubtaskIds();

            for (Map.Entry<Integer, Integer> entry : ids.entrySet()) {
                subTasks.remove(ids.get(entry.getValue()));
                historyManager.remove(entry.getValue());
            }

            epics.remove(epic.getId());
            historyManager.remove(epic.getId());
        } else {
            System.out.println("Такой id не существует!");
        }
    }


    @Override
    public List<SubTask> getAllSubtasksByEpic(int id) {
        List<SubTask> subTasksByEpic = new ArrayList<>();
        Epic epic = epics.get(id);
        if (epic != null) {
            Collection<Integer> ids = epic.getRelatedSubtaskIds().values();
            for (Integer subtaskId : ids) {
                subTasksByEpic.add(subTasks.get(subtaskId));
            }
        }
        return subTasksByEpic;
    }
}
