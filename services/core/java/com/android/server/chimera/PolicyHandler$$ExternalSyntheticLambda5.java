package com.android.server.chimera;

import java.util.function.ToIntFunction;

public final /* synthetic */ class PolicyHandler$$ExternalSyntheticLambda5
        implements ToIntFunction {
    @Override // java.util.function.ToIntFunction
    public final int applyAsInt(Object obj) {
        return ((SystemRepository.RunningAppProcessInfo) obj).pid;
    }
}
