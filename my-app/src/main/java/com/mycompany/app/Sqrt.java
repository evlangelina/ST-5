package com.mycompany.app;

/**
 * Вычисление квадратного корня методом последовательных приближений
 * (итерационная формула Герона / частный случай метода Ньютона).
 *
 * Идея: для числа {@code arg} ищем такое {@code guess}, что
 * {@code guess * guess} приблизительно равно {@code arg}. На каждом
 * шаге приближение уточняется как среднее между текущим значением
 * и частным {@code arg / guess}.
 */
public class Sqrt {

    /** Допустимая погрешность, при которой приближение считается достаточным. */
    private static final double DEFAULT_DELTA = 1e-8;

    private final double arg;
    private final double delta;

    public Sqrt(double arg) {
        this(arg, DEFAULT_DELTA);
    }

    /** Дополнительный конструктор позволяет задать собственную точность. */
    public Sqrt(double arg, double delta) {
        this.arg = arg;
        this.delta = delta;
    }

    /** Среднее арифметическое двух значений. */
    public double average(double x, double y) {
        return (x + y) * 0.5;
    }

    /** Проверяет, достаточно ли точно {@code guess} приближает корень из {@code x}. */
    public boolean good(double guess, double x) {
        double error = guess * guess - x;
        if (error < 0) {
            error = -error;
        }
        return error < delta;
    }

    /** Один шаг уточнения приближения. */
    public double improve(double guess, double x) {
        return average(guess, x / guess);
    }

    /**
     * Итеративно уточняет приближение, пока оно не станет достаточно точным.
     * В отличие от рекурсивной записи здесь используется обычный цикл,
     * что исключает риск переполнения стека на «тяжёлых» входных данных.
     */
    public double iter(double guess, double x) {
        double current = guess;
        while (!good(current, x)) {
            current = improve(current, x);
        }
        return current;
    }

    /** Возвращает квадратный корень из аргумента, заданного в конструкторе. */
    public double calc() {
        return iter(1.0, arg);
    }
}
