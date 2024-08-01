/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MainWindow.java
 *
 * Created on 16/09/2011, 14:19:51
 */
package com.ia.pve.front_end;

import com.ia.back_end.ImageProcesor;
import com.ia.back_end.Matrix;
import com.ia.back_end.Network;
import com.ia.back_end.Patrones;
import java.awt.CardLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author MotorolaUTN
 */
public class MainWindow extends javax.swing.JFrame {

    private BufferedImage grayImg, binaryImg, sobelImg, divBarsImg, divImg,
            origImg;//Imagen en sus diferentes etapas
    private BufferedImage subImg[]; //imagen dividida
    private com.ia.back_end.ImageProcesor img;//Motor de proceso de imagenes
    private String fileName;//File de la imagen
    private JFileChooser fileChooser; //FileChooser para buscar el archivo que se quiere procesar
    
/******************************************************************************
 *   Red Neuronal para los Numeros
 ******************************************************************************/
    int ID = 0;
    double matPatronesNum[][] = new double[10][112];//Array temporal con los patrones
    String nomPatronesNum[] = new String[10];//Array String con los nombres de los patrones
    double matObjetivosNum[][] = new double[10][10];//Array con los valores objetivo
    Network miRedJANum = null;
    Matrix entradaNum;
    //MATRICES (OBJETOS)
    Matrix patronesMNum;
    Matrix objetivosMNum;
    
/******************************************************************************
 *   Red Neuronal para las Letras
 ******************************************************************************/
    double matPatronesLet[][] = new double[21][112];//Array temporal con los patrones
    String nomPatronesLet[] = new String[21];//Array String con los nombres de los patrones
    double matObjetivosLet[][] = new double[21][21];//Array con los valores objetivo
    Network miRedJALet = null;
    Matrix entradaLet;
    //MATRICES (OBJETOS)
    Matrix patronesMLet;
    Matrix objetivosMLet;
    

    /** Creates new form MainWindow */
    public MainWindow() {
        this.setIconImage(new ImageIcon(getClass().getResource("/icons/Icon_Eye_64.png")).getImage());
        initComponents();
        img = null;
        fileChooser = new JFileChooser();

/******************************************************************************
 *   Red Neuronal para los Numeros
 ******************************************************************************/
        
        int pos = 0;//variable para inicializar la matriz objetivo
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (j == pos) {//Genera una matriz identidad
                    matObjetivosNum[i][j] = 1;
                } else {
                    matObjetivosNum[i][j] = 0;
                }
            }
            pos++;
        }
        
            matPatronesNum[0] = Patrones.patron0;
            nomPatronesNum[0] = "0";
            matPatronesNum[1] = Patrones.patron1;
            nomPatronesNum[1] = "1";
            matPatronesNum[2] = Patrones.patron2;
            nomPatronesNum[2] = "2";
            matPatronesNum[3] = Patrones.patron3;
            nomPatronesNum[3] = "3";
            matPatronesNum[4] = Patrones.patron4;
            nomPatronesNum[4] = "4";
            matPatronesNum[5] = Patrones.patron5;
            nomPatronesNum[5] = "5";
            matPatronesNum[6] = Patrones.patron6;
            nomPatronesNum[6] = "6";
            matPatronesNum[7] = Patrones.patron7;
            nomPatronesNum[7] = "7";
            matPatronesNum[8] = Patrones.patron8;
            nomPatronesNum[8] = "8";
            matPatronesNum[9] = Patrones.patron9;
            nomPatronesNum[9] = "9";
        
            System.out.println("Initial Paterns:" + matPatronesNum);

        patronesMNum = new Matrix(matPatronesNum);//Se inicializa la matriz de patrones
        patronesMNum = Matrix.transponer(patronesMNum);//Se transpone => 112x10
        objetivosMNum = new Matrix(matObjetivosNum);//Se inicializa la matriz objetivos
        objetivosMNum = Matrix.transponer(objetivosMNum);//Se transpone => 10x10

        miRedJANum = new Network(patronesMNum, objetivosMNum);


        System.out.println("\nMatriz de objetivos [" + objetivosMNum.getFilString() + "x" + objetivosMNum.getColString() + "]:\n");
        System.out.println(objetivosMNum.toPrettyString());
        
        System.out.print("Iniciando entrenamiento de la red neurolan...");
        String res=Network.trainNetLog(miRedJANum, 0.4d,0.0009d, 1000);
        System.out.println("Finalizado!");
        System.out.println(res);
        
