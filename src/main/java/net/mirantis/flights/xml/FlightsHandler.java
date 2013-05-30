package net.mirantis.flights.xml;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import net.mirantis.flights.model.Flight;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Parser for XML documents that hold information about flights.
 * 
 * @author Liubov Efremova
 */
public class FlightsHandler extends DefaultHandler {

    private static final Logger LOG = LoggerFactory.getLogger(FlightsHandler.class);
    private static final DateFormat DATE_PARSER;

    private List<Flight> flights;
    private Flight flight;
    private String thisElement;

    static {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        dateFormat.setLenient(false);
        DATE_PARSER = dateFormat;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startDocument() throws SAXException {
        LOG.info("Start parse XML...");
        flights = new ArrayList<Flight>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        if ("flight".equals(qName)) {
            flight = new Flight();
        }
        thisElement = qName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        if ("flight".equals(qName)) {
            flights.add(flight);
        }
        thisElement = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if ("flight_number".equals(thisElement)) {
            flight.setNumber(new String(ch, start, length));
        } else if ("tickets_count".equals(thisElement)) {
            flight.setTicketsCount(Integer.parseInt(new String(ch, start, length)));
        } else if ("date".equals(thisElement)) {
            try {
                Date date = DATE_PARSER.parse(new String(ch, start, length));
                flight.setDate(date);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void endDocument() {
        LOG.info("Stop parse XML...");
    }

    /**
     * Returns a collection of parsed {@link Flight}s.
     * 
     * @return a collection of parsed {@link Flight}s
     */
    public Collection<Flight> getFlights() {
        return flights;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warning(SAXParseException e) throws SAXException {
        throw e;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(SAXParseException e) throws SAXException {
        throw e;
    }

}
