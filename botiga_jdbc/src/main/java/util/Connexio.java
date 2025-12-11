package util;

import java.sql.*;

public class Connexio {
    private static final String URL = "jdbc:mysql://localhost:3306/BotigaCRUD";
    private static final String USER = "Dani-06AM";
    private static final String PASSWORD = "BotigaCRUD";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
