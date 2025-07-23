package com.google.ux.material.libmonet.dynamiccolor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class ToneDeltaPair {
    public final double delta;
    public final TonePolarity polarity;
    public final DynamicColor roleA;
    public final DynamicColor roleB;
    public final boolean stayTogether;

    public ToneDeltaPair(DynamicColor dynamicColor, DynamicColor dynamicColor2, double d, TonePolarity tonePolarity, boolean z) {
        this.roleA = dynamicColor;
        this.roleB = dynamicColor2;
        this.delta = d;
        this.polarity = tonePolarity;
        this.stayTogether = z;
    }
}
