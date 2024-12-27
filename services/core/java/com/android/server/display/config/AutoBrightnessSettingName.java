package com.android.server.display.config;

public enum AutoBrightnessSettingName {
    dim("dim"),
    normal("normal"),
    bright("bright");

    private final String rawName;

    AutoBrightnessSettingName(String str) {
        this.rawName = str;
    }

    public static AutoBrightnessSettingName fromString(String str) {
        for (AutoBrightnessSettingName autoBrightnessSettingName : values()) {
            if (autoBrightnessSettingName.rawName.equals(str)) {
                return autoBrightnessSettingName;
            }
        }
        throw new IllegalArgumentException(str);
    }

    public final String getRawName() {
        return this.rawName;
    }
}
