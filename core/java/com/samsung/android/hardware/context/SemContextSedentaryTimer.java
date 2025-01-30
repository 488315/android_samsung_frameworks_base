package com.samsung.android.hardware.context;

import android.p009os.Bundle;
import android.p009os.Parcel;
import android.p009os.Parcelable;

@Deprecated(forRemoval = true, since = "15.5")
/* loaded from: classes5.dex */
public class SemContextSedentaryTimer extends SemContextEventContext {
    public static final Parcelable.Creator<SemContextSedentaryTimer> CREATOR = new Parcelable.Creator<SemContextSedentaryTimer>() { // from class: com.samsung.android.hardware.context.SemContextSedentaryTimer.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextSedentaryTimer createFromParcel(Parcel in) {
            return new SemContextSedentaryTimer(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextSedentaryTimer[] newArray(int size) {
            return new SemContextSedentaryTimer[size];
        }
    };
    public static final int DEVICE_TYPE_MOBILE = 1;
    public static final int DEVICE_TYPE_WEARABLE = 2;
    public static final int STATUS_SEDENTARY = 2;
    public static final int STATUS_SEDENTARY_BREAK = 3;
    public static final int STATUS_SEDENTARY_START = 1;
    private Bundle mContext;

    SemContextSedentaryTimer() {
        this.mContext = new Bundle();
    }

    SemContextSedentaryTimer(Parcel src) {
        readFromParcel(src);
    }

    public int getDuration() {
        return this.mContext.getInt("InactiveTimeDuration");
    }

    public int getStatus() {
        return this.mContext.getInt("InactiveStatus");
    }

    public boolean isTimeOutExpired() {
        return this.mContext.getBoolean("IsTimeOut");
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
