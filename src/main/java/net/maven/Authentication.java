package net.maven;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Authentication extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public Authentication() {
        super();
    }

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException,
            SQLException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Connection connection = null;
        try {

            String uname = request.getParameter("uname");
            String upass = request.getParameter("upass");

            // Регистрируем драйвер JDBC для MySQL
            Class.forName("com.mysql.jdbc.Driver");

            // Задаем реквизиты подключения к БД
            String username = "root";
            String password = "";
            String url = "jdbc:mysql://localhost/authorization";

            connection = DriverManager.getConnection(url, username, password);

            // Запрос на выбор пользователя с именем и паролем, введенными в
            // форме
            PreparedStatement pstm = connection
                    .prepareStatement("SELECT * FROM regTable"
                            + " WHERE uname=? AND upass=?");
            pstm.setString(1, uname);
            pstm.setString(2, upass);

            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                out.println("User authorised");
            } else {
                out.println("Authorisation failed");
            }

        } catch (Exception exp) {
            out.println(exp.toString());
        } finally {
            if (connection != null) {
                connection.close();
            }
            out.close();
        }
    }

}
