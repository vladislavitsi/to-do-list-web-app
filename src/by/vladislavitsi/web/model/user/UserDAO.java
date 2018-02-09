package by.vladislavitsi.web.model.user;

import by.vladislavitsi.web.control.exceptions.DAOException;

public interface UserDAO {
    User getUser(String login, String sha256Password) throws DAOException;
    void addUser(String login, String sha256Password) throws DAOException;
    boolean validateUser(String id, String login, String token) throws DAOException;
}