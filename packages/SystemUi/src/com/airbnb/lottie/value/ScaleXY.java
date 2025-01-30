package com.airbnb.lottie.value;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ScaleXY {
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
