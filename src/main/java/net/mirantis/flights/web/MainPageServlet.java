package net.mirantis.flights.web;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.mirantis.flights.dao.FlightDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.HttpRequestHandler;

/**
 * Provides data for the main page that holds the table of flights available for reserving
 * and processes user's actions.
 * 
 * @author Liubov Efremova
 */
public class MainPageServlet implements HttpRequestHandler {

    private static final Logger LOG = LoggerFactory.getLogger(MainPageServlet.class);

    @Autowired
    private FlightDao flightDao;
    private String mainJSP;

    /**
     * Sets the path to the main page of the application
     * 
     * @param mainJSP  the path to the main page
     */
    public void setMainJSP(String mainJSP) {
        this.mainJSP = mainJSP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = request.getServletContext();

        if ("GET".equalsIgnoreCase(request.getMethod())) {
            try {
                showTable(request);
                context.getRequestDispatcher(mainJSP).forward(request, response);
            } catch (Exception ex) {
                LOG.info("Exception", ex);
            }
        } else {
            try {
                String[] selectedFlights = request.getParameterValues("chBox");
                if (selectedFlights != null) {
                    flightDao.updateFlights(selectedFlights);
                    ResourceBundle rb = ResourceBundle.getBundle("i18n.Messages", request.getLocale());
                    request.setAttribute("successMessage", rb.getString("success"));
                }
                showTable(request);
                context.getRequestDispatcher(mainJSP).forward(request, response);
            } catch (Exception ex) {
                LOG.info("Exception", ex);
            }
        }
    }

    private void showTable(HttpServletRequest request) {
        request.setAttribute("data", flightDao.selectAllFlights());
    }

}
