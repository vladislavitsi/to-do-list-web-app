package by.vladislavitsi.web.control.authentication;

import by.vladislavitsi.web.ApplicationContext;
import by.vladislavitsi.web.control.exceptions.DAOException;
import by.vladislavitsi.web.model.user.User;
import by.vladislavitsi.web.control.exceptions.WrongAuthenticationException;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

import static by.vladislavitsi.web.Constants.*;

public class LoginServlet extends HttpServlet {


    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            String login = request.getParameter(USER_LOGIN);
            String pass = request.getParameter(USER_PASSWORD);
            HttpSession session = request.getSession(true);
            if(session.getAttribute(ATTRIBUTE_USER)!=null){
                request.getRequestDispatcher(PAGE_ROOT).forward(request, response);
                return;
            }

            User user = ApplicationContext.getUserDAOImpl().getUser(login, pass);
            session.setAttribute(ATTRIBUTE_USER, user);

            Cookie idCookie = new Cookie(USER_ID, user.getId());
            idCookie.setMaxAge(MAX_COOKIE_AGE);
            Cookie loginCookie = new Cookie(USER_LOGIN,user.getLogin());
            loginCookie.setMaxAge(MAX_COOKIE_AGE);
            Cookie tokenCookie = new Cookie(USER_TOKEN,user.getToken());
            tokenCookie.setMaxAge(MAX_COOKIE_AGE);

            response.addCookie(idCookie);
            response.addCookie(loginCookie);
            response.addCookie(tokenCookie);

            response.sendRedirect(PAGE_ROOT);
        }catch (WrongAuthenticationException e){
            request.setAttribute(ATTRIBUTE_INFO, EXCEPTION_INVALID_LOGIN);
            request.getRequestDispatcher(PAGE_LOGIN).forward(request, response);
        } catch (DAOException e) {
            System.err.println(e.getMessage());
            request.setAttribute(ATTRIBUTE_INFO, MESSAGE_SOMETHING_WENT_WRONG);
            request.getRequestDispatcher(PAGE_LOGIN).forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        login(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        login(req, resp);
    }
}