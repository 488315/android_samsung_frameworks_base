package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class SemContextHallSensor extends SemContextEventContext {
    public static final Parcelable.Creator<SemContextHallSensor> CREATOR =
            new Parcelable.Creator<
                    SemContextHallSensor>() { // from class:
                                              // com.samsung.android.hardware.context.SemContextHallSensor.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemContextHallSensor createFromParcel(Parcel in) {
                    return new SemContextHallSensor(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemContextHallSensor[] newArray(int size) {
                    return new SemContextHallSensor[size];
                }
            };
    public static final int MODE_CARRY = 0;
    public static final int MODE_KIOSK = 3;
    public static final int MODE_SMARTPHONE = 4;
    public static final int MODE_TABLE = 1;
    public static final int MODE_TABLET = 2;
    private Bundle mContext;

    public SemContextHallSensor() {
        this.mContext = new Bundle();
    }

    public SemContextHallSensor(Parcel src) {
        readFromParcel(src);
    }

    public int getType() {
        return this.mContext.getInt("Type");
    }

    @Override // com.samsung.android.hardware.context.SemContextEventContext
    public void setValues(Bundle context) {
        this.mContext = context;
    }

    @Override // com.samsung.android.hardware.context.SemContextEventContext, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBundle(this.mContext);
    }

    private void readFromParcel(Parcel src) {
        this.mContext = src.readBundle(getClass().getClassLoader());
    }
}
