package com.android.keyguard;

import android.util.MathUtils;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class BouncerPanelExpansionCalculator {
    public static final /* synthetic */ int $r8$clinit = 0;

    static {
        new BouncerPanelExpansionCalculator();
    }

    private BouncerPanelExpansionCalculator() {
    }

    public static final float aboutToShowBouncerProgress(float f) {
        return MathUtils.constrain((f - 0.9f) / 0.1f, 0.0f, 1.0f);
    }

    public static final float showBouncerProgress(float f) {
        if (f >= 0.9f) {
            return 1.0f;
        }
        if (f < 0.6d) {
            return 0.0f;
        }
        return (f - 0.6f) / 0.3f;
    }
}
