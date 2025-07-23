package com.samsung.android.hardware.context;

import android.hardware.gnss.GnssSignalType;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SemContextEnvironmentAdaptiveDisplay extends SemContextEventContext {
    public static final Parcelable.Creator<SemContextEnvironmentAdaptiveDisplay> CREATOR = new Parcelable.Creator<SemContextEnvironmentAdaptiveDisplay>() { // from class: com.samsung.android.hardware.context.SemContextEnvironmentAdaptiveDisplay.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextEnvironmentAdaptiveDisplay createFromParcel(Parcel parcel) {
            return new SemContextEnvironmentAdaptiveDisplay(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextEnvironmentAdaptiveDisplay[] newArray(int i) {
            return new SemContextEnvironmentAdaptiveDisplay[i];
        }
    };
    private Bundle mContext;

    SemContextEnvironmentAdaptiveDisplay() {
        this.mContext = new Bundle();
    }

    SemContextEnvironmentAdaptiveDisplay(Parcel parcel) {
        readFromParcel(parcel);
    }

    public float getRed() {
        return this.mContext.getFloat("R");
    }

    public float getGreen() {
        return this.mContext.getFloat("G");
    }

    public float getBlue() {
        return this.mContext.getFloat(GnssSignalType.CODE_TYPE_B);
    }

    public long getLux() {
        return this.mContext.getLong("Lux");
    }

    public int getCCT() {
        return this.mContext.getInt("CCT");
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
