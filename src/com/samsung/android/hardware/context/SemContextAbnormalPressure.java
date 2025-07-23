package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SemContextAbnormalPressure extends SemContextEventContext {
    public static final Parcelable.Creator<SemContextAbnormalPressure> CREATOR = new Parcelable.Creator<SemContextAbnormalPressure>() { // from class: com.samsung.android.hardware.context.SemContextAbnormalPressure.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextAbnormalPressure createFromParcel(Parcel parcel) {
            return new SemContextAbnormalPressure(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextAbnormalPressure[] newArray(int i) {
            return new SemContextAbnormalPressure[i];
        }
    };
    private Bundle mContext;

    SemContextAbnormalPressure() {
        this.mContext = new Bundle();
    }

    SemContextAbnormalPressure(Parcel parcel) {
        readFromParcel(parcel);
    }

    public float getPressure() {
        return this.mContext.getFloat("barometer");
    }

    public float getAccX() {
        return this.mContext.getFloat("xaxis");
    }

    public float getAccY() {
        return this.mContext.getFloat("yaxis");
    }

    public float getAccZ() {
        return this.mContext.getFloat("zaxis");
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
