import org.junit.jupiter.api.*;
import ru.yandex.model.Epic;
import ru.yandex.model.SubTask;
import ru.yandex.model.Task;
import ru.yandex.model.TaskStatus;
import ru.yandex.server.HttpTaskServer;
import ru.yandex.server.KVServer;
import ru.yandex.taskmanager.TaskManager;
import ru.yandex.taskmanager.impl.HttpTaskManager;
import ru.yandex.util.Managers;

import java.io.IOException;
import java.time.LocalDateTime;

public class HttpTaskManagerTest extends TaskManagerTest<HttpTaskManager> {

    private KVServer kvServer;
    private HttpTaskServer taskServer;

/*    @BeforeEach
    void setUp() throws IOException {
        kvServer = new KVServer();
        kvServer.start();
        taskServer = new HttpTaskServer();
        taskServer.start();
    }*/



    @Test
    void DataEqualsTest() throws IOException {
        KVServer server = new KVServer();
        server.start();


        HttpTaskManager manager = new HttpTaskManager("http://localhost:8078");

        manager.createEpic(new Epic("Эпик1", "Темная тема в Пачке", null, 60 * 48, LocalDateTime.now().plusDays(5), null));
        manager.getEpicById(1);
        manager.createSubTask(new SubTask("Сабтаска2", "Техдолг Q2", TaskStatus.NEW, null, 60 * 24, LocalDateTime.now().plusDays(3), 1));
        manager.createTask(new Task("Таск1", "Доработать АС", TaskStatus.NEW, null, 60 * 12, LocalDateTime.now().plusDays(2)));
        manager.createTask(new Task("Таск2", "Доработать АС2", TaskStatus.NEW, null, 60 * 24, LocalDateTime.now().plusDays(22)));
        manager.getSubTaskById(2);
        manager.createSubTask(new SubTask("Сабтаска2", "Техдолг Q2", TaskStatus.NEW, null, 60 * 56, LocalDateTime.now().plusDays(4), 1));


        HttpTaskManager manager1 = new HttpTaskManager("http://localhost:8078", true);
        manager1.createEpic(new Epic("Эпик1", "Темная тема в Пачке", null, 60 * 48, LocalDateTime.now().plusDays(5), null));
        manager1.getEpicById(1);
        manager1.createSubTask(new SubTask("Сабтаска2", "Техдолг Q2", TaskStatus.NEW, null, 60 * 24, LocalDateTime.now().plusDays(3), 1));
        manager1.createTask(new Task("Таск1", "Доработать АС", TaskStatus.NEW, null, 60 * 12, LocalDateTime.now().plusDays(2)));
        manager1.createTask(new Task("Таск2", "Доработать АС2", TaskStatus.NEW, null, 60 * 24, LocalDateTime.now().plusDays(22)));
        manager1.getSubTaskById(2);
        manager1.createSubTask(new SubTask("Сабтаска2", "Техдолг Q2", TaskStatus.NEW, null, 60 * 56, LocalDateTime.now().plusDays(4), 1));

        server.stop();


        Assertions.assertEquals(manager.getAllTasks(), manager1.getAllTasks(),
                "Список задач после выгрузки не совпададает");
    }

    /*Следует добавить тесты с подобными проверками
    HttpTaskManager httpTaskManager = new HttpTaskManager("url....", true);
        assertEquals(taskManager.getAllTasks(), httpTaskManager.getAllTasks(),
                "Список задач после выгрузки не совпададает");
    Где проверяется каждая мапа на то, что все данные с сервера совпадают с теми, что были в памяти до выгрузки на KVServer. (следует таким образом проверить подзадачи, задачи, эпики, отсортированный список, историю)
    Так же можно было бы проверить значения поля protected int id; из InMemoryTaskManager
    */
}
