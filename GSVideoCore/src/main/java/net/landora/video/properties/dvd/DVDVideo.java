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


package net.landora.video.properties.dvd;

import net.landora.video.properties.VideoFormat;
import net.landora.video.properties.VideoStream;

/**
 *
 * @author bdickie
 */
public class DVDVideo extends VideoStream {
    
    
    private int angles;
    
    /** Creates a new instance of DVDVideo */
    public DVDVideo() {
        setFormat(VideoFormat.MPEG2);
    }

    public int getAngles() {
        return angles;
    }

    public void setAngles(int angles) {
        this.angles = angles;
    }
    
}
