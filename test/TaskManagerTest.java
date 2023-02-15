import org.junit.jupiter.api.*;
import ru.yandex.model.Task;
import ru.yandex.taskmanager.TaskManager;
import ru.yandex.taskmanager.impl.InMemoryTaskManager;
import ru.yandex.util.Managers;

import static ru.yandex.model.TaskStatus.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


abstract class TaskManagerTest<T extends TaskManager> {
    T manager;

    @Test
    void createTask() {

        Task task = new Task("Test addNewTask", "Test addNewTask description", NEW, null);
        manager.createTask(task);
        final Task savedTask = manager.getTaskById(task.getId());

        Assertions.assertNotNull(savedTask, "Задача не найдена.");
        Assertions.assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = manager.getAllTasks();

        Assertions.assertNotNull(tasks, "Задачи на возвращаются.");
        Assertions.assertEquals(1, tasks.size(), "Неверное количество задач.");
        Assertions.assertEquals(task, tasks.get(0), "Задачи не совпадают.");
    }

    @Test
    void updateTask() {
        Task task = new Task("Test addNewTask", "Test addNewTask description", NEW, null);
        manager.createTask(task);
        Task updTask = new Task("Test updated name", "Test updated description", IN_PROGRESS, task.getId());
        manager.updateTask(updTask);
        final List<Task> actTasks = manager.getAllTasks();
        Assertions.assertNotNull(actTasks, "Задачи на возвращаются.");
        Assertions.assertEquals(1, actTasks.size(), "Неверное количество задач.");
        Assertions.assertEquals(updTask, actTasks.get(0), "Поля задачи не были обновлены.");

        Task wrongTask = new Task("Test updated name", "Test updated description", IN_PROGRESS, 3);
        manager.updateTask(wrongTask);
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            actTasks.get(3);
        });
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            actTasks.clear();
            manager.updateTask(wrongTask);
        });
    }

    @Test
    void getAllTasks() {
        Task task = new Task("Test addNewTask", "Test addNewTask description", NEW, null);
        Task task1 = new Task("Test addNewTask", "Test addNewTask description", NEW, null);
        List<Task> expTasks = new ArrayList<>();
        expTasks.add(task);
        expTasks.add(task1);
        manager.createTask(task);
        manager.createTask(task1);
        List<Task> actTasks = manager.getAllTasks();
        Assertions.assertNotNull(actTasks, "Задачи на возвращаются.");
        Assertions.assertEquals(expTasks.size(), actTasks.size(), "Неверное количество задач.");
        Assertions.assertEquals(task, actTasks.get(0), "Задачи не совпадают.");
        Assertions.assertEquals(task1, actTasks.get(1), "Задачи не совпадают.");
    }

    @Test
    void deleteAllTasks() {
        Task task = new Task("Test addNewTask", "Test addNewTask description", NEW, null);
        Task task1 = new Task("Test addNewTask", "Test addNewTask description", NEW, null);
        List<Task> expTasks = new ArrayList<>();
        expTasks.add(task);
        expTasks.add(task1);
        expTasks.clear();
        manager.createTask(task);
        manager.createTask(task1);
        final List<Task> actTasks = manager.getAllTasks();
        actTasks.size();
        actTasks.clear();
        Assertions.assertEquals(expTasks.size(), actTasks.size(), "Неверное количество задач.");
    }

    @Test
    void getTaskById() {
        Task task = new Task("Test addNewTask", "Test addNewTask description", NEW, null);
        manager.createTask(task);
        Task expTask = manager.getTaskById(task.getId());
        Assertions.assertNotNull(expTask);
        Assertions.assertEquals(expTask, task);
    }
}