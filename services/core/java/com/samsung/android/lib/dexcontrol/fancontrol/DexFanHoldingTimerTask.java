package com.samsung.android.lib.dexcontrol.fancontrol;

import com.samsung.android.lib.dexcontrol.utils.SLog;

import java.util.TimerTask;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class DexFanHoldingTimerTask extends TimerTask {
    public final DexFanControlManager mIDexFanModeControl;
    public final String mSender;
    public boolean mSetUpdate = false;

    public DexFanHoldingTimerTask(DexFanControlManager dexFanControlManager, String str) {
        this.mIDexFanModeControl = dexFanControlManager;
        this.mSender = str;
    }

    @Override // java.util.TimerTask
    public final boolean cancel() {
        SLog.i(
                "DexFanHoldingTimerTask",
                "DexFanHoldingTimerTask - " + this.mSender + " is canceled");
        destroy();
        return super.cancel();
    }

    public final void destroy() {
        DexFanControlManager dexFanControlManager = this.mIDexFanModeControl;
        if (dexFanControlManager != null && !this.mSetUpdate) {
            SLog.d(
                    "DexFanControlManager",
                    "before remove:" + dexFanControlManager.getFanHoldingRequestCount());
            dexFanControlManager.getFanHoldingTimerTaskList().remove(this);
            SLog.d(
                    "DexFanControlManager",
                    "after remove:" + dexFanControlManager.getFanHoldingRequestCount());
            dexFanControlManager
                    .getCurrentFanMode()
                    .onChangedFanHoldingRequestCount(
                            dexFanControlManager.getFanHoldingRequestCount());
            dexFanControlManager.controlFanLevel();
        }
        this.mSetUpdate = false;
    }

    @Override // java.util.TimerTask, java.lang.Runnable
    public final void run() {
        SLog.i(
                "DexFanHoldingTimerTask",
                "DexFanHoldingTimerTask - " + this.mSender + " is finished");
        destroy();
    }
}
