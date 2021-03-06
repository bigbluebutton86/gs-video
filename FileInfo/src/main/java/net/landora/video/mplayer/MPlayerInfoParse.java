/**
 *     Copyright (C) 2012 Blake Dickie
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


package net.landora.video.mplayer;

import java.util.*;

/**
 *
 * @author bdickie
 */
public class MPlayerInfoParse {
    private Map<String,List<String>> values;

    public MPlayerInfoParse() {
        values = new HashMap<String,List<String>>();
    }
    
    
    public void add(String key, String value) {
        List<String> valList = values.get(key);
        if (valList == null) {
            valList = new ArrayList<String>();
            values.put(key, valList);
        }
        valList.add(value);
    }
    
    public Set<String> getKeys() {
        return values.keySet();
    }
    
    public String getSingle(String key) {
        List<String> valList = values.get(key);
        return (valList == null ? null : valList.get(valList.size() - 1));
    }
    
    public List<String> getList(String key) {
        List<String> valList = values.get(key);
        return (valList == null ? Collections.EMPTY_LIST : valList);
    }
}
