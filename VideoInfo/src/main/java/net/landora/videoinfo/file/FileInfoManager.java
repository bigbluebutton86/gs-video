/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.landora.videoinfo.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.CheckedOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import jonelo.jacksum.algorithm.Edonkey;
import net.landora.videoinfo.MetadataProvidersManager;
import net.landora.videoinfo.util.XMLUtilities;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.NullOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author bdickie
 */
public class FileInfoManager {
    private Logger log = LoggerFactory.getLogger(getClass());

    // <editor-fold defaultstate="collapsed" desc="Singleton">
    /**
     * SingletonHolder is loaded on the first execution of Singleton.getInstance()
     * or the first access to SingletonHolder.instance , not before.
     */
    private static class SingletonHolder {

        private final static FileInfoManager instance = new FileInfoManager();
    }

    public static FileInfoManager getInstance() {
        return SingletonHolder.instance;
    }
    // </editor-fold>

    private FileInfoManager() {
        cachedItems = new HashMap<File, DirectoryCacheItem>();
    }

    private static final String DIRECTORY_INFO_FILE_COMPRESSED = ".gs_video_info.xml.gz";
    private static final String DIRECTORY_INFO_FILE_UNCOMPRESSED = ".gs_video_info.xml";

    private static final boolean COMPRESS_INFO_FILE = false;
    private static final String DIRECTORY_INFO_FILE = (COMPRESS_INFO_FILE ? DIRECTORY_INFO_FILE_COMPRESSED : DIRECTORY_INFO_FILE_UNCOMPRESSED);

    private final Map<File,DirectoryCacheItem> cachedItems;


    public FileInfo getFileInfo(File file) {
        if (!file.exists())
            throw new IllegalArgumentException("Unable to find file.");

        DirectoryCacheItem cache;
        synchronized(this) {
            cache = getDirectoryCacheItem(file.getParentFile());
        }
        FileInfo info = cache.getFileInfo(file.getName());
        if (info != null && info.getLastModified() == file.lastModified() && info.getFileSize() == file.length()) {
            if (MetadataProvidersManager.getInstance().checkForMetadataUpdate(info)) {
                cache.setFileInfo(file.getName(), info);
            }
            return info;
        }

        synchronized(this) {
            info = createInfo(file);
            cache.setFileInfo(file.getName(), info);
        }

        return info;
    }
    
    void removeFileInfo(File file) {
        DirectoryCacheItem cache;
        synchronized(this) {
            cache = getDirectoryCacheItem(file.getParentFile());
            cache.removeFileInfo(file.getName());
        }
    }
    
    void setFileInfo(File file, FileInfo info) {
        DirectoryCacheItem cache;
        synchronized(this) {
            cache = getDirectoryCacheItem(file.getParentFile());
            cache.setFileInfo(file.getName(), info);
        }
    }

    private synchronized FileInfo createInfo(File file) {
        FileInfo result = new FileInfo();
        result.setFileSize(file.length());
        result.setLastModified(file.lastModified());
        result.setFilename(file.getName());

        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(file));
            Edonkey e2dkChecksum = new Edonkey();

            IOUtils.copy(is, new CheckedOutputStream(new NullOutputStream(), e2dkChecksum));

