package by.vladislavitsi.web.control.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.vladislavitsi.web.Constants.*;

public class JspFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        // if request contain header.jsp or main.jsp or something else when logged in
        if(request.getRequestURI().equals(PAGE_ROOT+PAGE_HEADER)
            || ( request.getRequestURI().equals(PAGE_ROOT + PAGE_MAIN)
                ^ (request.getSession().getAttribute(ATTRIBUTE_USER) != null) )) {
            ((HttpServletResponse)servletResponse).sendRedirect(PAGE_ROOT);
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
