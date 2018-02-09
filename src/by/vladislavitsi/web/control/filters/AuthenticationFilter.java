package by.vladislavitsi.web.control.filters;

import by.vladislavitsi.web.ApplicationContext;
import by.vladislavitsi.web.control.exceptions.DAOException;
import by.vladislavitsi.web.control.exceptions.WrongAuthenticationException;
import by.vladislavitsi.web.model.user.User;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static by.vladislavitsi.web.Constants.*;

public class AuthenticationFilter implements Filter {

    private static final String EXCEPTION_NO_COOKIES = ApplicationContext.getStringResource("exception.noCookie");

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        try {
            HttpSession session = request.getSession(true);
            User user = (User) session.getAttribute(ATTRIBUTE_USER);
            if (user == null) {
                user = getUserFromCookie(request.getCookies());
                session.setAttribute(ATTRIBUTE_USER, user);
            }
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (DAOException e) {
            if(!(e instanceof WrongAuthenticationException)){
                System.err.println(e.getMessage());
            }
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            request.getRequestDispatcher(PAGE_LOGIN).forward(request, response);
        }
    }

    private User getUserFromCookie(Cookie[] cookies) throws DAOException{
        if (cookies != null) {
            String loginCookie = null;
            String idCookie = null;
            String tokenCookie = null;
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(USER_ID)) {
                    idCookie = cookie.getValue();
                }
                if (cookie.getName().equals(USER_LOGIN)){
                    loginCookie = cookie.getValue();
                }
                if (cookie.getName().equals(USER_TOKEN)) {
                    tokenCookie = cookie.getValue();
                }
            }
            if (!(loginCookie == null || idCookie == null || tokenCookie == null
                    || !ApplicationContext.getUserDAOImpl().validateUser(idCookie, loginCookie, tokenCookie))) {
                return new User(idCookie, loginCookie, tokenCookie);
            }
        }
        throw new WrongAuthenticationException(EXCEPTION_NO_COOKIES);
    }

}
