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

package net.landora.video.filerenaming;

import javax.swing.DefaultComboBoxModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import net.landora.video.info.VideoMetadata;
import net.landora.video.ui.ConfigurationPanel;

public final class FileRenamingConfigPanel extends ConfigurationPanel {

    private RenameScriptManager mng = RenameScriptManager.getInstance();

    public FileRenamingConfigPanel() {
        initComponents();
        
        txtFolderScript.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                FileRenamingConfigPanel.this.changed();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                FileRenamingConfigPanel.this.changed();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                FileRenamingConfigPanel.this.changed();
            }
        });
        
        txtFileScript.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                FileRenamingConfigPanel.this.changed();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                FileRenamingConfigPanel.this.changed();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                FileRenamingConfigPanel.this.changed();
            }
        });
        
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        model.addElement(new TestMovieMetadata(false));
        model.addElement(new TestSeriesMetadata(false));
        model.addElement(new TestMultiSeasonSeriesMetadata(false));
        model.addElement(new TestMovieMetadata(true));
        model.addElement(new TestSeriesMetadata(true));
        model.addElement(new TestMultiSeasonSeriesMetadata(true));
        cboTestProfile.setModel(model);
        
        valid();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtFolderScript = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtFileScript = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        cboTestProfile = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtFolderResult = new javax.swing.JTextField();
        txtFileResult = new javax.swing.JTextField();

        setPanelName("File Renaming");
        setLayout(new java.awt.BorderLayout());

        jPanel3.setLayout(new java.awt.GridLayout(0, 1, 0, 5));

        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(jLabel1.getFont().deriveFont(jLabel1.getFont().getStyle() | java.awt.Font.BOLD));
        jLabel1.setText("Folder Rename"); // NOI18N
        jPanel1.add(jLabel1, java.awt.BorderLayout.PAGE_START);

        txtFolderScript.setColumns(80);
        txtFolderScript.setFont(new java.awt.Font("Monospaced", 0, 10));
        txtFolderScript.setRows(10);
        txtFolderScript.setTabSize(3);
        jScrollPane1.setViewportView(txtFolderScript);

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel1);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jLabel2.setFont(jLabel2.getFont().deriveFont(jLabel2.getFont().getStyle() | java.awt.Font.BOLD));
        jLabel2.setText("File Rename"); // NOI18N
        jPanel2.add(jLabel2, java.awt.BorderLayout.PAGE_START);

        txtFileScript.setColumns(80);
        txtFileScript.setFont(new java.awt.Font("Monospaced", 0, 10));
        txtFileScript.setRows(10);
        txtFileScript.setTabSize(3);
        jScrollPane2.setViewportView(txtFileScript);

        jPanel2.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel2);

        add(jPanel3, java.awt.BorderLayout.CENTER);

        jLabel3.setText("Test Profile"); // NOI18N

        cboTestProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTestProfileActionPerformed(evt);
            }
        });

        jLabel4.setText("Folder Test Result"); // NOI18N

        jLabel5.setText("File Test Result"); // NOI18N

        txtFolderResult.setEditable(false);

        txtFileResult.setEditable(false);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboTestProfile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFileResult, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                    .addComponent(txtFolderResult, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cboTestProfile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtFolderResult, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtFileResult, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        add(jPanel4, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

    private void cboTestProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTestProfileActionPerformed
        changed();
    }//GEN-LAST:event_cboTestProfileActionPerformed

    @Override
    public void load() {
        txtFolderScript.setText(mng.getFolderRenameScript());
        txtFileScript.setText(mng.getFileRenameScript());
    }

    public void store() {
        mng.setFileRenameScript(txtFileScript.getText());
        mng.setFolderRenameScript(txtFolderScript.getText());
        mng.loadCurrentScript();
    }

    @Override
    public boolean valid() {
//        if (mng.createRenamingScript(txtFolderScript.getText(), txtFileScript.getText(), false) == null)
//            return false;
        
        return testData();
    }

    @Override
    protected void changed() {
        super.changed();
        
        testData();
    }
    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cboTestProfile;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField txtFileResult;
    private javax.swing.JTextArea txtFileScript;
    private javax.swing.JTextField txtFolderResult;
    private javax.swing.JTextArea txtFolderScript;
    // End of variables declaration//GEN-END:variables


    private boolean testData() {
        RenamingScript renamingScript = mng.createRenamingScript(txtFolderScript.getText(), txtFileScript.getText(), false);
        
        VideoMetadata md = (VideoMetadata)cboTestProfile.getSelectedItem();
        
        boolean eitherFailed = false;
        boolean failed = true;
        try {
            if (renamingScript != null) {
                String value = renamingScript.findFolderName(md);
                txtFolderResult.setText(value == null ? "**No Change**" : value);
                failed = false;
            }
        } catch (Exception e) {
        }
        if (failed) {
            txtFolderResult.setText("*** Test Failed ***");
            eitherFailed = true;
        }
            
        failed = true;
        try {
            if (renamingScript != null) {
                String value = renamingScript.findFilename(md);
                txtFileResult.setText(value == null ? "**No Change**" : value);
                failed = false;
            }
        } catch (Exception e) {
        }
        if (failed) {
            txtFileResult.setText("*** Test Failed ***");
            eitherFailed = true;
        }
        
        return !eitherFailed;
    }
}
