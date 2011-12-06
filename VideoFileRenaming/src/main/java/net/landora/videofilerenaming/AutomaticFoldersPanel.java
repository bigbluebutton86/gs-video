/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.videofilerenaming;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import net.landora.gsuiutils.UIUtils;

final class AutomaticFoldersPanel extends javax.swing.JPanel {

    private final AutomaticFoldersOptionsPanelController controller;

    AutomaticFoldersPanel(AutomaticFoldersOptionsPanelController controller) {
        this.controller = controller;
        initComponents();
        
        model = new DefaultListModel();
        lstFolders.setModel(model);
        
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        lstFolders = new javax.swing.JList();
        jPanel1 = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        lstFolders.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstFoldersValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(lstFolders);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/landora/videofilerenaming/list-add.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(btnAdd, org.openide.util.NbBundle.getMessage(AutomaticFoldersPanel.class, "AutomaticFoldersPanel.btnAdd.text")); // NOI18N
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        jPanel1.add(btnAdd);

        btnRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/landora/videofilerenaming/list-remove.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(btnRemove, org.openide.util.NbBundle.getMessage(AutomaticFoldersPanel.class, "AutomaticFoldersPanel.btnRemove.text")); // NOI18N
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });
        jPanel1.add(btnRemove);

        add(jPanel1, java.awt.BorderLayout.NORTH);
    }// </editor-fold>//GEN-END:initComponents

    private void lstFoldersValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstFoldersValueChanged
        controller.changed();
    }//GEN-LAST:event_lstFoldersValueChanged

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        int[] rows = lstFolders.getSelectedIndices();
        if (rows.length == 0)
            return;
        
        int reply = JOptionPane.showConfirmDialog(this, "Are you sure you wish to remove all selected folders?", "Remove Folders", JOptionPane.YES_NO_OPTION);
        if (reply != JOptionPane.YES_OPTION)
            return;
        
        List<String> toRemove = new ArrayList<String>();
        for(int row: rows)
            toRemove.add((String)model.get(row));
        
        for(String value: toRemove)
            model.removeElement(value);
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
        int reply = chooser.showOpenDialog(this);
        if (reply != JFileChooser.APPROVE_OPTION)
            return;
        
        String path = chooser.getSelectedFile().getPath();
        
        if (!model.contains(path))
            model.addElement(path);
    }//GEN-LAST:event_btnAddActionPerformed

    private DefaultListModel model;
    
    void load() {
        List<String> currentFolders = RenamePreferences.AutomaticRenameFolders.getStringList();
        Collections.sort(currentFolders, UIUtils.LEXICAL_SORTER);
        
        model.clear();
        
        for(String folder: currentFolders)
            model.addElement(folder);
    }

    void store() {
        List<String> values = new ArrayList<String>();
        for(int row = 0; row < model.getSize(); row++)
            values.add((String)model.get(row));
        
        RenamePreferences.AutomaticRenameFolders.setStringList(values);
    }

    boolean valid() {
        return true;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnRemove;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList lstFolders;
    // End of variables declaration//GEN-END:variables
}
