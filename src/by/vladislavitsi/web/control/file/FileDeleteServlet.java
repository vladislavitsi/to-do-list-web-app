package by.vladislavitsi.web.control.file;

import by.vladislavitsi.web.control.app.AbstractPostOnlyServlet;
import by.vladislavitsi.web.control.exceptions.DAOException;
import by.vladislavitsi.web.util.impl.Cleaner;
import by.vladislavitsi.web.util.ICleaner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.vladislavitsi.web.util.Constants.ATTRIBUTE_FILE_ID;

public class FileDeleteServlet extends AbstractPostOnlyServlet {
    @Override
    protected void performRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int fileId = Integer.parseInt(request.getParameter(ATTRIBUTE_FILE_ID));
            ICleaner cleaner = new Cleaner();
            cleaner.cleanFile(fileId);
        } catch (DAOException e) {
            System.err.println(e.getMessage());
            System.err.println(e.getCause().toString());
        }
    }
}
