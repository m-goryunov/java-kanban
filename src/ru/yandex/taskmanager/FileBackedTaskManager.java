package ru.yandex.taskmanager;

import ru.yandex.exceptions.ManagerSaveException;
import ru.yandex.historymanager.HistoryManager;
import ru.yandex.util.Managers;
import ru.yandex.model.*;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class FileBackedTaskManager extends InMemoryTaskManager implements TaskManager {


    private static final Path path = Paths.get(System.getProperty("user.home"), "/IdeaProjects/java-kanban/resources", "backedTasks.csv");
    private static File file;
    private static final HistoryManager historyManager = Managers.getDefaultHistory();


    public static void main(String[] args) {
        // Тест для проверки записи из файла>>>>
        /*FileBackedTaskManager();
        System.out.println("Tasks...\n" + tasks);
        System.out.println("SubTasks...\n" +subTasks);
        System.out.println("Epics...\n" +epics);
        System.out.println("History...\n" + historyManager.getHistory().toString());*/

        //Тест для проверки записи в файл>>>>
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager();
        fileBackedTaskManager.createEpic(new Epic("Эпик1", "Темная тема в Пачке", TaskStatus.NEW, null, null, new HashMap<>()));
        fileBackedTaskManager.createTask(new Task("Таск1", "Доработать АС", TaskStatus.NEW, null, null));
        fileBackedTaskManager.printTaskById(1);
        fileBackedTaskManager.printEpicById(2);
        fileBackedTaskManager.createTask(new Task("Таск2", "Доработать АС", TaskStatus.NEW, null, null));
        fileBackedTaskManager.createTask(new Task("Таск3", "Доработать АС", TaskStatus.NEW, null, null));
        fileBackedTaskManager.createSubTask(new SubTask("Сабтаска2", "Техдолг Q2", TaskStatus.NEW, null, 1));
        fileBackedTaskManager.printSubTaskById(5);
        fileBackedTaskManager.createTask(new Task("Таск4", "Доработать АС", TaskStatus.NEW, null, null));
    }


    public FileBackedTaskManager() {
        file = new File(String.valueOf(path));
    }


    private void save() {
        try (Writer fw = new FileWriter(file)) {
            fw.write("name, description, status, id, epicId" + '\n');

            for (int i = 0; i < 100; i++) {
                if (tasks.containsKey(i + 1)) {
                    fw.write(toString(tasks.get(i + 1)) + '\n');
                } else if (epics.containsKey(i + 1)) {
                    fw.write(toString(epics.get(i + 1)) + '\n');
                } else if (subTasks.containsKey(i + 1)) {
                    fw.write(toString(subTasks.get(i + 1)) + '\n');
                }
            }
            if (!historyManager.getHistory().isEmpty()) {
                fw.write('\n');
                fw.write("History: " + historyToString(historyManager));
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new ManagerSaveException("Запись не произведена!");
        }

    }


    private String toString(Task task) {
        return task.getName() + ", " + task.getDescription() + ", " + task.getStatus() + ", " + task.getId() + ", " + task.getEpicId();
    }


    private Task fromString(String value) {
        String[] lineContent = value.split(", ");

        return new Task(lineContent[0],
                lineContent[1],
                Enum.valueOf(TaskStatus.class, lineContent[2]),
                Integer.parseInt(lineContent[3]),
                Integer.parseInt(lineContent[4]));
    }


    private static String historyToString(HistoryManager manager) {
        List<Task> getIDs = List.copyOf(manager.getHistory());
        List<String> ids = new ArrayList<>();
        for (int i = 0; i < manager.getHistory().size(); i++) {
            Task task = getIDs.get(i);
            ids.add(task.getId().toString());
        }
        return String.join(", ", ids);
    }


    private static List<Integer> historyFromString(String value) {
        List<Integer> history = new ArrayList<>();
        String[] values = value.split(", ");

        for (String s : values) {
            history.add(Integer.parseInt(s));
        }
        return history;
    }


    /*static void FileBackedTaskManager() {
        FileBackedTaskManager fbtm = new FileBackedTaskManager();
        TaskManager taskManager = Managers.getDefault();

        try (BufferedReader buffer = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {

            while (buffer.ready()) {
                String line = buffer.readLine();
                if (line.equals("") || line.startsWith("ID")) {
                    System.out.println("Лишнее");
                    List<String> trash = new ArrayList<>(); // не смог придумать как от этого избавиться по другмоу(((
                    trash.add(line);

                } else if (line.startsWith("History: ")) {
                    line = line.substring(9);
                    List<Integer> IDs = List.copyOf(historyFromString(line));

                    for (Integer id : IDs) {
                        if (tasks.containsKey(id)) {
                            historyManager.add(tasks.get(id));
                        } else if (subTasks.containsKey(id)) {
                            historyManager.add(subTasks.get(id));
                        } else if (epics.containsKey(id)) {
                            historyManager.add(epics.get(id));
                        }
                    }
                } else {
                    Task task = fbtm.fromString(line);

                    if (task.getType().equals(TaskType.TASK)) {
                        tasks.put(task.getId(), task);
                    } else if (task.getType() == TaskType.SUBTASK) {
                        subTasks.put(task.getId(), new SubTask(task.getName(), task.getDescription(), task.getStatus(), task.getId(), task.getEpicId()));
                    } else if (task.getType() == TaskType.EPIC) {
                        epics.put(task.getId(), new Epic(task.getName(), task.getDescription(), task.getStatus(), task.getId(), task.getId(), null));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/


    static String loadFromFile(File file) {
        try {
            return Files.readString(Path.of(file.getPath()));
        } catch (IOException e) {
            System.out.println("Файл не считан!");
            throw new RuntimeException(e);
        }
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
    public Task printTaskById(int id) {
        save();
        return super.printTaskById(id);
    }

    @Override
    public SubTask printSubTaskById(int id) {
        save();
        return super.printSubTaskById(id);
    }


    @Override
    public Epic printEpicById(int id) {

        save();
        return super.printEpicById(id);
    }
}