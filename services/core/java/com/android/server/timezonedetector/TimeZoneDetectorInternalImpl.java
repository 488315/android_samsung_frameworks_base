package com.android.server.timezonedetector;

import android.content.Context;
import android.os.Handler;

public final class TimeZoneDetectorInternalImpl {
    public final Handler mHandler;
    public final TimeZoneDetectorStrategy mTimeZoneDetectorStrategy;

    public TimeZoneDetectorInternalImpl(
            Context context,
            Handler handler,
            TimeZoneDetectorStrategyImpl timeZoneDetectorStrategyImpl) {
        this.mHandler = handler;
        this.mTimeZoneDetectorStrategy = timeZoneDetectorStrategyImpl;
    }
}
