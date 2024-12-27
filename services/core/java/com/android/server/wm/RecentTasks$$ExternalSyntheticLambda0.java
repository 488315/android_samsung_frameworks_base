package com.android.server.wm;

import java.util.Comparator;

public final /* synthetic */ class RecentTasks$$ExternalSyntheticLambda0 implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        return ((Task) obj2).mTaskId - ((Task) obj).mTaskId;
    }
}
