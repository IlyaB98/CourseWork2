import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ServiceTask {
    private final Map<Integer, Task> listTasks = new HashMap<>();

    public void createTask() {
        Scanner scan = new Scanner(System.in);

        System.out.print("Введите заголовок: ");
        String name = scan.useDelimiter("\n").next();

        System.out.print("Введите описание: ");
        String description = scan.useDelimiter("\n").next();

        Task.Type type = null;
        while (type == null) {
            System.out.println("Выберите тип задачи: ");
            System.out.println("1 - Личная");
            System.out.println("2 - Рабочая");
            System.out.print("Введите цифру соответствующую типу: ");
            int answerType = scan.nextInt();
            switch (answerType) {
                case 1:
                    type = Task.Type.ЛИЧНАЯ;
                    break;
                case 2:
                    type = Task.Type.РАБОЧАЯ;
                    break;
                default:
                    System.out.println("Такого типа задачи не существует!");
            }
        }
        LocalDateTime dateTime;
        while (true) {
            System.out.print("Установите дату и время в формате (год-месяц-день час:минуты): ");
            String answerDateTime = scan.useDelimiter("\n").next();
            if (answerDateTime.matches("[12]0[0-9][0-9]\\-[01][0-9]\\-[0-3][0-9]\\ [0-2][0-9]\\:[0-5][0-9]")) {
                dateTime = LocalDateTime.parse(answerDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                break;
            } else {
                System.out.println("Неверный формат ввода даты и времени!");
            }
        }

        Task.Repeat repeat = null;
        while (repeat == null) {
            System.out.println("Выберети повторяемость задачи:");
            System.out.println("1 - Однократная");
            System.out.println("2 - Ежедневная");
            System.out.println("3 - Еженедельная");
            System.out.println("4 - Ежемесячная");
            System.out.println("5 - Ежегодная");
            System.out.print("Какую повторяемость желаете установить: ");
            int answerRepeat = scan.nextInt();
            switch (answerRepeat) {
                case 1:
                    repeat = Task.Repeat.ОДНОКРАТНАЯ;
                    break;
                case 2:
                    repeat = Task.Repeat.ЕЖЕДНЕВНАЯ;
                    break;
                case 3:
                    repeat = Task.Repeat.ЕЖЕНЕДЕЛЬНАЯ;
                    break;
                case 4:
                    repeat = Task.Repeat.ЕЖЕМЕСЯЧНАЯ;
                    break;
                case 5:
                    repeat = Task.Repeat.ЕЖЕГОДНАЯ;
                    break;
                default:
                    System.out.println("Нет такой повторяемости");
            }
        }

        Task task = new Task(name, description, type, dateTime, repeat);
        System.out.println(task);
        System.out.println();
        addListTasks(task);
    }

    public void editTask() {
        Scanner scan = new Scanner(System.in);
        mark:
        while (true) {
            System.out.print("Введите id задачи которую хотите отредактировть: ");
            int answerId = scan.nextInt();
            if (answerId > 0) {
                for (Task current : listTasks.values()) {
                    if (current.getId() == answerId) {
                        System.out.println("Вы хотите отредактировать задачу:");
                        System.out.println(current);

                        System.out.print("Введите новый заголовок: ");
                        String name = scan.useDelimiter("\n").next();
                        current.setName(name);

                        System.out.print("Введите новое описание: ");
                        String description = scan.useDelimiter("\n").next();
                        current.setDescription(description);

                        System.out.println("Задача отредактирована");
                        System.out.println(current);
                        break mark;
                    }
                }
                System.out.println("Задача с таким Id не найдена, повторите попытку или для выхода введите 0");
            } else if (answerId == 0) {
                break;
            }
        }
    }

    public void getRemoteTasks() {
        System.out.println("Удаленные задачи:");
        for (Task current : listTasks.values()) {
            if (current.isRemote()) {
                System.out.println(current);
            }
        }
    }

    public void deleteTaskById() {
        Scanner scan = new Scanner(System.in);
        mark:
        while (true) {
            System.out.print("Введите id задачи которую хотите удалить: ");
            int answerId = scan.nextInt();
            if (answerId > 0) {
                for (Task current : listTasks.values()) {
                    if (current.getId() == answerId) {
                        current.setRemote(true);
                        System.out.println("Задача удалена:" + current);
                        break mark;
                    }
                }
                System.out.println("Нет задач с таким Id, для выхода введите 0");
            } else if (answerId == 0) {
                break;
            }
        }
    }

    public void addListTasks(Task task) {
        listTasks.put(task.getId(), task);
        Task.Repeat repeat = task.getRepeat();
        switch (repeat) {
            case ЕЖЕДНЕВНАЯ:
                for (int i = 0; i <= LocalDateTime.now().getDayOfYear(); i++) {
                    Task task1 = new Task(task.getName(), task.getDescription(), task.getType(), task.getDate(),
                            task.getRepeat());
                    listTasks.put(task1.getId(), task1);
                    task.setDate(task1.getDate().plusDays(1));
                    System.out.println("Следующее повторение задачи будет " + task1.getDate());
                }
                break;
            case ЕЖЕНЕДЕЛЬНАЯ:
                for (int i = 0; i <= 52; i++) {
                    Task task1 = new Task(task.getName(), task.getDescription(), task.getType(), task.getDate(),
                            task.getRepeat());
                    listTasks.put(task1.getId(), task1);
                    task.setDate(task1.getDate().plusWeeks(1));
                    System.out.println("Следующее повторение задачи будет: " + task1.getDate());
                }
                break;
            case ЕЖЕМЕСЯЧНАЯ:
                for (int i = 0; i <= 12; i++) {
                    Task task1 = new Task(task.getName(), task.getDescription(), task.getType(), task.getDate(),
                            task.getRepeat());
                    listTasks.put(task1.getId(), task1);
                    task.setDate(task1.getDate().plusMonths(1));
                    System.out.println("Следующее повторение задачи будет " + task1.getDate());
                }
                break;
            case ЕЖЕГОДНАЯ:
                for (int i = 0; i <= 5; i++) {
                    Task task1 = new Task(task.getName(), task.getDescription(), task.getType(), task.getDate(),
                            task.getRepeat());
                    listTasks.put(task1.getId(), task1);
                    task.setDate(task1.getDate().plusYears(1));
                    System.out.println("Следующее повторение задачи будет " + task1.getDate());
                }
                break;
        }
    }

    public void getTasksForDay() {
        Scanner scan = new Scanner(System.in);
        LocalDate date;
        while (true) {
            System.out.print("Введите дату в формате (год-месяц-день): ");
            String answerDate = scan.useDelimiter("\n").next();
            if (answerDate.matches("[12]0[0-9][0-9]\\-[01][0-9]\\-[0-3][0-9]")) {
                date = LocalDate.parse(answerDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                break;
            } else {
                System.out.println("Неверный формат ввода даты и времени!");
            }
        }
        System.out.println("Задачи на " + date + ":");
        for (Task current : listTasks.values()) {
            if (date.equals(LocalDate.parse(current.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), DateTimeFormatter.ofPattern("yyyy-MM-dd")))) {
                System.out.println(current);
            }
        }
    }

    public void getTasksToday() {
        System.out.println("Задачи на сегодня:");
        for (Task current : listTasks.values()) {
            if (LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                    .equals(current.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))) {
                System.out.println(current);
            }
        }
    }

    public void getTasksTomorrow() {
        System.out.println("Задачи на завтра:");
        for (Task current : listTasks.values()) {
            if (LocalDateTime.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                    .equals(current.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))) {
                System.out.println(current);
            }
        }
    }

    public Map<Integer, Task> getListTasks() {
        return listTasks;
    }
}