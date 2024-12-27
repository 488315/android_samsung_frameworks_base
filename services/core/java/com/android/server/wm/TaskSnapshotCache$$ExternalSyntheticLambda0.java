package com.android.server.wm;

import android.util.Pair;

import java.util.Comparator;

public final /* synthetic */ class TaskSnapshotCache$$ExternalSyntheticLambda0
        implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        return Long.compare(
                ((Long) ((Pair) obj).second).longValue(),
                ((Long) ((Pair) obj2).second).longValue());
    }
}
