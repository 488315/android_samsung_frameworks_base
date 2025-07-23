package com.google.android.material.carousel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class CarouselOrientationHelper {
    public final int orientation;

    public abstract int getParentBottom();

    public abstract int getParentLeft();

    public abstract int getParentRight();

    public abstract int getParentStart();

    public abstract int getParentTop();

    private CarouselOrientationHelper(int i) {
        this.orientation = i;
    }
}
