package com.passwordmanager;


import java.sql.*;
import com.passwordmanager.database.*;


public class Main {

    public static void main(String[] args) {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM TEST_TABLE";

        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString("TEST_COLUMN"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtils.closeResultSet(rs);
            DBUtils.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }

    }
}