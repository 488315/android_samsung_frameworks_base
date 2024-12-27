package com.android.server.appop;

import java.util.Comparator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class DiscreteRegistry$DiscreteOp$$ExternalSyntheticLambda0
        implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        long j = ((DiscreteRegistry.DiscreteOpEvent) obj).mNoteTime;
        long j2 = ((DiscreteRegistry.DiscreteOpEvent) obj2).mNoteTime;
        if (j < j2) {
            return -1;
        }
        return j == j2 ? 0 : 1;
    }
}
