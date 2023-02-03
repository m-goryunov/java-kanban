package Primary;

import Supplementary.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class FileBackedTaskManager extends InMemoryTaskManager implements TaskManager {
    private static final Path path = Paths.get(System.getProperty("user.home"), "/IdeaProjects/java-kanban/resources", "backedTasks.csv");
    private static File file;

    public FileBackedTaskManager() {
        file = new File(String.valueOf(path));
    }

    private void save() {
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


            if (!historyManager.getHistory().isEmpty()) {
                fw.write('\n');
                fw.write(historyToString(historyManager));
            }

        } catch (IOException e) {
            System.out.println("Не записано!");
            e.printStackTrace();
        }

    }


    private String toString(Task task) {
        return task.ID + ", " + task.type + ", " + task.name + ", " + task.status + ", " + task.description;
    }

    private Task fromString(String value) {
        String[] lineContent = value.split(", ");
        return new Task(
                lineContent[2],
                lineContent[4],
                Enum.valueOf(TaskStatus.class, lineContent[3]),
                Integer.parseInt(lineContent[0]),
                Enum.valueOf(TaskType.class, lineContent[1])
        );
    }

    private static String historyToString(HistoryManager manager) {
        List<Task> getIDs = List.copyOf(manager.getHistory());
        List<String> IDs = new ArrayList<>();
        for (int i = 0; i < manager.getHistory().size(); i++) {
            Task task = getIDs.get(i);
            IDs.add(task.ID.toString());
        }
        return String.join(", ", IDs);
    }

    private static List<Integer> historyFromString(String value) {
        List<Integer> history = new ArrayList<>();
        String[] values = value.split(", ");

        for (String s : values) {
            history.add(Integer.parseInt(s));
        }
        return history;
    }


    static void FileBackedTaskManager() {

        FileBackedTaskManager fbtm = new FileBackedTaskManager();
        try (BufferedReader buffer = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {

            while (buffer.ready()) {
                String line = buffer.readLine();
                if (!line.equals("") || !line.equals("ID, type, name, status, description")) {

                    if (fbtm.fromString(line).type.equals(TaskType.TASK)) {
                        tasks.put(fbtm.fromString(line).ID, new Task(
                                fbtm.fromString(line).name,
                                fbtm.fromString(line).description,
                                fbtm.fromString(line).status,
                                fbtm.fromString(line).ID,
                                fbtm.fromString(line).type)
                        );
                    } else if (fbtm.fromString(line).type.equals(TaskType.SUBTASK)) {
                        subTasks.put(fbtm.fromString(line).ID, new SubTask(
                                fbtm.fromString(line).name,
                                fbtm.fromString(line).description,
                                fbtm.fromString(line).status,
                                fbtm.fromString(line).ID,
                                null,
                                fbtm.fromString(line).type)
                        );
                    } else if (fbtm.fromString(line).type.equals(TaskType.EPIC)) {
                        epics.put(fbtm.fromString(line).ID, new Epic(
                                fbtm.fromString(line).name,
                                fbtm.fromString(line).description,
                                fbtm.fromString(line).status,
                                fbtm.fromString(line).ID,
                                fbtm.fromString(line).type)
                        );
                    } else {
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
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String loadFromFile(File file) {
        try {
            System.out.println("Путь: " + file.getPath());
            return Files.readString(Path.of(file.getPath()));
        } catch (IOException e) {
            System.out.println("Файл не считан!");
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {


        FileBackedTaskManager();
        System.out.println("Tasks...");
        System.out.println(tasks);
        System.out.println("===========");
        System.out.println("Subtasks...");
        System.out.println(subTasks);
        System.out.println("===========");
        System.out.println("Epics...");
        System.out.println(epics);
        System.out.println("===========");
        System.out.println("History...");
        System.out.println(historyManager.getHistory().toString());
        System.out.println("===========");

        /*
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
        fileBackedTaskManager.createSubTask(new SubTask("Сабтаска3", "Техдолг Q3", TaskStatus.NEW, null, null, TaskType.SUBTASK), 2);*/
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
        save();
    }

    @Override
    public void printSubTaskByID(int ID) {
        super.printSubTaskByID(ID);
        save();
    }

    @Override
    public void printEpicByID(int ID) {
        super.printEpicByID(ID);
        save();
    }
}
