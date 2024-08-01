/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ia.back_end;

import java.util.Random;
import java.lang.Math.*;

/**
 * Clase Red:Define las matrices de pesos, los métodos de entrenamiento, de propagación
 * y en general todos los aspectos de campos y funcionalidad de la red neuronal
 * propuesta como trabajo e implementada en Java
 * @author Carlos S. Moreno
 */
public class Network {

    Matrix wI;//Matrix de pesos de entrada-oculta 18x112
    Matrix wO;//Matrix de pesos de oculta salida 10x18
    Matrix trainMP;//Matrix 10 patrones de entrada 112x10
    Matrix trainMT;//Matrix de 10 patrones de salida 10x10
    Random generadorAl = new Random();//Para inicializar los pesos
    int epocas = 0;

    //CONSTRUCTORES
    /**
     *Constructor con matrices de patrones
     * @param mP1 Matrix de patrones de entrada [35x10]
     * @param mT1 Matrix de objetivos [10x10]
     */
    public Network(Matrix mP1, Matrix mT1) {

        this.trainMP = mP1;
        this.trainMT = mT1;
    }

    /**
     *Constructor vacío
     */
    public Network() {
    }

// METODOS
    /**
     *Establecer pesos
     * Coloca las matrices de la capa oculta y la capa de salida en
     * el objeto Network.
     * @param pesosI pesos de la capa oculta
     * @param pesosO pesos de la capa de salida
     */
    public void setPesos(Matrix pesosI, Matrix pesosO) {
        this.wI = pesosI;
        this.wO = pesosO;
    }

    /**
     *Inicializar Network
     * Inicializa los pesos de la Network neuronal pasada como parámetro
     * con un número aleatorio dentro de una distribución de probabilidad
     * Gaussiana con valores entre [-0.5,0.5]
     * @param NetworkNeu Network Neuronal Objetivo
     */
    public static void init(Network NetworkNeu) {
        double tempI[][] = new double[18][112];
        double tempO[][] = new double[NetworkNeu.getTrainMP().getCol()][18];
        for (int i = 0; i < 18; i++) {
            for (int j = 0; j < 112; j++) {
                tempI[i][j] = NetworkNeu.generadorAl.nextGaussian() * 0.5;
            }
        }

        for (int i = 0; i < NetworkNeu.getTrainMP().getCol(); i++) {
            for (int j = 0; j < 18; j++) {
                tempO[i][j] = NetworkNeu.generadorAl.nextGaussian() * 0.5;
            }
        }

        NetworkNeu.wI = new Matrix(tempI);
        NetworkNeu.wO = new Matrix(tempO);
    }

    /**
     * Obtener Wi
     * Devuelve la Matrix de pesos de la capa oculta
     * @return Matrix Wi [18x112]
     */
    public Matrix getWi() {
        return this.wI;
    }

    /**
     *Obtener Wo
     * Devuelve la Matrix de pesos de la capa de salida
     * @return Matrix Wo[10x18]
     */
    public Matrix getWo() {
        return this.wO;
    }

    /**
     *Obtener patrones de entrada
     * Devuelve la Matrix con los patrones de entrada de entrenamiento
     * @return Matrix patronesEntrada[112x10]
     */
    public Matrix getTrainMP() {
        return this.trainMP;
    }

    /**
     *Obtener salidas objetivo
     * devuelve la Matrix con los objetivos de entrenamiento
     * @return Matrix con los vectores de salida
     * @ Matrix salidas [10x10]
     */
    public Matrix getTrainMT() {
        return this.trainMT;
    }

    /**
     *fNetLog
     * Evalúa la  función sigmoidal 1/(1+e^-n) de un valor dado
     * @param value valor a evaluar
     * @return
     * @return1/(1+e^-value)
     */
    public static double fNetLog(double value) {
        double num = 1;
        double den = 0;
        double f;
        double exp = (-1) * value;
        den = (1 + Math.pow(Math.E, (exp)));
        f = num / den;
        return f;
    }

    /**
     *f prima de net
     * Evalúa la primera derivada de la función sigmoidal 1/(1+e^-n)
     * @param value valor a evaluar
     * @return (1/(1+e^-n))*(1-1/(1+e^-n))
     */
    public static double fPrimaNetLog(double value) {
        double f;

        f = Network.fNetLog(value) * (1 - Network.fNetLog(value));
        return f;
    }

