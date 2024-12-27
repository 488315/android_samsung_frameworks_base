package com.samsung.android.server.vibrator;

import java.util.HashMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class VibratorHqmData {
    public int mAlarmCount;
    public int mExtraCount;
    public HashMap mLoggingData;
    public int mNotificationCount;
    public int mRingCount;
    public int mTouchCount;

    public final void increaseCount(int i) {
        int i2;
        if (i == 17) {
            int i3 = this.mAlarmCount;
            i2 = i3 < Integer.MAX_VALUE ? i3 + 1 : Integer.MAX_VALUE;
            this.mAlarmCount = i2;
            this.mLoggingData.put("FW_AVPC", Integer.valueOf(i2));
            return;
        }
        if (i == 18) {
            int i4 = this.mTouchCount;
            i2 = i4 < Integer.MAX_VALUE ? i4 + 1 : Integer.MAX_VALUE;
            this.mTouchCount = i2;
            this.mLoggingData.put("FW_TVPC", Integer.valueOf(i2));
            return;
        }
        if (i == 33) {
            int i5 = this.mRingCount;
            i2 = i5 < Integer.MAX_VALUE ? i5 + 1 : Integer.MAX_VALUE;
            this.mRingCount = i2;
            this.mLoggingData.put("FW_RVPC", Integer.valueOf(i2));
            return;
        }
        if (i == 49) {
            int i6 = this.mNotificationCount;
            i2 = i6 < Integer.MAX_VALUE ? i6 + 1 : Integer.MAX_VALUE;
            this.mNotificationCount = i2;
            this.mLoggingData.put("FW_NVPC", Integer.valueOf(i2));
            return;
        }
        int i7 = this.mExtraCount + 1;
        this.mExtraCount = i7;
        if (i7 >= Integer.MAX_VALUE) {
            this.mExtraCount = Integer.MAX_VALUE;
        }
        this.mLoggingData.put("FW_EVPC", Integer.valueOf(this.mExtraCount));
    }
}
