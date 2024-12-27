package com.android.server.utils;

import android.util.Pair;
import com.android.server.utils.AlarmQueue;
import java.util.Comparator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class AlarmQueue$AlarmPriorityQueue$$ExternalSyntheticLambda0 implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        AlarmQueue$AlarmPriorityQueue$$ExternalSyntheticLambda0 alarmQueue$AlarmPriorityQueue$$ExternalSyntheticLambda0 = AlarmQueue.AlarmPriorityQueue.sTimeComparator;
        return Long.compare(((Long) ((Pair) obj).second).longValue(), ((Long) ((Pair) obj2).second).longValue());
    }
}
