package com.raylabs.mathverse.feature.volume.data;

final public class VolumeCalculator {
    private VolumeCalculator() {
    }

    static public double calculate(double width, double length) {
        if (width < 0 || length < 0) {
            throw new IllegalArgumentException("width and length must be >= 0");
        }
        return width * length;
    }
}