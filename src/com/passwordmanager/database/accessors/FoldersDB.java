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
//TODO add remove folder, call remove password

/**
 * @author Justin Scott
 * @author Nick Soltysiak
 */
public class FoldersDB {

    /**
     * Validates whether folder exists in the database.
     *
     * @param name Folder name entered in by user
     * @return Bool based on folder exists
     */
    public static boolean folderExists(String name) {
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

    /**
     * Gets a single folder object populated from the database using the inputs.
     *
     * @param attribute Column name in the FOLDERS table you'd like to
     *                  search by.
     * @param value Value of the column you'd like to filter by.
     * @return Single folder object given the filtered inputs.
     */
    public static Folder getFolder(String attribute, Object value) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Folder folder = null;

        String query = "SELECT * FROM FOLDERS " +
                "WHERE " + attribute + " = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, value.toString());
            folder = getFromDB(ps);
        } catch (SQLException e) {
            Logger.getLogger(FoldersDB.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtils.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return folder;

    }

    /**
     * Gets an arraylist of folder objects populated from the database using the inputs.
     * FOR ACCESS LEVELS USE getFoldersByAL
     * @param attribute Column name in the FOLDERS table you'd like to
     *                  search by.
     * @param value Value of the column you'd like to filter by.
     * @return arraylist of folder objects given the filtered inputs.
     */
    public static ArrayList<Folder> getFolders(String attribute, Object value) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Folder> folders = null;

        String query = "SELECT * FROM FOLDERS " +
                "WHERE " + attribute + " = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, value.toString());
            folders = getListFromDB(ps);
        } catch (SQLException e) {
            Logger.getLogger(FoldersDB.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtils.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return folders;
    }

    /**
     * Gets an arraylist of folder objects populated from the database using the access level.
     *
     * @param value Value of the column you'd like to filter by.
     * @return arraylist of folder objects given the filtered inputs.
     */
    public static ArrayList<Folder> getFoldersByAL(int value) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Folder> folders = null;

        String query = "SELECT * FROM FOLDERS " +
                "WHERE ACCESS_LEVEL >= ?";
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

    /**
     * Gets an arraylist of all folder objects populated from DB.
     * @return
     */
    public static ArrayList<Folder> getFolders() {
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

    /**
     * Updates passed folder object in the database
     * @param folder Object with updated values
     * @return Bool on whether update was completed.
     */
    public static boolean updateFolder(Folder folder) {

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

    /**
     * Inserts passed folder object into the database. Used only on new folder
     * objects. Use updateFolder to update the information of a user object.
     * @param folder New folder to insert into the database.
     * @return Bool based on success of insert statement. Use folderExists for double checking.
     */
    public static boolean insertFolder(Folder folder) {

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
