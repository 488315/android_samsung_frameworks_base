package com.android.server.credentials.metrics.shared;

import com.android.server.credentials.metrics.EntryEnum;

import java.util.function.ToIntFunction;

public final /* synthetic */ class ResponseCollective$$ExternalSyntheticLambda0
        implements ToIntFunction {
    @Override // java.util.function.ToIntFunction
    public final int applyAsInt(Object obj) {
        return ((EntryEnum) obj).ordinal();
    }
}
