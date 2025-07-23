package com.android.systemui.statusbar;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class ScalingDrawableWrapper extends DrawableWrapper {
    public Drawable mCloneDrawable;
    public final float mScaleFactor;

    public ScalingDrawableWrapper(Drawable drawable, float f) {
        super(drawable);
        this.mScaleFactor = f;
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final int getIntrinsicHeight() {
        return (int) (super.getIntrinsicHeight() * this.mScaleFactor);
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final int getIntrinsicWidth() {
        return (int) (super.getIntrinsicWidth() * this.mScaleFactor);
    }
}
