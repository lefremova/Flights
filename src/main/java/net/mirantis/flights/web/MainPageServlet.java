package net.mirantis.flights.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.mirantis.flights.dao.FlightDao;
import net.mirantis.flights.dao.FlightDaoFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet of the main page that holds the table of flights available for reserving.
 * 
 * @author Liubov Efremova
 */
public class MainPageServlet extends HttpServlet {

    private static Logger log = LoggerFactory.getLogger(MainPageServlet.class);
    private static FlightDao flight = FlightDaoFactory.getFlightDao();

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            showTable(request);
            getServletContext().getRequestDispatcher("/security/main.jsp").forward(request, response);
        } catch (Exception ex) {
            log.info("Exception", ex);
        }
    }

    /**
     * Updates the information about the number of available tickets of checked flights.
     * {@inheritDoc}
     */
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        String[] chBox = request.getParameterValues("chBox");

        try {
            if (chBox != null) {
                flight.updateFlights(chBox);
                String message = "The operation completed successfully";
                request.setAttribute("successMessage", message);
            }
            showTable(request);
            getServletContext().getRequestDispatcher("/security/main.jsp").forward(request, response);
        } catch (Exception ex) {
            log.info("Exception", ex);
        }

    }

    private static void showTable (HttpServletRequest request) throws Exception {
        request.setAttribute("data", flight.selectAllFlights());
    }

}
