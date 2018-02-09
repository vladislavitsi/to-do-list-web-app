package by.vladislavitsi.web;

import by.vladislavitsi.web.model.user.UserDAO;
import by.vladislavitsi.web.model.user.UserImplFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.ResourceBundle;

import static by.vladislavitsi.web.Constants.CONTEXT;

public class ApplicationContext implements ServletContextListener {

    private static UserDAO userDAOImpl;
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("strings");
    private static final String EXCEPTION_WRONG_SPECS = ApplicationContext.getStringResource("exception.daoSpecs");
    private static final String CONTEXT_DAO_NAME = ApplicationContext.getStringResource("context.dao.name");

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            Context env = (Context)new InitialContext().lookup(CONTEXT);
            String dao = (String)env.lookup(CONTEXT_DAO_NAME);
            userDAOImpl = UserImplFactory.getImplFromFactory(dao);
        } catch (NamingException e) {
            throw new IllegalStateException(EXCEPTION_WRONG_SPECS);
        }
    }

    public static String getStringResource(String key){
        return resourceBundle.getString(key);
    }

    public static UserDAO getUserDAOImpl() {
        return userDAOImpl;
    }
}
