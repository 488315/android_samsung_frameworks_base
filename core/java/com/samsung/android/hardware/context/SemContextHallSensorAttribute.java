package com.samsung.android.hardware.context;

import android.p009os.Bundle;
import android.p009os.Parcel;
import android.p009os.Parcelable;
import android.util.Log;

/* loaded from: classes5.dex */
public class SemContextHallSensorAttribute extends SemContextAttribute {
    public static final Parcelable.Creator<SemContextHallSensorAttribute> CREATOR = new Parcelable.Creator<SemContextHallSensorAttribute>() { // from class: com.samsung.android.hardware.context.SemContextHallSensorAttribute.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextHallSensorAttribute createFromParcel(Parcel in) {
            return new SemContextHallSensorAttribute(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextHallSensorAttribute[] newArray(int size) {
            return new SemContextHallSensorAttribute[size];
        }
    };
    private static final String TAG = "SemContextHallSensorAttribute";
    private int mDisplayStatus;

    SemContextHallSensorAttribute() {
        this.mDisplayStatus = 0;
        setAttribute();
    }

    SemContextHallSensorAttribute(Parcel src) {
        super(src);
        this.mDisplayStatus = 0;
    }

    public SemContextHallSensorAttribute(int displayStatus) {
        this.mDisplayStatus = 0;
        this.mDisplayStatus = displayStatus;
        setAttribute();
        Log.m94d(TAG, "constructor + " + displayStatus);
    }

    @Override // com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        int i = this.mDisplayStatus;
        if (i < 0 || i > 4) {
            Log.m96e(TAG, "The display status is wrong.");
            return false;
        }
        return true;
    }

    private void setAttribute() {
        Bundle attribute = new Bundle();
        attribute.putInt("display_status", this.mDisplayStatus);
        Log.m94d(TAG, "hall sensor status   + " + attribute.getInt("display_status"));
        super.setAttribute(43, attribute);
    }
}
