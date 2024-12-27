package com.android.server.alarm;

import java.util.function.ToLongFunction;

public final /* synthetic */ class LazyAlarmStore$$ExternalSyntheticLambda0
        implements ToLongFunction {
    @Override // java.util.function.ToLongFunction
    public final long applyAsLong(Object obj) {
        return ((Alarm) obj).mWhenElapsed;
    }
}
