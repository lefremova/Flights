package net.mirantis.flights.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Implementation of Filter which is used to forbid access to the administrator's page for other users.
 * 
 * @author Liubov Efremova
 */

public class AdminFilter implements Filter {

    public static final String ADMIN_MARKER = "Admin";

    private String mainPage;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        String contextPath = req.getContextPath();

        if (session != null && session.getAttribute(ADMIN_MARKER) != null) {
            chain.doFilter(request, response);
        } else {
            res.sendRedirect(contextPath + mainPage);
        }
    }

    @Override
    public void destroy() {}

    @Override
    public void init(FilterConfig config) throws ServletException {
        mainPage = config.getInitParameter("mainPage");
    }

}