/******************************************************************************
 *   Red Neuronal para las Letras
 ******************************************************************************/
        pos = 0;//variable para inicializar la matriz objetivo
        for (int i = 0; i < 21; i++) {
            for (int j = 0; j < 21; j++) {
                if (j == pos) {//Genera una matriz identidad
                    matObjetivosLet[i][j] = 1;
                } else {
                    matObjetivosLet[i][j] = 0;
                }
            }
            pos++;
        }
        
            matPatronesLet[0] = Patrones.patronE;
            nomPatronesLet[0] = "E";
            matPatronesLet[1] = Patrones.patronL;
            nomPatronesLet[1] = "L";
            matPatronesLet[2] = Patrones.patronV;
            nomPatronesLet[2] = "V";
            matPatronesLet[3] = Patrones.patronJ;
            nomPatronesLet[3] = "J";
            matPatronesLet[4] = Patrones.patronH;
            nomPatronesLet[4] = "H";
            matPatronesLet[5] = Patrones.patronT;
            nomPatronesLet[5] = "T";
            matPatronesLet[6] = Patrones.patronW;
            nomPatronesLet[6] = "W";
            matPatronesLet[7] = Patrones.patronF;
            nomPatronesLet[7] = "F";
            matPatronesLet[8] = Patrones.patronK;
            nomPatronesLet[8] = "K";
            matPatronesLet[9] = Patrones.patronP;
            nomPatronesLet[9] = "P";
            matPatronesLet[10] = Patrones.patronI;
            nomPatronesLet[10] = "I";
            matPatronesLet[11] = Patrones.patronX;
            nomPatronesLet[11] = "X";
            matPatronesLet[12] = Patrones.patronB;
            nomPatronesLet[12] = "B";
            matPatronesLet[13] = Patrones.patronY;
            nomPatronesLet[13] = "Y";
            matPatronesLet[14] = Patrones.patronD;
            nomPatronesLet[14] = "D";
            matPatronesLet[15] = Patrones.patronR;
            nomPatronesLet[15] = "R";
            matPatronesLet[16] = Patrones.patronA;
            nomPatronesLet[16] = "A";
            matPatronesLet[17] = Patrones.patronM;
            nomPatronesLet[17] = "M";
            matPatronesLet[18] = Patrones.patronG;
            nomPatronesLet[18] = "G";
            matPatronesLet[19] = Patrones.patronS;
            nomPatronesLet[19] = "S";
            matPatronesLet[20] = Patrones.patronO;
            nomPatronesLet[20] = "O";
        
            System.out.println("Initial Paterns:" + matPatronesLet);

        patronesMLet = new Matrix(matPatronesLet);//Se inicializa la matriz de patrones
        patronesMLet = Matrix.transponer(patronesMLet);//Se transpone => 112x21
        objetivosMLet = new Matrix(matObjetivosLet);//Se inicializa la matriz objetivos
        objetivosMLet = Matrix.transponer(objetivosMLet);//Se transpone => 21x21

        miRedJALet = new Network(patronesMLet, objetivosMLet);


        System.out.println("\nMatriz de objetivos [" + objetivosMLet.getFilString() + "x" + objetivosMLet.getColString() + "]:\n");
        System.out.println(objetivosMLet.toPrettyString());
        
        System.out.print("Iniciando entrenamiento de la red neurolan...");
        res=Network.trainNetLog(miRedJALet, 0.37d,0.00005d, 2000);
        System.out.println("Finalizado!");
        System.out.println(res);
        
    }

    private void enableButtons() {
        btnBW.setEnabled(true);
        btnGray.setEnabled(true);
        btnOriginal.setEnabled(true);
        btnGraphics.setEnabled(true);
        btnSobel.setEnabled(true);
        btnDivBars.setEnabled(true);
        btnDivision.setEnabled(true);
        btnLoadImage.setEnabled(true);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        btnLoadImage = new javax.swing.JButton();
        jToolBar = new javax.swing.JToolBar();
        btnOriginal = new javax.swing.JToggleButton();
        btnGray = new javax.swing.JToggleButton();
        btnBW = new javax.swing.JToggleButton();
        btnSobel = new javax.swing.JToggleButton();
        btnDivBars = new javax.swing.JToggleButton();
        btnDivision = new javax.swing.JToggleButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btnGraphics = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        statusPanel = new javax.swing.JPanel();
        lblStatus = new javax.swing.JLabel();
        lblPatent = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        FramePanel = new javax.swing.JPanel();
        simpleImagePanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lblImage = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        imagesPanel = new javax.swing.JPanel();
        lblImage1 = new javax.swing.JLabel();
        lblImage2 = new javax.swing.JLabel();
        lblImage3 = new javax.swing.JLabel();
        lblImage4 = new javax.swing.JLabel();
        lblImage5 = new javax.swing.JLabel();
        lblImage6 = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Virtual Patent Eye - Alpha");

        btnLoadImage.setMnemonic('p');
        btnLoadImage.setText("Cargar Imagen...");
        btnLoadImage.setToolTipText("Procesar imagen");
        btnLoadImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoadImageActionPerformed(evt);
            }
        });

        jToolBar.setFloatable(false);
        jToolBar.setRollover(true);

        buttonGroup.add(btnOriginal);
        btnOriginal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Icon_Type_Color.png"))); // NOI18N
        btnOriginal.setToolTipText("Imagen Original");
        btnOriginal.setEnabled(false);
        btnOriginal.setFocusable(false);
        btnOriginal.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnOriginal.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnOriginal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOriginalActionPerformed(evt);
            }
        });
        jToolBar.add(btnOriginal);

        buttonGroup.add(btnGray);
        btnGray.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Icon_Type_Gray.png"))); // NOI18N
        btnGray.setToolTipText("Imagen en Blanco y Negro");
        btnGray.setEnabled(false);
        btnGray.setFocusable(false);
        btnGray.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGray.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGray.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGrayActionPerformed(evt);
            }
        });
        jToolBar.add(btnGray);

        buttonGroup.add(btnBW);
        btnBW.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Icon_Type_BW.png"))); // NOI18N
        btnBW.setToolTipText("Imagen Binarizada");
        btnBW.setEnabled(false);
        btnBW.setFocusable(false);
        btnBW.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnBW.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnBW.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBWActionPerformed(evt);
            }
        });
        jToolBar.add(btnBW);

        buttonGroup.add(btnSobel);
        btnSobel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Icon_Type_Sobel.png"))); // NOI18N
        btnSobel.setToolTipText("Imagen con Sobel");
        btnSobel.setEnabled(false);
        btnSobel.setFocusable(false);
        btnSobel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSobel.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSobel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSobelActionPerformed(evt);
            }
        });
        jToolBar.add(btnSobel);

        buttonGroup.add(btnDivBars);
        btnDivBars.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Icon_Type_DivBars.png"))); // NOI18N
        btnDivBars.setToolTipText("Mostrar Lineas de División");
        btnDivBars.setEnabled(false);
        btnDivBars.setFocusable(false);
        btnDivBars.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDivBars.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDivBars.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDivBarsActionPerformed(evt);
            }
        });
        jToolBar.add(btnDivBars);

        buttonGroup.add(btnDivision);
        btnDivision.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Icon_Type_Divided.png"))); // NOI18N
        btnDivision.setToolTipText("Imagen Original Dividida");
        btnDivision.setEnabled(false);
        btnDivision.setFocusable(false);
        btnDivision.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDivision.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDivision.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDivisionActionPerformed(evt);
            }
        });
        jToolBar.add(btnDivision);
        jToolBar.add(jSeparator2);

        btnGraphics.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Icon_Type_Graph.png"))); // NOI18N
        btnGraphics.setMnemonic('g');
        btnGraphics.setToolTipText("Graphicos de la Imagen");
        btnGraphics.setEnabled(false);
        btnGraphics.setFocusable(false);
        btnGraphics.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGraphics.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGraphics.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGraphicsActionPerformed(evt);
            }
        });
        jToolBar.add(btnGraphics);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        statusPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblStatus.setText("Buffer Vacio");

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblStatus)
                .addContainerGap(947, Short.MAX_VALUE))
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblStatus)
        );

        lblPatent.setFont(new java.awt.Font("Tahoma", 1, 24));
        lblPatent.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPatent.setText("[??? ???]");

        jLabel2.setText("Patente Reconocida:");

        FramePanel.setLayout(new java.awt.CardLayout());

        lblImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblImage.setText("[No Image]");
        lblImage.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jScrollPane1.setViewportView(lblImage);

        javax.swing.GroupLayout simpleImagePanelLayout = new javax.swing.GroupLayout(simpleImagePanel);
        simpleImagePanel.setLayout(simpleImagePanelLayout);
        simpleImagePanelLayout.setHorizontalGroup(
            simpleImagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 867, Short.MAX_VALUE)
        );
        simpleImagePanelLayout.setVerticalGroup(
            simpleImagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
        );

        FramePanel.add(simpleImagePanel, "card3");

        lblImage1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblImage1.setText("[No Image]");

        lblImage2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblImage2.setText("[No Image]");

        lblImage3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblImage3.setText("[No Image]");

        lblImage4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblImage4.setText("[No Image]");

        lblImage5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblImage5.setText("[No Image]");

        lblImage6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblImage6.setText("[No Image]");

        javax.swing.GroupLayout imagesPanelLayout = new javax.swing.GroupLayout(imagesPanel);
        imagesPanel.setLayout(imagesPanelLayout);
        imagesPanelLayout.setHorizontalGroup(
            imagesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(imagesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblImage1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblImage2, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblImage3, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblImage4, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblImage5, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblImage6, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );
        imagesPanelLayout.setVerticalGroup(
            imagesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(imagesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(imagesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblImage1, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                    .addComponent(lblImage2, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                    .addComponent(lblImage3, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                    .addComponent(lblImage4, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                    .addComponent(lblImage5, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                    .addComponent(lblImage6, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE))
                .addContainerGap())
        );

        jScrollPane2.setViewportView(imagesPanel);

        FramePanel.add(jScrollPane2, "card4");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(FramePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 867, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(lblPatent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLoadImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel2))
                .addContainerGap())
            .addComponent(jToolBar, javax.swing.GroupLayout.DEFAULT_SIZE, 1033, Short.MAX_VALUE)
            .addComponent(statusPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jToolBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnLoadImage)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 284, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPatent))
                    .addComponent(FramePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        java.awt.Dimension dialogSize = getSize();
        setLocation((screenSize.width-dialogSize.width)/2,(screenSize.height-dialogSize.height)/2);
    }// </editor-fold>//GEN-END:initComponents

private void btnLoadImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadImageActionPerformed
    try {
        // this is a new frame, where the picture should be shown
        //final JFrame showPictureFrame = new JFrame("Title");
        // we will put the picture into this label
        //JLabel pictureLabel = new JLabel();

        /* The following will read the imagex| */
        // you should get your picture-path in another way. e.g. with a JFileChooser
        btnLoadImage.setEnabled(false);
        if (fileChooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
            btnLoadImage.setEnabled(true);
            return;
        }
        lblStatus.setText("Procesando Imagen...");
        img = new ImageProcesor(ImageIO.read(fileChooser.getSelectedFile()));
        origImg = ImageIO.read(fileChooser.getSelectedFile());
        lblStatus.setText("Pasando Imagen a escala de grises...");
        grayImg = img.generateGrayImage();
        lblStatus.setText("Binarizando Imagen...");
        binaryImg = img.generateBinaryImage();
        lblStatus.setText("Aplicando Sobel Imagen...");
        sobelImg = img.generateSobel();
        lblStatus.setText("Dividiendo Imagen...");
        divBarsImg = img.generateImageWithDivisionbars();
        divImg = img.getSubimage(5);
        //gridImage = img.getImageGrid(divImg);
        String patente = "";
        try
        {
            subImg = new BufferedImage[6];
            /*subImg[0] = img.resizeImage(img.getSubimage(0),
                img.getSubimage(0).getType(), 136);
            subImg[1] = img.resizeImage(img.getSubimage(1),
                img.getSubimage(1).getType(), 136);
            subImg[2] = img.resizeImage(img.getSubimage(2),
                img.getSubimage(2).getType(), 136);*/
        for (int i = 0; i < 3; i++) {
            //System.out.println(img.getImageMatrix(img.getSubimage(i)).toString());

            BufferedImage aux = img.getSubimage(i);
            Matrix tmp = img.getImageMatrix(aux);
            tmp = Matrix.transponer(tmp);

            subImg[i] = img.resizeImage(aux,
                aux.getType(), 136);

            Matrix resp = Network.simNet(miRedJALet, tmp);
            DecimalFormat df = new DecimalFormat("0.00");

            System.out.println("\n\nRESULTADOS SEGÚN LA RED NEURONAL DE JAVA:\n");
            for (int j = 0; j < 10; j++) {
                System.out.println(nomPatronesLet[j] + ": " + String.valueOf(df.format(Matrix.transponer(resp).toArray()[0][j])) + "  ");
            }    
            System.out.println("\n");

            tmp = new Matrix(Matrix.transponer(resp).toArray()[0]);
            System.out.println("Letra: "+Matrix.masProbable(tmp, nomPatronesLet));
            patente += Matrix.masProbable(tmp, nomPatronesLet);
        }
            
        patente += " ";
        
        for (int i = 3; i < 6; i++) {
            //System.out.println(img.getImageMatrix(img.getSubimage(i)).toString());

            BufferedImage aux = img.getSubimage(i);
            Matrix tmp = img.getImageMatrix(aux);
            tmp = Matrix.transponer(tmp);

            subImg[i] = img.resizeImage(aux,
                aux.getType(), 136);

            Matrix resp = Network.simNet(miRedJANum, tmp);
            DecimalFormat df = new DecimalFormat("0.00");

            System.out.println("\n\nRESULTADOS SEGÚN LA RED NEURONAL DE JAVA:\n");
            for (int j = 0; j < 10; j++) {
                System.out.println(nomPatronesNum[j] + ": " + String.valueOf(df.format(Matrix.transponer(resp).toArray()[0][j])) + "  ");
            }
            System.out.println("\n");

            tmp = new Matrix(Matrix.transponer(resp).toArray()[0]);
            System.out.println("Numero: "+Matrix.masProbable(tmp, nomPatronesNum));
            patente += Matrix.masProbable(tmp, nomPatronesNum);
        }
        }
        catch (NullPointerException e)
            {
            JOptionPane.showMessageDialog(this, "La Imagen no pudo ser interpretada debido a:\n"
                    + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            patente = "???";
        }
        lblPatent.setText("["+ patente + "]");



        fileName = fileChooser.getSelectedFile().getName();


        lblImage.setText("");
        lblImage.setIcon(new ImageIcon(img.resizeImage(origImg, origImg.getType(), 750)));
        enableButtons();
        btnOriginal.setSelected(true);
        lblStatus.setText("\"" + fileName + "\" cargado y procesado.");
        this.pack();

    } catch (IOException ex) {
        System.err.println("Some IOException accured (did you set the right path?): ");
        lblStatus.setText("Some IOException accured (did you set the right path?)");
        ex.printStackTrace();
        btnLoadImage.setEnabled(true);
    }
}//GEN-LAST:event_btnLoadImageActionPerformed

private void btnGrayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGrayActionPerformed
    lblImage.setIcon(new ImageIcon(img.resizeImage(grayImg, grayImg.getType(), 750)));
    ((CardLayout) FramePanel.getLayout()).first(FramePanel);
    lblStatus.setText("Archivo \"" + fileName + "\" en escala de gris.");
}//GEN-LAST:event_btnGrayActionPerformed

private void btnBWActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBWActionPerformed
    if (binaryImg != null) {
        lblImage.setIcon(new ImageIcon(img.resizeImage(binaryImg, binaryImg.getType(), 750)));
        ((CardLayout) FramePanel.getLayout()).first(FramePanel);
        lblStatus.setText("Archivo \"" + fileName + "\" binarizado.");
    } else {
        JOptionPane.showMessageDialog(this, "No se pudo generar la imagen binarizada.",
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}//GEN-LAST:event_btnBWActionPerformed

private void btnOriginalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOriginalActionPerformed
    lblImage.setIcon(new ImageIcon(img.resizeImage(origImg, origImg.getType(), 750)));
    ((CardLayout) FramePanel.getLayout()).first(FramePanel);
    lblStatus.setText("Archivo \"" + fileName + "\" original.");
}//GEN-LAST:event_btnOriginalActionPerformed

private void btnGraphicsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGraphicsActionPerformed
    new GraphicsView(img).setVisible(true);
}//GEN-LAST:event_btnGraphicsActionPerformed

