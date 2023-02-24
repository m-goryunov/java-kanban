package ru.yandex.taskmanager.impl;

import java.io.File;

public class HttpTaskManager  extends FileBackedTaskManager{

    public HttpTaskManager(File file) {
        super(file);
    }
}
