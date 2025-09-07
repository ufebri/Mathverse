package com.raylabs.mathverse.feature.volume.data;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class VolumeCalculatorTest {

    @Test
    public void calculate_validInputs_returnsCorrectArea() {
        assertEquals(20.0, VolumeCalculator.calculate(4, 5), 0.0001);
        assertEquals(0.0, VolumeCalculator.calculate(0, 10), 0.0001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void calculate_negativeWidth_throwsException() {
        VolumeCalculator.calculate(-1, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void calculate_negativeLength_throwsException() {
        VolumeCalculator.calculate(3, -5);
    }
}