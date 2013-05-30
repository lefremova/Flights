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
 * Forbids access to the web application for users with incorrect username and / or password.
 * 
 * @author Liubov Efremova
 */
public class AccessCheckerFilter implements Filter {

    /**
     * A marker that allows users to get access to the main pages of the web application, if its value is not null.
     */
    public static final String ACCESS_MARKER = "AccessInfo";

    private String firstPage;

    /**
     * {@inheritDoc}
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        if (session != null && session.getAttribute(ACCESS_MARKER) != null) {
            chain.doFilter(request, response);
        } else {
            res.sendRedirect(req.getContextPath() + firstPage);
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
        firstPage = config.getInitParameter("firstPage");
    }

}
