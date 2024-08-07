/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Splashscreen.java
 *
 * Created on 16/09/2011, 14:20:26
 */
package com.ia.pve.front_end;

/**
 *
 * @author MotorolaUTN
 */
public class Splashscreen extends javax.swing.JDialog {

    /** Creates new form Splashscreen */
    public Splashscreen() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane = new javax.swing.JLayeredPane();
        lblVersionInfo = new javax.swing.JLabel();
        lblImage = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setResizable(false);
        setUndecorated(true);

        lblVersionInfo.setForeground(new java.awt.Color(255, 255, 255));
        lblVersionInfo.setText("Beta Version");
        lblVersionInfo.setBounds(10, 220, 370, -1);
        jLayeredPane.add(lblVersionInfo, javax.swing.JLayeredPane.DEFAULT_LAYER);

        lblImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Splashscreen.png"))); // NOI18N
        lblImage.setBounds(0, 0, -1, -1);
        jLayeredPane.add(lblImage, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-400)/2, (screenSize.height-250)/2, 400, 250);
    }// </editor-fold>//GEN-END:initComponents
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane jLayeredPane;
    private javax.swing.JLabel lblImage;
    private javax.swing.JLabel lblVersionInfo;
    // End of variables declaration//GEN-END:variables
}
