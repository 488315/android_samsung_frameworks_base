package com.android.server.appop;

import com.android.internal.util.function.HeptConsumer;

public final /* synthetic */ class AppOpsUidStateTrackerImpl$EventLog$$ExternalSyntheticLambda0
        implements HeptConsumer {
    public final /* synthetic */ int $r8$classId;

    public final void accept(
            Object obj,
            Object obj2,
            Object obj3,
            Object obj4,
            Object obj5,
            Object obj6,
            Object obj7) {
        int i = this.$r8$classId;
        AppOpsUidStateTrackerImpl.EventLog eventLog = (AppOpsUidStateTrackerImpl.EventLog) obj;
        long longValue = ((Long) obj2).longValue();
        Integer num = (Integer) obj3;
        switch (i) {
            case 0:
                int intValue = num.intValue();
                int intValue2 = ((Integer) obj4).intValue();
                int intValue3 = ((Integer) obj5).intValue();
                int intValue4 = ((Integer) obj6).intValue();
                int intValue5 = ((Integer) obj7).intValue();
                int i2 = eventLog.mEvalForegroundModeLogHead;
                int i3 = eventLog.mEvalForegroundModeLogSize;
                int i4 = (i2 + i3) % 200;
                if (i3 == 200) {
                    eventLog.mEvalForegroundModeLogHead = (i2 + 1) % 200;
                } else {
                    eventLog.mEvalForegroundModeLogSize = i3 + 1;
                }
                int[] iArr = eventLog.mEvalForegroundModeLog[i4];
                iArr[0] = intValue;
                iArr[1] = intValue2;
                iArr[2] = intValue3;
                iArr[3] = intValue4;
                iArr[4] = intValue5;
                eventLog.mEvalForegroundModeLogTimestamps[i4] = longValue;
                break;
            default:
                int intValue6 = num.intValue();
                int intValue7 = ((Integer) obj4).intValue();
                int intValue8 = ((Integer) obj5).intValue();
                boolean booleanValue = ((Boolean) obj6).booleanValue();
                boolean booleanValue2 = ((Boolean) obj7).booleanValue();
                int i5 = eventLog.mCommitUidStateLogHead;
                int i6 = eventLog.mCommitUidStateLogSize;
                int i7 = (i5 + i6) % 200;
                if (i6 == 200) {
                    eventLog.mCommitUidStateLogHead = (i5 + 1) % 200;
                } else {
                    eventLog.mCommitUidStateLogSize = i6 + 1;
                }
                int[] iArr2 = eventLog.mCommitUidStateLog[i7];
                iArr2[0] = intValue6;
                iArr2[1] = intValue7;
                iArr2[2] = intValue8;
                iArr2[3] = 0;
                if (booleanValue) {
                    iArr2[3] = 1;
                }
                if (booleanValue2) {
                    iArr2[3] = iArr2[3] + 2;
                }
                eventLog.mCommitUidStateLogTimestamps[i7] = longValue;
                break;
        }
    }
}
