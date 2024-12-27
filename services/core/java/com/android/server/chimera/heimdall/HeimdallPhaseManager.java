package com.android.server.chimera.heimdall;

import android.content.Context;
import android.os.Looper;
import android.os.SystemClock;
import android.os.SystemProperties;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerService;
import com.android.server.chimera.ChimeraCommonUtil;
import com.android.server.chimera.heimdall.HeimdallTriggerManager.HeimdallSpecManager;
import com.android.server.clipboard.ClipboardService;

import com.samsung.android.knox.custom.KnoxCustomManagerService;

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public final class HeimdallPhaseManager {
    public final HeimdallKillManager mHeimdallKillManager;
    public final HeimdallProcessList mHeimdallProcessList;
    public final HeimdallReportManager mHeimdallReportManager;
    public final HeimdallTriggerManager mHeimdallTriggerManager;
    public final ActivityManagerService mService;
    public final DefaultStrategy mDefaultStrategy = new DefaultStrategy(this, 0);
    public final DefaultStrategy mAllowedStrategy = new DefaultStrategy(this, 1);

    public final class DefaultStrategy {
        public static final String[] sBgTrimPackages = {KnoxCustomManagerService.LAUNCHER_PACKAGE};
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ HeimdallPhaseManager this$0;
        public final /* synthetic */ HeimdallPhaseManager this$0$1;

        public DefaultStrategy(HeimdallPhaseManager heimdallPhaseManager, int i) {
            this.$r8$classId = i;
            this.this$0 = heimdallPhaseManager;
            this.this$0$1 = heimdallPhaseManager;
        }

        private final void
                sendBgTrimIfAppNeed$com$android$server$chimera$heimdall$HeimdallPhaseManager$DefaultStrategy(
                        HeimdallProcessData heimdallProcessData) {}
    }

    public HeimdallPhaseManager(
            Looper looper, Context context, ActivityManagerService activityManagerService) {
        this.mService = activityManagerService;
        HeimdallProcessList heimdallProcessList = new HeimdallProcessList();
        heimdallProcessList.mTimeoutReportProtectedHour = 24;
        heimdallProcessList.mInProgressProcesses = new HashSet();
        heimdallProcessList.mReportedProcesses = new LinkedList();
        heimdallProcessList.mProtectedProcesses = new HashSet();
        this.mHeimdallProcessList = heimdallProcessList;
        HeimdallTriggerManager heimdallTriggerManager = new HeimdallTriggerManager();
        heimdallTriggerManager.ENABLE_GLOBAL_KILL = true;
        heimdallTriggerManager.ENABLE_SPEC_KILL = true;
        heimdallTriggerManager.mAlwaysRunningGlobalQuotaSpec = 31744;
        HeimdallTriggerManager.HeimdallSpecManager heimdallSpecManager =
                heimdallTriggerManager.new HeimdallSpecManager();
        heimdallTriggerManager.mSpecManager = heimdallSpecManager;
        boolean equals = "0x4f4c".equals(SystemProperties.get("ro.boot.debug_level", "0x4f4c"));
        boolean equals2 = "eng".equals(SystemProperties.get("ro.build.type", "eng"));
        if (equals || equals2 || !heimdallSpecManager.mLoadSuccess) {
            heimdallTriggerManager.ENABLE_SPEC_KILL = false;
        }
        heimdallTriggerManager.mGlobalKillThresholdKb = 0;
        int totalMemorySizeKb = (int) ChimeraCommonUtil.getTotalMemorySizeKb();
        if (totalMemorySizeKb > 12582912) {
            heimdallTriggerManager.mGlobalKillThresholdKb = 8388608;
        } else if (totalMemorySizeKb > 8388608) {
            heimdallTriggerManager.mGlobalKillThresholdKb = 6291456;
        } else if (totalMemorySizeKb > 6291456) {
            heimdallTriggerManager.mGlobalKillThresholdKb = 4194304;
        } else if (totalMemorySizeKb > 4194304) {
            heimdallTriggerManager.mGlobalKillThresholdKb = 3145728;
        }
        if (heimdallTriggerManager.mGlobalKillThresholdKb == 0) {
            heimdallTriggerManager.ENABLE_GLOBAL_KILL = false;
        }
        this.mHeimdallTriggerManager = heimdallTriggerManager;
        HeimdallKillManager heimdallKillManager = new HeimdallKillManager();
        heimdallKillManager.KILL_DISABLED = false;
        heimdallKillManager.mSpecKillCntAfterBoot = 0;
        heimdallKillManager.mGlobalKillCntAfterBoot = 0;
        heimdallKillManager.mAlwaysRunningKillCntAfterBoot = 0;
        heimdallKillManager.mService = activityManagerService;
        this.mHeimdallKillManager = heimdallKillManager;
        this.mHeimdallReportManager =
                new HeimdallReportManager(looper, context, activityManagerService);
    }

    public final void dumpKillStatus(PrintWriter printWriter) {
        HeimdallKillManager heimdallKillManager = this.mHeimdallKillManager;
        heimdallKillManager.getClass();
        printWriter.println("\nKill status");
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                new StringBuilder("  Spec kill count after boot: "),
                                heimdallKillManager.mSpecKillCntAfterBoot,
                                printWriter,
                                "  Global kill count after boot: "),
                        heimdallKillManager.mGlobalKillCntAfterBoot,
                        printWriter,
                        "  AlwaysRunning kill count after boot: "),
                heimdallKillManager.mAlwaysRunningKillCntAfterBoot,
                printWriter);
    }

    public final void dumpProcessList(PrintWriter printWriter) {
        HeimdallProcessList heimdallProcessList = this.mHeimdallProcessList;
        heimdallProcessList.getClass();
        printWriter.println("\nProcess List");
        printWriter.println(
                "  In-progress process List (length="
                        + heimdallProcessList.mInProgressProcesses.size()
                        + ")");
        Iterator it = heimdallProcessList.mInProgressProcesses.iterator();
        while (it.hasNext()) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m50m(
                    printWriter, "    ", (String) it.next());
        }
        printWriter.println(
                "  Protected process List (length="
                        + heimdallProcessList.mProtectedProcesses.size()
                        + ")");
        Iterator it2 = heimdallProcessList.mProtectedProcesses.iterator();
        while (it2.hasNext()) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m50m(
                    printWriter, "    ", (String) it2.next());
        }
        printWriter.println(
                "  Reported process List (length="
                        + ((LinkedList) heimdallProcessList.mReportedProcesses).size()
                        + ")");
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean processScanPhase(
            com.android.server.chimera.heimdall.HeimdallProcessData r14) {
        /*
            Method dump skipped, instructions count: 820
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.chimera.heimdall.HeimdallPhaseManager.processScanPhase(com.android.server.chimera.heimdall.HeimdallProcessData):boolean");
    }

    public final boolean processStartPhase(HeimdallProcessData heimdallProcessData) {
        HeimdallProcessList heimdallProcessList = this.mHeimdallProcessList;
        if (heimdallProcessList.mProtectedProcesses.contains(heimdallProcessData.processName)) {
            return false;
        }
        if (!heimdallProcessList.mReportedProcesses.isEmpty()) {
            long elapsedRealtime =
                    SystemClock.elapsedRealtime()
                            - (heimdallProcessList.mTimeoutReportProtectedHour
                                    * ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS);
            while (!heimdallProcessList.mReportedProcesses.isEmpty()) {
                HeimdallAlwaysRunningProcInfo heimdallAlwaysRunningProcInfo =
                        (HeimdallAlwaysRunningProcInfo)
                                ((LinkedList) heimdallProcessList.mReportedProcesses).peek();
                if (heimdallAlwaysRunningProcInfo.reportTime > elapsedRealtime) {
                    break;
                }
                Heimdall.log(
                        String.format(
                                "Report-protecting (%dh) is expired. "
                                        + heimdallAlwaysRunningProcInfo.toDumpString(),
                                Integer.valueOf(heimdallProcessList.mTimeoutReportProtectedHour)));
                ((LinkedList) heimdallProcessList.mReportedProcesses).poll();
                heimdallProcessList.mInProgressProcesses.remove(
                        heimdallAlwaysRunningProcInfo.processName);
            }
        }
        if (heimdallProcessList.mInProgressProcesses.contains(heimdallProcessData.processName)) {
            return false;
        }
        heimdallProcessList.mInProgressProcesses.add(heimdallProcessData.processName);
        return true;
    }
}
