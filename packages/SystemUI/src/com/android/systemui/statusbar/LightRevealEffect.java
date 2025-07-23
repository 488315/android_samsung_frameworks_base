package com.android.systemui.statusbar;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface LightRevealEffect {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        public static float getPercentPastThreshold(float f, float f2) {
            float f3 = f - f2;
            if (f3 < 0.0f) {
                f3 = 0.0f;
            }
            return (1.0f / (1.0f - f2)) * f3;
        }
    }

    void setRevealAmountOnScrim(float f, LightRevealScrim lightRevealScrim);
}
