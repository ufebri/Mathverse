package com.raylabs.mathverse.feature.pyramid.data;

import org.junit.Test;
import static org.junit.Assert.*;

public class PyramidGeneratorTest {

    @Test
    public void build_zeroRows_returnsEmpty() {
        assertEquals("", PyramidGenerator.build(0, "*"));
    }

    @Test
    public void build_positiveRows_usesToken_andCentersWithLeftPad() {
        String out = PyramidGenerator.build(3, "*");
        String expected =
                "  *\n" +   // 2 spaces + 1 star
                " * *\n" +  // 1 space + 2 stars
                "* * *";    // 0 space + 3 stars
        assertEquals(expected, out);
    }

    @Test
    public void build_customToken_ok() {
        String out = PyramidGenerator.build(2, "#");
        assertEquals(" #\n# #", out);
    }

    @Test(expected = IllegalArgumentException.class)
    public void build_negative_throws() {
        PyramidGenerator.build(-1, "*");
    }

    @Test
    public void build_nullOrEmptyToken_defaultsToAsterisk() {
        assertEquals("*", PyramidGenerator.build(1, null));
        assertEquals("*", PyramidGenerator.build(1, ""));
    }
}