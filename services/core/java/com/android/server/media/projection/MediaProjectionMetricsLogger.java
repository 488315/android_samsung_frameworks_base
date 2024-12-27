package com.android.server.media.projection;

import com.android.internal.util.FrameworkStatsLog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class MediaProjectionMetricsLogger {
    public static MediaProjectionMetricsLogger sSingleton;
    public final FrameworkStatsLogWrapper mFrameworkStatsLogWrapper;
    public int mPreviousState = 0;
    public final MediaProjectionSessionIdGenerator mSessionIdGenerator;
    public final MediaProjectionTimestampStore mTimestampStore;

    public MediaProjectionMetricsLogger(
            FrameworkStatsLogWrapper frameworkStatsLogWrapper,
            MediaProjectionSessionIdGenerator mediaProjectionSessionIdGenerator,
            MediaProjectionTimestampStore mediaProjectionTimestampStore) {
        this.mFrameworkStatsLogWrapper = frameworkStatsLogWrapper;
        this.mSessionIdGenerator = mediaProjectionSessionIdGenerator;
        this.mTimestampStore = mediaProjectionTimestampStore;
    }

    public int contentToRecordToTargetType(int i) {
        if (i != 0) {
            return i != 1 ? 0 : 2;
        }
        return 1;
    }

    public int windowingModeToTargetWindowingMode(int i) {
        if (i == 1) {
            return 2;
        }
        if (i != 5) {
            return i != 6 ? 0 : 3;
        }
        return 4;
    }

    public final void writeStateChanged(int i, int i2, int i3, int i4, int i5, int i6) {
        int i7 = this.mPreviousState;
        this.mFrameworkStatsLogWrapper.getClass();
        FrameworkStatsLog.write(
                FrameworkStatsLog.MEDIA_PROJECTION_STATE_CHANGED, i, i2, i7, i3, i4, i5, i6);
        this.mPreviousState = i2;
    }
}
