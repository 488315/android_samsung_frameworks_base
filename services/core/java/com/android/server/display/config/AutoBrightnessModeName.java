package com.android.server.display.config;

public enum AutoBrightnessModeName {
    _default("default"),
    idle("idle"),
    doze("doze");

    private final String rawName;

    AutoBrightnessModeName(String str) {
        this.rawName = str;
    }

    public static AutoBrightnessModeName fromString(String str) {
        for (AutoBrightnessModeName autoBrightnessModeName : values()) {
            if (autoBrightnessModeName.rawName.equals(str)) {
                return autoBrightnessModeName;
            }
        }
        throw new IllegalArgumentException(str);
    }

    public final String getRawName() {
        return this.rawName;
    }
}
