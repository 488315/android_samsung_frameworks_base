package com.android.server.timedetector;

import android.content.Context;
import android.os.Handler;

import java.util.Objects;

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
