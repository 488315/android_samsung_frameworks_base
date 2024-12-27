package com.android.server.display.config;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
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
