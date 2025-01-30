package com.android.server.chimera.heimdall;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;
import android.os.SystemClock;
import com.android.server.am.ActivityManagerService;
import java.io.PrintWriter;

/* loaded from: classes.dex */
public class HeimdallPhaseManager {
    public HeimdallKillManager mHeimdallKillManager;
    public HeimdallReportManager mHeimdallReportManager;
    public ActivityManagerService mService;
    public HeimdallProcessList mHeimdallProcessList = new HeimdallProcessList();
    public HeimdallTriggerManager mHeimdallTriggerManager = new HeimdallTriggerManager();

    public final boolean isValidMemorySizeLong(long j) {
        return j >= 0 && j <= 2147483647L;
    }

    public boolean processStartPhase(HeimdallProcessData heimdallProcessData) {
        if (isProtectedProcess(heimdallProcessData) || isProcessInProgress(heimdallProcessData)) {
            return false;
        }
        addProcessInProgress(heimdallProcessData);
        return true;
    }

    public boolean processScanPhase(HeimdallProcessData heimdallProcessData) {
        scanProcess(heimdallProcessData);
        triggerProcess(heimdallProcessData);
        if (isProcessTriggered(heimdallProcessData)) {
            return true;
        }
        removeProcessInProgress(heimdallProcessData);
        return false;
    }

    public boolean processGcPhase(HeimdallProcessData heimdallProcessData) {
        forceGc(heimdallProcessData);
        return true;
    }

    public boolean processRescanPhase(HeimdallProcessData heimdallProcessData) {
        scanProcess(heimdallProcessData);
        triggerProcess(heimdallProcessData);
        if (isProcessTriggered(heimdallProcessData)) {
            return true;
        }
        removeProcessInProgress(heimdallProcessData);
        return false;
    }

    public boolean processKillAndReportPhase(HeimdallProcessData heimdallProcessData) {
        if (isPidChanged(heimdallProcessData)) {
            removeProcessInProgress(heimdallProcessData);
            return false;
        }
        killAndReportProcess(heimdallProcessData);
        return true;
    }

    public final void removeProcessInProgress(HeimdallProcessData heimdallProcessData) {
        this.mHeimdallProcessList.removeProcessInProgress(heimdallProcessData);
    }

    public final void addProcessInProgress(HeimdallProcessData heimdallProcessData) {
        this.mHeimdallProcessList.addProcessInProgress(heimdallProcessData);
    }

    public final boolean isProcessInProgress(HeimdallProcessData heimdallProcessData) {
        return this.mHeimdallProcessList.isProcessInProgress(heimdallProcessData);
    }

    public final boolean isProtectedProcess(HeimdallProcessData heimdallProcessData) {
        return this.mHeimdallProcessList.isProtectedProcesses(heimdallProcessData);
    }

    public final void scanProcess(HeimdallProcessData heimdallProcessData) {
        long[] readMemtrackMemory;
        long[] rss = Process.getRss(heimdallProcessData.pid);
        if (rss == null || rss.length < 4) {
            Heimdall.log("Process.getRss(" + heimdallProcessData.pid + ") error.");
            return;
        }
        for (int i = 0; i < 4; i++) {
            if (!isValidMemorySizeLong(rss[i])) {
                return;
            }
        }
        long j = rss[2] + rss[3];
        if (isValidMemorySizeLong(j)) {
            if (heimdallProcessData.currentPhase() == 32) {
                heimdallProcessData.scanTimeBeforeGc = SystemClock.elapsedRealtime();
                heimdallProcessData.anonBeforeGc = (int) j;
            } else if (heimdallProcessData.currentPhase() == 8) {
                heimdallProcessData.scanTimeAfterGc = SystemClock.elapsedRealtime();
                heimdallProcessData.anonAfterGc = (int) j;
                heimdallProcessData.vmRss = (int) rss[0];
                heimdallProcessData.rssFile = (int) rss[1];
                heimdallProcessData.rssAnon = (int) rss[2];
                heimdallProcessData.vmSwap = (int) rss[3];
            }
            if (Heimdall.SCAN_VERSION < 2 || (readMemtrackMemory = Heimdall.readMemtrackMemory(heimdallProcessData.pid)) == null || readMemtrackMemory.length < 4 || readMemtrackMemory[3] != 0) {
                return;
            }
            for (int i2 = 0; i2 < 3; i2++) {
                if (!isValidMemorySizeLong(readMemtrackMemory[i2])) {
                    return;
                }
            }
            long j2 = readMemtrackMemory[0] + readMemtrackMemory[1] + readMemtrackMemory[2];
            if (isValidMemorySizeLong(j2)) {
                if (heimdallProcessData.currentPhase() == 32) {
                    heimdallProcessData.graphicsBeforeGc = (int) j2;
                } else if (heimdallProcessData.currentPhase() == 8) {
                    heimdallProcessData.graphicsAfterGc = (int) j2;
                    heimdallProcessData.memTrackEgl = (int) readMemtrackMemory[0];
                    heimdallProcessData.memTrackGl = (int) readMemtrackMemory[1];
                    heimdallProcessData.memTrackOther = (int) readMemtrackMemory[2];
                }
            }
        }
    }

    public final void triggerProcess(HeimdallProcessData heimdallProcessData) {
        this.mHeimdallTriggerManager.trigger(heimdallProcessData);
    }

    public final boolean isProcessTriggered(HeimdallProcessData heimdallProcessData) {
        return this.mHeimdallTriggerManager.isTriggered(heimdallProcessData);
    }

    public final void forceGc(HeimdallProcessData heimdallProcessData) {
        Heimdall.log("Garbage Collection is performed to " + heimdallProcessData.toDumpString());
        Process.sendSignal(heimdallProcessData.pid, 10);
    }

    public final boolean isPidChanged(HeimdallProcessData heimdallProcessData) {
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : this.mService.getRunningAppProcesses()) {
            if (runningAppProcessInfo.processName.equals(heimdallProcessData.processName) && runningAppProcessInfo.pid == heimdallProcessData.pid) {
                return false;
            }
        }
        Heimdall.log("Pid and Process name doesn't match. " + heimdallProcessData.toDumpString());
        return true;
    }

    public final void killAndReportProcess(HeimdallProcessData heimdallProcessData) {
        this.mHeimdallProcessList.pushProcessToReported(heimdallProcessData);
        this.mHeimdallReportManager.reportDumpFile(heimdallProcessData);
        this.mHeimdallKillManager.kill(heimdallProcessData);
        this.mHeimdallReportManager.reportBigdata(heimdallProcessData);
    }

    public HeimdallPhaseManager(Context context, ActivityManagerService activityManagerService) {
        this.mService = activityManagerService;
        this.mHeimdallKillManager = new HeimdallKillManager(activityManagerService);
        this.mHeimdallReportManager = new HeimdallReportManager(context, activityManagerService);
    }

    public void setHeimdallKillDisabled() {
        this.mHeimdallKillManager.setHeimdallKillDisabled();
    }

    public void setHeimdallKillEnabled() {
        this.mHeimdallKillManager.setHeimdallKillEnabled();
    }

    public void dumpKillStatus(PrintWriter printWriter) {
        this.mHeimdallKillManager.dumpKillStatus(printWriter);
    }

    public void dumpProcessList(PrintWriter printWriter) {
        this.mHeimdallProcessList.dumpProcessInfo(printWriter);
    }

    public void dumpSpecList(PrintWriter printWriter) {
        this.mHeimdallTriggerManager.dumpInfo(printWriter);
    }
}
