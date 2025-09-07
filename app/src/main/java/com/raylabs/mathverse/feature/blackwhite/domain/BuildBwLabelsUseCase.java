package com.raylabs.mathverse.feature.blackwhite.domain;

public final class BuildBwLabelsUseCase {
    public BwLabels invoke(boolean isShowing, String whiteLabel, String blackLabel) {
        String w = isShowing ? whiteLabel : "";
        String b = isShowing ? blackLabel : "";
        return new BwLabels(
                /* topWhite   */ w,
                /* topBlack   */ b,
                /* centerBlack*/ b,
                /* centerWhite*/ w,
                /* bottomWhite*/ w,
                /* bottomBlack*/ b
        );
    }
}