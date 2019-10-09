package system.filter;

import system.model.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * If the authenticated user is an administrator, passes it on.
 * If the authenticated user is a user, passes it on if only the id parameter from the request matches the authenticated user ID.
 */
//@WebFilter(filterName = "UserFilter", urlPatterns = {"/edit", "/info"})
public class UserFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        User sessionUser = (User) req.getSession().getAttribute("sessionUser");

        if (sessionUser == null) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        if (sessionUser.getRole().equals("admin")) {
            chain.doFilter(request, response);
            return;
        }
        if (req.getMethod().equalsIgnoreCase("GET")) {
            String idParam = req.getParameter("id");
            if (!idParam.equals(sessionUser.getId().toString())) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
