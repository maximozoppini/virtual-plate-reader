/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PantallaPrueba.java
 *
 * Created on 16/09/2011, 13:51:37
 */

package com.ia.pve;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.*;
import java.awt.color.*;
import java.awt.image.*;
import java.awt.image.renderable.*;
import javax.media.jai.*;
import javax.swing.JPanel;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
/**
 *
 * @author maxi
 */
public class PantallaPrueba extends javax.swing.JFrame {

    private BufferedImage img;
    private static final int IMG_WIDTH = 600;
    private static final int IMG_HEIGHT = 600;
    private BufferedImage grayImg;
    private DefaultCategoryDataset datasetPrinc = new DefaultCategoryDataset();
    private CategoryDataset dataHor;
    private CategoryDataset dataVer;
    /** Creates new form PantallaPrueba */
    public PantallaPrueba() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Cargar Imagen");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Pasar a Escala de Gris 1");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Pasar a Escala de Gris 2");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Pasar a Escala de Gris 3");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Aplicar Sobel");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("jButton6");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(jLabel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 593, Short.MAX_VALUE)
            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 593, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3)
                    .addComponent(jButton4)
                    .addComponent(jButton5)
                    .addComponent(jButton6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
      try {
            // this is a new frame, where the picture should be shown
            //final JFrame showPictureFrame = new JFrame("Title");
            // we will put the picture into this label
            //JLabel pictureLabel = new JLabel();

            /* The following will read the image */
            // you should get your picture-path in another way. e.g. with a JFileChooser
            img = ImageIO.read(getClass().getResource("/images/patente3.jpg"));

            int type = img.getType() == 0? BufferedImage.TYPE_INT_ARGB : img.getType();

            BufferedImage resizeImageJpg = resizeImage(img, type);
            img = resizeImageJpg;
            //ImageIO.write(resizeImageJpg, "jpg", new File("c:\\image\\mkyong_jpg.jpg"));

            /* until here */

            // add the image as ImageIcon to the label
            //pictureLabel.setIcon(new ImageIcon(img));
            jLabel1.setIcon(new ImageIcon(resizeImageJpg));
            // add the label to the frame
            //showPictureFrame.add(pictureLabel);
            // pack everything (does many stuff. e.g. resizes the frame to fit the image)
            //showPictureFrame.pack();
            this.pack();
            //this is how you should open a new Frame or Dialog, but only using showPictureFrame.setVisible(true); would also work.
//            java.awt.EventQueue.invokeLater(new Runnable() {
//
//              public void run() {
//                showPictureFrame.setVisible(true);
//              }
//            });

          } catch (IOException ex) {
            System.err.println("Some IOException accured (did you set the right path?): ");
            ex.printStackTrace();
          }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        BufferedImage grayScaleImage = new BufferedImage(img.getWidth(), img.getHeight(),
        BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = grayScaleImage.getGraphics();
        g.drawImage(img, 0, 0, null);
        grayImg = grayScaleImage;
        try {
            try {
                ImageIO.write(grayImg, "jpg", new File(getClass().getResource("/images/LetrasSolas.jpg").toURI()));
                //ImageIO.write(grayImg, "jpg", new File("C:\\Users\\maxi\\Desktop\\Patente.jpg" ));
            } catch (URISyntaxException ex) {
                Logger.getLogger(PantallaPrueba.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        jLabel1.setIcon(new ImageIcon(grayScaleImage));
        //g.dispose();
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
        ColorConvertOp op = new ColorConvertOp(cs, null);
        BufferedImage image = op.filter(img, null);
        grayImg = image;
        try {
            try {
                ImageIO.write(grayImg, "jpg", new File(getClass().getResource("/images/LetrasSolas.jpg").toURI()));
            } catch (URISyntaxException ex) {
                Logger.getLogger(PantallaPrueba.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        jLabel1.setIcon(new ImageIcon(image));
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        try {
            
                RenderedImage src = JAI.create("url", getClass()
                        .getResource("/images/LetrasSolas.jpg"));

            
            // Generate a histogram.
            Histogram histogram =
                (Histogram)JAI.create("histogram", src).getProperty("histogram");

            // Get a threshold equal to the median.
            double[] threshold = histogram.getPTileThreshold(0.5);

            // Binarize the image.
            PlanarImage dst =
                JAI.create("binarize", src, new Double(threshold[0]));


            ColorModel colormodel = dst.getColorModel();
            SampleModel samplemodel = dst.getSampleModel();
            int hPlanar  = dst.getHeight();
            int a = dst.getMaxX();
            // Display the result.
            BufferedImage resultado = dst.getAsBufferedImage();
            WritableRaster r = resultado.getRaster();

//
//            int w = resultado.getWidth(null);
//            int h = resultado.getHeight(null);
//            int[] rgbs = new int[w*h];
//            resultado.getRGB(0, 0, w, h, rgbs, 0, h);
//

            int M = resultado.getWidth();
            int N = resultado.getHeight();
            double [][] horProj = new double[1][N];
            double [][] verProj = new double[1][M];

            for(int u = 0; u < M; u++){
                for(int v = 0; v< N; v++){
                    int p = resultado.getRGB(u, v);
                    horProj[0][v]+=p;
                    verProj[0][u]+=p;
                }
                //datasetPrinc.setValue(verProj[u],"X" , "x" +u);
                
            }

            dataHor =  DatasetUtilities.createCategoryDataset("Series","X", verProj);
            dataVer = DatasetUtilities.createCategoryDataset("Series","Y", horProj);
              

            jLabel1.setIcon(new ImageIcon(resultado));

        }
        catch(Exception e)
        {
            System.err.println("Some IOException accured (did you set the right path?): ");
            System.err.println(e.getMessage());
        }

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        KernelJAI sobelH = KernelJAI.GRADIENT_MASK_SOBEL_HORIZONTAL; //matriz horizontal
        KernelJAI sobelV = KernelJAI.GRADIENT_MASK_SOBEL_VERTICAL; //martriz vertical

        ParameterBlock pb = new ParameterBlock( ); //objeto que me agrupa pasos o procesos que se le van a aplicar a la imagen

        RenderedImage src = JAI.create("url", getClass()
                        .getResource("/images/LetrasSolas.jpg")); //obtengo la imagen

        // ImageMunger puts "Image" in global scope:
        pb.addSource( src ); //agrego la imagen al parameterBlock
        pb.add( sobelH ); //agrego filtro h
        pb.add( sobelV ); //agrego filtro v

        PlanarImage renderedOp = JAI.create( "gradientmagnitude", pb ); //llamo a la funcion gradientmagnitude y le paso los filtros y la imagen
        BufferedImage image = renderedOp.getAsBufferedImage(); //obtengo un bufferedImage del resultado

        jLabel1.setIcon(new ImageIcon(image));

        //-------------- Otra forma de aplicar el filtro de sobel --------------//
        // se podria utilizar esta forma para probar con el filtro de Prewitt //
//        float[] kernelMatrix = {     -1, -2, -1,
//                                         0, 0, 0,
//                                        1, 2, 1 };
//            KernelJAI kernel = new KernelJAI(3,3,kernelMatrix);
//            KernelJAI kernel1 = KernelJAI.GRADIENT_MASK_SOBEL_HORIZONTAL;
//
//            PlanarImage output = JAI.create("convolve", dst, kernel1);

    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed

        BufferedImage grafico = null;
        JFrame marco = new JFrame("cuadro");
        marco.setSize(1000, 800);
        JPanel panel = new JPanel();

        marco.add(panel);

        //int ancho = panel.getWidth() - 20;
        //int alto = panel.getHeight() - 40;


        JFreeChart chart1 = ChartFactory.createBarChart3D("Horizontal","X" , "Value", dataHor, PlotOrientation.VERTICAL, false, true, false);

        //colorearBarras(chart.getCategoryPlot());
        
        grafico = chart1.createBufferedImage(1000,700);
        panel.add(new JLabel(new ImageIcon(grafico)));
        marco.setVisible(true);

        //----------------------------------------------------------------------------//

        BufferedImage grafico2 = null;
        JFrame marco2 = new JFrame("cuadro");
        marco2.setSize(1000, 800);
        JPanel panel2 = new JPanel();

        marco2.add(panel2);

        //int ancho = panel.getWi   dth() - 20;
        //int alto = panel.getHeight() - 40;


        JFreeChart chart2 = ChartFactory.createBarChart("prueba","Y" , "Value", dataVer, PlotOrientation.HORIZONTAL, false, true, false);

        //colorearBarras(chart.getCategoryPlot());

        grafico2 = chart2.createBufferedImage(1000,700);
        panel2.add(new JLabel(new ImageIcon(grafico2)));
        marco2.setVisible(true);
    }//GEN-LAST:event_jButton6ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PantallaPrueba().setVisible(true);
            }
        });
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int type){
	BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
	Graphics2D g = resizedImage.createGraphics();
        
	g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
	g.dispose();

	return resizedImage;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

}
