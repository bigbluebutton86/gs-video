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

package net.landora.video.tasks;

/**
 *
 * @author bdickie
 */
public interface TaskProgress {
    public void startDeterminate(String name, long current, long total);
    public void startDeterminate(String name, double current, double total);
    public void startIndeterminate(String name);
    
    public void progressDeterminate(long current, long total);
    public void progressDeterminate(double current, double total);
    public void progressIndeterminate();
    
    public void setName(String name);
    public void setMessage(String message);
    
    public void finished();
}
