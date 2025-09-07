package com.raylabs.mathverse.feature.blackwhite.domain;

import org.junit.Test;
import static org.junit.Assert.*;

public class BuildBwLabelsUseCaseTest {
    private final BuildBwLabelsUseCase useCase = new BuildBwLabelsUseCase();

    @Test public void true_fills_labels() {
        BwLabels m = useCase.invoke(true, "WHITE", "BLACK");
        assertEquals("WHITE", m.topWhite);
        assertEquals("BLACK", m.topBlack);
        assertEquals("BLACK", m.centerBlack);
        assertEquals("WHITE", m.centerWhite);
        assertEquals("WHITE", m.bottomWhite);
        assertEquals("BLACK", m.bottomBlack);
    }

    @Test public void false_clears_all() {
        BwLabels m = useCase.invoke(false, "WHITE", "BLACK");
        assertEquals("", m.topWhite);
        assertEquals("", m.topBlack);
        assertEquals("", m.centerBlack);
        assertEquals("", m.centerWhite);
        assertEquals("", m.bottomWhite);
        assertEquals("", m.bottomBlack);
    }
}