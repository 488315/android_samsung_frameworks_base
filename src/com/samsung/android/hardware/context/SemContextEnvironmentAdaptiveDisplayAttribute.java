package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/* loaded from: classes6.dex */
public class SemContextEnvironmentAdaptiveDisplayAttribute extends SemContextAttribute {
    public static final Parcelable.Creator<SemContextEnvironmentAdaptiveDisplayAttribute> CREATOR = new Parcelable.Creator<SemContextEnvironmentAdaptiveDisplayAttribute>() { // from class: com.samsung.android.hardware.context.SemContextEnvironmentAdaptiveDisplayAttribute.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextEnvironmentAdaptiveDisplayAttribute createFromParcel(Parcel parcel) {
            return new SemContextEnvironmentAdaptiveDisplayAttribute(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextEnvironmentAdaptiveDisplayAttribute[] newArray(int i) {
            return new SemContextEnvironmentAdaptiveDisplayAttribute[i];
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

    SemContextEnvironmentAdaptiveDisplayAttribute(Parcel parcel) {
        super(parcel);
        this.mColorThreshold = 0.07f;
        this.mDuration = 35;
    }

    public SemContextEnvironmentAdaptiveDisplayAttribute(float f, int i) {
        this.mColorThreshold = f;
        this.mDuration = i;
        setAttribute();
    }

    @Override // com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        if (this.mColorThreshold < 0.0f) {
            Log.e(TAG, "The color threshold value is wrong.");
            return false;
        }
        int i = this.mDuration;
        if (i >= 0 && i <= 255) {
            return true;
        }
        Log.e(TAG, "The duration value is wrong.");
        return false;
    }

    private void setAttribute() {
        Bundle bundle = new Bundle();
        bundle.putFloat("color_threshold", this.mColorThreshold);
        bundle.putInt("duration", this.mDuration);
        Log.d(TAG, "setAttribute() mColorThreshold : " + bundle.getFloat("color_threshold"));
        Log.d(TAG, "setAttribute() mDuration : " + bundle.getInt("duration"));
        super.setAttribute(44, bundle);
    }
}
