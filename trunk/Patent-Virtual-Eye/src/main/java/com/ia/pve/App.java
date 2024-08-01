package com.ia.pve;

import com.ia.pve.front_end.MainWindow;
import com.ia.pve.front_end.Splashscreen;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Thread() {

                    public void run() {
                        try {
                            System.out.println("Initializing Virtual Patent Eye - Alpha Version");
                            Splashscreen splash = new Splashscreen();
                            System.out.println("Showing Splash");
                            splash.setVisible(true);
                            //Thread.sleep(500);
                            System.out.println("Creating Main Window");
                            MainWindow main = new MainWindow();
                            System.out.println("Showing Main Window");                            
                            main.setVisible(true);
                            splash.dispose();
                        } catch (Exception ex) {
                            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                            ex.printStackTrace();
                        }
                    }
                }.start();
            }
        });

    }
}
