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
package net.landora.video.filebrowser;

import java.awt.Color;
import java.io.File;
import java.util.*;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import net.landora.video.VideoManagerApp;
import net.landora.video.filerenaming.CheckFilesTask;
import net.landora.video.filestate.data.FileRecord;
import net.landora.video.filestate.data.LocalPathManager;
import net.landora.video.filestate.data.SharedDirectoryDBA;
import net.landora.video.info.MetadataUtils;
import net.landora.video.info.MetadataUtils.GroupingValue;
import net.landora.video.info.VideoMetadata;
import net.landora.video.manager.ContentPanel;
import net.landora.video.tasks.TaskCompletedEvent;
import net.landora.video.ui.ContextProducer;
import net.landora.video.ui.TableCornerPanel;
import net.landora.video.ui.TableRowHeader;
import net.landora.video.utils.*;

/**
 *
 * @author bdickie
 */
public class DuplicatePanel extends ContentPanel implements ListSelectionListener {

    /**
     * Creates new form DuplicatePanel
     */
    public DuplicatePanel() {
        initComponents();
        
        VideoManagerApp.getInstance().getEventBus().addHandlersWeak(this);
        
        tblVideos.getColumnModel().getSelectionModel().addListSelectionListener(this);
    }

    private DefaultTreeModel treeModel;
    private Map<OrderedRepresentation<String>, GroupingValue<FileRecord>> groups;
    
    @Override
    public void loadView() {
        Collection<FileRecord> duplicateFileRecords = SharedDirectoryDBA.getDuplicateFileRecords();
        
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("ROOT");
        
        groups = MetadataUtils.groupFileItems(duplicateFileRecords);
        addToTreeNode(rootNode, groups);
        
        treeModel = new DefaultTreeModel(rootNode);
        
        treeVideos.setModel(treeModel);
        
        treeVideos.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    }
    
