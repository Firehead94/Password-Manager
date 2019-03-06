package com.passwordmanager.database;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;
import com.mysql.cj.jdbc.MysqlPooledConnection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.PooledConnection;
import java.sql.*;
import java.util.Properties;


public class ConnectionPool {

    private static ConnectionPool pool = null;
    private static MysqlConnectionPoolDataSource msqlcp = null;
    private static final String DB_HOSTNAME = "jdbc:mysql://secs.oakland.edu:3306/justinscott?serverTimezone=EST";
    private static final String DB_USERNAME = "justinscott";
    private static final String DB_PASSWORD = "WYbtchbusLHDAHe7";
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
/*
    private ConnectionPool() {

        try {
            Properties prop = new Properties();
            prop.put(Context.PROVIDER_URL, "java://localhost/justin");
            prop.put(Context.INITIAL_CONTEXT_FACTORY, "com.mysql.cj.jdbc.Driver");
            InitialContext ic = new InitialContext(prop);
            dataSource = (DataSource) ic.lookup(DB_HOSTNAME);
        } catch (NamingException e) {
            System.out.println(e);
        }
    }

    public static synchronized ConnectionPool getInstance() {
        if (pool == null) {
            pool = new ConnectionPool();
        }
        return pool;
    }

    public Connection getConnection() {
        try {
            return dataSource.getConnection(DB_USERNAME,DB_PASSWORD);
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
*/