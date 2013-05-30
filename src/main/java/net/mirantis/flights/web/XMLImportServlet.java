package net.mirantis.flights.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import net.mirantis.flights.dao.FlightDao;
import net.mirantis.flights.dao.FlightDaoFactory;
import net.mirantis.flights.xml.FlightsHandler;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet for the admin page, in particular, processes flights import from XML data source.
 * 
 * @author Liubov Efremova
 */
public class XMLImportServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(XMLImportServlet.class);
    private static final FlightDao FLIGHT_DAO = FlightDaoFactory.getFlightDao();

    private String importPage;

    /**
     * {@inheritDoc}
     */
    @Override
    public void init() throws ServletException {
        importPage = getInitParameter("importPage");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = null;
        InputStream filecontent = null;
        try {
            parser = factory.newSAXParser();
            FlightsHandler flightsParser = new FlightsHandler();

            List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);
            if (!items.isEmpty()) {
                filecontent = items.get(0).getInputStream();
                parser.parse(filecontent, flightsParser);
                FLIGHT_DAO.addFlights(flightsParser.getFlights());
                ResourceBundle rb = ResourceBundle.getBundle("i18n.Messages", req.getLocale());
                req.setAttribute("successMessage", rb.getString("success"));
            }
        } catch (Exception e) {
            LOG.info("Error occurred", e);
        } finally {
            if (filecontent != null) {
                filecontent.close();
            }
        }
        getServletContext().getRequestDispatcher(importPage).forward(req, res);
    }

}
