package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class SemContextHallSensorAttribute extends SemContextAttribute {
    public static final Parcelable.Creator<SemContextHallSensorAttribute> CREATOR =
            new Parcelable.Creator<
                    SemContextHallSensorAttribute>() { // from class:
                                                       // com.samsung.android.hardware.context.SemContextHallSensorAttribute.1
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
        Log.d(TAG, "constructor + " + displayStatus);
    }

    @Override // com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        if (this.mDisplayStatus < 0 || this.mDisplayStatus > 4) {
            Log.e(TAG, "The display status is wrong.");
            return false;
        }
        return true;
    }

    private void setAttribute() {
        Bundle attribute = new Bundle();
        attribute.putInt("display_status", this.mDisplayStatus);
        Log.d(TAG, "hall sensor status   + " + attribute.getInt("display_status"));
        super.setAttribute(43, attribute);
    }
}
