package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/* loaded from: classes6.dex */
public class SemContextHallSensorAttribute extends SemContextAttribute {
    public static final Parcelable.Creator<SemContextHallSensorAttribute> CREATOR = new Parcelable.Creator<SemContextHallSensorAttribute>() { // from class: com.samsung.android.hardware.context.SemContextHallSensorAttribute.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextHallSensorAttribute createFromParcel(Parcel parcel) {
            return new SemContextHallSensorAttribute(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextHallSensorAttribute[] newArray(int i) {
            return new SemContextHallSensorAttribute[i];
        }
    };
    private static final String TAG = "SemContextHallSensorAttribute";
    private int mDisplayStatus;

    SemContextHallSensorAttribute() {
        this.mDisplayStatus = 0;
        setAttribute();
    }

    SemContextHallSensorAttribute(Parcel parcel) {
        super(parcel);
        this.mDisplayStatus = 0;
    }

    public SemContextHallSensorAttribute(int i) {
        this.mDisplayStatus = i;
        setAttribute();
        Log.d(TAG, "constructor + " + i);
    }

    @Override // com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        int i = this.mDisplayStatus;
        if (i >= 0 && i <= 4) {
            return true;
        }
        Log.e(TAG, "The display status is wrong.");
        return false;
    }

    private void setAttribute() {
        Bundle bundle = new Bundle();
        bundle.putInt("display_status", this.mDisplayStatus);
        Log.d(TAG, "hall sensor status   + " + bundle.getInt("display_status"));
        super.setAttribute(43, bundle);
    }
}
