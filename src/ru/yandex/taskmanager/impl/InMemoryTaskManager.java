package ru.yandex.taskmanager.impl;

import ru.yandex.taskmanager.HistoryManager;
import ru.yandex.taskmanager.TaskManager;
import ru.yandex.util.*;
import ru.yandex.model.*;

import java.time.LocalDateTime;
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

        if (getPrioritizedTasks().contains(task) && !getPrioritizedTasks().isEmpty()) { //надо все искать по датам!!
            throw new IllegalStateException("Можно выполнять не более 1 задачи в один день!");
        } else {
            int setId = getId();
            task.setId(setId);
            tasks.put(setId, task);
        }
    }


    @Override
    public void updateTask(Task task) {
        for (Task t : tasks.values()) {
            if (tasks.containsKey(task.getId()) && !task.getStartTime().equals(t.getStartTime())) {
                tasks.put(task.getId(), task);
            } else {
                throw new IllegalStateException("Можно выполнять не более 1 задачи в один день!");
            }
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

        if (!subTasks.isEmpty()) {
            for (SubTask s : subTasks.values()) {
                if (!subTask.getStartTime().equals(s.getStartTime()) && !subTask.getId().equals(s.getId())) {
                    if (epics.containsKey(subTask.getEpicId())) {
                        int setId = getId();
                        subTask.setId(setId);
                        epics.get(subTask.getEpicId()).addRelatedSubtaskIds(subTask.getId());
                        subTasks.put(setId, subTask);
                        updateEpicStatus(subTask.getEpicId());
                        setEpicCalendarization(subTask.getEpicId());
                    } else {
                        System.out.println("Для сабтаски не создан Эпик!");

                    }
                } else {
                    throw new IllegalStateException("Можно выполнять не более 1 задачи в один день!");
                }
            }
        }

    }

    @Override
    public void updateSubTask(SubTask subTask) {
        for (SubTask s : subTasks.values()) {
            if (subTasks.containsKey(subTask.getId()) && !subTask.getStartTime().equals(s.getStartTime())) {
                subTasks.put(subTask.getId(), subTask);
                updateEpicStatus(subTask.getEpicId());
                setEpicCalendarization(subTask.getEpicId());
            } else {
                throw new IllegalStateException("Можно выполнять не более 1 задачи в один день!");
            }
        }
    }

    public void updateEpicStatus(Integer id) {
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


    /*Продолжительность эпика — сумма продолжительности всех его подзадач.
      Время начала — дата старта самой ранней подзадачи, а время завершения — время окончания самой поздней из задач*/

    public void setEpicCalendarization(Integer id) {
        Epic epic = epics.get(id);
        long durationCount = 0;
        LocalDateTime tempDate;
        Map<Integer, Integer> ids = epic.getRelatedSubtaskIds();
        for (Integer subTaskId : ids.keySet()) {
            durationCount += subTasks.get(subTaskId).getDuration();
            tempDate = subTasks.get(subTaskId).getStartTime();
            if (tempDate.isAfter(epic.getStartTime())) {
                epic.setStartTime(tempDate);
            }
        }
        epic.setDuration(durationCount);
        epic.setEndTime(epic.getEndTime());
    }

    @Override
    public List<Task> getAllSubTasks() {
        return List.copyOf(subTasks.values());
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
            epic.setStartTime(null);
            epic.setDuration(0);
            epic.setEndTime(null);
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
            setEpicCalendarization(subTask.getEpicId());
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

    @Override
    public TreeSet<Task> getPrioritizedTasks() {

        Comparator<Task> comparator = (o1, o2) -> {

            if (o1.getStartTime() == null) {
                return 1;
            } else if (o2.getStartTime() == null) {
                return -1;
            } else if (o1.getStartTime().isBefore(o2.getStartTime())) {
                return 1;
            } else if (o2.getStartTime().isBefore(o1.getStartTime())) {
                return -1;
            } else {
                return 0;
            }
        };
        TreeSet<Task> prioritized = new TreeSet<>(comparator);
        prioritized.addAll(tasks.values());
        prioritized.addAll(subTasks.values());
        return prioritized;
    }
}
