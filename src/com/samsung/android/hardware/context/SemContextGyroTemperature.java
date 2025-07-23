package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SemContextGyroTemperature extends SemContextEventContext {
    public static final Parcelable.Creator<SemContextGyroTemperature> CREATOR = new Parcelable.Creator<SemContextGyroTemperature>() { // from class: com.samsung.android.hardware.context.SemContextGyroTemperature.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextGyroTemperature createFromParcel(Parcel parcel) {
            return new SemContextGyroTemperature(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextGyroTemperature[] newArray(int i) {
            return new SemContextGyroTemperature[i];
        }
    };
    private Bundle mContext;

    SemContextGyroTemperature() {
        this.mContext = new Bundle();
    }

    SemContextGyroTemperature(Parcel parcel) {
        readFromParcel(parcel);
    }

    public double getGyroTemperature() {
        return this.mContext.getDouble("GyroTemperature");
    }

    @Override // com.samsung.android.hardware.context.SemContextEventContext
    public void setValues(Bundle bundle) {
        this.mContext = bundle;
    }

    @Override // com.samsung.android.hardware.context.SemContextEventContext, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBundle(this.mContext);
    }

    private void readFromParcel(Parcel parcel) {
        this.mContext = parcel.readBundle(getClass().getClassLoader());
    }
}
