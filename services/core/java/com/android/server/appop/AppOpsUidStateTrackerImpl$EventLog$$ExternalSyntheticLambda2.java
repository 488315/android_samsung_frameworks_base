package com.android.server.appop;

import com.android.internal.util.function.QuintConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class AppOpsUidStateTrackerImpl$EventLog$$ExternalSyntheticLambda2
        implements QuintConsumer {
    public final void accept(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        AppOpsUidStateTrackerImpl.EventLog eventLog = (AppOpsUidStateTrackerImpl.EventLog) obj;
        long longValue = ((Long) obj2).longValue();
        int intValue = ((Integer) obj3).intValue();
        int intValue2 = ((Integer) obj4).intValue();
        int intValue3 = ((Integer) obj5).intValue();
        int i = eventLog.mUpdateUidProcStateLogHead;
        int i2 = eventLog.mUpdateUidProcStateLogSize;
        int i3 = (i + i2) % 200;
        if (i2 == 200) {
            eventLog.mUpdateUidProcStateLogHead = (i + 1) % 200;
        } else {
            eventLog.mUpdateUidProcStateLogSize = i2 + 1;
        }
        int[] iArr = eventLog.mUpdateUidProcStateLog[i3];
        iArr[0] = intValue;
        iArr[1] = intValue2;
        iArr[2] = intValue3;
        eventLog.mUpdateUidProcStateLogTimestamps[i3] = longValue;
    }
}
