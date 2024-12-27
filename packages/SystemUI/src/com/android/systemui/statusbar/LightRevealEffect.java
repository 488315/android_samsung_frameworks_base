package com.android.systemui.statusbar;

import kotlin.ranges.RangesKt___RangesKt;

public interface LightRevealEffect {
    public static final Companion Companion = Companion.$$INSTANCE;

    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        public static float getPercentPastThreshold(float f, float f2) {
            return (1.0f / (1.0f - f2)) * RangesKt___RangesKt.coerceAtLeast(f - f2, 0.0f);
        }
    }

    void setRevealAmountOnScrim(float f, LightRevealScrim lightRevealScrim);
}