private void btnSobelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSobelActionPerformed
    if (sobelImg != null) {
        lblImage.setIcon(new ImageIcon(img.resizeImage(sobelImg, sobelImg.getType(), 750)));
        ((CardLayout) FramePanel.getLayout()).first(FramePanel);
        lblStatus.setText("Sobel aplicado \"" + fileName + "\" binarizado.");
    } else {
        JOptionPane.showMessageDialog(this, "No se pudo aplicar sobel a la imagen.",
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}//GEN-LAST:event_btnSobelActionPerformed

private void btnDivBarsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDivBarsActionPerformed
    if (divBarsImg != null) {
        lblImage.setIcon(new ImageIcon(img.resizeImage(divBarsImg, divBarsImg.getType(), 750)));
        //lblImage.setIcon(new ImageIcon(divBarsImg));
        ((CardLayout) FramePanel.getLayout()).first(FramePanel);
        lblStatus.setText("Mostrando lineas de división en \"" + fileName + "\".");
    } else {
        JOptionPane.showMessageDialog(this, "No se pudo aplicar la división de la imagen.",
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}//GEN-LAST:event_btnDivBarsActionPerformed

private void btnDivisionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDivisionActionPerformed
    if (divBarsImg != null) {
        lblImage1.setIcon(new ImageIcon(subImg[0]));
        lblImage2.setIcon(new ImageIcon(subImg[1]));
        lblImage3.setIcon(new ImageIcon(subImg[2]));
        lblImage4.setIcon(new ImageIcon(subImg[3]));
        lblImage5.setIcon(new ImageIcon(subImg[4]));
        lblImage6.setIcon(new ImageIcon(subImg[5]));
        ((CardLayout) FramePanel.getLayout()).last(FramePanel);
        lblStatus.setText("Mostrando \"" + fileName + "\" dividido.");
    } else {
        JOptionPane.showMessageDialog(this, "No se pudo aplicar la división de la imagen.",
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}//GEN-LAST:event_btnDivisionActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel FramePanel;
    private javax.swing.JToggleButton btnBW;
    private javax.swing.JToggleButton btnDivBars;
    private javax.swing.JToggleButton btnDivision;
    private javax.swing.JButton btnGraphics;
    private javax.swing.JToggleButton btnGray;
    private javax.swing.JButton btnLoadImage;
    private javax.swing.JToggleButton btnOriginal;
    private javax.swing.JToggleButton btnSobel;
    private javax.swing.ButtonGroup buttonGroup;
    private javax.swing.JPanel imagesPanel;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar jToolBar;
    private javax.swing.JLabel lblImage;
    private javax.swing.JLabel lblImage1;
    private javax.swing.JLabel lblImage2;
    private javax.swing.JLabel lblImage3;
    private javax.swing.JLabel lblImage4;
    private javax.swing.JLabel lblImage5;
    private javax.swing.JLabel lblImage6;
    private javax.swing.JLabel lblPatent;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JPanel simpleImagePanel;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables
}
