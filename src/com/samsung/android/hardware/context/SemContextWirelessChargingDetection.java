package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SemContextWirelessChargingDetection extends SemContextEventContext {
    public static final Parcelable.Creator<SemContextWirelessChargingDetection> CREATOR = new Parcelable.Creator<SemContextWirelessChargingDetection>() { // from class: com.samsung.android.hardware.context.SemContextWirelessChargingDetection.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextWirelessChargingDetection createFromParcel(Parcel parcel) {
            return new SemContextWirelessChargingDetection(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextWirelessChargingDetection[] newArray(int i) {
            return new SemContextWirelessChargingDetection[i];
        }
    };
    public static final int MOVE = 1;
    public static final int NOMOVE = 0;
    private Bundle mContext;

    SemContextWirelessChargingDetection() {
        this.mContext = new Bundle();
    }

    SemContextWirelessChargingDetection(Parcel parcel) {
        readFromParcel(parcel);
    }

    public int getAction() {
        return this.mContext.getInt("Status");
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
