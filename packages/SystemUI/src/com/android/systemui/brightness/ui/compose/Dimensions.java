package com.android.systemui.brightness.ui.compose;

import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.DpKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class Dimensions {
    public static final Dimensions INSTANCE = new Dimensions();
    public static final float IconPadding;
    public static final long IconSize;
    public static final long SliderBackgroundFrameSize;
    public static final float SliderBackgroundRoundedCorner;
    public static final float SliderTrackRoundedCorner;
    public static final float ThumbTrackGapSize;

    static {
        Dp.Companion companion = Dp.Companion;
        float f = 6;
        SliderBackgroundFrameSize = DpKt.m838DpSizeYgX7TsA(10, f);
        SliderBackgroundRoundedCorner = 24;
        SliderTrackRoundedCorner = 12;
        float f2 = 28;
        IconSize = DpKt.m838DpSizeYgX7TsA(f2, f2);
        IconPadding = f;
        ThumbTrackGapSize = f;
    }

    private Dimensions() {
    }
}
