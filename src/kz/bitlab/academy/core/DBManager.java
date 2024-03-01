package kz.bitlab.academy.core;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBManager {

    protected static Connection connection;

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/final-project";
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "postgres";

    static {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
