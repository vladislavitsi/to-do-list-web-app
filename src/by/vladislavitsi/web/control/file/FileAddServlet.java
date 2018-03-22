package by.vladislavitsi.web.control.file;

import by.vladislavitsi.web.control.app.ApplicationContext;
import by.vladislavitsi.web.control.app.AbstractPostOnlyServlet;
import by.vladislavitsi.web.control.exceptions.DAOException;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Date;

import static by.vladislavitsi.web.util.Constants.ATTRIBUTE_FILE;
import static by.vladislavitsi.web.util.Constants.ATTRIBUTE_FILE_ID;

@MultipartConfig
public class FileAddServlet extends AbstractPostOnlyServlet {

    private static final String FILE_PATH = ApplicationContext.getStringResource("file.filepath");

    @Override
    protected void performRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int taskId = Integer.parseInt(request.getParameter(ATTRIBUTE_FILE_ID));
            Part file = request.getPart(ATTRIBUTE_FILE);
            String filename = file.getSubmittedFileName();
            String hash = DigestUtils.sha256Hex(filename + new Date().toString());
            String path = FILE_PATH + hash;
            file.write(path);
            ApplicationContext.getFileDAOImpl().addFile(new by.vladislavitsi.web.model.file.File(taskId, filename, path));
        } catch (DAOException e) {
            System.err.println(e.getMessage());
            System.err.println(e.getCause().toString());
        }
    }
}