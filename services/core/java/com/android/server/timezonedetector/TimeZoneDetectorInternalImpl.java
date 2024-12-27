package com.android.server.timezonedetector;

import android.content.Context;
import android.os.Handler;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
