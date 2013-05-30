package net.mirantis.flights.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import net.mirantis.flights.xml.FlightsHandler;

import org.junit.Test;

/**
 * Provides different tests for parser.
 * 
 * @author Liubov Efremova
 */

public class FlightsParserTest {

    /**
     * Tests the number of flights that were got from XML document.
     */
    @Test
    public void testParse() {
        FlightsHandler parseFlight = new FlightsHandler();
        parse("/Flights.xml", parseFlight);
        assertEquals(3, parseFlight.getFlights().size());
    }

    /**
     * Tests the number of the first flight from XML document.
     */
    @Test
    public void testFirstFlightNumber() {
        FlightsHandler parseFlight = new FlightsHandler();
        parse("/Flights.xml", parseFlight);
        assertEquals("LY 612", parseFlight.getFlights().iterator().next().getNumber());
    }

    /**
     * Tests if handler throws exception if the XML document is invalid.
     */
    @Test(expected = RuntimeException.class)
    public void testException() {
        FlightsHandler parseFlight = new FlightsHandler();
        parse("/Invalid.xml", parseFlight);
    }

    private static void parse(String fileRsource, FlightsHandler parseFlight) {
        InputStream inStr = null;
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            inStr = FlightsParserTest.class.getResourceAsStream(fileRsource);
            parser.parse(inStr, parseFlight);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (inStr != null) {
                try {
                    inStr.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
