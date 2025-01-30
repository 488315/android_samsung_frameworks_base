package com.android.server.chimera.heimdall;

import android.os.Process;
import android.os.SystemClock;
import com.android.server.am.ActivityManagerService;
import java.io.PrintWriter;

/* loaded from: classes.dex */
public class HeimdallKillManager {
    public ActivityManagerService mService;
    public boolean KILL_DISABLED = false;
    public int mSpecKillCntAfterBoot = 0;
    public int mGlobalKillCntAfterBoot = 0;

    public void kill(HeimdallProcessData heimdallProcessData) {
        if (this.KILL_DISABLED || !heimdallProcessData.shouldKill()) {
            return;
        }
        if (heimdallProcessData.isGlobalKill()) {
            int oomScore = getOomScore(heimdallProcessData);
            Process.sendSignal(heimdallProcessData.pid, 9);
            heimdallProcessData.killTime = SystemClock.elapsedRealtime();
            Heimdall.log("Global Kill is performed to (with oom score=" + oomScore + ") " + heimdallProcessData.toDumpString());
            this.mGlobalKillCntAfterBoot = this.mGlobalKillCntAfterBoot + 1;
            return;
        }
        if (heimdallProcessData.isSpecKill()) {
            Process.sendSignal(heimdallProcessData.pid, 11);
            heimdallProcessData.killTime = SystemClock.elapsedRealtime();
            Heimdall.log("Spec Kill is performed to " + heimdallProcessData.toDumpString());
            this.mSpecKillCntAfterBoot = this.mSpecKillCntAfterBoot + 1;
        }
    }

    public final int getOomScore(HeimdallProcessData heimdallProcessData) {
        int[] iArr = new int[1];
        int[] iArr2 = {heimdallProcessData.pid};
        this.mService.getProcessStatesAndOomScoresForPIDs(iArr2, new int[1], iArr);
        return iArr[0];
    }

    public HeimdallKillManager(ActivityManagerService activityManagerService) {
        this.mService = activityManagerService;
    }

    public void setHeimdallKillDisabled() {
        Heimdall.log("KILL_DISABLED set true");
        this.KILL_DISABLED = true;
    }

    public void setHeimdallKillEnabled() {
        Heimdall.log("KILL_DISABLED set true");
        this.KILL_DISABLED = false;
    }

    public void dumpKillStatus(PrintWriter printWriter) {
        printWriter.println("\nKill status");
        printWriter.println("  Spec kill count after boot: " + this.mSpecKillCntAfterBoot);
        printWriter.println("  Global kill count after boot: " + this.mGlobalKillCntAfterBoot);
    }
}
