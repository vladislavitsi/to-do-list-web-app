package by.vladislavitsi.web.model.user;

import by.vladislavitsi.web.model.user.impl.DBUserDAO;
import by.vladislavitsi.web.model.user.impl.RamUserDAO;

public enum UserImplFactory {
    RAM {
        @Override
        UserDAO getImpl() {
            return new RamUserDAO();
        }
    },
    DB {
        @Override
        UserDAO getImpl() {
            return new DBUserDAO();
        }
    };

    abstract UserDAO getImpl();

    public static UserDAO getImplFromFactory(String impl){
        return valueOf(impl.toUpperCase()).getImpl();
    }
}