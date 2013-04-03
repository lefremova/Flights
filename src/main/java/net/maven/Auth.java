package net.maven;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Auth extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Map<String, String> logins;

    public Auth() {
        logins = new HashMap<String, String>(3);
        logins.put("sasha", "sasha88");
        logins.put("katya", "1986");
        logins.put("irina", "110389");
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String uname = req.getParameter("uname");
        String upass = req.getParameter("upass");
        HttpSession session = req.getSession();

        String password = logins.get(uname);
        if (password != null && password.equals(upass)) {
            session.setAttribute(AccessCheckerFilter.ACCESS_MARKER, "allow");
            res.sendRedirect("security/Main");
        } else {
            String message = "Invalid username or password";
            req.setAttribute("message", message);
            getServletContext().getRequestDispatcher("/login.jsp").forward(req, res);
        }

    }

}