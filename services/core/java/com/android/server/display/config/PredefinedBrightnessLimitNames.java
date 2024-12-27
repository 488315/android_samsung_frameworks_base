package com.android.server.display.config;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
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
