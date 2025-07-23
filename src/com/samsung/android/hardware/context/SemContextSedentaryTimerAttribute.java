package com.samsung.android.hardware.context;

import android.media.tv.TvContract;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

@Deprecated(forRemoval = true, since = "15.5")
/* loaded from: classes6.dex */
public class SemContextSedentaryTimerAttribute extends SemContextAttribute {
    public static final Parcelable.Creator<SemContextSedentaryTimerAttribute> CREATOR = new Parcelable.Creator<SemContextSedentaryTimerAttribute>() { // from class: com.samsung.android.hardware.context.SemContextSedentaryTimerAttribute.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextSedentaryTimerAttribute createFromParcel(Parcel parcel) {
            return new SemContextSedentaryTimerAttribute(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextSedentaryTimerAttribute[] newArray(int i) {
            return new SemContextSedentaryTimerAttribute[i];
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

    SemContextSedentaryTimerAttribute(Parcel parcel) {
        super(parcel);
        this.mDeviceType = 1;
        this.mDuration = 3600;
        this.mAlertCount = 1;
        this.mStartTime = 1500;
        this.mEndTime = 1500;
    }

    public SemContextSedentaryTimerAttribute(int i, int i2, int i3, int i4, int i5) {
        this.mDeviceType = i;
        this.mDuration = i2;
        this.mAlertCount = i3;
        this.mStartTime = i4;
        this.mEndTime = i5;
        setAttribute();
    }

    @Override // com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        int i = this.mDeviceType;
        if (i != 1 && i != 2) {
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
        Bundle bundle = new Bundle();
        bundle.putInt("device_type", this.mDeviceType);
        bundle.putInt("duration", this.mDuration);
        bundle.putInt("alert_count", this.mAlertCount);
        bundle.putInt(TvContract.PARAM_START_TIME, this.mStartTime);
        bundle.putInt(TvContract.PARAM_END_TIME, this.mEndTime);
        super.setAttribute(35, bundle);
    }
}
