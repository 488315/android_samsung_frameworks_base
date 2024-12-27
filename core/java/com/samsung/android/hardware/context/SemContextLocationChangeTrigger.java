package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SemContextLocationChangeTrigger extends SemContextEventContext {
    public static final Parcelable.Creator<SemContextLocationChangeTrigger> CREATOR = new Parcelable.Creator<SemContextLocationChangeTrigger>() { // from class: com.samsung.android.hardware.context.SemContextLocationChangeTrigger.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextLocationChangeTrigger createFromParcel(Parcel in) {
            return new SemContextLocationChangeTrigger(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextLocationChangeTrigger[] newArray(int size) {
            return new SemContextLocationChangeTrigger[size];
        }
    };
    public static final int RESULT_FALSE = 2;
    public static final int RESULT_SENSOR_OUT = 3;
    public static final int RESULT_TRUE = 1;
    public static final int TYPE_MOVING = 2;
    public static final int TYPE_MOVING_AUTO = 3;
    public static final int TYPE_STATIONARY = 1;
    private Bundle mContext;

    SemContextLocationChangeTrigger() {
        this.mContext = new Bundle();
    }

    SemContextLocationChangeTrigger(Parcel src) {
        readFromParcel(src);
    }

    public int getProperty() {
        return this.mContext.getInt("property");
    }

    public int getResult() {
        return this.mContext.getInt("result");
    }

    public int getDuration() {
        return this.mContext.getInt("duration");
    }

    public int getReserved() {
        return this.mContext.getInt("reserved");
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
