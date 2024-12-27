package com.android.server.ondeviceintelligence;

import android.app.ondeviceintelligence.InferenceInfo;

import java.util.function.ToLongFunction;

public final /* synthetic */ class InferenceInfoStore$$ExternalSyntheticLambda0
        implements ToLongFunction {
    @Override // java.util.function.ToLongFunction
    public final long applyAsLong(Object obj) {
        return ((InferenceInfo) obj).getStartTimeMs();
    }
}
