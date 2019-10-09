package system.filter;

import system.model.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Checks that authenticated user has administrator rights to access /list or /delete
 */
//@WebFilter(filterName = "AdminFilter", urlPatterns = {"/list", "/delete"})
public class AdminFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        User authUser = (User) req.getSession().getAttribute("sessionUser");

        if (authUser == null || !authUser.getRole().equals("admin")) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        chain.doFilter(request, response);
    }
}