    /**
     * F (net) a Matrix
     * Evalúa la función sigmoidal a toda una Matrix
     * @param mat Matrix a evaluar
     * @return Matrix con sus elementos evaluados
     */
    public static Matrix fNetMatrixLog(Matrix mat) {
        double retorno[][];
        retorno = new double[mat.getFil()][mat.getCol()];
        Matrix res = new Matrix(retorno);

        for (int i = 0; i < mat.getFil(); i++) {

            for (int j = 0; j < mat.getCol(); j++) {

                res.setFC(i, j, Network.fNetLog(mat.getFC(i, j)));

            }

        }
        return res;

    }

    /**
     *Evaluar f'(net) a Matrix
     * Evalúa la derivada de la función sigmoidal a toda una Matrix
     * @param mat Matrix a evaluar
     * @return Matrix con sus elementos evaluados
     */
    public static Matrix fNetPrimaMatrixLog(Matrix mat) {
        double retorno[][];
        retorno = new double[mat.getFil()][mat.getCol()];
        Matrix res = new Matrix(retorno);

        for (int i = 0; i < mat.getFil(); i++) {

            for (int j = 0; j < mat.getCol(); j++) {

                res.setFC(i, j, Network.fPrimaNetLog(mat.getFC(i, j)));

            }

        }
        return res;

    }

    /**
     *Simular Network Neuronal
     * Simula el comportamiento de la Network neuronal.
     * Multiplica matrices, evalua las funciones de propagación y en general
     * propaga la Network hacia adelante.
     * @param NetworkNeu Network Neuronal modelo
     * @param Ventrada Vector de entrada a evaluar[35x1]
     * @return Matrix con un vector de respuestas de la Network [10x1]
     */
    public static Matrix simNet(Network NetworkNeu, Matrix Ventrada) {
//Ventrada 35x1
        Matrix netI;
        Matrix fNetI;

        Matrix netO;
        Matrix fNetO;


//MULTIPLICACION [NET]=[WI][XI]
        netI = Matrix.multiplicar(NetworkNeu.getWi(), Ventrada);
        fNetI = Network.fNetMatrixLog(netI);

//MULTIPLICACION [NET]=[WO][FNETO]
        netO = Matrix.multiplicar(NetworkNeu.getWo(), fNetI);
        fNetO = Network.fNetMatrixLog(netO);

        return fNetO;
//salida de 10x1
    }

    /**
     *Establecer épocas
     * Establece el número de épocas de entrenamiento han transcurrido
     * en un proceso de entrenamiento tras la condición de salida
     * @param num contador de épocas
     */
    public void setEpocas(int num) {
        this.epocas = num;
    }

    /**
     *Obtener el número de épocas
     * Devuelve el número de épocas que tuvo una Network para alcanzar la condición
     * de salida.
     * @return int numEpocas
     */
    public int getEpocas() {
        return this.epocas;
    }

    /**
     *Obtener Error cuadrático
     * @param errores Matrix con los errores (yd-o)[10x1]
     * @return Double con la sumatoria de los errores al cuadrado multiplicado
     * por 1/2
     */
    public static double getErrorCuadratico(Matrix errores) {
        double tmp = 0;
        for (int i = 0; i < errores.getFil(); i++) {
            tmp += Math.pow(errores.getFC(i, 0), 2);
        }
        tmp = tmp * 0.5;
        return tmp;
    }

