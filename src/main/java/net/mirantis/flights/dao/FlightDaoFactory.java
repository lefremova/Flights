package net.mirantis.flights.dao;

/**
 * A factory for {@link FlightDao} implementations.
 * 
 * @author Liubov Efremova
 */
public final class FlightDaoFactory {

    private static volatile FlightDao instance;

    /**
     * Returns a configured implementation of the <code>FlightDao</code> interface.
     * 
     * @return the <code>FlightDao</code> instance
     */
    public static FlightDao getFlightDao() {
        if (instance == null) {
            synchronized (FlightDaoFactory.class) {
                if (instance == null) {
                    instance = new FlightDaoImpl();
                }
            }
        }
        return instance;
    }

    /**
     * Prevents instantiations of <code>FlightDaoFactory</code> class.
     */
    private FlightDaoFactory() {}

}
