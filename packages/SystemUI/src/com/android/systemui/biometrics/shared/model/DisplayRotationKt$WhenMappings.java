package com.android.systemui.biometrics.shared.model;

public abstract /* synthetic */ class DisplayRotationKt$WhenMappings {
    public static final /* synthetic */ int[] $EnumSwitchMapping$0;

    static {
        int[] iArr = new int[DisplayRotation.values().length];
        try {
            iArr[DisplayRotation.ROTATION_0.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            iArr[DisplayRotation.ROTATION_90.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            iArr[DisplayRotation.ROTATION_180.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            iArr[DisplayRotation.ROTATION_270.ordinal()] = 4;
        } catch (NoSuchFieldError unused4) {
        }
        $EnumSwitchMapping$0 = iArr;
    }
}
