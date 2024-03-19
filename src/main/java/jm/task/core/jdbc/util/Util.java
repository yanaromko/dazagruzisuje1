package jm.task.core.jdbc.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Util {
    private static Connection connection;
    private static final String URL = "jdbc:mysql://localhost:3306/modulfirsttasksecond";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static Util instatce;

    private Util() {
        try {
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Util gitInstatce() {
        if (instatce == null) {
            instatce = new Util();
        }
        return instatce;
    }

    public static Connection getConnection() {
        try {
           if(connection == null || connection.isClosed()) {
               connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
           }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

}
