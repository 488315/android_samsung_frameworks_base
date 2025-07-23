package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SemContextSensorStatusCheck extends SemContextEventContext {
    public static final int ACC_DATA_DEFAULT = 40000;
    public static final int ACC_DATA_OFFSET = 2;
    public static final int ACC_DATA_STUCK = 1;
    public static final Parcelable.Creator<SemContextSensorStatusCheck> CREATOR = new Parcelable.Creator<SemContextSensorStatusCheck>() { // from class: com.samsung.android.hardware.context.SemContextSensorStatusCheck.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextSensorStatusCheck createFromParcel(Parcel parcel) {
            return new SemContextSensorStatusCheck(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextSensorStatusCheck[] newArray(int i) {
            return new SemContextSensorStatusCheck[i];
        }
    };
    public static final int SENSORHUB_RESET = 3;
    public static final int SENSOR_DATA_NORMAL = 0;
    private Bundle mContext;

    SemContextSensorStatusCheck() {
        this.mContext = new Bundle();
    }

    SemContextSensorStatusCheck(Parcel parcel) {
        readFromParcel(parcel);
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
