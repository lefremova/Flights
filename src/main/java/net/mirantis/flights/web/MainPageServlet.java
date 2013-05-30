package net.mirantis.flights.web;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.mirantis.flights.dao.FlightDao;
import net.mirantis.flights.dao.FlightDaoFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides data for the main page that holds the table of flights available for reserving
 * and processes user's actions.
 * 
 * @author Liubov Efremova
 */
public class MainPageServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(MainPageServlet.class);
    private static final FlightDao FLIGHT_DAO = FlightDaoFactory.getFlightDao();

    private String mainJSP;

    /**
     * {@inheritDoc}
     */
    @Override
    public void init() throws ServletException {
        mainJSP = getInitParameter("mainJSP");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            showTable(request);
            getServletContext().getRequestDispatcher(mainJSP).forward(request, response);
        } catch (Exception ex) {
            LOG.info("Exception", ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        String[] selectedFlights = request.getParameterValues("chBox");

        try {
            if (selectedFlights != null) {
                FLIGHT_DAO.updateFlights(selectedFlights);
                ResourceBundle rb = ResourceBundle.getBundle("i18n.Messages", request.getLocale());
                request.setAttribute("successMessage", rb.getString("success"));
            }
            showTable(request);
            getServletContext().getRequestDispatcher(mainJSP).forward(request, response);
        } catch (Exception ex) {
            LOG.info("Exception", ex);
        }
    }

    private static void showTable(HttpServletRequest request) {
        request.setAttribute("data", FLIGHT_DAO.selectAllFlights());
    }

}
