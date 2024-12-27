package com.android.server.appop;

import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.AppOpsManager;
import android.os.SystemProperties;
import android.util.ArrayMap;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;
import android.util.TimeUtils;

import com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags;
import com.android.internal.os.Clock;
import com.android.internal.util.function.pooled.PooledLambda;

import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AppOpsUidStateTrackerImpl implements AppOpsUidStateTracker {
    public static final boolean DEBUG =
            "0x4948".equals(SystemProperties.get("ro.boot.debug_level", "unknown"));
    public static final boolean DEBUG_MID =
            "0x494d".equals(SystemProperties.get("ro.boot.debug_level", "unknown"));
    public final ActivityManagerInternal mActivityManagerInternal;
    public final Clock mClock;
    public final AppOpsService.Constants mConstants;
    public final EventLog mEventLog;
    public final DelayableExecutor mExecutor;
    public final SparseIntArray mUidStates = new SparseIntArray();
    public final SparseIntArray mPendingUidStates = new SparseIntArray();
    public final SparseIntArray mCapability = new SparseIntArray();
    public final SparseIntArray mPendingCapability = new SparseIntArray();
    public final SparseBooleanArray mAppWidgetVisible = new SparseBooleanArray();
    public final SparseBooleanArray mPendingAppWidgetVisible = new SparseBooleanArray();
    public final SparseLongArray mPendingCommitTime = new SparseLongArray();
    public final SparseBooleanArray mPendingGone = new SparseBooleanArray();
    public final ArrayMap mUidStateChangedCallbacks = new ArrayMap();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    interface DelayableExecutor extends Executor {
        void executeDelayed(Runnable runnable, long j);
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class EventLog {
        public final int[][] mCommitUidStateLog;
        public int mCommitUidStateLogHead;
        public int mCommitUidStateLogSize;
        public final long[] mCommitUidStateLogTimestamps;
        public final int[][] mEvalForegroundModeLog;
        public int mEvalForegroundModeLogHead;
        public int mEvalForegroundModeLogSize;
        public final long[] mEvalForegroundModeLogTimestamps;
        public final DelayableExecutor mExecutor;
        public final int[][] mUpdateUidProcStateLog;
        public int mUpdateUidProcStateLogHead;
        public int mUpdateUidProcStateLogSize;
        public final long[] mUpdateUidProcStateLogTimestamps;

        public EventLog(DelayableExecutor delayableExecutor) {
            Class cls = Integer.TYPE;
            this.mUpdateUidProcStateLog = (int[][]) Array.newInstance((Class<?>) cls, 200, 3);
            this.mUpdateUidProcStateLogTimestamps = new long[200];
            this.mUpdateUidProcStateLogSize = 0;
            this.mUpdateUidProcStateLogHead = 0;
            this.mCommitUidStateLog = (int[][]) Array.newInstance((Class<?>) cls, 200, 4);
            this.mCommitUidStateLogTimestamps = new long[200];
            this.mCommitUidStateLogSize = 0;
            this.mCommitUidStateLogHead = 0;
            this.mEvalForegroundModeLog = (int[][]) Array.newInstance((Class<?>) cls, 200, 5);
            this.mEvalForegroundModeLogTimestamps = new long[200];
            this.mEvalForegroundModeLogSize = 0;
            this.mEvalForegroundModeLogHead = 0;
            this.mExecutor = delayableExecutor;
        }
    }

    public AppOpsUidStateTrackerImpl(
            ActivityManagerInternal activityManagerInternal,
            DelayableExecutor delayableExecutor,
            Clock clock,
            AppOpsService.Constants constants,
            Thread thread) {
        this.mActivityManagerInternal = activityManagerInternal;
        this.mExecutor = delayableExecutor;
        this.mClock = clock;
        this.mConstants = constants;
        this.mEventLog = new EventLog(delayableExecutor);
    }

    public final void commitUidPendingState(int i) {
        int i2 = this.mPendingUidStates.get(i, this.mUidStates.get(i, 700));
        int i3 = this.mPendingCapability.get(i, this.mCapability.get(i, 0));
        boolean z = this.mPendingAppWidgetVisible.get(i, this.mAppWidgetVisible.get(i, false));
        int i4 = this.mUidStates.get(i, 700);
        int i5 = this.mCapability.get(i, 0);
        boolean z2 = this.mAppWidgetVisible.get(i, false);
        boolean z3 = ((i4 <= 500) == (i2 <= 500) && i5 == i3 && z2 == z) ? false : true;
        if (i4 != i2 || i5 != i3 || z2 != z) {
            if (z3) {
                boolean z4 = z2 != z;
                EventLog eventLog = this.mEventLog;
                eventLog.getClass();
                eventLog.mExecutor.execute(
                        PooledLambda.obtainRunnable(
                                new AppOpsUidStateTrackerImpl$EventLog$$ExternalSyntheticLambda0(1),
                                eventLog,
                                Long.valueOf(System.currentTimeMillis()),
                                Integer.valueOf(i),
                                Integer.valueOf(i2),
                                Integer.valueOf(i3),
                                Boolean.valueOf(z),
                                Boolean.valueOf(z4)));
            }
            for (int i6 = 0; i6 < this.mUidStateChangedCallbacks.size(); i6++) {
                ((Executor) this.mUidStateChangedCallbacks.valueAt(i6))
                        .execute(
                                PooledLambda.obtainRunnable(
                                        new AppOpsUidStateTrackerImpl$$ExternalSyntheticLambda1(),
                                        (AppOpsService$$ExternalSyntheticLambda12)
                                                this.mUidStateChangedCallbacks.keyAt(i6),
                                        Integer.valueOf(i),
                                        Integer.valueOf(i2),
                                        Boolean.valueOf(z3)));
            }
        }
        if (this.mPendingGone.get(i, false)) {
            this.mUidStates.delete(i);
            this.mCapability.delete(i);
            this.mAppWidgetVisible.delete(i);
            this.mPendingGone.delete(i);
            if (Flags.finishRunningOpsForKilledPackages()) {
                for (int i7 = 0; i7 < this.mUidStateChangedCallbacks.size(); i7++) {
                    ((Executor) this.mUidStateChangedCallbacks.valueAt(i7))
                            .execute(
                                    PooledLambda.obtainRunnable(
                                            new AppOpsUidStateTrackerImpl$$ExternalSyntheticLambda1(),
                                            (AppOpsService$$ExternalSyntheticLambda12)
                                                    this.mUidStateChangedCallbacks.keyAt(i7),
                                            Integer.valueOf(i),
                                            Integer.MAX_VALUE,
                                            Boolean.valueOf(z3)));
                }
            }
        } else {
            this.mUidStates.put(i, i2);
            this.mCapability.put(i, i3);
            this.mAppWidgetVisible.put(i, z);
        }
        this.mPendingUidStates.delete(i);
        this.mPendingCapability.delete(i);
        this.mPendingAppWidgetVisible.delete(i);
        this.mPendingCommitTime.delete(i);
    }

    public final void dumpEvents(PrintWriter printWriter) {
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            EventLog eventLog = this.mEventLog;
            int i4 = eventLog.mUpdateUidProcStateLogSize;
            if (i >= i4
                    && i2 >= eventLog.mCommitUidStateLogSize
                    && i3 >= eventLog.mEvalForegroundModeLogSize) {
                return;
            }
            int i5 = (eventLog.mUpdateUidProcStateLogHead + i) % 200;
            int i6 = (eventLog.mCommitUidStateLogHead + i2) % 200;
            int i7 = (eventLog.mEvalForegroundModeLogHead + i3) % 200;
            long[] jArr = eventLog.mUpdateUidProcStateLogTimestamps;
            long j = i < i4 ? jArr[i5] : Long.MAX_VALUE;
            int i8 = eventLog.mCommitUidStateLogSize;
            long[] jArr2 = eventLog.mCommitUidStateLogTimestamps;
            long j2 = i2 < i8 ? jArr2[i6] : Long.MAX_VALUE;
            int i9 = eventLog.mEvalForegroundModeLogSize;
            long[] jArr3 = eventLog.mEvalForegroundModeLogTimestamps;
            long j3 = i3 < i9 ? jArr3[i7] : Long.MAX_VALUE;
            int i10 = i3;
            if (j <= j2 && j <= j3) {
                long j4 = jArr[i5];
                int[] iArr = eventLog.mUpdateUidProcStateLog[i5];
                int i11 = iArr[0];
                int i12 = iArr[1];
                int i13 = iArr[2];
                TimeUtils.dumpTime(printWriter, j4);
                printWriter.print(" UPDATE_UID_PROC_STATE");
                printWriter.print(" uid=");
                printWriter.print(String.format("%-8d", Integer.valueOf(i11)));
                printWriter.print(" procState=");
                printWriter.print(String.format("%-30s", ActivityManager.procStateToString(i12)));
                printWriter.print(" capability=");
                printWriter.print(ActivityManager.getCapabilitiesSummary(i13) + " ");
                printWriter.println();
                i++;
            } else if (j2 <= j3) {
                long j5 = jArr2[i6];
                int[] iArr2 = eventLog.mCommitUidStateLog[i6];
                int i14 = iArr2[0];
                int i15 = iArr2[1];
                int i16 = iArr2[2];
                int i17 = iArr2[3];
                boolean z = (i17 & 1) != 0;
                boolean z2 = (i17 & 2) != 0;
                TimeUtils.dumpTime(printWriter, j5);
                printWriter.print(" COMMIT_UID_STATE     ");
                printWriter.print(" uid=");
                printWriter.print(String.format("%-8d", Integer.valueOf(i14)));
                printWriter.print(" uidState=");
                printWriter.print(String.format("%-30s", AppOpsManager.uidStateToString(i15)));
                printWriter.print(" capability=");
                printWriter.print(ActivityManager.getCapabilitiesSummary(i16) + " ");
                printWriter.print(" appWidgetVisible=");
                printWriter.print(z);
                if (z2) {
                    printWriter.print(" (changed)");
                }
                printWriter.println();
                i2++;
            } else {
                long j6 = jArr3[i7];
                int[] iArr3 = eventLog.mEvalForegroundModeLog[i7];
                int i18 = iArr3[0];
                int i19 = iArr3[1];
                int i20 = iArr3[2];
                int i21 = iArr3[3];
                int i22 = iArr3[4];
                TimeUtils.dumpTime(printWriter, j6);
                printWriter.print(" EVAL_FOREGROUND_MODE ");
                printWriter.print(" uid=");
                printWriter.print(String.format("%-8d", Integer.valueOf(i18)));
                printWriter.print(" uidState=");
                printWriter.print(String.format("%-30s", AppOpsManager.uidStateToString(i19)));
                printWriter.print(" capability=");
                printWriter.print(ActivityManager.getCapabilitiesSummary(i20) + " ");
                printWriter.print(" code=");
                printWriter.print(String.format("%-20s", AppOpsManager.opToName(i21)));
                printWriter.print(" result=");
                printWriter.print(AppOpsManager.modeToName(i22));
                printWriter.println();
                i3 = i10 + 1;
            }
            i3 = i10;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x007c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int evalMode(int r13, int r14, int r15) {
        /*
            r12 = this;
            r0 = 4
            if (r15 == r0) goto L4
            return r15
        L4:
            r12.updateUidPendingStateIfNeeded(r13)
            android.util.SparseIntArray r15 = r12.mUidStates
            r1 = 700(0x2bc, float:9.81E-43)
            int r15 = r15.get(r13, r1)
            android.util.SparseIntArray r1 = r12.mCapability
            r2 = 0
            int r1 = r1.get(r13, r2)
            android.util.SparseBooleanArray r3 = r12.mAppWidgetVisible
            boolean r3 = r3.get(r13, r2)
            if (r3 != 0) goto La7
            android.app.ActivityManagerInternal r3 = r12.mActivityManagerInternal
            boolean r3 = r3.isPendingTopUid(r13)
            if (r3 != 0) goto La7
            android.app.ActivityManagerInternal r3 = r12.mActivityManagerInternal
            boolean r3 = r3.isTempAllowlistedForFgsWhileInUse(r13)
            if (r3 == 0) goto L30
            goto La7
        L30:
            r3 = 1
            if (r14 == 0) goto L54
            if (r14 == r3) goto L54
            r4 = 26
            if (r14 == r4) goto L52
            r4 = 27
            if (r14 == r4) goto L55
            r4 = 32
            if (r14 == r4) goto L4f
            r4 = 121(0x79, float:1.7E-43)
            if (r14 == r4) goto L55
            r0 = 41
            if (r14 == r0) goto L54
            r0 = 42
            if (r14 == r0) goto L54
            r0 = r2
            goto L55
        L4f:
            r0 = 64
            goto L55
        L52:
            r0 = 2
            goto L55
        L54:
            r0 = r3
        L55:
            java.lang.String r4 = " opCapability= "
            java.lang.String r5 = " uidCapability= "
            java.lang.String r6 = " uidState= "
            java.lang.String r7 = " code= "
            java.lang.String r8 = "AppOps"
            boolean r9 = com.android.server.appop.AppOpsUidStateTrackerImpl.DEBUG
            boolean r10 = com.android.server.appop.AppOpsUidStateTrackerImpl.DEBUG_MID
            if (r0 == 0) goto L7c
            r11 = r1 & r0
            if (r11 != 0) goto La7
            if (r10 != 0) goto L6d
            if (r9 == 0) goto L7a
        L6d:
            java.lang.String r2 = "evalModeInternal uidCapability: uid= "
            java.lang.StringBuilder r2 = com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0.m(r13, r14, r2, r7, r6)
            com.android.server.ServiceKeeper$$ExternalSyntheticOutline0.m(r15, r1, r5, r4, r2)
            com.android.server.HeapdumpWatcher$$ExternalSyntheticOutline0.m(r2, r0, r8)
        L7a:
            r2 = r3
            goto La7
        L7c:
            int r11 = android.app.AppOpsManager.resolveFirstUnrestrictedUidState(r14)
            if (r15 <= r11) goto La7
            if (r10 != 0) goto L86
            if (r9 == 0) goto L7a
        L86:
            java.lang.String r2 = "evalModeInternal uidState: uid= "
            java.lang.StringBuilder r2 = com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0.m(r13, r14, r2, r7, r6)
            com.android.server.ServiceKeeper$$ExternalSyntheticOutline0.m(r15, r1, r5, r4, r2)
            r2.append(r0)
            java.lang.String r0 = " first unrestricted state= "
            r2.append(r0)
            int r0 = android.app.AppOpsManager.resolveFirstUnrestrictedUidState(r14)
            r2.append(r0)
            java.lang.String r0 = r2.toString()
            android.util.Slog.w(r8, r0)
            goto L7a
        La7:
            com.android.server.appop.AppOpsUidStateTrackerImpl$EventLog r12 = r12.mEventLog
            r12.getClass()
            com.android.server.appop.AppOpsUidStateTrackerImpl$EventLog$$ExternalSyntheticLambda0 r3 = new com.android.server.appop.AppOpsUidStateTrackerImpl$EventLog$$ExternalSyntheticLambda0
            r0 = 0
            r3.<init>(r0)
            long r4 = java.lang.System.currentTimeMillis()
            java.lang.Long r5 = java.lang.Long.valueOf(r4)
            java.lang.Integer r6 = java.lang.Integer.valueOf(r13)
            java.lang.Integer r7 = java.lang.Integer.valueOf(r15)
            java.lang.Integer r8 = java.lang.Integer.valueOf(r1)
            java.lang.Integer r9 = java.lang.Integer.valueOf(r14)
            java.lang.Integer r10 = java.lang.Integer.valueOf(r2)
            r4 = r12
            com.android.internal.util.function.pooled.PooledRunnable r13 = com.android.internal.util.function.pooled.PooledLambda.obtainRunnable(r3, r4, r5, r6, r7, r8, r9, r10)
            com.android.server.appop.AppOpsUidStateTrackerImpl$DelayableExecutor r12 = r12.mExecutor
            r12.execute(r13)
            return r2
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.appop.AppOpsUidStateTrackerImpl.evalMode(int, int,"
                    + " int):int");
    }

    public final void updateUidPendingStateIfNeeded(int i) {
        if (this.mPendingCommitTime.get(i, 0L) == 0
                || this.mClock.elapsedRealtime() < this.mPendingCommitTime.get(i)) {
            return;
        }
        commitUidPendingState(i);
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x005f  */
    /* JADX WARN: Removed duplicated region for block: B:39:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateUidProcState(int r21, int r22, int r23) {
        /*
            Method dump skipped, instructions count: 237
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.appop.AppOpsUidStateTrackerImpl.updateUidProcState(int,"
                    + " int, int):void");
    }
}
