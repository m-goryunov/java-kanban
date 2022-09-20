import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String taskInitialName1 = "Тасочка";
        String taskInitialDescription1 = "СБОЛ упал";
        String taskInitialStatus1 = "NEW";
        Integer taskInitialID1 = null;

        String subTaskInitialName1 = "Сабтаска";
        String subTaskInitialDescription1 = "Полить цветочек";
        String subTaskInitialStatus1 = "NEW";
        Integer subTaskInitialID1 = null;

        String epicInitialName1 = "Сабтаска";
        String epicInitialDescription1 = "Самый важный ППР";
        String epicInitialStatus1 = "NEW";
        Integer epicInitialID1 = null;

        Scanner scanner = new Scanner(System.in);
        TaskManager taskManager = new TaskManager();
        printMenu();
        while (true) {
            switch (scanner.nextInt()) {
                case 1:
                    taskManager.printAllTasks();
                    break;
                case 2:
                    taskManager.deleteAllTasks();
                    break;
                case 3:
                    System.out.println("Какую задачу показать?");
                    int printID = scanner.nextInt();
                    taskManager.printTaskByID(printID);
                    break;
                case 4:
                    System.out.println("Введите тип задачи: \n 1. Task \n 2. SubTask \n 3. Epic");
                    switch (scanner.nextInt()) {
                        case 1:
                            taskManager.createTask(taskInitialName1, taskInitialDescription1, taskInitialStatus1, taskInitialID1);
                            break;
                        case 2:
                            System.out.println("К какому Эпику относится подзадача?");
                            Integer epicID = scanner.nextInt();
                            taskManager.createSubTask(subTaskInitialName1, subTaskInitialDescription1, subTaskInitialStatus1, subTaskInitialID1, epicID);
                            break;
                        case 3:

                            break;
                    }
                    break;
                case 5:
                    System.out.println("Какую задачу обновить?");
                    int UpdateID = scanner.nextInt();
                    taskManager.updateTask(UpdateID, new Task("Новое название", "Новое описание", "IN PROGRESS", UpdateID));

                    taskManager.updateSubTask(UpdateID, new SubTask("Новое название", "Новое описание", "IN PROGRESS", UpdateID));
                    break;
                case 6:
                    System.out.println("Какую задачу удалить?");
                    int deleteID = scanner.nextInt();
                    taskManager.deleteTaskByID(deleteID);
                    break;
                case 7:

                default:
                    System.out.println("Такой команды нет");
            }
            printMenu();
        }
    }

    private static void printMenu() {
        System.out.println(" 1. Получить список всех задач\n 2. Удалить все задачи " + "\n 3. Получить задачу по идентификатору \n 4. Создать задачу \n 5. Обновить задачу " + "\n 6. Удалить задачу по идентификатору \n 7. Получить Подзадачи по Эпику");
    }
}
