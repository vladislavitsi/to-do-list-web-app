package by.vladislavitsi.web.model.user.impl;

import by.vladislavitsi.web.control.exceptions.DAOException;
import by.vladislavitsi.web.control.exceptions.ProfileExistsException;
import by.vladislavitsi.web.control.exceptions.WrongAuthenticationException;
import by.vladislavitsi.web.model.user.UserDAO;
import by.vladislavitsi.web.model.user.User;

import java.util.HashMap;
import java.util.Map;

import static by.vladislavitsi.web.Constants.EXCEPTION_INVALID_LOGIN;
import static by.vladislavitsi.web.Constants.EXCEPTION_USER_EXIST;

public class RamUserDAO implements UserDAO {

    private static int userIdCounter = 1;

    private static final Map<String, UserD> users = new HashMap<>();

    @Override
    public User getUser(String login, String sha256Password) throws DAOException {
        UserD userD = users.get(login);
        if(userD != null && sha256Password.equals(userD.getHashPassword())){
            return new User(userD.getId(), userD.getLogin(), userD.getToken());
        }
        throw new WrongAuthenticationException(EXCEPTION_INVALID_LOGIN);
    }

    @Override
    public void addUser(String login, String sha256Password) throws DAOException {
        synchronized (users){
            if(users.get(login)!=null){
                throw new ProfileExistsException(EXCEPTION_USER_EXIST);
            }
            String id = String.valueOf(userIdCounter);
            userIdCounter++;
            String token = org.apache.commons.codec.digest.DigestUtils.sha256Hex(login+Math.random()+id);
            UserD userD = new UserD(id, login, sha256Password, token);
            users.put(login, userD);
        }
    }

    @Override
    public boolean validateUser(String id, String login, String token) {
        UserD user = users.get(login);
        return user != null && token.equals(user.getToken()) && id.equals(user.getId());
    }

    private static class UserD{
        private final String id;
        private final String login;
        private final String hashPassword;
        private final String token;

        private UserD(String id, String login, String hashPassword, String token) {
            this.id = id;
            this.login = login;
            this.hashPassword = hashPassword;
            this.token = token;
        }

        String getId() {
            return id;
        }

        String getLogin() {
            return login;
        }

        String getHashPassword() {
            return hashPassword;
        }

        String getToken() {
            return token;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof UserD)) return false;

            UserD userD = (UserD) o;

            return login.equals(userD.login) && hashPassword.equals(userD.hashPassword);
        }

        @Override
        public int hashCode() {
            int result = login.hashCode();
            result = 31 * result + hashPassword.hashCode();
            return result;
        }

        @Override
        public String toString() {
            return "UserD{" +
                    "id=" + id +
                    ", login='" + login + '\'' +
                    ", hashPassword='" + hashPassword + '\'' +
                    ", token='" + token + '\'' +
                    '}';
        }
    }
}
