package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated(forRemoval = true, since = "15.5")
/* loaded from: classes6.dex */
public class SemContextPhoneStatusMonitor extends SemContextEventContext {
    public static final Parcelable.Creator<SemContextPhoneStatusMonitor> CREATOR =
            new Parcelable.Creator<
                    SemContextPhoneStatusMonitor>() { // from class:
                                                      // com.samsung.android.hardware.context.SemContextPhoneStatusMonitor.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemContextPhoneStatusMonitor createFromParcel(Parcel in) {
                    return new SemContextPhoneStatusMonitor(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemContextPhoneStatusMonitor[] newArray(int size) {
                    return new SemContextPhoneStatusMonitor[size];
                }
            };
    public static final int PROXIMITY_CLOSE = 2;
    public static final int PROXIMITY_NONE = 0;
    public static final int PROXIMITY_OPEN = 1;
    public static final int SCREEN_DOWN = 4;
    public static final int SCREEN_NONE = 0;
    public static final int SCREEN_PERFECT_DOWN = 5;
    public static final int SCREEN_PERFECT_UP = 1;
    public static final int SCREEN_TILT = 3;
    public static final int SCREEN_UP = 2;
    private Bundle mContext;

    SemContextPhoneStatusMonitor() {
        this.mContext = new Bundle();
    }

    SemContextPhoneStatusMonitor(Parcel src) {
        readFromParcel(src);
    }

    public boolean isInClosedSpace() {
        return this.mContext.getBoolean("lcdOffRecommend");
    }

    public int getProximity() {
        return this.mContext.getInt("embower");
    }

    public int getScreenDirection() {
        return this.mContext.getInt("lcddirect");
    }

    public long getTimeStamp() {
        return this.mContext.getLong("timestamp");
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
