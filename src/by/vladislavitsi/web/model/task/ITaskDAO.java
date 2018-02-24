package by.vladislavitsi.web.model.task;

import by.vladislavitsi.web.control.exceptions.DAOException;

import java.util.List;

public interface ITaskDAO {
    List<Task> getTasks(int userId) throws DAOException;
    void addTask(int userId, Task task) throws DAOException;
    void doneTask(int userId, int taskId) throws DAOException;
    void binTask(int userId, int taskId) throws DAOException;
    void deleteTask(int userId, int taskId) throws DAOException;
    void editTask(int userId, Task task) throws DAOException;
    List<Integer> getTasksToDelete(int i) throws DAOException;
}
