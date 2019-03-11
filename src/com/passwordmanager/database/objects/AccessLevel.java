package com.passwordmanager.database.objects;


public class AccessLevel {

    private int access_ID;
    private String access_title;

    /**
     * @return the access_ID
     */
    public int getAccess_ID()
    {
        return access_ID;
    }

    /**
     * @return the access_title
     */

    public String getAccess_title()
    {
        return access_title;
    }

    /**
     * @param access_ID the access_ID to set
     */
    public void setAccess_ID(int access_ID)
    {
        this.access_ID = access_ID;
    }

    /**
     * @param access_title the access_title to set
     */
    public void setAccess_title (String access_title)
    {
        this.access_title = access_title;
    }
}
