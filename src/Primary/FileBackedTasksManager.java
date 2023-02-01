package Primary;

import Supplementary.Epic;
import Supplementary.SubTask;
import Supplementary.Task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager {
    private static final String HOME_DIR = System.getProperty("user.home");


    public static void main(String[] args){
        FileBackedTasksManager fbtm = new FileBackedTasksManager();

        fbtm.save();
    }


    void save() { //Должен сохранять в файл все задачи, подзадачи, эпики и историю просмотра любых задач. Лучше CSV
        //1. Все поля задач через запятую
        //2. Список задач с новой строки
        //3. Пустая строка
        //4. Идентификаторы задач из истории просмотров (флаг что были просмотрены и выполнены)


        Path path = Paths.get(HOME_DIR,"/IdeaProjects/java-kanban/resources","test.csv");
        try {
            Files.createFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    String toString(Task task) {

        return task.ID + ", " + task.name + ", " + task.status + ", " + task.description;
    }

    Task fromString(String value){

    }

    public FileBackedTasksManager() { // конструктор
    }


    static void FileBackedTasksManager() { //

    }

    static void loadFromFile(File file){

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
}
