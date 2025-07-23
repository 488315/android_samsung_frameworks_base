package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/* loaded from: classes6.dex */
public class SemContextSpecificPoseAlertAttribute extends SemContextAttribute {
    public static final Parcelable.Creator<SemContextSpecificPoseAlertAttribute> CREATOR = new Parcelable.Creator<SemContextSpecificPoseAlertAttribute>() { // from class: com.samsung.android.hardware.context.SemContextSpecificPoseAlertAttribute.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextSpecificPoseAlertAttribute createFromParcel(Parcel parcel) {
            return new SemContextSpecificPoseAlertAttribute(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextSpecificPoseAlertAttribute[] newArray(int i) {
            return new SemContextSpecificPoseAlertAttribute[i];
        }
    };
    private static final String TAG = "SemContextSpecificPoseAlertAttribute";
    private int mMaximumAngle;
    private int mMinimumAngle;
    private int mMovingThrs;
    private int mRetentionTime;

    SemContextSpecificPoseAlertAttribute() {
        this.mRetentionTime = 1;
        this.mMinimumAngle = -90;
        this.mMaximumAngle = 90;
        this.mMovingThrs = 1;
        setAttribute();
    }

    SemContextSpecificPoseAlertAttribute(Parcel parcel) {
        super(parcel);
        this.mRetentionTime = 1;
        this.mMinimumAngle = -90;
        this.mMaximumAngle = 90;
        this.mMovingThrs = 1;
    }

    public SemContextSpecificPoseAlertAttribute(int i, int i2, int i3, int i4) {
        this.mRetentionTime = i;
        this.mMinimumAngle = i2;
        this.mMaximumAngle = i3;
        this.mMovingThrs = i4;
        setAttribute();
    }

    @Override // com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        if (this.mRetentionTime < 0) {
            Log.e(TAG, "The retention time is wrong.");
            return false;
        }
        int i = this.mMinimumAngle;
        if (i < -90 || i > 90) {
            Log.e(TAG, "The minimum angle is wrong. The angle must be between -90 and 90.");
            return false;
        }
        int i2 = this.mMaximumAngle;
        if (i2 < -90 || i2 > 90) {
            Log.e(TAG, "The maximum angle is wrong. The angle must be between -90 and 90.");
            return false;
        }
        if (i > i2) {
            Log.e(TAG, "The minimum angle must be less than the maximum angle.");
            return false;
        }
        if (this.mMovingThrs >= 0) {
            return true;
        }
        Log.e(TAG, "The moving threshold is wrong.");
        return false;
    }

    private void setAttribute() {
        Bundle bundle = new Bundle();
        bundle.putInt("retention_time", this.mRetentionTime);
        bundle.putInt("minimum_angle", this.mMinimumAngle);
        bundle.putInt("maximum_angle", this.mMaximumAngle);
        bundle.putInt("moving_thrs", this.mMovingThrs);
        super.setAttribute(28, bundle);
    }
}
