package com.android.server.utils.quota;

import android.util.LongArrayQueue;

import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
