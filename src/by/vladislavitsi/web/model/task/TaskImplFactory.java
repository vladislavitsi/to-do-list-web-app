package by.vladislavitsi.web.model.task;

import by.vladislavitsi.web.model.task.impl.DBTaskDAO;

public enum TaskImplFactory {
    DB {
        @Override
        ITaskDAO getImpl() {
            return new DBTaskDAO();
        }
    };

    abstract ITaskDAO getImpl();

    public static ITaskDAO getImplFromFactory(String impl){
        return valueOf(impl.toUpperCase()).getImpl();
    }
}