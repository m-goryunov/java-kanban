import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import ru.yandex.model.Epic;
import ru.yandex.model.SubTask;
import ru.yandex.model.Task;
import ru.yandex.taskmanager.impl.InMemoryTaskManager;

import java.util.ArrayList;
import java.util.List;

import static ru.yandex.model.TaskStatus.*;
import static ru.yandex.model.TaskStatus.DONE;


public class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @BeforeEach
    void createManager() {manager = new InMemoryTaskManager();}


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
    void getAllSubTasks() {
        Epic epic = new Epic("Test addNewEpic", "Test addNewEpic description", null);
        manager.createEpic(epic);
        SubTask subTask = new SubTask("Test addNewSubTask", "Test addNewSubTask description", NEW, null, 1);
        SubTask subTask1 = new SubTask("Test addNewSubTask", "Test addNewSubTask description", NEW, null, 1);
        List<Task> expTasks = new ArrayList<>();
        expTasks.add(subTask);
        expTasks.add(subTask1);
        manager.createSubTask(subTask);
        manager.createSubTask(subTask1);
        List<Task> actTasks = manager.getAllSubTasks();
        Assertions.assertNotNull(actTasks, "Задачи на возвращаются.");
        Assertions.assertEquals(expTasks.size(), actTasks.size(), "Неверное количество задач.");
        Assertions.assertEquals(subTask, actTasks.get(0), "Задачи не совпадают.");
        Assertions.assertEquals(subTask1, actTasks.get(1), "Задачи не совпадают.");
    }

    @Test
    void getAllEpics() {
        Epic epic = new Epic("Test addNewTask", "Test addNewTask description", 1);
        Epic epic1 = new Epic("Test addNewTask", "Test addNewTask description", 2);
        List<Epic> expEpics = new ArrayList<>();
        expEpics.add(epic);
        expEpics.add(epic1);
        manager.createEpic(epic);
        manager.createEpic(epic1);
        List<Epic> actEpics = manager.getAllEpics();
        Assertions.assertNotNull(actEpics, "Задачи на возвращаются.");
        Assertions.assertEquals(expEpics.size(), actEpics.size(), "Неверное количество задач.");
        Assertions.assertEquals(epic, actEpics.get(0), "Задачи не совпадают.");
        Assertions.assertEquals(epic1, actEpics.get(1), "Задачи не совпадают.");
    }


    @Test
    void getAllSubtasksByEpic() {
        Epic epic = new Epic("Test addNewEpic", "Test addNewEpic description", null);
        manager.createEpic(epic);
        SubTask subTask = new SubTask("Test addNewSubTask", "Test addNewSubTask description", NEW, null, 1);
        SubTask subTask1 = new SubTask("Test addNewSubTask", "Test addNewSubTask description", NEW, null, 1);
        manager.createSubTask(subTask);
        manager.createSubTask(subTask1);

        List<SubTask> expectedSubTasks = new ArrayList<>();
        expectedSubTasks.add(subTask);
        expectedSubTasks.add(subTask1);

        List<SubTask> actualSubtasks = manager.getAllSubtasksByEpic(epic.getId());

        Assertions.assertEquals(expectedSubTasks, actualSubtasks, "Список сабтасков составлен некорректно!");
        Assertions.assertNotNull(actualSubtasks);
    }

    @Test
    void updateEpicStatus() {
        Epic epic = new Epic("Test addNewEpic", "Test addNewEpic description", null);
        SubTask subTask1 = new SubTask("Test addNewSubTask", "Test addNewSubTask description", NEW, null, 1);
        SubTask subTask2 = new SubTask("Test addNewSubTask", "Test addNewSubTask description", NEW, null, 1);
        SubTask subTask3 = new SubTask("Test addNewSubTask", "Test addNewSubTask description", NEW, null, 1);
        manager.createEpic(epic);
        manager.createSubTask(subTask1);
        manager.createSubTask(subTask2);
        manager.createSubTask(subTask3);

        Assertions.assertEquals(NEW, epic.getStatus());
        subTask1.setStatus(IN_PROGRESS);
        manager.updateEpicStatus(epic.getId());
        Assertions.assertEquals(IN_PROGRESS, epic.getStatus());
        subTask2.setStatus(DONE);
        manager.updateEpicStatus(epic.getId());
        Assertions.assertEquals(IN_PROGRESS, epic.getStatus());
        subTask3.setStatus(DONE);
        manager.updateEpicStatus(epic.getId());
        Assertions.assertEquals(IN_PROGRESS, epic.getStatus());
        subTask1.setStatus(DONE);
        manager.updateEpicStatus(epic.getId());
        Assertions.assertEquals(DONE, epic.getStatus());
        manager.deleteAllSubTasks();
        manager.updateEpicStatus(epic.getId());
        Assertions.assertEquals(NEW, epic.getStatus());
    }
}

