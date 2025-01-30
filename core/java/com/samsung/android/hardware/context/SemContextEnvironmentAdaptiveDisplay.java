package com.samsung.android.hardware.context;

import android.hardware.gnss.GnssSignalType;
import android.p009os.Bundle;
import android.p009os.Parcel;
import android.p009os.Parcelable;

/* loaded from: classes5.dex */
public class SemContextEnvironmentAdaptiveDisplay extends SemContextEventContext {
    public static final Parcelable.Creator<SemContextEnvironmentAdaptiveDisplay> CREATOR = new Parcelable.Creator<SemContextEnvironmentAdaptiveDisplay>() { // from class: com.samsung.android.hardware.context.SemContextEnvironmentAdaptiveDisplay.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextEnvironmentAdaptiveDisplay createFromParcel(Parcel in) {
            return new SemContextEnvironmentAdaptiveDisplay(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextEnvironmentAdaptiveDisplay[] newArray(int size) {
            return new SemContextEnvironmentAdaptiveDisplay[size];
        }
    };
    private Bundle mContext;

    SemContextEnvironmentAdaptiveDisplay() {
        this.mContext = new Bundle();
    }

    SemContextEnvironmentAdaptiveDisplay(Parcel src) {
        readFromParcel(src);
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
    public void setValues(Bundle context) {
        this.mContext = context;
    }

    @Override // com.samsung.android.hardware.context.SemContextEventContext, android.p009os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBundle(this.mContext);
    }

    private void readFromParcel(Parcel src) {
        this.mContext = src.readBundle(getClass().getClassLoader());
    }
}
