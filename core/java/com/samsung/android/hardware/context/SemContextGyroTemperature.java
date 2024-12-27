package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SemContextGyroTemperature extends SemContextEventContext {
    public static final Parcelable.Creator<SemContextGyroTemperature> CREATOR =
            new Parcelable.Creator<
                    SemContextGyroTemperature>() { // from class:
                                                   // com.samsung.android.hardware.context.SemContextGyroTemperature.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemContextGyroTemperature createFromParcel(Parcel in) {
                    return new SemContextGyroTemperature(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemContextGyroTemperature[] newArray(int size) {
                    return new SemContextGyroTemperature[size];
                }
            };
    private Bundle mContext;

    SemContextGyroTemperature() {
        this.mContext = new Bundle();
    }

    SemContextGyroTemperature(Parcel src) {
        readFromParcel(src);
    }

    public double getGyroTemperature() {
        return this.mContext.getDouble("GyroTemperature");
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
