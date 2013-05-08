package net.mirantis.flights.dao;

import java.util.Collection;

import net.mirantis.flights.model.Flights;

/**
 * Provides methods for the work with the information about flights that is kept in the database.
 * 
 * @author Liubov Efremova
 */
public interface FlightDao {

    /**
     * Decrements the number of available tickets at those flights which were reserved by the user.
     *
     */
    void updateFlights(String[] rowNumbers);

    /**
     * Returns the Collection of Flights. The flight includes into Collection if its number of available tickets 
     * is not equal to zero.
     *
     * @return the Collection of Flights which are available for reserving.
     */
    Collection<Flights> selectAllFlights();

    /**
     * Inserts flights given in the Collection into database.
     * 
     */
    void addFlights(Collection<Flights> flights);

}
