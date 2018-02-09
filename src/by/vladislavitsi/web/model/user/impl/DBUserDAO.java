package by.vladislavitsi.web.model.user.impl;

import by.vladislavitsi.web.ApplicationContext;
import by.vladislavitsi.web.control.exceptions.DAOException;
import by.vladislavitsi.web.control.exceptions.ProfileExistsException;
import by.vladislavitsi.web.control.exceptions.WrongAuthenticationException;
import by.vladislavitsi.web.model.user.UserDAO;
import by.vladislavitsi.web.model.user.User;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import static by.vladislavitsi.web.Constants.*;
import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;

public class DBUserDAO implements UserDAO {

    private static final String QUERY_USER_GET = ApplicationContext.getStringResource("query.user.get");
    private static final String QUERY_USER_VALID = ApplicationContext.getStringResource("query.user.valid");
    private static final String QUERY_USER_CHECK_EXIST = ApplicationContext.getStringResource("query.user.exist");
    private static final String QUERY_USER_ADD = ApplicationContext.getStringResource("query.user.add");
    private static final String DATASOURCE_DB = ApplicationContext.getStringResource("datasource.db");

    private DataSource dataSource;

    @Override
    public User getUser(String login, String sha256Password) throws DAOException {
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_USER_GET);
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
        } catch (NamingException | SQLException e) {
            System.out.println(e.getMessage());
            throw new DAOException(Arrays.toString(e.getStackTrace()));
        } finally {
            try {
                if(connection!=null){
                    connection.close();
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
    }

    @Override
    public void addUser(String login, String sha256Password) throws DAOException {
        Connection connection = null;
        try{
            connection = getConnection();
            synchronized (this){
                checkAlreadyExistsUser(connection, login);
                PreparedStatement preparedStatement = connection.prepareStatement(QUERY_USER_ADD);
                preparedStatement.setString(1, login);
                preparedStatement.setString(2, sha256Password);
                preparedStatement.setString(3, sha256Hex(login+Math.random()+sha256Password));
                preparedStatement.executeUpdate();
            }
        }catch (NamingException | SQLException e) {
            throw new DAOException(e);
        }finally {
            try {
                if (connection!=null){
                    connection.close();
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
    }

    private void checkAlreadyExistsUser(Connection connection, String login) throws DAOException, SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_USER_CHECK_EXIST);
        preparedStatement.setString(1, login);
        if(preparedStatement.executeQuery().first()){
            throw new ProfileExistsException(EXCEPTION_USER_EXIST);
        }
    }

    @Override
    public boolean validateUser(String id, String login, String token) throws DAOException {
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_USER_VALID);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, login);
            preparedStatement.setString(3, token);
            ResultSet rs = preparedStatement.executeQuery();
            return rs.first();
        } catch (NamingException | SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                if (connection != null){
                    connection.close();
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
    }

    private Connection getConnection() throws NamingException, SQLException {
        if (dataSource==null){
            Context envCtx = (Context) (new InitialContext().lookup(CONTEXT));
            dataSource = (DataSource) envCtx.lookup(DATASOURCE_DB);
        }
        return dataSource.getConnection();
    }
}
