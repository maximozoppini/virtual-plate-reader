/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ia.back_end;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.ColorModel;
import java.awt.image.RasterFormatException;
import java.awt.image.RenderedImage;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;
import java.awt.image.renderable.ParameterBlock;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.media.jai.Histogram;
import javax.media.jai.JAI;
import javax.media.jai.KernelJAI;
import javax.media.jai.PlanarImage;
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;

/**
 *
 * @author santi
 */
public class ImageProcesor {
    
    private BufferedImage origImg, grayImg, lineImg;
    private CategoryDataset dataHorizontal, dataVertical;
    private int gWidth, gHeight;
    private double errorLevel;
    private double[][] horProj, verProj, horProjSobel, verProjSobel;
    private int[][] points = {{-1, -1},
            {-1, -1},
            {-1, -1},
            {-1, -1},
            {-1, -1},
            {-1, -1},
            {-1, -1}};//Matriz para guardar los puntos de las letras;
    
    public ImageProcesor(BufferedImage image, int width, int height) {
        gWidth = gHeight = 600;
        errorLevel = 0.09d;
        if (width > 0) {
            
            if (height > 0) {
                origImg = resizeImage(image,
                        image.getType(), width, height);
                lineImg = resizeImage(image,
                        image.getType(), width, height);
            } else {
                origImg = resizeImage(image,
                        image.getType(), width);
                lineImg = resizeImage(image,
                        image.getType(), width);
            }
        } else {
            origImg = image;
            lineImg = image;
        }
        
    }
    
    public ImageProcesor(BufferedImage image, int width) {
        this(image, width, -1);
    }
    
    public ImageProcesor(BufferedImage image) {
        this(image, -1);
    }
    
    public ImageProcesor(File file) throws IOException {
        this(ImageIO.read(file));
    }
    
    public ImageProcesor(File file, int width) throws IOException {
        this(ImageIO.read(file), width);
    }
    
    public ImageProcesor(File file, int width, int height) throws IOException {
        this(ImageIO.read(file), width, height);
    }
    
