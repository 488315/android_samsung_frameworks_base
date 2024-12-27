package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/* loaded from: classes6.dex */
public class SemContextEnvironmentAdaptiveDisplayAttribute extends SemContextAttribute {
    public static final Parcelable.Creator<SemContextEnvironmentAdaptiveDisplayAttribute> CREATOR =
            new Parcelable.Creator<
                    SemContextEnvironmentAdaptiveDisplayAttribute>() { // from class:
                                                                       // com.samsung.android.hardware.context.SemContextEnvironmentAdaptiveDisplayAttribute.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemContextEnvironmentAdaptiveDisplayAttribute createFromParcel(Parcel in) {
                    return new SemContextEnvironmentAdaptiveDisplayAttribute(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemContextEnvironmentAdaptiveDisplayAttribute[] newArray(int size) {
                    return new SemContextEnvironmentAdaptiveDisplayAttribute[size];
                }
            };
    private static final String TAG = "SemContextEnvironmentAdaptiveDisplayAttribute";
    private float mColorThreshold;
    private int mDuration;

    SemContextEnvironmentAdaptiveDisplayAttribute() {
        this.mColorThreshold = 0.07f;
        this.mDuration = 35;
        setAttribute();
    }

    SemContextEnvironmentAdaptiveDisplayAttribute(Parcel src) {
        super(src);
        this.mColorThreshold = 0.07f;
        this.mDuration = 35;
    }

    public SemContextEnvironmentAdaptiveDisplayAttribute(float colorThreshold, int duration) {
        this.mColorThreshold = 0.07f;
        this.mDuration = 35;
        this.mColorThreshold = colorThreshold;
        this.mDuration = duration;
        setAttribute();
    }

    @Override // com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        if (this.mColorThreshold < 0.0f) {
            Log.e(TAG, "The color threshold value is wrong.");
            return false;
        }
        if (this.mDuration < 0 || this.mDuration > 255) {
            Log.e(TAG, "The duration value is wrong.");
            return false;
        }
        return true;
    }

    private void setAttribute() {
        Bundle attribute = new Bundle();
        attribute.putFloat("color_threshold", this.mColorThreshold);
        attribute.putInt("duration", this.mDuration);
        Log.d(TAG, "setAttribute() mColorThreshold : " + attribute.getFloat("color_threshold"));
        Log.d(TAG, "setAttribute() mDuration : " + attribute.getInt("duration"));
        super.setAttribute(44, attribute);
    }
}
