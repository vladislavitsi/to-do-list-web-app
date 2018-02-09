package by.vladislavitsi.web.control.authentication;

import by.vladislavitsi.web.ApplicationContext;
import by.vladislavitsi.web.control.exceptions.DAOException;
import by.vladislavitsi.web.control.exceptions.ProfileExistsException;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

import static by.vladislavitsi.web.Constants.*;

public class SignUpServlet extends HttpServlet {

    private void signUp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        signUp(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        signUp(req, resp);
    }
}
