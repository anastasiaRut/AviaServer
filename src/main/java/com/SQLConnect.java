package com;
import java.sql.*;

public class SQLConnect {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String URL = "jdbc:mysql://localhost:3306/mysql"+
            "?verifyServerCertificate=false"+
            "&useUnicode=true"+
            "&characterEncoding=utf8"+
            "&useSSL=false"+
            "&requireSSL=false"+
            "&useLegacyDatetimeCode=false"+
            "&amp"+
            "&serverTimezone=UTC";
    private Connection connection = null;
    private  Statement stm=null;

    public Connection getConnection() {
        return connection;
    }

    public Statement connect(){

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            stm=connection.createStatement();
        }
        catch (SQLException ex) {
            System.out.println(ex.getErrorCode());
        }

        return stm;


    }
    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}


