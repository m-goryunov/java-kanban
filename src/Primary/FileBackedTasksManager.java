package Primary;

import Supplementary.Task;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager {

    void save(){ //Должен сохранять в файл все задачи, подзадачи, эпики и историю просмотра любых задач. Лучше CSV

    }
    static void FileBackedTasksManager(){

    }

    @Override
    public void createTask(Task task) {
        super.createTask(task);
        save();
    }
}
