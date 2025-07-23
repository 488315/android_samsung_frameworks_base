package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/* loaded from: classes6.dex */
public class SemContextStepCountAlertAttribute extends SemContextAttribute {
    public static final Parcelable.Creator<SemContextStepCountAlertAttribute> CREATOR = new Parcelable.Creator<SemContextStepCountAlertAttribute>() { // from class: com.samsung.android.hardware.context.SemContextStepCountAlertAttribute.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextStepCountAlertAttribute createFromParcel(Parcel parcel) {
            return new SemContextStepCountAlertAttribute(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextStepCountAlertAttribute[] newArray(int i) {
            return new SemContextStepCountAlertAttribute[i];
        }
    };
    public static final int INTERRUPT_GYRO_DISABLE_SYSFS_NODE = 0;
    public static final int INTERRUPT_GYRO_ENABLE_SYSFS_NODE = 1;
    private static final String TAG = "SemContextStepCountAlertAttribute";
    private int mStepCount;

    SemContextStepCountAlertAttribute() {
        this.mStepCount = 10;
        setAttribute();
    }

    SemContextStepCountAlertAttribute(Parcel parcel) {
        super(parcel);
        this.mStepCount = 10;
    }

    public SemContextStepCountAlertAttribute(int i) {
        this.mStepCount = i;
        setAttribute();
    }

    public int getStepCount() {
        return this.mStepCount;
    }

    @Override // com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        if (this.mStepCount >= 0) {
            return true;
        }
        Log.e(TAG, "The step count is wrong.");
        return false;
    }

    private void setAttribute() {
        Bundle bundle = new Bundle();
        bundle.putInt("step_count", this.mStepCount);
        super.setAttribute(3, bundle);
    }
}
