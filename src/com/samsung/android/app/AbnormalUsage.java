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
        long j;
        this.mCurrStopCount++;
        long freeMemory = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024;
        boolean z = false;
        if ((freeMemory < RUNTIME_USED_WARM_LIMIT || (this.mCurrStopCount & 7) != 1) && freeMemory < RUNTIME_USED_LIMIT) {
            j = 0;
        } else {
            j = VMDebug.countInstancesOfClass(View.class, false);
            if (j > VIEW_COUNT_WARM_LIMIT) {
                z = true;
            }
        }
        if (j > VIEW_COUNT_LIMIT || (z && freeMemory > RUNTIME_USED_LIMIT)) {
            try {
                ActivityManager.getService().reportAbnormalUsage(Process.myPid(), 1);
            } catch (RemoteException e) {
                Slog.e(TAG, "ViewCount: report abnormal resource usage: " + e.getMessage());
            }
            Slog.e(TAG, "report abnormal resource usage: PID " + Process.myPid() + " view count : " + j + " memory usage : " + freeMemory + " stop count : " + this.mCurrStopCount);
        }
    }
}
