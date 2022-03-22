package com.example.ziibdprojekt;

import java.sql.*;

public class ConnectionManager {
    private final String JDBCURL = "jdbc:oracle:thin:@155.158.112.45:1521:oltpstud";
    private final String USERNAME = "ziibd19";
    private final String PASSWORD = "haslo2015";
    private Connection connection = null;


    public ConnectionManager(){
        initDatabaseConnection();
    }

    private void initDatabaseConnection(){
        while(connection == null){
            try{
                System.out.println("[ConnectionManager] Trwa próba połączenia z bazą danych...");
                Class.forName("oracle.jdbc.driver.OracleDriver");
                connection = DriverManager.getConnection(JDBCURL,USERNAME, PASSWORD);
                if(connection != null){
                    System.out.println("[Database] Pomyślnie połączono z bazą danych!");
                    break;
                }
            }catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                System.err.println("DATABASE CONNECTION ERROR!" + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
