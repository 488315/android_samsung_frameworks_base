package com.android.server.wm;

import com.android.server.am.ActivityManagerService;
import com.android.server.am.ProcessRecord;

import java.util.function.Consumer;

public final /* synthetic */ class WindowProcessController$$ExternalSyntheticLambda6
        implements Consumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ WindowProcessController$$ExternalSyntheticLambda6(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ProcessRecord processRecord = (ProcessRecord) obj;
        switch (this.$r8$classId) {
            case 0:
                ActivityManagerService activityManagerService = processRecord.mService;
                ActivityManagerService.boostPriorityForLockedSection();
                synchronized (activityManagerService) {
                    try {
                        processRecord.mService.mServices.updateServiceConnectionActivitiesLocked(
                                processRecord.mServices);
                    } catch (Throwable th) {
                        ActivityManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                ActivityManagerService.resetPriorityAfterLockedSection();
                return;
            default:
                synchronized (processRecord.mService.mAppProfiler.mProfilerLock) {
                    processRecord.mService.mAppProfiler.clearProfilerLPf();
                }
                return;
        }
    }
}
