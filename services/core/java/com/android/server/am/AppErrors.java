package com.android.server.am;

import android.app.ActivityManager;
import android.app.ApplicationErrorReport;
import android.app.IActivityController;
import android.content.Context;
import android.content.pm.PackageManagerInternal;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.TimeUtils;
import android.util.proto.ProtoOutputStream;

import com.android.internal.app.ProcessMap;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.PackageWatchdog;
import com.android.server.PinnerService$$ExternalSyntheticOutline0;
import com.android.server.Watchdog;
import com.android.server.pm.PackageManagerService;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.ActivityTaskManagerService;
import com.android.server.wm.WindowManagerGlobalLock;
import com.android.server.wm.WindowManagerService;

import com.sec.android.iaft.IAFDDiagnosis;

import java.io.PrintWriter;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AppErrors {
    public ArraySet mAppsNotReportingCrashes;
    public final Context mContext;
    public final PackageWatchdog mPackageWatchdog;
    public final ActivityManagerGlobalLock mProcLock;
    public final ActivityManagerService mService;
    public final ProcessMap mProcessCrashTimes = new ProcessMap();
    public final ProcessMap mProcessCrashTimesPersistent = new ProcessMap();
    public final ProcessMap mProcessCrashShowDialogTimes = new ProcessMap();
    public final ProcessMap mProcessCrashCounts = new ProcessMap();
    public volatile ProcessMap mBadProcesses = new ProcessMap();
    public final Object mBadProcessLock = new Object();
    public final CrashDetectionAndOptimization crashDetectionAndOptimization =
            new CrashDetectionAndOptimization();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BadProcessInfo {
        public final String longMsg;
        public final String shortMsg;
        public final String stack;
        public final long time;

        public BadProcessInfo(String str, long j, String str2, String str3) {
            this.time = j;
            this.shortMsg = str;
            this.longMsg = str2;
            this.stack = str3;
        }
    }

    public AppErrors(
            Context context,
            ActivityManagerService activityManagerService,
            PackageWatchdog packageWatchdog) {
        context.assertRuntimeOverlayThemable();
        this.mService = activityManagerService;
        this.mProcLock = activityManagerService.mProcLock;
        this.mContext = context;
        this.mPackageWatchdog = packageWatchdog;
        IAFDDiagnosis.getInstance().init(context);
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x006b A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x006c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.content.Intent createAppErrorIntentLOSP(
            com.android.server.am.ProcessRecord r4,
            long r5,
            android.app.ApplicationErrorReport.CrashInfo r7) {
        /*
            com.android.server.am.ProcessErrorStateRecord r0 = r4.mErrorState
            android.content.ComponentName r1 = r0.mErrorReportReceiver
            r2 = 0
            if (r1 != 0) goto L9
        L7:
            r1 = r2
            goto L69
        L9:
            boolean r1 = r0.mCrashing
            if (r1 != 0) goto L16
            boolean r1 = r0.mNotResponding
            if (r1 != 0) goto L16
            boolean r1 = r0.mForceCrashReport
            if (r1 != 0) goto L16
            goto L7
        L16:
            android.app.ApplicationErrorReport r1 = new android.app.ApplicationErrorReport
            r1.<init>()
            android.content.pm.ApplicationInfo r3 = r4.info
            java.lang.String r3 = r3.packageName
            r1.packageName = r3
            android.content.ComponentName r3 = r0.mErrorReportReceiver
            java.lang.String r3 = r3.getPackageName()
            r1.installerPackageName = r3
            java.lang.String r3 = r4.processName
            r1.processName = r3
            r1.time = r5
            android.content.pm.ApplicationInfo r5 = r4.info
            int r5 = r5.flags
            r6 = 1
            r5 = r5 & r6
            if (r5 == 0) goto L39
            r5 = r6
            goto L3a
        L39:
            r5 = 0
        L3a:
            r1.systemApp = r5
            boolean r5 = r0.mCrashing
            if (r5 != 0) goto L65
            boolean r5 = r0.mForceCrashReport
            if (r5 == 0) goto L45
            goto L65
        L45:
            boolean r5 = r0.mNotResponding
            if (r5 == 0) goto L69
            android.app.ActivityManager$ProcessErrorStateInfo r5 = r0.mNotRespondingReport
            if (r5 != 0) goto L4e
            goto L7
        L4e:
            r6 = 2
            r1.type = r6
            android.app.ApplicationErrorReport$AnrInfo r6 = new android.app.ApplicationErrorReport$AnrInfo
            r6.<init>()
            r1.anrInfo = r6
            java.lang.String r7 = r5.tag
            r6.activity = r7
            java.lang.String r7 = r5.shortMsg
            r6.cause = r7
            java.lang.String r5 = r5.longMsg
            r6.info = r5
            goto L69
        L65:
            r1.type = r6
            r1.crashInfo = r7
        L69:
            if (r1 != 0) goto L6c
            return r2
        L6c:
            android.content.Intent r5 = new android.content.Intent
            java.lang.String r6 = "android.intent.action.APP_ERROR"
            r5.<init>(r6)
            com.android.server.am.ProcessErrorStateRecord r4 = r4.mErrorState
            android.content.ComponentName r4 = r4.mErrorReportReceiver
            r5.setComponent(r4)
            java.lang.String r4 = "android.intent.extra.BUG_REPORT"
            r5.putExtra(r4, r1)
            r4 = 268435456(0x10000000, float:2.5243549E-29)
            r5.addFlags(r4)
            return r5
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.AppErrors.createAppErrorIntentLOSP(com.android.server.am.ProcessRecord,"
                    + " long,"
                    + " android.app.ApplicationErrorReport$CrashInfo):android.content.Intent");
    }

    public static ActivityManager.ProcessErrorStateInfo generateProcessError(
            ProcessRecord processRecord, int i, String str, String str2, String str3, String str4) {
        ActivityManager.ProcessErrorStateInfo processErrorStateInfo =
                new ActivityManager.ProcessErrorStateInfo();
        processErrorStateInfo.condition = i;
        processErrorStateInfo.processName = processRecord.processName;
        processErrorStateInfo.pid = processRecord.mPid;
        processErrorStateInfo.uid = processRecord.info.uid;
        processErrorStateInfo.tag = str;
        processErrorStateInfo.shortMsg = str2;
        processErrorStateInfo.longMsg = str3;
        processErrorStateInfo.stackTrace = str4;
        return processErrorStateInfo;
    }

    public static void resetProcessCrashMapLBp(SparseArray sparseArray, boolean z, int i, int i2) {
        for (int size = sparseArray.size() - 1; size >= 0; size--) {
            int keyAt = sparseArray.keyAt(size);
            if (z) {
                if (UserHandle.getUserId(keyAt) != i2) {}
                sparseArray.removeAt(size);
            } else if (i2 == -1) {
                if (UserHandle.getAppId(keyAt) != i) {}
                sparseArray.removeAt(size);
            } else {
                if (keyAt != UserHandle.getUid(i2, i)) {}
                sparseArray.removeAt(size);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:117:0x02de A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:124:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0034  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x003d  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:210:0x00e8  */
    /* JADX WARN: Removed duplicated region for block: B:211:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:213:0x005e  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00a2  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00e0  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00f4 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void crashApplicationInner(
            final com.android.server.am.ProcessRecord r25,
            android.app.ApplicationErrorReport.CrashInfo r26,
            int r27,
            int r28) {
        /*
            Method dump skipped, instructions count: 779
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.AppErrors.crashApplicationInner(com.android.server.am.ProcessRecord,"
                    + " android.app.ApplicationErrorReport$CrashInfo, int, int):void");
    }

    public final void dumpDebugLPr(ProtoOutputStream protoOutputStream, String str) {
        ArrayMap arrayMap;
        int i;
        String str2;
        SparseArray sparseArray;
        long j;
        String str3;
        SparseArray sparseArray2;
        ArrayMap arrayMap2;
        int i2;
        ProcessMap processMap = this.mBadProcesses;
        if (this.mProcessCrashTimes.getMap().isEmpty() && processMap.getMap().isEmpty()) {
            return;
        }
        long start = protoOutputStream.start(1146756268045L);
        protoOutputStream.write(1112396529665L, SystemClock.uptimeMillis());
        long j2 = 1138166333441L;
        if (!processMap.getMap().isEmpty()) {
            ArrayMap map = processMap.getMap();
            int size = map.size();
            int i3 = 0;
            while (i3 < size) {
                long start2 = protoOutputStream.start(2246267895811L);
                String str4 = (String) map.keyAt(i3);
                SparseArray sparseArray3 = (SparseArray) map.valueAt(i3);
                int size2 = sparseArray3.size();
                protoOutputStream.write(j2, str4);
                int i4 = 0;
                while (i4 < size2) {
                    int keyAt = sparseArray3.keyAt(i4);
                    ProcessRecord processRecord =
                            (ProcessRecord)
                                    this.mService.mProcessList.mProcessNames.get(str4, keyAt);
                    if (str == null
                            || (processRecord != null && processRecord.mPkgList.containsKey(str))) {
                        BadProcessInfo badProcessInfo = (BadProcessInfo) sparseArray3.valueAt(i4);
                        j = start;
                        str3 = str4;
                        sparseArray2 = sparseArray3;
                        ArrayMap arrayMap3 = map;
                        long start3 = protoOutputStream.start(2246267895810L);
                        protoOutputStream.write(1120986464257L, keyAt);
                        arrayMap2 = arrayMap3;
                        i2 = size;
                        protoOutputStream.write(1112396529666L, badProcessInfo.time);
                        protoOutputStream.write(1138166333443L, badProcessInfo.shortMsg);
                        protoOutputStream.write(1138166333444L, badProcessInfo.longMsg);
                        protoOutputStream.write(1138166333445L, badProcessInfo.stack);
                        protoOutputStream.end(start3);
                    } else {
                        arrayMap2 = map;
                        j = start;
                        i2 = size;
                        str3 = str4;
                        sparseArray2 = sparseArray3;
                    }
                    i4++;
                    size = i2;
                    str4 = str3;
                    sparseArray3 = sparseArray2;
                    start = j;
                    map = arrayMap2;
                }
                protoOutputStream.end(start2);
                i3++;
                j2 = 1138166333441L;
            }
        }
        long j3 = start;
        synchronized (this.mBadProcessLock) {
            try {
                if (!this.mProcessCrashTimes.getMap().isEmpty()) {
                    ArrayMap map2 = this.mProcessCrashTimes.getMap();
                    int size3 = map2.size();
                    int i5 = 0;
                    while (i5 < size3) {
                        long start4 = protoOutputStream.start(2246267895810L);
                        String str5 = (String) map2.keyAt(i5);
                        SparseArray sparseArray4 = (SparseArray) map2.valueAt(i5);
                        int size4 = sparseArray4.size();
                        protoOutputStream.write(1138166333441L, str5);
                        int i6 = 0;
                        while (i6 < size4) {
                            int keyAt2 = sparseArray4.keyAt(i6);
                            ProcessRecord processRecord2 =
                                    (ProcessRecord)
                                            this.mService.mProcessList.mProcessNames.get(
                                                    str5, keyAt2);
                            if (str == null
                                    || (processRecord2 != null
                                            && processRecord2.mPkgList.containsKey(str))) {
                                arrayMap = map2;
                                i = size3;
                                long start5 = protoOutputStream.start(2246267895810L);
                                protoOutputStream.write(1120986464257L, keyAt2);
                                str2 = str5;
                                sparseArray = sparseArray4;
                                protoOutputStream.write(
                                        1112396529666L,
                                        ((Long) sparseArray4.valueAt(i6)).longValue());
                                protoOutputStream.end(start5);
                            } else {
                                arrayMap = map2;
                                i = size3;
                                str2 = str5;
                                sparseArray = sparseArray4;
                            }
                            i6++;
                            str5 = str2;
                            map2 = arrayMap;
                            size3 = i;
                            sparseArray4 = sparseArray;
                        }
                        protoOutputStream.end(start4);
                        i5++;
                        map2 = map2;
                        size3 = size3;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        protoOutputStream.end(j3);
    }

    public final boolean dumpLPr(PrintWriter printWriter, String str, boolean z) {
        boolean z2;
        int i;
        int i2;
        AppErrors appErrors = this;
        long uptimeMillis = SystemClock.uptimeMillis();
        synchronized (appErrors.mBadProcessLock) {
            try {
                if (appErrors.mProcessCrashTimes.getMap().isEmpty()) {
                    z2 = z;
                } else {
                    ArrayMap map = appErrors.mProcessCrashTimes.getMap();
                    int size = map.size();
                    z2 = z;
                    int i3 = 0;
                    boolean z3 = false;
                    while (i3 < size) {
                        String str2 = (String) map.keyAt(i3);
                        SparseArray sparseArray = (SparseArray) map.valueAt(i3);
                        int size2 = sparseArray.size();
                        int i4 = 0;
                        while (i4 < size2) {
                            int keyAt = sparseArray.keyAt(i4);
                            ArrayMap arrayMap = map;
                            ProcessRecord processRecord =
                                    (ProcessRecord)
                                            appErrors.mService.mProcessList.mProcessNames.get(
                                                    str2, keyAt);
                            if (str == null
                                    || (processRecord != null
                                            && processRecord.mPkgList.containsKey(str))) {
                                if (!z3) {
                                    if (z2) {
                                        printWriter.println();
                                    }
                                    printWriter.println("  Time since processes crashed:");
                                    z2 = true;
                                    z3 = true;
                                }
                                printWriter.print("    Process ");
                                printWriter.print(str2);
                                printWriter.print(" uid ");
                                printWriter.print(keyAt);
                                printWriter.print(": last crashed ");
                                i2 = size;
                                TimeUtils.formatDuration(
                                        uptimeMillis - ((Long) sparseArray.valueAt(i4)).longValue(),
                                        printWriter);
                                printWriter.println(" ago");
                                i4++;
                                size = i2;
                                map = arrayMap;
                            }
                            i2 = size;
                            i4++;
                            size = i2;
                            map = arrayMap;
                        }
                        i3++;
                        map = map;
                    }
                }
                if (!appErrors.mProcessCrashCounts.getMap().isEmpty()) {
                    ArrayMap map2 = appErrors.mProcessCrashCounts.getMap();
                    int size3 = map2.size();
                    boolean z4 = false;
                    for (int i5 = 0; i5 < size3; i5++) {
                        String str3 = (String) map2.keyAt(i5);
                        SparseArray sparseArray2 = (SparseArray) map2.valueAt(i5);
                        int size4 = sparseArray2.size();
                        int i6 = 0;
                        while (i6 < size4) {
                            int keyAt2 = sparseArray2.keyAt(i6);
                            ArrayMap arrayMap2 = map2;
                            ProcessRecord processRecord2 =
                                    (ProcessRecord)
                                            appErrors.mService.mProcessList.mProcessNames.get(
                                                    str3, keyAt2);
                            if (str == null
                                    || (processRecord2 != null
                                            && processRecord2.mPkgList.containsKey(str))) {
                                if (!z4) {
                                    if (z2) {
                                        printWriter.println();
                                    }
                                    printWriter.println(
                                            "  First time processes crashed and counts:");
                                    z4 = true;
                                    z2 = true;
                                }
                                printWriter.print("    Process ");
                                printWriter.print(str3);
                                printWriter.print(" uid ");
                                printWriter.print(keyAt2);
                                printWriter.print(": first crashed ");
                                i = size3;
                                TimeUtils.formatDuration(
                                        uptimeMillis
                                                - ((Long) ((Pair) sparseArray2.valueAt(i6)).first)
                                                        .longValue(),
                                        printWriter);
                                printWriter.print(" ago; crashes since then: ");
                                printWriter.println(((Pair) sparseArray2.valueAt(i6)).second);
                            } else {
                                i = size3;
                            }
                            i6++;
                            map2 = arrayMap2;
                            size3 = i;
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        ProcessMap processMap = appErrors.mBadProcesses;
        if (!processMap.getMap().isEmpty()) {
            ArrayMap map3 = processMap.getMap();
            int size5 = map3.size();
            int i7 = 0;
            boolean z5 = false;
            while (i7 < size5) {
                String str4 = (String) map3.keyAt(i7);
                SparseArray sparseArray3 = (SparseArray) map3.valueAt(i7);
                int size6 = sparseArray3.size();
                int i8 = 0;
                while (i8 < size6) {
                    int keyAt3 = sparseArray3.keyAt(i8);
                    ProcessRecord processRecord3 =
                            (ProcessRecord)
                                    appErrors.mService.mProcessList.mProcessNames.get(str4, keyAt3);
                    if (str == null
                            || (processRecord3 != null
                                    && processRecord3.mPkgList.containsKey(str))) {
                        if (!z5) {
                            if (z2) {
                                printWriter.println();
                            }
                            printWriter.println("  Bad processes:");
                            z5 = true;
                            z2 = true;
                        }
                        BadProcessInfo badProcessInfo = (BadProcessInfo) sparseArray3.valueAt(i8);
                        printWriter.print("    Bad process ");
                        printWriter.print(str4);
                        printWriter.print(" uid ");
                        printWriter.print(keyAt3);
                        printWriter.print(": crashed at time ");
                        printWriter.println(badProcessInfo.time);
                        if (badProcessInfo.shortMsg != null) {
                            printWriter.print("      Short msg: ");
                            printWriter.println(badProcessInfo.shortMsg);
                        }
                        if (badProcessInfo.longMsg != null) {
                            printWriter.print("      Long msg: ");
                            printWriter.println(badProcessInfo.longMsg);
                        }
                        if (badProcessInfo.stack != null) {
                            printWriter.println("      Stack:");
                            int i9 = 0;
                            for (int i10 = 0; i10 < badProcessInfo.stack.length(); i10++) {
                                if (badProcessInfo.stack.charAt(i10) == '\n') {
                                    printWriter.print("        ");
                                    printWriter.write(badProcessInfo.stack, i9, i10 - i9);
                                    printWriter.println();
                                    i9 = i10 + 1;
                                }
                            }
                            if (i9 < badProcessInfo.stack.length()) {
                                printWriter.print("        ");
                                String str5 = badProcessInfo.stack;
                                printWriter.write(str5, i9, str5.length() - i9);
                                printWriter.println();
                            }
                        }
                    }
                    i8++;
                    appErrors = this;
                }
                i7++;
                appErrors = this;
            }
        }
        return z2;
    }

    public final boolean handleAppCrashInActivityController(
            final ProcessRecord processRecord,
            final ApplicationErrorReport.CrashInfo crashInfo,
            final String str,
            final String str2,
            final String str3,
            long j,
            int i,
            int i2) {
        AppErrors appErrors;
        final int i3;
        Runnable runnable = null;
        String str4 = processRecord != null ? processRecord.processName : null;
        int i4 = processRecord != null ? processRecord.mPid : i;
        if (processRecord != null) {
            appErrors = this;
            i3 = processRecord.info.uid;
        } else {
            appErrors = this;
            i3 = i2;
        }
        ActivityTaskManagerInternal activityTaskManagerInternal = appErrors.mService.mAtmInternal;
        String str5 = crashInfo.stackTrace;
        final String str6 = str4;
        final int i5 = i4;
        Runnable runnable2 =
                new Runnable() { // from class:
                                 // com.android.server.am.AppErrors$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        AppErrors appErrors2 = AppErrors.this;
                        ApplicationErrorReport.CrashInfo crashInfo2 = crashInfo;
                        String str7 = str6;
                        int i6 = i5;
                        ProcessRecord processRecord2 = processRecord;
                        String str8 = str;
                        String str9 = str2;
                        String str10 = str3;
                        int i7 = i3;
                        appErrors2.getClass();
                        if (Build.IS_DEBUGGABLE
                                && "Native crash".equals(crashInfo2.exceptionClassName)) {
                            Slog.w(
                                    "ActivityManager",
                                    AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0.m(
                                            i6,
                                            "Skip killing native crashed app ",
                                            str7,
                                            "(",
                                            ") during testing"));
                            return;
                        }
                        PinnerService$$ExternalSyntheticOutline0.m(
                                "Force-killing crashed app ",
                                str7,
                                " at watcher's request",
                                "ActivityManager");
                        if (processRecord2 != null) {
                            if (appErrors2.makeAppCrashingLocked(
                                    processRecord2, str8, str9, str10, null)) {
                                return;
                            }
                            processRecord2.killLocked(4, "crash");
                        } else {
                            Process.killProcess(i6);
                            ProcessList.killProcessGroup(i7, i6);
                            appErrors2.mService.mProcessList.noteAppKill(i6, i7, 4, 0, "crash");
                        }
                    }
                };
        ActivityTaskManagerService.LocalService localService =
                (ActivityTaskManagerService.LocalService) activityTaskManagerInternal;
        WindowManagerGlobalLock windowManagerGlobalLock =
                ActivityTaskManagerService.this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                IActivityController iActivityController =
                        ActivityTaskManagerService.this.mController;
                if (iActivityController == null) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return false;
                }
                try {
                    if (!iActivityController.appCrashed(str4, i4, str, str2, j, str5)) {
                        runnable = runnable2;
                    }
                } catch (RemoteException unused) {
                    ActivityTaskManagerService.this.mController = null;
                    Watchdog.getInstance().setActivityController(null);
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                if (runnable == null) {
                    return false;
                }
                runnable.run();
                return true;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    /* JADX WARN: Finally extract failed */
    /* JADX WARN: Removed duplicated region for block: B:104:0x02ec  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x0347  */
    /* JADX WARN: Removed duplicated region for block: B:116:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:82:0x02a2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean handleAppCrashLSPB(
            com.android.server.am.ProcessRecord r26,
            java.lang.String r27,
            java.lang.String r28,
            java.lang.String r29,
            java.lang.String r30,
            com.android.server.am.AppErrorDialog.Data r31) {
        /*
            Method dump skipped, instructions count: 854
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.AppErrors.handleAppCrashLSPB(com.android.server.am.ProcessRecord,"
                    + " java.lang.String, java.lang.String, java.lang.String, java.lang.String,"
                    + " com.android.server.am.AppErrorDialog$Data):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x00d8  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00df  */
    /* JADX WARN: Removed duplicated region for block: B:36:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void handleShowAnrUi(android.os.Message r14) {
        /*
            Method dump skipped, instructions count: 235
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.AppErrors.handleShowAnrUi(android.os.Message):void");
    }

    /* JADX WARN: Can't wrap try/catch for region: R(24:47|48|(1:50)(1:116)|51|(1:53)(1:115)|54|(1:56)(1:114)|57|(1:113)(1:61)|62|(2:64|(4:66|67|(9:91|92|93|94|95|96|(3:98|(1:100)|101)(1:105)|102|(1:104))(2:(1:71)|72)|73))(1:112)|111|67|(0)|91|92|93|94|95|96|(0)(0)|102|(0)|73) */
    /* JADX WARN: Code restructure failed: missing block: B:108:0x019e, code lost:

       android.util.Slog.d("ActivityManager", "IAFDParse false");
       r4 = false;
    */
    /* JADX WARN: Code restructure failed: missing block: B:110:0x019c, code lost:

       r3 = r19;
    */
    /* JADX WARN: Removed duplicated region for block: B:104:0x01c2 A[Catch: all -> 0x00d2, TryCatch #1 {all -> 0x00d2, blocks: (B:48:0x00c1, B:50:0x00c5, B:51:0x00d6, B:54:0x00e8, B:57:0x0102, B:59:0x010a, B:62:0x0117, B:64:0x011d, B:67:0x0131, B:71:0x01d2, B:73:0x01d9, B:89:0x0145, B:91:0x0149, B:93:0x0167, B:96:0x016d, B:98:0x01a8, B:100:0x01b2, B:102:0x01be, B:104:0x01c2, B:105:0x01b8, B:108:0x019e), top: B:47:0x00c1, outer: #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:105:0x01b8 A[Catch: all -> 0x00d2, TryCatch #1 {all -> 0x00d2, blocks: (B:48:0x00c1, B:50:0x00c5, B:51:0x00d6, B:54:0x00e8, B:57:0x0102, B:59:0x010a, B:62:0x0117, B:64:0x011d, B:67:0x0131, B:71:0x01d2, B:73:0x01d9, B:89:0x0145, B:91:0x0149, B:93:0x0167, B:96:0x016d, B:98:0x01a8, B:100:0x01b2, B:102:0x01be, B:104:0x01c2, B:105:0x01b8, B:108:0x019e), top: B:47:0x00c1, outer: #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x01e0 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:83:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:98:0x01a8 A[Catch: all -> 0x00d2, TryCatch #1 {all -> 0x00d2, blocks: (B:48:0x00c1, B:50:0x00c5, B:51:0x00d6, B:54:0x00e8, B:57:0x0102, B:59:0x010a, B:62:0x0117, B:64:0x011d, B:67:0x0131, B:71:0x01d2, B:73:0x01d9, B:89:0x0145, B:91:0x0149, B:93:0x0167, B:96:0x016d, B:98:0x01a8, B:100:0x01b2, B:102:0x01be, B:104:0x01c2, B:105:0x01b8, B:108:0x019e), top: B:47:0x00c1, outer: #4 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void handleShowAppErrorUi(android.os.Message r30) {
        /*
            Method dump skipped, instructions count: 517
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.AppErrors.handleShowAppErrorUi(android.os.Message):void");
    }

    public final void killAppAtUserRequestLocked(ProcessRecord processRecord) {
        int i;
        int i2;
        ErrorDialogController errorDialogController = processRecord.mErrorState.mDialogController;
        ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
        ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerGlobalLock) {
            try {
                if (errorDialogController.mWaitDialog != null) {
                    i = 13;
                    i2 = 1;
                } else {
                    i = 6;
                    i2 = 0;
                }
                int i3 = i;
                int i4 = i2;
                List list = errorDialogController.mCrashDialogs;
                if (list != null) {
                    errorDialogController.scheduleForAllDialogs(
                            list, new ErrorDialogController$$ExternalSyntheticLambda0(0));
                    errorDialogController.mCrashDialogs = null;
                }
                errorDialogController.clearAnrDialogs();
                List list2 = errorDialogController.mViolationDialogs;
                if (list2 != null) {
                    errorDialogController.scheduleForAllDialogs(
                            list2, new ErrorDialogController$$ExternalSyntheticLambda0(0));
                    errorDialogController.mViolationDialogs = null;
                }
                AppWaitingForDebuggerDialog appWaitingForDebuggerDialog =
                        errorDialogController.mWaitDialog;
                if (appWaitingForDebuggerDialog != null) {
                    errorDialogController.mService.mUiHandler.post(
                            new ErrorDialogController$$ExternalSyntheticLambda2(
                                    2, appWaitingForDebuggerDialog));
                    errorDialogController.mWaitDialog = null;
                }
                killAppImmediateLSP(
                        processRecord, i3, i4, "user-terminated", "user request after error");
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterProcLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterProcLockedSection();
    }

    public final void killAppImmediateLSP(
            ProcessRecord processRecord, int i, int i2, String str, String str2) {
        ProcessErrorStateRecord processErrorStateRecord = processRecord.mErrorState;
        processErrorStateRecord.mCrashing = false;
        processErrorStateRecord.mApp.mWindowProcessController.mCrashing = false;
        processErrorStateRecord.mCrashingReport = null;
        processErrorStateRecord.setNotResponding(false);
        processErrorStateRecord.mNotRespondingReport = null;
        int i3 = processErrorStateRecord.mApp.mPid;
        if (i3 <= 0 || i3 == ActivityManagerService.MY_PID) {
            return;
        }
        synchronized (this.mBadProcessLock) {
            handleAppCrashLSPB(processRecord, str, null, null, null, null);
        }
        processRecord.killLocked(i, i2, str2, str2, true, true);
    }

    public final boolean makeAppCrashingLocked(
            ProcessRecord processRecord,
            String str,
            String str2,
            String str3,
            AppErrorDialog.Data data) {
        boolean handleAppCrashLSPB;
        ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
        ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerGlobalLock) {
            try {
                ProcessErrorStateRecord processErrorStateRecord = processRecord.mErrorState;
                processErrorStateRecord.mCrashing = true;
                processErrorStateRecord.mApp.mWindowProcessController.mCrashing = true;
                processErrorStateRecord.mCrashingReport =
                        generateProcessError(processRecord, 1, null, str, str2, str3);
                processErrorStateRecord.startAppProblemLSP();
                processRecord.mWindowProcessController.stopFreezingActivities();
                synchronized (this.mBadProcessLock) {
                    handleAppCrashLSPB =
                            handleAppCrashLSPB(processRecord, "force-crash", str, str2, str3, data);
                }
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterProcLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterProcLockedSection();
        return handleAppCrashLSPB;
    }

    public final void scheduleAppCrashLocked(
            int i, int i2, String str, int i3, String str2, boolean z, int i4, Bundle bundle) {
        int i5;
        final ProcessRecord processRecord;
        synchronized (this.mService.mPidsSelfLocked) {
            processRecord = null;
            int i6 = 0;
            while (true) {
                try {
                    if (i6 >= ((SparseArray) this.mService.mPidsSelfLocked.mPidMap).size()) {
                        break;
                    }
                    ProcessRecord valueAt = this.mService.mPidsSelfLocked.valueAt(i6);
                    if (i < 0 || valueAt.uid == i) {
                        if (valueAt.mPid == i2) {
                            processRecord = valueAt;
                            break;
                        } else if (valueAt.mPkgList.containsKey(str)
                                && (i3 < 0 || valueAt.userId == i3)) {
                            processRecord = valueAt;
                        }
                    }
                    i6++;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        if (processRecord == null) {
            StringBuilder m =
                    ArrayUtils$$ExternalSyntheticOutline0.m(
                            i,
                            i2,
                            "crashApplication: nothing for uid=",
                            " initialPid=",
                            " packageName=");
            m.append(str);
            m.append(" userId=");
            m.append(i3);
            Slog.w("ActivityManager", m.toString());
            return;
        }
        if (i4 == 5) {
            String[] packageList = processRecord.mPkgList.getPackageList();
            for (i5 = 0; i5 < packageList.length; i5++) {
                PackageManagerInternal packageManagerInternal = this.mService.mPackageManagerInt;
                if (PackageManagerService.this.mProtectedPackages.isPackageStateProtected(
                        processRecord.userId, packageList[i5])) {
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            new StringBuilder("crashApplication: Can not crash protected package "),
                            packageList[i5],
                            "ActivityManager");
                    return;
                }
            }
        }
        this.mService.mOomAdjuster.mCachedAppOptimizer.unfreezeProcess(i2, 12);
        if (!processRecord.mKilledByAm && processRecord.mThread != null) {
            if (processRecord.mPid == Process.myPid()) {
                Slog.w("ActivityManager", "scheduleCrash: trying to crash system process!");
            } else {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    try {
                        processRecord.mThread.scheduleCrash(str2, i4, bundle);
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (RemoteException unused) {
                    processRecord.killLocked(4, "scheduleCrash for '" + str2 + "' failed");
                }
            }
        }
        if (z) {
            this.mService.mHandler.postDelayed(
                    new Runnable() { // from class:
                                     // com.android.server.am.AppErrors$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            AppErrors appErrors = AppErrors.this;
                            ProcessRecord processRecord2 = processRecord;
                            ActivityManagerService activityManagerService = appErrors.mService;
                            ActivityManagerService.boostPriorityForLockedSection();
                            synchronized (activityManagerService) {
                                try {
                                    ActivityManagerGlobalLock activityManagerGlobalLock =
                                            appErrors.mProcLock;
                                    ActivityManagerService.boostPriorityForProcLockedSection();
                                    synchronized (activityManagerGlobalLock) {
                                        try {
                                            appErrors.killAppImmediateLSP(
                                                    processRecord2,
                                                    13,
                                                    14,
                                                    "forced",
                                                    "killed for invalid state");
                                        } catch (Throwable th2) {
                                            ActivityManagerService
                                                    .resetPriorityAfterProcLockedSection();
                                            throw th2;
                                        }
                                    }
                                    ActivityManagerService.resetPriorityAfterProcLockedSection();
                                } catch (Throwable th3) {
                                    ActivityManagerService.resetPriorityAfterLockedSection();
                                    throw th3;
                                }
                            }
                            ActivityManagerService.resetPriorityAfterLockedSection();
                        }
                    },
                    5000L);
        }
    }
}
