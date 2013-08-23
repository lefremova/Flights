package net.mirantis.flights.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import net.mirantis.flights.dao.FlightDao;
import net.mirantis.flights.xml.FlightsHandler;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.web.HttpRequestHandler;

/**
 * Servlet for the admin page, in particular, processes flights import from XML data source.
 * 
 * @author Liubov Efremova
 */
public class XMLImportServlet implements HttpRequestHandler, BeanFactoryAware {

    private static final Logger LOG = LoggerFactory.getLogger(XMLImportServlet.class);

    private FlightDao flightDao;
    private String importPage;

    /**
     * Sets the path to the administrator page of the application
     * 
     * @param importPage the path to the administrator page
     */
    public XMLImportServlet(String importPage) {
        this.importPage = importPage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        flightDao = beanFactory.getBean(FlightDao.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleRequest(HttpServletRequest req,
            HttpServletResponse res) throws ServletException, IOException {
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
                flightDao.addFlights(flightsParser.getFlights());
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
        req.getServletContext().getRequestDispatcher(importPage).forward(req, res);
    }

}
