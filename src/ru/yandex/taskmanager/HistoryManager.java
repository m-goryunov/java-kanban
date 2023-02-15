package ru.yandex.taskmanager;

import ru.yandex.model.*;

import java.util.List;

public interface HistoryManager {

    void add(Task task);

    void remove(int id);


    List<Task> getHistory();

}
