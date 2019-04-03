package com.passwordmanager.database.accessors;
import java.util.ArrayList;
import java.sql.*;
import com.passwordmanager.database.objects.*;
import com.passwordmanager.utils.DB;
import com.sun.xml.internal.ws.util.StringUtils;
import com.passwordmanager.utils.HashPassword;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Justin Scott
 * @author Nick Soltysiak
 */
public class UserDB {

    /**
     * Validates a users login information. Will return true if the user has
     * entered the correct credentials.
     *
     * @param username Username entered in by user
     * @param pass hash generated from password
     * @return Bool based on login information
     */
    public static boolean validateUser(String username, String pass) {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String retval = "";

        String query = "SELECT USER_PASSWORD FROM USERS " +
                "WHERE USER_USERNAME = ?;";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, username);
            rs = ps.executeQuery();

            if (rs.next()) {
                retval = rs.getString(DB.USER_PASSWORD);
                String[] split = retval.split(":");
                return HashPassword.checkPasswordMatch(pass, split[0], split[1]);
            }
        } catch (SQLException e) {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtils.closeResultSet(rs);
            DBUtils.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return false;
    }

    /**
     * Validates whether user exists in the database.
     *
     * @param username Username entered in by user
     * @return Bool based on user exists
     */
    public static boolean userExists(String username) {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Boolean flag = false;

        String query = "SELECT * FROM USERS " +
                "WHERE " + DB.USER_USERNAME + " = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, username);
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
     * Gets a single user object populated from the database using the inputs.
     *
     * @param attribute Column name in the USER table you'd like to
     *                  search by.
     * @param value Value of the column you'd like to filter by.
     * @return Single user object given the filtered inputs.
     */
    public static User getUser(String attribute, Object value) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = new User();

        String query = "SELECT * FROM USERS " +
                "WHERE " + attribute + " = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, value.toString());
            user = getFromDB(ps);
        } catch (SQLException e) {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtils.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return user;
    }

    /**
     * @return All users in the database
     */
    public static ArrayList<User> getUsers() {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<User> users = null;

        String query = "SELECT * FROM USERS";
        try {
            ps = connection.prepareStatement(query);
            users = getListFromDB(ps);
        } catch (SQLException e) {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtils.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return users;
    }

    /**
     * Gets a list of user object populated from the databased using the inputs.
     *
     * @param attribute Column name in the USER table you'd like to
     *                  search by.
     * @param value Value of the column you'd like to filter by.
     * @return Arraylist of user objects given the filtered inputs.
     */
    public static ArrayList<User> getUsers(String attribute, Object value) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<User> users = null;

        String query = "SELECT * FROM USERS " +
                "WHERE " + attribute + " = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, value.toString());
            users = getListFromDB(ps);
        } catch (SQLException e) {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtils.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return users;
    }

    /**
     * Gets a list of user objects by access level of less prio
     * than passed number.
     *
     * @param value Value of the column you'd like to filter by.
     * @return Arraylist of user objects given the filtered inputs.
     */
    public static ArrayList<User> getUsersChildren(int value) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<User> users = null;

        String query = "SELECT * FROM USERS " +
                "WHERE USER_ACCESS_LEVEL > ?";
        try {
            ps = connection.prepareStatement(query);
                ps.setInt(1,value);
            users = getListFromDB(ps);
        } catch (SQLException e) {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtils.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return users;
    }
    // UPDATE

    /**
     * Updates passed user object in the database
     * @param user Object with updated values
     * @return Bool on whether update was completed.
     */
    public static boolean updateUser(User user) {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        int retVal = 0;

        String query = "UPDATE USERS SET " + DB.USER_USERNAME +
                " = ?, " + DB.USER_FIRSTNAME +
                " = ?, " + DB.USER_LASTNAME +
                " = ?, " + DB.USER_ACCESS_LEVEL +
                " = ?, " + DB.USER_PASSWORD +
                " = ? WHERE " + DB.USER_ID + " = ?;";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, user.getUser_username());
            ps.setString(2, user.getUser_first_name());
            ps.setString(3, user.getUser_last_name());
            ps.setInt(4, user.getAccess_level());
            ps.setString(5, user.getUser_password());
            ps.setInt(6, user.getUser_ID());
            retVal = ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtils.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return retVal != 0;
    }

    // INSERT

    /**
     * Inserts passed user object into the database. Used only on new user
     * objects. Use updateUser to update the information of a user object.
     * @param user New user to insert into the database.
     * @return Bool based on success of insert statement. Use userExists for double checking.
     */
    public static boolean insertUser(User user) {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        int retVal = 0;

        String query = "INSERT INTO USERS (" +
        DB.USER_USERNAME + ", " +
        DB.USER_FIRSTNAME + ", " +
        DB.USER_LASTNAME + ", " +
        DB.USER_PASSWORD + ") " +
                "VALUES (?,?,?,?);";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, StringUtils.capitalize(user.getUser_username()));
            ps.setString(2, StringUtils.capitalize(user.getUser_first_name()));
            ps.setString(3, StringUtils.capitalize(user.getUser_last_name()));
            ps.setString(4, user.getUser_password());
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
     * Gets a single user bean from the a DB based on a prepared statement
     * @param ps
     * @return
     */
    private static User getFromDB(PreparedStatement ps) {

        ResultSet rs = null;
        User user = new User();
        try {
            rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();

                user.setUser_ID(rs.getInt(DB.USER_ID));
                user.setUser_username(rs.getString(DB.USER_USERNAME));
                user.setUser_first_name(rs.getString(DB.USER_FIRSTNAME));
                user.setUser_last_name(rs.getString(DB.USER_LASTNAME));
                user.setAccess_level(rs.getInt(DB.USER_ACCESS_LEVEL));
            }
        } catch (SQLException e) {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtils.closeResultSet(rs);
        }
        return user;
    }

    /**
     * Gets a single user bean from the a DB based on a prepared statement
     * @param val ID of user
     * @return
     */
    public static boolean deleteFromDB(int val) {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        boolean rs = false;

        String query = "DELETE FROM users WHERE USER_ID = ?;";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1,val);
            rs = ps.execute();
        } catch (SQLException e) {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtils.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return rs;
    }

    /**
     * Gets a list of user beans from a DB based on a prepared
     * statement.
     *
     * @param ps
     * @return
     */
    private static ArrayList<User> getListFromDB(PreparedStatement ps) {
        ResultSet rs = null;
        ArrayList<User> users = null;
        try {
            rs = ps.executeQuery();
            users = new ArrayList<User>();
            while (rs.next()) {
                User user = new User();

                user.setUser_ID(rs.getInt(DB.USER_ID));
                user.setUser_username(rs.getString(DB.USER_USERNAME));
                user.setUser_first_name(rs.getString(DB.USER_FIRSTNAME));
                user.setUser_last_name(rs.getString(DB.USER_LASTNAME));
                user.setAccess_level(rs.getInt(DB.USER_ACCESS_LEVEL));

                users.add(user);
            }
        } catch (SQLException e) {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtils.closeResultSet(rs);
        }
        return users;
    }

}
