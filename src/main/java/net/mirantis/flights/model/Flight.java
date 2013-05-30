package net.mirantis.flights.model;

import java.io.Serializable;
import java.util.Date;

/**
 * This class represents a flight.
 * 
 * @author Liubov Efremova
 */
public class Flight implements Serializable {

    private int id;
    private String number;
    private int ticketsCount;
    private Date date;

    /**
     * Creates a new instance of <code>Flight</code>.
     */
    public Flight() {}

    /**
     * Creates a new instance of <code>Flight</code>.
     * 
     * @param id the flight id
     * @param number the flight number
     * @param ticketsCount the number of available tickets
     * @param date the departure date
     */
    public Flight(int id, String number, int ticketsCount, Date date) {
        this.id = id;
        this.number = number;
        this.ticketsCount = ticketsCount;
        this.date = date;
    }

    /**
     * Returns the id of flight. 
     * 
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id of flight.
     * 
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the flight number.
     * 
     * @return the number
     */
    public String getNumber() {
        return number;
    }

    /**
     * Sets the flight number.
     * 
     * @param number the number to set
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * Returns the number of available tickets of the flight.
     * 
     * @return the ticketsCount
     */
    public int getTicketsCount() {
        return ticketsCount;
    }

    /**
     * Sets the number of available tickets of the flight.
     * 
     * @param ticketsCount the ticketsCount to set
     */
    public void setTicketsCount(int ticketsCount) {
        this.ticketsCount = ticketsCount;
    }

    /**
     * Returns the date of the flight.
     * 
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the date of the flight.
     * 
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

}
