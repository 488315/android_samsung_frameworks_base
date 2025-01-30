package com.android.systemui.statusbar;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface LightRevealEffect {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
