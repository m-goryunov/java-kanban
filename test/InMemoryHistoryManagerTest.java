import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.model.Epic;
import ru.yandex.model.SubTask;
import ru.yandex.model.Task;
import ru.yandex.model.TaskStatus;
import ru.yandex.taskmanager.TaskManager;
import ru.yandex.taskmanager.impl.InMemoryHistoryManager;
import ru.yandex.util.Managers;

import java.util.List;

public class InMemoryHistoryManagerTest extends InMemoryHistoryManager {

    @Test
    void historyManagerTest() {
        TaskManager taskManager = Managers.getDefault();

        Epic epic = new Epic("Эпик1", "Темная тема в Пачке", null);
        SubTask subTask = new SubTask("Сабтаска2", "Техдолг Q2", TaskStatus.NEW, null, 1);
        Task task = new Task("Таск1", "Доработать АС", TaskStatus.NEW, null);
        SubTask subTask1 = new SubTask("Сабтаска2", "Техдолг Q2", TaskStatus.NEW, null, 1);

        taskManager.createEpic(epic);
        taskManager.createTask(task);
        taskManager.createSubTask(subTask);
        taskManager.createSubTask(subTask1);

        taskManager.getSubTaskById(subTask.getId());
        taskManager.deleteSubTaskById(subTask.getId());
        taskManager.createSubTask(subTask);


        Assertions.assertTrue(taskManager.getHistory().isEmpty(), "История не пустая.");
        Assertions.assertNotNull(taskManager.getHistory(), "Null");


        taskManager.getTaskById(task.getId());
        taskManager.getTaskById(task.getId());
        taskManager.deleteTaskById(task.getId());
        taskManager.createTask(task);

        Assertions.assertEquals(0, taskManager.getHistory().size(), "Выявлен дубль");

        taskManager.getTaskById(task.getId());
        taskManager.getEpicById(epic.getId());
        taskManager.getSubTaskById(subTask.getId());
        taskManager.getSubTaskById(subTask1.getId());

        Assertions.assertEquals(4, taskManager.getHistory().size(), "История не заполнена для теста начало-середина-конец");

        taskManager.deleteTaskById(task.getId());
        List<Task> expected = List.of(epic, subTask, subTask1);
        Assertions.assertEquals(expected, taskManager.getHistory(), "Тест на начало не пройден.");
        taskManager.deleteSubTaskById(subTask.getId());
        expected = List.of(epic, subTask1);
        Assertions.assertEquals(expected, taskManager.getHistory(), "Тест на середину не пройден.");
        taskManager.deleteSubTaskById(subTask1.getId());
        expected = List.of(epic);
        Assertions.assertEquals(expected, taskManager.getHistory(), "Тест на конец не пройден.");
        Assertions.assertNotNull(taskManager.getHistory(), "Null");
    }
}
