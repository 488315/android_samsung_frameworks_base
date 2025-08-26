package com.samsung.sesl.compose.component;

import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.StrokeCap;
import androidx.compose.ui.unit.Dp;
import com.samsung.sesl.compose.component.tokens.SeslPaletteTokens;

/* loaded from: classes4.dex */
public final class SeslProgressIndicatorDefaults {
    public static final float CircularProgressSmall;
    public static final SeslProgressIndicatorDefaults INSTANCE = new SeslProgressIndicatorDefaults();
    public static final long indeterminateCircularPointColor;

    static {
        Dp.Companion companion = Dp.Companion;
        CircularProgressSmall = 24;
        StrokeCap.Companion.getClass();
        SeslPaletteTokens.INSTANCE.getClass();
        indeterminateCircularPointColor = ColorKt.Color(4280534162L);
        int i = SeslProgressIndicatorDefaults$TrackWidthSelector$1.$r8$clinit;
    }

    private SeslProgressIndicatorDefaults() {
    }
}
