package ru.yandex.util;

import ru.yandex.model.Task;

import java.util.Comparator;

public class PriorityComparator implements Comparator<Task> {
    @Override
    public int compare(Task o1, Task o2) {
        if (o1.getStartTime() == null && o2.getStartTime() == null) {
            return o1.getId().compareTo(o2.getId());
        } else if (o1.getStartTime() == null) {
            return 1;
        } else if (o2.getStartTime() == null) {
            return -1;
        } else {
            return o1.getStartTime().compareTo(o2.getStartTime());
        }
    }
}
