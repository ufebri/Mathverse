package com.raylabs.mathverse.feature.blackwhite.ui;

import com.raylabs.mathverse.feature.blackwhite.domain.BwLabels;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.*;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

public class BwViewModelTest {

    @Rule public InstantTaskExecutorRule instantRule = new InstantTaskExecutorRule();

    @Test public void emits_state_correctly() {
        BwViewModel vm = new BwViewModel();
        vm.setShowingColor(true, "WHITE", "BLACK");

        BwLabels s = vm.getState().getValue();
        assertNotNull(s);
        assertEquals("WHITE", s.topWhite);
        assertEquals("BLACK", s.topBlack);

        vm.setShowingColor(false, "WHITE", "BLACK");
        BwLabels s2 = vm.getState().getValue();
        assertNotNull(s2);
        assertEquals("", s2.topWhite);
        assertEquals("", s2.topBlack);
    }
}