    public BufferedImage generateGrayImage() {
        BufferedImage grayScaleImage = new BufferedImage(origImg.getWidth(), origImg.getHeight(),
                BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = grayScaleImage.getGraphics();
        g.drawImage(origImg, 0, 0, null);
        grayImg = grayScaleImage;
        return grayScaleImage;
    }
    
    public BufferedImage generateAlternativeGrayImage() {
        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
        ColorConvertOp op = new ColorConvertOp(cs, null);
        BufferedImage image = op.filter(origImg, null);
        
        BufferedImage grayScaleImage = new BufferedImage(image.getWidth(), image.getHeight(),
                BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = grayScaleImage.getGraphics();
        g.drawImage(image, 0, 0, null);
        
        
        grayImg = grayScaleImage;
        return grayScaleImage;
    }
    
    public static BufferedImage resizeImage(BufferedImage originalImage, int type, int width) {
        
        int newHeight = ((width*originalImage.getHeight())/originalImage.getWidth());
        
        return resizeImage(originalImage, type, width, newHeight);
    }
    
    private static BufferedImage resizeImage(BufferedImage originalImage, int type, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D g = resizedImage.createGraphics();
        
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        
        return resizedImage;
    }
    
    public BufferedImage generateBinaryImage() {
        BufferedImage img = null;
        try {
            //RenderedImage src = JAI.create("fileload", origImg);
            
            // Generate a histogram.
            Histogram histogram =
                    (Histogram) JAI.create("histogram", grayImg).getProperty("histogram");

            // Get a threshold equal to the median.
            double[] threshold = histogram.getPTileThreshold(0.70);

            // Binarize the image.
            PlanarImage dst =
                    JAI.create("binarize", grayImg, new Double(threshold[0]));
            
            ColorModel colormodel = dst.getColorModel();
            SampleModel samplemodel = dst.getSampleModel();
            int hPlanar = dst.getHeight();
            int a = dst.getMaxX();
            // Display the result.
            BufferedImage resultado = dst.getAsBufferedImage();
            WritableRaster r = resultado.getRaster();
            
            int M = resultado.getWidth();
            int N = resultado.getHeight();
            horProj = new double[1][N];
            verProj = new double[1][M];
            
            for (int u = 0; u < M; u++) {
                for (int v = 0; v < N; v++) {
                    int p = resultado.getRGB(u, v);
                    horProj[0][v] += p;
                    verProj[0][u] += p;
                }
                //datasetPrinc.setValue(verProj[u],"X" , "x" +u);

            }
            
            getMaxGraphicPoints(verProj);
            
            dataHorizontal = DatasetUtilities.createCategoryDataset("Series", "X", verProj);
            dataVertical = DatasetUtilities.createCategoryDataset("Series", "Y", horProj);


            // Display the result.
            img = dst.getAsBufferedImage();
        } catch (Exception e) {
            System.err.println("Some IOException accured (did you set the right path?): ");
            e.printStackTrace();
        }
        return img;
    }
    
    public BufferedImage generateSobel() {
        KernelJAI sobelH = KernelJAI.GRADIENT_MASK_SOBEL_HORIZONTAL; //matriz horizontal
        KernelJAI sobelV = KernelJAI.GRADIENT_MASK_SOBEL_VERTICAL; //martriz vertical

        ParameterBlock pb = new ParameterBlock(); //objeto que me agrupa pasos o procesos que se le van a aplicar a la imagen

        RenderedImage src = grayImg;

        // ImageMunger puts "Image" in global scope:
        pb.addSource(src); //agrego la imagen al parameterBlock
        pb.add(sobelH); //agrego filtro h
        pb.add(sobelV); //agrego filtro v

        PlanarImage renderedOp = JAI.create("gradientmagnitude", pb); //llamo a la funcion gradientmagnitude y le paso los filtros y la imagen

        // Display the result.
        BufferedImage resultado = renderedOp.getAsBufferedImage();
        WritableRaster r = resultado.getRaster();
        
        int M = resultado.getWidth();
        int N = resultado.getHeight();
        horProjSobel = new double[1][N];
        verProjSobel = new double[1][M];
        
        for (int u = 0; u < M; u++) {
            for (int v = 0; v < N; v++) {
                int p = resultado.getRGB(u, v);
                horProjSobel[0][v] += p;
                verProjSobel[0][u] += p;
            }
        }        
        return renderedOp.getAsBufferedImage(); //obtengo un bufferedImage del resultado
    }
    
    public BufferedImage generateImageWithDivisionbars() {
        BufferedImage imgWithBars = lineImg.getSubimage(0, 0,
                lineImg.getWidth(), lineImg.getHeight());
        
        ArrayList<Integer> xBars = getMaxGraphicPoints(verProj);
        ArrayList<Integer> desv = new ArrayList<Integer>();
        
        for ( int i = 0; i < xBars.size()-1; i++)
        {
            desv.add(xBars.get(i+1)-xBars.get(i));
        }
        
        double media = calculateMedia(desv)*1.7;
        int max = Integer.MIN_VALUE;
        for (Integer d : desv) {
            if (d > max) max = d;
        }
        max = (int)(max*0.75);
      
        int letterNumber = 0;
        for ( int i = 0; i < xBars.size()-1; i++)
        {
            if ((xBars.get(i+1)-xBars.get(i))>max && letterNumber < 7)
            {
                points[letterNumber][0] = xBars.get(i);
                points[letterNumber][1] = xBars.get(i+1);
                letterNumber++;
            }
        }
        
        for (Integer col : xBars) {
                for (int y = 0; y < imgWithBars.getHeight(); y++) {
                    imgWithBars.setRGB(col, y, Color.blue.getRGB());
                }
            }
        
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 2; j++) {
                for (int y = 0; y < imgWithBars.getHeight(); y++) {
                    if (points[i][j] > -1)
                    imgWithBars.setRGB(points[i][j], y, Color.red.getRGB());
                }
            }
        }
        return imgWithBars;
    }

    private double calculateMedia(ArrayList<Integer> desv) {
        double sum = 0;
        for (Integer d : desv)
        {
            sum += d;
        }
        return sum/desv.size();
    }
    
    public ArrayList<Integer> getMaxGraphicPoints(double[][] points) {
        ArrayList<Integer> maxPoints = new ArrayList<Integer>();
        
        double limit = getLimitValue(points);
        
        
        double maxPoint;
        maxPoint = Integer.MAX_VALUE;
        for (int i = 1; i < points[0].length; i++)
        {
            if (points[0][i] < limit)//y esta por encima del 80%
                {
                    maxPoints.add(i);
                }
        }
//        for (int i = 1; i < points[0].length; i++) {
//            if (maxPoint > points[0][i]) {
//                maxPoint = points[0][i];
//            } else if (maxPoint < points[0][i]) {
//                //Es un maximo       
//                if (points[0][i] < limit)//y esta por encima del 80%
//                {
//                    maxPoints.add(i);
//                }
//                
//                maxPoint = Integer.MAX_VALUE;
//            }
//        }
        System.out.println("Max Points: " + maxPoints);
        return maxPoints;
    }
    
    private double getLimitValue(double[][] points) {
        double minValue = Integer.MAX_VALUE;
        
        for (int i = 1; i < points[0].length; i++) {
            if (minValue > points[0][i]) {
                minValue = points[0][i];
            }
        }
        
        return minValue * (1 - getErrorLevel());
    }
    
