package by.vladislavitsi.web.control.authentication;

import by.vladislavitsi.web.control.app.AbstractPostOnlyServlet;
import by.vladislavitsi.web.control.app.AbstractServlet;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

import static by.vladislavitsi.web.util.Constants.*;

public class LogOutServlet extends AbstractServlet {

    private static final String EMPTY_STRING = "";
    @Override
    protected void performRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        Cookie idCookie = new Cookie(USER_ID, EMPTY_STRING);
        idCookie.setMaxAge(MAX_COOKIE_AGE_DEAD);
        response.addCookie(idCookie);
        Cookie loginCookie = new Cookie(USER_LOGIN, EMPTY_STRING);
        loginCookie.setMaxAge(MAX_COOKIE_AGE_DEAD);
        response.addCookie(loginCookie);
        Cookie tokenCookie = new Cookie(USER_TOKEN, EMPTY_STRING);
        tokenCookie.setMaxAge(MAX_COOKIE_AGE_DEAD);
        response.addCookie(tokenCookie);
        response.sendRedirect(PAGE_ROOT);
    }
}