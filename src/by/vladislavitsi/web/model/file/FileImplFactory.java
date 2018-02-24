package by.vladislavitsi.web.model.file;

import by.vladislavitsi.web.model.file.impl.DBFileDAO;

public enum FileImplFactory {
    DB {
        @Override
        IFileDAO getImpl() {
            return new DBFileDAO();
        }
    };

    abstract IFileDAO getImpl();

    public static IFileDAO getImplFromFactory(String impl){
        return valueOf(impl.toUpperCase()).getImpl();
    }
}