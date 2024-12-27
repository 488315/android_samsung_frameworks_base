package com.android.systemui.blur;

import android.view.SemBlurInfo;
import com.android.systemui.LsRune;

public final class BouncerColorCurve {
    public static float[] mThickLightAttrList = new float[7];
    public static float[] mDarkGrayishAttrList = new float[7];
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
            mThickLightAttrList = new float[]{53.0f, 0.0f, 8.0f, 0.0f, 255.0f, 1.0f, 255.0f};
            mDarkGrayishAttrList = new float[]{53.0f, 0.0f, 8.0f, 0.0f, 255.0f, 2.0f, 178.2f};
        } else {
            new SemBlurInfo.Builder(1);
            mThickLightAttrList = SemBlurInfo.Builder.getBlurPresetAttrs(133);
            mDarkGrayishAttrList = SemBlurInfo.Builder.getBlurPresetAttrs(137);
        }
    }

    public final void setFraction(float f, boolean z) {
        float[] fArr = z ? mThickLightAttrList : mDarkGrayishAttrList;
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
