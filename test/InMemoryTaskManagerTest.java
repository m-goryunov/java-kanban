import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.model.Epic;
import ru.yandex.model.SubTask;
import ru.yandex.model.Task;
import ru.yandex.taskmanager.impl.InMemoryTaskManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import static ru.yandex.model.TaskStatus.*;


public class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @BeforeEach
    void createManager() {
        manager = new InMemoryTaskManager();
    }


    @Test
    void getAllTasks() {
        Task task = new Task("Test addNewTask", "Test addNewTask description", NEW, null, 60 * 48, LocalDateTime.now());
        Task task1 = new Task("Test addNewTask", "Test addNewTask description", NEW, null, 60 * 48, LocalDateTime.now());
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
        Epic epic = new Epic("Test addNewEpic", "Test addNewEpic description", null, 60 * 48, LocalDateTime.now(), null);
        manager.createEpic(epic);
        SubTask subTask = new SubTask("Test addNewSubTask", "Test addNewSubTask description", NEW, null, 60 * 48, LocalDateTime.now(), 1);
        SubTask subTask1 = new SubTask("Test addNewSubTask", "Test addNewSubTask description", NEW, null, 60 * 48, LocalDateTime.now(), 1);
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
        Epic epic = new Epic("Test addNewTask", "Test addNewTask description", 1, 60 * 48, LocalDateTime.now(), null);
        Epic epic1 = new Epic("Test addNewTask", "Test addNewTask description", 2, 60 * 48, LocalDateTime.now(), null);
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
        Epic epic = new Epic("Test addNewEpic", "Test addNewEpic description", null, 60 * 48, LocalDateTime.now(), null);
        manager.createEpic(epic);
        SubTask subTask = new SubTask("Test addNewSubTask", "Test addNewSubTask description", NEW, null, 60 * 48, LocalDateTime.now(), 1);
        SubTask subTask1 = new SubTask("Test addNewSubTask", "Test addNewSubTask description", NEW, null, 60 * 48, LocalDateTime.now(), 1);
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
        Epic epic = new Epic("Test addNewEpic", "Test addNewEpic description", null, 60 * 48, LocalDateTime.now(), null);
        SubTask subTask1 = new SubTask("Test addNewSubTask", "Test addNewSubTask description", NEW, null, 60 * 48, LocalDateTime.now(), 1);
        SubTask subTask2 = new SubTask("Test addNewSubTask", "Test addNewSubTask description", NEW, null, 60 * 48, LocalDateTime.now(), 1);
        SubTask subTask3 = new SubTask("Test addNewSubTask", "Test addNewSubTask description", NEW, null, 60 * 48, LocalDateTime.now(), 1);
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

    @Test
    void dateTimeCalculationIsCorrect() {
        Task task = new Task("Test addNewTask", "Test addNewTask description", NEW, null
                , 60 * 48, LocalDateTime.of(2022, 6, 6, 10, 0));

        manager.createTask(task);
        Assertions.assertNotNull(manager.getTaskById(task.getId()));
        Assertions.assertEquals(task, manager.getTaskById(task.getId()));

        LocalDateTime expectedEndTime = LocalDateTime.of(2022, 6, 6, 10, 0).plusMinutes(60 * 48);
        Assertions.assertEquals(expectedEndTime, manager.getTaskById(task.getId()).getEndTime());
    }

    @Test
    void dateTimeEpicCalculationIsCorrect() {

        Epic epic = new Epic("Test addNewEpic", "Test addNewEpic description", null, 60 * 48, null, null);
        SubTask subTask1 = new SubTask("Test addNewSubTask", "Test addNewSubTask description", NEW, null
                , 60 * 48, LocalDateTime.of(2022, 6, 6, 11, 0), 1);
        SubTask subTask2 = new SubTask("Test addNewSubTask", "Test addNewSubTask description", NEW, null
                , 60 * 24, LocalDateTime.of(2022, 7, 6, 12, 0), 1);
        SubTask subTask3 = new SubTask("Test addNewSubTask", "Test addNewSubTask description", NEW, null
                , 60 * 12, LocalDateTime.of(2022, 5, 6, 13, 0), 1);

        manager.createEpic(epic);
        manager.createSubTask(subTask1);
        manager.createSubTask(subTask2);
        manager.createSubTask(subTask3);

        Assertions.assertNotNull(manager.getEpicById(epic.getId()));
        Assertions.assertEquals(epic, manager.getEpicById(epic.getId()));
        Assertions.assertNotNull(manager.getSubTaskById(subTask1.getId()));
        Assertions.assertEquals(subTask1, manager.getSubTaskById(subTask1.getId()));
        Assertions.assertNotNull(manager.getSubTaskById(subTask2.getId()));
        Assertions.assertEquals(subTask2, manager.getSubTaskById(subTask2.getId()));
        Assertions.assertNotNull(manager.getSubTaskById(subTask3.getId()));
        Assertions.assertEquals(subTask3, manager.getSubTaskById(subTask3.getId()));

        LocalDateTime expectedEpicStartTime = LocalDateTime.of(2022, 5, 6, 13, 0);
        manager.setEpicCalendarization(manager.getEpicById(epic.getId()).getId());
        Assertions.assertEquals(expectedEpicStartTime, manager.getEpicById(epic.getId()).getStartTime());

        long expectedEpicDuration = (60 * 48) + (60 * 24) + (60 * 12);
        Assertions.assertEquals(expectedEpicDuration, manager.getEpicById(epic.getId()).getDuration());

        LocalDateTime expectedEndTime = expectedEpicStartTime.plusMinutes(expectedEpicDuration);
        Assertions.assertEquals(expectedEndTime, manager.getEpicById(epic.getId()).getEndTime());

    }

    @Test
    void taskPriorityIsCorrect() {
        Epic epic = new Epic("Test addNewEpic", "Test addNewEpic description", null, 60 * 48, null, null);
        SubTask subTask1 = new SubTask("Test addNewSubTask", "Test addNewSubTask description", NEW, null
                , 60 * 48, LocalDateTime.of(2022, 6, 6, 11, 0), 2);
        SubTask subTask2 = new SubTask("Test addNewSubTask", "Test addNewSubTask description", NEW, null
                , 60 * 24, LocalDateTime.of(2022, 7, 6, 12, 0), 2);
        SubTask subTask3 = new SubTask("Test addNewSubTask", "Test addNewSubTask description", NEW, null
                , 60 * 12, LocalDateTime.of(2022, 5, 6, 13, 0), 2);
        SubTask sameDateSubTask4 = new SubTask("Same", "Same", NEW, null
                , 60 * 12, LocalDateTime.of(2022, 5, 6, 13, 0), 2);
        Task task = new Task("Test addNewTask", "Test addNewTask description", NEW, null
                , 60 * 48, null);

        manager.createTask(task);
        manager.createEpic(epic);
        manager.createSubTask(subTask1);
        manager.createSubTask(subTask2);
        manager.createSubTask(subTask3);
        manager.createSubTask(sameDateSubTask4);


        Assertions.assertEquals(4,manager.getPrioritizedTasks().size());

        List<Task> expectedSet = new ArrayList<>();

        subTask2.setId(4);
        expectedSet.add(subTask2);
        subTask1.setId(3);
        expectedSet.add(subTask1);
        subTask3.setId(5);
        expectedSet.add(subTask3);
        task.setId(1);
        expectedSet.add(task);

        Assertions.assertEquals(expectedSet.toString(),manager.getPrioritizedTasks().toString());


    }
}

