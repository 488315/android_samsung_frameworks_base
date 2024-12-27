package com.android.server.chimera.ppn;

import com.android.server.chimera.SystemRepository;

import java.util.function.Function;

public final /* synthetic */
class ChimeraQuotaMonitor$AlwaysRunningMemCollectTask$$ExternalSyntheticLambda0
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return Integer.valueOf(((SystemRepository.RunningAppProcessInfo) obj).pid);
    }
}