            result.setE2dkHash(e2dkChecksum.getHexValue());
        } catch (Exception e) {
            log.error("Error hashing file.", e);
            return null;
        } finally {
            IOUtils.closeQuietly(is);
        }

        MetadataProvidersManager.getInstance().checkForMetadata(result);

        return result;
    }

    private DirectoryCacheItem getDirectoryCacheItem(File directory) {
        synchronized(cachedItems) {
            File cacheFile = new File(directory, DIRECTORY_INFO_FILE);
            DirectoryCacheItem cache = cachedItems.get(directory);

            if (cache == null || (cacheFile.exists() && cacheFile.lastModified() > cache.getLastLoaded())) {
                
                if (cacheFile.exists()) {
                    cache = new DirectoryCacheItem(parseCacheFile(cacheFile));
                    cache.setLastLoaded(cacheFile.lastModified());
                } else {
                    cache = new DirectoryCacheItem();
                    cache.setLastLoaded(System.currentTimeMillis());
                }
                cache.setDirectory(directory);
                
                cachedItems.put(directory, cache);
            }
            
            return cache;
        }
    }

    private void updateDirectoryCache(DirectoryCacheItem cache, String changedFileName) {
        synchronized(cachedItems) {
            File directory = cache.getDirectory();

            File cacheFile = new File(directory, DIRECTORY_INFO_FILE);

            if (cacheFile.exists() && cacheFile.lastModified() > cache.getLastLoaded()) {
                DirectoryCacheItem newCache;
                if (cacheFile.exists()) {
                    newCache = new DirectoryCacheItem(parseCacheFile(cacheFile));
                    newCache.setLastLoaded(cacheFile.lastModified());
                } else {
                    newCache = new DirectoryCacheItem();
                    newCache.setLastLoaded(System.currentTimeMillis());
                }
                newCache.setDirectory(directory);

                FileInfo info = cache.getFileInfo(changedFileName);
                if (info != null) {
                    newCache.files.put(changedFileName, info);
                } else {
                    newCache.files.remove(changedFileName);
                }

                cachedItems.put(directory, newCache);
                cache = newCache;
            }

            if (cache.files.isEmpty())
                cacheFile.delete();
            else
                writeCacheFile(cacheFile, cache.files);
        }
    }

    private synchronized Map<String,FileInfo> parseCacheFile(File file) {
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(file));
            if (COMPRESS_INFO_FILE)
                is = new GZIPInputStream(is);

            XMLStreamReader reader = XMLInputFactory.newInstance().createXMLStreamReader(is);

            reader.nextTag();
            reader.require(XMLStreamReader.START_ELEMENT, null, "files");

            Map<String,FileInfo> files = new HashMap<String, FileInfo>();

            while(reader.nextTag() != XMLStreamReader.END_ELEMENT) {
                reader.require(XMLStreamReader.START_ELEMENT, null, "file");

                FileInfo info = new FileInfo();
                String filename = reader.getAttributeValue(null, "filename");
                info.setFilename(filename);
                info.setE2dkHash(reader.getAttributeValue(null, "ed2k"));
                info.setFileSize(Long.parseLong(reader.getAttributeValue(null, "length")));
                info.setLastModified(Long.parseLong(reader.getAttributeValue(null, "lastmodified")));
                info.setMetadataSource(reader.getAttributeValue(null, "metadatasource"));
                info.setMetadataId(reader.getAttributeValue(null, "metadataid"));

                files.put(filename, info);
                
                XMLUtilities.ignoreTag(reader);
                
            }
            reader.close();

            return files;

        } catch (Exception e) {
            log.error("Error parsing file cache.", e);
            return new HashMap<String, FileInfo>();
        } finally {
            if (is != null)
                IOUtils.closeQuietly(is);
        }
    }

    private synchronized void writeCacheFile(File file, Map<String,FileInfo> infoMap) {
        OutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file));
            if (COMPRESS_INFO_FILE)
                os = new GZIPOutputStream(os);

            XMLStreamWriter writer = XMLOutputFactory.newInstance().createXMLStreamWriter(os);
            writer.writeStartDocument();

            writer.writeStartElement("files");
            writer.writeCharacters("\n");

            for(Map.Entry<String,FileInfo> entry: infoMap.entrySet()) {
                FileInfo info = entry.getValue();

                writer.writeStartElement("file");
                writer.writeAttribute("filename", entry.getKey());

                writer.writeAttribute("ed2k", info.getE2dkHash());
                writer.writeAttribute("length", String.valueOf(info.getFileSize()));
                writer.writeAttribute("lastmodified", String.valueOf(info.getLastModified()));


                if (info.getMetadataSource() != null)
                    writer.writeAttribute("metadatasource", info.getMetadataSource());
                if (info.getMetadataId() != null)
                    writer.writeAttribute("metadataid", info.getMetadataId());

                writer.writeEndElement();
                writer.writeCharacters("\n");
            }

            writer.writeEndElement();
            writer.writeEndDocument();
            writer.close();
            
        } catch (Exception e) {
            log.error("Error writing file cache.", e);
        } finally {
            if (os != null)
                IOUtils.closeQuietly(os);
        }
    }

    private class DirectoryCacheItem {
        private long lastLoaded;

        private File directory;
        private Map<String,FileInfo> files;

        public DirectoryCacheItem() {
            files = new HashMap<String, FileInfo>();
        }

        public DirectoryCacheItem(Map<String, FileInfo> files) {
            this.files = files;
        }

        public File getDirectory() {
            return directory;
        }

        public void setDirectory(File directory) {
            this.directory = directory;
        }

        
        

        public long getLastLoaded() {
            return lastLoaded;
        }

        public void setLastLoaded(long lastLoaded) {
            this.lastLoaded = lastLoaded;
        }

        public synchronized FileInfo getFileInfo(String filename) {
            return files.get(filename);
        }

        public synchronized void setFileInfo(String filename, FileInfo info) {
            files.put(filename, info);

            updateDirectoryCache(this, filename);
        }

        public synchronized void removeFileInfo(String filename) {
            files.remove(filename);

            updateDirectoryCache(this, filename);
        }
    }
}
