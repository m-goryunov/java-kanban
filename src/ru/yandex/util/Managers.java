package ru.yandex.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.yandex.taskmanager.HistoryManager;
import ru.yandex.taskmanager.impl.FileBackedTaskManager;
import ru.yandex.taskmanager.impl.HttpTaskManager;
import ru.yandex.taskmanager.impl.InMemoryHistoryManager;
import ru.yandex.taskmanager.impl.InMemoryTaskManager;
import ru.yandex.taskmanager.TaskManager;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.time.LocalDateTime;

public class Managers {

    private Managers() {
    }

    public static TaskManager getDefault(){
        return new HttpTaskManager("http://localhost:8078");
    }

    public static HistoryManager getDefaultHistory(){
        return new InMemoryHistoryManager();
    }

    public static TaskManager getDefaultBacked(){
        return new FileBackedTaskManager(
                new File(String.valueOf(Path.of(System.getProperty("user.home"),
                        "/IdeaProjects/java-kanban/resources",
                        "backedTasks.csv"))));}

    public static Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
        gsonBuilder.serializeNulls();
        gsonBuilder.setPrettyPrinting();
        return gsonBuilder.create();
    }
}
