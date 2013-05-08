package net.mirantis.flights.dao;

public final class FlightDaoFactory {

    private static volatile FlightDao instance;

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

    private FlightDaoFactory() {}

}
