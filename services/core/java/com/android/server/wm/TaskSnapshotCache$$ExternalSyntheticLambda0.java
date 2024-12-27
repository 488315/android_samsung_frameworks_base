package com.android.server.wm;

import android.util.Pair;

import java.util.Comparator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class TaskSnapshotCache$$ExternalSyntheticLambda0
        implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        return Long.compare(
                ((Long) ((Pair) obj).second).longValue(),
                ((Long) ((Pair) obj2).second).longValue());
    }
}
