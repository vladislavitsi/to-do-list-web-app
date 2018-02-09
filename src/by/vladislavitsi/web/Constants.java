package by.vladislavitsi.web;

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

    public static final String EXCEPTION_INVALID_LOGIN = ApplicationContext.getStringResource("exception.invalidLogin");
    public static final String EXCEPTION_USER_EXIST = ApplicationContext.getStringResource("exception.userExist");

    public static final String CONTEXT = ApplicationContext.getStringResource("context");

    public static final String MESSAGE_SOMETHING_WENT_WRONG = ApplicationContext.getStringResource("message.somethingWentWrong");
    public static final String MESSAGE_SUCCESS_REGISTRATION = ApplicationContext.getStringResource("message.successRegistration");
}