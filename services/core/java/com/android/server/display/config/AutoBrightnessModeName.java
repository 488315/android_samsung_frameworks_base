package com.android.server.display.config;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
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
