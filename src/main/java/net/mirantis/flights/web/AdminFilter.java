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
 * Forbids access to the administrator's page for users who aren't admins.
 * 
 * @author Liubov Efremova
 */
public class AdminFilter implements Filter {

    /**
     * A marker that allows users to get access to the admin pages of the web application, if its value is not null.
     */
    public static final String ADMIN_MARKER = "Admin";

    private String mainPage;

    /**
     * {@inheritDoc}
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        if (session != null && session.getAttribute(ADMIN_MARKER) != null) {
            chain.doFilter(request, response);
        } else {
            res.sendRedirect(req.getContextPath() + mainPage);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destroy() {}

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(FilterConfig config) throws ServletException {
        mainPage = config.getInitParameter("mainPage");
    }

}
