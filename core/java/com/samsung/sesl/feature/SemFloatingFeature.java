package com.samsung.sesl.feature;

public class SemFloatingFeature {
    private static String hidden_getString(String tag, String defaultValue) {
        return com.samsung.android.feature.SemFloatingFeature.getInstance()
                .getString(tag, defaultValue);
    }
}
