package by.vladislavitsi.web.control.app;

import by.vladislavitsi.web.model.file.FileImplFactory;
import by.vladislavitsi.web.model.file.IFileDAO;
import by.vladislavitsi.web.model.task.ITaskDAO;
import by.vladislavitsi.web.model.task.TaskImplFactory;
import by.vladislavitsi.web.model.user.IUserDAO;
import by.vladislavitsi.web.model.user.UserImplFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.ResourceBundle;

import static by.vladislavitsi.web.util.Constants.CONTEXT;

public class ApplicationContext implements ServletContextListener {

    private static IUserDAO userDAOImpl;
    private static ITaskDAO taskDAOImpl;
    private static IFileDAO fileDAOImpl;
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("strings");
    private static final String EXCEPTION_WRONG_SPECS = ApplicationContext.getStringResource("exception.daoSpecs");
    private static final String CONTEXT_DAO_NAME = ApplicationContext.getStringResource("context.dao.name");

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            Context env = (Context)new InitialContext().lookup(CONTEXT);
            String dao = (String)env.lookup(CONTEXT_DAO_NAME);
            userDAOImpl = UserImplFactory.getImplFromFactory(dao);
            taskDAOImpl = TaskImplFactory.getImplFromFactory(dao);
            fileDAOImpl = FileImplFactory.getImplFromFactory(dao);
        } catch (NamingException e) {
            throw new IllegalStateException(EXCEPTION_WRONG_SPECS);
        }
    }

    public static String getStringResource(String key){
        return resourceBundle.getString(key);
    }

    public static IUserDAO getUserDAOImpl() {
        return userDAOImpl;
    }

    public static ITaskDAO getTaskDAOImpl() {
        return taskDAOImpl;
    }

    public static IFileDAO getFileDAOImpl(){
        return fileDAOImpl;
    }
}
