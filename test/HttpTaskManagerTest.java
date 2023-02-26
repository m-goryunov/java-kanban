import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import ru.yandex.server.HttpTaskServer;
import ru.yandex.server.KVServer;

import java.io.IOException;

public class HttpTaskManagerTest {

    private KVServer kvServer;
    private HttpTaskServer taskServer;

    @BeforeEach
    void setUp() throws IOException {
        kvServer = new KVServer();
        kvServer.start();
        taskServer = new HttpTaskServer();
        taskServer.start();
    }

    @AfterEach
    void setUp1() throws IOException {
        kvServer.stop();
        taskServer.stop();
    }
}
