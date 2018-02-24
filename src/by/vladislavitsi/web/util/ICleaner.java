package by.vladislavitsi.web.util;

import by.vladislavitsi.web.control.exceptions.DAOException;
import by.vladislavitsi.web.model.user.User;

import java.io.IOException;
import java.util.List;

public interface ICleaner {
    void cleanFile(int fileId) throws DAOException, IOException;

    void cleanTasksAndFiles(User user, int taskId) throws DAOException, IOException;

    void cleanTasksAndFiles(User user, List<Integer> taskIds) throws DAOException, IOException;
}
