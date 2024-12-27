package com.android.server.chimera.ppn;

import com.android.server.chimera.SystemRepository;

import java.util.function.ToIntFunction;

public final /* synthetic */
class ChimeraQuotaMonitor$AlwaysRunningMemCollectTask$$ExternalSyntheticLambda1
        implements ToIntFunction {
    @Override // java.util.function.ToIntFunction
    public final int applyAsInt(Object obj) {
        return ((SystemRepository.RunningAppProcessInfo) obj).pid;
    }
}
