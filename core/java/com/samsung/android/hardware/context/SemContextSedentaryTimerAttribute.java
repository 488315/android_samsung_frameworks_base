package com.samsung.android.hardware.context;

import android.media.tv.TvContract;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

@Deprecated(forRemoval = true, since = "15.5")
public class SemContextSedentaryTimerAttribute extends SemContextAttribute {
    public static final Parcelable.Creator<SemContextSedentaryTimerAttribute> CREATOR =
            new Parcelable.Creator<
                    SemContextSedentaryTimerAttribute>() { // from class:
                                                           // com.samsung.android.hardware.context.SemContextSedentaryTimerAttribute.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemContextSedentaryTimerAttribute createFromParcel(Parcel in) {
                    return new SemContextSedentaryTimerAttribute(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemContextSedentaryTimerAttribute[] newArray(int size) {
                    return new SemContextSedentaryTimerAttribute[size];
                }
            };
    private static final String TAG = "SemContextSedentaryTimerAttribute";
    private int mAlertCount;
    private int mDeviceType;
    private int mDuration;
    private int mEndTime;
    private int mStartTime;

    SemContextSedentaryTimerAttribute() {
        this.mDeviceType = 1;
        this.mDuration = 3600;
        this.mAlertCount = 1;
        this.mStartTime = 1500;
        this.mEndTime = 1500;
        setAttribute();
    }

    SemContextSedentaryTimerAttribute(Parcel src) {
        super(src);
        this.mDeviceType = 1;
        this.mDuration = 3600;
        this.mAlertCount = 1;
        this.mStartTime = 1500;
        this.mEndTime = 1500;
    }

    public SemContextSedentaryTimerAttribute(
            int deviceType, int duration, int alertCount, int startTime, int endTime) {
        this.mDeviceType = 1;
        this.mDuration = 3600;
        this.mAlertCount = 1;
        this.mStartTime = 1500;
        this.mEndTime = 1500;
        this.mDeviceType = deviceType;
        this.mDuration = duration;
        this.mAlertCount = alertCount;
        this.mStartTime = startTime;
        this.mEndTime = endTime;
        setAttribute();
    }

    @Override // com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        if (this.mDeviceType != 1 && this.mDeviceType != 2) {
            Log.e(TAG, "The device type is wrong.");
            return false;
        }
        if (this.mDuration < 0) {
            Log.e(TAG, "The duration is wrong.");
            return false;
        }
        if (this.mAlertCount < 0) {
            Log.e(TAG, "The alert count is wrong.");
            return false;
        }
        if (this.mStartTime < 0) {
            Log.e(TAG, "The start time is wrong.");
            return false;
        }
        if (this.mEndTime >= 0) {
            return true;
        }
        Log.e(TAG, "The end time is wrong.");
        return false;
    }

    private void setAttribute() {
        Bundle attribute = new Bundle();
        attribute.putInt("device_type", this.mDeviceType);
        attribute.putInt("duration", this.mDuration);
        attribute.putInt("alert_count", this.mAlertCount);
        attribute.putInt(TvContract.PARAM_START_TIME, this.mStartTime);
        attribute.putInt(TvContract.PARAM_END_TIME, this.mEndTime);
        super.setAttribute(35, attribute);
    }
}
