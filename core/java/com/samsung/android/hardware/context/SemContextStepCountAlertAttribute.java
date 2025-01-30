package com.samsung.android.hardware.context;

import android.p009os.Bundle;
import android.p009os.Parcel;
import android.p009os.Parcelable;
import android.util.Log;

/* loaded from: classes5.dex */
public class SemContextStepCountAlertAttribute extends SemContextAttribute {
    public static final Parcelable.Creator<SemContextStepCountAlertAttribute> CREATOR = new Parcelable.Creator<SemContextStepCountAlertAttribute>() { // from class: com.samsung.android.hardware.context.SemContextStepCountAlertAttribute.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextStepCountAlertAttribute createFromParcel(Parcel in) {
            return new SemContextStepCountAlertAttribute(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextStepCountAlertAttribute[] newArray(int size) {
            return new SemContextStepCountAlertAttribute[size];
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

    SemContextStepCountAlertAttribute(Parcel src) {
        super(src);
        this.mStepCount = 10;
    }

    public SemContextStepCountAlertAttribute(int stepCount) {
        this.mStepCount = 10;
        this.mStepCount = stepCount;
        setAttribute();
    }

    public int getStepCount() {
        return this.mStepCount;
    }

    @Override // com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        if (this.mStepCount < 0) {
            Log.m96e(TAG, "The step count is wrong.");
            return false;
        }
        return true;
    }

    private void setAttribute() {
        Bundle attribute = new Bundle();
        attribute.putInt("step_count", this.mStepCount);
        super.setAttribute(3, attribute);
    }
}
