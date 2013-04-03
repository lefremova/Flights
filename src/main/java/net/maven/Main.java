package net.maven;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class Main extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Connection con = null;
        try {
            InitialContext initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            DataSource dataSource = (DataSource) envCtx.lookup("jdbc/flightschedule");
            con = dataSource.getConnection();

            PreparedStatement select_date = con.prepareStatement("SELECT * FROM test.flights");

            ResultSet rs = select_date.executeQuery();
            Collection<Flight> dataList = new ArrayList<Flight>();
            while (rs.next()) {
                Flight fl = new Flight(rs.getString("flight_number"), rs.getInt("tickets_count"), rs.getString("date"));
                dataList.add(fl);
            }
            request.setAttribute("data", dataList);
            getServletContext().getRequestDispatcher("/security/main.jsp").forward(request, response); 

        } catch (Exception exp) {
            exp.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

}