package com.android.server.am;

import com.android.internal.os.ProcessCpuTracker;

import java.util.function.Predicate;

public final /* synthetic */ class AppProfiler$$ExternalSyntheticLambda3 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return ((ProcessCpuTracker.Stats) obj).vsize > 0;
    }
}
