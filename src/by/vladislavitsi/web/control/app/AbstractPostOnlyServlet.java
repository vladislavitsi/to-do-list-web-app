package by.vladislavitsi.web.control.app;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AbstractPostOnlyServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        performRequest(req, resp);
    }

    protected abstract void performRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
