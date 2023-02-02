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

    void save() { //Должен сохранять в файл все задачи, подзадачи, эпики и историю просмотра любых задач.
        //1. Все поля задач через запятую
        //2. Список задач с новой строки
        //3. Пустая строка
        //4. Идентификаторы задач из истории просмотров (флаг что были просмотрены и выполнены)
        try {
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
            Writer fileWriter = new FileWriter(file);

            fileWriter.write("ID, type, name, status, description");
            fileWriter.write('\n');
            for (int i = 0; i <= tasks.size(); i++) {
                if (tasks.containsKey(i + 1)) {
                    fileWriter.write(toString(tasks.get(i + 1)));
                    fileWriter.write('\n');
                }
            }
            for (int i = 0; i <= epics.size(); i++) {
                if (epics.containsKey(i + 1)) {
                    fileWriter.write(toString(epics.get(i + 1)));
                    fileWriter.write('\n');
                }
            }
            for (int i = 0; i <= subTasks.size(); i++) {
                if (subTasks.containsKey(i + 1)) {
                    fileWriter.write(toString(subTasks.get(i + 1)));
                    fileWriter.write('\n');
                }
            }
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    String toString(Task task) {
        System.out.println("Перевод в String...");
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
                    lineContent[4]
            );
            readTasks.put(task.ID, task);
        }
        return readTasks;
    }

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

    /*static String historyToString(HistoryManager manager) {

    }

    static List<Integer> historyFromString(String value) {

    }

    static void FileBackedTaskManager() { //восстанавливать данные менеджера из файла при запуске программы

    }*/

    public static void main(String[] args) {
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager();
        Integer setEpic = 1;


        fileBackedTaskManager.createTask(new Task("Таск1", "Доработать АС", TaskStatus.NEW, null, "Task"));
        fileBackedTaskManager.createEpic(new Epic("Эпик1", "Темная тема в Пачке", TaskStatus.NEW, null, "Epic"));
/*        fileBackedTaskManager.createSubTask(new SubTask("Сабтаска2", "Техдолг Q2", TaskStatus.NEW, null, null, "SubTask"), setEpic);
        fileBackedTaskManager.createSubTask(new SubTask("Сабтаска1", "Техдолг Q1", TaskStatus.NEW, null, null, "SubTask"), setEpic);
        fileBackedTaskManager.createSubTask(new SubTask("Сабтаска3", "Техдолг Q3", TaskStatus.NEW, null, null, "SubTask"), setEpic);*/


        //System.out.println(fileBackedTaskManager.fromString(FileBackedTaskManager.loadFromFile(file)));
    }
}
