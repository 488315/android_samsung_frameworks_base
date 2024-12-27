package com.android.server.am;

import java.util.function.ToLongFunction;

public final /* synthetic */ class KillPolicyManager$MemoryFloodDetector$$ExternalSyntheticLambda0
        implements ToLongFunction {
    @Override // java.util.function.ToLongFunction
    public final long applyAsLong(Object obj) {
        return (long) ((((KillPolicyManager.MemoryDumpItem) obj).mDumpSize / 1024) + 0.5d);
    }
}
