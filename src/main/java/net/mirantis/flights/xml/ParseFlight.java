package net.mirantis.flights.xml;

import java.util.ArrayList;

import java.util.Collection;

import net.mirantis.flights.model.Flights;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.*;

/**
 * Parser for xml document that holds information about flights.
 * 
 * @author Liubov Efremova
 */
public class ParseFlight extends DefaultHandler {

    private Collection <Flights> flights = new ArrayList<Flights>();
    private static final Logger LOG = LoggerFactory.getLogger(ParseFlight.class);
    private String thisElement = "";
    private int id;
    private String flight_number;
    private int tickets_count;
    private String date;

    /**
     * {@inheritDoc}
     */
    @Override
    public void startDocument() throws SAXException {
        System.out.println("Start parse XML...");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        thisElement = qName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        thisElement = "";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if ("id".equals(thisElement)) {
            id = Integer.parseInt(new String(ch, start, length));
        } else if (thisElement.equals("flight_number")) {
            flight_number = new String(ch, start, length);
        }
        if (thisElement.equals("tickets_count")) {
            tickets_count = new Integer(new String(ch, start, length));
        }
        if (thisElement.equals("date")) {
            date = new String(ch, start, length);
            flights.add(new Flights(id, flight_number, tickets_count, date));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void endDocument() {
        System.out.println("Stop parse XML...");
    }

    public Collection<Flights> getFlights() {
        return flights;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warning(SAXParseException e) throws SAXException {
        LOG.info("Warning occurred", e);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(SAXParseException e) throws SAXException {
        LOG.info("Error occurred", e);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fatalError(SAXParseException e) throws SAXException {
        LOG.info("Fatal error occurred", e);
        throw e;
    }

}
