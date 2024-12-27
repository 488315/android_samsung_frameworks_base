package com.android.server.utils.quota;

import android.util.LongArrayQueue;

import java.util.function.Function;

public final /* synthetic */ class CountQuotaTracker$$ExternalSyntheticLambda2 implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return new CountQuotaTracker.ExecutionStats();
            default:
                return new LongArrayQueue();
        }
    }
}
