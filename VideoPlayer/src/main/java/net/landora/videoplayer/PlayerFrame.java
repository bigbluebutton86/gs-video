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
package net.landora.videoplayer;

import net.landora.videoplayer.menu.ui.PlayerMenu;



/**
 *
 * @author bdickie
 */
public class PlayerFrame extends javax.swing.JFrame {

    /**
     * Creates new form PlayerFrame
     */
    public PlayerFrame() {
        initComponents();
        
    }

    public PlayerMenu getMenuComponent() {
        return menu;
    }
    
    public void prepareToDisplay() {
        
        pnlMain.setBackgroundPainter(menu.getSkin().getScrollPainter());
        menu.setBaseMenu(VideoPlayerAddon.getInstance().getTopLevelMenu());
        
        pack();
        
        pnlBackground.setBackgroundPainter(menu.getSkin().getWindowPainter());
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menu = new net.landora.videoplayer.menu.ui.PlayerMenu();
        pnlBackground = new org.jdesktop.swingx.JXPanel();
        pnlSidePanel = new javax.swing.JPanel();
        pnlMain = new org.jdesktop.swingx.JXPanel();
        vpMenu = new javax.swing.JViewport();
        pnlStatus = new org.jdesktop.swingx.JXPanel();
        jLabel1 = new javax.swing.JLabel();

        javax.swing.GroupLayout menuLayout = new javax.swing.GroupLayout(menu);
        menu.setLayout(menuLayout);
        menuLayout.setHorizontalGroup(
            menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 669, Short.MAX_VALUE)
        );
        menuLayout.setVerticalGroup(
            menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("GS Video Player");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        pnlBackground.setBackground(new java.awt.Color(153, 153, 153));
        pnlBackground.setLayout(new java.awt.BorderLayout());

        pnlSidePanel.setOpaque(false);

        javax.swing.GroupLayout pnlSidePanelLayout = new javax.swing.GroupLayout(pnlSidePanel);
        pnlSidePanel.setLayout(pnlSidePanelLayout);
        pnlSidePanelLayout.setHorizontalGroup(
            pnlSidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        pnlSidePanelLayout.setVerticalGroup(
            pnlSidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 477, Short.MAX_VALUE)
        );

        pnlBackground.add(pnlSidePanel, java.awt.BorderLayout.LINE_END);

        pnlMain.setOpaque(false);
        pnlMain.setLayout(new java.awt.BorderLayout());

        vpMenu.setOpaque(false);
        vpMenu.setView(menu);
        pnlMain.add(vpMenu, java.awt.BorderLayout.CENTER);

        pnlBackground.add(pnlMain, java.awt.BorderLayout.CENTER);

        pnlStatus.setOpaque(false);
        pnlStatus.setLayout(new java.awt.BorderLayout());

        jLabel1.setText("jLabel1");
        pnlStatus.add(jLabel1, java.awt.BorderLayout.CENTER);

        pnlBackground.add(pnlStatus, java.awt.BorderLayout.NORTH);

        getContentPane().add(pnlBackground, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        System.exit(0);
    }//GEN-LAST:event_formWindowClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private net.landora.videoplayer.menu.ui.PlayerMenu menu;
    private org.jdesktop.swingx.JXPanel pnlBackground;
    private org.jdesktop.swingx.JXPanel pnlMain;
    private javax.swing.JPanel pnlSidePanel;
    private org.jdesktop.swingx.JXPanel pnlStatus;
    private javax.swing.JViewport vpMenu;
    // End of variables declaration//GEN-END:variables
}
