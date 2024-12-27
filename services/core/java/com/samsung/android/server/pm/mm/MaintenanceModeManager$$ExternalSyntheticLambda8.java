package com.samsung.android.server.pm.mm;

import android.app.ActivityManager;

import com.samsung.android.server.pm.PmLog;

import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class MaintenanceModeManager$$ExternalSyntheticLambda8
        implements Runnable {
    public final /* synthetic */ int f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ MaintenanceModeManager$$ExternalSyntheticLambda8(int i, int i2) {
        this.f$0 = i;
        this.f$1 = i2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.f$0;
        int i2 = this.f$1;
        try {
            List<ActivityManager.RunningAppProcessInfo> runningAppProcesses =
                    ActivityManager.getService().getRunningAppProcesses();
            if (runningAppProcesses != null) {
                for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo :
                        runningAppProcesses) {
                    if (runningAppProcessInfo.pid == i && runningAppProcessInfo.uid == i2) {
                        PmLog.logDebugInfoAndLogcat(
                                "Requested by " + runningAppProcessInfo.processName,
                                "MaintenanceMode");
                        return;
                    }
                }
            }
        } catch (Exception unused) {
        }
    }
}
