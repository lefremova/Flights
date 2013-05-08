package net.mirantis.flights.web;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Implementation of Filter which is used to forbid access to the web application for users with incorrect username or
 * password.
 * 
 * @author Liubov Efremova
 */

public class AccessCheckerFilter implements Filter {

    public static final String ACCESS_MARKER = "AccessInfo";

    private String firstPage;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        String contextPath = req.getContextPath();

        if (session != null && session.getAttribute(ACCESS_MARKER) != null) {
            chain.doFilter(request, response);
        } else {
            res.sendRedirect(contextPath + firstPage);
        }
    }

    @Override
    public void destroy() {}

    @Override
    public void init(FilterConfig config) throws ServletException {
        firstPage = config.getInitParameter("firstPage");
    }

}
