package com.passwordmanager.database.objects;


public class LoginTimestamp {

    private int user_ID;
    private long login_timestamp;

    /**
     * @return the user_ID
     */
    public int getUser_ID()
    {
        return user_ID;
    }

    /**
     * @return the login_timestamp
     */

    public long getLogin_timestamp()
    {
        return login_timestamp;
    }

    /**
     * @param user_ID the user_ID to set
     */
    public void setUser_ID(int user_ID)
    {
        this.user_ID = user_ID;
    }

    /**
     * @param login_timestamp the login_timestamp to set
     */
    public void setLogin_timestamp (long login_timestamp)
    {
        this.login_timestamp = login_timestamp;
    }
}
