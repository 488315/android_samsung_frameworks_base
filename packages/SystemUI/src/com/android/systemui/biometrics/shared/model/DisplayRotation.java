package com.android.systemui.biometrics.shared.model;

import kotlin.enums.EnumEntriesKt;

public final class DisplayRotation {
    public static final /* synthetic */ DisplayRotation[] $VALUES;
    public static final DisplayRotation ROTATION_0;
    public static final DisplayRotation ROTATION_180;
    public static final DisplayRotation ROTATION_270;
    public static final DisplayRotation ROTATION_90;

    static {
        DisplayRotation displayRotation = new DisplayRotation("ROTATION_0", 0);
        ROTATION_0 = displayRotation;
        DisplayRotation displayRotation2 = new DisplayRotation("ROTATION_90", 1);
        ROTATION_90 = displayRotation2;
        DisplayRotation displayRotation3 = new DisplayRotation("ROTATION_180", 2);
        ROTATION_180 = displayRotation3;
        DisplayRotation displayRotation4 = new DisplayRotation("ROTATION_270", 3);
        ROTATION_270 = displayRotation4;
        DisplayRotation[] displayRotationArr = {displayRotation, displayRotation2, displayRotation3, displayRotation4};
        $VALUES = displayRotationArr;
        EnumEntriesKt.enumEntries(displayRotationArr);
    }

    private DisplayRotation(String str, int i) {
    }

    public static DisplayRotation valueOf(String str) {
        return (DisplayRotation) Enum.valueOf(DisplayRotation.class, str);
    }

    public static DisplayRotation[] values() {
        return (DisplayRotation[]) $VALUES.clone();
    }
}
