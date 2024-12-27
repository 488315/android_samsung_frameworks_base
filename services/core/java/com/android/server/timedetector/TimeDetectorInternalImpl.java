package com.android.server.timedetector;

import android.content.Context;
import android.os.Handler;

import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class TimeDetectorInternalImpl implements TimeDetectorInternal {
    public final Handler mHandler;
    public final TimeDetectorStrategy mTimeDetectorStrategy;

    public TimeDetectorInternalImpl(
            Context context,
            Handler handler,
            ServiceConfigAccessorImpl serviceConfigAccessorImpl,
            TimeDetectorStrategyImpl timeDetectorStrategyImpl) {
        this.mHandler = handler;
        Objects.requireNonNull(serviceConfigAccessorImpl);
        this.mTimeDetectorStrategy = timeDetectorStrategyImpl;
    }
}
