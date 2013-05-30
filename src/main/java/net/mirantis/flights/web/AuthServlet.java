package net.mirantis.flights.web;

import java.io.IOException;
import java.util.Hashtable;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet for LDAP authentication. Examines if user's name and password are correct. If it is so,
 * sets {@link AdminFilter#ADMIN_MARKER} and {@link AccessCheckerFilter#ACCESS_MARKER} into session with some values
 * and redirects to the main page of the web application.
 */
public class AuthServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(AuthServlet.class);

    private String firstPage;
    private String mainPage;
    private String initConFact;
    private String providerURL;
    private String securityPrincipal;

    /**
     * {@inheritDoc}
     */
    @Override
    public void init() throws ServletException {
        firstPage = getInitParameter("firstPage");
        mainPage = getInitParameter("mainPage");
        initConFact = getInitParameter("initConFact");
        providerURL = getInitParameter("providerURL");
        securityPrincipal = getInitParameter("securityPrincipal");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String uname = req.getParameter("uname");
        String upass = req.getParameter("upass");
        HttpSession session = req.getSession();

        Hashtable<String, String> env = new Hashtable<String, String>(4);
        env.put(Context.INITIAL_CONTEXT_FACTORY, initConFact);
        env.put(Context.PROVIDER_URL, providerURL);
        env.put(Context.SECURITY_PRINCIPAL, String.format(securityPrincipal, uname));
        env.put(Context.SECURITY_CREDENTIALS, upass);
        DirContext ctx = null;
        try {
            ctx = new InitialDirContext(env);
            if (uname.equals("lefremova")) {
                session.setAttribute(AdminFilter.ADMIN_MARKER, "allow");
            }
            session.setAttribute(AccessCheckerFilter.ACCESS_MARKER, "allow");
            res.sendRedirect(mainPage);
         } catch (NamingException e) {
             LOG.info("NamingException", e);
             ResourceBundle rb = ResourceBundle.getBundle("i18n.Messages", req.getLocale());
             req.setAttribute("message", rb.getString("inval"));
             getServletContext().getRequestDispatcher(firstPage).forward(req, res);
        } finally {
            if (ctx != null) {
                try {
                    ctx.close();
                } catch (NamingException e) {
                    LOG.info("NamingException", e);
                }
            }
        }
    }

}