    /**
     *Entrenar Network neuronal
     * Entrena la Network neuronal con el siguiente algoritmo:
     * <br>1.Se inicializa a Network (valores aleatorios de wi y wo)
     * <br>2.huboError=true;
     *<br>
     * <br>Ciclo1.Mientras (epocas < iteracionesMáximas)&& (huboError==true))
     * <br>Ciclo2 for j=0;j<10;j++
     *<br>      3.Se presenta el patrón j y se propaga la Network hacia adelante
     *<br>     4 Se calcula el error
     *<br>      Cond1 .If error>error{
     *<br>              -huboError=true
     *<br>              -Se propaga la Network hacia atrás
     *<br>              -Se actualizan los pesos}
     *<br>
     *<br>
     *<br>           fin del ciclo2
     *<br>            epocas++;
     * <br>         fin del cliclo1
     *<br>El algoritmo se termina cuando todos los patrones tengan un valor de
     *error medio cuadrático menor que el establecido como parámetro o cuando
     *se supera el número máximo de iteraciones
     *
     * @param NetworkNeu Network neuronal modelo
     * @param alpha factor de aprendizaje
     * @param error error objetivo por patrón
     * @param iteraciones numero máximo de iteraciones del algoritmo
     * @return String cadena con los resultados del entrenamiento
     */
    public static String trainNetLog(Network NetworkNeu, double alpha, double error, int iteraciones) {

        Network.init(NetworkNeu);
        //Ventrada 35x1
        int contIteraciones = 0;
        int j = 0;
        Matrix netI;
        Matrix fNetI;
        Matrix fNetPrimaI;
        Matrix wI;
        Matrix eI;
        Matrix dI;

        Matrix netO;
        Matrix fNetO;
        Matrix fNetPrimaO;
        Matrix wO;
        Matrix eO;
        Matrix dO;

        double errG[] = new double[NetworkNeu.getTrainMP().getCol()];
        double errGvalue = 0;
        double errP;
        boolean huboError = true;

        NetworkNeu.setEpocas(0);

        while ((contIteraciones < iteraciones) && huboError == true) {

            huboError = false;
            for (j = 0; j < errG.length; j++) {
//********************PASO HACIA ADELANTE*************************************
                //PROPAGAR LA CAPA OCULTA
                //[18x1] = [18x35] * [35x1] Net=WIxXI
                netI = Matrix.multiplicar(NetworkNeu.getWi(), NetworkNeu.getTrainMP().getColumna(j));
                //[18x1] = f([18x1])
                fNetI = Network.fNetMatrixLog(netI);

                //PROPAGAR LA SALIDA
                //[10x1] = [10x18] * [18x1]
                netO = Matrix.multiplicar(NetworkNeu.getWo(), fNetI);
                //[10x1] = f([10x1])
                fNetO = Network.fNetMatrixLog(netO);

                //CALCULAR LOS ERRORES
                //[10x1] = [10x1] - [10x1] (yd-o)
                eO = Matrix.restar(NetworkNeu.getTrainMT().getColumna(j), fNetO);

                //calcular el error cuadrático
                errP = Network.getErrorCuadratico(eO);
                errG[j] = errP;



                if (errP > error) {

                    huboError = true;

                    //SE CALCULAN LAS DERIVADAS
                    //[18x1] = f'([18x1])
                    fNetPrimaI = Network.fNetPrimaMatrixLog(netI);

                    //[10x1] = f'([10x1])
                    fNetPrimaO = Network.fNetPrimaMatrixLog(fNetO);


                    //CALCULAR dO
                    //[10x1]=e[10x1]xe[10x1]  = (yd-o)*f'(netO)
                    dO = Matrix.multElementos(eO, fNetPrimaO);


                    //Calcular dI .. error propagado
                    //[18x10]=[10x18]'T
                    Matrix woT = Matrix.transponer(NetworkNeu.getWo());
                    //[18x1]=[18x10]x[10x1]
                    Matrix tmp = Matrix.multiplicar(woT, dO);
                    //[18x1]=e[18x1]xe[18x1]
                    dI = Matrix.multElementos(tmp, fNetPrimaI);

                    //ACTUALIZAR LOS PESOS
                    //[10x18]=[10x1][18x1]'T
                    Matrix deltaWO = Matrix.multEscalar(Matrix.multiplicar(dO, Matrix.transponer(fNetI)), alpha);
                    wO = Matrix.sumar(NetworkNeu.getWo(), deltaWO);

                    //[18x35]=[18x1][35x1]'T
                    Matrix deltaWI = Matrix.multEscalar(Matrix.multiplicar(dI, Matrix.transponer(NetworkNeu.getTrainMP().getColumna(j))), alpha);
                    wI = Matrix.sumar(NetworkNeu.getWi(), deltaWI);


                    //Se actualizan los pesos
                    NetworkNeu.setPesos(wI, wO);

                    //Vuelve al primer patrón

                }
            }

            contIteraciones++;//Una época más (iteración)
        }
        NetworkNeu.setEpocas(contIteraciones);


        String ep = String.valueOf(NetworkNeu.getEpocas());

        if (huboError == false) {

            for (int i = 0; i < errG.length; i++) {
                errGvalue += errG[i];
            }
            String errorG = String.valueOf(errGvalue);
            return "¡Red entrenada con éxito!\n" + "Iteraciones: " + ep + "\nValor de error alcanzado\nError Global: " + errorG + "\n";

        } else {
            return "¡Red entrenada con éxito!\n" + "Iteraciones: " + ep + "\nValor de error NO alcanzado\n";
        }



    }
}
