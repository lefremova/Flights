package net.mirantis.flights.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import net.mirantis.flights.model.Flight;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of the <code>FlightDao</code> interface for which the storage will be a database.
 * 
 * @author Liubov Efremova
 */
class FlightDaoImpl implements FlightDao {

    private static final Logger LOG = LoggerFactory.getLogger(FlightDaoImpl.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateFlights(String[] flights) {
        final String sql = "UPDATE flights SET tickets_count = tickets_count - 1 WHERE id = ?";
        Connection con = null;
        try {
            con = getConnection();
            con.setAutoCommit(false);
            PreparedStatement s = con.prepareStatement(sql);
            for (String row : flights) {
                s.setString(1, row);
                s.addBatch();
            }
            s.executeBatch();
            con.commit();
        } catch (Exception e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException e1) {
                    LOG.info("Error occurred", e1);
                }
            }
            throw new RuntimeException(e);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    LOG.info("Error occurred", e);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Flight> selectAllFlights() {
        final String sql = "SELECT * FROM flights WHERE tickets_count > 0";
        Connection con = null;
        try {
            con = getConnection();
            Statement select = con.createStatement();
            ResultSet rs = select.executeQuery(sql);
            Collection<Flight> flights = new ArrayList<Flight>();
            while (rs.next()) {
                Flight fl = new Flight(rs.getInt("id"), rs.getString("number"),
                                       rs.getInt("tickets_count"), rs.getTimestamp("date"));
                flights.add(fl);
            }
            return flights;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    LOG.info("Error occurred", e);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addFlights(Collection<Flight> flights) {
        final String sql = "INSERT INTO flights (number, tickets_count, date) VALUES (?, ?, ?)";
        Connection con = null;
        try {
            con = getConnection();
            con.setAutoCommit(false);
            PreparedStatement s = con.prepareStatement(sql);
            for (Flight f : flights) {
                s.setString(1, f.getNumber());
                s.setInt(2, f.getTicketsCount());
                s.setTimestamp(3, new Timestamp(f.getDate().getTime()));
                s.addBatch();
            }
            s.executeBatch();
            con.commit();
        } catch (Exception e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException e1) {
                    LOG.info("Error occurred", e1);
                }
            }
            throw new RuntimeException(e);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    LOG.info("Error occurred", e);
                }
            }
        }
    }

    private static Connection getConnection() {
        try {
            InitialContext initCtx = new InitialContext();
            DataSource dataSource = (DataSource) initCtx.lookup("java:comp/env/jdbc/flightschedule");
            return dataSource.getConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
