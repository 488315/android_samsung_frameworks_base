package com.samsung.android.hardware.context;

import android.p009os.Bundle;
import android.p009os.Parcel;
import android.p009os.Parcelable;

/* loaded from: classes5.dex */
public class SemContextSensorStatusCheck extends SemContextEventContext {
    public static final int ACC_DATA_DEFAULT = 40000;
    public static final int ACC_DATA_OFFSET = 2;
    public static final int ACC_DATA_STUCK = 1;
    public static final Parcelable.Creator<SemContextSensorStatusCheck> CREATOR = new Parcelable.Creator<SemContextSensorStatusCheck>() { // from class: com.samsung.android.hardware.context.SemContextSensorStatusCheck.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextSensorStatusCheck createFromParcel(Parcel in) {
            return new SemContextSensorStatusCheck(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextSensorStatusCheck[] newArray(int size) {
            return new SemContextSensorStatusCheck[size];
        }
    };
    public static final int SENSORHUB_RESET = 3;
    public static final int SENSOR_DATA_NORMAL = 0;
    private Bundle mContext;

    SemContextSensorStatusCheck() {
        this.mContext = new Bundle();
    }

    SemContextSensorStatusCheck(Parcel src) {
        readFromParcel(src);
    }

    public int getXAxis() {
        return this.mContext.getInt("XAxis");
    }

    public int getYAxis() {
        return this.mContext.getInt("YAxis");
    }

    public int getZAxis() {
        return this.mContext.getInt("ZAxis");
    }

    public int getStatus() {
        return this.mContext.getInt("Status");
    }

    public int getResetCount() {
        return this.mContext.getInt("ResetCnt");
    }

    public long getSensorHubLastEventTimeStamp() {
        return this.mContext.getLong("SensorHubLastEventTime");
    }

    public long[] getSensorHubResetTimeStampArray() {
        return this.mContext.getLongArray("SensorHubResetTimeStampArray");
    }

    public int getSensorHubResetTimeStampArraySize() {
        return this.mContext.getInt("SensorHubResetTimeStampArraySize");
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
