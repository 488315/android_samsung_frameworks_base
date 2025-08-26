package com.android.systemui.blur;

import android.view.SemBlurInfo;
import com.android.systemui.LsRune;

/* loaded from: classes.dex */
public class BouncerColorCurve {
    public final float[] mDarkGrayishAttrList;
    public final float[] mThickLightAttrList;
    public float mRadius = 0.0f;
    public float mSaturation = 0.0f;
    public float mCurve = 0.0f;
    public float mMinX = 0.0f;
    public float mMaxX = 0.0f;
    public float mMinY = 0.0f;
    public float mMaxY = 0.0f;
    public float mFraction = -1.0f;

    public BouncerColorCurve() {
        if (LsRune.SECURITY_CAPTURED_BLUR) {
            this.mThickLightAttrList = SemBlurInfo.Builder.getBlurPresetAttrs(103);
            this.mDarkGrayishAttrList = SemBlurInfo.Builder.getBlurPresetAttrs(122);
        } else {
            this.mThickLightAttrList = SemBlurInfo.Builder.getBlurPresetAttrs(103);
            this.mDarkGrayishAttrList = SemBlurInfo.Builder.getBlurPresetAttrs(118);
        }
    }

    public final void setFraction(float f, boolean z) {
        float[] fArr = z ? this.mThickLightAttrList : this.mDarkGrayishAttrList;
        this.mFraction = f;
        this.mRadius = fArr[0] * f;
        this.mSaturation = fArr[1] * f;
        this.mCurve = fArr[2] * f;
        this.mMinX = fArr[3] * f;
        this.mMinY = fArr[5] * f;
        this.mMaxX = 255.0f - ((255.0f - fArr[4]) * f);
        this.mMaxY = 255.0f - ((255.0f - fArr[6]) * f);
    }
}
