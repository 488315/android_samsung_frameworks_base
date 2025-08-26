package com.android.systemui.qs.panels.ui.compose;

import androidx.compose.ui.unit.Dp;

/* loaded from: classes2.dex */
public final class Dimensions {
    public static final float FooterHeight;
    public static final Dimensions INSTANCE = new Dimensions();
    public static final float InterPageSpacing;

    static {
        Dp.Companion companion = Dp.Companion;
        FooterHeight = 48;
        InterPageSpacing = 16;
    }

    private Dimensions() {
    }
}
