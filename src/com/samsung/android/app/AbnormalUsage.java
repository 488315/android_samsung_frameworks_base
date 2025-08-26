package com.samsung.android.app;

import android.app.ActivityManager;
import android.os.Process;
import android.os.RemoteException;
import android.util.Slog;
import android.view.View;
import dalvik.system.VMDebug;

/* loaded from: classes6.dex */
public class AbnormalUsage {
    private static final long RUNTIME_USED_LIMIT = 450000;
    private static final long RUNTIME_USED_WARM_LIMIT = 380000;
    private static final String TAG = "AbnormalUsage";
    private static final long VIEW_COUNT_LIMIT = 2200;
    private static final long VIEW_COUNT_PERIOD = 8;
    private static final long VIEW_COUNT_WARM_LIMIT = 300;
    private int mCurrStopCount = 0;

    public void checkAbnormalUsage() {
        checkViewUsage();
    }

    private void checkViewUsage() {
        long jCountInstancesOfClass;
        this.mCurrStopCount++;
        long jFreeMemory = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024;
        boolean z = false;
        if ((jFreeMemory < RUNTIME_USED_WARM_LIMIT || (this.mCurrStopCount & 7) != 1) && jFreeMemory < RUNTIME_USED_LIMIT) {
            jCountInstancesOfClass = 0;
        } else {
            jCountInstancesOfClass = VMDebug.countInstancesOfClass(View.class, false);
            if (jCountInstancesOfClass > VIEW_COUNT_WARM_LIMIT) {
                z = true;
            }
        }
        if (jCountInstancesOfClass > VIEW_COUNT_LIMIT || (z && jFreeMemory > RUNTIME_USED_LIMIT)) {
            try {
                ActivityManager.getService().reportAbnormalUsage(Process.myPid(), 1);
            } catch (RemoteException e) {
                Slog.e(TAG, "ViewCount: report abnormal resource usage: " + e.getMessage());
            }
            Slog.e(TAG, "report abnormal resource usage: PID " + Process.myPid() + " view count : " + jCountInstancesOfClass + " memory usage : " + jFreeMemory + " stop count : " + this.mCurrStopCount);
        }
    }
}
