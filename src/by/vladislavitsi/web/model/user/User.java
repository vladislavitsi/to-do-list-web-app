package by.vladislavitsi.web.model.user;

public class User {
    private final String id;
    private final String login;
    private final String token;

    public User(String id, String login, String token) {
        this.id = id;
        this.login = login;
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getToken() {
        return token;
    }
}
