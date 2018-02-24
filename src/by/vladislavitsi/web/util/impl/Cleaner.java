package by.vladislavitsi.web.util.impl;

import by.vladislavitsi.web.control.ApplicationContext;
import by.vladislavitsi.web.control.exceptions.DAOException;
import by.vladislavitsi.web.model.user.User;
import by.vladislavitsi.web.util.ICleaner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class Cleaner implements ICleaner {

    @Override
    public void cleanFile(int fileId) throws DAOException, IOException {
        by.vladislavitsi.web.model.file.File dataFile = ApplicationContext.getFileDAOImpl().getFile(fileId);
        if(dataFile != null){
            Files.deleteIfExists(new File(dataFile.getPath()).toPath());
        }
        ApplicationContext.getFileDAOImpl().deleteFile(fileId);
    }

    @Override
    public void cleanTasksAndFiles(User user, int taskId) throws DAOException, IOException {
        cleanFile(taskId);
        ApplicationContext.getTaskDAOImpl().deleteTask(Integer.parseInt(user.getId()), taskId);
    }

    @Override
    public void cleanTasksAndFiles(User user, List<Integer> taskIds) throws DAOException, IOException {
        for (int taskId : taskIds) {
            cleanTasksAndFiles(user, taskId);
        }
    }
}
