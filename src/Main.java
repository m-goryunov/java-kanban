import Primary.InMemoryTaskManager;
import Primary.TaskManager;
import Supplementary.Epic;
import Supplementary.SubTask;
import Supplementary.Task;
import Supplementary.TaskStatus;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Task task1 = new Task("Тасочка", "Доработать АС", TaskStatus.NEW, null);
        SubTask subTask1 = new SubTask("Сабтаска1", "Техдолг Q1", TaskStatus.NEW, null, null);
        SubTask subTask2 = new SubTask("Сабтаска2", "Техдолг Q2", TaskStatus.NEW, null, null);
        SubTask subTask3 = new SubTask("Сабтаска3", "Техдолг Q3", TaskStatus.NEW, null, null);
        Epic epic1 = new Epic("Самый важный ППР", "Доля прокрастинации сотрудников < 90 б.п.",
                                                                                                TaskStatus.NEW, null);

        Scanner scanner = new Scanner(System.in);
        TaskManager taskManager = new InMemoryTaskManager();

        printMenu();
        while (true) {
            switch (scanner.nextInt()) {
                case 1:
                    taskManager.printAllTasks();
                    taskManager.printAllEpics();
                    taskManager.printAllSubTasks();
                    break;
                case 2:
                    System.out.println("Что удаляем? \n 1. Таски \n 2. Эпики \n 3. Сабтаски");
                    switch (scanner.nextInt()) {
                        case 1:
                            taskManager.deleteAllTasks();
                            break;
                        case 2:
                            taskManager.deleteAllEpics();
                            break;
                        case 3:
                            taskManager.deleteAllSubTasks();
                    }
                    break;
                case 3:

                    System.out.println("Введите ID");
                    int printID = scanner.nextInt();
                    System.out.println("Какую задачу показать? \n 1. Таски \n 2. Эпики \n 3. Сабтаски");
                    switch (scanner.nextInt()) {
                        case 1:
                            taskManager.printTaskByID(printID);
                            break;
                        case 2:
                            taskManager.printEpicByID(printID);
                            break;
                        case 3:
                            taskManager.printSubTaskByID(printID);
                            break;
                    }
                    break;
                case 4:
                    System.out.println("Введите тип задачи: \n 1. Supplementary.Task \n 2. Supplementary.SubTask \n 3. Supplementary.Epic");
                    switch (scanner.nextInt()) {
                        case 1:
                            taskManager.createTask(task1);
                            break;
                        case 2:
                            System.out.println("К какому Эпику относится подзадача?");
                            Integer setEpic = scanner.nextInt();
                            taskManager.createSubTask(subTask1, setEpic);
                            break;
                        case 3:
                            taskManager.createEpic(epic1);
                            break;
                    }
                    break;
                case 5:
                    System.out.println("Введите ID задачи");
                    int updateID = scanner.nextInt();
                    System.out.println("Какую задачу обновить? \n 1. Supplementary.Task \n 2. Supplementary.SubTask \n 3. Supplementary.Epic");
                    switch (scanner.nextInt()) {
                        case 1:
                            taskManager.updateTask(updateID, new Task("Новое название"
                                    , "Новое описание"
                                    , TaskStatus.IN_PROGRESS, updateID));
                            break;
                        case 2:
                            System.out.println("К какому эпику присвоить?");
                            Integer newEpicID = scanner.nextInt();
                            taskManager.updateSubTask(updateID, new SubTask("Новое название"
                                    , "Новое описание"
                                    , TaskStatus.IN_PROGRESS
                                    , updateID, newEpicID));
                            break;
                        case 3:
                            taskManager.updateEpic(updateID, new Epic("Новое название"
                                    , "Новое описание"
                                    , TaskStatus.IN_PROGRESS
                                    , updateID));
                            break;
                    }
                    break;
                case 6:

                    System.out.println("Введите ID задачи");
                    int deleteID = scanner.nextInt();
                    System.out.println("Какую задачу удалить? \n 1. Supplementary.Task \n 2. Supplementary.SubTask \n 3. Supplementary.Epic");
                    switch (scanner.nextInt()) {
                        case 1:
                            taskManager.deleteTaskByID(deleteID);
                            break;
                        case 2:
                            taskManager.deleteSubTaskByID(deleteID);
                            break;
                        case 3:
                            taskManager.deleteEpicByID(deleteID);
                            break;
                    }
                    break;
                case 7:
                    System.out.println("По какому эпику получить подзадачи?");
                    int epicSubtasksIds = scanner.nextInt();
                    taskManager.getAllSubtasksByEpic(epicSubtasksIds);
                    break;

                case 8:
                    System.out.println(taskManager.getHistory());
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Такой команды нет");
            }
            printMenu();
        }
    }

    private static void printMenu() {
        System.out.println(" 1. Получить список всех задач\n 2. Удалить все задачи "
                + "\n 3. Получить задачу по идентификатору \n 4. Создать задачу \n 5. Обновить задачу "
                + "\n 6. Удалить задачу по идентификатору \n 7. Получить Подзадачи по Эпику" +
                "\n 8. Посмотреть историю просмотров");
    }
}
