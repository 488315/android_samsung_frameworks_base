package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SemContextDeviceActivityDetector extends SemContextEventContext {
    public static final int ACTIVITY_MOVING = 2;
    public static final int ACTIVITY_STATIONARY = 1;
    public static final Parcelable.Creator<SemContextDeviceActivityDetector> CREATOR = new Parcelable.Creator<SemContextDeviceActivityDetector>() { // from class: com.samsung.android.hardware.context.SemContextDeviceActivityDetector.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextDeviceActivityDetector createFromParcel(Parcel parcel) {
            return new SemContextDeviceActivityDetector(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextDeviceActivityDetector[] newArray(int i) {
            return new SemContextDeviceActivityDetector[i];
        }
    };
    public static final int DETECTED = 1;
    public static final int ERROR_SENSOR_OUT = 3;
    public static final int NOT_DETECTED = 2;
    public static final int POSTURE_FACE_DOWN = 2048;
    public static final int POSTURE_FACE_UP = 1024;
    public static final int POSTURE_LANDSCAPE = 256;
    public static final int POSTURE_PORTRAIT = 512;
    private Bundle mContext;

    SemContextDeviceActivityDetector() {
        this.mContext = new Bundle();
    }

    SemContextDeviceActivityDetector(Parcel parcel) {
        readFromParcel(parcel);
    }

    public int getActivity() {
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
