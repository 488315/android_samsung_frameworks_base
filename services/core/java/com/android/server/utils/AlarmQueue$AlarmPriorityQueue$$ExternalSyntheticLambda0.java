package com.android.server.utils;

import android.util.Pair;

import java.util.Comparator;

public final /* synthetic */ class AlarmQueue$AlarmPriorityQueue$$ExternalSyntheticLambda0
        implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        AlarmQueue$AlarmPriorityQueue$$ExternalSyntheticLambda0
                alarmQueue$AlarmPriorityQueue$$ExternalSyntheticLambda0 =
                        AlarmQueue.AlarmPriorityQueue.sTimeComparator;
        return Long.compare(
                ((Long) ((Pair) obj).second).longValue(),
                ((Long) ((Pair) obj2).second).longValue());
    }
}
