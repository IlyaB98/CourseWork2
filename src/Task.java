import java.time.LocalDateTime;
import java.util.Objects;

public class Task {

    public boolean isRemote() {
        return remote;
    }

    public void setRemote(boolean remote) {
        this.remote = remote;
    }

    public enum Type {
        ЛИЧНАЯ,
        РАБОЧАЯ
    }

    public enum Repeat {
        ОДНОКРАТНАЯ,
        ЕЖЕДНЕВНАЯ,
        ЕЖЕНЕДЕЛЬНАЯ,
        ЕЖЕМЕСЯЧНАЯ,
        ЕЖЕГОДНАЯ
    }

    private String name;
    private String description;
    private Type type;
    private LocalDateTime date;
    private Repeat repeat;
    private static int currentId;
    private int id;
    private boolean remote;

    public Task(String name, String description, Type type, LocalDateTime date, Repeat repeat) {
        setName(name);
        setDescription(description);
        setType(type);
        this.date = date;
        setRepeat(repeat);
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

    public void setType(Type type) {
        if (type == null) {
            throw new IllegalArgumentException("Некоректные данные задачи в поле ТИП");
        }
        this.type = type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Repeat getRepeat() {
        return repeat;
    }

    public void setRepeat(Repeat repeat) {
        if (repeat == null) {
            throw new IllegalArgumentException("Некоректные данные задачи в поле ПОВТОРЯЕМОСТЬ");
        }
        this.repeat = repeat;
    }

    public int getId() {
        return id;
    }


    @Override
    public String toString() {
        return "Задача: " + getName() + ", описание: " + getDescription().toLowerCase() +
                ", планируемая дата: " + getDate() + ", тип задачи - " + getType().toString().toLowerCase() +
                ", повторяемость - " + getRepeat().toString().toLowerCase() + ", id - " + getId() +
                ", удаленная задача - " + isRemote();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && name.equals(task.name) && description.equals(task.description) && type == task.type && date.equals(task.date) && repeat == task.repeat;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, type, date, repeat, id);
    }
}
