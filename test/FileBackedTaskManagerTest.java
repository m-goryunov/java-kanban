import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.model.Epic;
import ru.yandex.model.SubTask;
import ru.yandex.model.Task;
import ru.yandex.model.TaskStatus;
import ru.yandex.taskmanager.impl.FileBackedTaskManager;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static ru.yandex.taskmanager.impl.FileBackedTaskManager.loadFromFile;

public class FileBackedTaskManagerTest extends TaskManagerTest<FileBackedTaskManager> {

    private File getActualFile() {
        Path path = Path.of(System.getProperty("user.home"), "/IdeaProjects/java-kanban/resources", "backedTasks.csv");
        return new File(String.valueOf(path));
    }

    @BeforeEach
    void setUp() {
        manager = new FileBackedTaskManager(getActualFile());
    }

    @Test
    void fileBackedTaskManagerSaveToFileIsEmptyTest() throws IOException {
        manager.createEpic(new Epic("Эпик1", "Темная тема в Пачке", null, 60 * 48, LocalDateTime.now(), null));
        manager.createSubTask(new SubTask("Сабтаска2", "Техдолг Q2", TaskStatus.NEW, null, 60 * 48, LocalDateTime.now(), null));
        manager.createTask(new Task("Таск1", "Доработать АС", TaskStatus.NEW, null, 60 * 48, LocalDateTime.now()));
        manager.deleteAllTasks();
        manager.deleteAllEpics();

        try (BufferedReader buffer = new BufferedReader(new FileReader(getActualFile(), StandardCharsets.UTF_8))) {
            while (buffer.ready()) {
                String line = buffer.readLine();
                if (line.startsWith("NAME")) {
                    continue;
                } else if (line.equals("")) {
                    continue;
                } else {
                    Assertions.fail("Файл не пуст.");
                }
            }
        }
    }

    @Test
    void fileBackedTaskManagerSaveToFileEpicWithNoSubTasksTest() throws IOException { // Надеюсь правильно понял граничное условие - b. Эпик без подзадач.
        manager.createEpic(new Epic("Эпик1", "Темная тема в Пачке", null, 60 * 48, LocalDateTime.now(), null));

        try (BufferedReader buffer = new BufferedReader(new FileReader(getActualFile(), StandardCharsets.UTF_8))) {
            while (buffer.ready()) {
                String line = buffer.readLine();
                if (line.startsWith("NAME")) {
                    continue;
                } else if (line.startsWith("Эпик1")) {
                    continue;
                } else if (line.matches("[a-zA-Z ]*\\d+.*")) {
                    continue;
                } else {
                    Assertions.fail("Файл содержит прочие строки.");
                }
            }
        }
    }

    @Test
    void fileBackedTaskManagerSaveToFileIsEmptyHistoryTest() throws IOException {
        manager.createEpic(new Epic("Эпик1", "Темная тема в Пачке", null, 60 * 48, LocalDateTime.now(), null));
        manager.createSubTask(new SubTask("Сабтаска2", "Техдолг Q2", TaskStatus.NEW, null, 60 * 48, LocalDateTime.now(), 1));
        manager.createTask(new Task("Таск1", "Доработать АС", TaskStatus.NEW, null, 60 * 48, LocalDateTime.now()));

        try (BufferedReader buffer = new BufferedReader(new FileReader(getActualFile(), StandardCharsets.UTF_8))) {
            while (buffer.ready()) {
                String line = buffer.readLine();
                if (line.startsWith("NAME")) {
                    continue;
                } else if (line.matches("[a-zA-Z ]*\\d+.*")) {
                    Assertions.fail("Файл содержит историю.");
                }
            }
        }
    }

    @Test
    void fileBackedTaskManagerLoadFromFileTest() {
        List<Task> expTasks = new ArrayList<>();
        List<SubTask> expSubTasks = new ArrayList<>();
        List<Epic> expEpics = new ArrayList<>();
        List<Task> expHistory = new ArrayList<>();


        Epic epic = new Epic("Эпик1", "Темная тема в Пачке", null, 60 * 48, LocalDateTime.now(), null);
        SubTask subTask = new SubTask("Сабтаска2", "Техдолг Q2", TaskStatus.NEW, null, 60 * 48, LocalDateTime.now(), 1);
        Task task = new Task("Таск1", "Доработать АС", TaskStatus.NEW, null, 60 * 48, LocalDateTime.now());
        SubTask subTask1 = new SubTask("Сабтаска2", "Техдолг Q2", TaskStatus.NEW, null, 60 * 48, LocalDateTime.now(), 1);

        manager.createEpic(epic);
        manager.getEpicById(epic.getId());
        manager.createSubTask(subTask);
        manager.getSubTaskById(subTask.getId());
        manager.createTask(task);
        manager.createSubTask(subTask1);

        expEpics.add(epic);
        expSubTasks.add(subTask);
        expSubTasks.add(subTask1);
        expTasks.add(task);
        expHistory.add(epic);
        expHistory.add(subTask);

        loadFromFile(getActualFile());

        List<Task> actTasks = manager.getAllTasks();
        List<Task> actSubtasks = manager.getAllSubTasks();
        List<Epic> actEpics = manager.getAllEpics();
        List<Task> actHistory = manager.getHistory();

        Assertions.assertNotNull(actTasks);
        Assertions.assertNotNull(actSubtasks);
        Assertions.assertNotNull(actEpics);
        Assertions.assertNotNull(actHistory);

        Assertions.assertEquals(expTasks, actTasks, "Ошибка при загрузке Тасок.");
        Assertions.assertEquals(expSubTasks, actSubtasks, "Ошибка при загрузке Сабтасок.");
        Assertions.assertEquals(expEpics, actEpics, "Ошибка при загрузке Эпиков.");
        Assertions.assertEquals(expHistory, actHistory, "Ошибка при загрузке Истории.");
    }
}
