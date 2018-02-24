package by.vladislavitsi.web.model.user.impl;

import by.vladislavitsi.web.control.ApplicationContext;
import by.vladislavitsi.web.control.exceptions.DAOException;
import by.vladislavitsi.web.control.exceptions.ProfileExistsException;
import by.vladislavitsi.web.control.exceptions.WrongAuthenticationException;
import by.vladislavitsi.web.model.DBConnector;
import by.vladislavitsi.web.model.user.IUserDAO;
import by.vladislavitsi.web.model.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static by.vladislavitsi.web.util.Constants.*;
import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;

public class DBUserDAO implements IUserDAO {

    private static final String QUERY_USER_GET = ApplicationContext.getStringResource("query.user.get");
    private static final String QUERY_USER_VALID = ApplicationContext.getStringResource("query.user.valid");
    private static final String QUERY_USER_CHECK_EXIST = ApplicationContext.getStringResource("query.user.exist");
    private static final String QUERY_USER_ADD = ApplicationContext.getStringResource("query.user.add");

    @Override
    public User getUser(String login, String sha256Password) throws DAOException {
        try (Connection connection = DBConnector.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(QUERY_USER_GET)){
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, sha256Password);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.first()){
                String dId = String.valueOf(rs.getInt(1));
                String dLogin = rs.getString(2);
                String dToken = rs.getString(3);
                return new User(dId, dLogin, dToken);
            } else {
                throw new WrongAuthenticationException(EXCEPTION_INVALID_LOGIN);
            }
        } catch (SQLException e) {
            throw new DAOException(EXCEPTION_USER_GET, e);
        }
    }

    @Override
    public void addUser(String login, String sha256Password) throws DAOException {
        try (Connection connection = DBConnector.getConnection()){
            synchronized (DBUserDAO.class){
                checkAlreadyExistsUser(connection, login);
                try(PreparedStatement preparedStatement = connection.prepareStatement(QUERY_USER_ADD)){
                    preparedStatement.setString(1, login);
                    preparedStatement.setString(2, sha256Password);
                    preparedStatement.setString(3, sha256Hex(login+Math.random()+sha256Password));
                    preparedStatement.executeUpdate();
                }
            }
        }catch (SQLException e) {
            throw new DAOException(EXCEPTION_USER_ADD, e);
        }
    }

    private void checkAlreadyExistsUser(Connection connection, String login) throws DAOException, SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY_USER_CHECK_EXIST)){
            preparedStatement.setString(1, login);
            if(preparedStatement.executeQuery().first()){
                throw new ProfileExistsException(EXCEPTION_USER_EXIST);
            }
        }
    }

    @Override
    public boolean validateUser(String id, String login, String token) throws DAOException {
        try (Connection connection = DBConnector.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(QUERY_USER_VALID)){
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, login);
            preparedStatement.setString(3, token);
            ResultSet rs = preparedStatement.executeQuery();
            return rs.first();
        } catch (SQLException e) {
            throw new DAOException(EXCEPTION_USER_VALIDATE, e);
        }
    }
}