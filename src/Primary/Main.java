package Primary;

import Supplementary.Epic;
import Supplementary.SubTask;
import Supplementary.Task;
import Supplementary.TaskStatus;


import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        TaskManager taskManager = new InMemoryTaskManager();
        HistoryManager historyManager = new InMemoryHistoryManager();

        printMenu();
        while (true) {
            switch (scanner.nextInt()) {
                case 1 -> {
                    taskManager.printAllTasks();
                    taskManager.printAllEpics();
                    taskManager.printAllSubTasks();
                }
                case 2 -> {
                    System.out.println("Что удаляем? \n 1. Таски \n 2. Эпики \n 3. Сабтаски");
                    switch (scanner.nextInt()) {
                        case 1 -> taskManager.deleteAllTasks();
                        case 2 -> taskManager.deleteAllEpics();
                        case 3 -> taskManager.deleteAllSubTasks();
                    }
                }
                case 3 -> {
                    System.out.println("Введите ID");
                    int printID = scanner.nextInt();
                    System.out.println("Какую задачу показать? \n 1. Таски \n 2. Эпики \n 3. Сабтаски");
                    switch (scanner.nextInt()) {
                        case 1 -> taskManager.printTaskByID(printID);
                        case 2 -> taskManager.printEpicByID(printID);
                        case 3 -> taskManager.printSubTaskByID(printID);
                    }
                }
                case 4 -> {
                    System.out.println("Введите тип задачи: \n \n 1. Task \n 2. SubTask \n 3. Epic");
                    switch (scanner.nextInt()) {
                        case 1 -> taskManager.createTask(new Task("Тасочка1", "Доработать АС", TaskStatus.NEW, null));
                        case 2 -> {
                            System.out.println("К какому Эпику относится подзадача?");
                            Integer setEpic = scanner.nextInt();
                            taskManager.createSubTask(new SubTask("Сабтаска1", "Техдолг Q1", TaskStatus.NEW, null, null), setEpic);
                        }
                        case 3 -> taskManager.createEpic(new Epic("Эпик1", "Темная тема в Пачке", TaskStatus.NEW, null));
                    }
                }
                case 5 -> {
                    System.out.println("Введите ID задачи");
                    int ID = scanner.nextInt();
                    System.out.println("Какую задачу обновить? \n 1. Task \n 2. SubTask \n 3. Epic");
                    switch (scanner.nextInt()) {
                        case 1 -> taskManager.updateTask(ID, new Task("Новое название"
                                , "Новое описание"
                                , TaskStatus.IN_PROGRESS, ID));
                        case 2 -> {
                            System.out.println("К какому эпику присвоить?");
                            Integer newEpicID = scanner.nextInt();
                            taskManager.updateSubTask(ID, new SubTask("Новое название"
                                    , "Новое описание"
                                    , TaskStatus.IN_PROGRESS
                                    , ID, newEpicID));
                        }
                        case 3 -> taskManager.updateEpic(ID, new Epic("Новое название"
                                , "Новое описание"
                                , TaskStatus.IN_PROGRESS
                                , ID));
                    }
                }
                case 6 -> {
                    System.out.println("Введите ID задачи");
                    int deleteID = scanner.nextInt();
                    System.out.println("Какую задачу удалить? \n \n 1. Task \n 2. SubTask \n 3. Epic");
                    switch (scanner.nextInt()) {
                        case 1 -> taskManager.deleteTaskByID(deleteID);
                        case 2 -> taskManager.deleteSubTaskByID(deleteID);
                        case 3 -> taskManager.deleteEpicByID(deleteID);
                    }
                }
                case 7 -> {
                    System.out.println("По какому эпику получить подзадачи?");
                    int epicSubtasksIds = scanner.nextInt();
                    taskManager.getAllSubtasksByEpic(epicSubtasksIds);
                }
                case 8 -> System.out.println(historyManager.getHistory().toString());
                case 0 -> System.exit(0);
                default -> System.out.println("Такой команды нет");
            }
            printMenu();
        }
    }

    private static void printMenu() {
        System.out.println("""
                 1. Получить список всех задач
                 2. Удалить все задачи
                 3. Получить задачу по идентификатору
                 4. Создать задачу
                 5. Обновить задачу
                 6. Удалить задачу по идентификатору
                 7. Получить Подзадачи по Эпику
                 8. Посмотреть историю просмотров
                 0. Выход из программы
                """);
    }
}

        /*Task task1 = new Task("Тасочка1", "Доработать АС", TaskStatus.NEW, null);
        SubTask subTask1 = new SubTask("Сабтаска1", "Техдолг Q1", TaskStatus.NEW, null, null);
        SubTask subTask2 = new SubTask("Сабтаска2", "Техдолг Q2", TaskStatus.NEW, null, null);
        SubTask subTask3 = new SubTask("Сабтаска3", "Техдолг Q3", TaskStatus.NEW, null, null);
        Epic epic1 = new Epic("Эпик1", "Темная тема в Пачке", TaskStatus.NEW, null);*/