package com.android.server.am;

import android.app.ActivityManager;
import android.app.AnrController;
import android.app.ApplicationErrorReport;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.p000pm.PackageManagerInternal;
import android.content.pm.ApplicationInfo;
import android.content.pm.IncrementalStatesInfo;
import android.os.BugreportParams;
import android.os.Debug;
import android.os.IBinder;
import android.os.Message;
import android.os.Process;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.incremental.IIncrementalService;
import android.os.incremental.IncrementalManager;
import android.os.incremental.IncrementalMetrics;
import android.provider.Settings;
import android.util.EventLog;
import android.util.Slog;
import android.util.SparseBooleanArray;
import com.android.internal.os.ProcessCpuTracker;
import com.android.internal.os.TimeoutRecord;
import com.android.internal.os.anr.AnrLatencyTracker;
import com.android.internal.util.FrameworkStatsLog;
import com.android.modules.expresslog.Counter;
import com.android.server.ResourcePressureUtil;
import com.android.server.Watchdog;
import com.android.server.criticalevents.CriticalEventLog;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.am.AppNotRespondingDialog;
import com.android.server.am.BinderTransaction;
import com.android.server.wm.WindowProcessController;
import com.android.server.stats.pull.ProcfsMemoryUtil;
import com.samsung.android.core.SystemDumpWriter;
import com.samsung.android.rune.CoreRune;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public class ProcessErrorStateRecord {
    public boolean DEBUG_ATRACE;
    public String mAnrAnnotation;
    public AppNotRespondingDialog.Data mAnrData;
    public final ProcessRecord mApp;
    public boolean mBad;
    public Runnable mCrashHandler;
    public boolean mCrashing;
    public ActivityManager.ProcessErrorStateInfo mCrashingReport;
    public final ErrorDialogController mDialogController;
    public ComponentName mErrorReportReceiver;
    public boolean mForceCrashReport;
    public boolean mNotResponding;
    public ActivityManager.ProcessErrorStateInfo mNotRespondingReport;
    public final ActivityManagerGlobalLock mProcLock;
    public final ActivityManagerService mService;

    public boolean isBad() {
        return this.mBad;
    }

    public void setBad(boolean z) {
        this.mBad = z;
    }

    public boolean isCrashing() {
        return this.mCrashing;
    }

    public void setCrashing(boolean z) {
        this.mCrashing = z;
        this.mApp.getWindowProcessController().setCrashing(z);
    }

    public boolean isForceCrashReport() {
        return this.mForceCrashReport;
    }

    public void setForceCrashReport(boolean z) {
        this.mForceCrashReport = z;
    }

    public boolean isNotResponding() {
        return this.mNotResponding;
    }

    public void setNotResponding(boolean z) {
        this.mNotResponding = z;
        this.mApp.getWindowProcessController().setNotResponding(z);
    }

    public Runnable getCrashHandler() {
        return this.mCrashHandler;
    }

    public void setCrashHandler(Runnable runnable) {
        this.mCrashHandler = runnable;
    }

    public ActivityManager.ProcessErrorStateInfo getCrashingReport() {
        return this.mCrashingReport;
    }

    public void setCrashingReport(ActivityManager.ProcessErrorStateInfo processErrorStateInfo) {
        this.mCrashingReport = processErrorStateInfo;
    }

    public String getAnrAnnotation() {
        return this.mAnrAnnotation;
    }

    public void setAnrAnnotation(String str) {
        this.mAnrAnnotation = str;
    }

    public ActivityManager.ProcessErrorStateInfo getNotRespondingReport() {
        return this.mNotRespondingReport;
    }

    public void setNotRespondingReport(ActivityManager.ProcessErrorStateInfo processErrorStateInfo) {
        this.mNotRespondingReport = processErrorStateInfo;
    }

    public ComponentName getErrorReportReceiver() {
        return this.mErrorReportReceiver;
    }

    public ErrorDialogController getDialogController() {
        return this.mDialogController;
    }

    public void setAnrData(AppNotRespondingDialog.Data data) {
        this.mAnrData = data;
    }

    public AppNotRespondingDialog.Data getAnrData() {
        return this.mAnrData;
    }

    public ProcessErrorStateRecord(ProcessRecord processRecord) {
        this.DEBUG_ATRACE = CoreRune.IS_DEBUG_LEVEL_MID || CoreRune.IS_DEBUG_LEVEL_HIGH;
        this.mApp = processRecord;
        ActivityManagerService activityManagerService = processRecord.mService;
        this.mService = activityManagerService;
        this.mProcLock = activityManagerService.mProcLock;
        this.mDialogController = new ErrorDialogController(processRecord);
    }

    public boolean skipAnrLocked(String str) {
        if (this.mService.mAtmInternal.isShuttingDown()) {
            Slog.i("ActivityManager", "During shutdown skipping ANR: " + this + " " + str);
            return true;
        }
        if (isNotResponding()) {
            Slog.i("ActivityManager", "Skipping duplicate ANR: " + this + " " + str);
            return true;
        }
        if (isCrashing()) {
            Slog.i("ActivityManager", "Crashing app skipping ANR: " + this + " " + str);
            return true;
        }
        if (this.mApp.isKilledByAm()) {
            Slog.i("ActivityManager", "App already killed by AM skipping ANR: " + this + " " + str);
            return true;
        }
        if (!this.mApp.isKilled()) {
            return false;
        }
        Slog.i("ActivityManager", "Skipping died app ANR: " + this + " " + str);
        return true;
    }

    public void captureAtraceLog() {
        if (this.DEBUG_ATRACE) {
            int i = SystemProperties.getInt("debug.perfmond.atrace", 0);
            boolean z = i == 1 || i == 3 || i == 103;
            Slog.i("ActivityManager", "Trace mode: " + i + ", ANR trace enabled : " + z);
            if (z) {
                Intent intent = new Intent("com.samsung.intent.action.PERFORMANCE_LOGGING");
                intent.putExtra("performance_logging_ctrl", 3);
                this.mService.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x0419  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x0426  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x042d  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x0439  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x0444  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x045a  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x0465  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x0470  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x047b  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x0486  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x048e  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x0499  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x04a4  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x04bc  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x04c8  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x051d A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:143:0x051e  */
    /* JADX WARN: Removed duplicated region for block: B:181:0x04cb  */
    /* JADX WARN: Removed duplicated region for block: B:182:0x04c2  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x04a0  */
    /* JADX WARN: Removed duplicated region for block: B:184:0x0495  */
    /* JADX WARN: Removed duplicated region for block: B:185:0x0482  */
    /* JADX WARN: Removed duplicated region for block: B:186:0x0477  */
    /* JADX WARN: Removed duplicated region for block: B:187:0x046c  */
    /* JADX WARN: Removed duplicated region for block: B:188:0x0461  */
    /* JADX WARN: Removed duplicated region for block: B:190:0x044b  */
    /* JADX WARN: Removed duplicated region for block: B:191:0x0440  */
    /* JADX WARN: Removed duplicated region for block: B:192:0x0434  */
    /* JADX WARN: Removed duplicated region for block: B:193:0x0429  */
    /* JADX WARN: Removed duplicated region for block: B:194:0x0420  */
    /* JADX WARN: Removed duplicated region for block: B:195:0x040b  */
    /* JADX WARN: Removed duplicated region for block: B:197:0x03fe  */
    /* JADX WARN: Removed duplicated region for block: B:198:0x03e8  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x03c9  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x03e2  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x03ee  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x0408  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void appNotResponding(String str, ApplicationInfo applicationInfo, String str2, WindowProcessController windowProcessController, boolean z, TimeoutRecord timeoutRecord, ExecutorService executorService, final boolean z2, boolean z3, Future future) {
        UUID uuid;
        long j;
        long j2;
        IncrementalMetrics incrementalMetrics;
        String str3;
        final String str4 = timeoutRecord.mReason;
        final AnrLatencyTracker anrLatencyTracker = timeoutRecord.mLatencyTracker;
        final ArrayList arrayList = new ArrayList(5);
        final SparseBooleanArray sparseBooleanArray = new SparseBooleanArray(20);
        captureAtraceLog();
        this.mApp.getWindowProcessController().appEarlyNotResponding(str4, new Runnable() { // from class: com.android.server.am.ProcessErrorStateRecord$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ProcessErrorStateRecord.this.lambda$appNotResponding$0(anrLatencyTracker, str4);
            }
        });
        long uptimeMillis = SystemClock.uptimeMillis();
        Future<?> submit = isMonitorCpuUsage() ? executorService.submit(new Runnable() { // from class: com.android.server.am.ProcessErrorStateRecord$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                ProcessErrorStateRecord.this.lambda$appNotResponding$1(anrLatencyTracker);
            }
        }) : null;
        SystemDumpWriter.saveDumpsysFiles("ANR", false);
        final int pid = this.mApp.getPid();
        anrLatencyTracker.waitingOnAMSLockStarted();
        ActivityManagerService activityManagerService = this.mService;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                anrLatencyTracker.waitingOnAMSLockEnded();
                setAnrAnnotation(str4);
                Counter.logIncrement("stability_anr.value_total_anrs");
                if (skipAnrLocked(str4)) {
                    anrLatencyTracker.anrSkippedProcessErrorStateRecordAppNotResponding();
                    Counter.logIncrement("stability_anr.value_skipped_anrs");
                    return;
                }
                anrLatencyTracker.waitingOnProcLockStarted();
                ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
                ActivityManagerService.boostPriorityForProcLockedSection();
                synchronized (activityManagerGlobalLock) {
                    try {
                        anrLatencyTracker.waitingOnProcLockEnded();
                        setNotResponding(true);
                    } finally {
                    }
                }
                ActivityManagerService.resetPriorityAfterProcLockedSection();
                ProcessRecord processRecord = this.mApp;
                EventLog.writeEvent(30008, Integer.valueOf(this.mApp.userId), Integer.valueOf(pid), processRecord.processName, Integer.valueOf(processRecord.info.flags), str4);
                TraceErrorLogger traceErrorLogger = this.mService.mTraceErrorLogger;
                if (traceErrorLogger == null || !traceErrorLogger.isAddErrorIdEnabled()) {
                    uuid = null;
                } else {
                    UUID generateErrorId = this.mService.mTraceErrorLogger.generateErrorId();
                    this.mService.mTraceErrorLogger.addProcessInfoAndErrorIdToTrace(this.mApp.processName, pid, generateErrorId);
                    this.mService.mTraceErrorLogger.addSubjectToTrace(str4, generateErrorId);
                    uuid = generateErrorId;
                }
                FrameworkStatsLog.write(FrameworkStatsLog.ANR_OCCURRED_PROCESSING_STARTED, this.mApp.processName);
                arrayList.add(Integer.valueOf(pid));
                final boolean isSilentAnr = isSilentAnr();
                if (!isSilentAnr && !z2) {
                    final int pid2 = (windowProcessController == null || windowProcessController.getPid() <= 0) ? pid : windowProcessController.getPid();
                    if (pid2 != pid) {
                        arrayList.add(Integer.valueOf(pid2));
                    }
                    int i = ActivityManagerService.MY_PID;
                    if (i != pid && i != pid2) {
                        arrayList.add(Integer.valueOf(i));
                    }
                    if (i != pid && i != pid2 && !arrayList.contains(Integer.valueOf(i))) {
                        sparseBooleanArray.put(i, true);
                    }
                    this.mService.mProcessList.forEachLruProcessesLOSP(false, new Consumer() { // from class: com.android.server.am.ProcessErrorStateRecord$$ExternalSyntheticLambda2
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ProcessErrorStateRecord.lambda$appNotResponding$2(pid, pid2, arrayList, sparseBooleanArray, (ProcessRecord) obj);
                        }
                    });
                }
                ActivityManagerService.resetPriorityAfterLockedSection();
                String buildMemoryHeadersFor = buildMemoryHeadersFor(pid);
                anrLatencyTracker.criticalEventLogStarted();
                CriticalEventLog criticalEventLog = CriticalEventLog.getInstance();
                int processClassEnum = this.mApp.getProcessClassEnum();
                ProcessRecord processRecord2 = this.mApp;
                String logLinesForTraceFile = criticalEventLog.logLinesForTraceFile(processClassEnum, processRecord2.processName, processRecord2.uid);
                anrLatencyTracker.criticalEventLogEnded();
                CriticalEventLog criticalEventLog2 = CriticalEventLog.getInstance();
                int processClassEnum2 = this.mApp.getProcessClassEnum();
                ProcessRecord processRecord3 = this.mApp;
                SparseBooleanArray sparseBooleanArray2 = sparseBooleanArray;
                criticalEventLog2.logAnr(str4, processClassEnum2, processRecord3.processName, processRecord3.uid, processRecord3.mPid);
                StringBuilder sb = new StringBuilder();
                sb.setLength(0);
                sb.append("ANR in ");
                sb.append(this.mApp.processName);
                if (str != null) {
                    sb.append(" (");
                    sb.append(str);
                    sb.append(")");
                }
                sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                sb.append("PID: ");
                sb.append(pid);
                sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                if (str4 != null) {
                    sb.append("Reason: ");
                    sb.append(str4);
                    sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                }
                if (str2 != null && str2.equals(str)) {
                    sb.append("Parent: ");
                    sb.append(str2);
                    sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                }
                if (uuid != null) {
                    sb.append("ErrorId: ");
                    sb.append(uuid.toString());
                    sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                }
                sb.append("Frozen: s[");
                sb.append(Process.isFrozenState(pid));
                sb.append("] g[");
                sb.append(this.mApp.mOptRecord.isFrozen());
                sb.append("]");
                sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                AnrController anrController = this.mService.mActivityTaskManager.getAnrController(applicationInfo);
                if (anrController != null) {
                    String str5 = applicationInfo.packageName;
                    int i2 = applicationInfo.uid;
                    long anrDelayMillis = anrController.getAnrDelayMillis(str5, i2);
                    anrController.onAnrDelayStarted(str5, i2);
                    Slog.i("ActivityManager", "ANR delay of " + anrDelayMillis + "ms started for " + str5);
                    j = anrDelayMillis;
                } else {
                    j = 0;
                }
                StringBuilder sb2 = new StringBuilder();
                anrLatencyTracker.currentPsiStateCalled();
                String currentPsiState = ResourcePressureUtil.currentPsiState();
                anrLatencyTracker.currentPsiStateReturned();
                sb2.append(currentPsiState);
                ProcessCpuTracker processCpuTracker = new ProcessCpuTracker(true);
                Future submit2 = executorService.submit(new Callable() { // from class: com.android.server.am.ProcessErrorStateRecord$$ExternalSyntheticLambda3
                    @Override // java.util.concurrent.Callable
                    public final Object call() {
                        ArrayList lambda$appNotResponding$3;
                        lambda$appNotResponding$3 = ProcessErrorStateRecord.this.lambda$appNotResponding$3(anrLatencyTracker, isSilentAnr, z2);
                        return lambda$appNotResponding$3;
                    }
                });
                StringWriter stringWriter = new StringWriter();
                long j3 = j;
                AtomicLong atomicLong = new AtomicLong(-1L);
                ProcessCpuTracker processCpuTracker2 = isSilentAnr ? null : processCpuTracker;
                if (isSilentAnr) {
                    sparseBooleanArray2 = null;
                }
                File dumpStackTraces = StackTracesDumpHelper.dumpStackTraces(arrayList, processCpuTracker2, sparseBooleanArray2, submit2, stringWriter, atomicLong, str4, logLinesForTraceFile, buildMemoryHeadersFor, executorService, future, anrLatencyTracker);
                if (isMonitorCpuUsage()) {
                    try {
                        submit.get();
                    } catch (InterruptedException e) {
                        Slog.w("ActivityManager", "Interrupted while updating the CPU stats", e);
                    } catch (ExecutionException e2) {
                        Slog.w("ActivityManager", "Failed to update the CPU stats", e2.getCause());
                    }
                    this.mService.updateCpuStatsNow();
                    j2 = uptimeMillis;
                    this.mService.mAppProfiler.printCurrentCpuState(sb2, j2);
                    sb.append(processCpuTracker.printCurrentLoad());
                    sb.append(processCpuTracker.printCpuCoreInfo());
                    sb.append((CharSequence) sb2);
                } else {
                    j2 = uptimeMillis;
                }
                sb2.append(stringWriter.getBuffer());
                sb.append(processCpuTracker.printCurrentState(j2));
                Slog.e("ActivityManager", sb.toString());
                if (dumpStackTraces == null) {
                    Process.sendSignal(pid, 3);
                } else if (atomicLong.get() > 0) {
                    long j4 = atomicLong.get();
                    AppExitInfoTracker appExitInfoTracker = this.mService.mProcessList.mAppExitInfoTracker;
                    ProcessRecord processRecord4 = this.mApp;
                    appExitInfoTracker.scheduleLogAnrTrace(pid, processRecord4.uid, processRecord4.getPackageList(), dumpStackTraces, 0L, j4);
                }
                PackageManagerInternal packageManagerInternal = this.mService.getPackageManagerInternal();
                if (this.mApp.info != null && this.mApp.info.packageName != null && packageManagerInternal != null) {
                    IncrementalStatesInfo incrementalStatesInfo = packageManagerInternal.getIncrementalStatesInfo(this.mApp.info.packageName, 1000, this.mApp.userId);
                    r7 = incrementalStatesInfo != null ? incrementalStatesInfo.getProgress() : 1.0f;
                    String codePath = this.mApp.info.getCodePath();
                    if (codePath != null && !codePath.isEmpty() && IncrementalManager.isIncrementalPath(codePath)) {
                        Slog.e("ActivityManager", "App ANR on incremental package " + this.mApp.info.packageName + " which is " + ((int) (r7 * 100.0f)) + "% loaded.");
                        IBinder service = ServiceManager.getService("incremental");
                        if (service != null) {
                            incrementalMetrics = new IncrementalManager(IIncrementalService.Stub.asInterface(service)).getMetrics(codePath);
                            if (incrementalMetrics != null) {
                                sb.append("Package is ");
                                sb.append((int) (100.0f * r7));
                                sb.append("% loaded.\n");
                            }
                            ProcessRecord processRecord5 = this.mApp;
                            FrameworkStatsLog.write(79, processRecord5.uid, processRecord5.processName, str != null ? "unknown" : str, str4, processRecord5.info == null ? this.mApp.info.isInstantApp() ? 2 : 1 : 0, !this.mApp.isInterestingToUserLocked() ? 2 : 1, this.mApp.getProcessClassEnum(), this.mApp.info == null ? this.mApp.info.packageName : "", incrementalMetrics == null, r7, incrementalMetrics == null ? incrementalMetrics.getMillisSinceOldestPendingRead() : -1L, incrementalMetrics == null ? incrementalMetrics.getStorageHealthStatusCode() : -1, incrementalMetrics == null ? incrementalMetrics.getDataLoaderStatusCode() : -1, incrementalMetrics == null && incrementalMetrics.getReadLogsEnabled(), incrementalMetrics == null ? incrementalMetrics.getMillisSinceLastDataLoaderBind() : -1L, incrementalMetrics == null ? incrementalMetrics.getDataLoaderBindDelayMillis() : -1L, incrementalMetrics == null ? incrementalMetrics.getTotalDelayedReads() : -1, incrementalMetrics == null ? incrementalMetrics.getTotalFailedReads() : -1, incrementalMetrics != null ? incrementalMetrics.getLastReadErrorUid() : -1, incrementalMetrics == null ? incrementalMetrics.getMillisSinceLastReadError() : -1L, incrementalMetrics == null ? incrementalMetrics.getLastReadErrorNumber() : 0, incrementalMetrics != null ? incrementalMetrics.getTotalDelayedReadsDurationMillis() : -1L);
                            ProcessRecord processRecord6 = windowProcessController == null ? (ProcessRecord) windowProcessController.mOwner : null;
                            BinderTransaction.BinderProcsInfo binderTransactionInfo = !ActivityManagerService.DEBUG_LEVEL_LOW ? null : StackTracesDumpHelper.getBinderTransactionInfo(pid);
                            ActivityManagerService activityManagerService2 = this.mService;
                            ProcessRecord processRecord7 = this.mApp;
                            activityManagerService2.addErrorToDropBox("anr", processRecord7, processRecord7.processName, str, str2, processRecord6, null, sb2.toString(), dumpStackTraces, null, new Float(r7), incrementalMetrics, uuid, binderTransactionInfo);
                            if (this.mApp.getWindowProcessController().appNotResponding(sb.toString(), new Runnable() { // from class: com.android.server.am.ProcessErrorStateRecord$$ExternalSyntheticLambda4
                                @Override // java.lang.Runnable
                                public final void run() {
                                    ProcessErrorStateRecord.this.lambda$appNotResponding$4();
                                }
                            }, new Runnable() { // from class: com.android.server.am.ProcessErrorStateRecord$$ExternalSyntheticLambda5
                                @Override // java.lang.Runnable
                                public final void run() {
                                    ProcessErrorStateRecord.this.lambda$appNotResponding$5();
                                }
                            })) {
                                ActivityManagerService activityManagerService3 = this.mService;
                                ActivityManagerService.boostPriorityForLockedSection();
                                synchronized (activityManagerService3) {
                                    try {
                                        BatteryStatsService batteryStatsService = this.mService.mBatteryStatsService;
                                        if (batteryStatsService != null) {
                                            ProcessRecord processRecord8 = this.mApp;
                                            batteryStatsService.noteProcessAnr(processRecord8.processName, processRecord8.uid);
                                        }
                                        if (isSilentAnr() && !this.mApp.isDebugging()) {
                                            this.mApp.killLocked("bg anr", 6, true);
                                            return;
                                        }
                                        ActivityManagerGlobalLock activityManagerGlobalLock2 = this.mProcLock;
                                        ActivityManagerService.boostPriorityForProcLockedSection();
                                        synchronized (activityManagerGlobalLock2) {
                                            if (str4 != null) {
                                                try {
                                                    str3 = "ANR " + str4;
                                                } finally {
                                                }
                                            } else {
                                                str3 = "ANR";
                                            }
                                            makeAppNotRespondingLSP(str, str3, sb.toString());
                                            this.mDialogController.setAnrController(anrController);
                                        }
                                        ActivityManagerService.resetPriorityAfterProcLockedSection();
                                        if (this.mService.mUiHandler != null) {
                                            Message obtain = Message.obtain();
                                            obtain.what = 2;
                                            obtain.obj = new AppNotRespondingDialog.Data(this.mApp, applicationInfo, z, z3);
                                            this.mService.mUiHandler.sendMessageDelayed(obtain, j3);
                                        }
                                        ActivityManagerService.resetPriorityAfterLockedSection();
                                        Debug.saveDump(new BugreportParams(15), this.mApp.processName);
                                        return;
                                    } finally {
                                        ActivityManagerService.resetPriorityAfterLockedSection();
                                    }
                                }
                            }
                            return;
                        }
                    }
                }
                incrementalMetrics = null;
                if (incrementalMetrics != null) {
                }
                ProcessRecord processRecord52 = this.mApp;
                FrameworkStatsLog.write(79, processRecord52.uid, processRecord52.processName, str != null ? "unknown" : str, str4, processRecord52.info == null ? this.mApp.info.isInstantApp() ? 2 : 1 : 0, !this.mApp.isInterestingToUserLocked() ? 2 : 1, this.mApp.getProcessClassEnum(), this.mApp.info == null ? this.mApp.info.packageName : "", incrementalMetrics == null, r7, incrementalMetrics == null ? incrementalMetrics.getMillisSinceOldestPendingRead() : -1L, incrementalMetrics == null ? incrementalMetrics.getStorageHealthStatusCode() : -1, incrementalMetrics == null ? incrementalMetrics.getDataLoaderStatusCode() : -1, incrementalMetrics == null && incrementalMetrics.getReadLogsEnabled(), incrementalMetrics == null ? incrementalMetrics.getMillisSinceLastDataLoaderBind() : -1L, incrementalMetrics == null ? incrementalMetrics.getDataLoaderBindDelayMillis() : -1L, incrementalMetrics == null ? incrementalMetrics.getTotalDelayedReads() : -1, incrementalMetrics == null ? incrementalMetrics.getTotalFailedReads() : -1, incrementalMetrics != null ? incrementalMetrics.getLastReadErrorUid() : -1, incrementalMetrics == null ? incrementalMetrics.getMillisSinceLastReadError() : -1L, incrementalMetrics == null ? incrementalMetrics.getLastReadErrorNumber() : 0, incrementalMetrics != null ? incrementalMetrics.getTotalDelayedReadsDurationMillis() : -1L);
                if (windowProcessController == null) {
                }
                if (!ActivityManagerService.DEBUG_LEVEL_LOW) {
                }
                ActivityManagerService activityManagerService22 = this.mService;
                ProcessRecord processRecord72 = this.mApp;
                activityManagerService22.addErrorToDropBox("anr", processRecord72, processRecord72.processName, str, str2, processRecord6, null, sb2.toString(), dumpStackTraces, null, new Float(r7), incrementalMetrics, uuid, binderTransactionInfo);
                if (this.mApp.getWindowProcessController().appNotResponding(sb.toString(), new Runnable() { // from class: com.android.server.am.ProcessErrorStateRecord$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        ProcessErrorStateRecord.this.lambda$appNotResponding$4();
                    }
                }, new Runnable() { // from class: com.android.server.am.ProcessErrorStateRecord$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        ProcessErrorStateRecord.this.lambda$appNotResponding$5();
                    }
                })) {
                }
            } finally {
                ActivityManagerService.resetPriorityAfterLockedSection();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$appNotResponding$0(AnrLatencyTracker anrLatencyTracker, String str) {
        anrLatencyTracker.waitingOnAMSLockStarted();
        ActivityManagerService activityManagerService = this.mService;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                anrLatencyTracker.waitingOnAMSLockEnded();
                setAnrAnnotation(str);
                this.mApp.killLocked("anr", 6, true);
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterLockedSection();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$appNotResponding$1(AnrLatencyTracker anrLatencyTracker) {
        anrLatencyTracker.updateCpuStatsNowCalled();
        this.mService.updateCpuStatsNow();
        anrLatencyTracker.updateCpuStatsNowReturned();
    }

    public static /* synthetic */ void lambda$appNotResponding$2(int i, int i2, ArrayList arrayList, SparseBooleanArray sparseBooleanArray, ProcessRecord processRecord) {
        int pid;
        if (processRecord == null || processRecord.getThread() == null || (pid = processRecord.getPid()) <= 0 || pid == i || pid == i2 || pid == ActivityManagerService.MY_PID) {
            return;
        }
        if (processRecord.isPersistent()) {
            arrayList.add(Integer.valueOf(pid));
        } else if (processRecord.mServices.isTreatedLikeActivity()) {
            arrayList.add(Integer.valueOf(pid));
        } else {
            sparseBooleanArray.put(pid, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ ArrayList lambda$appNotResponding$3(AnrLatencyTracker anrLatencyTracker, boolean z, boolean z2) {
        String[] strArr;
        anrLatencyTracker.nativePidCollectionStarted();
        ArrayList arrayList = null;
        if (!(this.mApp.info.isSystemApp() || this.mApp.info.isSystemExt()) || z || z2) {
            int i = 0;
            while (true) {
                String[] strArr2 = Watchdog.NATIVE_STACKS_OF_INTEREST;
                if (i >= strArr2.length) {
                    strArr = null;
                    break;
                }
                if (strArr2[i].equals(this.mApp.processName)) {
                    strArr = new String[]{this.mApp.processName};
                    break;
                }
                i++;
            }
        } else {
            strArr = Watchdog.NATIVE_STACKS_OF_INTEREST;
        }
        int[] pidsForCommands = strArr == null ? null : Process.getPidsForCommands(strArr);
        if (pidsForCommands != null) {
            arrayList = new ArrayList(pidsForCommands.length);
            for (int i2 : pidsForCommands) {
                arrayList.add(Integer.valueOf(i2));
            }
        }
        anrLatencyTracker.nativePidCollectionEnded();
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$appNotResponding$4() {
        ActivityManagerService activityManagerService = this.mService;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                this.mApp.killLocked("anr", 6, true);
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterLockedSection();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$appNotResponding$5() {
        ActivityManagerService activityManagerService = this.mService;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                this.mService.mServices.scheduleServiceTimeoutLocked(this.mApp);
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterLockedSection();
    }

    public final void makeAppNotRespondingLSP(String str, String str2, String str3) {
        setNotResponding(true);
        AppErrors appErrors = this.mService.mAppErrors;
        if (appErrors != null) {
            this.mNotRespondingReport = appErrors.generateProcessError(this.mApp, 2, str, str2, str3, null);
        }
        startAppProblemLSP();
        this.mApp.getWindowProcessController().stopFreezingActivities();
    }

    public void startAppProblemLSP() {
        this.mErrorReportReceiver = null;
        for (int i : this.mService.mUserController.getCurrentProfileIds()) {
            ProcessRecord processRecord = this.mApp;
            if (processRecord.userId == i) {
                this.mErrorReportReceiver = ApplicationErrorReport.getErrorReportReceiver(this.mService.mContext, processRecord.info.packageName, this.mApp.info.flags);
            }
        }
        for (BroadcastQueue broadcastQueue : this.mService.mBroadcastQueues) {
            broadcastQueue.onApplicationProblemLocked(this.mApp);
        }
    }

    public final boolean isInterestingForBackgroundTraces() {
        if (this.mApp.getPid() == ActivityManagerService.MY_PID || this.mApp.isInterestingToUserLocked()) {
            return true;
        }
        return (this.mApp.info != null && "com.android.systemui".equals(this.mApp.info.packageName)) || this.mApp.mState.hasTopUi() || this.mApp.mState.hasOverlayUi();
    }

    public final boolean getShowBackground() {
        ContentResolver contentResolver = this.mService.mContext.getContentResolver();
        return Settings.Secure.getIntForUser(contentResolver, "anr_show_background", 0, contentResolver.getUserId()) != 0;
    }

    public final String buildMemoryHeadersFor(int i) {
        if (i <= 0) {
            Slog.i("ActivityManager", "Memory header requested with invalid pid: " + i);
            return null;
        }
        ProcfsMemoryUtil.MemorySnapshot readMemorySnapshotFromProcfs = ProcfsMemoryUtil.readMemorySnapshotFromProcfs(i);
        if (readMemorySnapshotFromProcfs == null) {
            Slog.i("ActivityManager", "Failed to get memory snapshot for pid:" + i);
            return null;
        }
        return "RssHwmKb: " + readMemorySnapshotFromProcfs.rssHighWaterMarkInKilobytes + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE + "RssKb: " + readMemorySnapshotFromProcfs.rssInKilobytes + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE + "RssAnonKb: " + readMemorySnapshotFromProcfs.anonRssInKilobytes + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE + "RssShmemKb: " + readMemorySnapshotFromProcfs.rssShmemKilobytes + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE + "VmSwapKb: " + readMemorySnapshotFromProcfs.swapInKilobytes + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE;
    }

    public boolean isSilentAnr() {
        return (getShowBackground() || isInterestingForBackgroundTraces()) ? false : true;
    }

    public boolean isMonitorCpuUsage() {
        AppProfiler appProfiler = this.mService.mAppProfiler;
        return true;
    }

    public void onCleanupApplicationRecordLSP() {
        getDialogController().clearAllErrorDialogs();
        setCrashing(false);
        setNotResponding(false);
    }

    public void dump(PrintWriter printWriter, String str, long j) {
        ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
        ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerGlobalLock) {
            try {
                if (this.mCrashing || this.mDialogController.hasCrashDialogs() || this.mNotResponding || this.mDialogController.hasAnrDialogs() || this.mBad) {
                    printWriter.print(str);
                    printWriter.print(" mCrashing=" + this.mCrashing);
                    printWriter.print(" " + this.mDialogController.getCrashDialogs());
                    printWriter.print(" mNotResponding=" + this.mNotResponding);
                    printWriter.print(" " + this.mDialogController.getAnrDialogs());
                    printWriter.print(" bad=" + this.mBad);
                    if (this.mErrorReportReceiver != null) {
                        printWriter.print(" errorReportReceiver=");
                        printWriter.print(this.mErrorReportReceiver.flattenToShortString());
                    }
                    printWriter.println();
                }
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterProcLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterProcLockedSection();
    }
}
