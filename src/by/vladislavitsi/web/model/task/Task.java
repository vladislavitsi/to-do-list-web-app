package by.vladislavitsi.web.model.task;

public class Task {
    private int id;
    private String text;
    private boolean done;
    private boolean bin;
    private String date;
    private String filename;

    public Task(int id, String text, boolean done, boolean bin, String date, String filename) {
        this.id = id;
        this.text = text;
        this.done = done;
        this.bin = bin;
        this.date = date;
        this.filename = filename;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public boolean isDone() {
        return done;
    }

    public boolean isBin() {
        return bin;
    }

    public String getDate() {
        return date;
    }

    public String getFilename() {
        return filename;
    }
}
