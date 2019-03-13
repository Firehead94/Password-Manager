package com.passwordmanager.database.accessors;

import com.passwordmanager.database.objects.AccessLevel;
import com.passwordmanager.utils.DB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccessLevelDB {

    // GET

    public AccessLevel getAccessLevel(String title, int id) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        AccessLevel access = null;

        String query = "SELECT * FROM ACCESS_LEVELS " +
                "WHERE " + title + " = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, Integer.toString(id));
            access = getFromDB(ps);
        } catch (SQLException e) {
            Logger.getLogger(AccessLevelDB.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtils.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return access;

    }

    public AccessLevel getAccessLevel(String title, String id) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        AccessLevel access = null;

        String query = "SELECT * FROM ACCESS_LEVELS " +
                "WHERE " + title + " = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, id);
            access = getFromDB(ps);
        } catch (SQLException e) {
            Logger.getLogger(AccessLevelDB.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtils.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return access;

    }


    public ArrayList<AccessLevel> getAccessLevels(){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<AccessLevel> accessLevels = null;

        String query = "SELECT * FROM ACCESS_LEVELS";
        try {
            ps = connection.prepareStatement(query);
            accessLevels = getListFromDB(ps);
        } catch (SQLException e) {
            Logger.getLogger(AccessLevelDB.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtils.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return accessLevels;
    }

    // UPDATE
    public boolean updateAccessLevel(AccessLevel access) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        int retVal = 0;

        String query = "UPDATE Access_Levels SET " + DB.ACCESS_TITLE +
                " = ? WHERE " + DB.ACCESS_ID + " = ?;";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, access.getAccess_title());
            ps.setInt(2, access.getAccess_ID());
            retVal = ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(AccessLevelDB.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtils.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return retVal != 0;
    }

    // INSERT
    public boolean insertAccessLevel(AccessLevel access) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        int retVal = 0;

        String query = "INSERT INTO ACCESS_LEVELS (" +
                DB.ACCESS_ID + ", " +
                DB.ACCESS_TITLE + ") " +
                "VALUES (?,?);";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, access.getAccess_ID());
            ps.setString(2, access.getAccess_title());
            retVal = ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(AccessLevelDB.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtils.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return retVal != 0;
    }


    private static AccessLevel getFromDB(PreparedStatement ps) {

        ResultSet rs = null;
        AccessLevel access = null;
        try {
            rs = ps.executeQuery();
            if (rs.next()) {
                access = new AccessLevel();

                access.setAccess_ID(rs.getInt(DB.ACCESS_ID));
                access.setAccess_title(rs.getString(DB.ACCESS_TITLE);
            }
        } catch (SQLException e) {
            Logger.getLogger(AccessLevelDB.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtils.closeResultSet(rs);
        }
        return access;
    }

    private static ArrayList<AccessLevel> getListFromDB(PreparedStatement ps) {
        ResultSet rs = null;
        ArrayList<AccessLevel> accessLevels = null;
        try {
            rs = ps.executeQuery();
            accessLevels = new ArrayList<AccessLevel>();
            while (rs.next()) {
                AccessLevel access = new AccessLevel();

                access.setAccess_ID(rs.getInt(DB.ACCESS_ID));
                access.setAccess_title(rs.getString(DB.ACCESS_TITLE));
                accessLevels.add(access);
            }
        } catch (SQLException e) {
            Logger.getLogger(FoldersDB.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtils.closeResultSet(rs);
        }
        return accessLevels;
    }

}
