package com.facebook.rebound;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SpringConfig {
    public static final SpringConfig defaultConfig = new SpringConfig(OrigamiValueConverter.tensionFromOrigamiValue(40.0d), OrigamiValueConverter.frictionFromOrigamiValue(7.0d));
    public double friction;
    public double tension;

    public SpringConfig(double d, double d2) {
        this.tension = d;
        this.friction = d2;
    }
}
