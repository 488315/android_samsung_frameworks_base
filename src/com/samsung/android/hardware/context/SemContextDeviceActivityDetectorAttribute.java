package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/* loaded from: classes6.dex */
public class SemContextDeviceActivityDetectorAttribute extends SemContextAttribute {
    public static final Parcelable.Creator<SemContextDeviceActivityDetectorAttribute> CREATOR = new Parcelable.Creator<SemContextDeviceActivityDetectorAttribute>() { // from class: com.samsung.android.hardware.context.SemContextDeviceActivityDetectorAttribute.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextDeviceActivityDetectorAttribute createFromParcel(Parcel parcel) {
            return new SemContextDeviceActivityDetectorAttribute(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextDeviceActivityDetectorAttribute[] newArray(int i) {
            return new SemContextDeviceActivityDetectorAttribute[i];
        }
    };
    private static final String TAG = "SemContextDeviceActivityDetectorAttribute";
    private int mActivity;
    private int mDuration;
    private boolean mNeedsRequestToUpdate;
    private int mPosture;

    SemContextDeviceActivityDetectorAttribute() {
        this.mActivity = 1;
        this.mDuration = 10;
        this.mPosture = 0;
        this.mNeedsRequestToUpdate = false;
        setAttribute();
    }

    SemContextDeviceActivityDetectorAttribute(Parcel parcel) {
        super(parcel);
        this.mActivity = 1;
        this.mDuration = 10;
        this.mPosture = 0;
        this.mNeedsRequestToUpdate = false;
    }

    public SemContextDeviceActivityDetectorAttribute(int i, int i2, boolean z) {
        this.mPosture = (65280 & i) >> 8;
        this.mDuration = i2;
        this.mActivity = i & 255;
        this.mNeedsRequestToUpdate = z;
        Log.d(TAG, "SemContextDeviceActivityDetectorAttribute activity, posture : " + this.mActivity + " , " + this.mPosture);
        setAttribute();
    }

    @Override // com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        int i = this.mActivity;
        if (i < 1 || i > 2) {
            Log.e(TAG, "SemContextDeviceActivityDetector activity is wrong.");
            return false;
        }
        if ((i == 1 && !this.mNeedsRequestToUpdate) || (i == 2 && this.mDuration > 0)) {
            Log.e(TAG, "This option is NOT supported, activity : " + this.mActivity + ", duration : " + this.mDuration + ", request : " + this.mNeedsRequestToUpdate);
            return false;
        }
        Log.d(TAG, "SemContextDeviceActivityDetector checkAttribute : " + this.mActivity);
        return this.mDuration >= 0;
    }

    private void setAttribute() {
        Bundle bundle = new Bundle();
        int i = this.mActivity;
        if (i == 2 && !this.mNeedsRequestToUpdate) {
            bundle.putInt("trigger_type", 3);
            bundle.putInt("duration", 0);
        } else if (i == 2 && this.mNeedsRequestToUpdate) {
            bundle.putInt("trigger_type", 2);
            bundle.putInt("duration", 0);
        } else if (i == 1 && this.mNeedsRequestToUpdate) {
            bundle.putInt("trigger_type", 1);
            bundle.putInt("duration", this.mDuration);
        } else {
            Log.e(TAG, "The attribute is wrong.");
            return;
        }
        bundle.putInt("posture", this.mPosture);
        super.setAttribute(54, bundle);
    }
}
