package system.filter;

import system.model.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebFilter(filterName = "UserFilter", urlPatterns = {"/edit", "/info"})
public class UserFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        User authUser = (User) req.getSession().getAttribute("authUser");

        if (authUser == null) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        if (authUser.getRole().equals("admin")) {
            chain.doFilter(request, response);
            return;
        }
        if (req.getMethod().equalsIgnoreCase("GET")) {
            String idParam = req.getParameter("id");
            if (!idParam.equals(authUser.getId().toString())) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
