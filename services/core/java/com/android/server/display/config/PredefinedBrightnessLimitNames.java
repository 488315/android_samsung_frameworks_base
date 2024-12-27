package com.android.server.display.config;

public enum PredefinedBrightnessLimitNames {
    /* JADX INFO: Fake field, exist only in values array */
    EF8("default"),
    /* JADX INFO: Fake field, exist only in values array */
    EF16("adaptive");

    private final String rawName;

    PredefinedBrightnessLimitNames(String str) {
        this.rawName = str;
    }

    public static PredefinedBrightnessLimitNames fromString(String str) {
        for (PredefinedBrightnessLimitNames predefinedBrightnessLimitNames : values()) {
            if (predefinedBrightnessLimitNames.rawName.equals(str)) {
                return predefinedBrightnessLimitNames;
            }
        }
        throw new IllegalArgumentException(str);
    }
}
