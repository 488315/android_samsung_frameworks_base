package com.android.server.chimera;

import java.util.Comparator;

public final /* synthetic */ class GPUMemoryReclaimer$Dump$$ExternalSyntheticLambda0
        implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        return ((GPUMemoryReclaimer.ReclaimableTask) obj).mPid
                - ((GPUMemoryReclaimer.ReclaimableTask) obj2).mPid;
    }
}
