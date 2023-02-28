package ru.yandex.taskmanager.impl;

import com.google.gson.Gson;
import ru.yandex.model.Epic;
import ru.yandex.model.SubTask;
import ru.yandex.model.Task;
import ru.yandex.server.KVTaskClient;
import ru.yandex.util.Managers;

import java.util.ArrayList;
import java.util.List;

public class HttpTaskManager extends FileBackedTaskManager {

    private static final String TASKS = "Tasks";
    private static final String SUBTASKS = "SubTasks";
    private static final String EPICS = "Epics";
    private static final String HISTORY = "History";
    private final Gson gson = Managers.getGson();
    private String url;

    public HttpTaskManager(String url, boolean load) {
        this.url = url;
        if (load) {loadFromServer();}
    }

    public HttpTaskManager(String url) {
        this(url, false);
    }

    private KVTaskClient client = new KVTaskClient(url);


    @Override
    protected void save() {

        String jsonTasks = gson.toJson(new ArrayList<>(tasks.values()));
        client.put(TASKS, jsonTasks);

        String jsonSubTasks = gson.toJson(new ArrayList<>(subTasks.values()));
        client.put(SUBTASKS, jsonSubTasks);

        String jsonEpics = gson.toJson(new ArrayList<>(epics.values()));
        client.put(EPICS, jsonEpics);

        if (!historyManager.getHistory().isEmpty()) {
            client.put(HISTORY, gson.toJson(historyToString(historyManager))); //это и так список id
        }
    }

    private void loadFromServer() {

        //HttpTaskManager manager = new HttpTaskManager("http://localhost:8078", false);

        List<Task> listTask = List.of(gson.fromJson(client.load(TASKS), Task[].class));
        List<SubTask> listSubTask = List.of(gson.fromJson(client.load(SUBTASKS), SubTask[].class));
        List<Epic> listEpic = List.of(gson.fromJson(client.load(EPICS), Epic[].class));
        String value = gson.fromJson(client.load(HISTORY), String.class);

        for (Task task : listTask) {
            if (id < task.getId()) {
                id = task.getId();
            }
            tasks.put(task.getId(), task);
            prioritized.add(task);
        }

        for (Epic epic : listEpic) {
            if (id < epic.getId()) {
                id = epic.getId();
            }
            epic = new Epic(epic.getName(), epic.getDescription(), epic.getId(), epic.getDuration(), epic.getStartTime(), epic.getEndTime());
            epics.put(epic.getId(), epic);
        }

        for (SubTask subTask : listSubTask) {
            if (id < subTask.getId()) {
                id = subTask.getId();
            }
            subTasks.put(subTask.getId(), subTask);
            epics.get(subTask.getEpicId()).addRelatedSubtaskIds(subTask.getId());
            prioritized.add(subTask);
        }


        List<Integer> history = new ArrayList<>();
        String[] values = value.split(",");
        for (String s : values) {
            history.add(Integer.parseInt(s));
        }
        List<Integer> IDs = List.copyOf(history);
        for (Integer id : IDs) {
            if (tasks.containsKey(id)) {
                historyManager.add(tasks.get(id));
            } else if (subTasks.containsKey(id)) {
                historyManager.add(subTasks.get(id));
            } else if (epics.containsKey(id)) {
                historyManager.add(epics.get(id));
            }
        }
    }

}


