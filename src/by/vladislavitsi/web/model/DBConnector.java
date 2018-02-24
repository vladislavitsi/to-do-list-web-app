package by.vladislavitsi.web.model;

import by.vladislavitsi.web.control.ApplicationContext;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static by.vladislavitsi.web.util.Constants.CONTEXT;

public class DBConnector {

    private static final String DATASOURCE_DB = ApplicationContext.getStringResource("datasource.db");
    private static final String EXCEPTION_DB_CONFIGURATION = ApplicationContext.getStringResource("exception.dbconfig");

    private static DataSource dataSource;

    public static Connection getConnection() throws SQLException {
        try {
            if (dataSource==null){
                Context envCtx = (Context) (new InitialContext().lookup(CONTEXT));
                dataSource = (DataSource) envCtx.lookup(DATASOURCE_DB);
            }
        } catch (NamingException e) {
            throw new IllegalStateException(EXCEPTION_DB_CONFIGURATION, e);
        }
        return dataSource.getConnection();
    }
}
