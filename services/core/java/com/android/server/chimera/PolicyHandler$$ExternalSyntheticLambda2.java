package com.android.server.chimera;

import java.util.function.Predicate;

public final /* synthetic */ class PolicyHandler$$ExternalSyntheticLambda2 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return ((SystemRepository.CameraProcInfo) obj).pid != 0;
    }
}
