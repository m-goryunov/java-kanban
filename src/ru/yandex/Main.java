package ru.yandex;

import ru.yandex.taskmanager.TaskManager;
import ru.yandex.util.Managers;
import ru.yandex.model.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        TaskManager taskManager = Managers.getDefault();

        printMenu();
        while (true) {
            switch (scanner.nextInt()) {
                case 1 -> {
                    System.out.println(taskManager.getAllTasks().toString());
                    System.out.println(taskManager.getAllEpics().toString());
                    System.out.println(taskManager.getAllSubTasks().toString());

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
                        case 1 -> System.out.println(taskManager.getTaskById(printID));
                        case 2 -> System.out.println(taskManager.getEpicById(printID));
                        case 3 -> System.out.println(taskManager.getSubTaskById(printID));
                    }
                }
                case 4 -> {
                    System.out.println("Введите тип задачи: \n \n 1. Task \n 2. SubTask \n 3. Epic");
                    switch (scanner.nextInt()) {
                        case 1 -> {
                            taskManager.createTask(new Task("Тасочка1", "Доработать АС",
                                    TaskStatus.NEW, null));
                        }
                        case 2 -> {
                            System.out.println("К какому Эпику относится подзадача?");
                            Integer setEpic = scanner.nextInt();
                            taskManager.createSubTask(new SubTask("Сабтаска1", "Техдолг Q1",
                                    TaskStatus.NEW, null, setEpic));
                        }
                        case 3 -> taskManager.createEpic(new Epic("Эпик1", "Темная тема в Пачке", null));
                    }
                }
                case 5 -> {
                    System.out.println("Введите ID задачи");
                    int ID = scanner.nextInt();
                    System.out.println("Какую задачу обновить? \n 1. Task \n 2. SubTask \n 3. Epic");
                    System.out.println("К какому эпику присвоить?");
                    Integer newEpicID = scanner.nextInt();
                    switch (scanner.nextInt()) {
                        case 1 -> taskManager.updateTask(new Task("Новое название"
                                , "Новое описание"
                                , TaskStatus.IN_PROGRESS, ID));
                        case 2 -> taskManager.updateSubTask(new SubTask("Новое название"
                                , "Новое описание"
                                , TaskStatus.IN_PROGRESS
                                , ID, newEpicID));
                        case 3 -> taskManager.updateEpic(new Epic("Новое название"
                                , "Новое описание"
                                , ID));
                    }
                }
                case 6 -> {
                    System.out.println("Введите ID задачи");
                    int deleteID = scanner.nextInt();
                    System.out.println("Какую задачу удалить? \n \n 1. Task \n 2. SubTask \n 3. Epic");
                    switch (scanner.nextInt()) {
                        case 1 -> taskManager.deleteTaskById(deleteID);
                        case 2 -> taskManager.deleteSubTaskById(deleteID);
                        case 3 -> taskManager.deleteEpicById(deleteID);
                    }
                }
                case 7 -> {
                    System.out.println("По какому эпику получить подзадачи?");
                    int epicSubtasksIds = scanner.nextInt();
                    System.out.println(taskManager.getAllSubtasksByEpic(epicSubtasksIds).toString());
                }
                case 8 -> System.out.println(taskManager.getHistory().toString());
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
