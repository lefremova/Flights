package net.mirantis.flights.web;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Invalidates user HTTP session and redirects to the login page.
 * 
 * @author Liubov Efremova
 */
public class LogOutServlet extends HttpServlet {

    private String firstPage;

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        firstPage = config.getInitParameter("firstPage");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect(firstPage);
    }

}
