package net.mirantis.flights.dao;

import java.util.Collection;

import net.mirantis.flights.model.Flight;


/**
 * Provides methods to work with {@link Flight}s.
 * 
 * @author Liubov Efremova
 */
public interface FlightDao {

    /**
     * Decrements the number of available tickets for specified flights.
     * 
     * @param flights the array of flights IDs to update
     */
    void updateFlights(String[] flights);

    /**
     * Returns a collection of <code>Flight</code>s. The flight is returned if its number of available tickets
     * is positive.
     * 
     * @return the collection of <code>Flight</code>s that are available for reserving
     */
    Collection<Flight> selectAllFlights();

    /**
     * Stores specified flights into a data source.
     * 
     * @param flights the collection of flights that will be stored into a data source
     */
    void addFlights(Collection<Flight> flights);

}
