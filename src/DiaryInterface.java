import java.util.Scanner;

public class DiaryInterface {

    ServiceTask serviceTask = new ServiceTask();

    public void start() {
        Scanner scan = new Scanner(System.in);
        mark:
        while (true) {
            System.out.println("1 - Создать задачу");
            System.out.println("2 - Получить список задач на сегодня");
            System.out.println("3 - Получить список задач на завтра");
            System.out.println("4 - Получить список задач по дате");
            System.out.println("5 - Удалить задачу по id");
            System.out.println("6 - Показать удаленные задачи");
            System.out.println("7 - Редактировать заголовок и описание");
            System.out.println("0 - Выход");
            System.out.print("Введи номер действия: ");
            int answer = scan.nextInt();
            if (answer < 0 || answer > 7) {
                System.out.println("Действие не поддерживается, введите коректное значение!");
                System.out.println();
            } else {
                switch (answer) {
                    case 1:
                        serviceTask.createTask();
                        break;
                    case 2:
                        serviceTask.getTasksToday();
                        break;
                    case 3:
                        serviceTask.getTasksTomorrow();
                        break;
                    case 4:
                        serviceTask.getTasksForDay();
                        break;
                    case 5:
                        serviceTask.deleteTaskById();
                        break;
                    case 6:
                        serviceTask.getRemoteTasks();
                        break;
                    case 7:
                        serviceTask.editTask();
                        break;
                    case 0:
                        System.out.println("Хорошего дня!");
                        break mark;
                    default:
                }
            }
        }
    }
}
