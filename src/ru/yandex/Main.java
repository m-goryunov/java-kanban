package ru.yandex;

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



public class Main {

    public static void main(String[] args) throws IOException {

        KVServer server = new KVServer();
        server.start();
        TaskManager manager = Managers.getDefault();

        HttpTaskServer taskServer = new HttpTaskServer();
        taskServer.start();

        //Тест для проверки записи на сервер>>>>
        manager.createEpic(new Epic("Эпик1", "Темная тема в Пачке", null, 60 * 48, LocalDateTime.now().plusDays(5), null));
        manager.getEpicById(1);
        manager.createSubTask(new SubTask("Сабтаска2", "Техдолг Q2", TaskStatus.NEW, null, 60 * 24, LocalDateTime.now().plusDays(3), 1));
        manager.createTask(new Task("Таск1", "Доработать АС", TaskStatus.NEW, null, 60 * 12, LocalDateTime.now().plusDays(2)));
        manager.createTask(new Task("Таск2", "Доработать АС2", TaskStatus.NEW, null, 60 * 24, LocalDateTime.now().plusDays(22)));
        manager.getSubTaskById(2);
        manager.createSubTask(new SubTask("Сабтаска2", "Техдолг Q2", TaskStatus.NEW, null, 60 * 56, LocalDateTime.now().plusDays(4), 1));
        // Тест для проверки записи из сервера>>>>
        TaskManager manager1 = new HttpTaskManager("http://localhost:8078",true);

    }
}
