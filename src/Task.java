import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    public enum Type {
        PERSONAL,
        WORKING
    }

    public enum Repeat {
        ONE_TIME,
        DAILY,
        WEEKLY,
        MONTHLY,
        ANNUAL
    }

    private String name;
    private String description;
    private final Type type;
    private final LocalDateTime date;
    private final Repeat repeat;
    private static int currentId;
    private final int id;
    private boolean remote;

    public Task(String name, String description, Type type, LocalDateTime date, Repeat repeat) {

        setName(name);
        setDescription(description);

        if (type == null) {
            throw new IllegalArgumentException("Некоректные данные задачи в поле ТИП");
        } else {
            this.type = type;
        }

        this.date = date;

        if (repeat == null) {
            throw new IllegalArgumentException("Некоректные данные задачи в поле ПОВТОРЯЕМОСТЬ");
        } else {
            this.repeat = repeat;
        }

        this.id = ++currentId;
        remote = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty() || name.isBlank()) {
            throw new IllegalArgumentException("Некоректные данные задачи в поле ИМЯ");
        }
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null && description.isEmpty() && description.isBlank()) {
            throw new IllegalArgumentException("Некоректные данные задачи");
        }
        this.description = description;
    }

    public Type getType() {
        return type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Repeat getRepeat() {
        return repeat;
    }

    public int getId() {
        return id;
    }

    public boolean isRemote() {
        return remote;
    }

    public void setRemote(boolean remote) {
        this.remote = remote;
    }

    @Override
    public String toString() {
        return getName() + ", описание: " + getDescription().toLowerCase() +
                ", планируемая дата: " + getDate() + ", тип задачи - " + getType().toString().toLowerCase() +
                ", повторяемость - " + getRepeat().toString().toLowerCase() + ", id - " + getId() +
                ", удаленная задача - " + isRemote();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return name.equals(task.name) && description.equals(task.description) && type == task.type && date.equals(task.date) && repeat == task.repeat;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, type, date, repeat, id);
    }
}
