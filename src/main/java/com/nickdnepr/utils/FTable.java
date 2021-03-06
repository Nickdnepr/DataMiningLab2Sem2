package com.nickdnepr.utils;

public class FTable {

    private static double[][] table =
            {
                    {161.4, 199.5, 215.8, 224.8, 230.0, 233.8, 236.5, 238.6, 240.1, 242.2},
                    {18.51, 19, 19.16, 19.25, 19.3, 19.33, 19.35, 19.37, 19.38, 19.40},
                    {10.13, 9.55, 9.28, 9.12, 9.01, 8.94, 8.89, 8.85, 8.81, 8.79},
                    {7.71, 6.94, 6.59, 6.39, 6.26, 6.16, 6.09, 6.04, 6, 5.96},
                    {6.61, 5.79, 5.41, 5.19, 5.05, 4.95, 4.88, 4.82, 4.77, 4.74},
                    {5.99, 5.14, 4.76, 4.53, 4.39, 4.28, 4.21, 4.15, 4.10, 4.06},
                    {5.59, 4.74, 4.35, 4.12, 3.97, 3.87, 3.79, 3.73, 3.68, 3.64},
                    {5.32, 4.46, 4.07, 3.84, 3.69, 3.58, 3.50, 3.44, 3.39, 3.35},
                    {5.12, 4.26, 3.86, 3.63, 3.48, 3.37, 3.29, 3.23, 3.18, 3.14},
                    {4.96, 4.10, 3.71, 3.48, 3.33, 3.22, 3.14, 3.07, 3.02, 2.98},
            };

    public static double getValue(int numDF, int denDF) {
        if (numDF > 10) {
            numDF = 10;
        }
        if (denDF > 10) {
            denDF = 10;
        }
        return table[denDF - 1][numDF - 1];
    }
}
