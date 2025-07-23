package com.android.compose.gesture;

import androidx.compose.foundation.gestures.Orientation;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
