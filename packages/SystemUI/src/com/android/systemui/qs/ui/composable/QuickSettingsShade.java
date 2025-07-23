package com.android.systemui.qs.ui.composable;

import androidx.compose.ui.unit.Dp;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class QuickSettingsShade {
    public static final QuickSettingsShade INSTANCE = new QuickSettingsShade();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Dimensions {
        public static final Dimensions INSTANCE = new Dimensions();
        public static final float Padding;

        static {
            Dp.Companion companion = Dp.Companion;
            Padding = 16;
        }

        private Dimensions() {
        }
    }

    private QuickSettingsShade() {
    }
}
