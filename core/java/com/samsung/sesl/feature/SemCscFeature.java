package com.samsung.sesl.feature;

public class SemCscFeature {
    private static String hidden_getString(String tag, String defaultValue) {
        return com.samsung.android.feature.SemCscFeature.getInstance().getString(tag, defaultValue);
    }
}
