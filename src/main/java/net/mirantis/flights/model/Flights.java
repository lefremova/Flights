package net.mirantis.flights.model;

/**
 * This class is used to keep information about flights that we get from the database.
 * 
 * @author Liubov Efremova
 */
public class Flights {

    private int id;
    private String number;
    private int ticketsCount;
    private String date;

    public Flights() {}

    /**
     * Creates a new instance with given values of parameters.
     * 
     * @param id
     * @param number Flight number
     * @param ticketsCount The number of available tickets
     * @param date Departure date
     */
    public Flights(int id, String number, int ticketsCount, String date) {
        this.id = id;
        this.number = number;
        this.ticketsCount = ticketsCount;
        this.date = date;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the number
     */
    public String getNumber() {
        return number;
    }

    /**
     * @param number the number to set
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * @return the ticketsCount
     */
    public int getTicketsCount() {
        return ticketsCount;
    }

    /**
     * @param ticketsCount the ticketsCount to set
     */
    public void setTicketsCount(int ticketsCount) {
        this.ticketsCount = ticketsCount;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

}