    private void addToTreeNode(DefaultMutableTreeNode parent, Map<OrderedRepresentation<String>, GroupingValue<FileRecord>> values) {
        for (Map.Entry<OrderedRepresentation<String>, GroupingValue<FileRecord>> entry : values.entrySet()) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(entry.getKey());
            
            addToTreeNode(node, entry.getValue().getChildrenGroups());
            
            parent.add(node);
        }
    }

    private void reloadIfActive() {
        if (treeModel != null)
            loadView();
    }
    
    @BusReciever
    public void checkFilesCompleted(TaskCompletedEvent e) {
        if (e.isTaskOf(CheckFilesTask.class))
            reloadIfActive();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        treeVideos = new javax.swing.JTree();
        scrTables = new javax.swing.JScrollPane();
        tblVideos = new javax.swing.JTable();

        setTitle("Duplicate Files");
        setLayout(new java.awt.BorderLayout());

        treeVideos.setRootVisible(false);
        treeVideos.setShowsRootHandles(true);
        treeVideos.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                treeVideosValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(treeVideos);

        jSplitPane1.setLeftComponent(jScrollPane1);

        tblVideos.setColumnSelectionAllowed(true);
        tblVideos.setRowSelectionAllowed(false);
        tblVideos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblVideosMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblVideosMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblVideosMouseReleased(evt);
            }
        });
        scrTables.setViewportView(tblVideos);

        jSplitPane1.setRightComponent(scrTables);

        add(jSplitPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void treeVideosValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_treeVideosValueChanged
        TreePath path = treeVideos.getSelectionPath();
        if (path == null) {
            setSelectedItems(Collections.EMPTY_LIST);
        } else {
            Map<OrderedRepresentation<String>, GroupingValue<FileRecord>> map = groups;
            for(int i = 1; i < path.getPathCount(); i++) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getPathComponent(i);
                OrderedRepresentation<String> rep = (OrderedRepresentation<String>) node.getUserObject();
                GroupingValue<FileRecord> groupValue = map.get(rep);
                if (i + 1 == path.getPathCount()) {
                    setSelectedItems(groupValue.getLeafs());
                } else
                    map = groupValue.getChildrenGroups();
                
            }
        }
    }//GEN-LAST:event_treeVideosValueChanged

    private void tblVideosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVideosMouseClicked
        maybePopup(evt);
    }//GEN-LAST:event_tblVideosMouseClicked

    private void tblVideosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVideosMousePressed
        maybePopup(evt);
    }//GEN-LAST:event_tblVideosMousePressed

    private void tblVideosMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVideosMouseReleased
        maybePopup(evt);
    }//GEN-LAST:event_tblVideosMouseReleased

    
    private List<Touple<FileRecord,VideoMetadata>> selectedRecords;
    
    private void setSelectedItems(List<Touple<FileRecord,VideoMetadata>> records) {
        if (ComparisionUtils.equals(selectedRecords, records))
            return;
        
        this.selectedRecords = records;
        
        LocalPathManager localPathManager = LocalPathManager.getInstance();
        
        List<Map<String,String>> values = new ArrayList<Map<String, String>>();
        for (Touple<FileRecord, VideoMetadata> touple : records) {
            LinkedHashMap<String,String> v = new LinkedHashMap<String, String>();
            
            v.putAll(touple.getSecond().getAllInformation(true));
            
            v.put("ED2K", touple.getFirst().getE2dkHash());
            
            File path = localPathManager.getLocalPath(touple.getFirst());
            if (path != null)
                v.put("Path", path.getPath());
            
            values.add(v);
        }
        
        List<String> rowsValues = new ArrayList<String>();
        for (Map<String, String> map : values) {
            combineValues(rowsValues, new ArrayList<String>(map.keySet()));
        }
        
        String[][] dataMap = new String[rowsValues.size()][records.size()];
        Object[] ids = new Object[records.size()];
        
        for (int i = 0; i < records.size(); i++) {
            Touple<FileRecord, VideoMetadata> touple = records.get(i);
            FileRecord fileRecord = touple.getFirst();
            Map<String,String> v = values.get(i);
            
            ids[i] = fileRecord.getFilename();
            
            for (int j = 0; j < rowsValues.size(); j++) {
                String rowValue = rowsValues.get(j);
                
                String value = v.get(rowValue);
                
                dataMap[j][i] = (value == null ? "" : value);
            }
        }
        
        List<Object> headers = new ArrayList<Object>();
        for (int i = 0; i < rowsValues.size(); i++) {
            String header = rowsValues.get(i);
            
            boolean allEqual = true;
            String currentValue = dataMap[i][0];
            for (int j = 1; j < records.size(); j++) {
                if (!ComparisionUtils.equalsIgnoreCase(currentValue, dataMap[i][j])) {
                    allEqual = false;
                    break;
                }
            }
            if (allEqual) {
                headers.add(header);
            } else {
                TableRowHeaderRenderer.StyledValue sv = new TableRowHeaderRenderer.StyledValue(header);
                sv.setBackground(new Color(255, 200, 200));
                headers.add(sv);
            }
        }
        
        ConfigurableTableModel model = new ConfigurableTableModel(ids);
        
        model.setDataVector(dataMap, ids);
        tblVideos.setModel(model);
        
        scrTables.setRowHeaderView(new TableRowHeader(tblVideos, headers, new TableRowHeaderRenderer()));
        scrTables.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, new TableCornerPanel(tblVideos));
    }
    
    private void combineValues(List<String> insertTo, List<String> toInsert) {
        if (insertTo.isEmpty()) {
            insertTo.addAll(toInsert);
            return;
        }
        
        for (int i = 0; i < toInsert.size(); i++) {
            String newValue = toInsert.get(i);
            
            if (insertTo.contains(newValue))
                continue;
            
            for (int j = 0; j < insertTo.size(); j++) {
                boolean good = true;
                
                for (int k = 0; k < toInsert.size(); k++) {
                    if (k == i)
                        continue;
                    
                    String checkValue = toInsert.get(k);
                    boolean before1 = k < i;
                    
                    Boolean before2 = null;
                    for (int l = 0; l < insertTo.size(); l++) {
                        if (insertTo.get(l).equals(checkValue)) {
                            before2 = l < j;
                            break;
                        }
                    }
                    
                    if (before2 != null && before1 != before2.booleanValue()) {
                        good = false;
                        break;
                    }
                }
                
                
                
                if (good) {
                    insertTo.add(j, newValue);
                    break;
                }
            }
            
            if (!insertTo.contains(newValue))
                insertTo.add(newValue);
            
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JScrollPane scrTables;
    private javax.swing.JTable tblVideos;
    private javax.swing.JTree treeVideos;
    // End of variables declaration//GEN-END:variables

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting())
            return;
        
        LocalPathManager localPathManager = LocalPathManager.getInstance();
        
        int[] columns = tblVideos.getSelectedColumns();
        List<Object> pairs = new ArrayList<Object>();
        for (int c : columns) {
            Touple<FileRecord, VideoMetadata> t = selectedRecords.get(c);
            FileRecord record = t.getFirst();
            
            File file = localPathManager.getLocalPath(record);
            if (file == null)
                pairs.add(new MetaPair(t.getFirst(), t.getSecond()));
            else {
                VideoFile video = new VideoFile(file);
                video.setInfo(record);
                video.setVideo(t.getSecond());
                pairs.add(video);
            }
        }
        
        setCurrentContext(pairs);
    }
    
    private static class MetaPair implements ContextProducer {
        private FileRecord record;
        private VideoMetadata md;

        public MetaPair(FileRecord record, VideoMetadata md) {
            this.record = record;
            this.md = md;
        }

        @Override
        public void addContentObjects(Collection<Object> addTo) {
            addTo.add(md);
            addTo.add(record);
        }
        
    }
}
