package com.facebook.rebound;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
