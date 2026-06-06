package com.mycompany.app;

public class App {

    public static void main(String[] args) {
        double[] values = {2.0, 9.0, 0.25, 100.0};

        for (double value : values) {
            Sqrt sqrt = new Sqrt(value);
            double result = sqrt.calc();
            System.out.printf("sqrt(%.4f) = %.8f%n", value, result);
        }
    }
}
