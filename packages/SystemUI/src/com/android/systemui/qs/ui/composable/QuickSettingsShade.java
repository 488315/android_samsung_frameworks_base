package com.android.systemui.qs.ui.composable;

import androidx.compose.ui.unit.Dp;

/* loaded from: classes2.dex */
public final class QuickSettingsShade {
    public static final QuickSettingsShade INSTANCE = new QuickSettingsShade();

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
