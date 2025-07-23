package android.hardware.scontext;

import android.media.tv.TvContract;
import android.os.Bundle;
import android.util.Log;

@Deprecated
/* loaded from: classes2.dex */
public class SContextInactiveTimerAttribute extends SContextAttribute {
    private static final String TAG = "SContextInactiveTimerAttribute";
    private int mAlertCount;
    private int mDeviceType;
    private int mDuration;
    private int mEndTime;
    private int mStartTime;

    SContextInactiveTimerAttribute() {
        this.mDeviceType = 1;
        this.mDuration = 3600;
        this.mAlertCount = 1;
        this.mStartTime = 1500;
        this.mEndTime = 1500;
        setAttribute();
    }

    public SContextInactiveTimerAttribute(int i, int i2, int i3, int i4, int i5) {
        this.mDeviceType = i;
        this.mDuration = i2;
        this.mAlertCount = i3;
        this.mStartTime = i4;
        this.mEndTime = i5;
        setAttribute();
    }

    @Override // android.hardware.scontext.SContextAttribute, com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        int i = this.mDeviceType;
        if (i != 1 && i != 2) {
            Log.e(TAG, "The deivce type is wrong.");
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
