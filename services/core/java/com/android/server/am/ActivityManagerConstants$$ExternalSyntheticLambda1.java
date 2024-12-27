package com.android.server.am;

import android.util.FeatureFlagUtils;
import android.util.Slog;

import java.util.Collections;
import java.util.Comparator;

public final /* synthetic */ class ActivityManagerConstants$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ PhantomProcessList f$0;

    public /* synthetic */ ActivityManagerConstants$$ExternalSyntheticLambda1(
            PhantomProcessList phantomProcessList) {
        this.f$0 = phantomProcessList;
    }

    @Override // java.lang.Runnable
    public final void run() {
        final PhantomProcessList phantomProcessList = this.f$0;
        if (phantomProcessList.mService.mSystemReady
                && FeatureFlagUtils.isEnabled(
                        phantomProcessList.mService.mContext,
                        "settings_enable_monitor_phantom_procs")) {
            ActivityManagerProcLock activityManagerProcLock = phantomProcessList.mService.mProcLock;
            ActivityManagerService.boostPriorityForProcLockedSection();
            synchronized (activityManagerProcLock) {
                try {
                    synchronized (phantomProcessList.mLock) {
                        try {
                            phantomProcessList.mTrimPhantomProcessScheduled = false;
                            if (phantomProcessList.mService.mConstants.MAX_PHANTOM_PROCESSES
                                    < phantomProcessList.mPhantomProcesses.size()) {
                                for (int size = phantomProcessList.mPhantomProcesses.size() - 1;
                                        size >= 0;
                                        size--) {
                                    phantomProcessList.mTempPhantomProcesses.add(
                                            (PhantomProcessRecord)
                                                    phantomProcessList.mPhantomProcesses.valueAt(
                                                            size));
                                }
                                synchronized (phantomProcessList.mService.mPidsSelfLocked) {
                                    try {
                                        Collections.sort(
                                                phantomProcessList.mTempPhantomProcesses,
                                                new Comparator() { // from class:
                                                    // com.android.server.am.PhantomProcessList$$ExternalSyntheticLambda2
                                                    /* JADX WARN: Code restructure failed: missing block: B:21:0x0043, code lost:

                                                       if (r4 < r7) goto L10;
                                                    */
                                                    /* JADX WARN: Code restructure failed: missing block: B:6:0x0027, code lost:

                                                       if (r4 < r7) goto L10;
                                                    */
                                                    @Override // java.util.Comparator
                                                    /*
                                                        Code decompiled incorrectly, please refer to instructions dump.
                                                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                                                    */
                                                    public final int compare(
                                                            java.lang.Object r7,
                                                            java.lang.Object r8) {
                                                        /*
                                                            r6 = this;
                                                            com.android.server.am.PhantomProcessList r6 = com.android.server.am.PhantomProcessList.this
                                                            com.android.server.am.PhantomProcessRecord r7 = (com.android.server.am.PhantomProcessRecord) r7
                                                            com.android.server.am.PhantomProcessRecord r8 = (com.android.server.am.PhantomProcessRecord) r8
                                                            com.android.server.am.ActivityManagerService r6 = r6.mService
                                                            com.android.server.am.ActivityManagerService$PidMap r0 = r6.mPidsSelfLocked
                                                            int r1 = r7.mPpid
                                                            com.android.server.am.ProcessRecord r0 = r0.get(r1)
                                                            com.android.server.am.ActivityManagerService$PidMap r6 = r6.mPidsSelfLocked
                                                            int r1 = r8.mPpid
                                                            com.android.server.am.ProcessRecord r6 = r6.get(r1)
                                                            r1 = 0
                                                            r2 = -1
                                                            r3 = 1
                                                            long r4 = r7.mKnownSince
                                                            long r7 = r8.mKnownSince
                                                            if (r0 != 0) goto L2a
                                                            if (r6 != 0) goto L2a
                                                            int r6 = (r4 > r7 ? 1 : (r4 == r7 ? 0 : -1))
                                                            if (r6 == 0) goto L46
                                                            if (r6 >= 0) goto L30
                                                            goto L2c
                                                        L2a:
                                                            if (r0 != 0) goto L2e
                                                        L2c:
                                                            r1 = r3
                                                            goto L46
                                                        L2e:
                                                            if (r6 != 0) goto L32
                                                        L30:
                                                            r1 = r2
                                                            goto L46
                                                        L32:
                                                            com.android.server.am.ProcessStateRecord r0 = r0.mState
                                                            int r0 = r0.mCurAdj
                                                            com.android.server.am.ProcessStateRecord r6 = r6.mState
                                                            int r6 = r6.mCurAdj
                                                            if (r0 == r6) goto L3f
                                                            int r1 = r0 - r6
                                                            goto L46
                                                        L3f:
                                                            int r6 = (r4 > r7 ? 1 : (r4 == r7 ? 0 : -1))
                                                            if (r6 == 0) goto L46
                                                            if (r6 >= 0) goto L30
                                                            goto L2c
                                                        L46:
                                                            return r1
                                                        */
                                                        throw new UnsupportedOperationException(
                                                                "Method not decompiled:"
                                                                    + " com.android.server.am.PhantomProcessList$$ExternalSyntheticLambda2.compare(java.lang.Object,"
                                                                    + " java.lang.Object):int");
                                                    }
                                                });
                                    } catch (Exception e) {
                                        Slog.e(
                                                "ActivityManager",
                                                "trimPhantomProcesses sort failed",
                                                e);
                                    }
                                }
                                for (int size2 =
                                                phantomProcessList.mTempPhantomProcesses.size() - 1;
                                        size2
                                                >= phantomProcessList
                                                        .mService
                                                        .mConstants
                                                        .MAX_PHANTOM_PROCESSES;
                                        size2--) {
                                    ((PhantomProcessRecord)
                                                    phantomProcessList.mTempPhantomProcesses.get(
                                                            size2))
                                            .killLocked("Trimming phantom processes", true);
                                }
                                phantomProcessList.mTempPhantomProcesses.clear();
                            } else {
                                phantomProcessList.removePhantomProcessesWithNoParentLocked();
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    ActivityManagerService.resetPriorityAfterProcLockedSection();
                    throw th2;
                }
            }
            ActivityManagerService.resetPriorityAfterProcLockedSection();
        }
    }
}
