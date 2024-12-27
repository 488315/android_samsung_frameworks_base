package com.android.server.job;

import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.IPackageManager;
import android.net.util.NetworkConstants;
import android.os.Binder;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import com.android.modules.utils.BasicShellCommandHandler;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.job.JobSchedulerService;
import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class JobSchedulerShellCommand extends BasicShellCommandHandler {
    public JobSchedulerService mInternal;
    public IPackageManager mPM;

    public final int cacheConfigChanges(PrintWriter printWriter) {
        checkPermission("change config caching", "android.permission.DUMP");
        String nextArgRequired = getNextArgRequired();
        boolean z = true;
        if (!"on".equals(nextArgRequired)) {
            if (!"off".equals(nextArgRequired)) {
                BinaryTransparencyService$$ExternalSyntheticOutline0.m50m(getErrPrintWriter(), "Error: unknown option ", nextArgRequired);
                return 1;
            }
            z = false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            JobSchedulerService jobSchedulerService = this.mInternal;
            synchronized (jobSchedulerService.mLock) {
                JobSchedulerService.ConstantsObserver constantsObserver = jobSchedulerService.mConstantsObserver;
                if (!z || constantsObserver.mCacheConfigChanges) {
                    constantsObserver.mLastPropertiesPulled = null;
                } else {
                    constantsObserver.mLastPropertiesPulled = DeviceConfig.getProperties("jobscheduler", new String[0]);
                }
                constantsObserver.mCacheConfigChanges = z;
            }
            printWriter.println("Config caching ".concat(z ? "enabled" : "disabled"));
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return 0;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Failed to find switch 'out' block (already processed)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.calcSwitchOut(SwitchRegionMaker.java:202)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:61)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:115)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:94)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.makeEndlessLoop(LoopRegionMaker.java:281)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:64)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:92)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.makeEndlessLoop(LoopRegionMaker.java:281)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:64)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:92)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:49)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
        */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final int cancelJob(java.io.PrintWriter r14) {
        /*
            r13 = this;
            r0 = 1
            r1 = -1
            java.lang.String r2 = "cancel jobs"
            r13.checkPermission(r2)
            r2 = 0
            r3 = 0
            r7 = r2
        Lb:
            r8 = r3
        Lc:
            java.lang.String r3 = r13.getNextOption()
            if (r3 == 0) goto L5f
            int r4 = r3.hashCode()
            switch(r4) {
                case 1505: goto L3c;
                case 1512: goto L31;
                case 1333469547: goto L26;
                case 1740612539: goto L1b;
                default: goto L19;
            }
        L19:
            r4 = r1
            goto L46
        L1b:
            java.lang.String r4 = "--namespace"
            boolean r4 = r3.equals(r4)
            if (r4 != 0) goto L24
            goto L19
        L24:
            r4 = 3
            goto L46
        L26:
            java.lang.String r4 = "--user"
            boolean r4 = r3.equals(r4)
            if (r4 != 0) goto L2f
            goto L19
        L2f:
            r4 = 2
            goto L46
        L31:
            java.lang.String r4 = "-u"
            boolean r4 = r3.equals(r4)
            if (r4 != 0) goto L3a
            goto L19
        L3a:
            r4 = r0
            goto L46
        L3c:
            java.lang.String r4 = "-n"
            boolean r4 = r3.equals(r4)
            if (r4 != 0) goto L45
            goto L19
        L45:
            r4 = r2
        L46:
            switch(r4) {
                case 0: goto L5a;
                case 1: goto L51;
                case 2: goto L51;
                case 3: goto L5a;
                default: goto L49;
            }
        L49:
            java.lang.String r13 = "Error: unknown option '"
            java.lang.String r0 = "'"
            com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0.m(r14, r13, r3, r0)
            return r1
        L51:
            java.lang.String r3 = r13.getNextArgRequired()
            int r7 = android.os.UserHandle.parseUserArg(r3)
            goto Lc
        L5a:
            java.lang.String r3 = r13.getNextArgRequired()
            goto Lb
        L5f:
            if (r7 >= 0) goto L67
            java.lang.String r13 = "Error: must specify a concrete user ID"
            r14.println(r13)
            return r1
        L67:
            java.lang.String r6 = r13.getNextArg()
            java.lang.String r3 = r13.getNextArg()
            if (r3 == 0) goto L75
            int r1 = java.lang.Integer.parseInt(r3)
        L75:
            r10 = r1
            long r11 = android.os.Binder.clearCallingIdentity()
            com.android.server.job.JobSchedulerService r4 = r13.mInternal     // Catch: java.lang.Throwable -> L8a
            if (r3 == 0) goto L80
            r9 = r0
            goto L81
        L80:
            r9 = r2
        L81:
            r5 = r14
            int r13 = r4.executeCancelCommand(r5, r6, r7, r8, r9, r10)     // Catch: java.lang.Throwable -> L8a
            android.os.Binder.restoreCallingIdentity(r11)
            return r13
        L8a:
            r13 = move-exception
            android.os.Binder.restoreCallingIdentity(r11)
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.job.JobSchedulerShellCommand.cancelJob(java.io.PrintWriter):int");
    }

    public final void checkPermission(String str) {
        checkPermission(str, "android.permission.CHANGE_APP_IDLE_STATE");
    }

    public final void checkPermission(String str, String str2) {
        int callingUid = Binder.getCallingUid();
        if (callingUid != 0 && this.mPM.checkUidPermission(str2, callingUid) != 0) {
            throw new SecurityException(AccessibilityManagerService$$ExternalSyntheticOutline0.m(callingUid, "Uid ", " not permitted to ", str));
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final int enableFlexPolicy(PrintWriter printWriter) {
        char c;
        checkPermission("enable flex policy");
        int i = 0;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mInternal.setFlexPolicy(i, true);
                    printWriter.println("Set flex policy to " + i);
                    return 0;
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            if (!nextOption.equals("-o") && !nextOption.equals("--option")) {
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(printWriter, "Error: unknown option '", nextOption, "'");
                return -1;
            }
            String nextArgRequired = getNextArgRequired();
            nextArgRequired.getClass();
            switch (nextArgRequired.hashCode()) {
                case -409840230:
                    if (nextArgRequired.equals("battery-not-low")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 3227604:
                    if (nextArgRequired.equals("idle")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1436115569:
                    if (nextArgRequired.equals("charging")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 1923312055:
                    if (nextArgRequired.equals("connectivity")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    i |= 2;
                    break;
                case 1:
                    i |= 4;
                    break;
                case 2:
                    i |= 1;
                    break;
                case 3:
                    i |= 268435456;
                    break;
                default:
                    printWriter.println("Unsupported option: ".concat(nextArgRequired));
                    return -1;
            }
        }
    }

    public final void getAconfigFlagState(PrintWriter printWriter) {
        String nextArgRequired;
        checkPermission("get aconfig flag state", "android.permission.DUMP");
        nextArgRequired = getNextArgRequired();
        nextArgRequired.getClass();
        switch (nextArgRequired) {
            case "android.app.job.enforce_minimum_time_windows":
                printWriter.println(com.android.internal.hidden_from_bootclasspath.android.app.job.Flags.enforceMinimumTimeWindows());
                break;
            case "android.app.job.job_debug_info_apis":
                printWriter.println(com.android.internal.hidden_from_bootclasspath.android.app.job.Flags.jobDebugInfoApis());
                break;
            case "com.android.server.job.batch_connectivity_jobs_per_network":
                Flags.batchConnectivityJobsPerNetwork();
                printWriter.println(false);
                break;
            case "com.android.server.job.do_not_force_rush_execution_at_boot":
                Flags.doNotForceRushExecutionAtBoot();
                printWriter.println(false);
                break;
            case "android.app.job.backup_jobs_exemption":
                printWriter.println(com.android.internal.hidden_from_bootclasspath.android.app.job.Flags.backupJobsExemption());
                break;
            case "com.android.server.job.batch_active_bucket_jobs":
                Flags.batchActiveBucketJobs();
                printWriter.println(false);
                break;
            default:
                printWriter.println("Unknown flag: ".concat(nextArgRequired));
                break;
        }
    }

    public final void getConfigValue(PrintWriter printWriter) {
        String str;
        checkPermission("get device config value", "android.permission.DUMP");
        String nextArgRequired = getNextArgRequired();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            JobSchedulerService jobSchedulerService = this.mInternal;
            synchronized (jobSchedulerService.mLock) {
                DeviceConfig.Properties properties = jobSchedulerService.mConstantsObserver.mLastPropertiesPulled;
                str = null;
                if (properties != null) {
                    str = properties.getString(nextArgRequired, (String) null);
                }
            }
            printWriter.println(str);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final int getEstimatedNetworkBytes(int i, PrintWriter printWriter) {
        char c;
        checkPermission("get estimated bytes");
        String str = null;
        int i2 = 0;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                int currentUser = i2 == -2 ? ActivityManager.getCurrentUser() : i2;
                String nextArgRequired = getNextArgRequired();
                int parseInt = Integer.parseInt(getNextArgRequired());
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    int estimatedNetworkBytes = this.mInternal.getEstimatedNetworkBytes(currentUser, parseInt, i, printWriter, nextArgRequired, str);
                    printError(estimatedNetworkBytes, currentUser, parseInt, nextArgRequired, str);
                    return estimatedNetworkBytes;
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            switch (nextOption.hashCode()) {
                case 1505:
                    if (nextOption.equals("-n")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 1512:
                    if (nextOption.equals("-u")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1333469547:
                    if (nextOption.equals("--user")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 1740612539:
                    if (nextOption.equals("--namespace")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                case 3:
                    str = getNextArgRequired();
                    break;
                case 1:
                case 2:
                    i2 = UserHandle.parseUserArg(getNextArgRequired());
                    break;
                default:
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(printWriter, "Error: unknown option '", nextOption, "'");
                    return -1;
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final int getJobState(PrintWriter printWriter) {
        char c;
        checkPermission("get job state");
        String str = null;
        int i = 0;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                int currentUser = i == -2 ? ActivityManager.getCurrentUser() : i;
                String nextArgRequired = getNextArgRequired();
                int parseInt = Integer.parseInt(getNextArgRequired());
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    int jobState = this.mInternal.getJobState(printWriter, nextArgRequired, currentUser, str, parseInt);
                    printError(jobState, currentUser, parseInt, nextArgRequired, str);
                    return jobState;
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            switch (nextOption.hashCode()) {
                case 1505:
                    if (nextOption.equals("-n")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 1512:
                    if (nextOption.equals("-u")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1333469547:
                    if (nextOption.equals("--user")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 1740612539:
                    if (nextOption.equals("--namespace")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                case 3:
                    str = getNextArgRequired();
                    break;
                case 1:
                case 2:
                    i = UserHandle.parseUserArg(getNextArgRequired());
                    break;
                default:
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(printWriter, "Error: unknown option '", nextOption, "'");
                    return -1;
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final int getTransferredNetworkBytes(int i, PrintWriter printWriter) {
        char c;
        checkPermission("get transferred bytes");
        String str = null;
        int i2 = 0;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                int currentUser = i2 == -2 ? ActivityManager.getCurrentUser() : i2;
                String nextArgRequired = getNextArgRequired();
                int parseInt = Integer.parseInt(getNextArgRequired());
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    int transferredNetworkBytes = this.mInternal.getTransferredNetworkBytes(currentUser, parseInt, i, printWriter, nextArgRequired, str);
                    printError(transferredNetworkBytes, currentUser, parseInt, nextArgRequired, str);
                    return transferredNetworkBytes;
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            switch (nextOption.hashCode()) {
                case 1505:
                    if (nextOption.equals("-n")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 1512:
                    if (nextOption.equals("-u")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1333469547:
                    if (nextOption.equals("--user")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 1740612539:
                    if (nextOption.equals("--namespace")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                case 3:
                    str = getNextArgRequired();
                    break;
                case 1:
                case 2:
                    i2 = UserHandle.parseUserArg(getNextArgRequired());
                    break;
                default:
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(printWriter, "Error: unknown option '", nextOption, "'");
                    return -1;
            }
        }
    }

    public final int monitorBattery(PrintWriter printWriter) {
        checkPermission("change battery monitoring");
        String nextArgRequired = getNextArgRequired();
        boolean z = true;
        if (!"on".equals(nextArgRequired)) {
            if (!"off".equals(nextArgRequired)) {
                BinaryTransparencyService$$ExternalSyntheticOutline0.m50m(getErrPrintWriter(), "Error: unknown option ", nextArgRequired);
                return 1;
            }
            z = false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            JobSchedulerService jobSchedulerService = this.mInternal;
            synchronized (jobSchedulerService.mLock) {
                jobSchedulerService.mBatteryStateTracker.setMonitorBatteryLocked(z);
            }
            if (z) {
                printWriter.println("Battery monitoring enabled");
            } else {
                printWriter.println("Battery monitoring disabled");
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return 0;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final int onCommand(String str) {
        char c;
        long clearCallingIdentity;
        int i;
        int i2;
        boolean z;
        PrintWriter outPrintWriter = getOutPrintWriter();
        String str2 = str != null ? str : "";
        try {
            switch (str2.hashCode()) {
                case -1894245460:
                    if (str2.equals("trigger-dock-state")) {
                        c = 24;
                        break;
                    }
                    c = 65535;
                    break;
                case -1845752298:
                    if (str2.equals("get-storage-seq")) {
                        c = '\r';
                        break;
                    }
                    c = 65535;
                    break;
                case -1711668249:
                    if (str2.equals("get-estimated-upload-bytes")) {
                        c = '\f';
                        break;
                    }
                    c = 65535;
                    break;
                case -1687551032:
                    if (str2.equals("get-battery-charging")) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case -1367724422:
                    if (str2.equals("cancel")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case -1313911455:
                    if (str2.equals("timeout")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case -1274672338:
                    if (str2.equals("get-estimated-download-bytes")) {
                        c = 11;
                        break;
                    }
                    c = 65535;
                    break;
                case -1040109341:
                    if (str2.equals("cache-config-changes")) {
                        c = 19;
                        break;
                    }
                    c = 65535;
                    break;
                case 113291:
                    if (str2.equals("run")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 3540994:
                    if (str2.equals("stop")) {
                        c = 23;
                        break;
                    }
                    c = 65535;
                    break;
                case 55361425:
                    if (str2.equals("get-storage-not-low")) {
                        c = 14;
                        break;
                    }
                    c = 65535;
                    break;
                case 200896764:
                    if (str2.equals("heartbeat")) {
                        c = 18;
                        break;
                    }
                    c = 65535;
                    break;
                case 298223069:
                    if (str2.equals("get-transferred-upload-bytes")) {
                        c = 16;
                        break;
                    }
                    c = 65535;
                    break;
                case 703160488:
                    if (str2.equals("get-battery-seq")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 826231557:
                    if (str2.equals("reset-execution-quota")) {
                        c = 20;
                        break;
                    }
                    c = 65535;
                    break;
                case 859357184:
                    if (str2.equals("reset-schedule-quota")) {
                        c = 21;
                        break;
                    }
                    c = 65535;
                    break;
                case 1025586161:
                    if (str2.equals("get-aconfig-flag-state")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case 1173086269:
                    if (str2.equals("get-config-value")) {
                        c = '\n';
                        break;
                    }
                    c = 65535;
                    break;
                case 1749711139:
                    if (str2.equals("get-battery-not-low")) {
                        c = '\t';
                        break;
                    }
                    c = 65535;
                    break;
                case 1790568356:
                    if (str2.equals("get-transferred-download-bytes")) {
                        c = 15;
                        break;
                    }
                    c = 65535;
                    break;
                case 1791471818:
                    if (str2.equals("get-job-state")) {
                        c = 17;
                        break;
                    }
                    c = 65535;
                    break;
                case 1854493850:
                    if (str2.equals("monitor-battery")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 1929440801:
                    if (str2.equals("disable-flex-policy")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 1986627080:
                    if (str2.equals("reset-flex-policy")) {
                        c = 22;
                        break;
                    }
                    c = 65535;
                    break;
                case 1993990204:
                    if (str2.equals("enable-flex-policy")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    return runJob(outPrintWriter);
                case 1:
                    return timeout(outPrintWriter);
                case 2:
                    return cancelJob(outPrintWriter);
                case 3:
                    return monitorBattery(outPrintWriter);
                case 4:
                    checkPermission("disable flex policy");
                    clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        this.mInternal.setFlexPolicy(0, true);
                        outPrintWriter.println("Set flex policy to 0");
                        return 0;
                    } finally {
                    }
                case 5:
                    return enableFlexPolicy(outPrintWriter);
                case 6:
                    getAconfigFlagState(outPrintWriter);
                    return 0;
                case 7:
                    JobSchedulerService jobSchedulerService = this.mInternal;
                    synchronized (jobSchedulerService.mLock) {
                        i = jobSchedulerService.mBatteryStateTracker.mLastBatterySeq;
                    }
                    outPrintWriter.println(i);
                    return 0;
                case '\b':
                    outPrintWriter.println(this.mInternal.isBatteryCharging());
                    return 0;
                case '\t':
                    outPrintWriter.println(this.mInternal.isBatteryNotLow());
                    return 0;
                case '\n':
                    getConfigValue(outPrintWriter);
                    return 0;
                case 11:
                    return getEstimatedNetworkBytes(0, outPrintWriter);
                case '\f':
                    return getEstimatedNetworkBytes(1, outPrintWriter);
                case '\r':
                    JobSchedulerService jobSchedulerService2 = this.mInternal;
                    synchronized (jobSchedulerService2.mLock) {
                        i2 = jobSchedulerService2.mStorageController.getTracker().mLastStorageSeq;
                    }
                    outPrintWriter.println(i2);
                    return 0;
                case 14:
                    JobSchedulerService jobSchedulerService3 = this.mInternal;
                    synchronized (jobSchedulerService3.mLock) {
                        z = !jobSchedulerService3.mStorageController.getTracker().mStorageLow;
                    }
                    outPrintWriter.println(z);
                    return 0;
                case 15:
                    return getTransferredNetworkBytes(0, outPrintWriter);
                case 16:
                    return getTransferredNetworkBytes(1, outPrintWriter);
                case 17:
                    return getJobState(outPrintWriter);
                case 18:
                    checkPermission("manipulate scheduler heartbeat");
                    outPrintWriter.println("Heartbeat command is no longer supported");
                    return -1;
                case 19:
                    return cacheConfigChanges(outPrintWriter);
                case 20:
                    return resetExecutionQuota(outPrintWriter);
                case 21:
                    checkPermission("reset schedule quota");
                    clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        this.mInternal.mQuotaTracker.clear();
                        return 0;
                    } finally {
                    }
                case 22:
                    checkPermission("reset flex policy");
                    clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        this.mInternal.setFlexPolicy(0, false);
                        outPrintWriter.println("Reset flex policy to its default state");
                        return 0;
                    } finally {
                    }
                case 23:
                    return stop(outPrintWriter);
                case 24:
                    return triggerDockState();
                default:
                    return handleDefaultCommands(str);
            }
        } catch (Exception e) {
            outPrintWriter.println("Exception: " + e);
            return -1;
        }
        outPrintWriter.println("Exception: " + e);
        return -1;
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Job scheduler (jobscheduler) commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println("  run [-f | --force] [-s | --satisfied] [-u | --user USER_ID] [-n | --namespace NAMESPACE] PACKAGE JOB_ID");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    Trigger immediate execution of a specific scheduled job. For historical", "    reasons, some constraints, such as battery, are ignored when this", "    command is called. If you don't want any constraints to be ignored,", "    include the -s flag.");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    Options:", "      -f or --force: run the job even if technical constraints such as", "         connectivity are not currently met. This is incompatible with -f ", "         and so an error will be reported if both are given.");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "      -n or --namespace: specify the namespace this job sits in; the default", "         is null (no namespace).", "      -s or --satisfied: run the job only if all constraints are met.", "         This is incompatible with -f and so an error will be reported");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "         if both are given.", "      -u or --user: specify which user's job is to be run; the default is", "         the primary or system user", "  stop [-u | --user USER_ID] [-n | --namespace NAMESPACE] [-s | --stop-reason STOP_REASON] [-i | --internal-stop-reason STOP_REASON] [PACKAGE] [JOB_ID]");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    Trigger immediate stop of currently executing jobs using the specified", "    stop reasons.", "    Options:", "      -u or --user: specify which user's job is to be run; the default is");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "         all users", "      -n or --namespace: specify the namespace this job sits in; the default", "         is null (no namespace).", "      -s or --stop-reason: specify the stop reason given to the job.");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "         Valid values are those that can be returned from", "         JobParameters.getStopReason().", "          The default value is STOP_REASON_USER.", "      -i or --internal-stop-reason: specify the internal stop reason.");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "         JobScheduler will use for internal processing.", "         Valid values are those that can be returned from", "         JobParameters.getInternalStopReason().", "          The default value is INTERNAL_STOP_REASON_UNDEFINED.");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "  timeout [-u | --user USER_ID] [-n | --namespace NAMESPACE] [PACKAGE] [JOB_ID]", "    Trigger immediate timeout of currently executing jobs, as if their", "    execution timeout had expired.", "    This is the equivalent of calling `stop -s 3 -i 3`.");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    Options:", "      -u or --user: specify which user's job is to be run; the default is", "         all users", "      -n or --namespace: specify the namespace this job sits in; the default");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "         is null (no namespace).", "  cancel [-u | --user USER_ID] [-n | --namespace NAMESPACE] PACKAGE [JOB_ID]", "    Cancel a scheduled job.  If a job ID is not supplied, all jobs scheduled", "    by that package will be canceled.  USE WITH CAUTION.");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    Options:", "      -u or --user: specify which user's job is to be run; the default is", "         the primary or system user", "      -n or --namespace: specify the namespace this job sits in; the default");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "         is null (no namespace).", "  heartbeat [num]", "    No longer used.", "  cache-config-changes [on|off]");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    Control caching the set of most recently processed config flags.", "    Off by default.  Turning on makes get-config-value useful.", "  monitor-battery [on|off]", "    Control monitoring of all battery changes.  Off by default.  Turning");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    on makes get-battery-seq useful.", "  enable-flex-policy --option <option>", "    Enable flex policy with the specified options. Supported options are", "    battery-not-low, charging, connectivity, idle.");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    Multiple enable options can be specified (e.g.", "    enable-flex-policy --option battery-not-low --option charging", "  disable-flex-policy", "    Turn off flex policy so that it does not affect job execution.");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "  reset-flex-policy", "    Resets the flex policy to its default state.", "  get-aconfig-flag-state FULL_FLAG_NAME", "    Return the state of the specified aconfig flag, if known. The flag name");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "         must be fully qualified.", "  get-battery-seq", "    Return the last battery update sequence number that was received.", "  get-battery-charging");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    Return whether the battery is currently considered to be charging.", "  get-battery-not-low", "    Return whether the battery is currently considered to not be low.", "  get-config-value KEY");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    Return the most recently processed and cached config value for the KEY.", "    Only useful if caching is turned on with cache-config-changes.", "  get-estimated-download-bytes [-u | --user USER_ID] [-n | --namespace NAMESPACE] PACKAGE JOB_ID", "    Return the most recent estimated download bytes for the job.");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    Options:", "      -u or --user: specify which user's job is to be run; the default is", "         the primary or system user", "  get-estimated-upload-bytes [-u | --user USER_ID] [-n | --namespace NAMESPACE] PACKAGE JOB_ID");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    Return the most recent estimated upload bytes for the job.", "    Options:", "      -u or --user: specify which user's job is to be run; the default is", "         the primary or system user");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "  get-storage-seq", "    Return the last storage update sequence number that was received.", "  get-storage-not-low", "    Return whether storage is currently considered to not be low.");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "  get-transferred-download-bytes [-u | --user USER_ID] [-n | --namespace NAMESPACE] PACKAGE JOB_ID", "    Return the most recent transferred download bytes for the job.", "    Options:", "      -u or --user: specify which user's job is to be run; the default is");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "         the primary or system user", "  get-transferred-upload-bytes [-u | --user USER_ID] [-n | --namespace NAMESPACE] PACKAGE JOB_ID", "    Return the most recent transferred upload bytes for the job.", "    Options:");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "      -u or --user: specify which user's job is to be run; the default is", "         the primary or system user", "  get-job-state [-u | --user USER_ID] [-n | --namespace NAMESPACE] PACKAGE JOB_ID", "    Return the current state of a job, may be any combination of:");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "      pending: currently on the pending list, waiting to be active", "      active: job is actively running", "      user-stopped: job can't run because its user is stopped", "      backing-up: job can't run because app is currently backing up its data");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "      no-component: job can't run because its component is not available", "      ready: job is ready to run (all constraints satisfied or bypassed)", "      waiting: if nothing else above is printed, job not ready to run", "    Options:");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "      -u or --user: specify which user's job is to be run; the default is", "         the primary or system user", "      -n or --namespace: specify the namespace this job sits in; the default", "         is null (no namespace).");
        outPrintWriter.println("  trigger-dock-state [idle|active]");
        outPrintWriter.println("    Trigger wireless charging dock state.  Active by default.");
        outPrintWriter.println();
    }

    public final boolean printError(int i, int i2, int i3, String str, String str2) {
        switch (i) {
            case -1002:
                PrintWriter errPrintWriter = getErrPrintWriter();
                errPrintWriter.print("Job ");
                errPrintWriter.print(i3);
                errPrintWriter.print(" in package ");
                errPrintWriter.print(str);
                if (str2 != null) {
                    errPrintWriter.print(" / namespace ");
                    errPrintWriter.print(str2);
                }
                errPrintWriter.print(" / user ");
                errPrintWriter.print(i2);
                errPrintWriter.println(" has functional constraints but --force not specified");
                break;
            case -1001:
                PrintWriter errPrintWriter2 = getErrPrintWriter();
                errPrintWriter2.print("Could not find job ");
                errPrintWriter2.print(i3);
                errPrintWriter2.print(" in package ");
                errPrintWriter2.print(str);
                if (str2 != null) {
                    errPrintWriter2.print(" / namespace ");
                    errPrintWriter2.print(str2);
                }
                errPrintWriter2.print(" / user ");
                errPrintWriter2.println(i2);
                break;
            case -1000:
                PrintWriter errPrintWriter3 = getErrPrintWriter();
                errPrintWriter3.print("Package not found: ");
                errPrintWriter3.print(str);
                errPrintWriter3.print(" / user ");
                errPrintWriter3.println(i2);
                break;
        }
        return true;
    }

    public final int resetExecutionQuota(PrintWriter printWriter) {
        checkPermission("reset execution quota");
        int i = 0;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                if (i == -2) {
                    i = ActivityManager.getCurrentUser();
                }
                String nextArgRequired = getNextArgRequired();
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    JobSchedulerService jobSchedulerService = this.mInternal;
                    synchronized (jobSchedulerService.mLock) {
                        jobSchedulerService.mQuotaController.clearAppStatsLocked(i, nextArgRequired);
                    }
                    return 0;
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            if (!nextOption.equals("-u") && !nextOption.equals("--user")) {
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(printWriter, "Error: unknown option '", nextOption, "'");
                return -1;
            }
            i = UserHandle.parseUserArg(getNextArgRequired());
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final int runJob(PrintWriter printWriter) {
        char c;
        checkPermission("force scheduled jobs");
        String str = null;
        int i = 0;
        boolean z = false;
        boolean z2 = false;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                if (z2 && z) {
                    printWriter.println("Cannot specify both --force and --satisfied");
                    return -1;
                }
                int currentUser = i == -2 ? ActivityManager.getCurrentUser() : i;
                String nextArgRequired = getNextArgRequired();
                int parseInt = Integer.parseInt(getNextArgRequired());
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    int executeRunCommand = this.mInternal.executeRunCommand(currentUser, parseInt, nextArgRequired, str, z, z2);
                    if (printError(executeRunCommand, currentUser, parseInt, nextArgRequired, str)) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return executeRunCommand;
                    }
                    printWriter.print("Running job");
                    if (z2) {
                        printWriter.print(" [FORCED]");
                    }
                    printWriter.println();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return executeRunCommand;
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            }
            switch (nextOption.hashCode()) {
                case -1626076853:
                    if (nextOption.equals("--force")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case -969907566:
                    if (nextOption.equals("--satisfied")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1497:
                    if (nextOption.equals("-f")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 1505:
                    if (nextOption.equals("-n")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 1510:
                    if (nextOption.equals("-s")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 1512:
                    if (nextOption.equals("-u")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case 1333469547:
                    if (nextOption.equals("--user")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case 1740612539:
                    if (nextOption.equals("--namespace")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                case 2:
                    z2 = true;
                    break;
                case 1:
                case 4:
                    z = true;
                    break;
                case 3:
                case 7:
                    str = getNextArgRequired();
                    break;
                case 5:
                case 6:
                    i = UserHandle.parseUserArg(getNextArgRequired());
                    break;
                default:
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(printWriter, "Error: unknown option '", nextOption, "'");
                    return -1;
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final int stop(PrintWriter printWriter) {
        char c;
        checkPermission("stop jobs");
        int i = -1;
        String str = null;
        int i2 = 13;
        int i3 = -1;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                if (i3 == -2) {
                    i3 = ActivityManager.getCurrentUser();
                }
                int i4 = i3;
                String nextArg = getNextArg();
                String nextArg2 = getNextArg();
                int parseInt = nextArg2 != null ? Integer.parseInt(nextArg2) : -1;
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mInternal.executeStopCommand(printWriter, nextArg, i4, str, nextArg2 != null, parseInt, i2, i);
                    return 0;
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            switch (nextOption.hashCode()) {
                case -1405909809:
                    if (nextOption.equals("--stop-reason")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case NetworkConstants.ETHER_MTU /* 1500 */:
                    if (nextOption.equals("-i")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1505:
                    if (nextOption.equals("-n")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 1510:
                    if (nextOption.equals("-s")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 1512:
                    if (nextOption.equals("-u")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 617801983:
                    if (nextOption.equals("--internal-stop-reason")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case 1333469547:
                    if (nextOption.equals("--user")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case 1740612539:
                    if (nextOption.equals("--namespace")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                case 3:
                    i2 = Integer.parseInt(getNextArgRequired());
                    break;
                case 1:
                case 5:
                    i = Integer.parseInt(getNextArgRequired());
                    break;
                case 2:
                case 7:
                    str = getNextArgRequired();
                    break;
                case 4:
                case 6:
                    i3 = UserHandle.parseUserArg(getNextArgRequired());
                    break;
                default:
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(printWriter, "Error: unknown option '", nextOption, "'");
                    return -1;
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final int timeout(PrintWriter printWriter) {
        char c;
        checkPermission("force timeout jobs");
        String str = null;
        int i = -1;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                if (i == -2) {
                    i = ActivityManager.getCurrentUser();
                }
                int i2 = i;
                String nextArg = getNextArg();
                String nextArg2 = getNextArg();
                int parseInt = nextArg2 != null ? Integer.parseInt(nextArg2) : -1;
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mInternal.executeStopCommand(printWriter, nextArg, i2, str, nextArg2 != null, parseInt, 3, 3);
                    return 0;
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            switch (nextOption.hashCode()) {
                case 1505:
                    if (nextOption.equals("-n")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 1512:
                    if (nextOption.equals("-u")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1333469547:
                    if (nextOption.equals("--user")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 1740612539:
                    if (nextOption.equals("--namespace")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                case 3:
                    str = getNextArgRequired();
                    break;
                case 1:
                case 2:
                    i = UserHandle.parseUserArg(getNextArgRequired());
                    break;
                default:
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(printWriter, "Error: unknown option '", nextOption, "'");
                    return -1;
            }
        }
    }

    public final int triggerDockState() {
        checkPermission("trigger wireless charging dock state");
        String nextArgRequired = getNextArgRequired();
        boolean z = true;
        if (!"idle".equals(nextArgRequired)) {
            if (!"active".equals(nextArgRequired)) {
                BinaryTransparencyService$$ExternalSyntheticOutline0.m50m(getErrPrintWriter(), "Error: unknown option ", nextArgRequired);
                return 1;
            }
            z = false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            JobSchedulerService jobSchedulerService = this.mInternal;
            jobSchedulerService.getClass();
            Intent intent = z ? new Intent("android.intent.action.DOCK_IDLE") : new Intent("android.intent.action.DOCK_ACTIVE");
            intent.setPackage("android");
            intent.addFlags(1342177280);
            jobSchedulerService.getContext().sendBroadcastAsUser(intent, UserHandle.ALL);
            return 0;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
