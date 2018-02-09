package by.vladislavitsi.web.control.authentication;

import by.vladislavitsi.web.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

import static by.vladislavitsi.web.Constants.*;

public class LogOutServlet extends HttpServlet {

    private static final String EMPTY_STRING = "";

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().invalidate();
        Cookie idCookie = new Cookie(USER_ID, EMPTY_STRING);
        idCookie.setMaxAge(MAX_COOKIE_AGE_DEAD);
        resp.addCookie(idCookie);
        Cookie loginCookie = new Cookie(USER_LOGIN, EMPTY_STRING);
        loginCookie.setMaxAge(MAX_COOKIE_AGE_DEAD);
        resp.addCookie(loginCookie);
        Cookie tokenCookie = new Cookie(USER_TOKEN, EMPTY_STRING);
        tokenCookie.setMaxAge(MAX_COOKIE_AGE_DEAD);
        resp.addCookie(tokenCookie);
        resp.sendRedirect(PAGE_ROOT);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }
}