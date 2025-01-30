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
import android.view.InputApplicationHandle;
import com.android.internal.os.TimeoutRecord;
import com.android.server.FgThread;
import com.android.server.SystemServerInitThreadPool$$ExternalSyntheticLambda1;
import com.android.server.criticalevents.CriticalEventLog;
import com.android.server.am.StackTracesDumpHelper;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class AnrController {
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

    /* JADX WARN: Finally extract failed */
    public void notifyAppUnresponsive(InputApplicationHandle inputApplicationHandle, TimeoutRecord timeoutRecord) {
        WindowState windowState;
        try {
            timeoutRecord.mLatencyTracker.notifyAppUnresponsiveStarted();
            timeoutRecord.mLatencyTracker.preDumpIfLockTooSlowStarted();
            preDumpIfLockTooSlow();
            timeoutRecord.mLatencyTracker.preDumpIfLockTooSlowEnded();
            timeoutRecord.mLatencyTracker.waitingOnGlobalLockStarted();
            WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    timeoutRecord.mLatencyTracker.waitingOnGlobalLockEnded();
                    ActivityRecord forTokenLocked = ActivityRecord.forTokenLocked(inputApplicationHandle.token);
                    if (forTokenLocked == null) {
                        Slog.e(StartingSurfaceController.TAG, "Unknown app appToken:" + inputApplicationHandle.name + ". Dropping notifyNoFocusedWindowAnr request");
                    } else if (forTokenLocked.mAppStopped) {
                        Slog.d(StartingSurfaceController.TAG, "App is in stopped state:" + inputApplicationHandle.name + ". Dropping notifyNoFocusedWindowAnr request");
                    } else {
                        DisplayContent displayContent = this.mService.mRoot.getDisplayContent(forTokenLocked.getDisplayId());
                        IBinder iBinder = displayContent != null ? displayContent.getInputMonitor().mInputFocus : null;
                        InputTarget inputTargetFromToken = this.mService.getInputTargetFromToken(iBinder);
                        boolean z = false;
                        if (inputTargetFromToken != null) {
                            windowState = inputTargetFromToken.getWindowState();
                            if (SystemClock.uptimeMillis() - displayContent.getInputMonitor().mInputFocusRequestTimeMillis >= ActivityTaskManagerService.getInputDispatchingTimeoutMillisLocked(windowState.getActivityRecord())) {
                                z = true;
                            }
                        } else {
                            windowState = null;
                        }
                        if (!z) {
                            Slog.i(StartingSurfaceController.TAG, "ANR in " + forTokenLocked.getName() + ".  Reason: " + timeoutRecord.mReason);
                            this.mUnresponsiveAppByDisplay.put(forTokenLocked.getDisplayId(), forTokenLocked);
                        }
                        WindowManagerService.resetPriorityAfterLockedSection();
                        if (z && notifyWindowUnresponsive(iBinder, timeoutRecord)) {
                            Slog.i(StartingSurfaceController.TAG, "Blamed " + windowState.getName() + " using pending focus request. Focused activity: " + forTokenLocked.getName());
                        } else {
                            forTokenLocked.inputDispatchingTimedOut(timeoutRecord, -1);
                        }
                        if (!z) {
                            dumpAnrStateAsync(forTokenLocked, null, timeoutRecord.mReason);
                        }
                        return;
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            timeoutRecord.mLatencyTracker.notifyAppUnresponsiveEnded();
        }
    }

    public void notifyWindowUnresponsive(IBinder iBinder, OptionalInt optionalInt, TimeoutRecord timeoutRecord) {
        try {
            timeoutRecord.mLatencyTracker.notifyWindowUnresponsiveStarted();
            if (!notifyWindowUnresponsive(iBinder, timeoutRecord)) {
                if (!optionalInt.isPresent()) {
                    Slog.w(StartingSurfaceController.TAG, "Failed to notify that window token=" + iBinder + " was unresponsive.");
                } else {
                    notifyWindowUnresponsive(optionalInt.getAsInt(), timeoutRecord);
                }
            }
        } finally {
            timeoutRecord.mLatencyTracker.notifyWindowUnresponsiveEnded();
        }
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
                ActivityRecord activityRecord = windowState.mInputChannelToken == iBinder ? windowState.mActivityRecord : null;
                Slog.i(StartingSurfaceController.TAG, "ANR in " + inputTargetFromToken + ". Reason:" + timeoutRecord.mReason);
                boolean isWindowAboveSystem = isWindowAboveSystem(windowState);
                WindowManagerService.resetPriorityAfterLockedSection();
                preDumpIfLockTooSlow_onlyForAnr(pid);
                if (activityRecord != null) {
                    activityRecord.inputDispatchingTimedOut(timeoutRecord, pid);
                } else {
                    this.mService.mAmInternal.inputDispatchingTimedOut(pid, isWindowAboveSystem, timeoutRecord);
                }
                dumpAnrStateAsync(activityRecord, windowState, timeoutRecord.mReason);
                return true;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void notifyWindowUnresponsive(int i, TimeoutRecord timeoutRecord) {
        Slog.i(StartingSurfaceController.TAG, "ANR in input window owned by pid=" + i + ". Reason: " + timeoutRecord.mReason);
        this.mService.mAmInternal.inputDispatchingTimedOut(i, true, timeoutRecord);
        dumpAnrStateAsync(null, null, timeoutRecord.mReason);
    }

    public void notifyWindowResponsive(IBinder iBinder, OptionalInt optionalInt) {
        if (notifyWindowResponsive(iBinder)) {
            return;
        }
        if (!optionalInt.isPresent()) {
            Slog.w(StartingSurfaceController.TAG, "Failed to notify that window token=" + iBinder + " was responsive.");
            return;
        }
        notifyWindowResponsive(optionalInt.getAsInt());
    }

    public final boolean notifyWindowResponsive(IBinder iBinder) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                InputTarget inputTargetFromToken = this.mService.getInputTargetFromToken(iBinder);
                if (inputTargetFromToken == null) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return false;
                }
                int pid = inputTargetFromToken.getPid();
                WindowManagerService.resetPriorityAfterLockedSection();
                this.mService.mAmInternal.inputDispatchingResumed(pid);
                return true;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void notifyWindowResponsive(int i) {
        this.mService.mAmInternal.inputDispatchingResumed(i);
    }

    public void onFocusChanged(WindowState windowState) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                ActivityRecord activityRecord = (ActivityRecord) this.mUnresponsiveAppByDisplay.get(windowState.getDisplayId());
                if (activityRecord != null && activityRecord == windowState.mActivityRecord) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    this.mService.mAmInternal.inputDispatchingResumed(activityRecord.getPid());
                    this.mUnresponsiveAppByDisplay.remove(windowState.getDisplayId());
                    return;
                }
                WindowManagerService.resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void preDumpIfLockTooSlow() {
        if (Build.IS_DEBUGGABLE) {
            final long uptimeMillis = SystemClock.uptimeMillis();
            if (this.mLastPreDumpTimeMs <= 0 || uptimeMillis - this.mLastPreDumpTimeMs >= PRE_DUMP_MIN_INTERVAL_MS) {
                Trace.traceBegin(64L, "preDumpIfLockTooSlow()");
                try {
                    final boolean[] zArr = {true};
                    ArrayMap arrayMap = new ArrayMap(2);
                    final WindowManagerService windowManagerService = this.mService;
                    Objects.requireNonNull(windowManagerService);
                    arrayMap.put(StartingSurfaceController.TAG, new Runnable() { // from class: com.android.server.wm.AnrController$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            WindowManagerService.this.monitor();
                        }
                    });
                    final ActivityManagerInternal activityManagerInternal = this.mService.mAmInternal;
                    Objects.requireNonNull(activityManagerInternal);
                    arrayMap.put("ActivityManager", new Runnable() { // from class: com.android.server.wm.AnrController$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            activityManagerInternal.monitor();
                        }
                    });
                    CountDownLatch countDownLatch = new CountDownLatch(arrayMap.size());
                    int i = 0;
                    while (i < arrayMap.size()) {
                        final String str = (String) arrayMap.keyAt(i);
                        final Runnable runnable = (Runnable) arrayMap.valueAt(i);
                        final CountDownLatch countDownLatch2 = countDownLatch;
                        int i2 = i;
                        CountDownLatch countDownLatch3 = countDownLatch;
                        ArrayMap arrayMap2 = arrayMap;
                        new Thread() { // from class: com.android.server.wm.AnrController.1
                            @Override // java.lang.Thread, java.lang.Runnable
                            public void run() {
                                runnable.run();
                                countDownLatch2.countDown();
                                long uptimeMillis2 = SystemClock.uptimeMillis() - uptimeMillis;
                                if (uptimeMillis2 > AnrController.PRE_DUMP_MONITOR_TIMEOUT_MS) {
                                    Slog.i(StartingSurfaceController.TAG, "Pre-dump acquired " + str + " in " + uptimeMillis2 + "ms");
                                    return;
                                }
                                if (StartingSurfaceController.TAG.equals(str)) {
                                    zArr[0] = false;
                                }
                            }
                        }.start();
                        i = i2 + 1;
                        countDownLatch = countDownLatch3;
                        arrayMap = arrayMap2;
                    }
                    try {
                        if (countDownLatch.await(PRE_DUMP_MONITOR_TIMEOUT_MS, TimeUnit.MILLISECONDS)) {
                            return;
                        }
                    } catch (InterruptedException unused) {
                    }
                    this.mLastPreDumpTimeMs = uptimeMillis;
                    Slog.i(StartingSurfaceController.TAG, "Pre-dump for unresponsive");
                    ArrayList arrayList = new ArrayList(1);
                    arrayList.add(Integer.valueOf(WindowManagerService.MY_PID));
                    ArrayList arrayList2 = null;
                    int[] pidsForCommands = zArr[0] ? Process.getPidsForCommands(new String[]{"/system/bin/surfaceflinger"}) : null;
                    if (pidsForCommands != null) {
                        arrayList2 = new ArrayList(1);
                        for (int i3 : pidsForCommands) {
                            arrayList2.add(Integer.valueOf(i3));
                        }
                    }
                    File dumpStackTraces = StackTracesDumpHelper.dumpStackTraces(arrayList, null, null, CompletableFuture.completedFuture(arrayList2), null, "Pre-dump", CriticalEventLog.getInstance().logLinesForSystemServerTraceFile(), new SystemServerInitThreadPool$$ExternalSyntheticLambda1(), null);
                    if (dumpStackTraces != null) {
                        dumpStackTraces.renameTo(new File(dumpStackTraces.getParent(), dumpStackTraces.getName() + "_pre"));
                    }
                } finally {
                    Trace.traceEnd(64L);
                }
            }
        }
    }

    public final void preDumpIfLockTooSlow_onlyForAnr(int i) {
        if (SystemProperties.get("ro.boot.debug_level", "0x4f4c").equals("0x4f4c")) {
            return;
        }
        long uptimeMillis = SystemClock.uptimeMillis();
        if (this.mLastPreDumpTimeMs_onlyForAnr <= 0 || uptimeMillis - this.mLastPreDumpTimeMs_onlyForAnr >= PRE_DUMP_MIN_INTERVAL_MS) {
            this.mLastPreDumpTimeMs_onlyForAnr = uptimeMillis;
            Slog.i(StartingSurfaceController.TAG, "Pre-dump(onlyForAnr) for unresponsive");
            new ArrayList(1).add(Integer.valueOf(i));
        }
    }

    public final void dumpAnrStateAsync(final ActivityRecord activityRecord, final WindowState windowState, final String str) {
        FgThread.getExecutor().execute(new Runnable() { // from class: com.android.server.wm.AnrController$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                AnrController.this.lambda$dumpAnrStateAsync$0(activityRecord, windowState, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dumpAnrStateAsync$0(ActivityRecord activityRecord, WindowState windowState, String str) {
        try {
            Trace.traceBegin(64L, "dumpAnrStateLocked()");
            WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    this.mService.saveANRStateLocked(activityRecord, windowState, str);
                    this.mService.mAtmService.saveANRState(str);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        } finally {
            Trace.traceEnd(64L);
        }
    }

    public final boolean isWindowAboveSystem(WindowState windowState) {
        return windowState.mBaseLayer > this.mService.mPolicy.getWindowLayerFromTypeLw(2038, windowState.mOwnerCanAddInternalSystemWindow);
    }
}
