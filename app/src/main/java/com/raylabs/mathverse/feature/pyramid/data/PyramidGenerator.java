package com.raylabs.mathverse.feature.pyramid.data;

final public class PyramidGenerator {
    private PyramidGenerator() {}

    public static String build(int numRows, String token) {
        if (numRows < 0) throw new IllegalArgumentException("numRows must be >= 0");
        if (token == null || token.isEmpty()) token = "*";

        StringBuilder out = new StringBuilder();
        for (int i = 1; i <= numRows; i++) {
            // spaces for left padding so that the pyramid looks centered
            int pad = numRows - i;
            for (int s = 0; s < pad; s++) {
                out.append(" ");
            }
            // append tokens separated by a space
            for (int t = 0; t < i; t++) {
                out.append(token);
                if (t < i - 1) out.append(' ');
            }
            if (i < numRows) out.append('\n');
        }
        return out.toString();
    }
}