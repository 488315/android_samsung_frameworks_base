package com.android.systemui.biometrics.shared.model;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
