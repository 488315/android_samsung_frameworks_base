package com.android.server.chimera.heimdall;

import android.os.Process;
import android.os.SystemClock;

import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class HeimdallKillManager {
    public boolean KILL_DISABLED;
    public int mAlwaysRunningKillCntAfterBoot;
    public int mGlobalKillCntAfterBoot;
    public ActivityManagerService mService;
    public int mSpecKillCntAfterBoot;

    public final void kill(HeimdallProcessData heimdallProcessData) {
        if (this.KILL_DISABLED || (heimdallProcessData.flags & 1024) != 1024) {
            return;
        }
        if (heimdallProcessData.isGlobalKill()) {
            int[] iArr = new int[1];
            this.mService.getProcessStatesAndOomScoresForPIDs(
                    new int[] {heimdallProcessData.pid}, new int[1], iArr);
            int i = iArr[0];
            Process.sendSignal(heimdallProcessData.pid, 9);
            heimdallProcessData.killTime = SystemClock.elapsedRealtime();
            StringBuilder m =
                    BatteryService$$ExternalSyntheticOutline0.m(
                            i, "Global Kill is performed to (with oom score=", ") ");
            m.append(heimdallProcessData.toDumpString());
            Heimdall.log(m.toString());
            this.mGlobalKillCntAfterBoot++;
            return;
        }
        if (heimdallProcessData.isSpecKill() || heimdallProcessData.isAlwaysRunningSpecKill()) {
            Process.sendSignal(heimdallProcessData.pid, 11);
            heimdallProcessData.killTime = SystemClock.elapsedRealtime();
            boolean isSpecKill = heimdallProcessData.isSpecKill();
            StringBuilder m2 =
                    Preconditions$$ExternalSyntheticOutline0.m(
                            isSpecKill ? "Spec Kill" : "AlwaysRunning Kill", " is performed to ");
            m2.append(heimdallProcessData.toDumpString());
            Heimdall.log(m2.toString());
            if (isSpecKill) {
                this.mSpecKillCntAfterBoot++;
            } else {
                this.mAlwaysRunningKillCntAfterBoot++;
            }
        }
    }
}