    public BufferedImage getHorizontalGraphic() {
        JFreeChart chart1 = ChartFactory.createBarChart3D("Horizontal", "X", "Value",
                DatasetUtilities.createCategoryDataset("Series", "Y", horProj), PlotOrientation.HORIZONTAL, false, true, false);

        //colorearBarras(chart.getCategoryPlot());

        return chart1.createBufferedImage(gWidth, gHeight);
    }
    
    public BufferedImage getVerticalGraphic() {
        JFreeChart chart1 = ChartFactory.createBarChart3D("Vertical", "Y", "Value",
                DatasetUtilities.createCategoryDataset("Series", "X", verProj), PlotOrientation.VERTICAL, false, true, false);

        //colorearBarras(chart.getCategoryPlot());

        return chart1.createBufferedImage(gWidth, gHeight);
    }
    
    public BufferedImage getHorizontalGraphicFromSobel() {
        JFreeChart chart1 = ChartFactory.createBarChart3D("Horizontal", "X",
                "Value", DatasetUtilities.createCategoryDataset("Series", "X", horProjSobel), PlotOrientation.HORIZONTAL,
                false, true, false);

        //colorearBarras(chart.getCategoryPlot());

        return chart1.createBufferedImage(gWidth, gHeight);
    }
    
    public BufferedImage getVerticalGraphicFromSobel() {
        JFreeChart chart1 = ChartFactory.createBarChart3D("Vertical", "Y",
                "Value", DatasetUtilities.createCategoryDataset("Series", "Y", verProjSobel), PlotOrientation.VERTICAL,
                false, true, false);

        //colorearBarras(chart.getCategoryPlot());

        return chart1.createBufferedImage(gWidth, gHeight);
    }

    /**
     * @return the origImg
     */
    public BufferedImage getOrigImg() {
        return origImg;
    }

    /**
     * @return the errorLevel
     */
    public double getErrorLevel() {
        return errorLevel;
    }

    /**
     * @param errorLevel the errorLevel to set
     */
    public void setErrorLevel(double errorLevel) {
        if (errorLevel < 0d || errorLevel > 1d) {
            throw new NumberFormatException("The error level must be between 0 and 1.");
        }
        this.errorLevel = errorLevel;
    }
    
    public BufferedImage getSubimage(int index)
    {
        if (index < 0 || index > 5)
            return null;
        BufferedImage source = generateBinaryImage();
        try
        {
        return source.getSubimage(points[index][0], 0,
                points[index][1]-points[index][0], source.getHeight());
        }
        catch (RasterFormatException e)
        {
            System.out.println("[E] Not enough simbols detected: "+ e.getMessage());
            return null;
        }
    }

    /**
     * Take a Buffered Image, and divide in a 8x14 matrix.
     * Calculate the percentage of white pixels in each matrix cell,
     * and fill a double matrix of the same size, with 1 for white
     * cells, and 0 for black cells.
     * @param source Buffered image to divide and analize
     * @return a Matrix Object with the patern of the image.
     */
    public Matrix getImageMatrix(BufferedImage source)
    {        
        int matrixWidth = 8;
        int matrixHeight = 14;
        
        if (null == source) return new Matrix(new double[matrixWidth][matrixHeight]);
        
        int cellHeight = (int) Math.ceil(source.getHeight()/matrixHeight);
        int cellWidth = (int) Math.ceil(source.getWidth()/matrixWidth);
        
        //BufferedImage grilla[][] = new BufferedImage[matrixWidth][matrixHeight];
        double valores [][] = new double[matrixWidth][matrixHeight];
        int limit = (int)((cellWidth*cellHeight)*0.5);
        try
        {
            for (int a = 0; a < matrixWidth; a++) {
                for (int h = 0; h < matrixHeight; h++) {
                    BufferedImage aux =  source.getSubimage(a*cellWidth, h*cellHeight, cellWidth, cellHeight);
                    //grilla [a][h] = aux;
                    for (int u = 0; u < aux.getWidth(); u++) {
                        for (int v = 0; v < aux.getHeight(); v++) {
                            if( aux.getRGB(u, v) == -1)//-1 es el blanco y -167... es el negro
                                valores[a][h] += 1;
                        }
                    }
                    
                }
            }

            double[] patern = new double[matrixHeight*matrixWidth];
            int x = 0;
            
            for (int a = 0; a < matrixWidth; a++) {
                for (int h = 0; h < matrixHeight; h++) {
                    if(valores[a][h] > limit){
                        patern[x++] = 1;
                        valores[a][h] = 1;
                    }                       
                    else
                    {
                        patern[x++] = 0;
                        valores[a][h] = 0;
                    }
                }
            }
            Matrix matrix = new Matrix(patern);

            Matrix aux = new Matrix(valores);
            System.out.println("Matrix Detected:" + aux.toString());
            
            return matrix;


        }
        catch(Exception e)
        {
            System.err.println("Error al cargar las celdas de la grilla ");
            e.printStackTrace();
            return null;
        }
    }


}
