import org.junit.jupiter.api.*;
import ru.yandex.model.Epic;
import ru.yandex.model.SubTask;
import ru.yandex.model.Task;
import ru.yandex.model.TaskStatus;
import ru.yandex.server.HttpTaskServer;
import ru.yandex.server.KVServer;
import ru.yandex.taskmanager.TaskManager;
import ru.yandex.taskmanager.impl.FileBackedTaskManager;
import ru.yandex.taskmanager.impl.HttpTaskManager;
import ru.yandex.taskmanager.impl.InMemoryTaskManager;
import ru.yandex.util.Managers;

import java.io.IOException;
import java.time.LocalDateTime;

import static ru.yandex.model.TaskStatus.NEW;

public class HttpTaskManagerTest extends TaskManagerTest<HttpTaskManager> {


    @Test
    void DataEqualsTest() throws IOException {

        KVServer server = new KVServer();
        server.start();
        HttpTaskManager manager = new HttpTaskManager("http://localhost:8078");

        manager.createEpic(new Epic("Test addNewEpic", "Test addNewEpic description", null, 60 * 48, null, null));
        manager.getEpicById(1);
        manager.createSubTask(new SubTask("Test addNewSubTask", "Test addNewSubTask description", NEW, null
                , 60 * 48, LocalDateTime.of(2022, 10, 6, 5, 0), 1));
        manager.createSubTask(new SubTask("Test addNewSubTask", "Test addNewSubTask description", NEW, null
                , 60 * 24, LocalDateTime.of(2022, 4, 6, 18, 0), 1));
        manager.createSubTask(new SubTask("Test addNewSubTask", "Test addNewSubTask description", NEW, null
                , 60 * 12, LocalDateTime.of(2022, 2, 6, 22, 0), 1));
        manager.getSubTaskById(4);
        manager.createTask(new Task("Таск1", "Доработать АС", TaskStatus.NEW, null, 60 * 12,
                LocalDateTime.of(2022, 5, 20, 12, 0)));

        manager.createTask(new Task("Таск2", "Доработать АС2", TaskStatus.NEW, null, 60 * 24,
                LocalDateTime.of(2022, 3, 20, 12, 0)));


        HttpTaskManager manager1 = new HttpTaskManager("http://localhost:8078", true);


        server.stop();


        Assertions.assertEquals(manager.getAllTasks(), manager1.getAllTasks(),
                "Список задач после выгрузки не совпададает");
        Assertions.assertEquals(manager.getAllSubTasks(), manager1.getAllSubTasks(),
                "Список задач после выгрузки не совпададает");
        Assertions.assertEquals(manager.getAllEpics(), manager1.getAllEpics(),
                "Список задач после выгрузки не совпададает");
        Assertions.assertEquals(manager.getHistory(), manager1.getHistory(),
                "Список задач после выгрузки не совпададает");
        Assertions.assertEquals(manager.getPrioritizedTasks(), manager1.getPrioritizedTasks(),
                "Список задач после выгрузки не совпададает");
    }

    @Test
    void IdIsCorrectTest() throws IOException {

        KVServer server = new KVServer();
        server.start();
        HttpTaskManager manager = new HttpTaskManager("http://localhost:8078");

        Task task = new Task("Таск1", "Доработать АС", TaskStatus.NEW, null, 60 * 48
                , LocalDateTime.of(2022, 5, 6, 13, 0));


        manager.createTask(task);
        manager.getTaskById(1);

        HttpTaskManager httpManager = new HttpTaskManager("http://localhost:8078", true);

        server.stop();

        Assertions.assertEquals(httpManager.getId(), manager.getId());

    }
}
