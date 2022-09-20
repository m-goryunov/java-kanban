import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String initialName = "Тасочка";
        String initialDescription = "Самый важный ППР";
        String initialStatus = "NEW";
        Integer initialID = null;
        Scanner scanner = new Scanner(System.in);
        TaskManager taskManager = new TaskManager();
        printMenu();
        switch(scanner.nextInt()){
            case 1:
                taskManager.printAllTasks();
            case 2:
                taskManager.deleteAllTasks();
            case 3:
                int printID = 1;
                taskManager.printTaskByID(printID);
            case 4:
                System.out.println("Введите тип задачи: \n 1. Task \n 2. SubTask \n 3. Epic");
                switch (scanner.nextInt()){
                    case 1:
                        taskManager.createTask(initialName,initialDescription,initialStatus,initialID);
                    case 2:
                        Integer epicID = 2;

//                        SubTask subTask = new SubTask(name,description,status,ID,epicID);
                    case 3:

//                        ...
                }

            case 5:
                int UpdateID = 1; //выбрали, какой таск обновляем
                taskManager.updateTask(1, new Task
                        ("Новое название",
                        "Новое описание",
                        "IN PROGRESS",UpdateID));
            case 6:
                int deleteID = 1;
                taskManager.deleteTaskByID(deleteID);
            case 7:

        }

    }

    private static void printMenu() {
        System.out.println(" 1. Получить список всех задач\n 2. Удалить все задачи " +
                "\n 3. Получить задачу по идентификатору \n 4. Создать задачу \n 5. Обновить задачу " +
                "\n 6. Удалить задачу по идентификатору \n 7. Получить Подзадачи по Эпику");
    }
}
