package ru.yandex.util;

import ru.yandex.taskmanager.HistoryManager;
import ru.yandex.taskmanager.impl.InMemoryHistoryManager;
import ru.yandex.taskmanager.impl.InMemoryTaskManager;
import ru.yandex.taskmanager.TaskManager;

public class Managers {
    private Managers() {
    }

    public static TaskManager getDefault(){
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory(){
        return new InMemoryHistoryManager();
    }
}
