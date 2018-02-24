package by.vladislavitsi.web.model.task.impl;

import by.vladislavitsi.web.control.ApplicationContext;
import by.vladislavitsi.web.control.exceptions.DAOException;
import by.vladislavitsi.web.model.DBConnector;
import by.vladislavitsi.web.model.task.ITaskDAO;
import by.vladislavitsi.web.model.task.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static by.vladislavitsi.web.util.Constants.*;

public class DBTaskDAO implements ITaskDAO {

    private static final String QUERY_TASKS_GET = ApplicationContext.getStringResource("query.task.get-tasks");
    private static final String QUERY_TASK_ADD = ApplicationContext.getStringResource("query.task.add");
    private static final String QUERY_TASK_DONE = ApplicationContext.getStringResource("query.task.done");
    private static final String QUERY_TASK_BIN = ApplicationContext.getStringResource("query.task.bin");
    private static final String QUERY_TASK_DELETE = ApplicationContext.getStringResource("query.task.delete");
    private static final String QUERY_TASKS_TO_DELETE = ApplicationContext.getStringResource("query.task.to-delete");
    private static final String QUERY_TASK_EDIT = ApplicationContext.getStringResource("query.task.edit");

    @Override
    public List<Task> getTasks(int userId) throws DAOException {
        List<Task> tasks = new ArrayList<>();
        try (Connection connection =  DBConnector.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(QUERY_TASKS_GET)){
            preparedStatement.setInt(1, userId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                int id = rs.getInt(1);
                String text = rs.getString(2);
                boolean done = rs.getBoolean(3);
                boolean bin = rs.getBoolean(4);
                Date date = rs.getDate(5);
                String filename = rs.getString(6);
                tasks.add(new Task(id, text, done, bin, String.valueOf(date), filename));
            }
            return tasks;
        } catch (SQLException e) {
            throw new DAOException(EXCEPTION_TASK_GET_ALL, e);
        }
    }

    @Override
    public void addTask(int userId, Task task) throws DAOException {
        try (Connection connection =  DBConnector.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(QUERY_TASK_ADD)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, task.getText());
            preparedStatement.setBoolean(3, task.isDone());
            preparedStatement.setBoolean(4, task.isBin());
            preparedStatement.setDate(5, Date.valueOf(task.getDate()));
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(EXCEPTION_TASK_ADD, e);
        }
    }

    private void specifyTask(int userId, int taskId, String query) throws DAOException{
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, taskId);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(EXCEPTION_TASK_SPECIFY, e);
        }
    }

    @Override
    public void doneTask(int userId, int taskId) throws DAOException {
        specifyTask(userId, taskId, QUERY_TASK_DONE);
    }

    @Override
    public void binTask(int userId, int taskId) throws DAOException {
        specifyTask(userId, taskId, QUERY_TASK_BIN);
    }

    @Override
    public void deleteTask(int userId, int taskId) throws DAOException {
        specifyTask(userId, taskId, QUERY_TASK_DELETE);
    }

    @Override
    public void editTask(int userId, Task task) throws DAOException {
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QUERY_TASK_EDIT)){
            preparedStatement.setString(1, task.getText());
            preparedStatement.setBoolean(2, task.isDone());
            preparedStatement.setBoolean(3, task.isBin());
            preparedStatement.setDate(4, Date.valueOf(task.getDate()));
            preparedStatement.setInt(5, userId);
            preparedStatement.setInt(6, task.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(EXCEPTION_TASK_EDIT, e);
        }
    }

    @Override
    public List<Integer> getTasksToDelete(int userId) throws DAOException {
        try (Connection connection = DBConnector.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(QUERY_TASKS_TO_DELETE)){
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Integer> taskIds = new ArrayList<>();
            while (resultSet.next()){
                taskIds.add(resultSet.getInt(1));
            }
            return taskIds;
        } catch (SQLException e) {
            throw new DAOException(EXCEPTION_TASK_GET_BIN, e);
        }
    }
}
