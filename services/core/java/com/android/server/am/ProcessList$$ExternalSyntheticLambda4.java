package com.android.server.am;

import android.os.Process;
import android.os.UserHandle;
import android.util.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class ProcessList$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ProcessList f$0;
    public final /* synthetic */ ProcessRecord f$1;
    public final /* synthetic */ long f$10;
    public final /* synthetic */ String f$2;
    public final /* synthetic */ int[] f$3;
    public final /* synthetic */ int f$4;
    public final /* synthetic */ int f$5;
    public final /* synthetic */ int f$6;
    public final /* synthetic */ String f$7;
    public final /* synthetic */ String f$8;
    public final /* synthetic */ String f$9;

    public /* synthetic */ ProcessList$$ExternalSyntheticLambda4(
            ProcessList processList,
            ProcessRecord processRecord,
            int[] iArr,
            int i,
            int i2,
            int i3,
            String str,
            String str2,
            String str3,
            long j,
            int i4) {
        this.$r8$classId = i4;
        this.f$0 = processList;
        this.f$1 = processRecord;
        this.f$3 = iArr;
        this.f$4 = i;
        this.f$5 = i2;
        this.f$6 = i3;
        this.f$7 = str;
        this.f$8 = str2;
        this.f$9 = str3;
        this.f$10 = j;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v1 */
    /* JADX WARN: Type inference failed for: r10v3, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r10v4 */
    /* JADX WARN: Type inference failed for: r12v2 */
    /* JADX WARN: Type inference failed for: r12v5, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r12v6 */
    @Override // java.lang.Runnable
    public final void run() {
        long j;
        ProcessRecord processRecord;
        switch (this.$r8$classId) {
            case 0:
                ProcessList processList = this.f$0;
                ProcessRecord processRecord2 = this.f$1;
                int[] iArr = this.f$3;
                int i = this.f$4;
                int i2 = this.f$5;
                int i3 = this.f$6;
                String str = this.f$7;
                String str2 = this.f$8;
                String str3 = this.f$9;
                long j2 = this.f$10;
                processList.getClass();
                ProcessList$$ExternalSyntheticLambda4 processList$$ExternalSyntheticLambda4 =
                        new ProcessList$$ExternalSyntheticLambda4(
                                processList,
                                processRecord2,
                                iArr,
                                i,
                                i2,
                                i3,
                                str,
                                str2,
                                str3,
                                j2,
                                1);
                ProcessRecord processRecord3 = processRecord2.mPredecessor;
                if (processRecord3 == null || processRecord3.mDyingPid <= 0) {
                    processList$$ExternalSyntheticLambda4.run();
                    return;
                }
                if (processRecord3.mSuccessorStartRunnable != null) {
                    Slog.wtf(
                            "ActivityManager",
                            "We've been watching for the death of " + processRecord3);
                    return;
                } else {
                    Slog.d(
                            "ActivityManager_PRED",
                            "handleProcessStartWithPredecessor predecessor = " + processRecord3);
                    processRecord3.mSuccessorStartRunnable = processList$$ExternalSyntheticLambda4;
                    ProcessList.ProcStartHandler procStartHandler =
                            processList.mService.mProcStartHandler;
                    procStartHandler.sendMessageDelayed(
                            procStartHandler.obtainMessage(2, processRecord3),
                            processList.mService.mConstants.mProcessKillTimeoutMs);
                    return;
                }
            default:
                ProcessList processList2 = this.f$0;
                ProcessRecord processRecord4 = this.f$1;
                int[] iArr2 = this.f$3;
                int i4 = this.f$4;
                int i5 = this.f$5;
                int i6 = this.f$6;
                String str4 = this.f$7;
                String str5 = this.f$8;
                String str6 = this.f$9;
                long j3 = this.f$10;
                processList2.getClass();
                try {
                    HostingRecord hostingRecord = processRecord4.mHostingRecord;
                    int i7 = processRecord4.mStartUid;
                    j = processRecord4.mSeInfo;
                    processRecord = str5;
                    try {
                        Process.ProcessStartResult startProcess =
                                processList2.startProcess(
                                        hostingRecord,
                                        processRecord4,
                                        i7,
                                        iArr2,
                                        i4,
                                        i5,
                                        i6,
                                        j,
                                        str4,
                                        processRecord,
                                        str6,
                                        processRecord4.mStartUptime);
                        ActivityManagerService activityManagerService = processList2.mService;
                        ActivityManagerService.boostPriorityForLockedSection();
                        try {
                            try {
                                synchronized (activityManagerService) {
                                    try {
                                    } catch (Throwable th) {
                                        th = th;
                                    }
                                    try {
                                        if (processList2.mPendingStarts.get(j3) != null) {
                                            processList2.handleProcessStartedLocked(
                                                    processRecord4,
                                                    startProcess.pid,
                                                    startProcess.usingWrapper,
                                                    j3,
                                                    false);
                                        } else if (processRecord4.mPid == startProcess.pid) {
                                            boolean z = startProcess.usingWrapper;
                                            processRecord4.mUsingWrapper = z;
                                            processRecord4.mWindowProcessController.mUsingWrapper =
                                                    z;
                                        }
                                        ActivityManagerService.resetPriorityAfterLockedSection();
                                        return;
                                    } catch (Throwable th2) {
                                        th = th2;
                                        throw th;
                                    }
                                }
                            } catch (RuntimeException e) {
                                e = e;
                                ActivityManagerService activityManagerService2 =
                                        processList2.mService;
                                ActivityManagerService.boostPriorityForLockedSection();
                                synchronized (activityManagerService2) {
                                    try {
                                        Slog.e(
                                                "ActivityManager",
                                                "Failure starting process "
                                                        + processRecord.processName,
                                                e);
                                        processList2.mPendingStarts.remove(j);
                                        processRecord.mPendingStart = false;
                                        processList2.mService.forceStopPackageLocked(
                                                processRecord.info.packageName,
                                                UserHandle.getAppId(processRecord.uid),
                                                false,
                                                false,
                                                true,
                                                false,
                                                false,
                                                processRecord.userId,
                                                "start failure");
                                        if (processRecord.mThread == null) {
                                            processRecord.mService.mOomAdjuster.onProcessEndLocked(
                                                    processRecord);
                                        }
                                    } finally {
                                        ActivityManagerService.resetPriorityAfterLockedSection();
                                    }
                                }
                                ActivityManagerService.resetPriorityAfterLockedSection();
                                return;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                        }
                    } catch (RuntimeException e2) {
                        e = e2;
                        j = j3;
                        processRecord = processRecord4;
                    }
                } catch (RuntimeException e3) {
                    e = e3;
                    j = j3;
                    processRecord = processRecord4;
                }
        }
    }
}
