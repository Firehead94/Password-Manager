package com.passwordmanager.database.accessors;

import com.passwordmanager.database.objects.Folder;
import com.passwordmanager.utils.DB;
import com.sun.xml.internal.ws.util.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FoldersDB {

    public boolean folderExists(String name) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean flag = false;

        String query = "SELECT * FROM FOLDERS " +
                "WHERE " + DB.FOLDER_NAME + " = ?";
            try {
            ps = connection.prepareStatement(query);
            ps.setString(1, name);
            rs = ps.executeQuery();
            if (rs.next()) {
                flag = true;
            }
        } catch (SQLException e) {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtils.closeResultSet(rs);
            DBUtils.closePreparedStatement(ps);
            pool.freeConnection(connection);
            return flag;
        }

    }

    // GET
    public Folder getFolder(String attribute, int value) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Folder folder = null;

        String query = "SELECT * FROM FOLDERS " +
                "WHERE " + attribute + " = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, Integer.toString(value));
            folder = getFromDB(ps);
        } catch (SQLException e) {
            Logger.getLogger(FoldersDB.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtils.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return folder;

    }
    public Folder getFolder(String attribute, String value) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Folder folder = null;

        String query = "SELECT * FROM FOLDERS " +
                "WHERE " + attribute + " = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, value);
            folder = getFromDB(ps);
        } catch (SQLException e) {
            Logger.getLogger(FoldersDB.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtils.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return folder;

    }

    public ArrayList<Folder> getFolders(String attribute, int value) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Folder> folders = null;

        String query = "SELECT * FROM FOLDERS " +
                "WHERE " + attribute + " = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, Integer.toString(value));
            folders = getListFromDB(ps);
        } catch (SQLException e) {
            Logger.getLogger(FoldersDB.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtils.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return folders;
    }
    public ArrayList<Folder> getFolders(String attribute, String value) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Folder> folders = null;

        String query = "SELECT * FROM FOLDERS " +
                "WHERE " + attribute + " = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, value);
            folders = getListFromDB(ps);
        } catch (SQLException e) {
            Logger.getLogger(FoldersDB.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtils.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return folders;
    }
    public ArrayList<Folder> getFolders() {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Folder> folders = null;

        String query = "SELECT * FROM FOLDERS";
        try {
            ps = connection.prepareStatement(query);
            folders = getListFromDB(ps);
        } catch (SQLException e) {
            Logger.getLogger(FoldersDB.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtils.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return folders;
    }

    // UPDATE
    public boolean updateFolder(Folder folder) {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        int retVal = 0;

        String query = "UPDATE FOLDERS SET " + DB.FOLDER_NAME +
                " = ?, " + DB.FOLDER_PARENT +
                " = ?, " + DB.FOLDER_PASSWORD +
                " = ?, " + DB.ACCESS_LEVEL +
                " = ? WHERE " + DB.FOLDER_ID + " = ?;";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, folder.getFolder_name());
            ps.setInt(2, folder.getFolder_parent());
            ps.setString(3, folder.getFolder_password());
            ps.setInt(4, folder.getAccess_level());
            ps.setInt(5, folder.getFolder_ID());
            retVal = ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(FoldersDB.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtils.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return retVal != 0;
    }

    // INSERT
    public boolean insertFolder(Folder folder) {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        int retVal = 0;

        String query = "INSERT INTO FOLDERS (" +
                DB.FOLDER_NAME + ", " +
                DB.FOLDER_PARENT + ", " +
                DB.FOLDER_PASSWORD + ", " +
                DB.ACCESS_LEVEL + ") " +
                "VALUES (?,?,?,?);";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, StringUtils.capitalize(folder.getFolder_name()));
            ps.setInt(2, folder.getFolder_parent());
            ps.setString(3, folder.getFolder_password());
            ps.setInt(4, folder.getAccess_level());
            retVal = ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(FoldersDB.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtils.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return retVal != 0;

    }

    // GENERIC
    /**
     * Gets a single folder bean from the a DB based on a prepared statement
     * @param ps
     * @return
     */
    private static Folder getFromDB(PreparedStatement ps) {

        ResultSet rs = null;
        Folder folder = null;
        try {
            rs = ps.executeQuery();
            if (rs.next()) {
                folder = new Folder();

                folder.setFolder_ID(rs.getInt(DB.FOLDER_ID));
                folder.setFolder_name(rs.getString(DB.FOLDER_NAME));
                folder.setFolder_parent(rs.getInt(DB.FOLDER_PARENT));
            }
        } catch (SQLException e) {
            Logger.getLogger(FoldersDB.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtils.closeResultSet(rs);
        }
        return folder;
    }

    /**
     * Gets a list of folder beans from a DB based on a prepared
     * statement.
     *
     * @param ps
     * @return
     */
    private static ArrayList<Folder> getListFromDB(PreparedStatement ps) {
        ResultSet rs = null;
        ArrayList<Folder> folders = null;
        try {
            rs = ps.executeQuery();
            folders = new ArrayList<Folder>();
            while (rs.next()) {
                Folder folder = new Folder();

                folder.setFolder_ID(rs.getInt(DB.FOLDER_ID));
                folder.setFolder_name(rs.getString(DB.FOLDER_NAME));
                folder.setFolder_parent(rs.getInt(DB.FOLDER_PARENT));

                folders.add(folder);
            }
        } catch (SQLException e) {
            Logger.getLogger(FoldersDB.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtils.closeResultSet(rs);
        }
        return folders;
    }
}
