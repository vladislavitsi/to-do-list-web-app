package by.vladislavitsi.web.model.user;

import by.vladislavitsi.web.model.user.impl.DBUserDAO;
import by.vladislavitsi.web.model.user.impl.RamUserDAO;

public enum UserImplFactory {
    RAM {
        @Override
        IUserDAO getImpl() {
            return new RamUserDAO();
        }
    },
    DB {
        @Override
        IUserDAO getImpl() {
            return new DBUserDAO();
        }
    };

    abstract IUserDAO getImpl();

    public static IUserDAO getImplFromFactory(String impl){
        return valueOf(impl.toUpperCase()).getImpl();
    }
}