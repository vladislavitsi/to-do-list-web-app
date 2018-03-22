package by.vladislavitsi.web.model.file;

public class File {
    private int id;
    private String name;
    private String path;

    public File(int id, String name, String path) {
        this.id = id;
        this.name = name;
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }
}
