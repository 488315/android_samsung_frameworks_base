package com.android.server.wm;

import com.android.server.am.ActivityManagerService;
import com.android.server.am.ProcessRecord;

import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class WindowProcessController$$ExternalSyntheticLambda5
        implements BiConsumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ WindowProcessController$$ExternalSyntheticLambda5(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        switch (this.$r8$classId) {
            case 0:
                ((ProcessRecord) obj).setPendingUiClean(((Boolean) obj2).booleanValue());
                return;
            case 1:
                ProcessRecord processRecord = (ProcessRecord) obj;
                int intValue = ((Integer) obj2).intValue();
                ActivityManagerService activityManagerService = processRecord.mService;
                ActivityManagerService.boostPriorityForLockedSection();
                synchronized (activityManagerService) {
                    try {
                        processRecord.setPendingUiClean(true);
                        processRecord.mState.forceProcessStateUpTo(intValue);
                    } finally {
                    }
                }
                ActivityManagerService.resetPriorityAfterLockedSection();
                return;
            default:
                ProcessRecord processRecord2 = (ProcessRecord) obj;
                String str = (String) obj2;
                ActivityManagerService activityManagerService2 = processRecord2.mService;
                ActivityManagerService.boostPriorityForLockedSection();
                synchronized (activityManagerService2) {
                    try {
                        ActivityManagerService activityManagerService3 = processRecord2.mService;
                        activityManagerService3.getClass();
                        activityManagerService3.appDiedLocked(
                                processRecord2,
                                processRecord2.mPid,
                                processRecord2.mThread,
                                false,
                                str);
                    } finally {
                    }
                }
                ActivityManagerService.resetPriorityAfterLockedSection();
                return;
        }
    }
}
