import org.junit.jupiter.api.*;
import ru.yandex.model.Epic;
import ru.yandex.model.SubTask;
import ru.yandex.model.Task;
import ru.yandex.taskmanager.TaskManager;

import static ru.yandex.model.TaskStatus.*;

import java.util.List;


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
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> actTasks.get(3));
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            actTasks.clear();
            manager.updateTask(wrongTask);
        });
    }


    @Test
    void deleteAllTasks() {
        Task task = new Task("Test addNewTask", "Test addNewTask description", NEW, null);
        Task task1 = new Task("Test addNewTask", "Test addNewTask description", NEW, null);
        manager.createTask(task);
        manager.createTask(task1);
        Assertions.assertNotNull(manager.getAllTasks(), "Задачи на возвращаются.");
        manager.deleteAllTasks();
        Assertions.assertEquals(0, manager.getAllTasks().size(), "Неверное количество задач.");
    }

    @Test
    void getTaskById() {
        Task task = new Task("Test addNewTask", "Test addNewTask description", NEW, null);
        manager.createTask(task);

        Task expTask = manager.getTaskById(task.getId());
        Assertions.assertEquals(1, expTask.getId(), "Id не соответствует.");
        Assertions.assertNotNull(expTask, "Задача не возвращается.");
        Assertions.assertEquals(expTask, task, "Задача не равна ожидаемой.");
    }

    @Test
    void deleteTaskById() {
        Task task = new Task("Test addNewTask", "Test addNewTask description", NEW, null);
        manager.createTask(task);
        Assertions.assertEquals(task, manager.getTaskById(task.getId()));
        manager.deleteTaskById(task.getId());
        Assertions.assertNull(manager.getTaskById(1), "Задача найдена в БД.");
    }

    @Test
    void createSubTask() {
        Epic epic = new Epic("Test addNewEpic", "Test addNewEpic description", null);
        manager.createEpic(epic);
        SubTask subTask = new SubTask("Test addNewTask", "Test addNewTask description", NEW, null, 1);
        manager.createSubTask(subTask);
        final SubTask savedSubTask = manager.getSubTaskById(subTask.getId());
        Assertions.assertNotNull(savedSubTask, "Задача не найдена.");
        Assertions.assertEquals(subTask, savedSubTask, "Задачи не совпадают.");
        Assertions.assertNotNull(manager.getEpicById(savedSubTask.getEpicId()), "Связанный эпик не существует!");
        Assertions.assertEquals(epic.getId(), savedSubTask.getEpicId(), "Связанный эпик не совпадает!");

        final List<Task> subTasks = manager.getAllSubTasks();

        Assertions.assertNotNull(subTasks, "Задачи на возвращаются.");
        Assertions.assertEquals(1, subTasks.size(), "Неверное количество задач.");
        Assertions.assertEquals(subTask, subTasks.get(0), "Задачи не совпадают.");
    }

    @Test
    void updateSubTask() {
        Epic epic = new Epic("Test addNewEpic", "Test addNewEpic description", null);
        manager.createEpic(epic);
        SubTask subTask = new SubTask("Test addNewSubTask", "Test addNewSubTask description", NEW, null, 1);
        manager.createSubTask(subTask);
        SubTask updSubTask = new SubTask("Test updated name", "Test updated description", IN_PROGRESS, subTask.getId(), subTask.getEpicId());
        manager.updateSubTask(updSubTask);
        Assertions.assertNotEquals(subTask, manager.getSubTaskById(0));
        final List<Task> actSubTasks = manager.getAllSubTasks();
        Assertions.assertNotNull(actSubTasks, "Задачи на возвращаются.");
        Assertions.assertEquals(1, actSubTasks.size(), "Неверное количество задач.");
        Assertions.assertEquals(updSubTask, actSubTasks.get(0), "Поля задачи не были обновлены.");

        SubTask wrongSubTask = new SubTask("Test updated name", "Test updated description", IN_PROGRESS, 10, 3);
        manager.updateSubTask(wrongSubTask);
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> actSubTasks.get(3));
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            actSubTasks.clear();
            manager.updateSubTask(wrongSubTask);
        });
    }

    @Test
    void deleteAllSubTasks() {
        Epic epic = new Epic("Test addNewEpic", "Test addNewEpic description", null);
        manager.createEpic(epic);
        SubTask subTask = new SubTask("Test addNewSubTask", "Test addNewSubTask description", NEW, null, 1);
        SubTask subTask1 = new SubTask("Test addNewSubTask", "Test addNewSubTask description", NEW, null, 1);
        manager.createSubTask(subTask);
        manager.createSubTask(subTask1);
        Assertions.assertNotNull(manager.getAllSubTasks(), "Задачи на возвращаются.");
        manager.deleteAllSubTasks();
        Assertions.assertEquals(0, manager.getAllSubTasks().size(), "Неверное количество задач.");
        Assertions.assertEquals(NEW, manager.getEpicById(epic.getId()).getStatus(), "Статус эпика не обновлен!");
    }

    @Test
    void getSubTaskById() {
        Epic epic = new Epic("Test addNewEpic", "Test addNewEpic description", null);
        manager.createEpic(epic);
        SubTask subTask = new SubTask("Test addNewSubTask", "Test addNewSubTask description", NEW, null, 1);
        manager.createSubTask(subTask);

        Task expTask = manager.getSubTaskById(subTask.getId());
        Assertions.assertEquals(2, expTask.getId(), "Id не соответствует.");
        Assertions.assertNotNull(expTask, "Задача не возвращается.");
        Assertions.assertEquals(expTask, subTask, "Задача не равна ожидаемой.");
    }

    @Test
    void deleteSubTaskById() {
        Epic epic = new Epic("Test addNewEpic", "Test addNewEpic description", null);
        manager.createEpic(epic);
        SubTask subTask = new SubTask("Test addNewSubTask", "Test addNewSubTask description", NEW, null, 1);
        manager.createSubTask(subTask);

        Assertions.assertEquals(subTask, manager.getSubTaskById(subTask.getId()));
        manager.deleteSubTaskById(subTask.getId());
        Assertions.assertNull(manager.getSubTaskById(2), "Задача найдена в БД.");
    }

    @Test
    void createEpic() {
        Epic epic = new Epic("Test addNewTask", "Test addNewTask description", null);
        manager.createEpic(epic);
        final Epic savedEpic = manager.getEpicById(epic.getId());

        Assertions.assertNotNull(savedEpic, "Задача не найдена.");
        Assertions.assertEquals(epic, savedEpic, "Задачи не совпадают.");

        final List<Epic> epics = manager.getAllEpics();

        Assertions.assertNotNull(epics, "Задачи на возвращаются.");
        Assertions.assertEquals(1, epics.size(), "Неверное количество задач.");
        Assertions.assertEquals(epic, epics.get(0), "Задачи не совпадают.");
    }

    @Test
    void deleteEpicById() {
        Epic epic = new Epic("Test addNewTask", "Test addNewTask description", null);
        manager.createEpic(epic);
        Assertions.assertEquals(epic, manager.getEpicById(epic.getId()));
        manager.deleteEpicById(epic.getId());
        Assertions.assertNull(manager.getEpicById(1), "Задача найдена в БД.");
        for (Integer relatedIds : epic.getRelatedSubtaskIds().values()) {
            Assertions.assertNull(manager.getSubTaskById(relatedIds), "Найден не удаленный связанный сабтаск!");
        }
    }

    @Test
    void updateEpic() {
        Epic epic = new Epic("Test addNewEpic", "Test addNewEpic description", null);
        Epic updEpic = new Epic("Test addUpdatedEpic", "Test addUpdatedEpic description", 1);
        manager.createEpic(epic);
        manager.updateEpic(updEpic);
        Assertions.assertNotEquals(epic, manager.getEpicById(0));
        final List<Epic> actEpics = manager.getAllEpics();
        Assertions.assertNotNull(actEpics, "Задачи на возвращаются.");
        Assertions.assertEquals(1, actEpics.size(), "Неверное количество задач.");
        Assertions.assertEquals(updEpic, actEpics.get(0), "Поля задачи не были обновлены.");

        Epic wrongEpic = new Epic("Test updated name", "Test updated description", 10);
        manager.updateEpic(wrongEpic);
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> actEpics.get(3));
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            actEpics.clear();
            manager.updateEpic(wrongEpic);
        });
    }


    @Test
    void deleteAllEpics() {
        Epic epic = new Epic("Test addNewTask", "Test addNewTask description", 1);
        Epic epic1 = new Epic("Test addNewTask", "Test addNewTask description", 2);
        manager.createEpic(epic);
        manager.createEpic(epic1);
        Assertions.assertNotNull(manager.getAllEpics(), "Задачи на возвращаются.");
        manager.deleteAllEpics();
        manager.deleteAllSubTasks();
        Assertions.assertEquals(0, manager.getAllEpics().size(), "Неверное количество задач.");
        Assertions.assertEquals(0, manager.getAllSubTasks().size(), "Неверное количество задач.");
    }

    @Test
    void getEpicById() {
        Epic epic = new Epic("Test addNewTask", "Test addNewTask description", 1);
        manager.createEpic(epic);

        Epic expEpic = manager.getEpicById(epic.getId());
        Assertions.assertEquals(1, expEpic.getId(), "Id не соответствует.");
        Assertions.assertNotNull(expEpic, "Задача не возвращается.");
        Assertions.assertEquals(expEpic, epic, "Задача не равна ожидаемой.");
    }
}