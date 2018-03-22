package by.vladislavitsi.web.control.app;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.vladislavitsi.web.util.Constants.PAGE_MAIN;

public class MainServlet extends AbstractServlet {

    @Override
    protected void performRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(PAGE_MAIN).forward(request, response);
    }

}