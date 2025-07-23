package com.android.keyguard;

import android.util.MathUtils;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
}
