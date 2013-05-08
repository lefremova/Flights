package net.mirantis.flights.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import net.mirantis.flights.model.Flights;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of the <tt>FlightDao</tt> interface.
 * 
 * @author Liubov Efremova
 */

class FlightDaoImpl implements FlightDao {

    private static final Logger LOG = LoggerFactory.getLogger(FlightDaoImpl.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateFlights(String[] rowNumbers) {
        final String sql = "UPDATE flights SET ticketsCount = ticketsCount - 1 WHERE id = ?";
        Connection con = null;
        try {
            con = getConnection();
            con.setAutoCommit(false);
            PreparedStatement s = con.prepareStatement(sql);
            for (String row : rowNumbers) {
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
    public Collection<Flights> selectAllFlights() {
        final String sql = "SELECT * FROM flights WHERE ticketsCount <> 0";
        Collection<Flights> dataList = null;
        Connection con = null;
        try {
            con = getConnection();
            Statement select = con.createStatement();
            ResultSet rs = select.executeQuery(sql);
            dataList = new ArrayList<Flights>();
            while (rs.next()) {
                Flights fl = new Flights(rs.getInt("id"), rs.getString("number"),
                                       rs.getInt("ticketsCount"), rs.getString("date"));
                dataList.add(fl);
            }
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
        return dataList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addFlights(Collection<Flights> flights) {
        final String sql = "INSERT INTO flights (number, ticketsCount, date) VALUES (?, ?, ?)";
        Connection con = null;
        try {
            con = getConnection();
            con.setAutoCommit(false);
            PreparedStatement s = con.prepareStatement(sql);
            for (Flights f : flights) {
                s.setString(1, f.getNumber());
                s.setInt(2, f.getTicketsCount());
                s.setString(3, f.getDate());
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
