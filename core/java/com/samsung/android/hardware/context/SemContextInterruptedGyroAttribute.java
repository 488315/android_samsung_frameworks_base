package com.samsung.android.hardware.context;

import android.p009os.Bundle;
import android.p009os.Parcel;
import android.p009os.Parcelable;
import android.util.Log;

/* loaded from: classes5.dex */
public class SemContextInterruptedGyroAttribute extends SemContextAttribute {
    public static final Parcelable.Creator<SemContextInterruptedGyroAttribute> CREATOR = new Parcelable.Creator<SemContextInterruptedGyroAttribute>() { // from class: com.samsung.android.hardware.context.SemContextInterruptedGyroAttribute.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextInterruptedGyroAttribute createFromParcel(Parcel in) {
            return new SemContextInterruptedGyroAttribute(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextInterruptedGyroAttribute[] newArray(int size) {
            return new SemContextInterruptedGyroAttribute[size];
        }
    };
    public static final int INTERRUPTED_GYRO_DISABLE_SYSFS_NODE = 0;
    public static final int INTERRUPTED_GYRO_ENABLE_SYSFS_NODE = 1;
    private static final String TAG = "SemContextInterruptedGyroAttribute";
    private int mEnabled;

    SemContextInterruptedGyroAttribute() {
        this.mEnabled = 0;
        setAttribute();
    }

    SemContextInterruptedGyroAttribute(Parcel src) {
        super(src);
        this.mEnabled = 0;
    }

    public SemContextInterruptedGyroAttribute(int enable) {
        this.mEnabled = 0;
        this.mEnabled = enable;
        setAttribute();
    }

    @Override // com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        int i = this.mEnabled;
        if (i >= 0 && i <= 1) {
            return true;
        }
        Log.m96e(TAG, "The interrupt gyro value is wrong.");
        return false;
    }

    private void setAttribute() {
        Bundle attribute = new Bundle();
        attribute.putInt("interrupt_gyro", this.mEnabled);
        super.setAttribute(48, attribute);
    }
}
