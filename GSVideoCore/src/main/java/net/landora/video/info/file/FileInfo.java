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


package net.landora.video.info.file;

import java.io.Serializable;

/**
 *
 * @author bdickie
 */
public class FileInfo implements Serializable {

    private String filename;
    private long fileSize;
    private String e2dkHash;
    private String metadataSource;
    private String metadataId;
    private String videoId;
    private long lastModified;

    public FileInfo() {
    }
    
    @Override
    public FileInfo clone() {
        FileInfo info = new FileInfo();
        info.filename = filename;
        info.fileSize = fileSize;
        info.e2dkHash = e2dkHash;
        info.metadataSource = metadataSource;
        info.metadataId = metadataId;
        info.lastModified = lastModified;
        info.videoId = videoId;
        
        return info;
    }

    public String getE2dkHash() {
        return e2dkHash;
    }

    public void setE2dkHash(String e2dkHash) {
        this.e2dkHash = e2dkHash;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getMetadataId() {
        return metadataId;
    }

    public void setMetadataId(String metadataId) {
        this.metadataId = metadataId;
    }

    public String getMetadataSource() {
        return metadataSource;
    }

    public void setMetadataSource(String metadataSource) {
        this.metadataSource = metadataSource;
    }

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    @Override
    public String toString() {
        return String.format("%s: %s - %s", getFilename(), getMetadataSource(), getMetadataId());
    }

    
    
}
