package by.vladislavitsi.web.control.exceptions;

public class WrongAuthenticationException extends DAOException {

    public WrongAuthenticationException(String message) {
        super(message);
    }
}
