/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ia.back_end;

import java.text.DecimalFormat;

/**
 *Clase Matrix
 *Clase que define objetos de tipo matriz para realizar operaciones de matrices
 * de forma rápida y sencilla
 * @author Carlos S. Moreno
 */
public class Matrix {
    //Campos
    int fil = 0;
    int col = 0;
    double mat[][];

//Constructures sobrecargados
    /**
     *Contructor Matrix
     * @param m Arreglo bidimensional de double
     */
    public Matrix(double m[][]) {
        this.mat = m;
        this.fil = m.length;
        this.col = m[0].length;
    }

    /**
     *Constructor Matrix
     * @param m Arreglo unidimensional de double
     */
    public Matrix(double m[]) {
        this.mat = new double[1][m.length];
        for (int i = 0; i < m.length; i++) {
            this.mat[0][i] = m[i];
            this.fil = 1;
            this.col = m.length;
        }
    }

    /****************************************************************************
     * MÉTODOS
     ****************************************************************************
     */
    /**
     * Obtener número de columnas
     * @param m Matrix
     * @return int NumColumnas
     */
    public static int getCol(Matrix m) {
        return m.col;
    }

    /**
     * Obtener número de columnas
     * @return int NumColumnas
     */
    public int getCol() {
        return this.col;
    }

    /**
     * Obtener número de columnas
     * @return String NumColumnas
     */
    public String getColString() {
        return String.valueOf(this.col);
    }

    /**
     * Obtener número de filas
     * @param m Matrix
     * @return int NumFilas
     */
    public static int getFil(Matrix m) {
        return m.fil;
    }

    /**
     * Obtener número de filas
     * @return int NumFilas
     */
    public int getFil() {
        return this.fil;
    }

    /**
     * Obtener número de filas
     * @return String NumFilas
     */
    public String getFilString() {
        return String.valueOf(this.fil);
    }

    /**
     * Convertir Matrix en array
     * @return Double array[][]
     */
    public double[][] toArray() {
        return this.mat;
    }

    /**
     * Convertir Matrix en array
     * @param m Matrix
     * @return Double array[][]
     */
    public static double[][] toArray(Matrix m) {
        return m.mat;
    }

    /**
     *Colocar valor en posicion[f][c]
     * @param f índice de fila
     * @param c índice de columna
     * @param value Valor que se coloca en el arreglo de la Matrix
     */
    public void setFC(int f, int c, double value) {
        this.mat[f][c] = value;
    }

    /**
     *Obtener valor en posición[f][c]
     * @param f índice fila
     * @param c índice columna
     * @return double valor
     */
    public double getFC(int f, int c) {
        return this.mat[f][c];
    }

    /**
     *Convertir la Matrix en un String
     * @param m Matrix a convertir
     * @return String con las filas y las columnas de la Matrix
     * debidamente ordenadas.
     */
    public static String toString(Matrix m) {
        DecimalFormat df = new DecimalFormat("0.00");
        StringBuffer mat1 = new StringBuffer();
        for (int j = 0; j < m.getFil(); j++) {

            for (int i = 0; i < m.getCol(); i++) {
                mat1.append(" ");
                mat1.append(df.format(m.toArray()[j][i]));
            }
            mat1.append("\n");
        }
        String salida = mat1.toString();
        return salida;
    }

    /**
     *Convertir Matrix a String
    @return String con las filas y las columnas de la Matrix
     * debidamente ordenadas.
     */
    public String toString() {
        return toString(this);
    }

    /**
     *Transponer Matrix
     * @param m Matrix a transponer
     * @return Matrix m'
     */
    public static Matrix transponer(Matrix m) {
        double retorno[][];
        retorno = new double[m.getCol()][m.getFil()];

        for (int i = 0; i < m.getFil(); i++) {
            for (int j = 0; j < m.getCol(); j++) {

                retorno[j][i] = m.toArray()[i][j];

            }
        }
        Matrix ret = new Matrix(retorno);
        return ret;
    }

    /**
     *Sumar matrices
     * @param mA sumandoA
     * @param mB sumandoB
     * @return Matrix (ma+mB)
     */
    public static Matrix sumar(Matrix mA, Matrix mB) {
        double retorno[][];
        retorno = new double[mB.getFil()][mB.getCol()];
        Matrix ret = new Matrix(retorno);
        for (int i = 0; i < mA.getFil(); i++) {
            for (int j = 0; j < mA.getCol(); j++) {

                ret.setFC(i, j, (mA.getFC(i, j) + mB.getFC(i, j)));

            }
        }

        return ret;
    }

