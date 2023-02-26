package ru.yandex.taskmanager.impl;

import com.google.gson.Gson;
import ru.yandex.model.Epic;
import ru.yandex.model.SubTask;
import ru.yandex.model.Task;
import ru.yandex.server.KVTaskClient;
import ru.yandex.util.Managers;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class HttpTaskManager extends FileBackedTaskManager {

    private URI url;
    private static final String TASKS = "Tasks";
    private static final String SUBTASKS = "SubTasks";
    private static final String EPICS = "Epics";
    private static final String HISTORY = "History";
    Gson gson = Managers.getGson();

    public HttpTaskManager(URI url) {
        this.url = url;
    }

    KVTaskClient client = new KVTaskClient(url);


    @Override
    protected void save() {

        List<String> toStringed = new ArrayList<>();

        for (Task task : tasks.values()) {
            toStringed.add(toString(tasks.get(task.getId())));
        }
        client.put(TASKS, gson.toJson(toStringed));
        toStringed.clear();

        for (Epic epic : epics.values()) {
            toStringed.add(toString(epics.get(epic.getId())));
        }
        client.put(EPICS, gson.toJson(toStringed));
        toStringed.clear();

        for (SubTask subTask : subTasks.values()) {
            toStringed.add(toString(subTasks.get(subTask.getId())) + ", " + subTask.getEpicId().toString());
        }
        client.put(SUBTASKS, gson.toJson(toStringed));
        toStringed.clear();

        if (!historyManager.getHistory().isEmpty()) {
            client.put(HISTORY, gson.toJson(historyToString(historyManager)));
        }
    }

    @Override
    protected String toString(Task task) {
        return "[{" +
                "name:" + task.getName() +
                ",description:" + task.getDescription() +
                ",status:" + task.getStatus() +
                ",id:" + task.getId() +
                ",type:" + task.getType() +
                ",duration:" + task.getDuration() +
                ",startTime:" + task.getStartTime() +
                ",endTime:" + task.getEndTime() +
                "}]";

    }

    public static HttpTaskManager loadFromServer(URI uri) {
        HttpTaskManager manager = new HttpTaskManager(uri);
        KVTaskClient client = new KVTaskClient(uri);
        Gson gson = Managers.getGson();


        List<Task> listTask = List.of(gson.fromJson(client.load(TASKS), Task[].class));
        List<SubTask> listSubTask = List.of(gson.fromJson(client.load(TASKS), SubTask[].class));
        List<Epic> listEpic = List.of(gson.fromJson(client.load(TASKS), Epic[].class));
        List<Integer> listHistory = List.of(gson.fromJson(client.load(HISTORY), Integer.class));

        return manager;
    }

}


