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

import java.util.EventObject;

/**
 *
 * @author bdickie
 */
public class TaskProgressImplEvent extends EventObject {

    public TaskProgressImplEvent(TaskProgressImpl source) {
        super(source);
    }

    @Override
    public TaskProgressImpl getSource() {
        return (TaskProgressImpl)super.getSource();
    }
    
    
    
}
