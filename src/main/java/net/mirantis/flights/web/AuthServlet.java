package net.mirantis.flights.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet to authenticate user.
 * 
 * @author Liubov Efremova
 */
public class AuthServlet extends HttpServlet {

    private static final Map<String, String> LOGINS;

    static {
        LOGINS = new HashMap<String, String>(3);
        LOGINS.put("sasha", "sasha88");
        LOGINS.put("katya", "1986"); //admin
        LOGINS.put("irina", "110389");
    }

    /**
     * Examine if username and password are correct. If it is so, makes the necessary values of ADMIN_MARKER
     * and ACCESS_MARKER and redirects to the main page of the web application.
     * {@inheritDoc}
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String uname = req.getParameter("uname");
        String upass = req.getParameter("upass");
        HttpSession session = req.getSession();

        String password = LOGINS.get(uname);

        if (password != null && password.equals(upass)) {
            if (uname.equals("katya")){
                session.setAttribute(AdminFilter.ADMIN_MARKER, "allow");
            }
            session.setAttribute(AccessCheckerFilter.ACCESS_MARKER, "allow");
            res.sendRedirect("security/MainPageServlet");
        } else {
            String message = "Invalid username or password";
            req.setAttribute("message", message);
            getServletContext().getRequestDispatcher("/login.jsp").forward(req, res);
        }
    }

}
