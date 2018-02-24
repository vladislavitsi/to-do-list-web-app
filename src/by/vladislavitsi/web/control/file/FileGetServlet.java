package by.vladislavitsi.web.control.file;

import by.vladislavitsi.web.control.ApplicationContext;
import by.vladislavitsi.web.control.app.AbstractPostOnlyServlet;
import by.vladislavitsi.web.control.exceptions.DAOException;
import by.vladislavitsi.web.model.file.File;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static by.vladislavitsi.web.util.Constants.ATTRIBUTE_FILE_ID;

public class FileGetServlet extends AbstractPostOnlyServlet {

    private static final String FILE_RESPONSE_CONTENT_TYPE = ApplicationContext.getStringResource("file.response.content.type");
    private static final String FILE_RESPONSE_HEADER = ApplicationContext.getStringResource("file.response.header");
    private static final String FILE_RESPONSE_HEADER_CONTENT = ApplicationContext.getStringResource("file.response.header.content");
    private static final String QUOTE = "\"";

    @Override
    protected void performRequest(HttpServletRequest request,
                                  HttpServletResponse response) throws ServletException, IOException {
        int fileId = Integer.parseInt(request.getParameter(ATTRIBUTE_FILE_ID));
        try {
            File file = ApplicationContext.getFileDAOImpl().getFile(fileId);
            if(file != null){
                java.io.File toBeSent = new java.io.File(file.getPath());
                response.setContentType(FILE_RESPONSE_CONTENT_TYPE);
                response.setHeader(FILE_RESPONSE_HEADER, FILE_RESPONSE_HEADER_CONTENT+ QUOTE+ file.getName() + QUOTE);
                response.setContentLength(Long.valueOf(toBeSent.length()).intValue());
                try (OutputStream out = response.getOutputStream()) {
                    Path path = toBeSent.toPath();
                    Files.copy(path, out);
                }
            }
        } catch (DAOException e) {
            System.err.println(e.getMessage());
            System.err.println(e.getCause().toString());
        }
    }
}
