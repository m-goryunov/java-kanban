import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import ru.yandex.model.Task;
import ru.yandex.taskmanager.impl.InMemoryTaskManager;


public class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @BeforeEach
    void createManager() {manager = new InMemoryTaskManager();}
}

