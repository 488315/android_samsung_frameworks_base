package com.android.systemui.blur;

import com.android.systemui.LsRune;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class BouncerColorCurve {
    public static final float RADIUS;
    public float mRadius = 0.0f;
    public float mSaturation = 0.0f;
    public float mCurve = 0.0f;
    public float mMinX = 0.0f;
    public float mMaxX = 0.0f;
    public float mMinY = 0.0f;
    public float mMaxY = 0.0f;
    public float mFraction = -1.0f;

    static {
        RADIUS = LsRune.SECURITY_CAPTURED_BLUR ? 53.0f : 150.0f;
    }

    public final void setFraction(float f, boolean z) {
        this.mFraction = f;
        this.mRadius = RADIUS * f;
        float f2 = 0.0f * f;
        this.mSaturation = f2;
        this.mCurve = 8.0f * f;
        this.mMinX = f2;
        this.mMinY = (z ? 1.0f : 2.0f) * f;
        this.mMaxX = 255.0f - f2;
        this.mMaxY = 255.0f - ((255.0f - (z ? 255.0f : 178.2f)) * f);
    }
}