    /**
     *Restar matrices
     * @param mA Minuendo
     * @param mB Sustraendo
     * @return Matrix diferencia= mA-mB
     */
    public static Matrix restar(Matrix mA, Matrix mB) {
        double retorno[][];
        retorno = new double[mB.getFil()][mB.getCol()];
        Matrix ret = new Matrix(retorno);
        for (int i = 0; i < mA.getFil(); i++) {
            for (int j = 0; j < mA.getCol(); j++) {

                ret.setFC(i, j, (mA.getFC(i, j) - mB.getFC(i, j)));

            }
        }

        return ret;
    }

    /**
     *Multiplicar matrices
     * @param mA factor A
     * @param mB factor B
     * @return mAxmB
     * La Matrix de retorno tiene dimensions [fA]x[cB]
     */
    public static Matrix multiplicar(Matrix mA, Matrix mB) {
        double retorno[][];
        double tmp = 0;
        retorno = new double[mA.getFil()][mB.getCol()];
        Matrix ret = new Matrix(retorno);

        for (int i = 0; i < mA.getFil(); i++) {

            for (int j = 0; j < mB.getCol(); j++) {
                tmp = 0;
                for (int k = 0; k < mA.getCol(); k++) {

                    tmp += mA.getFC(i, k) * mB.getFC(k, j);

                }
                ret.setFC(i, j, tmp);
            }
        }
        return ret;
    }

    /**
     *Calcular la respueta más probable
     * @param resp Matrix con las respuestas de la simulación [1]x[10]
     * @param nom Arreglo de Strings con los nombres de los patrones
     * @return String con el patrón más probable del arreglo
     */
    public static String masProbable(Matrix resp, String nom[]) {
        int index = 0;
        double tmp = resp.toArray()[0][0];
        for (int i = 1; i < resp.toArray()[0].length; i++) {

            if (resp.toArray()[0][i] > tmp) {
                tmp = resp.toArray()[0][i];
                index = i;
            }
        }
        return nom[index];
    }

    /**
     *Obtener columna
     * @param index índice de la columna a obtener
     * @return Matrix Columna con todos los valores de la columna indicada
     */
    public Matrix getColumna(int index) {
        double tmp[][] = new double[this.getFil()][1];

        for (int i = 0; i < this.getFil(); i++) {
            tmp[i][0] = this.toArray()[i][index];
        }
        Matrix retorno = new Matrix(tmp);
        return retorno;

    }

    /**
     *Multiplicar Matrix por escalar
     * @param m Matrix a multiplicar
     * @param esc factor escalar
     * @return Matrix escalada
     */
    public static Matrix multEscalar(Matrix m, double esc) {

        double tmp[][] = new double[m.getFil()][m.getCol()];
        for (int i = 0; i < m.getFil(); i++) {

            for (int j = 0; j < m.getCol(); j++) {
                tmp[i][j] = m.toArray()[i][j] * esc;
            }

        }

        Matrix ret = new Matrix(tmp);
        return ret;
    }

    /**
     *Multiplicar elementos entre matrices
     * @param m1 Matrix 1
     * @param m2 Matrix 2
     * @return Devuelve una Matrix con las mismas dimensiones
     * con los productos de los elementos de cada Matrix
     */
    public static Matrix multElementos(Matrix m1, Matrix m2) {

        double tmp[][] = new double[m1.getFil()][m2.getCol()];
        for (int i = 0; i < m1.getFil(); i++) {

            for (int j = 0; j < m1.getCol(); j++) {
                tmp[i][j] = m1.toArray()[i][j] * m2.toArray()[i][j];
            }

        }

        Matrix ret = new Matrix(tmp);
        return ret;
    }
    
    public static String toPrettyString(Matrix matrix)
    {
        String grid = "";
        DecimalFormat df = new DecimalFormat("0");
        StringBuffer mat1 = new StringBuffer();
        for (int j = 0; j < matrix.getCol(); j++) {
            mat1.append("[");
            for (int i = 0; i < matrix.getFil(); i++) {
                mat1.append(" ");
                mat1.append(df.format(matrix.toArray()[i][j]));
            }
            mat1.append(" ]");
            mat1.append("\n");
        }
        grid += mat1.toString();
        return grid;
    }
    
    public String toPrettyString()
    {
        return toPrettyString(this);
    }
}