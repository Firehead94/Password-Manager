package com.passwordmanager.database.objects;


public class Folder {

    private int folder_ID;
    private int access_level;
    private String folder_name;
    private String folder_password;
    private int folder_parent;

    /**
     * @return the folder_ID of parent folder
     */
    public int getFolder_parent() { return folder_parent; }

    /**
     * @return the folder_ID
     */
    public int getFolder_ID()
    {
        return folder_ID;
    }

    /**
     * @return the access_level;
     */

    public int getAccess_level()
    {
        return access_level;
    }

    /**
     * @return the folder_name;
     */

    public String getFolder_name()
    {
        return folder_name;
    }

    /**
     * @return the folder_password;
     */

    public String getFolder_password()
    {
        return folder_password;
    }

    /**
     * @param folder_ID the folder_ID to set
     */
    public void setFolder_ID(int folder_ID)
    {
        this.folder_ID = folder_ID;
    }

    /**
     * @param access_level the access_level to set
     */
    public void setAccess_level (int access_level)
        {
        this.access_level = access_level;
    }

    /**
     * @param folder_name the folder_name to set
     */
    public void setFolder_name (String folder_name)
    {
        this.folder_name = folder_name;
    }

    /**
     * @param folder_password the folder_password to set
     */
    public void setFolder_password (String folder_password)
    {
        this.folder_password = folder_password;
    }

    /**
     * @param folder_parent Set the ID of the parent folder
     */
    public void setFolder_parent(int folder_parent) { this.folder_parent = folder_parent; }
}
