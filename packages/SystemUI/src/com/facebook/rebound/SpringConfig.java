package com.facebook.rebound;

/* loaded from: classes3.dex */
public class SpringConfig {
    public static final SpringConfig defaultConfig = new SpringConfig(OrigamiValueConverter.tensionFromOrigamiValue(40.0d), OrigamiValueConverter.frictionFromOrigamiValue(7.0d));
    public double friction;
    public double tension;

    public SpringConfig(double d, double d2) {
        this.tension = d;
        this.friction = d2;
    }
}
