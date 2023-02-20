package ru.yandex.taskmanager.impl;

import ru.yandex.exception.ManagerSaveException;
import ru.yandex.model.*;
import ru.yandex.taskmanager.HistoryManager;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileBackedTaskManager extends InMemoryTaskManager {

    private final File file;
    private static final String title = "NAME, DESCRIPTION, STATUS, ID, TYPE, DURATION, START_TIME, END_TIME, EPIC_ID";


    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    private void save() {
        try (Writer fw = new FileWriter(file)) {
            fw.write(title + '\n');

            for (Task task : tasks.values()) {
                fw.write(toString(tasks.get(task.getId())) + '\n');
            }
            for (Epic epic : epics.values()) {
                fw.write(toString(epics.get(epic.getId())) + '\n');
            }
            for (SubTask subTask : subTasks.values()) {
                fw.write(toString(subTasks.get(subTask.getId())) + ", " + subTask.getEpicId().toString() + '\n');
            }

            if (!historyManager.getHistory().isEmpty()) {
                fw.write('\n');
                fw.write(historyToString(historyManager));
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new ManagerSaveException("Запись не произведена!");
        }

    }


    private String toString(Task task) {
        return task.getName() + ", " + task.getDescription() + ", " + task.getStatus()
                + ", " + task.getId() + ", " + task.getType() + ", " + task.getDuration() + ", " + task.getStartTime()
                + ", " + task.getEndTime();
    }


    private static Task fromString(String value) {
        String[] lineContent = value.split(", ");
        if (lineContent[4].equals(TaskType.TASK.toString())) {
            return new Task(
                    lineContent[0],
                    lineContent[1],
                    Enum.valueOf(TaskStatus.class, lineContent[2]),
                    Integer.parseInt(lineContent[3]),
                    Long.parseLong(lineContent[5]),
                    parseDate(lineContent[6])
            );
        } else if (lineContent[4].equals(TaskType.SUBTASK.toString())) {
            return new SubTask(
                    lineContent[0],
                    lineContent[1],
                    Enum.valueOf(TaskStatus.class, lineContent[2]),
                    Integer.parseInt(lineContent[3]),
                    Long.parseLong(lineContent[5]),
                    parseDate(lineContent[6]),
                    Integer.parseInt(lineContent[8])
            );
        } else {
            Epic epic = new Epic(
                    lineContent[0],
                    lineContent[1],
                    Integer.parseInt(lineContent[3]),
                    Integer.parseInt(lineContent[5]),
                    parseDate(lineContent[6]),
                    parseDate(lineContent[7])
            );
            epic.setStatus(Enum.valueOf(TaskStatus.class, lineContent[2]));
            return epic;
        }
    }

    private static LocalDateTime parseDate(String date) {
        if (Objects.equals(date, "null")) {
            return null;
        }
        return LocalDateTime.parse(date);
    }


    private static String historyToString(HistoryManager manager) {
        List<Task> getIDs = List.copyOf(manager.getHistory());
        List<String> ids = new ArrayList<>();
        for (int i = 0; i < manager.getHistory().size(); i++) {
            Task task = getIDs.get(i);
            ids.add(task.getId().toString());
        }
        return String.join(",", ids);
    }


    private static List<Integer> historyFromString(String value) {
        List<Integer> history = new ArrayList<>();
        String[] values = value.split(",");

        for (String s : values) {
            history.add(Integer.parseInt(s));
        }
        return history;
    }

    public static FileBackedTaskManager loadFromFile(File file) {

        FileBackedTaskManager manager = new FileBackedTaskManager(file);

        try (BufferedReader buffer = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            buffer.readLine();
            while (buffer.ready()) {
                String line = buffer.readLine();
                if (line.equals("")) {

                } else if (line.matches("[a-zA-Z ]*\\d+.*")) {
                    List<Integer> IDs = List.copyOf(historyFromString(line));

                    for (Integer id : IDs) {
                        if (manager.tasks.containsKey(id)) {
                            manager.historyManager.add(manager.tasks.get(id));
                        } else if (manager.subTasks.containsKey(id)) {
                            manager.historyManager.add(manager.subTasks.get(id));
                        } else if (manager.epics.containsKey(id)) {
                            manager.historyManager.add(manager.epics.get(id));
                        }
                    }
                } else {
                    Task task = fromString(line);
                    if (manager.id < task.getId()) {
                        manager.id = task.getId();
                    }


                    if (task.getType().equals(TaskType.TASK)) {
                        manager.tasks.put(task.getId(), task);
                        manager.prioritized.add(task);
                    } else if (task.getType() == TaskType.SUBTASK) {
                        SubTask subTask = (SubTask) task;
                        manager.subTasks.put(task.getId(), subTask);
                        manager.epics.get(subTask.getEpicId()).addRelatedSubtaskIds(subTask.getId());
                        manager.prioritized.add(subTask);
                    } else if (task.getType() == TaskType.EPIC) {
                        Epic epic = new Epic(task.getName(), task.getDescription(), task.getId(), task.getDuration(), task.getStartTime(), task.getEndTime());
                        manager.epics.put(task.getId(), epic);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return manager;
    }

    @Override
    public void createTask(Task task) {
        super.createTask(task);
        save();
    }


    @Override
    public void createSubTask(SubTask subTask) {
        super.createSubTask(subTask);
        save();
    }


    @Override
    public void createEpic(Epic epic) {
        super.createEpic(epic);
        save();
    }


    @Override
    public Task getTaskById(int id) {
        Task task = super.getTaskById(id);
        save();
        return task;
    }

    @Override
    public SubTask getSubTaskById(int id) {
        SubTask subTask = super.getSubTaskById(id);
        save();
        return subTask;
    }


    @Override
    public Epic getEpicById(int id) {
        Epic epic = super.getEpicById(id);
        save();
        return epic;
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void deleteTaskById(int id) {
        super.deleteTaskById(id);
        save();
    }

    @Override
    public void deleteAllSubTasks() {
        super.deleteAllSubTasks();
        save();
    }

    @Override
    public void deleteSubTaskById(int id) {
        super.deleteSubTaskById(id);
        save();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    @Override
    public void deleteEpicById(int id) {
        super.deleteEpicById(id);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        super.updateSubTask(subTask);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();

    }

    public static void main(String[] args) {
        Path path = Path.of(System.getProperty("user.home"), "/IdeaProjects/java-kanban/resources", "backedTasks.csv");
        File file = new File(String.valueOf(path));
        //Тест для проверки записи в файл>>>>
        FileBackedTaskManager manager = new FileBackedTaskManager(file);
        manager.createEpic(new Epic("Эпик1", "Темная тема в Пачке", null, 60 * 48, LocalDateTime.now().plusDays(5), null));
        manager.getEpicById(1);
        manager.createSubTask(new SubTask("Сабтаска2", "Техдолг Q2", TaskStatus.NEW, null, 60 * 24, LocalDateTime.now().plusDays(3), 1));
        manager.createTask(new Task("Таск1", "Доработать АС", TaskStatus.NEW, null, 60 * 12, LocalDateTime.now().plusDays(2)));
        manager.getSubTaskById(2);
        manager.createSubTask(new SubTask("Сабтаска2", "Техдолг Q2", TaskStatus.NEW, null, 60 * 56, LocalDateTime.now().plusDays(4), 1));
        // Тест для проверки записи из файла>>>>
        loadFromFile(file);
    }
}