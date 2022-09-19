import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        printMenu();
        switch(scanner.nextInt()){
            case 1:

            case 2:

            case 3:

            case 4:

                System.out.println("Введите тип задачи: \n 1. Task \n 2. SubTask \n 3. Epic");
                switch (scanner.nextInt()){
                    case 1:
                        TaskManager taskManager = new TaskManager();
                        taskManager.createTask();
                    case 2:
                        System.out.println("Введите название/ID связанного Epic:");
                        Integer epicID = scanner.nextInt();

//                        SubTask subTask = new SubTask(name,description,status,ID,epicID);
                    case 3:

//                        ...
                }

            case 5:

            case 6:

            case 7:

        }

    }

    private static void printMenu() {
        System.out.println(" 1. Получить список всех задач\n 2. Удалить все задачи " +
                "\n 3. Получить задачу по идентификатору \n 4. Создать задачу \n 5. Обновить задачу " +
                "\n 6. Удалить задачу по идентификатору \n 7. Получить Подзадачи по Эпику");
    }
}
