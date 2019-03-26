package com.passwordmanager.database.objects;

/**
 * @author Justin Scott
 * @author Nick Soltysiak
 */
public class FolderTimestamp {

    private int folder_ID;
    private long folder_timestamp;

    /**
     * @return the folder_ID
     */
    public int getFolder_ID()
    {
        return folder_ID;
    }

    /**
     * @return the folder_timestamp
     */

    public long getFolder_timestamp()
    {
        return folder_timestamp;
    }

    /**
     * @param folder_ID the folder_ID to set
     */
    public void setFolder_ID(int folder_ID)
    {
        this.folder_ID = folder_ID;
    }

    /**
     * @param folder_timestamp the folder_timestamp to set
     */
    public void setFolder_timestamp (long folder_timestamp)
        {
        this.folder_timestamp = folder_timestamp;
    }
}
