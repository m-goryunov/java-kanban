package Primary;

import Supplementary.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class FileBackedTaskManager extends InMemoryTaskManager implements TaskManager {
    static final Path path = Paths.get(System.getProperty("user.home"), "/IdeaProjects/java-kanban/resources", "backedTasks.csv");
    static File file;

    public FileBackedTaskManager() {
        file = new File(String.valueOf(path));
    }

    void save() {
        try (Writer fw = new FileWriter(file)) {
            fw.write("ID, type, name, status, description" + '\n');

            for (int i = 0; i < 100; i++) {
                if (tasks.containsKey(i + 1)) {
                    fw.write(toString(tasks.get(i + 1)) + '\n');
                } else if (epics.containsKey(i + 1)) {
                    fw.write(toString(epics.get(i + 1)) + '\n');
                } else if (subTasks.containsKey(i + 1)) {
                    fw.write(toString(subTasks.get(i + 1)) + '\n');
                }
            }

            fw.write('\n');
            fw.write(historyToString(historyManager)); //НЕЧЕГО ПОКАЗЫВАТЬ!!!

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    String toString(Task task) {
        return task.ID + ", " + task.type + ", " + task.name + ", " + task.status + ", " + task.description;
    }

    Map<Integer, Task> fromString(String value) {
        System.out.println("Перевод в Task...");
        Map<Integer, Task> readTasks = new HashMap<>();
        String[] values = value.split(System.lineSeparator());
        for (int i = 0; i < values.length; i++) {
            String[] lineContent = values[i].split(",");
            Task task = new Task(
                    lineContent[0],
                    lineContent[1],
                    Enum.valueOf(TaskStatus.class, lineContent[2]),
                    Integer.parseInt(lineContent[3]),
                    Enum.valueOf(TaskType.class, lineContent[4])
            );
            readTasks.put(task.ID, task);
        }
        return readTasks;
    }

    private static String historyToString(HistoryManager manager) {
        System.out.println(manager.getHistory().toString());
        List<Task> getIDs = List.copyOf(manager.getHistory());
        List<String> IDs = new ArrayList<>();
        for (int i = 0; i <= manager.getHistory().size(); i++) {
            Task task = getIDs.get(i);
            IDs.add(task.ID.toString());
        }
        return String.join(", ", IDs);
    }

/*    static List<Integer> historyFromString(String value) {

    }*/

    static String loadFromFile(File file) { //восстанавливать данные менеджера из файла при запуске программы
        try {
            System.out.println("ПУТЬ: " + file.getPath());
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
    public void createSubTask(SubTask subTask, Integer epicID) {
        super.createSubTask(subTask, epicID);
        save();
    }

    @Override
    public void createEpic(Epic epic) {
        super.createEpic(epic);
        save();
    }

    @Override
    public void printTaskByID(int ID) {
        super.printTaskByID(ID);
    }

    @Override
    public void printSubTaskByID(int ID) {
        super.printSubTaskByID(ID);
    }

    @Override
    public void printEpicByID(int ID) {
        super.printEpicByID(ID);
    }

    static void FileBackedTaskManager() { //восстанавливать данные менеджера из файла при запуске программы

    }

    public static void main(String[] args) {
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager();

        fileBackedTaskManager.createTask(new Task("Таск1", "Доработать АС", TaskStatus.NEW, null, TaskType.TASK));
        fileBackedTaskManager.printTaskByID(1);
        fileBackedTaskManager.createEpic(new Epic("Эпик1", "Темная тема в Пачке", TaskStatus.NEW, 2, TaskType.EPIC));
        fileBackedTaskManager.printEpicByID(2);
        fileBackedTaskManager.createTask(new Task("Таск2", "Доработать АС", TaskStatus.NEW, null, TaskType.TASK));
        fileBackedTaskManager.createTask(new Task("Таск3", "Доработать АС", TaskStatus.NEW, null, TaskType.TASK));
        fileBackedTaskManager.createSubTask(new SubTask("Сабтаска2", "Техдолг Q2", TaskStatus.NEW, null, null, TaskType.SUBTASK), 2);
        fileBackedTaskManager.printSubTaskByID(5);
        fileBackedTaskManager.createTask(new Task("Таск4", "Доработать АС", TaskStatus.NEW, null, TaskType.TASK));
        fileBackedTaskManager.createTask(new Task("Таск5", "Доработать АС", TaskStatus.NEW, null, TaskType.TASK));
        fileBackedTaskManager.createSubTask(new SubTask("Сабтаска1", "Техдолг Q1", TaskStatus.NEW, null, null, TaskType.SUBTASK), 2);
        fileBackedTaskManager.createSubTask(new SubTask("Сабтаска3", "Техдолг Q3", TaskStatus.NEW, null, null, TaskType.SUBTASK), 2);


        //System.out.println(fileBackedTaskManager.fromString(FileBackedTaskManager.loadFromFile(file)));
    }
}
