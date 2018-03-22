package by.vladislavitsi.web.control.authentication;

import by.vladislavitsi.web.control.app.ApplicationContext;
import by.vladislavitsi.web.control.app.AbstractPostOnlyServlet;
import by.vladislavitsi.web.control.exceptions.DAOException;
import by.vladislavitsi.web.control.exceptions.ProfileExistsException;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

import static by.vladislavitsi.web.util.Constants.*;

public class SignUpServlet extends AbstractPostOnlyServlet {

    @Override
    protected void performRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String login = request.getParameter(USER_LOGIN);
            String pass = request.getParameter(USER_PASSWORD);
            HttpSession session = request.getSession(true);
            if(session.getAttribute(ATTRIBUTE_USER)!=null){
                request.getRequestDispatcher(PAGE_ROOT).forward(request, response);
                return;
            }
            ApplicationContext.getUserDAOImpl().addUser(login, pass);
            request.setAttribute(ATTRIBUTE_INFO, MESSAGE_SUCCESS_REGISTRATION);
            response.sendRedirect(PAGE_ROOT);
        } catch (ProfileExistsException e) {
            request.setAttribute(ATTRIBUTE_INFO, EXCEPTION_USER_EXIST);
            request.getRequestDispatcher(PAGE_SIGNUP).forward(request, response);
        } catch (DAOException e) {
            System.err.println(e.getMessage());
            request.setAttribute(ATTRIBUTE_INFO, MESSAGE_SOMETHING_WENT_WRONG);
            request.getRequestDispatcher(PAGE_SIGNUP).forward(request, response);
        }
    }
}
