package com.android.server.wm;

import android.app.ActivityManagerInternal;
import android.os.Build;
import android.os.IBinder;
import android.os.Process;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.util.ArrayMap;
import android.util.Slog;
import android.util.SparseArray;

import com.android.framework.protobuf.nano.MessageNanoPrinter;
import com.android.internal.os.TimeoutRecord;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.FgThread;
import com.android.server.SystemServerInitThreadPool$$ExternalSyntheticLambda0;
import com.android.server.am.StackTracesDumpHelper;
import com.android.server.criticalevents.CriticalEventLog;
import com.android.server.policy.WindowManagerPolicy;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public final class AnrController {
    public static final long PRE_DUMP_MIN_INTERVAL_MS;
    public static final long PRE_DUMP_MONITOR_TIMEOUT_MS;
    public volatile long mLastPreDumpTimeMs;
    public volatile long mLastPreDumpTimeMs_onlyForAnr;
    public final WindowManagerService mService;
    public final SparseArray mUnresponsiveAppByDisplay = new SparseArray();

    static {
        TimeUnit timeUnit = TimeUnit.SECONDS;
        PRE_DUMP_MIN_INTERVAL_MS = timeUnit.toMillis(20L);
        PRE_DUMP_MONITOR_TIMEOUT_MS = timeUnit.toMillis(1L);
    }

    public AnrController(WindowManagerService windowManagerService) {
        this.mService = windowManagerService;
    }

    public final void notifyWindowUnresponsive(int i, TimeoutRecord timeoutRecord) {
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                BatteryService$$ExternalSyntheticOutline0.m(
                        i, "ANR in input window owned by pid=", ". Reason: "),
                timeoutRecord.mReason,
                "WindowManager");
        this.mService.mAmInternal.inputDispatchingTimedOut(i, true, timeoutRecord);
        String str = timeoutRecord.mReason;
        FgThread.getExecutor()
                .execute(
                        new AnrController$$ExternalSyntheticLambda0(
                                this, null, 0 == true ? 1 : 0, str));
    }

    public final boolean notifyWindowUnresponsive(IBinder iBinder, TimeoutRecord timeoutRecord) {
        timeoutRecord.mLatencyTracker.preDumpIfLockTooSlowStarted();
        preDumpIfLockTooSlow();
        timeoutRecord.mLatencyTracker.preDumpIfLockTooSlowEnded();
        timeoutRecord.mLatencyTracker.waitingOnGlobalLockStarted();
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                timeoutRecord.mLatencyTracker.waitingOnGlobalLockEnded();
                InputTarget inputTargetFromToken = this.mService.getInputTargetFromToken(iBinder);
                if (inputTargetFromToken == null) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return false;
                }
                WindowState windowState = inputTargetFromToken.getWindowState();
                int pid = inputTargetFromToken.getPid();
                ActivityRecord activityRecord =
                        windowState.mInputChannelToken == iBinder
                                ? windowState.mActivityRecord
                                : null;
                Slog.i(
                        "WindowManager",
                        "ANR in " + inputTargetFromToken + ". Reason:" + timeoutRecord.mReason);
                WindowManagerPolicy windowManagerPolicy = this.mService.mPolicy;
                boolean z = windowState.mOwnerCanAddInternalSystemWindow;
                windowManagerPolicy.getClass();
                boolean z2 =
                        windowState.mBaseLayer
                                > WindowManagerPolicy.getWindowLayerFromTypeLw(2038, z, false);
                WindowManagerService.resetPriorityAfterLockedSection();
                if (!SystemProperties.get("ro.boot.debug_level", "0x4f4c").equals("0x4f4c")) {
                    long uptimeMillis = SystemClock.uptimeMillis();
                    if (this.mLastPreDumpTimeMs_onlyForAnr <= 0
                            || uptimeMillis - this.mLastPreDumpTimeMs_onlyForAnr
                                    >= PRE_DUMP_MIN_INTERVAL_MS) {
                        this.mLastPreDumpTimeMs_onlyForAnr = uptimeMillis;
                        Slog.i("WindowManager", "Pre-dump(onlyForAnr) for unresponsive");
                        ArrayList arrayList = new ArrayList(1);
                        arrayList.add(Integer.valueOf(pid));
                        File dumpStackTraces =
                                StackTracesDumpHelper.dumpStackTraces(
                                        arrayList, null, null, null, null, null, null, null, null,
                                        null, null, null);
                        if (dumpStackTraces != null) {
                            dumpStackTraces.renameTo(
                                    new File(
                                            dumpStackTraces.getParent(),
                                            dumpStackTraces.getName() + "_preOnlyForAnr"));
                        }
                    }
                }
                if (activityRecord != null) {
                    activityRecord.inputDispatchingTimedOut(pid, timeoutRecord);
                } else {
                    this.mService.mAmInternal.inputDispatchingTimedOut(pid, z2, timeoutRecord);
                }
                FgThread.getExecutor()
                        .execute(
                                new AnrController$$ExternalSyntheticLambda0(
                                        this, activityRecord, windowState, timeoutRecord.mReason));
                return true;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void preDumpIfLockTooSlow() {
        final int i = 0;
        final int i2 = 1;
        if (Build.IS_DEBUGGABLE) {
            final long uptimeMillis = SystemClock.uptimeMillis();
            if (this.mLastPreDumpTimeMs <= 0
                    || uptimeMillis - this.mLastPreDumpTimeMs >= PRE_DUMP_MIN_INTERVAL_MS) {
                Trace.traceBegin(64L, "preDumpIfLockTooSlow()");
                try {
                    final boolean[] zArr = {true};
                    ArrayMap arrayMap = new ArrayMap(2);
                    final WindowManagerService windowManagerService = this.mService;
                    Objects.requireNonNull(windowManagerService);
                    arrayMap.put(
                            "WindowManager",
                            new Runnable() { // from class:
                                             // com.android.server.wm.AnrController$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    int i3 = i;
                                    Object obj = windowManagerService;
                                    switch (i3) {
                                        case 0:
                                            ((WindowManagerService) obj).monitor();
                                            break;
                                        default:
                                            ((ActivityManagerInternal) obj).monitor();
                                            break;
                                    }
                                }
                            });
                    final ActivityManagerInternal activityManagerInternal =
                            this.mService.mAmInternal;
                    Objects.requireNonNull(activityManagerInternal);
                    arrayMap.put(
                            "ActivityManager",
                            new Runnable() { // from class:
                                             // com.android.server.wm.AnrController$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    int i3 = i2;
                                    Object obj = activityManagerInternal;
                                    switch (i3) {
                                        case 0:
                                            ((WindowManagerService) obj).monitor();
                                            break;
                                        default:
                                            ((ActivityManagerInternal) obj).monitor();
                                            break;
                                    }
                                }
                            });
                    CountDownLatch countDownLatch = new CountDownLatch(arrayMap.size());
                    int i3 = 0;
                    while (i3 < arrayMap.size()) {
                        final String str = (String) arrayMap.keyAt(i3);
                        final Runnable runnable = (Runnable) arrayMap.valueAt(i3);
                        final CountDownLatch countDownLatch2 = countDownLatch;
                        int i4 = i3;
                        CountDownLatch countDownLatch3 = countDownLatch;
                        ArrayMap arrayMap2 = arrayMap;
                        new Thread() { // from class: com.android.server.wm.AnrController.1
                            @Override // java.lang.Thread, java.lang.Runnable
                            public final void run() {
                                runnable.run();
                                countDownLatch2.countDown();
                                long uptimeMillis2 = SystemClock.uptimeMillis() - uptimeMillis;
                                if (uptimeMillis2 <= AnrController.PRE_DUMP_MONITOR_TIMEOUT_MS) {
                                    if ("WindowManager".equals(str)) {
                                        zArr[0] = false;
                                    }
                                } else {
                                    StringBuilder sb = new StringBuilder("Pre-dump acquired ");
                                    sb.append(str);
                                    sb.append(" in ");
                                    sb.append(uptimeMillis2);
                                    DeviceIdleController$$ExternalSyntheticOutline0.m(
                                            sb, "ms", "WindowManager");
                                }
                            }
                        }.start();
                        i3 = i4 + 1;
                        countDownLatch = countDownLatch3;
                        arrayMap = arrayMap2;
                    }
                    try {
                        if (countDownLatch.await(
                                PRE_DUMP_MONITOR_TIMEOUT_MS, TimeUnit.MILLISECONDS)) {
                            return;
                        }
                    } catch (InterruptedException unused) {
                    }
                    this.mLastPreDumpTimeMs = uptimeMillis;
                    Slog.i("WindowManager", "Pre-dump for unresponsive");
                    ArrayList arrayList = new ArrayList(1);
                    arrayList.add(Integer.valueOf(WindowManagerService.MY_PID));
                    boolean z = zArr[0];
                    ArrayList arrayList2 = null;
                    int[] pidsForCommands =
                            z
                                    ? Process.getPidsForCommands(
                                            new String[] {"/system/bin/surfaceflinger"})
                                    : null;
                    if (pidsForCommands != null) {
                        arrayList2 = new ArrayList(1);
                        for (int i5 : pidsForCommands) {
                            arrayList2.add(Integer.valueOf(i5));
                        }
                    }
                    File dumpStackTraces =
                            StackTracesDumpHelper.dumpStackTraces(
                                    arrayList,
                                    null,
                                    null,
                                    CompletableFuture.completedFuture(arrayList2),
                                    null,
                                    null,
                                    "Pre-dump",
                                    "--- CriticalEventLog ---\n"
                                            + MessageNanoPrinter.print(
                                                    CriticalEventLog.getInstance()
                                                            .getOutputLogProto(
                                                                    3, "AID_SYSTEM", 1000))
                                            + '\n',
                                    null,
                                    new SystemServerInitThreadPool$$ExternalSyntheticLambda0(),
                                    null,
                                    null);
                    if (dumpStackTraces != null) {
                        dumpStackTraces.renameTo(
                                new File(
                                        dumpStackTraces.getParent(),
                                        dumpStackTraces.getName() + "_pre"));
                    }
                } finally {
                    Trace.traceEnd(64L);
                }
            }
        }
    }
}
