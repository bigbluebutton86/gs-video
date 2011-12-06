/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.videofilerenaming;

import java.util.List;
import net.landora.videoinfo.data.preferences.NBPreferenceObject;

/**
 *
 * @author bdickie
 */
public enum RenamePreferences {
    AutomaticRenameFolders(new String[] { "/var/storage/Downloads"});
    
    private RenamePreferences(String defaultValue) {
        pref = new NBPreferenceObject(getClass(), name(), defaultValue);
    }
    
    private RenamePreferences(int defaultValue) {
        pref = new NBPreferenceObject(getClass(), name(), defaultValue);
    }
    
    private RenamePreferences(long defaultValue) {
        pref = new NBPreferenceObject(getClass(), name(), defaultValue);
    }
    
    private RenamePreferences(boolean defaultValue) {
        pref = new NBPreferenceObject(getClass(), name(), defaultValue);
    }
    
    private RenamePreferences(String[] defaultValue) {
        pref = new NBPreferenceObject(getClass(), name(), defaultValue);
    }
    
    private NBPreferenceObject pref;

    public void setStringList(List<String> value) {
        pref.setStringList(value);
    }

    public void setString(String value) {
        pref.setString(value);
    }

    public void setInt(int value) {
        pref.setInt(value);
    }

    public void setBoolean(boolean value) {
        pref.setBoolean(value);
    }

    public List<String> getStringList() {
        return pref.getStringList();
    }

    public String getString() {
        return pref.getString();
    }

    public String getPrefName() {
        return pref.getPrefName();
    }

    public int getInt() {
        return pref.getInt();
    }

    public boolean getBoolean() {
        return pref.getBoolean();
    }

    public void setLong(long value) {
        pref.setLong(value);
    }

    public long getLong() {
        return pref.getLong();
    }

}
