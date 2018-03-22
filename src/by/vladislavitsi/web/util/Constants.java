package by.vladislavitsi.web.util;

import by.vladislavitsi.web.control.app.ApplicationContext;

public class Constants {
    public static final String PAGE_HEADER = ApplicationContext.getStringResource("page.header");
    public static final String PAGE_LOGIN = ApplicationContext.getStringResource("page.login");
    public static final String PAGE_SIGNUP = ApplicationContext.getStringResource("page.signup");
    public static final String PAGE_ROOT = ApplicationContext.getStringResource("page.root");
    public static final String PAGE_MAIN = ApplicationContext.getStringResource("page.main");

    public static final String USER_ID = ApplicationContext.getStringResource("user.id");
    public static final String USER_LOGIN = ApplicationContext.getStringResource("user.login");
    public static final String USER_TOKEN = ApplicationContext.getStringResource("user.token");
    public static final String USER_PASSWORD = ApplicationContext.getStringResource("user.password");

    public static final int MAX_COOKIE_AGE = 60*60*24*356;
    public static final int MAX_COOKIE_AGE_DEAD  = 0;

    public static final String ATTRIBUTE_USER = ApplicationContext.getStringResource("attribute.user");
    public static final String ATTRIBUTE_INFO = ApplicationContext.getStringResource("attribute.info");
    public static final String ATTRIBUTE_FILE = ApplicationContext.getStringResource("attribute.file");
    public static final String ATTRIBUTE_FILE_ID = ApplicationContext.getStringResource("attribute.fileId");
    public static final String ATTRIBUTE_TASK_ID = ApplicationContext.getStringResource("attribute.taskId");
    public static final String ATTRIBUTE_TASK_JSON = ApplicationContext.getStringResource("attribute.taskJson");
    public static final String ATTRIBUTE_ACTION = ApplicationContext.getStringResource("attribute.action");

    public static final String EXCEPTION_INVALID_LOGIN = ApplicationContext.getStringResource("exception.invalidLogin");
    public static final String EXCEPTION_FILE_GET = ApplicationContext.getStringResource("exception.file.get");

    public static final String EXCEPTION_FILE_ADD = ApplicationContext.getStringResource("exception.file.add");
    public static final String EXCEPTION_FILE_DELETE = ApplicationContext.getStringResource("exception.file.delete");
    public static final String EXCEPTION_USER_GET = ApplicationContext.getStringResource("exception.user.get");

    public static final String EXCEPTION_USER_ADD = ApplicationContext.getStringResource("exception.user.add");
    public static final String EXCEPTION_USER_VALIDATE = ApplicationContext.getStringResource("exception.user.validate");
    public static final String EXCEPTION_USER_EXIST = ApplicationContext.getStringResource("exception.user.exist");

    public static final String EXCEPTION_TASK_GET_ALL = ApplicationContext.getStringResource("exception.task.get.all");
    public static final String EXCEPTION_TASK_ADD = ApplicationContext.getStringResource("exception.task.add");
    public static final String EXCEPTION_TASK_SPECIFY = ApplicationContext.getStringResource("exception.task.specify");
    public static final String EXCEPTION_TASK_EDIT = ApplicationContext.getStringResource("exception.task.edit");
    public static final String EXCEPTION_TASK_GET_BIN = ApplicationContext.getStringResource("exception.task.get.bin");

    public static final String CONTEXT = ApplicationContext.getStringResource("context");

    public static final String MESSAGE_SOMETHING_WENT_WRONG = ApplicationContext.getStringResource("message.somethingWentWrong");
    public static final String MESSAGE_SUCCESS_REGISTRATION = ApplicationContext.getStringResource("message.successRegistration");
}