package com.android.compose.gesture;

import androidx.compose.foundation.gestures.Orientation;

/* loaded from: classes.dex */
public abstract /* synthetic */ class OrientationAware$WhenMappings {
    public static final /* synthetic */ int[] $EnumSwitchMapping$0;

    static {
        int[] iArr = new int[Orientation.values().length];
        try {
            iArr[Orientation.Horizontal.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            iArr[Orientation.Vertical.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        $EnumSwitchMapping$0 = iArr;
    }
}
