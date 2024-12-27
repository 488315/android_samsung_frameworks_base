package com.android.server.am.pmm;

import com.android.internal.os.KernelAllocationStats;

import java.util.function.ToIntFunction;

public final /* synthetic */ class DmaBufLeakDetector$$ExternalSyntheticLambda0
        implements ToIntFunction {
    @Override // java.util.function.ToIntFunction
    public final int applyAsInt(Object obj) {
        return -((KernelAllocationStats.ProcessDmabuf) obj).retainedSizeKb;
    }
}
