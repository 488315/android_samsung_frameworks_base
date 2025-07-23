package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/* loaded from: classes6.dex */
public class SemContextInterruptedGyroAttribute extends SemContextAttribute {
    public static final Parcelable.Creator<SemContextInterruptedGyroAttribute> CREATOR = new Parcelable.Creator<SemContextInterruptedGyroAttribute>() { // from class: com.samsung.android.hardware.context.SemContextInterruptedGyroAttribute.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextInterruptedGyroAttribute createFromParcel(Parcel parcel) {
            return new SemContextInterruptedGyroAttribute(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextInterruptedGyroAttribute[] newArray(int i) {
            return new SemContextInterruptedGyroAttribute[i];
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

    SemContextInterruptedGyroAttribute(Parcel parcel) {
        super(parcel);
        this.mEnabled = 0;
    }

    public SemContextInterruptedGyroAttribute(int i) {
        this.mEnabled = i;
        setAttribute();
    }

    @Override // com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        int i = this.mEnabled;
        if (i >= 0 && i <= 1) {
            return true;
        }
        Log.e(TAG, "The interrupt gyro value is wrong.");
        return false;
    }

    private void setAttribute() {
        Bundle bundle = new Bundle();
        bundle.putInt("interrupt_gyro", this.mEnabled);
        super.setAttribute(48, bundle);
    }
}
