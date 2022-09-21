import java.util.*;

public class TaskManager {
    Map<Integer, Task> tasks = new HashMap<>();
    Map<Integer, SubTask> subTasks = new HashMap<>();
    Map<Integer, Epic> epics = new HashMap<>();

    public Integer getID() {
        return new Random().nextInt(1000);
    }

    void createTask(Task task) {
        tasks.put(getID(), task);
    }

    void updateTask(int ID, Task task) {
        if (tasks.containsKey(ID)) {
            tasks.put(ID, task);
        } else {
            System.out.println("Такой ID не существует.");
        }
    }

    void printAllTasks() {
        for (Map.Entry<Integer, Task> entry : tasks.entrySet()) {
            Integer ID = entry.getKey();
            Object task = entry.getValue();
            System.out.println("ID: " + ID + "\n" + task);
        }
    }

    void deleteAllTasks() {
        tasks.clear();
    }

    void printTaskByID(int ID) {
        System.out.println(tasks.get(ID));
    }

    void deleteTaskByID(int ID) {
        tasks.remove(ID);
    }

    void createSubTask(SubTask subTask, Integer epicID) {
        if (epics.containsKey(epicID)) {
            subTask.setEpicID(epicID);
            subTasks.put(getID(), subTask);
        } else {
            System.out.println("Для сабтаски не создан Эпик!");

        }
    }

    void updateSubTask(int ID, SubTask subTask) {
        if (subTasks.containsKey(ID)) {
            subTasks.put(ID, subTask);
            updateEpicStatus(subTask.getEpicID());

        } else {
            System.out.println("Такой ID не существует");
        }
    }

    void updateEpicStatus(Integer epicID) {
        for (SubTask subTask : subTasks.values()) {
            for (Epic epic: epics.values()) {
                if (subTask.status == "NEW" && epicID == epic.ID) {
                   epic.setEpicStatus("NEW");
                } else if (subTask.status == "DONE" && epicID == epic.ID) {
                    epic.setEpicStatus("DONE");
                } else {
                    epic.setEpicStatus("IN PROGRESS");
                }
            }
        }
    }


    /*
    Если у эпика нет подзадач или все они имеют статус NEW, то статус должен быть NEW.
    Если все подзадачи имеют статус DONE, то и эпик считается завершённым — со статусом DONE.
    Во всех остальных случаях статус должен быть IN_PROGRESS.
2. Когда меняется статус любой подзадачи в эпике, вам необходимо проверить,
     что статус эпика изменится соответствующим образом.
     При этом изменение статуса эпика может и не произойти,
     если в нём, к примеру, всё ещё есть незакрытые задачи.*/


    void printAllSubTasks() {
        for (Map.Entry<Integer, SubTask> entry : subTasks.entrySet()) {
            Integer ID = entry.getKey();
            Object task = entry.getValue();
            System.out.println("ID: " + ID + "\n" + task);
        }
    }

    void deleteAllSubTasks() {
        subTasks.clear();
        //Статус Эпика!!!
    }

    void printSubTaskByID(int ID) {
        System.out.println(subTasks.get(ID));
    }

    void deleteSubTaskByID(int ID) {
        subTasks.remove(ID);
    }

    void createEpic(Epic epic) {
        epics.put(getID(), epic);
    }

    void updateEpic(int ID, Epic epic) {
        if (epics.containsKey(ID)) {
            epics.put(ID, epic);
        } else {
            System.out.println("Такой ID не существует.");
        }
    }

    void printAllEpics() {
        for (Map.Entry<Integer, Epic> entry : epics.entrySet()) {
            Integer ID = entry.getKey();
            Object task = entry.getValue();
            System.out.println("ID: " + ID + "\n" + task);
        }
    }

    void deleteAllEpics() {
        epics.clear();
        subTasks.clear(); //не может существовать без Epic
    }

    void printEpicByID(int ID) {
        System.out.println(epics.get(ID));
    }

    void deleteEpicByID(int ID) {
        epics.remove(ID);
    }

    void getAllSubtasksByEpic(int ID) {
        List<SubTask> subTasksByEpic = new ArrayList<>();

        for (SubTask subTask : subTasks.values()) {
            if (subTask.getEpicID() == ID) {
                subTasksByEpic.add(subTask);
            }
            System.out.println(subTasksByEpic);
        }

    }


}
