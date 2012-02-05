/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.videofilerenaming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import org.apache.commons.io.IOUtils;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;

/**
 *
 * @author bdickie
 */
public class RenameScriptManager {

    // <editor-fold defaultstate="collapsed" desc="Singleton">
    /**
     * SingletonHolder is loaded on the first execution of Singleton.getInstance()
     * or the first access to SingletonHolder.instance , not before.
     */
    private static class SingletonHolder {

        private final static RenameScriptManager instance = new RenameScriptManager();
    }

    public static RenameScriptManager getInstance() {
        return SingletonHolder.instance;
    }
    // </editor-fold>

    private RenameScriptManager() {
        loadCurrentScript();
    }
    private RenamingScript renamingScript;

    public RenamingScript getRenamingScript() {
        return renamingScript;
    }
    
    final void loadCurrentScript() {
        renamingScript = createRenamingScript(getFolderRenameScript(), getFileRenameScript(), true);
    }

    RenamingScript createRenamingScript(String folderScript, String fileScript, boolean reportExceptions) {
        try {
            StringBuilder str = new StringBuilder();
            
            
            str.append(getRenameScript("RenameScript.py"));
            
            str.append(createScript("findFolderName", folderScript));
            str.append("\n");
            str.append(createScript("findFilename", fileScript));

            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("jython");

            engine.eval(str.toString());

            Invocable inv = (Invocable) engine;
            return inv.getInterface(RenamingScript.class);
        } catch (Exception ex) {
            if (reportExceptions)
                Exceptions.printStackTrace(ex);
            return null;
        }
    }

    private String createScript(String functionName, String function) {
        try {
            StringBuilder builder = new StringBuilder();

            builder.append("def ");
            builder.append(functionName);
            builder.append("(metadata):\n");

            BufferedReader reader = new BufferedReader(new StringReader(function));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append("  ");
                builder.append(line.replaceAll("\t", "  "));
                builder.append("\n");
            }

            return builder.toString();
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
            return "";
        }
    }

    public String getFileRenameScript() {
        return getRenameScript("RenameScript_File.py");
    }

    public String getFolderRenameScript() {
        return getRenameScript("RenameScript_Folder.py");
    }

    public void setFileRenameScript(String script) {
        setRenameScript("RenameScript_File.py", script);
    }

    public void setFolderRenameScript(String script) {
        setRenameScript("RenameScript_Folder.py", script);
    }
    private static final String CHARSET = "UTF-8";

    private String getRenameScript(String filename) {
        FileObject scriptFile = FileUtil.getConfigFile("GSVideoManager/" + filename);
        InputStream is = null;
        try {
            is = scriptFile.getInputStream();
            return IOUtils.toString(is, CHARSET);
        } catch (Exception e) {
            Exceptions.printStackTrace(e);
            return "return None";
        } finally {
            if (is != null) {
                IOUtils.closeQuietly(is);
            }
        }
    }

    private void setRenameScript(String filename, String script) {
        FileObject scriptFile = FileUtil.getConfigFile("GSVideoManager/" + filename);
        OutputStream os = null;
        try {
            os = scriptFile.getOutputStream();
            IOUtils.write(script, os, CHARSET);
        } catch (Exception e) {
            Exceptions.printStackTrace(e);
        } finally {
            if (os != null) {
                IOUtils.closeQuietly(os);
            }
        }
    }
}