package by.vladislavitsi.web.model.file.impl;

import by.vladislavitsi.web.control.app.ApplicationContext;
import by.vladislavitsi.web.control.exceptions.DAOException;
import by.vladislavitsi.web.model.DBConnector;
import by.vladislavitsi.web.model.file.File;
import by.vladislavitsi.web.model.file.IFileDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static by.vladislavitsi.web.util.Constants.EXCEPTION_FILE_ADD;
import static by.vladislavitsi.web.util.Constants.EXCEPTION_FILE_DELETE;
import static by.vladislavitsi.web.util.Constants.EXCEPTION_FILE_GET;

public class DBFileDAO implements IFileDAO {

    private static final String QUERY_FILE_ADD = ApplicationContext.getStringResource("query.file.add");
    private static final String QUERY_FILE_GET = ApplicationContext.getStringResource("query.file.get");
    private static final String QUERY_FILE_DELETE = ApplicationContext.getStringResource("query.file.delete");

    @Override
    public File getFile(int fileId) throws DAOException {
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QUERY_FILE_GET)){
            preparedStatement.setInt(1, fileId);
            ResultSet resultSet = preparedStatement.executeQuery();
            File file = null;
            if (resultSet.first()) {
                int id = resultSet.getInt(1);
                String filename = resultSet.getString(2);
                String path = resultSet.getString(3);
                file = new File(id, filename, path);
            }
            return file;
        } catch (SQLException e) {
            throw new DAOException(EXCEPTION_FILE_GET, e);
        }
    }

    @Override
    public void addFile(File file) throws DAOException {
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QUERY_FILE_ADD)){
            preparedStatement.setInt(1, file.getId());
            preparedStatement.setString(2, file.getName());
            preparedStatement.setString(3, file.getPath());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(EXCEPTION_FILE_ADD, e);
        }
    }

    @Override
    public void deleteFile(int fileId) throws DAOException {
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QUERY_FILE_DELETE)){
            preparedStatement.setInt(1, fileId);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(EXCEPTION_FILE_DELETE, e);
        }
    }
}
