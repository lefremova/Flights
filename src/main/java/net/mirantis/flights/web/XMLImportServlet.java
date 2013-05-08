package net.mirantis.flights.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import net.mirantis.flights.dao.FlightDao;
import net.mirantis.flights.dao.FlightDaoFactory;
import net.mirantis.flights.model.Flights;
import net.mirantis.flights.xml.ParseFlight;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet for the admin page.
 * 
 * @author Liubov Efremova
 */
public class XMLImportServlet extends HttpServlet {

    private static FlightDao flight = FlightDaoFactory.getFlightDao();
    private static final Logger LOG = LoggerFactory
            .getLogger(XMLImportServlet.class);

    /**
     * Adds flights from xml document into database and redirects user to the main page of the web application.
     * {@inheritDoc}
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = null;
        String contextPath = req.getContextPath();
        InputStream filecontent = null;
        try {
            parser = factory.newSAXParser();
            ParseFlight flightsParser = new ParseFlight();

            List<FileItem> item = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);
            try{
                FileItem fileItem = item.get(0);
                fileItem.getInputStream();
                filecontent = item.get(0).getInputStream();
            } catch(IndexOutOfBoundsException e) {
                LOG.info("Error occurred", e);
            }

            parser.parse(filecontent, flightsParser);
            Collection<Flights> flights = flightsParser.getFlights();
            flight.addFlights(flights);
        } catch (Exception e) {
            LOG.info("Error occurred", e);
        } finally {
            if (filecontent != null) {
                filecontent.close();
            }
        }
        res.sendRedirect(contextPath + "/security/MainPageServlet");
    }

}
