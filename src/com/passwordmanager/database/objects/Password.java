package com.passwordmanager.database.objects;


public class Password {

    private int password_ID;
    private int folder_ID;
    private String password;
    private String password_title;
    private long password_timestamp;

    /**
     * @return the password_ID
     */
    public int getPassword_ID()
    {
        return password_ID;
    }

    /**
     * @return the folder_ID
     */
    public int getFolder_ID()
    {
        return folder_ID;
    }

    /**
     * @return the password;
     */

    public String getPassword()
    {
        return password;
    }

    /**
     * @return the password_title;
     */

    public String getPassword_title()
    {
        return password_title;
    }

    /**
     * @return the password_timestamp;
     */

    public long password_timestamp()
    {
        return password_timestamp;
    }

    /**
     * @param password_ID the password_ID to set
     */
    public void setPassword_ID(int password_ID)
    {
        this.password_ID = password_ID;
    }

    /**
     * @param folder_ID the folder_ID to set
     */
    public void setFolder_ID (int folder_ID)
        {
        this.folder_ID = folder_ID;
    }

    /**
     * @param password the password to set
     */
    public void setPassword (String password)
    {
        this.password = password;
    }

    /**
     * @param password_title the password_title to set
     */
    public void setPassword_title (String password_title)
    {
        this.password_title = password_title;
    }

    /**
     * @param password_timestamp the password_timestamp to set
     */
    public void setPassword_timestamp (long password_timestamp)
    {
        this.password_timestamp = password_timestamp;
    }
}
