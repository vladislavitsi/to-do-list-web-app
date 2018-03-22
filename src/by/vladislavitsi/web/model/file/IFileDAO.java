package by.vladislavitsi.web.model.file;

import by.vladislavitsi.web.control.exceptions.DAOException;

import java.util.List;

public interface IFileDAO {
    File getFile(int fileId) throws DAOException;
    void addFile(File file) throws DAOException;
    void deleteFile(int fileId) throws DAOException;
}
