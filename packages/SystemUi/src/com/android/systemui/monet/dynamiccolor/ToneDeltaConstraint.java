package com.android.systemui.monet.dynamiccolor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ToneDeltaConstraint {
    public final double delta;
    public final DynamicColor keepAway;
    public final TonePolarity keepAwayPolarity;

    public ToneDeltaConstraint(double d, DynamicColor dynamicColor, TonePolarity tonePolarity) {
        this.delta = d;
        this.keepAway = dynamicColor;
        this.keepAwayPolarity = tonePolarity;
    }
}
