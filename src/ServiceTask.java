import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.*;

public class ServiceTask<T extends Task> {
    private final Map<Integer, Task> listTasks = new HashMap<>();

    public void createTask() {
        Scanner scan = new Scanner(System.in);

        System.out.print("Введите заголовок: ");
        String name = scan.nextLine();

        System.out.print("Введите описание: ");
        String description = scan.nextLine();

        Task.Type type = null;
        while (type == null) {
            System.out.println("Выберите тип задачи: ");
            System.out.println("1 - Личная");
            System.out.println("2 - Рабочая");
            System.out.print("Введите цифру соответствующую типу: ");
            int answerType = scan.nextInt();
            switch (answerType) {
                case 1:
                    type = Task.Type.PERSONAL;
                    break;
                case 2:
                    type = Task.Type.WORKING;
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
                    repeat = Task.Repeat.ONE_TIME;
                    break;
                case 2:
                    repeat = Task.Repeat.DAILY;
                    break;
                case 3:
                    repeat = Task.Repeat.WEEKLY;
                    break;
                case 4:
                    repeat = Task.Repeat.MONTHLY;
                    break;
                case 5:
                    repeat = Task.Repeat.ANNUAL;
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

    public void addListTasks(Task task) {
        listTasks.put(task.getId(), task);
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

    public boolean appearsIn(LocalDate localDate, Task task) {
            Task.Repeat repeat = task.getRepeat();
            switch (repeat) {
                case ONE_TIME:
                    if (localDate.equals(task.getDate().toLocalDate())) {
                        return true;
                    }
                    break;

                case DAILY:
                    if (localDate.isBefore(task.getDate().toLocalDate())) {
                        return false;
                    } else {
                        return true;
                    }

                case WEEKLY:
                    if (Objects.equals(localDate.getDayOfWeek(), task.getDate().getDayOfWeek()) &&
                            localDate.isAfter(task.getDate().toLocalDate()) ||
                            localDate.isEqual(task.getDate().toLocalDate())) {
                        return true;
                    }
                    break;

                case MONTHLY:
                    if (Objects.equals(localDate.get(ChronoField.DAY_OF_MONTH), task.getDate().get(ChronoField.DAY_OF_MONTH)) &&
                            localDate.isAfter(task.getDate().toLocalDate()) ||
                            localDate.isEqual(task.getDate().toLocalDate())) {
                        return true;
                    }
                    break;

                case ANNUAL:
                    if (Objects.equals(localDate.get(ChronoField.DAY_OF_MONTH), task.getDate().get(ChronoField.DAY_OF_MONTH)) &&
                            Objects.equals(localDate.getMonth(), task.getDate().getMonth()) &&
                            localDate.isAfter(task.getDate().toLocalDate()) ||
                            localDate.isEqual(task.getDate().toLocalDate())) {
                        return true;
                    }
                    break;

                default:
                    return false;
            }
        return false;
    }


    public void getTasksForDay() {
        Scanner scan = new Scanner(System.in);
        LocalDate date;
        while (true) {
            System.out.print("Введите дату в формате (год-месяц-день): ");
            String answerDate = scan.nextLine();
            if (answerDate.matches("[12]0[0-9][0-9]\\-[01][0-9]\\-[0-3][0-9]")) {
                date = LocalDate.parse(answerDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                break;
            } else {
                System.out.println("Неверный формат ввода даты и времени!");
            }
        }
        System.out.println("Задачи на " + date + ":");
        for (Task current : listTasks.values()) {
            while (appearsIn(date, current)) {
                System.out.println(current.getName() + " " + current.getDescription() + " " + current.getType() +
                        " " + date);
                break;
            }
        }
    }

    public void getTasksToday() {
        System.out.println("Задачи на сегодня:");
        for (Task current : listTasks.values()) {
            while (appearsIn(LocalDate.now(), current)) {
                System.out.println(current.getName() + " " + current.getDescription() + " " + current.getType() +
                        " " + LocalDate.now());
                break;
            }
        }
    }

    public void getTasksTomorrow() {
        System.out.println("Задачи на завтра:");
        for (Task current : listTasks.values()) {
            while (appearsIn(LocalDate.now().plusDays(1), current)) {
                System.out.println(current.getName() + " " + current.getDescription() + " " + current.getType() +
                        " " + LocalDate.now().plusDays(1));
                break;
            }
        }
    }

    public Map<Integer, Task> getListTasks() {
        return listTasks;
    }
}