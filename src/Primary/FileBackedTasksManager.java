package Primary;

import Supplementary.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager {
    static Path path;

    public FileBackedTasksManager() {
        path = Paths.get(System.getProperty("user.home"), "/IdeaProjects/java-kanban/resources", "check.csv");
    }


    void save() { //Должен сохранять в файл все задачи, подзадачи, эпики и историю просмотра любых задач. Лучше CSV
        //1. Все поля задач через запятую
        //2. Список задач с новой строки
        //3. Пустая строка
        //4. Идентификаторы задач из истории просмотров (флаг что были просмотрены и выполнены)
        try {
            Files.createFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }



    }


    String toString(Task task) {
        System.out.println("Перевод в String...");
        return task.ID + ", " + task.type + task.name + ", " + task.status + ", " + task.description;
    }

    Task fromString(String value) {
        System.out.println("Перевод в Task...");
        List<Task> task = new ArrayList<>();
        String[] values = value.split(System.lineSeparator());
        for (int i = 0; i < values.length; i++) {
            String[] lineContent = values[i].split(",");
            Task task1 = new Task(
                    lineContent[0],
                    lineContent[1],
                    Enum.valueOf(TaskStatus.class, lineContent[2]),
                    Integer.parseInt(lineContent[3]),
                    lineContent[4]
            );
            task.add(task1);
        }
        return task.get(1); // делает одну
    }


    static void FileBackedTasksManager() { //восстанавливать данные менеджера из файла при запуске программы

    }

    static String loadFromFile(File file) { //восстанавливать данные менеджера из файла при запуске программы
        try {
            System.out.println("ПУТЬ!" + file.getPath());
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

    public static void main(String[] args) {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager();


        System.out.println(fileBackedTasksManager.fromString(FileBackedTasksManager.loadFromFile(path.toFile())));

    }
}
