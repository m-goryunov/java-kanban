package ru.yandex.util;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import ru.yandex.model.Task;

import java.io.IOException;

public class TaskAdapter extends TypeAdapter<Task> {
    @Override
    public void write(JsonWriter jsonWriter, Task task) throws IOException {

    }

    @Override
    public Task read(JsonReader jsonReader) throws IOException {
        return null;
    }
}
