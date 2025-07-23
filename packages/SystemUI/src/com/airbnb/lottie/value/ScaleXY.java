package com.airbnb.lottie.value;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class ScaleXY {
    public float scaleX;
    public float scaleY;

    public ScaleXY(float f, float f2) {
        this.scaleX = f;
        this.scaleY = f2;
    }

    public final String toString() {
        return this.scaleX + "x" + this.scaleY;
    }

    public ScaleXY() {
        this(1.0f, 1.0f);
    }
}
