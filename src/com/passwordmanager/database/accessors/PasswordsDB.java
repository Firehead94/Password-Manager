package com.passwordmanager.database.accessors;

import com.passwordmanager.database.objects.Password;
import com.passwordmanager.utils.DB;
import com.sun.xml.internal.ws.util.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PasswordsDB {

    // GET
    public Password getPassword(String attribute, int value) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Password pwd = null;

        String query = "SELECT * FROM PASSWORDS " +
                "WHERE " + attribute + " = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, Integer.toString(value));
            pwd = getFromDB(ps);
        } catch (SQLException e) {
            Logger.getLogger(PasswordsDB.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtils.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return pwd;
    }

    public Password getPassword(String attribute, String value) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Password pwd = null;

        String query = "SELECT * FROM PASSWORDS " +
                "WHERE " + attribute + " = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, value);
            pwd = getFromDB(ps);
        } catch (SQLException e) {
            Logger.getLogger(PasswordsDB.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtils.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return pwd;
    }

    public ArrayList<Password> getPasswords() {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Password> pwds = null;

        String query = "SELECT * FROM PASSWORDS";
        try {
            ps = connection.prepareStatement(query);
            pwds = getListFromDB(ps);
        } catch (SQLException e) {
            Logger.getLogger(PasswordsDB.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtils.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return pwds;
    }

    // UPDATE
    public boolean updatePassword(Password pwd) {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        int retVal = 0;

        String query = "UPDATE PASSWORDS SET " + DB.PASSWORD +
                " = ?, " + DB.PASSWORD_TITLE +
                " = ?, " + DB.PASSWORD_TIMESTAMP +
                " = ? WHERE " + DB.PASSWORD_ID + " = ?;";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, pwd.getPassword());
            ps.setString(2, pwd.getPassword_title());
            ps.setLong(3, pwd.password_timestamp());
            ps.setInt(4, pwd.getPassword_ID());
            retVal = ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(PasswordsDB.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtils.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return retVal != 0;
    }


    // INSERT
    public boolean insertPassword(Password pwd) {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        int retVal = 0;

        String query = "INSERT INTO PASSWORDS (" +
                DB.PASSWORD + ", " +
                DB.PASSWORD_TITLE + ", " +
                DB.PASSWORD_TIMESTAMP + ", " +
                "VALUES (?,?,?);";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, pwd.getPassword());
            ps.setString(2, StringUtils.capitalize(pwd.getPassword_title()));
            ps.setLong(3, pwd.password_timestamp());
            retVal = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtils.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return retVal != 0;

    }

    // GENERIC
    /**
     * Gets a single password bean from the a DB based on a prepared statement
     * @param ps
     * @return
     */
    private static Password getFromDB(PreparedStatement ps) {

        ResultSet rs = null;
        Password pwd = null;
        try {
            rs = ps.executeQuery();
            if (rs.next()) {
                pwd = new Password();

                pwd.setPassword(rs.getString(DB.PASSWORD));
                pwd.setFolder_ID(rs.getInt(DB.FOLDER_ID));
                pwd.setPassword_title(rs.getString(DB.PASSWORD_TITLE));
                pwd.setPassword_timestamp(rs.getInt(DB.PASSWORD_TIMESTAMP));
            }
        } catch (SQLException e) {
            Logger.getLogger(PasswordsDB.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtils.closeResultSet(rs);
        }
        return pwd;
    }

    /**
     * Gets a list of password beans from a DB based on a prepared
     * statement.
     *
     * @param ps
     * @return
     */
    private static ArrayList<Password> getListFromDB(PreparedStatement ps) {
        ResultSet rs = null;
        ArrayList<Password> pwds = null;
        try {
            rs = ps.executeQuery();
            pwds = new ArrayList<Password>();
            while (rs.next()) {
                Password pwd = new Password();

                pwd.setPassword(rs.getString(DB.PASSWORD));
                pwd.setFolder_ID(rs.getInt(DB.FOLDER_ID));
                pwd.setPassword_title(rs.getString(DB.PASSWORD_TITLE));
                pwd.setPassword_timestamp(rs.getInt(DB.PASSWORD_TIMESTAMP));

                pwds.add(pwd);
            }
        } catch (SQLException e) {
            Logger.getLogger(PasswordsDB.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtils.closeResultSet(rs);
        }
        return pwds;
    }


}
