package com.passwordmanager.database.accessors;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;

import java.sql.*;

/**
 * @author Justin Scott
 */
public class ConnectionPool {

    private static ConnectionPool pool = null;
    private static MysqlConnectionPoolDataSource msqlcp = null;
    //private static final String DB_HOSTNAME = "jdbc:mysql://secs.oakland.edu:3306/justinscott?serverTimezone=EST";
    //private static final String DB_USERNAME = "justinscott";
    //private static final String DB_PASSWORD = "WYbtchbusLHDAHe7";
    private static final String DB_HOSTNAME = "jdbc:mysql://97.70.145.108:3306/passwordmanager";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "!Q2aw3zse4";
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";

    private ConnectionPool() {

            msqlcp = new MysqlConnectionPoolDataSource();
            msqlcp.setURL(DB_HOSTNAME);
            msqlcp.setUser(DB_USERNAME);
            msqlcp.setPassword(DB_PASSWORD);

    }

    public static synchronized ConnectionPool getInstance() {
        if (pool == null) {
            pool = new ConnectionPool();
        }
        return pool;
    }

    public Connection getConnection() {
        try {
            return msqlcp.getConnection();
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public void freeConnection(Connection c) {
        try {
            c.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
