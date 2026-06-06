package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class AppTest {

    private static final double TOLERANCE = 1e-6;

    private Sqrt sqrt;

    @BeforeEach
    void setUp() {
        sqrt = new Sqrt(2.0);
    }

    @Test
    void averageOfPositiveNumbers() {
        assertEquals(7.5, sqrt.average(5.0, 10.0), TOLERANCE);
    }

    @Test
    void averageOfEqualNumbers() {
        assertEquals(3.0, sqrt.average(3.0, 3.0), TOLERANCE);
    }

    @ParameterizedTest
    @CsvSource({
            "0.0, 0.0, 0.0",
            "-4.0, 4.0, 0.0",
            "-2.0, -8.0, -5.0",
            "1.5, 2.5, 2.0"
    })
    void averageWithVariousPairs(double x, double y, double expected) {
        assertEquals(expected, sqrt.average(x, y), TOLERANCE);
    }

    @Test
    void goodAcceptsExactGuess() {
        assertTrue(sqrt.good(3.0, 9.0));
    }

    @Test
    void goodRejectsRoughGuess() {
        assertFalse(sqrt.good(2.0, 9.0));
    }

    @Test
    void goodRespectsCustomDelta() {
        Sqrt loose = new Sqrt(9.0, 0.5);
        assertTrue(loose.good(3.05, 9.0));
    }

    @Test
    void improveMakesNewtonStep() {
        assertEquals(1.5, sqrt.improve(1.0, 2.0), TOLERANCE);
    }

    @Test
    void improveGetsCloserToRoot() {
        double before = Math.abs(2.0 * 2.0 - 9.0);
        double improved = sqrt.improve(2.0, 9.0);
        double after = Math.abs(improved * improved - 9.0);
        assertTrue(after < before);
    }

    @Test
    void iterReturnsAlreadyGoodGuess() {
        assertEquals(5.0, sqrt.iter(5.0, 25.0), TOLERANCE);
    }

    @Test
    void iterConvergesFromArbitraryStart() {
        assertEquals(Math.sqrt(2.0), sqrt.iter(1.0, 2.0), TOLERANCE);
    }

    @Test
    void calcForPerfectSquare() {
        Sqrt s = new Sqrt(144.0);
        assertEquals(12.0, s.calc(), TOLERANCE);
    }

    @Test
    void calcForFraction() {
        Sqrt s = new Sqrt(0.81);
        assertEquals(0.9, s.calc(), TOLERANCE);
    }

    @ParameterizedTest
    @ValueSource(doubles = {1.0, 2.0, 3.0, 16.0, 50.0, 1234.5})
    void calcMatchesMathSqrt(double value) {
        Sqrt s = new Sqrt(value);
        assertEquals(Math.sqrt(value), s.calc(), TOLERANCE);
    }
}
